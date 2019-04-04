package top.systemsec.survey.custom_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.AttributeSet;

public class MyHintTextView extends android.support.v7.widget.AppCompatTextView {

    public MyHintTextView(Context context) {
        super(context);
        init();
    }

    public MyHintTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        String str = getText().toString();//得到文字
        str += "<font color='#FF0000'><small>*</small></font>";
        setText(Html.fromHtml(str));
    }

}
