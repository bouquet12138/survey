package top.systemsec.survey.bean;

import android.text.TextUtils;


import java.io.Serializable;

public class ImageUploadState implements Serializable {

    private int imageArrId;//这个图片属于哪个组

    private String imageName;//图片名称

    private String imagePath;//图片本地选择路径

    private String imageUrl;//图片后台Url


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

    /**
     * 是否上传成功
     *
     * @return
     */
    public boolean isUploadOk() {
        return !(TextUtils.isEmpty(imageUrl));
    }


    public int getImageArrId() {
        return imageArrId;
    }

    public void setImageArrId(int imageArrId) {
        this.imageArrId = imageArrId;
    }


    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "ImageUploadState{" +
                "imageArrId=" + imageArrId +
                ", imagePath='" + imagePath + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }


}
