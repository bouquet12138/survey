package top.systemsec.survey.activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhihu.matisse.Matisse;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.presenter.TempStorageShowPresenter;
import top.systemsec.survey.utils.LocalImageSave;
import top.systemsec.survey.utils.MatisseUtil;
import top.systemsec.survey.view.ITempStorageShowView;
import top.systemsec.survey.view.NewSurveyView;

import static top.systemsec.survey.utils.LocalImageSave.IMAGE_DAMAGE;
import static top.systemsec.survey.utils.LocalImageSave.SAVE_FAIL;
import static top.systemsec.survey.utils.LocalImageSave.SAVE_OK;
import static top.systemsec.survey.utils.LocalImageSave.STORAGE_CARD_DISABLED;

public class TempStorageShowActivity extends MVPBaseActivity implements View.OnClickListener, ITempStorageShowView {

    private static final String TAG = "TempStorageShowActivity";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage_show);
        initView();
        initData();
        initAdapter();
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
        SurveyBean surveyBean = gson.fromJson(surveyBeanJson, SurveyBean.class);//勘察Bean

        if (surveyBean == null) {
            Toast.makeText(this, "数据读取失败", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        mNewSurveyView.initData(surveyBean);//初始化数据

        List<ImageUploadState> imageList = surveyBean.getImgList();//得到存在本地的图片列表

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
        mNewSurveyView.initClickListener(this);
        mSubmitBt.setOnClickListener(this::onClick);
        mCancelBt.setOnClickListener(this::onClick);

        /**
         * 添加图片监听
         */
        mNewSurveyView.initAddImageListener((int index, int maxNum) -> {
            mNowAddImgIndex = index;
            mMaxImgNum = maxNum;
            if (mMaxImgNum >= 0)
                selectImage();//从相册选择图片
        });

        mNewSurveyView.initWatchImageListener((int index, String imageName, List<ImageUploadState> imageList, int imgIndex) -> {
            mNowWatchImgIndex = index;//当前查看的图片索引

            Intent intent = new Intent(TempStorageShowActivity.this, PictureViewActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString("imageName", imageName);
            bundle.putSerializable("imageList", (Serializable) imageList);
            bundle.putInt("imgIndex", imgIndex);
            intent.putExtras(bundle);

            startActivityForResult(intent, VIEW_PIC);//启动活动
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

            List<ImageUploadState> pathList = new ArrayList<>();

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
                        pathList.add(new ImageUploadState(mNowAddImgIndex, LocalImageSave.sImagePath));//将路径添加进来
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
    protected void onDestroy() {
        mTempStorageShowPresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
