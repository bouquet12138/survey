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
import android.widget.Toast;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;

public class ChangePasswordActivity extends MVPBaseActivity implements View.OnClickListener {

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
        mNewPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s + "";

                if (!TextUtils.isEmpty(str) && (str.length() < 6 || str.length() > 10)) {
                    mNewPasswordHint.setText("新密码不符合要求！");
                } else {
                    mNewPasswordHint.setText("");
                }

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
                String password1 = mNewPasswordEdit.getText().toString();
                String password2 = s + "";

                if (!TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2) && !password1.equals(password2)) {
                    mConfirmPasswordHint.setText("两次密码输入不一致！");
                } else {
                    mConfirmPasswordHint.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSubmitBt.setOnClickListener(this);
        mBackImage.setOnClickListener(this);

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

        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO：确定旧密码部分

        if (newPassword.length() < 6 || newPassword.length() > 10) {
            Toast.makeText(this, "密码长度不符合规范", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!confirmPassword.equals(newPassword)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
