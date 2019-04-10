package top.systemsec.survey.mobel;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.bean.StreetAndPolice;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.utils.OkHttpUtil;
import top.systemsec.survey.utils.ServerInfoUtil;

public class NewSurveyModel {

    private static final String TAG = "NewSurveyModel";

    /**
     * 保存勘察
     */
    public void saveSurvey(SurveyBean surveyBean) {
        if (surveyBean != null) {
            surveyBean.save();//保存surveyBean
        }
    }

    /**
     * 得到街道和警局
     */
    public void getStreetAndPolice(OnGetInfoListener<StreetAndPolice> onGetInfoListener) {

        String address = ServerInfo.SERVER_IP + "/Exploit/basedata";
        Log.d(TAG, "getStreetAndPolice: " + address);

        OkHttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetInfoListener.onComplete();//完成
                onGetInfoListener.onFail();//网络错误
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                onGetInfoListener.onComplete();//完成
                String responseData = response.body().string();//响应数据
                Log.d(TAG, "onResponse: " + responseData);
                String data = ServerInfoUtil.getData(responseData);//将响应数据添加进去

                if (!TextUtils.isEmpty(data)) {
                    try {
                        StreetAndPolice streetAndPolice = new Gson().fromJson
                                (data, StreetAndPolice.class);//解析一下
                        onGetInfoListener.onSuccess(streetAndPolice);
                    } catch (JsonSyntaxException e) {
                        onGetInfoListener.onFail();//网络错误
                    }
                } else {
                    onGetInfoListener.onFail();//网络错误
                }
            }
        },3);

    }

}
