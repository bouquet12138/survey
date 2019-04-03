package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.presenter.LoginPresenter;
import top.systemsec.survey.view.ILoginView;

public class LoginActivity extends MVPBaseActivity implements View.OnClickListener, ILoginView {

    private LoginPresenter mLoginPresenter;

    private EditText mPhoneNumEdit;
    private EditText mPasswordEdit;
    private CheckBox mRememberPassword;
    private Button mLoginBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        mLoginPresenter = new LoginPresenter();
        mLoginPresenter.attachView(this);//绑定
    }

    private void initView() {
        mPhoneNumEdit = findViewById(R.id.phoneNumEdit);
        mPasswordEdit = findViewById(R.id.passwordEdit);
        mRememberPassword = findViewById(R.id.rememberPassword);
        mLoginBt = findViewById(R.id.loginBt);
    }

    private void initListener() {
        mLoginBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBt:
                break;
        }
    }

    @Override
    public boolean isRememberPassword() {
        return mRememberPassword.isChecked();
    }

    @Override
    public String getAccount() {
        return mPhoneNumEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEdit.getText().toString();
    }

    /**
     * 进入主界面
     */
    @Override
    public void enterMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);//跳转到主界面
        finish();//销毁自己
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
