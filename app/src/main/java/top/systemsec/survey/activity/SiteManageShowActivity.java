package top.systemsec.survey.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.ImageFixAdapter;
import top.systemsec.survey.base.BaseActivity;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.ImageList;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;

public class SiteManageShowActivity extends BaseActivity {

    private static final String TAG = "SiteManageShowActivity";
    private ImageView mBackImage;//返回按钮

    private TextView mNumberText;
    private TextView mPointNameEdit;
    private TextView mDetailAddressEdit;
    private TextView mLongitude;
    private TextView mLatitudeEdit;
    private TextView mStreetSpinner;
    private TextView mPoliceSpinner;

    private RadioButton mPoleRadio;
    private RadioButton mWallRadio;
    private TextView mPoleHighEdit;
    private TextView mCrossArmNumEdit;
    private TextView mDirEdit1;
    private TextView mDirEdit2;
    private TextView mFaceRecNumEdit;
    private TextView mFaceLightNumEdit;
    private TextView mCarNumRecNumEdit;
    private TextView mGlobalNumEdit;

    private RecyclerView mEnvImgRecyclerView;
    private RecyclerView mOverallViewRecyclerView;
    private RecyclerView mCloseShotRecyclerView;
    private RecyclerView mGpsImgRecyclerView;
    private RecyclerView mSceneImgRecyclerView;

