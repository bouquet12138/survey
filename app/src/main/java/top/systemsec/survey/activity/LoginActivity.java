package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
        mLoginPresenter.readInfo();//读取存储在本地的信息
    }

    private void initView() {
        mPhoneNumEdit = findViewById(R.id.phoneNumEdit);
        mPasswordEdit = findViewById(R.id.passwordEdit);
        mRememberPassword = findViewById(R.id.rememberPassword);
        mLoginBt = findViewById(R.id.loginBt);
    }

    private void initListener() {
        mLoginBt.setOnClickListener(this);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginEnable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mPhoneNumEdit.addTextChangedListener(textWatcher);//监听输入框的变化
        mPasswordEdit.addTextChangedListener(textWatcher);//监听输入框的变化
    }

    /**
     * 登录按钮是否可用
     */
    public void loginEnable() {
        String account = mPhoneNumEdit.getText().toString();//账号
        String password = mPasswordEdit.getText().toString();//密码
        if (!TextUtils.isEmpty(account) && account.length() == 11 && !TextUtils.isEmpty(password)) {
            mLoginBt.setEnabled(true);//登录按钮可用
        } else {
            mLoginBt.setEnabled(false);//登录按钮不可用
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBt:
                mLoginPresenter.login();//登录
                break;
        }
    }

    /**
     * 是否记住密码
     * @return
     */
    @Override
    public boolean isRememberPassword() {
        return mRememberPassword.isChecked();
    }

    @Override
    public void setRememberPassword(boolean check) {
        mRememberPassword.setChecked(check);//设置是否选中
    }

    @Override
    public void setAccount(String account) {
        mPhoneNumEdit.setText(account);//账号
    }

    /**
     * 设置光标
     * @param position
     */
    @Override
    public void setAccountSelection(int position) {
        mPhoneNumEdit.setSelection(position);
    }

    @Override
    public void setPassword(String password) {
        mPasswordEdit.setText(password);
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
