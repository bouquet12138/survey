package top.systemsec.survey.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    }

    private void initListener() {
        mSubmitBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submitBt:
                submit();
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
        }

    }
}