    private TextView mRemarkEdit;
    private ImageFixAdapter mImageFixAdapter;
    private ImageFixAdapter mImageFixAdapter1;
    private ImageFixAdapter mImageFixAdapter2;
    private ImageFixAdapter mImageFixAdapter3;
    private ImageFixAdapter mImageFixAdapter4;
    private String mImgHead;
    private ImageList mImageList;
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_manage_show);
        initView();
        initData();
    }

    /**
     * 初始化View
     */
    private void initView() {

        mBackImage = findViewById(R.id.backImage);//返回按钮
        mBackImage.setOnClickListener((v) -> {
            finish();//销毁活动
        });

        mNumberText = findViewById(R.id.numberText);
        mPointNameEdit = findViewById(R.id.pointNameEdit);
        mDetailAddressEdit = findViewById(R.id.detailAddressEdit);
        mLongitude = findViewById(R.id.longitude);
        mLatitudeEdit = findViewById(R.id.latitudeEdit);
        mStreetSpinner = findViewById(R.id.streetSpinner);
        mPoliceSpinner = findViewById(R.id.policeSpinner);

        mPoleRadio = findViewById(R.id.poleRadio);
        mWallRadio = findViewById(R.id.wallRadio);
        mPoleHighEdit = findViewById(R.id.poleHighEdit);
        mCrossArmNumEdit = findViewById(R.id.crossArmNumEdit);
        mDirEdit1 = findViewById(R.id.dirEdit1);
        mDirEdit2 = findViewById(R.id.dirEdit2);
        mFaceRecNumEdit = findViewById(R.id.faceRecNumEdit);
        mFaceLightNumEdit = findViewById(R.id.faceLightNumEdit);
        mCarNumRecNumEdit = findViewById(R.id.carNumRecNumEdit);
        mGlobalNumEdit = findViewById(R.id.globalNumEdit);

        mEnvImgRecyclerView = findViewById(R.id.envImgRecyclerView);
        mOverallViewRecyclerView = findViewById(R.id.overallViewRecyclerView);
        mCloseShotRecyclerView = findViewById(R.id.closeShotRecyclerView);
        mGpsImgRecyclerView = findViewById(R.id.gpsImgRecyclerView);
        mSceneImgRecyclerView = findViewById(R.id.sceneImgRecyclerView);

        mRemarkEdit = findViewById(R.id.remarkEdit);//备注
    }

    /**
     * 初始化数据
     */
    private void initData() {

        String surveyBeanJson = getIntent().getStringExtra("surveyBeanJson");

        if (TextUtils.isEmpty(surveyBeanJson)) {
            Toast.makeText(this, "数据读取失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Gson gson = new Gson();
        SurveyBean surveyBean = gson.fromJson(surveyBeanJson, SurveyBean.class);//勘察Bean

        if (surveyBean == null) {
            Toast.makeText(this, "数据读取失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setData(surveyBean);

    }

    /**
     * 设置数据
     *
     * @param surveyBean
     */
    private void setData(SurveyBean surveyBean) {
        //id_站点名_勘察人_日期

        mImgHead = surveyBean.getNumber();
        mImgHead += "_";
        mImgHead += surveyBean.getPointName();//站点名
        mImgHead += "_";
        mImgHead += NowUserInfo.getUserBean().getName();//勘察人
        mImgHead += "_";
        String date = surveyBean.getSubmitTime();//提交时间
        try {
            date = date.substring(0, date.indexOf(" ")).trim();//去掉一下空格
        } catch (IndexOutOfBoundsException e) {
        }

        String arr[] = date.split("-");
        if (arr != null) {
            for (int i = 0; i < arr.length; i++)
                mImgHead += arr[i];
        }

        Log.d(TAG, "setData: " + mImgHead + "图片头");

        mNumberText.setText(surveyBean.getNumber());//编号
        mPointNameEdit.setText(surveyBean.getPointName());//站点名称

        mDetailAddressEdit.setText(surveyBean.getDetailAddress());//详细地址
        mLongitude.setText(surveyBean.getLongitude());//经度
        mLatitudeEdit.setText(surveyBean.getLatitude());//纬度
        mStreetSpinner.setText(surveyBean.getStreet());//街道
        mPoliceSpinner.setText(surveyBean.getPolice());//警局

        //摄像头信息
        int installType = surveyBean.getCameraInstallType();

        if (installType == 2)
            mWallRadio.setChecked(true);//选中壁挂安装

        mPoleHighEdit.setText(surveyBean.getPoleHigh() + "");
        mCrossArmNumEdit.setText(surveyBean.getCrossArmNum() + "");//横臂数
        mDirEdit1.setText(surveyBean.getDir1());
        mDirEdit2.setText(surveyBean.getDir2());
        mFaceRecNumEdit.setText(surveyBean.getFaceRecNum() + "");//人脸识别数
        mFaceLightNumEdit.setText(surveyBean.getFaceLightNum() + "");//人脸补光灯数

        mCarNumRecNumEdit.setText(surveyBean.getCarNumRecNum() + "");//车牌识别监控头
        mGlobalNumEdit.setText(surveyBean.getGlobalNum() + "");//环球监控数

        mRemarkEdit.setText(surveyBean.getRemark());//备注

        //图片列表
        mImageList = new ImageList(surveyBean.getImgList());

        mLayoutListener = () -> {
            initAdapter(mEnvImgRecyclerView.getWidth());//初始化适配器
            mEnvImgRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutListener);//移除布局监听
        };

        mEnvImgRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(mLayoutListener);//设置布局监听

    }

    /**
     * 初始化监听
     */
    private void initListener() {

        ImageFixAdapter.OnImageClickListener onImageClickListener =
                (String imageTitle, List<ImageUploadState> imgPath, int imgIndex) -> {

                    Intent intent = new Intent(SiteManageShowActivity.this, PictureViewActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("imageName", imageTitle);
                    bundle.putSerializable("imageList", (Serializable) imgPath);
                    bundle.putInt("imgIndex", imgIndex);
                    bundle.putString("imgHead", mImgHead);
                    bundle.putBoolean("showDelete", false);//不展示删除
                    intent.putExtras(bundle);

                    startActivity(intent);//启动活动

                };


        mImageFixAdapter.setOnImageClickListener(onImageClickListener);//设置监听
        mImageFixAdapter1.setOnImageClickListener(onImageClickListener);//设置监听
        mImageFixAdapter2.setOnImageClickListener(onImageClickListener);//设置监听
        mImageFixAdapter3.setOnImageClickListener(onImageClickListener);//设置监听
        mImageFixAdapter4.setOnImageClickListener(onImageClickListener);//设置监听

    }

    /**
     * 初始化适配器
     */
    private void initAdapter(int width) {

        mImageFixAdapter = new ImageFixAdapter(mImageList.getImagePaths(), mImgHead, this, "环境照", width);
        mEnvImgRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mEnvImgRecyclerView.setAdapter(mImageFixAdapter);//设置适配器

        mImageFixAdapter1 = new ImageFixAdapter(mImageList.getImagePaths1(), mImgHead, this, "全景照", width);
        mOverallViewRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mOverallViewRecyclerView.setAdapter(mImageFixAdapter1);//设置适配器

        mImageFixAdapter2 = new ImageFixAdapter(mImageList.getImagePaths2(), mImgHead, this, "近景照", width);
        mCloseShotRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mCloseShotRecyclerView.setAdapter(mImageFixAdapter2);//设置适配器

        mImageFixAdapter3 = new ImageFixAdapter(mImageList.getImagePaths3(), mImgHead, this, "gps照", width);
        mGpsImgRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mGpsImgRecyclerView.setAdapter(mImageFixAdapter3);//设置适配器

        mImageFixAdapter4 = new ImageFixAdapter(mImageList.getImagePaths4(), mImgHead, this, "现场画面照", width);
        mSceneImgRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mSceneImgRecyclerView.setAdapter(mImageFixAdapter4);//设置适配器

        initListener();//初始化监听
    }

    /**
     * 启动活动
     *
     * @param surveyBeanJson json 字符串
     * @param context        上下文
     */
    public static void actionStart(String surveyBeanJson, Context context) {
        Intent intent = new Intent(context, SiteManageShowActivity.class);
        intent.putExtra("surveyBeanJson", surveyBeanJson);
        context.startActivity(intent);//开启Activity
    }

}
