package top.systemsec.survey.view;

import top.systemsec.survey.base.IMVPBaseView;

public interface IChangePasswordView extends IMVPBaseView {

    /**
     * 得到新密码
     *
     * @return
     */
    String getNewPassword();

    /**
     * 结束活动
     */
    void finishActivity();

    /**
     * 跳转到登录页面
     */
    void jumpToLogin();

    /**
     * 得到旧密码
     *
     * @return
     */
    String getOldPW();

    /**
     * 密码修改成功后改变旧密码
     */
    void changeOldPW(String newPW);

}
