package top.systemsec.survey.base;

import android.content.Context;

/**
 * Created by xiaohan on 2018/3/4.
 */

public interface IMVPBaseView {

    /**
     * 设置上传文本的提示信息
     *
     * @param msg
     */
    void setLoadingHint(String msg);

    /**
     * 显示加载中
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 关闭正在加载view
     */
    void hideLoading();

    /**
     * 显示提示
     *
     * @param msg
     */
    void showToast(String msg);

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    Context getContext();

}
