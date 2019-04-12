package top.systemsec.survey.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import top.systemsec.survey.R;
import top.systemsec.survey.base.ActivityCollector;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.presenter.ChangePasswordPresenter;
import top.systemsec.survey.view.IChangePasswordView;

public class ChangePasswordActivity extends MVPBaseActivity implements View.OnClickListener, IChangePasswordView {

    private String mOldPWStr;//旧密码

    private EditText mOldPasswordEdit;
    private TextView mOldPasswordHint;
    private EditText mNewPasswordEdit;
    private TextView mNewPasswordHint;
    private EditText mConfirmPasswordEdit;
    private TextView mConfirmPasswordHint;
    private Button mSubmitBt;
    private ImageView mBackImage;

    private ChangePasswordPresenter mPresenter;//修改密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        initData();
        initListener();
        mPresenter = new ChangePasswordPresenter();
        mPresenter.attachView(this);//绑定一下
    }

    /**
     * 初始化View
     */
    private void initView() {
        mOldPasswordEdit = findViewById(R.id.oldPasswordEdit);
        mOldPasswordHint = findViewById(R.id.oldPasswordHint);
        mNewPasswordEdit = findViewById(R.id.newPasswordEdit);
        mNewPasswordHint = findViewById(R.id.newPasswordHint);
        mConfirmPasswordEdit = findViewById(R.id.confirmPasswordEdit);
        mConfirmPasswordHint = findViewById(R.id.confirmPasswordHint);
        mSubmitBt = findViewById(R.id.submitBt);
        mBackImage = findViewById(R.id.backImage);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mOldPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s + "";
                if (!TextUtils.isEmpty(str) && str.equals(mOldPWStr)) //不是空且不等于旧密码
                    mOldPasswordHint.setText("");
                else
                    mOldPasswordHint.setText("密码输入错误！");//清空
                submitEnable();//提交按钮是否可用
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mNewPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password1 = s + "";
                judgePassword();//判断前后密码
                if (TextUtils.isEmpty(password1) || password1.length() < 6 || password1.length() > 10) {
                    mNewPasswordHint.setText("请输入6-10位新密码");
                } else {
                    mNewPasswordHint.setText("");
                }
                submitEnable();//提交按钮是否可用
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mConfirmPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s + "";
                if (TextUtils.isEmpty(str)) {
                    mConfirmPasswordHint.setText("请确认密码");
                }
                judgePassword();//判断一下密码
                submitEnable();//提交按钮是否可用
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSubmitBt.setOnClickListener(this);
        mBackImage.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mOldPWStr = NowUserInfo.getUserBean().getPassword();//得到密码
    }

    /**
     * 判断前后密码是否一致
     *
     * @return
     */
    private void judgePassword() {
        String password1 = mNewPasswordEdit.getText().toString();
        String password2 = mConfirmPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2))//不做判断
            return;

        if (!password1.equals(password2))
            mConfirmPasswordHint.setText("两次密码输入不一致！");
        else
            mConfirmPasswordHint.setText("");
    }

    /**
     * 提交按钮是否可用
     */
    private void submitEnable() {

        String oldStr = mOldPasswordEdit.getText().toString();
        String newPWStr = mNewPasswordEdit.getText().toString();//新密码
        String confirmPWStr = mConfirmPasswordEdit.getText().toString();//新密码

        if (TextUtils.isEmpty(oldStr) || TextUtils.isEmpty(newPWStr) || TextUtils.isEmpty(confirmPWStr)) {
            mSubmitBt.setEnabled(false);//提交按钮不可用
        }

        if (oldStr.equals(mOldPWStr) && newPWStr.length() >= 6 && newPWStr.length() <= 10 && newPWStr.equals(confirmPWStr))
            mSubmitBt.setEnabled(true);//提交按钮可用
        else
            mSubmitBt.setEnabled(false);//不可用

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBt:
                submit();
                break;
            case R.id.backImage:
                finish();//销毁活动
                break;
        }
    }

    /**
     * 提交
     */
    private void submit() {
        mPresenter.submitNewPassword();//提交新密码
    }

    @Override
    public String getNewPassword() {
        return mNewPasswordEdit.getText().toString();//得到新密码
    }

    @Override
    public void finishActivity() {
        finish();//销毁活动
    }

    @Override
    public void jumpToLogin() {
        ActivityCollector.finishAll();
        LoginActivity.actionStart(ChangePasswordActivity.this);//跳转活动
    }

    /**
     * 得到旧密码
     *
     * @return
     */
    @Override
    public String getOldPW() {
        return mOldPWStr;
    }

    /**
     * 更改旧密码
     *
     * @param newPW
     */
    @Override
    public void changeOldPW(String newPW) {
        mOldPWStr = newPW;
        mSubmitBt.setEnabled(false);//提交按钮不管用
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
