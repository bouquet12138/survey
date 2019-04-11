package top.systemsec.survey.bean;

import java.util.ArrayList;
import java.util.List;

public class ImageList {

    private List<ImageUploadState> mImagePaths = new ArrayList<>();
    private List<ImageUploadState> mImagePaths1 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths2 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths3 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths4 = new ArrayList<>();

    public ImageList(List<ImageUploadState> imageList) {

        if (imageList != null)//把图片分一下
            for (ImageUploadState image : imageList) {
                switch (image.getImageArrId()) {
                    case 0:
                        mImagePaths.add(image);//环境照
                        break;
                    case 1:
                        mImagePaths1.add(image);//全景照
                        break;
                    case 2:
                        mImagePaths2.add(image);//近景照
                        break;
                    case 3:
                        mImagePaths3.add(image);//gps照
                        break;
                    case 4:
                        mImagePaths4.add(image);//现场画面照
                        break;
                }
            }

    }

    /**
     * 得到第一组图片的字符串
     *
     * @return
     */
    public String getImageUrlStr() {
        String str = "[";

        for (int i = 0; i < mImagePaths.size(); i++) {
            str += "\"" + mImagePaths.get(i).getImageUrl() + "\"";
            if (i != mImagePaths.size() - 1)
                str += ",";
        }

        str += "]";
        return str;
    }

    /**
     * 得到第一组图片的字符串
     *
     * @return
     */
    public String getImageUrlStr1() {
        String str = "[";

        for (int i = 0; i < mImagePaths1.size(); i++) {
            str += "\"" + mImagePaths1.get(i).getImageUrl() + "\"";
            if (i != mImagePaths1.size() - 1)
                str += ",";
        }

        str += "]";
        return str;
    }

    /**
     * 得到第一组图片的字符串
     *
     * @return
     */
    public String getImageUrlStr2() {
        String str = "[";

        for (int i = 0; i < mImagePaths2.size(); i++) {
            str += "\"" + mImagePaths2.get(i).getImageUrl() + "\"";
            if (i != mImagePaths2.size() - 1)
                str += ",";
        }

        str += "]";
        return str;
    }

    /**
     * 得到第一组图片的字符串
     *
     * @return
     */
    public String getImageUrlStr3() {
        String str = "[";

        for (int i = 0; i < mImagePaths3.size(); i++) {
            str += "\"" + mImagePaths3.get(i).getImageUrl() + "\"";
            if (i != mImagePaths3.size() - 1)
                str += ",";
        }

        str += "]";
        return str;
    }

    /**
     * 得到第一组图片的字符串
     *
     * @return
     */
    public String getImageUrlStr4() {
        String str = "[";

        for (int i = 0; i < mImagePaths4.size(); i++) {
            str += "\"" + mImagePaths4.get(i).getImageUrl() + "\"";
            if (i != mImagePaths4.size() - 1)
                str += ",";
        }

        str += "]";
        return str;
    }

}
