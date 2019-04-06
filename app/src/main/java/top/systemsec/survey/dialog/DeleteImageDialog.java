package top.systemsec.survey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import top.systemsec.survey.R;

public class DeleteImageDialog extends Dialog {


    private TextView mDeleteBt;
    private TextView mCancelBt;

    public DeleteImageDialog(@NonNull Context context) {
        super(context, R.style.DialogBackgroundNull);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        setContentView(R.layout.dialog_app_alert);//设置布局
        initView();
        initListener();

        Window window = getWindow();
        window.setWindowAnimations(R.style.windowAnim);
    }

    private void initView() {
        mDeleteBt = findViewById(R.id.deleteBt);
        mCancelBt = findViewById(R.id.cancelBt);
    }

    private void initListener() {
        mCancelBt.setOnClickListener((v) -> {
            dismiss();//对话框消失
        });
        mDeleteBt.setOnClickListener((v) -> {
            if (mOnDeleteClickListener != null)
                mOnDeleteClickListener.onDeleteClick();//点击了删除按钮
        });
    }

    /**
     * 删除监听
     */
    public interface OnDeleteClickListener {
        void onDeleteClick();//按下删除
    }

    private OnDeleteClickListener mOnDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        mOnDeleteClickListener = onDeleteClickListener;
    }
}
