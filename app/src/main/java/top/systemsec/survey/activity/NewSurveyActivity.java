package top.systemsec.survey.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.presenter.NewSurveyPresenter;
import top.systemsec.survey.R;
import top.systemsec.survey.utils.MatisseUtil;
import top.systemsec.survey.view.INewSurveyView;
import top.systemsec.survey.view.NewSurveyView;


public class NewSurveyActivity extends NewSurveyPermissionActivity implements INewSurveyView {

    private NewSurveyPresenter mNewSurveyPresenter;//主持
    private static final String TAG = "NewSurveyActivity";

    private final int SELECT_PICTURE = 0;//选择图片
    private final int VIEW_PICTURE = 1;//查看图片

    private NewSurveyView mNewSurveyView;

    private List<ImageUploadState> mImagePaths = new ArrayList<>();
    private List<ImageUploadState> mImagePaths1 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths2 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths3 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths4 = new ArrayList<>();

    private int mNowAddImgIndex, mMaxImgNum;//当前图片索引 最大图片数
    private int mNowWatchImgIndex;//当前查看图片的索引

    private LocationClient mLocationClient;

    private Button mTempStorageBt;//暂存按钮
    private Button mSubmitBt;//提交按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        initView();
        initAdapter();
        initListener();
        mNewSurveyPresenter = new NewSurveyPresenter();//支持
        mNewSurveyPresenter.attachView(this);//绑定一下

        mNewSurveyPresenter.getStreetAndPoliceInfo();//获取警局和街道信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mNewSurveyView = findViewById(R.id.newSurveyView);//新勘察
        mNewSurveyView.initView();//初始化view

        mTempStorageBt = findViewById(R.id.tempStorageBt);//暂存按钮
        mSubmitBt = findViewById(R.id.submitBt);//提交按钮
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mNewSurveyView.initImageAdapter(mImagePaths);
        mNewSurveyView.initImageAdapter1(mImagePaths1);
        mNewSurveyView.initImageAdapter2(mImagePaths2);
        mNewSurveyView.initImageAdapter3(mImagePaths3);
        mNewSurveyView.initImageAdapter4(mImagePaths4);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mNewSurveyView.initClickListener((v) -> {
                    switch (v.getId()) {
                        case R.id.backImage:
                            finish();//返回销毁
                            break;
                        case R.id.locText://获取位置
                            obtainLocAuthor();//获取位置
                            break;
                    }
                }
        );

        //提交
        mSubmitBt.setOnClickListener((v) -> {
            SurveyBean surveyBean = mNewSurveyView.getSurveyInfo();//得到勘察信息
            mNewSurveyPresenter.upLoadImage(surveyBean);
        });
        //暂存
        mTempStorageBt.setOnClickListener((v) -> {
            SurveyBean surveyBean = mNewSurveyView.getSurveyInfo();//得到勘察信息
            mNewSurveyPresenter.tempSave(surveyBean);//暂存一下
        });

        //添加图片监听
        mNewSurveyView.initAddImageListener((int index, int maxNum) -> {

            String pointName = mNewSurveyView.getPointName();
            if (TextUtils.isEmpty(pointName)) {
                mNewSurveyView.pointNameFocus();//填写点位名称
                return;
            }

            mNowAddImgIndex = index;
            mMaxImgNum = maxNum;
            if (mMaxImgNum >= 0)
                selectImageAuthor();//从相册选择图片
        });

        mNewSurveyView.initWatchImageListener((int index, String imageName, List<ImageUploadState> imageList, int imgIndex) -> {
            mNowWatchImgIndex = index;//当前查看的图片索引

            Intent intent = new Intent(NewSurveyActivity.this, PictureViewActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("imageName", imageName);
            bundle.putSerializable("imageList", (Serializable) imageList);
            bundle.putInt("imgIndex", imgIndex);
            intent.putExtras(bundle);

            startActivityForResult(intent, VIEW_PICTURE);//启动活动
        });

    }

    /**
     * 从相册选择图片
     */
    @Override
    public void selectImage() {
        MatisseUtil.selectImage(NewSurveyActivity.this, mMaxImgNum, getResources(), SELECT_PICTURE);
    }

    /**
     * 开始
     */
    @Override
    public void requestLocation() {
        showLoading("定位获取中..");
        mLocationClient.restart();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            List<String> sourcePathList = Matisse.obtainPathResult(data);
            addImage(sourcePathList);
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
            }

        }
        //endregion

    }

    /**
     * 初始化街道
     *
     * @param streets
     */
    @Override
    public void initStreet(String[] streets) {
        mNewSurveyView.initStreets(streets);//初始化街道
    }

    @Override
    public void initPolice(String[] polices) {
        mNewSurveyView.initPolices(polices);//初始化警局
    }

    /**
     * 站点名称
     *
     * @return
     */
    @Override
    public String getPointName() {
        return mNewSurveyView.getPointName();
    }

    /**
     * 重启当前活动
     */
    @Override
    public void reStart() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);//没有动画Z
        finish();
        overridePendingTransition(0, 0);//没有动画
        startActivity(intent);
    }

    /**
     * 添加图片
     *
     * @param sourcePathList
     */
    private void addImage(List<String> sourcePathList) {

        int num = 1;//编号
        String pointName = mNewSurveyView.getPointName();//站点名
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");//年月日时分秒
        String fileName = pointName + "_" + NowUserInfo.getUserBean().getName() + "_" + simpleDateFormat.format(new Date());

        List<ImageUploadState> pathList = new ArrayList<>();

        for (String imagePath : sourcePathList) {

            File file = new File(imagePath);
            if (!file.exists())//图片破损
                continue;

            ImageUploadState imageUploadState = new ImageUploadState(mNowAddImgIndex, imagePath);
            imageUploadState.setImageName(fileName + "(" + num + ")");//文件名
            pathList.add(imageUploadState);//添加
            num++;//数字加加
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
        }

    }


    /**
     * 定位
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            hideLoading();//隐藏dialog
            double longitude = bdLocation.getLongitude();
            double latitude = bdLocation.getLatitude();
            if (longitude == 4.9E-324 || latitude == 4.9E-324) {
                Toast.makeText(NewSurveyActivity.this, "位置获取失败", Toast.LENGTH_SHORT).show();
            } else {
                mNewSurveyView.setLongitude(bdLocation.getLongitude() + "");//经度
                mNewSurveyView.setLatitude(bdLocation.getLatitude() + "");//纬度
            }

        }
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        mNewSurveyPresenter.detachView();//解除绑定
        mLocationClient.stop();//停止
        super.onDestroy();
    }

}
