package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.utils.MatisseUtil;
import top.systemsec.survey.view.NewSurveyView;

public abstract class SurveyActivity extends SurveyPermissionActivity implements View.OnClickListener {

    private static final String TAG = "SurveyActivity";

    protected boolean mCanViewPic;//是否可以左右查看图片

    protected final int SELECT_PICTURE = 0;//选择图片
    protected final int VIEW_PICTURE = 1;//查看图片
    protected final int TAKE_PHOTO = 2;//拍照
    protected final int OPEN_GPS = 3;//拍照


    protected NewSurveyView mNewSurveyView;

    protected List<ImageUploadState> mImagePaths = new ArrayList<>();
    protected List<ImageUploadState> mImagePaths1 = new ArrayList<>();
    protected List<ImageUploadState> mImagePaths2 = new ArrayList<>();
    protected List<ImageUploadState> mImagePaths3 = new ArrayList<>();
    protected List<ImageUploadState> mImagePaths4 = new ArrayList<>();
    protected List<ImageUploadState> mImagePaths5 = new ArrayList<>();

    protected int mNowAddImgIndex, mMaxImgNum;//当前图片索引 最大图片数
    protected int mNowWatchImgIndex;//当前查看图片的索引

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * 初始化适配器
     */
    protected void initAdapter(int width) {
        mNewSurveyView.initImageAdapter(mImagePaths, width);
        mNewSurveyView.initImageAdapter1(mImagePaths1, width);
        mNewSurveyView.initImageAdapter2(mImagePaths2, width);
        mNewSurveyView.initImageAdapter3(mImagePaths3, width);
        mNewSurveyView.initImageAdapter4(mImagePaths4, width);
        mNewSurveyView.initImageAdapter5(mImagePaths5, width);//初始化适配器
    }

    /**
     * 初始化图片列表
     */
    protected void initImageList() {

        //测量layout
        mNewSurveyView.initLayout();

        //得到宽度的监听
        mNewSurveyView.setOnGetWidthListener((w) -> {
            initAdapter(w);
            /**
             * 添加图片监听
             */
            mNewSurveyView.initAddImageListener((int index, int maxNum) -> {
                /*
                String pointName = mNewSurveyView.getPointName();//站点名称
                if (TextUtils.isEmpty(pointName)) {//站点名是空
                    mNewSurveyView.pointNameFocus();//站点名
                    return;//返回
                }*/
                mNowAddImgIndex = index;
                mMaxImgNum = maxNum;
                if (mMaxImgNum >= 0)//可以选择的最多相片
                    mNewSurveyView.popBottomDialog();//弹出底部弹窗
            });

            mNewSurveyView.initWatchImageListener((int index, String imageName, List<ImageUploadState> imageList, int imgIndex) -> {

                if (!mCanViewPic)//如果不可以查看图片
                    return;
                mCanViewPic = false;//不可以查看

                mNowWatchImgIndex = index;//当前查看的图片索引
                Intent intent = new Intent(SurveyActivity.this, PictureViewActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("imageName", imageName);
                bundle.putSerializable("imageList", (Serializable) imageList);
                bundle.putInt("imgIndex", imgIndex);
                intent.putExtras(bundle);

                startActivityForResult(intent, VIEW_PICTURE);//启动活动
            });
        });

    }


    /**
     * 打开相机
     */
    @Override
    protected void openCamera() {
        Intent intent = new Intent(SurveyActivity.this, CameraActivity.class);//开启活动
        intent.putExtra("pointName", mNewSurveyView.getPointName());//得到站点名
        intent.putExtra("maxImage", mMaxImgNum);
        startActivityForResult(intent, TAKE_PHOTO);//启动活动
    }

    /**
     * 从相册选择图片
     */
    @Override
    public void selectImage() {
        MatisseUtil.selectImage(SurveyActivity.this, mMaxImgNum, getResources(), SELECT_PICTURE);
    }

    @Override
    @CallSuper//必须super
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            List<String> sourcePathList = Matisse.obtainPathResult(data);
            addImage(sourcePathList);
        }

        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            List<String> imageList = (List<String>) data.getSerializableExtra("imageList");//图片列表
            Log.d(TAG, "onActivityResult: " + imageList);
            addImage(imageList);//添加图片列表
        }

        //region查看图片
        if (requestCode == VIEW_PICTURE && resultCode == RESULT_OK) {

            List<ImageUploadState> imageList = (List<ImageUploadState>) data.getSerializableExtra("imageList");//图片列表

            Log.d(TAG, "onActivityResult: imageList " + imageList);

            switch (mNowWatchImgIndex) {
                case 0:
                    mImagePaths.clear();
                    mImagePaths.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter();//唤醒数据更新
                    break;
                case 1:
                    mImagePaths1.clear();
                    mImagePaths1.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter1();//唤醒数据更新
                    break;
                case 2:
                    mImagePaths2.clear();
                    mImagePaths2.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter2();//唤醒数据更新
                    break;
                case 3:
                    mImagePaths3.clear();
                    mImagePaths3.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter3();//唤醒数据更新
                    break;
                case 4:
                    mImagePaths4.clear();
                    mImagePaths4.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter4();//唤醒数据更新
                    break;
                case 5:
                    mImagePaths5.clear();
                    mImagePaths5.addAll(imageList);//全添加进去
                    mNewSurveyView.notifyImgAdapter5();//唤醒数据更新
                    break;
            }

        }
        //endregion
    }

    /**
     * 添加图片
     *
     * @param sourcePathList
     */
    protected void addImage(List<String> sourcePathList) {

        if (sourcePathList == null || sourcePathList.size() == 0)
            return;

        List<ImageUploadState> pathList = new ArrayList<>();

        for (String imagePath : sourcePathList) {
            File file = new File(imagePath);
            if (!file.exists())//图片破损
                continue;
            ImageUploadState imageUploadState = new ImageUploadState(mNowAddImgIndex, imagePath);
            pathList.add(imageUploadState);//添加
        }

        switch (mNowAddImgIndex) {
            case 0:
                mImagePaths.addAll(pathList);
                mNewSurveyView.notifyImgAdapter();
                break;
            case 1:
                mImagePaths1.addAll(pathList);
                mNewSurveyView.notifyImgAdapter1();
                break;
            case 2:
                mImagePaths2.addAll(pathList);
                mNewSurveyView.notifyImgAdapter2();
                break;
            case 3:
                mImagePaths3.addAll(pathList);
                mNewSurveyView.notifyImgAdapter3();
                break;
            case 4:
                mImagePaths4.addAll(pathList);
                mNewSurveyView.notifyImgAdapter4();
                break;
            case 5://唤醒数据更新
                mImagePaths5.addAll(pathList);
                mNewSurveyView.notifyImgAdapter5();
                break;
        }

    }

    /**
     * 在栈顶
     */
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        mCanViewPic = true;//可以查看图片
        super.onResume();
    }

}
