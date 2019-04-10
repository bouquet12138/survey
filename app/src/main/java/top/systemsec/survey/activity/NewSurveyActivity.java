package top.systemsec.survey.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.presenter.NewSurveyPresenter;
import top.systemsec.survey.R;
import top.systemsec.survey.utils.LocalImageSave;
import top.systemsec.survey.utils.MatisseUtil;
import top.systemsec.survey.view.INewSurveyView;
import top.systemsec.survey.view.NewSurveyView;

import static top.systemsec.survey.utils.LocalImageSave.IMAGE_DAMAGE;
import static top.systemsec.survey.utils.LocalImageSave.SAVE_FAIL;
import static top.systemsec.survey.utils.LocalImageSave.SAVE_OK;
import static top.systemsec.survey.utils.LocalImageSave.STORAGE_CARD_DISABLED;


public class NewSurveyActivity extends MVPBaseActivity implements INewSurveyView {

    private NewSurveyPresenter mNewSurveyPresenter;//主持
    private static final String TAG = "NewSurveyActivity";

    private final int REQUEST_CODE_CHOOSE = 0;//选择图片
    private final int VIEW_PIC = 1;//查看图片

    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;//请求读取本地图片
    private final int MY_PERMISSIONS_LOCATION_CODE = 1;//定位权限

    private NewSurveyView mNewSurveyView;

    private List<String> mImagePaths = new ArrayList<>();
    private List<String> mImagePaths1 = new ArrayList<>();
    private List<String> mImagePaths2 = new ArrayList<>();
    private List<String> mImagePaths3 = new ArrayList<>();
    private List<String> mImagePaths4 = new ArrayList<>();

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

        });
        mTempStorageBt.setOnClickListener((v) -> {
            SurveyBean surveyBean = mNewSurveyView.getSurveyInfo();//得到勘察信息
            mNewSurveyPresenter.saveSurvey(surveyBean);//保存一下
        });

        /**
         * 添加图片监听
         */
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

        mNewSurveyView.initWatchImageListener((int index, String imageName, List<String> imageList, int imgIndex) -> {
            mNowWatchImgIndex = index;//当前查看的图片索引

            Intent intent = new Intent(NewSurveyActivity.this, PictureViewActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("imageName", imageName);
            bundle.putSerializable("imageList", (Serializable) imageList);
            bundle.putInt("imgIndex", imgIndex);
            intent.putExtras(bundle);

            startActivityForResult(intent, VIEW_PIC);//启动活动
        });

    }

    /**
     * 请求权限的响应
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // 从数组中取出返回结果
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "用户拒绝权限无法打开相册", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    selectImage();//从本地选择图片
                }
            }
            break;
            case MY_PERMISSIONS_LOCATION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 权限被用户同意。执形我们想要的操作
                    requestLocation();//请求定位
                } else {
                    Toast.makeText(this, "用户拒绝权限不能获取定位", Toast.LENGTH_SHORT).show();
                }
            }
            break;

        }
    }


    /**
     * 从相册选择图片 权限
     */
    private void selectImageAuthor() {
        List<String> permissionList = new ArrayList<>();//权限组
        if (ContextCompat.checkSelfPermission(NewSurveyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);//读取权限
        if (ContextCompat.checkSelfPermission(NewSurveyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//写权限
        if (ContextCompat.checkSelfPermission(NewSurveyActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.CAMERA);//相机权限

        if (permissionList.size() != 0) {
            // 如果用户已经拒绝了当前权限,shouldShowRequestPermissionRationale返回true，此时我们需要进行必要的解释和处理
            for (String str : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(NewSurveyActivity.this, str)) {
                    Toast.makeText(NewSurveyActivity.this, "用户拒绝了权限，不能读取本地图片，" +
                            "请到设置界面打开", Toast.LENGTH_SHORT).show();
                    return;//返回出去
                }
            }
            ActivityCompat.requestPermissions(NewSurveyActivity.this,
                    permissionList.toArray(new String[permissionList.size()]), MY_PERMISSIONS_REQUEST_READ_CONTACTS);//读取相册

        } else {
            selectImage();//从本地选择图片
        }
    }

    /**
     * 获取定位
     */
    private void obtainLocAuthor() {
        //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(NewSurveyActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(NewSurveyActivity.this, "请前往设置界面打开定位权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_LOCATION_CODE);//获取定位权限
            }

        } else {
            requestLocation();//请求定位
        }
    }

    /**
     * 从相册选择图片
     */
    public void selectImage() {
        MatisseUtil.selectImage(NewSurveyActivity.this, mMaxImgNum, getResources(), REQUEST_CODE_CHOOSE);
    }

    /**
     * 开始
     */
    public void requestLocation() {
        showLoading("定位获取中..");
        mLocationClient.restart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<String> sourcePathList = Matisse.obtainPathResult(data);
            Log.d(TAG, "onActivityResult: pathList " + sourcePathList);//源路径

            List<String> pathList = new ArrayList<>();

            for (String sourcePath : sourcePathList) {

                String pointName = mNewSurveyView.getPointName();//站点名

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");//年月日时分秒
                String fileName = pointName + "_" + NowUserInfo.getUserBean().getName() + "_" + simpleDateFormat.format(new Date());


                int state = LocalImageSave.moveImageToAlbum(sourcePath, pointName, fileName);

                switch (state) {
                    case IMAGE_DAMAGE:
                        Toast.makeText(this, "图片破损", Toast.LENGTH_SHORT).show();
                        break;
                    case STORAGE_CARD_DISABLED:
                        Toast.makeText(this, "手机内存不足", Toast.LENGTH_SHORT).show();
                        break;
                    case SAVE_OK:
                        pathList.add(LocalImageSave.sImagePath);//将路径添加进来
                        break;
                    case SAVE_FAIL:
                        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                        break;
                }
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

        //查看图片
        if (requestCode == VIEW_PIC && resultCode == RESULT_OK) {

            List<String> imageList = (List<String>) data.getSerializableExtra("imageList");//图片列表
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

    @Override
    protected void onDestroy() {
        mNewSurveyPresenter.detachView();//解除绑定
        mLocationClient.stop();//停止
        super.onDestroy();
    }
}
