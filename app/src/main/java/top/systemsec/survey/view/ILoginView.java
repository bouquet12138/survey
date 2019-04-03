package top.systemsec.survey.view;

import top.systemsec.survey.base.IMVPBaseView;

public interface ILoginView extends IMVPBaseView {

    /**
     * 是否记住密码
     *
     * @return
     */
    boolean isRememberPassword();

    /**
     * 得到账号
     *
     * @return
     */
    String getAccount();

    /**
     * 得到密码
     */
    String getPassword();

    /**
     * 进入主界面
     */
    void enterMain();

}
