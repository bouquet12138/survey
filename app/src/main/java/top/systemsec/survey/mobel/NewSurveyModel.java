package top.systemsec.survey.mobel;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.bean.ImageList;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.StreetAndPolice;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.utils.OkHttpUtil;
import top.systemsec.survey.utils.ServerInfoUtil;

public class NewSurveyModel {

    private static final String TAG = "NewSurveyModel";

    /**
     * 上传没有图片的数据
     *
     * @param bean   要上传的勘察bean
     * @param hasImg 是否有图片
     */
    public void uploadDataNoImage(SurveyBean bean, boolean hasImg, OnGetInfoListener<String> onGetInfoListener) {

        String address = ServerInfo.SERVER_IP + "/Exploit/add";

        Log.d(TAG, "UploadDataNoImage: address " + address);

        Map<String, String> maps = new LinkedHashMap<>();//线性hashMap
        maps.put("pointName", bean.getPointName());//点名称
        maps.put("detailAddress", bean.getDetailAddress());//详细地址
        maps.put("longitude", bean.getLongitude());//经度
        maps.put("latitude", bean.getLatitude());//纬度
        maps.put("street", bean.getStreet());//街道
        maps.put("police", bean.getPolice());//街道
        maps.put("cameraInstallType", bean.getCameraInstallType() + "");//安装方式
        maps.put("poleHigh", bean.getPoleHigh() + "");//立杆高度
        maps.put("crossArmNum", bean.getCrossArmNum() + "");//立杆高度
        maps.put("direction1", bean.getDir1());//方向1
        maps.put("direction2", bean.getDir2());//方向2
        maps.put("faceRecNum", bean.getFaceRecNum() + "");//人脸识别数
        maps.put("faceLightNum", bean.getFaceLightNum() + "");//人脸识别数
        maps.put("carNumRecNum", bean.getCarNumRecNum() + "");//车牌数
        maps.put("globalNum", bean.getGlobalNum() + "");//环球灯数
        maps.put("globalNum", bean.getGlobalNum() + "");//环球灯数

        if (!hasImg) { //如果不包含图片
            maps.put("envImgList", "[]");//环境数
            maps.put("overallViewList", "[]");//全景照
            maps.put("closeShotList", "[]");//近景照
            maps.put("gpsImgList", "[]");//gps图片
            maps.put("sceneImgList", "[]");//现场画面照
        } else {//包含图片
            List<ImageUploadState> imageUploadStates = bean.getImgList();//图片列表
            ImageList imageList = new ImageList(imageUploadStates);
            Log.d(TAG, "uploadDataNoImage: " + imageList.getImageUrlStr());
            maps.put("envImgList", imageList.getImageUrlStr());//环境数
            maps.put("overallViewList", imageList.getImageUrlStr1());//全景照
            maps.put("closeShotList", imageList.getImageUrlStr2());//近景照
            maps.put("gpsImgList", imageList.getImageUrlStr3());//gps图片
            maps.put("sceneImgList", imageList.getImageUrlStr4());//现场画面照
        }

        maps.put("remark", bean.getRemark());//备注

        bean.setSubmitTime();//提交时间

        maps.put("submitTime", bean.getSubmitTime());//提交时间
        maps.put("id", bean.getNumber());//编号
        Log.d(TAG, "uploadDataNoImage: userId " + NowUserInfo.getUserBean().getId() + "");
        maps.put("userid", NowUserInfo.getUserBean().getId() + "");//用户id

        OkHttpUtil.postOkHttpFormRequest(address, maps, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetInfoListener.onComplete();//成功
                onGetInfoListener.onFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                boolean success = false;
                String responseData = response.body().string();
                Log.d(TAG, "onResponse: " + responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    int code = jsonObject.getInt("code");

                    if (code == 1) {
                        String data = jsonObject.getString("data");
                        success = true;
                        onGetInfoListener.onSuccess(data);//成功
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (!success)
                    onGetInfoListener.onFail();//网络错误
            }
        });

    }

    /**
     * 上传图片
     */
    public void uploadImage(File file, OnGetInfoListener<String> onGetInfoListener) {

        String address = ServerInfo.SERVER_IP + "/Exploit/uploadImge";//上传图片
        Log.d(TAG, "uploadImage: " + address);
        OkHttpUtil.postImage(address, file, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetInfoListener.onFail();//网络错误
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    String path = jsonObject.getString("path");

                    if (!TextUtils.isEmpty(path)) {
                        onGetInfoListener.onSuccess(path);
                    } else
                        onGetInfoListener.onFail();//上传失败

                } catch (JSONException e) {
                    e.printStackTrace();
                    onGetInfoListener.onFail();//上传失败
                }

            }
        });
    }

    /**
     * 保存勘察
     */
    public void saveSurveyToLocal(SurveyBean surveyBean) {
        if (surveyBean != null) {
            String number = surveyBean.getNumber();
            if (number.equals("0"))
                surveyBean.save();//保存surveyBean
            else {
                List<SurveyBean> surveyBeanList = LitePal.where("number = ?", number).find(SurveyBean.class);
                if (surveyBeanList != null && surveyBeanList.size() > 0)
                    surveyBean.updateAll("number = ?", number);//更新一下
                else
                    surveyBean.save();//保存一下
            }
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
        }, 3);

    }

}
