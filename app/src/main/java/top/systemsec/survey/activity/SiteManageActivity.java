package top.systemsec.survey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.custom_view.MyEditText;
import top.systemsec.survey.presenter.SiteManagePresenter;
import top.systemsec.survey.view.ISiteManageView;

public class SiteManageActivity extends MVPBaseActivity implements ISiteManageView, View.OnClickListener {

    private ImageView mBackImage;
    private MyEditText mMyEdit;
    private Button mSearchBt;
    private RecyclerView mRecyclerView;

    private SiteManagePresenter mSiteManagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manage);
        initView();
        initListener();
        mSiteManagePresenter = new SiteManagePresenter();
        mSiteManagePresenter.attachView(this);//绑定
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mMyEdit = findViewById(R.id.myEdit);
        mSearchBt = findViewById(R.id.searchBt);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackImage.setOnClickListener(this);
        mSearchBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();
                break;
            case R.id.searchBt:
                break;
        }
    }

    /**
     * 返回用户要搜索的信息
     *
     * @return
     */
    @Override
    public String getSearchInfo() {
        return mMyEdit.getMyEditText();
    }

    @Override
    protected void onDestroy() {
        mSiteManagePresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
