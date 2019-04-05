package top.systemsec.survey.base;

import android.content.Context;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;


import top.systemsec.survey.dialog.AppProgressBar;

/**
 * 所有需要mvp设计模式的activity的基类
 * Created by xiaohan on 2018/3/4.
 */

public class MVPBaseActivity extends BaseActivity implements IMVPBaseView {

    private AppProgressBar appProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appProgressBar = new AppProgressBar(this, "");
    }

    /**
     * 展示加载进度
     *
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (!appProgressBar.isShowing()) {
            appProgressBar.show();
        }
    }

    /**
     * 隐藏加载进度
     */
    @Override
    public void hideLoading() {
        if (appProgressBar.isShowing()) {
            appProgressBar.dismiss();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return MVPBaseActivity.this;
    }


}
