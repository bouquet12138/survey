package top.systemsec.survey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import top.systemsec.survey.R;

public class BottomDialog extends Dialog {


    private TextView mContinuousPhotoBt;
    private TextView mOrdinaryPhotoBt;
    private TextView mAlbumBt;
    private TextView mCancelBt;

    private View.OnClickListener mOnClickListener;

    public BottomDialog(@NonNull Context context) {
        super(context, R.style.bottomDialogStyle);//底部弹窗样式
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题
        setContentView(R.layout.dialog_bottom);//设置布局
        setCancelable(true);
        initView();
        initListener();

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;

        dialogWindow.setAttributes(layoutParams);
    }

    /**
     * 初始化view
     */
    private void initView() {
        mContinuousPhotoBt = findViewById(R.id.continuousPhotoBt);
        mOrdinaryPhotoBt = findViewById(R.id.ordinaryPhotoBt);
        mAlbumBt = findViewById(R.id.albumBt);
        mCancelBt = findViewById(R.id.cancelBt);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mContinuousPhotoBt.setOnClickListener(mOnClickListener);
        mOrdinaryPhotoBt.setOnClickListener(mOnClickListener);
        mAlbumBt.setOnClickListener(mOnClickListener);
        mCancelBt.setOnClickListener(mOnClickListener);
    }

    /**
     * 初始化点击监听
     *
     * @param listener
     */
    public void initClickListener(View.OnClickListener listener) {
        this.mOnClickListener = listener;//设置一下监听
    }

}
