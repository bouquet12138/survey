package top.systemsec.survey.bean;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.utils.GsonUtil;

public class SurveyBean extends LitePalSupport {

    private static final String TAG = "SurveyBean";

    private String number;//编号
    private boolean isUpLoadOk;//是否上传成功

    private String pointName;
    private String detailAddress;
    private String longitude;
    private String latitude;
    private String street;
    private String police;

    /**
     * 摄像机信息
     */
    private String cameraInstallType;
    private float poleHigh;
    private int crossArmNum;
    private String dir1;
    private String dir2;
    private int faceRecNum;
    private int faceLightNum;
    private int carNumRecNum;
    private int globalNum;

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

    private String submitTime;//提交时间

    public SurveyBean() {
    }

    public SurveyBean(String pointName, String detailAddress, String longitude, String latitude, String street, String police,
                      String cameraInstallType, float poleHigh, int crossArmNum, String dir1, String dir2,
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


    public String getCameraInstallType() {
        return cameraInstallType;
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

    @Override
    public String toString() {
        return "SurveyBean{" +
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
}
