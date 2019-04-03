package top.systemsec.survey.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import top.systemsec.survey.R;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mNewSurvey;
    private ViewGroup mSaveInfo;
    private ViewGroup mSiteManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mNewSurvey = findViewById(R.id.newSurvey);
        mSaveInfo = findViewById(R.id.saveInfo);
        mSiteManage = findViewById(R.id.siteManage);
    }
}
