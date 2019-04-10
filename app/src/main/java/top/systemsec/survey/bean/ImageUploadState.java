package top.systemsec.survey.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class ImageUploadState implements Serializable {

    private int imageArrId;//这个图片属于哪个组

    private String imagePath;//图片路径

    private String imageUrl;//图片后台Url

    private boolean isUploadOk = false;//是否上传成功

    private SurveyBean mSurveyBean;//勘察bean

    public ImageUploadState(int imageArrId, String imagePath) {
        this.imageArrId = imageArrId;
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isUploadOk() {
        return isUploadOk;
    }

    public void setUploadOk(boolean uploadOk) {
        isUploadOk = uploadOk;
    }

    public int getImageArrId() {
        return imageArrId;
    }

    public void setImageArrId(int imageArrId) {
        this.imageArrId = imageArrId;
    }

    public void setSurveyBean(SurveyBean surveyBean) {
        mSurveyBean = surveyBean;
    }


    @Override
    public String toString() {
        return "ImageUploadState{" +
                "imageArrId=" + imageArrId +
                ", imagePath='" + imagePath + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isUploadOk=" + isUploadOk +
                ", mSurveyBean=" + mSurveyBean +
                '}';
    }
}
