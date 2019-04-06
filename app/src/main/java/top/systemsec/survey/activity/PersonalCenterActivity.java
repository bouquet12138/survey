package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import top.systemsec.survey.R;
import top.systemsec.survey.base.ActivityCollector;
import top.systemsec.survey.base.BaseActivity;
import top.systemsec.survey.dialog.ConfirmDialog;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackImage;
    private TextView mUserName;
    private TextView mPhoneNum;
    private TextView mStreetName;
    private TextView mPoliceName;
    private TextView mChangePasswordText;
    private TextView mAppUpdate;
    private TextView mFeedBack;
    private TextView mAbout;
    private Button mQuitBt;

    private ConfirmDialog mConfirmDialog;//确定对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        initView();
        initListener();
    }

    private void initView() {

        mConfirmDialog = new ConfirmDialog(PersonalCenterActivity.this);

        mBackImage = findViewById(R.id.backImage);
        mUserName = findViewById(R.id.userName);
        mPhoneNum = findViewById(R.id.phoneNum);
        mStreetName = findViewById(R.id.streetName);
        mPoliceName = findViewById(R.id.policeName);
        mChangePasswordText = findViewById(R.id.changePasswordText);
        mAppUpdate = findViewById(R.id.appUpdate);
        mFeedBack = findViewById(R.id.feedBack);
        mAbout = findViewById(R.id.about);
        mQuitBt = findViewById(R.id.quitBt);
    }

    private void initListener() {
        mBackImage.setOnClickListener(this);
        mChangePasswordText.setOnClickListener(this);//改变密码
        mQuitBt.setOnClickListener(this);
        mConfirmDialog.setOnConfirmClickListener(() -> {
            ActivityCollector.finishAll();//销毁所有
            startActivity(new Intent(PersonalCenterActivity.this,
                    LoginActivity.class));//启动登录页面
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();
                break;
            case R.id.changePasswordText://修改密码
                startActivity(new Intent(PersonalCenterActivity.this,
                        ChangePasswordActivity.class));//修改密码页面
                break;
            case R.id.quitBt:
                mConfirmDialog.show();//展示对话框
                break;
        }
    }
}