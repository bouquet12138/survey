package top.systemsec.survey.bean;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.utils.GsonUtil;

public class SurveyBean extends LitePalSupport {

    private static final String TAG = "SurveyBean";

    @Column(unique = true, defaultValue = "0")
    private long uniqueId = 0;//本地唯一编号
    private boolean isUpLoadOk;//是否上传成功

    private String number = "0";//后台编号

    /**
     * 点位信息
     */
    private String pointName;//站点名
    private String detailAddress;//详细地址
    private String longitude;//经度
    private String latitude;//纬度
    private String street;//街道
    private String police;//派出所

    /**
     * 摄像机信息
     */
    private int cameraInstallType;//安装方式
    private float poleHigh;//立杆高
    private int crossArmNum;//横臂数
    private String dir1;//方向1
    private String dir2;//方向2
    private int faceRecNum;//人脸别监控数
    private int faceLightNum;//人脸补光灯数
    private int carNumRecNum;//车牌识别数
    private int globalNum;//环球监控头

    /**
     * 图像信息
     */
    private String imgInfo;//图片信息
    /**
     * 备注信息
     */
    private String remark;//备注

    /**
     * 保存时间
     */
    private String saveTime;//保存时间

    /**
     * 提交时间
     */
    private String submitTime;//提交时间

    public SurveyBean() {
    }

    public SurveyBean(String pointName, String detailAddress, String longitude, String latitude, String street, String police,
                      int cameraInstallType, float poleHigh, int crossArmNum, String dir1, String dir2,
                      int faceRecNum, int faceLightNum, int carNumRecNum, int globalNum, List<ImageUploadState> imgList, String remark) {
        this.pointName = pointName;
        this.detailAddress = detailAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.police = police;
        this.cameraInstallType = cameraInstallType;
        this.poleHigh = poleHigh;
        this.crossArmNum = crossArmNum;
        this.dir1 = dir1;
        this.dir2 = dir2;
        this.faceRecNum = faceRecNum;
        this.faceLightNum = faceLightNum;
        this.carNumRecNum = carNumRecNum;
        this.globalNum = globalNum;

        imgInfo = new Gson().toJson(imgList);

        Log.d(TAG, "SurveyBean: " + imgInfo);

        this.remark = remark;
    }

    public String getPointName() {
        return pointName;
    }


    public String getDetailAddress() {
        return detailAddress;
    }


    public String getLongitude() {
        return longitude;
    }


    public String getLatitude() {
        return latitude;
    }


    public String getStreet() {
        return street;
    }


    public String getPolice() {
        return police;
    }


    public int getCameraInstallType() {
        return cameraInstallType;
    }

    /**
     * 设置图片列表
     *
     * @param imgList
     */
    public void setImgList(List<ImageUploadState> imgList) {
        imgInfo = new Gson().toJson(imgList);
    }

    public List<ImageUploadState> getImgList() {

        if (TextUtils.isEmpty(imgInfo))
            return null;
        try {
            List<ImageUploadState> imageUploadStates = new Gson().fromJson(imgInfo, new TypeToken<List<ImageUploadState>>() {
            }.getType());
            return imageUploadStates;
        } catch (JsonSyntaxException e) {
        }
        return null;
    }

    public float getPoleHigh() {
        return poleHigh;
    }


    public int getCrossArmNum() {
        return crossArmNum;
    }


    public String getDir1() {
        return dir1;
    }


    public String getDir2() {
        return dir2;
    }

    public int getFaceRecNum() {
        return faceRecNum;
    }


    public int getFaceLightNum() {
        return faceLightNum;
    }

    public int getCarNumRecNum() {
        return carNumRecNum;
    }

    public int getGlobalNum() {
        return globalNum;
    }


    public String getRemark() {
        return remark;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.saveTime = simpleDateFormat.format(new Date());
    }

    public void setSubmitTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        this.submitTime = simpleDateFormat.format(new Date());
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public String getNumber() {
        return number;
    }

    /**
     * 设置编号
     *
     * @param number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 设置是否上传成功
     *
     * @param upLoadOk
     */
    public void setUpLoadOk(boolean upLoadOk) {
        isUpLoadOk = upLoadOk;
    }

    /**
     * 数据信息
     *
     * @return
     */
    @Override
    public String toString() {
        return "SurveyBean{" +
                "id" + getBaseObjId() + "" +
                "number='" + number + '\'' +
                ", isUpLoadOk=" + isUpLoadOk +
                ", pointName='" + pointName + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", street='" + street + '\'' +
                ", police='" + police + '\'' +
                ", cameraInstallType='" + cameraInstallType + '\'' +
                ", poleHigh=" + poleHigh +
                ", crossArmNum=" + crossArmNum +
                ", dir1='" + dir1 + '\'' +
                ", dir2='" + dir2 + '\'' +
                ", faceRecNum=" + faceRecNum +
                ", faceLightNum=" + faceLightNum +
                ", carNumRecNum=" + carNumRecNum +
                ", globalNum=" + globalNum +
                ", imgList=" + imgInfo +
                ", remark='" + remark + '\'' +
                ", saveTime='" + saveTime + '\'' +
                ", submitTime='" + submitTime + '\'' +
                '}';
    }

    /**
     * 得到唯一id
     */
    public long getUniqueId() {
        return uniqueId;
    }

    /**
     * 设置唯一id
     *
     * @param uniqueId
     */
    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }

}
