package top.systemsec.survey.custom_view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.ListViewAdapter;
import top.systemsec.survey.utils.DensityUtil;

public class SpinnerView extends LinearLayout {

    private static final String TAG = "SpinnerView";

    private View mLayout;

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private TextView mSpinnerText;

    private String mNowStr;//当前字符串
    private String[] mListData;

    public SpinnerView(Context context) {
        super(context);
        init(context);
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpinnerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_spinner_head, this);
        mSpinnerText = findViewById(R.id.textView);
        if (!TextUtils.isEmpty(mNowStr))//不是空
            mSpinnerText.setText(mNowStr);//设置字符串
    }

    public void setData(String[] data) {
        this.mListData = data;
        mAdapter = new ListViewAdapter(getContext(), R.layout.item_string, data); //默认设置下拉框的标题为数据的第一个
        mSpinnerText.setOnClickListener((v) -> {
                    initView();

                    mPopupWindow.showAsDropDown(mSpinnerText);
                }
        );
    }


    private void initView() {
        if (mLayout == null) {
            mLayout = LayoutInflater.from(getContext()).inflate(R.layout.dialog_custom_spinner, null);
            // 实例化listView
            mListView = mLayout.findViewById(R.id.listView);
            // 设置listView的适配器
            mListView.setAdapter(mAdapter);
            // listView的item点击事件
            mListView.setOnItemClickListener((AdapterView<?> arg0, View arg1, int arg2, long arg3) -> {
                        mNowStr = mListData[arg2];
                        mSpinnerText.setText(mNowStr);// 设置所选的item作为下拉框的标题
                        if (mPopupWindow != null)
                            mPopupWindow.dismiss(); //弹框消失
                    }
            );
        }

        if (mPopupWindow == null) {
            // 实例化一个PopupWindow对象
            mPopupWindow = new PopupWindow(getContext());
            // 设置弹框的宽度为布局文件的宽

            mPopupWindow.setWidth(getWidth());
            // 高度设置的300
            mPopupWindow.setHeight(DensityUtil.dipToPx(300));
            // 设置一个透明的背景，不然无法实现点击弹框外，弹框消失
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击弹框外部，弹框消失
            mPopupWindow.setOutsideTouchable(true);
            // 设置焦点
            mPopupWindow.setFocusable(true);
            // 设置所在布局
            mPopupWindow.setContentView(mLayout);
            // 设置弹框出现的位置，在v的正下方横轴偏移textView的宽度
        }

    }

    /**
     * 设置当前
     *
     * @param nowStr
     */
    public void setNowStr(String nowStr) {
        mNowStr = nowStr;
        if (mSpinnerText != null)
            mSpinnerText.setText(mNowStr);//设置名称
    }

    /**
     * 得到当前字符串
     *
     * @return
     */
    public String getNowStr() {
        return mNowStr;
    }
}