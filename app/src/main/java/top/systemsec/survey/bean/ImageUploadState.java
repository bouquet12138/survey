package top.systemsec.survey.bean;

public class ImageUploadState {

    private String imagePath;//图片路径

    private String imageUrl;//图片后台Url

    private boolean isUploadOk;//是否上传成功

    public ImageUploadState(String imagePath, String imageUrl, boolean isUploadOk) {
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.isUploadOk = isUploadOk;
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
}
