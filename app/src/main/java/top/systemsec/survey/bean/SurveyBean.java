package top.systemsec.survey.bean;

import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SurveyBean extends LitePalSupport {

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
    private List<String> envImgList;
    private List<String> overallViewList;
    private List<String> closeShotList;
    private String gpsImgList;
    private String sceneImgList;
    /**
     * 备注信息
     */
    private String remark;//备注

    /**
     * 保存时间
     */
    private String saveTime;//保存时间

    public SurveyBean() {
    }

    public SurveyBean(String pointName, String detailAddress, String longitude, String latitude, String street, String police,
                      String cameraInstallType, float poleHigh, int crossArmNum, String dir1, String dir2, int faceRecNum, int faceLightNum, int carNumRecNum, int globalNum,
                      List<String> envImgList, List<String> overallViewList, List<String> closeShotList, String gpsImgList, String sceneImgList,
                      String remark) {
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
        this.envImgList = envImgList;
        this.overallViewList = overallViewList;
        this.closeShotList = closeShotList;
        this.gpsImgList = gpsImgList;
        this.sceneImgList = sceneImgList;
        this.remark = remark;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        saveTime = simpleDateFormat.format(new Date());//格式化一个日期
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPolice() {
        return police;
    }

    public void setPolice(String police) {
        this.police = police;
    }

    public String getCameraInstallType() {
        return cameraInstallType;
    }

    public void setCameraInstallType(String cameraInstallType) {
        this.cameraInstallType = cameraInstallType;
    }

    public float getPoleHigh() {
        return poleHigh;
    }

    public void setPoleHigh(float poleHigh) {
        this.poleHigh = poleHigh;
    }

    public int getCrossArmNum() {
        return crossArmNum;
    }

    public void setCrossArmNum(int crossArmNum) {
        this.crossArmNum = crossArmNum;
    }

    public String getDir1() {
        return dir1;
    }

    public void setDir1(String dir1) {
        this.dir1 = dir1;
    }

    public String getDir2() {
        return dir2;
    }

    public void setDir2(String dir2) {
        this.dir2 = dir2;
    }

    public int getFaceRecNum() {
        return faceRecNum;
    }

    public void setFaceRecNum(int faceRecNum) {
        this.faceRecNum = faceRecNum;
    }

    public int getFaceLightNum() {
        return faceLightNum;
    }

    public void setFaceLightNum(int faceLightNum) {
        this.faceLightNum = faceLightNum;
    }

    public int getCarNumRecNum() {
        return carNumRecNum;
    }

    public void setCarNumRecNum(int carNumRecNum) {
        this.carNumRecNum = carNumRecNum;
    }

    public int getGlobalNum() {
        return globalNum;
    }

    public void setGlobalNum(int globalNum) {
        this.globalNum = globalNum;
    }

    public List<String> getEnvImgList() {
        return envImgList;
    }

    public void setEnvImgList(List<String> envImgList) {
        this.envImgList = envImgList;
    }

    public List<String> getOverallViewList() {
        return overallViewList;
    }

    public void setOverallViewList(List<String> overallViewList) {
        this.overallViewList = overallViewList;
    }

    public List<String> getCloseShotList() {
        return closeShotList;
    }

    public void setCloseShotList(List<String> closeShotList) {
        this.closeShotList = closeShotList;
    }

    public String getGpsImgList() {
        return gpsImgList;
    }

    public void setGpsImgList(String gpsImgList) {
        this.gpsImgList = gpsImgList;
    }

    public String getSceneImgList() {
        return sceneImgList;
    }

    public void setSceneImgList(String sceneImgList) {
        this.sceneImgList = sceneImgList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    @Override
    public String toString() {
        return "SurveyBean{" +
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
                ", envImgList=" + envImgList +
                ", overallViewList=" + overallViewList +
                ", closeShotList=" + closeShotList +
                ", gpsImgList='" + gpsImgList + '\'' +
                ", sceneImgList='" + sceneImgList + '\'' +
                ", remark='" + remark + '\'' +
                "}\n";
    }
}
