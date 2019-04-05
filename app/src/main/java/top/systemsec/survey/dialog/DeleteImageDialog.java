package top.systemsec.survey.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Window;
import android.widget.TextView;

import top.systemsec.survey.R;

public class DeleteImageDialog extends AlertDialog {


    private TextView mDeleteBt;
    private TextView mCancelBt;

    public DeleteImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        setContentView(R.layout.dialog_delete_image);//设置布局
        initView();
        initListener();
    }

    private void initView() {
        mDeleteBt = findViewById(R.id.deleteBt);
        mCancelBt = findViewById(R.id.cancelBt);
    }

    private void initListener() {
        mCancelBt.setOnClickListener((v) -> {
            dismiss();//对话框消失
        });
    }

}
