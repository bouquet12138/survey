package top.systemsec.survey.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.SiteManageAdapter;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.custom_view.MyEditText;
import top.systemsec.survey.presenter.SiteManagePresenter;
import top.systemsec.survey.view.ISiteManageView;

public class SiteManageActivity extends MVPBaseActivity implements ISiteManageView, View.OnClickListener {

    private static final String TAG = "SiteManageActivity";

    private ImageView mBackImage;
    private MyEditText mMyEdit;

    private RecyclerView mRecyclerView;
    private List<SurveyBean> mSurveyBeanList = new ArrayList<>();//勘察Bean
    private SiteManageAdapter mSiteManageAdapter;//站点管理

    private SiteManagePresenter mSiteManagePresenter;//站点管理主持
    private TextView mNoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manage);
        initView();
        initListener();
        mSiteManagePresenter = new SiteManagePresenter();
        mSiteManagePresenter.attachView(this);//绑定

        mSiteManagePresenter = new SiteManagePresenter();
        mSiteManagePresenter.attachView(this);//绑定View

        mSiteManagePresenter.search();//搜索
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mMyEdit = findViewById(R.id.myEdit);
        mRecyclerView = findViewById(R.id.recyclerView);
        mNoData = findViewById(R.id.noData);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackImage.setOnClickListener(this);
        mMyEdit.setOnTextChangeListener((str) -> mSiteManagePresenter.search());//搜索一下
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();
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

        if (mSiteManageAdapter == null) {
            mSiteManageAdapter = new SiteManageAdapter(mSurveyBeanList);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(SiteManageActivity.this));//垂直布局
            mRecyclerView.setAdapter(mSiteManageAdapter);//设置适配器

            mSiteManageAdapter.setOnItemClickListener((position) -> {
                SurveyBean surveyBean = mSurveyBeanList.get(position);//得到勘察Bean
                Gson gson = new Gson();
                String surveyBeanStr = gson.toJson(surveyBean);//转换成json字符串
                Log.d(TAG, "setSearchResult: json " + surveyBeanStr);
                SiteManageShowActivity.actionStart(surveyBeanStr, SiteManageActivity.this);//跳转活动
            });

        } else {
            mSiteManageAdapter.notifyDataSetChanged();//唤醒数据更新
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
        mSiteManagePresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
