package top.systemsec.survey.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;


import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.dialog.ConfirmDialog;
import top.systemsec.survey.presenter.NewSurveyPresenter;
import top.systemsec.survey.R;
import top.systemsec.survey.utils.GPSUtil;

import top.systemsec.survey.view.INewSurveyView;


public class NewSurveyActivity extends SurveyActivity implements INewSurveyView {

    private NewSurveyPresenter mNewSurveyPresenter;//主持

    private LocationClient mLocationClient;

    private Button mTempStorageBt;//暂存按钮
    private Button mSubmitBt;//提交按钮
    private ConfirmDialog mConfirmDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);

        initView();
        initLocation();
        initListener();
        initImageList();//初始化图片列表

        mNewSurveyPresenter = new NewSurveyPresenter();//支持
        mNewSurveyPresenter.attachView(this);//绑定一下

    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//需要地址
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setLocOption(option);//设置选项
        mLocationClient.registerLocationListener(new MyLocationListener());
    }

    /**
     * 初始化view
     */
    private void initView() {
        mNewSurveyView = findViewById(R.id.newSurveyView);//新勘察
        mNewSurveyView.initView();//初始化view
        mTempStorageBt = findViewById(R.id.tempStorageBt);//暂存按钮
        mSubmitBt = findViewById(R.id.submitBt);//提交按钮

        mConfirmDialog = new ConfirmDialog(NewSurveyActivity.this);  //初始化
        mConfirmDialog.setData("请打开GPS", "确定", 0xff5b8cff);//对话框
    }


    /**
     * 初始化监听
     */
    private void initListener() {

        //初始化点击监听
        mNewSurveyView.initClickListener(this);

        mConfirmDialog.setOnConfirmClickListener(() -> {//确定按钮
            mConfirmDialog.dismiss();//消失
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, OPEN_GPS); // 设置完成后返回到原来的界面
        });

        //提交
        mSubmitBt.setOnClickListener(this);

        //暂存
        mTempStorageBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();//返回销毁
                break;
            case R.id.locText://获取位置
                obtainLocAuthor();//获取位置
                break;
            case R.id.tempStorageBt:
                SurveyBean surveyBean1 = mNewSurveyView.getSurveyInfo();//得到勘察信息
                if (surveyBean1 != null)
                    mNewSurveyPresenter.tempSave(surveyBean1);//暂存一下
                break;
            case R.id.submitBt://提交按钮
                SurveyBean surveyBean = mNewSurveyView.getSurveyInfo();//得到勘察信息
                if (surveyBean != null)
                    mNewSurveyPresenter.upLoadImage(surveyBean);
                break;
            case R.id.continuousPhotoBt://连续拍照
                takePhotoAuthor();//打开相机
                mNewSurveyView.hideBottomDialog();//隐藏底部
                break;
            case R.id.ordinaryPhotoBt://普通拍照
                mMaxImgNum = 1;//最大图片数变为1
                takePhotoAuthor();//打开相机
                mNewSurveyView.hideBottomDialog();//隐藏底部
                break;
            case R.id.albumBt://相册
                mNewSurveyView.hideBottomDialog();//隐藏底部
                selectImageAuthor();//从相册选择图片
                break;
            case R.id.cancelBt:
                mNewSurveyView.hideBottomDialog();//隐藏底部弹窗
                break;
        }
    }

    /**
     * 开始
     */
    @Override
    public void requestLocation() {
        if (!GPSUtil.isOPen(NewSurveyActivity.this)) {
            mConfirmDialog.show();
        } else {
            showLoading("定位获取中..");
            mLocationClient.restart();
        }
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

        SurveyBean surveyBean = new SurveyBean();
        mNewSurveyView.initData(surveyBean);//设置一下数据
        mNewSurveyView.clearData();//清除下数据
        mNewSurveyView.scrollTop();//滚动最上面去
        mImagePaths.clear();
        mImagePaths1.clear();
        mImagePaths2.clear();
        mImagePaths3.clear();
        mImagePaths4.clear();
        mImagePaths5.clear();
        mNewSurveyView.notifyImgAdapter();
        mNewSurveyView.notifyImgAdapter1();
        mNewSurveyView.notifyImgAdapter2();
        mNewSurveyView.notifyImgAdapter3();
        mNewSurveyView.notifyImgAdapter4();
        mNewSurveyView.notifyImgAdapter5();
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

                StringBuilder address = new StringBuilder();//字符串缓冲

                String province = bdLocation.getProvince();//省
                if (!TextUtils.isEmpty(province))
                    address.append(province);
                String city = bdLocation.getCity();//城市
                if (!TextUtils.isEmpty(city))
                    address.append(city);
                String district = bdLocation.getDistrict();//区
                if (!TextUtils.isEmpty(district))
                    address.append(district);
                String street = bdLocation.getStreet();//街道
                if (!TextUtils.isEmpty(street))
                    address.append(street);

                mNewSurveyView.setAddress(address.toString());//设置地址
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
