package top.systemsec.survey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.TextView;

import top.systemsec.survey.R;

public class ConfirmDialog extends Dialog {

    private String mTitleStr = "您确定要退出当前账户吗?";
    private String mConfirmStr = "确定";
    private int mColor = 0xff5b8cff;//蓝色

    private TextView mTitleText;//标题文本
    private TextView mDeleteBt;
    private TextView mCancelBt;

    public ConfirmDialog(@NonNull Context context) {
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
        mTitleText = findViewById(R.id.titleTextView);
        mDeleteBt = findViewById(R.id.deleteBt);
        mCancelBt = findViewById(R.id.cancelBt);

        mTitleText.setText(mTitleStr);
        mDeleteBt.setText(mConfirmStr);
        mDeleteBt.setTextColor(mColor);//设置文本蓝色
    }

    private void initListener() {
        mCancelBt.setOnClickListener((v) -> {
            dismiss();//对话框消失
        });
        mDeleteBt.setOnClickListener((v) -> {
            if (mOnConfirmClickListener != null)
                mOnConfirmClickListener.onConfirmClick();//确定监听
        });
    }

    /**
     * 删除监听
     */
    public interface OnConfirmClickListener {
        void onConfirmClick();//按下删除
    }

    private OnConfirmClickListener mOnConfirmClickListener;

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        mOnConfirmClickListener = onConfirmClickListener;
    }

    /**
     * 设置数据
     *
     * @param titleStr
     * @param confirmStr
     * @param confirmColor
     */
    public void setData(String titleStr, String confirmStr, int confirmColor) {
        mTitleStr = titleStr;
        mConfirmStr = confirmStr;
        mColor = confirmColor;//确定文本的颜色
    }

}
