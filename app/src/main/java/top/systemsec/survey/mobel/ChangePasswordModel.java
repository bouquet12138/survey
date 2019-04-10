package top.systemsec.survey.mobel;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.utils.OkHttpUtil;

/**
 * 改变密码
 */
public class ChangePasswordModel {

    private static final String TAG = "ChangePasswordModel";

    /**
     * 改变密码
     *
     * @param phone
     * @param newPassword
     * @param onGetInfoListener
     */
    public void changePassword(String phone, String newPassword, OnGetInfoListener<Integer> onGetInfoListener) {
        String address = ServerInfo.SERVER_IP + "/Exploit/changepass?phone=" + phone + "&newpassword=" + newPassword;//账号密码
        Log.d(TAG, "changePassword: address " + address);//打印一下地址
        OkHttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetInfoListener.onComplete();//完成
                onGetInfoListener.onFail();//失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onGetInfoListener.onComplete();//完成
                String responseData = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    int code = jsonObject.getInt("code");//代码
                    onGetInfoListener.onSuccess(code);//传出去
                } catch (JSONException e) {
                    e.printStackTrace();
                    onGetInfoListener.onFail();//失败
                }
            }
        });
    }

}
