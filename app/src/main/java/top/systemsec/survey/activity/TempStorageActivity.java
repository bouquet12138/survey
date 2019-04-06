package top.systemsec.survey.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

    private TempStoragePresenter mTempStoragePresenter;

    private ImageView mBackImage;
    private MyEditText mMyEdit;
    private Button mSearchBt;
    private RecyclerView mRecyclerView;
    private TempSaveInfoAdapter mTempSaveInfoAdapter;

    private List<SurveyBean> mSurveyBeanList = new ArrayList<>();//勘察Bean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage);
        initView();
        initListener();

        mTempStoragePresenter = new TempStoragePresenter();
        mTempStoragePresenter.attachView(this);

    }

    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mMyEdit = findViewById(R.id.myEdit);
        mSearchBt = findViewById(R.id.searchBt);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        mBackImage.setOnClickListener(this);
        mSearchBt.setOnClickListener(this);
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
        } else {
            mTempSaveInfoAdapter.notifyDataSetChanged();//唤醒数据更新
        }
    }

    @Override
    protected void onDestroy() {
        mTempStoragePresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
