package top.systemsec.survey.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import top.systemsec.survey.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewGroup mNewSurvey;
    private ViewGroup mSaveInfo;
    private ViewGroup mSiteManage;

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
    }

    /**
     * 初始化
     */
    private void initListener() {
        mNewSurvey.setOnClickListener(this);
        mSaveInfo.setOnClickListener(this);
        mSiteManage.setOnClickListener(this);
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
        }
    }
}
