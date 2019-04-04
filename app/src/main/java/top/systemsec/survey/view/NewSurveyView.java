package top.systemsec.survey.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import top.systemsec.survey.R;

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

}
