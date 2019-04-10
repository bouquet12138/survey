package top.systemsec.survey.presenter;

import android.os.Handler;
import android.os.Message;

import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.mobel.ChangePasswordModel;
import top.systemsec.survey.view.IChangePasswordView;

public class ChangePasswordPresenter extends MVPBasePresenter<IChangePasswordView> {

    private ChangePasswordModel mChangePasswordModel = new ChangePasswordModel();//修改密码

    private final int SUCCESS = 0;//成功
    private final int USER_NOT_EXIST = 1;//用户不存在
    private final int NET_ERROR = 2;//网络错误
    private final int COMPLETE = 3;//响应完成

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    getView().showToast("密码修改成功");
                    NowUserInfo.getUserBean().setPassword(getView().getNewPassword());//设置新密码
                    getView().changeOldPW(getView().getNewPassword());//修改旧密码
                    break;
                case USER_NOT_EXIST://用户不存在请重新登录
                    getView().jumpToLogin();//跳转到登录
                    break;
                case NET_ERROR:
                    getView().showToast("网络错误");
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度条
                    break;
            }
        }
    };

    /**
     * 提交新密码
     */
    public void submitNewPassword() {
        if (!isViewAttached())
            return;

        String oldPW = getView().getOldPW();
        String newPW = getView().getNewPassword();
        if (oldPW.equals(newPW)) {
            getView().showToast("新旧密码一致");
            return;
        }

        if (newPW.contains("&")) {
            getView().showToast("新密码不能包含&");
            return;
        }

        getView().showLoading("密码修改中..");

        mChangePasswordModel.changePassword(NowUserInfo.getUserBean().getPhone(), newPW, new OnGetInfoListener<Integer>() {
            @Override
            public void onSuccess(Integer info) {
                if (info == 1)
                    mHandler.sendEmptyMessage(SUCCESS);//修改成功
                else if (info == -1)
                    mHandler.sendEmptyMessage(USER_NOT_EXIST);//用户不存在
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(COMPLETE);//完成
            }
        });

    }

}
