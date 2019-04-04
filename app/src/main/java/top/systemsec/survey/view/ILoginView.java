package top.systemsec.survey.view;

import top.systemsec.survey.base.IMVPBaseView;

public interface ILoginView extends IMVPBaseView {

    /**
     * 是否记住密码
     *
     * @return
     */
    boolean isRememberPassword();

    void setRememberPassword(boolean check);

    /**
     * 设置账号
     *
     * @param account
     */
    void setAccount(String account);

    /**
     * 设置账号光标的位置
     *
     * @param position
     */
    void setAccountSelection(int position);

    /**
     * 设置密码
     *
     * @param password
     */
    void setPassword(String password);

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
