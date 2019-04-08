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
import top.systemsec.survey.base.MVPBaseActivity;

public class ChangePasswordActivity extends MVPBaseActivity implements View.OnClickListener {

    private boolean mOldPW, mNewPW, mConfirmPW;
    private EditText mOldPasswordEdit;
    private TextView mOldPasswordHint;
    private EditText mNewPasswordEdit;
    private TextView mNewPasswordHint;
    private EditText mConfirmPasswordEdit;
    private TextView mConfirmPasswordHint;
    private Button mSubmitBt;
    private ImageView mBackImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
        initListener();
    }

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

    private void initListener() {

        mOldPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:旧密码确认
                String str = s + "";
                if (TextUtils.isEmpty(str)) {
                    mOldPW = false;//旧密码错误
                } else {
                    mOldPW = true;//旧密码正确
                }

                mSubmitBt.setEnabled(mOldPW && mNewPW && mConfirmPW);//提交按钮是否可用
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
                    mNewPW = false;
                } else {
                    mNewPasswordHint.setText("");
                    mNewPW = true;
                }

                mSubmitBt.setEnabled(mOldPW && mNewPW && mConfirmPW);//提交按钮是否可用
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
                    mConfirmPW = false;
                }

                judgePassword();//判断一下密码

                mSubmitBt.setEnabled(mOldPW && mNewPW && mConfirmPW);//提交按钮是否可用
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSubmitBt.setOnClickListener(this);
        mBackImage.setOnClickListener(this);

    }

    /**
     * 判断前后密码是否一致
     *
     * @return
     */
    public void judgePassword() {
        String password1 = mNewPasswordEdit.getText().toString();
        String password2 = mConfirmPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2))//不做判断
            return;

        if (!password1.equals(password2)) {
            mConfirmPasswordHint.setText("两次密码输入不一致！");
            mConfirmPW = false;//确认密码错误
        } else {
            mConfirmPasswordHint.setText("");
            mConfirmPW = true;//密码确认成功
        }
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
        String oldPassword = mOldPasswordEdit.getText().toString();
        String newPassword = mNewPasswordEdit.getText().toString();
        String confirmPassword = mConfirmPasswordEdit.getText().toString();

        //TODO:直接提交即可
    }
}
