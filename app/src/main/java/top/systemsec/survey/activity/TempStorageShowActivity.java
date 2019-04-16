package top.systemsec.survey.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.presenter.TempStorageShowPresenter;
import top.systemsec.survey.utils.DensityUtil;
import top.systemsec.survey.utils.MatisseUtil;
import top.systemsec.survey.view.ITempStorageShowView;
import top.systemsec.survey.view.NewSurveyView;


public class TempStorageShowActivity extends MVPBaseActivity implements View.OnClickListener, ITempStorageShowView {

    private static final String TAG = "TempStorageShowActivity";

    private boolean mCanSelect;//是否可以选择图片
    private boolean mCanViewPic;//是否可以左右查看图片

    private TempStorageShowPresenter mTempStorageShowPresenter;

    private final int REQUEST_CODE_CHOOSE = 0;//选择图片
    private final int VIEW_PIC = 1;//查看图片

    private int mNowAddImgIndex, mMaxImgNum;//当前图片索引 最大图片数
    private int mNowWatchImgIndex;//当前查看图片的索引

    private NewSurveyView mNewSurveyView;

    private List<ImageUploadState> mImagePaths = new ArrayList<>();
    private List<ImageUploadState> mImagePaths1 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths2 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths3 = new ArrayList<>();
    private List<ImageUploadState> mImagePaths4 = new ArrayList<>();

    private Button mSubmitBt, mCancelBt;//确定按钮 取消按钮
    private SurveyBean mSurveyBean;//勘察Bean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage_show);

        initView();
        initData();
        initListener();

        mTempStorageShowPresenter = new TempStorageShowPresenter();
        mTempStorageShowPresenter.attachView(this);//绑定View
        mTempStorageShowPresenter.getStreetAndPoliceInfo();//从后台获取一下街道信息
    }

    /**
     * 初始化view
     */
    private void initView() {
        mNewSurveyView = findViewById(R.id.newSurveyView);//新勘察
        mNewSurveyView.initView();//初始化view
        mNewSurveyView.hideLocation();//隐藏定位

        mSubmitBt = findViewById(R.id.submitBt);//提交按钮
        mCancelBt = findViewById(R.id.cancelBt);//取消按钮
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
     * 初始化适配器
     */
    private void initAdapter(int width) {
        mNewSurveyView.initImageAdapter(mImagePaths, width);
        mNewSurveyView.initImageAdapter1(mImagePaths1, width);
        mNewSurveyView.initImageAdapter2(mImagePaths2, width);
        mNewSurveyView.initImageAdapter3(mImagePaths3, width);
        mNewSurveyView.initImageAdapter4(mImagePaths4, width);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mNewSurveyView.initClickListener(this);
        mSubmitBt.setOnClickListener(this::onClick);
        mCancelBt.setOnClickListener(this::onClick);

        mNewSurveyView.initLayout();//初始化布局

        mNewSurveyView.setOnGetWidthListener((w) -> {

            initAdapter(w);

            //添加图片监听
            mNewSurveyView.initAddImageListener((int index, int maxNum) -> {

                if (!mCanSelect)
                    return;
                mCanSelect = false;//不可以选择

                mNowAddImgIndex = index;
                mMaxImgNum = maxNum;
                if (mMaxImgNum >= 0)
                    selectImage();//从相册选择图片
            });

            //初始化查看图片的
            mNewSurveyView.initWatchImageListener((int index, String imageName, List<ImageUploadState> imageList, int imgIndex) -> {

                if (!mCanViewPic)//如果不可以查看图片
                    return;
                mCanViewPic = false;//不可以查看

                mNowWatchImgIndex = index;//当前查看的图片索引

                Intent intent = new Intent(TempStorageShowActivity.this, PictureViewActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("imageName", imageName);
                bundle.putSerializable("imageList", (Serializable) imageList);
                bundle.putInt("imgIndex", imgIndex);
                intent.putExtras(bundle);

                startActivityForResult(intent, VIEW_PIC);//启动活动
            });

        });

    }

    /**
     * 从相册选择图片
     */
    public void selectImage() {
        MatisseUtil.selectImage(TempStorageShowActivity.this, mMaxImgNum, getResources(), REQUEST_CODE_CHOOSE);
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
            case R.id.cancelBt:
                finish();//返回销毁
                break;
        }
    }

    /**
     * Activity 结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<String> sourcePathList = Matisse.obtainPathResult(data);
            addImage(sourcePathList);
        }

        //查看图片
        if (requestCode == VIEW_PIC && resultCode == RESULT_OK) {

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
    }

    /**
     * 添加图片
     *
     * @param sourcePathList
     */
    private void addImage(List<String> sourcePathList) {

        int num = 1;//编号
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");//年月日时分秒
        String fileName = simpleDateFormat.format(new Date());//文件名

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
     * 初始化街道
     *
     * @param streets
     */
    @Override
    public void initStreet(String[] streets) {
        mNewSurveyView.initStreets(streets);
    }

    /**
     * 初始化警局
     *
     * @param polices
     */
    @Override
    public void initPolice(String[] polices) {
        mNewSurveyView.initPolices(polices);
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

    @Override
    protected void onDestroy() {
        mTempStorageShowPresenter.detachView();//解除绑定
        super.onDestroy();
    }

    /**
     * 在栈顶
     */
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        mCanSelect = true;//可以选择图片了
        mCanViewPic = true;//可以查看图片
        super.onResume();
    }

}
