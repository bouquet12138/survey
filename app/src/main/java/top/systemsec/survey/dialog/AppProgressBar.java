package top.systemsec.survey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import top.systemsec.survey.R;
import top.systemsec.survey.utils.DensityUtil;


/**
 * app加载进度类
 * Created by xiaohan on 2018/1/30.
 */

public class AppProgressBar extends Dialog {

    private String hintStr;
    private TextView hintText;//提醒文本

    public AppProgressBar(Context context, String hintStr) {
        super(context, R.style.DialogBackgroundNull);
        this.hintStr = hintStr;//提醒文本
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_app_progress);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        window.setLayout(DensityUtil.dipToPx(130),
                WindowManager.LayoutParams.WRAP_CONTENT);

        layoutParams.x = 0;
        layoutParams.y = -DensityUtil.dipToPx(20);

        hintText = findViewById(R.id.hintText);
        hintText.setText(hintStr);
        setCancelable(false);//不能取消

    }

    /**
     * 全屏
     */
    private void fullScreen() {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    /**
     * 设置提醒文本
     */
    public void setHintText(String hint) {
        if (hintText != null)
            hintText.setText(hint);
    }

}
