package top.systemsec.survey.bean;

import java.util.List;

public class ImageBean {

    private int mMaxNum;//能选择的最大图片数
    private List<String> mImagePaths;//图片路径

    public ImageBean(int maxNum, List<String> imagePaths) {
        mMaxNum = maxNum;
        mImagePaths = imagePaths;
    }

    public int getMaxNum() {
        return mMaxNum;
    }

    public void setMaxNum(int maxNum) {
        mMaxNum = maxNum;
    }

    public List<String> getImagePaths() {
        return mImagePaths;
    }

    public void setImagePaths(List<String> imagePaths) {
        mImagePaths = imagePaths;
    }
}
