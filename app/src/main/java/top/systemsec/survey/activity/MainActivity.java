package top.systemsec.survey.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import top.systemsec.survey.R;
import top.systemsec.survey.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ViewGroup mNewSurvey;
    private ViewGroup mSaveInfo;
    private ViewGroup mSiteManage;

    private ImageView mPersonalCenterImg;//个人中心

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        mNewSurvey = findViewById(R.id.newSurvey);
        mSaveInfo = findViewById(R.id.saveInfo);
        mSiteManage = findViewById(R.id.siteManage);
        mPersonalCenterImg = findViewById(R.id.personalCenterImg);
    }

    /**
     * 初始化
     */
    private void initListener() {
        mNewSurvey.setOnClickListener(this);
        mSaveInfo.setOnClickListener(this);
        mSiteManage.setOnClickListener(this);
        mPersonalCenterImg.setOnClickListener(this);//点击监听
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newSurvey:
                startActivity(new Intent(MainActivity.this, NewSurveyActivity.class));
                break;
            case R.id.saveInfo:
                startActivity(new Intent(MainActivity.this, TempStorageActivity.class));
                break;
            case R.id.siteManage:
                startActivity(new Intent(MainActivity.this, SiteManageActivity.class));
                break;
            case R.id.personalCenterImg:
                startActivity(new Intent(MainActivity.this, PersonalCenterActivity.class));//个人中心
                break;
        }
    }
}
