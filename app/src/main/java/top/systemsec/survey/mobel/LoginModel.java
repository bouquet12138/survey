package top.systemsec.survey.mobel;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.bean.UserBean;
import top.systemsec.survey.utils.OkHttpUtil;
import top.systemsec.survey.utils.ServerInfoUtil;

public class LoginModel {

    private static final String TAG = "LoginModel";

    /**
     * 登录方法
     *
     * @param phone
     * @param password
     * @param onGetInfoListener
     */
    public void login(String phone, String password, OnGetInfoListener<UserBean> onGetInfoListener) {

        String address = ServerInfo.SERVER_IP + "/Exploit/login?phone=" + phone + "&password=" + password;//账号密码
        Log.d(TAG, "login: address " + address);//地址

        OkHttpUtil.sendOkHttpRequest(address,
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: ");
                        onGetInfoListener.onComplete();//完成
                        onGetInfoListener.onFail();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        onGetInfoListener.onComplete();//完成
                        String responseData = response.body().string();
                        Log.d(TAG, "onResponse: " + responseData);//响应数据

                        String data = ServerInfoUtil.getData(responseData);//得到数据
                        if (TextUtils.isEmpty(data) || data.equals("null")) {
                            onGetInfoListener.onSuccess(null);//返回空表示用户名或密码错误
                        } else {
                            Gson gson = new Gson();
                            UserBean userBean = gson.fromJson(data, UserBean.class);
                            onGetInfoListener.onSuccess(userBean);//解析成功
                        }

                    }
                });

    }

}
