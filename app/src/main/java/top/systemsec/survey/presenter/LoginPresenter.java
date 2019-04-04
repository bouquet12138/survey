package top.systemsec.survey.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.view.ILoginView;

public class LoginPresenter extends MVPBasePresenter<ILoginView> {

    private final int SUCCESS = 0;//成功
    private final int PW_ERROR = 1;//账号或密码错误
    private final int NET_ERROR = 2;//网络错误

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;

            switch (msg.what) {
                case SUCCESS:
                    getView().hideLoading();//隐藏进度条
                    saveInfo();//保存用户信息
                    getView().enterMain();//进入主界面
                    break;
                case PW_ERROR:
                    break;
                case NET_ERROR:
                    break;
            }
        }
    };

    /**
     * 供Activity使用的登录方法
     */
    public void login() {
        if (!isViewAttached())//没有绑定view直接返回
            return;
        getView().showLoading("登录中");
        mHandler.sendEmptyMessageDelayed(SUCCESS, 1000);//TODO:测试用的记得删除
    }

    /**
     * 读取用户缓存在本地的信息
     */
    public void readInfo() {
        if (!isViewAttached())//没有绑定view直接返回
            return;
        SharedPreferences sharedPreferences = getView().getContext().
                getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("isRemember", false);
        String account = sharedPreferences.getString("account", "");
        String password = sharedPreferences.getString("password", "");
        getView().setAccount(account);
        getView().setPassword(password);
        getView().setRememberPassword(isRemember);//设置checkBox的选中情况

        if (!TextUtils.isEmpty(account))
            getView().setAccountSelection(account.length());//设置账号光标的位置
    }

    /**
     * 保存信息
     */
    private void saveInfo() {

        if (!isViewAttached())//没有绑定view直接返回
            return;
        SharedPreferences sharedPreferences = getView().getContext().
                getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isRemember = getView().isRememberPassword();//是否记住密码
        editor.putBoolean("isRemember", isRemember);//是否记住密码
        if (isRemember) {
            String account = getView().getAccount();//得到账号
            String password = getView().getAccount();//得到账号
            editor.putString("account", account);//账号
            editor.putString("password", password);//密码
        } else {
            editor.putString("account", "");//账号
            editor.putString("password", "");//密码
        }
        editor.apply();//应用一下
    }

}
