package top.systemsec.survey.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.ImageSelectAdapter;

public class NewSurveyView extends LinearLayout {

    private ImageView mBackImage;//返回按钮

    /**
     * 点位置信息
     */
    private TextView mLocText;
    private EditText mNumberEdit;
    private EditText mNameEdit;
    private EditText mDetailAddressEdit;
    private EditText mLongitude;
    private EditText mLatitudeEdit;
    private Spinner mStreetSpinner;
    private Spinner mPoliceSpinner;

    /**
     * 摄像头信息
     */
    private RadioGroup mCameraRadioGroup;
    private EditText mPoleHighEdit;
    private EditText mCrossArmNumEdit;
    private EditText mDirEdit1;
    private EditText mDirEdit2;
    private EditText mFaceRecNumEdit;
    private EditText mFaceLightNumEdit;
    private EditText mCarNumRecNumEdit;
    private EditText mGlobalNumEdit;

    /**
     * 摄像头信息
     */
    private RecyclerView mEnvImgRecyclerView;
    private RecyclerView mOverallViewRecyclerView;
    private RecyclerView mCloseShotRecyclerView;
    private RecyclerView mGpsImgRecyclerView;
    private RecyclerView mSceneImgRecyclerView;

    /**
     * 备注信息
     */
    private EditText mRemarkEdit;

    private Button mTempStorageBt;//暂存按钮
    private Button mSubmitBt;//提交按钮

    private ImageSelectAdapter mImageSelectAdapter;
    private ImageSelectAdapter mImageSelectAdapter1;
    private ImageSelectAdapter mImageSelectAdapter2;
    private ImageSelectAdapter mImageSelectAdapter3;
    private ImageSelectAdapter mImageSelectAdapter4;

    public NewSurveyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化view
     */
    public void initView() {

        mBackImage = findViewById(R.id.backImage);//返回按钮

        mLocText = findViewById(R.id.locText);
        mNumberEdit = findViewById(R.id.numberEdit);
        mNameEdit = findViewById(R.id.nameEdit);
        mDetailAddressEdit = findViewById(R.id.detailAddressEdit);
        mLongitude = findViewById(R.id.longitude);
        mLatitudeEdit = findViewById(R.id.latitudeEdit);
        mStreetSpinner = findViewById(R.id.streetSpinner);
        mPoliceSpinner = findViewById(R.id.policeSpinner);

        mCameraRadioGroup = findViewById(R.id.cameraRadioGroup);
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

        mRemarkEdit = findViewById(R.id.remarkEdit);

        mTempStorageBt = findViewById(R.id.tempStorageBt);//暂存按钮
        mSubmitBt = findViewById(R.id.submitBt);
    }

    /**
     * 初始化点击监听
     *
     * @param onClickListener
     */
    public void initClickListener(OnClickListener onClickListener) {
        mBackImage.setOnClickListener(onClickListener);
        mSubmitBt.setOnClickListener(onClickListener);
        mTempStorageBt.setOnClickListener(onClickListener);
    }

    /**
     * 初始化图片适配器
     */
    public void initImageAdapter(List<String> imagePaths) {
        mImageSelectAdapter = new ImageSelectAdapter(imagePaths, getContext(), "环境照", 0, 8);
        mEnvImgRecyclerView.setAdapter(mImageSelectAdapter);
        mEnvImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    /**
     * 初始化全景照图片适配器
     */
    public void initImageAdapter1(List<String> imagePaths) {
        mImageSelectAdapter1 = new ImageSelectAdapter(imagePaths, getContext(), "全景照", 1, 2);
        mOverallViewRecyclerView.setAdapter(mImageSelectAdapter1);
        mOverallViewRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    /**
     * 初始化近照图片适配器
     */
    public void initImageAdapter2(List<String> imagePaths) {
        mImageSelectAdapter2 = new ImageSelectAdapter(imagePaths, getContext(), "近景照", 2, 2);
        mCloseShotRecyclerView.setAdapter(mImageSelectAdapter2);
        mCloseShotRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    /**
     * 初始化gps图片适配器
     */
    public void initImageAdapter3(List<String> imagePaths) {
        mImageSelectAdapter3 = new ImageSelectAdapter(imagePaths, getContext(), "gps照", 3, 1);
        mGpsImgRecyclerView.setAdapter(mImageSelectAdapter3);
        mGpsImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * 初始化现场照图片适配器
     */
    public void initImageAdapter4(List<String> imagePaths) {
        mImageSelectAdapter4 = new ImageSelectAdapter(imagePaths, getContext(), "现场画面照", 4, 1);
        mSceneImgRecyclerView.setAdapter(mImageSelectAdapter4);
        mSceneImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * 初始化添加图片的监听
     *
     * @param onAddImageListener
     */
    public void initAddImageListener(ImageSelectAdapter.OnAddImageListener onAddImageListener) {
        mImageSelectAdapter.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter1.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter2.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter3.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter4.setOnAddImageListener(onAddImageListener);
    }

    /**
     * 查看图片的监听
     */
    public void initWatchImageListener(ImageSelectAdapter.OnImageClickListener onImageClickListener) {
        mImageSelectAdapter.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter1.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter2.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter3.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter4.setOnImageClickListener(onImageClickListener);
    }

    public void notifyImgAdapter() {
        mImageSelectAdapter.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter1() {
        mImageSelectAdapter1.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter2() {
        mImageSelectAdapter2.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter3() {
        mImageSelectAdapter3.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter4() {
        mImageSelectAdapter4.notifyDataSetChanged();//唤醒数据更新
    }

}