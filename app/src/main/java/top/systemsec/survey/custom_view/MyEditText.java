package top.systemsec.survey.custom_view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import top.systemsec.survey.R;

public class MyEditText extends RelativeLayout {

    private EditText mEditText;
    private ImageView mDeleteImg;

    public MyEditText(Context context) {
        super(context);
        init(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_my_edit, this, true);
        mEditText = findViewById(R.id.myEditText);
        mDeleteImg = findViewById(R.id.deleteImg);

        initListener();
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mDeleteImg.setOnClickListener((View v) -> {
                    mEditText.setText("");//清空输入框
                }
        );
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s + "";
                if (TextUtils.isEmpty(str))
                    mDeleteImg.setVisibility(GONE);
                else
                    mDeleteImg.setVisibility(VISIBLE);//可见
                if (mOnTextChangeListener != null)
                    mOnTextChangeListener.onTextChange(str);//文字变化
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 得到输入框的文本
     *
     * @return
     */
    public String getMyEditText() {
        return mEditText.getText().toString();
    }

    public interface OnTextChangeListener {
        void onTextChange(String st);
    }

    private OnTextChangeListener mOnTextChangeListener;//文本变化监听

    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        mOnTextChangeListener = onTextChangeListener;
    }
}
