package top.systemsec.survey.custom_view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
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
    private String[] mListData;
    private Drawable mRightDrawable;
    private Drawable mDownDrawable;

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

        mRightDrawable = getContext().getResources().getDrawable(R.drawable.right_rectangle);
        mDownDrawable = getContext().getResources().getDrawable(R.drawable.down_rectangle);

    }

    public void setData(String[] data) {
        this.mListData = data;
        mAdapter = new ListViewAdapter(getContext(), R.layout.item_string, data); //默认设置下拉框的标题为数据的第一个
        mSpinnerText.setOnClickListener((v) -> {
                    initView();
                    mSpinnerText.setCompoundDrawables(null, null, mDownDrawable, null);
                    mPopupWindow.showAsDropDown(v, 0, 0);
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
                        mSpinnerText.setText(mListData[arg2]);// 设置所选的item作为下拉框的标题
                        mSpinnerText.setCompoundDrawables(null, null, mRightDrawable, null);

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

}