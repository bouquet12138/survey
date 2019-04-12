package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.TempSaveInfoAdapter;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.custom_view.MyEditText;
import top.systemsec.survey.presenter.TempStoragePresenter;
import top.systemsec.survey.view.ITempStorageView;

public class TempStorageActivity extends MVPBaseActivity implements View.OnClickListener, ITempStorageView {

    private static final String TAG = "TempStorageActivity";

    private TempStoragePresenter mTempStoragePresenter;

    private ImageView mBackImage;
    private MyEditText mMyEdit;
    private Button mSearchBt;
    private RecyclerView mRecyclerView;
    private TempSaveInfoAdapter mTempSaveInfoAdapter;

    private List<SurveyBean> mSurveyBeanList = new ArrayList<>();//勘察Bean
    private TextView mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage);
        initView();
        initListener();

        mTempStoragePresenter = new TempStoragePresenter();
        mTempStoragePresenter.attachView(this);

        mTempStoragePresenter.search();//刚上来就搜索一下
    }

    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mMyEdit = findViewById(R.id.myEdit);
        mSearchBt = findViewById(R.id.searchBt);
        mRecyclerView = findViewById(R.id.recyclerView);
        mNoData = findViewById(R.id.noData);
    }

    private void initListener() {
        mBackImage.setOnClickListener(this);
        mSearchBt.setOnClickListener(this);

        mMyEdit.setOnTextChangeListener((str) -> mTempStoragePresenter.search());//搜索一下
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();//销毁自己
                break;
            case R.id.searchBt:
                mTempStoragePresenter.search();//搜索一下
                break;
        }
    }

    /**
     * 返回用户要查找的数据
     *
     * @return
     */
    @Override
    public String getSearchInfo() {
        return mMyEdit.getMyEditText();
    }

    /**
     * 设置搜索结果
     *
     * @param surveyBeans
     */
    @Override
    public void setSearchResult(List<SurveyBean> surveyBeans) {

        mSurveyBeanList.clear();
        mSurveyBeanList.addAll(surveyBeans);//全添加进来

        if (mTempSaveInfoAdapter == null) {
            mTempSaveInfoAdapter = new TempSaveInfoAdapter(mSurveyBeanList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(TempStorageActivity.this));//垂直布局
            mRecyclerView.setAdapter(mTempSaveInfoAdapter);//设置适配器

            mTempSaveInfoAdapter.setOnItemClickListener((position) -> {
                SurveyBean surveyBean = mSurveyBeanList.get(position);//得到勘察Bean
                Gson gson = new Gson();
                String surveyBeanStr = gson.toJson(surveyBean);//转换成json字符串
                TempStorageShowActivity.actionStart(surveyBeanStr, TempStorageActivity.this);
            });

        } else {
            mTempSaveInfoAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    public void showNoData(boolean show) {
        if (show)
            mNoData.setVisibility(View.VISIBLE);//可见
        else
            mNoData.setVisibility(View.GONE);//不可见
    }

    @Override
    protected void onDestroy() {
        mTempStoragePresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 重新回来就刷新一下
     */
    @Override
    protected void onResume() {
        super.onResume();
        mTempStoragePresenter.refresh();//要刷新
    }
}
