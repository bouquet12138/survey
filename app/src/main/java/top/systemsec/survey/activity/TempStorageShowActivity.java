package top.systemsec.survey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.presenter.TempStorageShowPresenter;
import top.systemsec.survey.view.ITempStorageShowView;


public class TempStorageShowActivity extends SurveyActivity implements View.OnClickListener, ITempStorageShowView {

    private TempStorageShowPresenter mTempStorageShowPresenter;

    private Button mSubmitBt, mCancelBt;//确定按钮 取消按钮
    private SurveyBean mSurveyBean;//勘察Bean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage_show);

        initView();
        initData();
        initListener();
        initImageList();//初始化图片列表

        mTempStorageShowPresenter = new TempStorageShowPresenter();
        mTempStorageShowPresenter.attachView(this);//绑定View
        // mTempStorageShowPresenter.getStreetAndPoliceInfo();//从后台获取一下街道信息
    }

    /**
     * 初始化view
     */
    private void initView() {

        mNewSurveyView = findViewById(R.id.newSurveyView);//新勘察
        mNewSurveyView.initView();//初始化view

        mNewSurveyView.hideLocation();//隐藏定位
        mSubmitBt = findViewById(R.id.submitBt);//提交按钮
        mCancelBt = findViewById(R.id.myCancelBt);//取消按钮
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
        mSurveyBean = gson.fromJson(surveyBeanJson, SurveyBean.class);//勘察Bean

        if (mSurveyBean == null) {
            Toast.makeText(this, "数据读取失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mNewSurveyView.initData(mSurveyBean);//初始化数据

        List<ImageUploadState> imageList = mSurveyBean.getImgList();//得到存在本地的图片列表
        sysImageState(imageList);
    }


    /**
     * 初始化监听
     */
    private void initListener() {
        mNewSurveyView.initClickListener(this);
        mSubmitBt.setOnClickListener(this);
        mCancelBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();//返回销毁
                break;
            case R.id.submitBt://提交按钮
                SurveyBean surveyBean = mNewSurveyView.getSurveyInfo();//得到要提交的surveyBean
                if (surveyBean != null) { //不为空
                    surveyBean.setNumber(mSurveyBean.getNumber());//编号
                    surveyBean.setUniqueId(mSurveyBean.getUniqueId());//设置id
                    mTempStorageShowPresenter.upLoad(surveyBean);//提交数据
                }
                break;
            case R.id.myCancelBt:
                finish();//返回销毁
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


    @Override
    public void uploadOk() {
        finish();//销毁活动就可以了
    }

    @Override
    public void sysImageState(List<ImageUploadState> imageList) {

        mImagePaths.clear();
        mImagePaths1.clear();
        mImagePaths2.clear();
        mImagePaths3.clear();
        mImagePaths4.clear();//清除一下图片状态
        mImagePaths5.clear();//清除一下图片状态

        if (imageList != null)
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
                    case 5:
                        mImagePaths5.add(image);//其他图片
                        break;
                }
            }

    }

    /**
     * 启动活动
     *
     * @param surveyBeanJson json 字符串
     * @param context        上下文
     */
    public static void actionStart(String surveyBeanJson, Context context) {
        Intent intent = new Intent(context, TempStorageShowActivity.class);
        intent.putExtra("surveyBeanJson", surveyBeanJson);
        context.startActivity(intent);//开启Activity
    }

    /**
     * 销毁时
     */
    @Override
    protected void onDestroy() {
        mTempStorageShowPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 这里用不到
     */
    @Override
    protected void requestLocation() {

    }
}
