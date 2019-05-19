package top.systemsec.survey.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.CameraView;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.utils.LocalImageSave;

public class CameraActivity extends AppCompatActivity {

    private static final String TAG = "CameraActivity";

    private String mPointName;//站点名
    private int mMaxImageNum;//最大图片数

    private List<String> mImageList = new ArrayList<>();//图片列表
    private CameraView mCamera;
    private Button mTakePhotoBt;
    private ImageView mBackImg;
    private View mBlackView;
    private ValueAnimator mValueAnimator;

    private int mAddImg = 0;

    private float mScreenWidth;
    private float mScreenHeight;

    private Toast mSuccessToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏一下

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;

        Log.d(TAG, "onCreate: " + mScreenWidth + " screenHeight " + mScreenHeight);

        initView();
        initAnim();
        initData();
        initListener();
    }


    /**
     * 初始化动画
     */
    private void initAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1, 0);
        mValueAnimator.setDuration(500);//200秒
        mValueAnimator.addUpdateListener((ValueAnimator animation) -> {
                    float value = (float) animation.getAnimatedValue();
                    mBlackView.setAlpha(value);//设置透明度
                }
        );
    }

    /**
     * 初始化view
     */
    private void initView() {
        mCamera = findViewById(R.id.camera);
        mTakePhotoBt = findViewById(R.id.takePhotoBt);
        mBackImg = findViewById(R.id.backImg);
        mBlackView = findViewById(R.id.blackView);

        mSuccessToast = Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //站点名称
        mPointName = getIntent().getStringExtra("pointName");
        //最大图片数
        mMaxImageNum = getIntent().getIntExtra("maxImage", 1);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mTakePhotoBt.setOnClickListener((v) -> {
            if (mAddImg < mMaxImageNum) {
                mBlackView.setVisibility(View.VISIBLE);//可见
                mValueAnimator.start();
                mCamera.takePicture();//拍照
                mTakePhotoBt.setEnabled(false);//不可用
            }
        });

        mBackImg.setOnClickListener((v) -> {
            returnResult();//带着结果返回
        });

        mCamera.addCallback(new CameraView.Callback() {
            @Override
            public void onCameraOpened(CameraView cameraView) {
                super.onCameraOpened(cameraView);
                Log.d(TAG, "onCameraOpened: " + mScreenHeight / mScreenWidth);

                try {
                    mCamera.setAspectRatio(AspectRatio.of(1920, 1080));
                    mCamera.setPictureSize(1920, 1080);
                } catch (UnsupportedOperationException e) {
                } catch (NullPointerException e) {
                }
                mCamera.setAutoFocus(true);

            }

            @Override
            public void onCameraClosed(CameraView cameraView) {
                super.onCameraClosed(cameraView);
            }

            @Override
            public void onPictureTaken(CameraView cameraView, byte[] data) {
                super.onPictureTaken(cameraView, data);
                Log.d(TAG, "onPictureTaken: " + data.length);

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");//年月日时分秒
                String fileName;

                if (TextUtils.isEmpty(mPointName)) {
                    fileName = NowUserInfo.getUserBean().getName() + "_" + simpleDateFormat.format(new Date());//文件名
                } else {
                    fileName = mPointName + "_" + NowUserInfo.getUserBean().getName() + "_" + simpleDateFormat.format(new Date());//文件名
                }

                int state = LocalImageSave.saveImage(bitmap, mPointName, fileName, data.length);
                switch (state) {
                    case LocalImageSave.STORAGE_CARD_DISABLED://SD卡错误
                        Toast.makeText(CameraActivity.this, "SD卡错误", Toast.LENGTH_SHORT).show();
                        mTakePhotoBt.setEnabled(true);//可用
                        break;
                    case LocalImageSave.SAVE_OK:
                        mSuccessToast.show();//展示
                        String imagePath = LocalImageSave.sImagePath;
                        if (mImageList.size() < mMaxImageNum)//只有小于才能添加
                            mImageList.add(imagePath);//添加图片

                        if (mImageList.size() >= mMaxImageNum) //大于等于最大图片数
                            returnResult();//将结果返回
                        else {
                            mTakePhotoBt.setEnabled(true);//保存完成才启用
                        }
                        break;
                    default:
                        Toast.makeText(CameraActivity.this, "内存空间不足", Toast.LENGTH_SHORT).show();
                        mTakePhotoBt.setEnabled(true);//可用
                        break;
                }

            }
        });

    }

    /**
     * 判断图片张数
     */
    private void returnResult() {
        if (mImageList.size() != 0) {
            Intent intent = new Intent();
            intent.putExtra("imageList", (Serializable) mImageList);
            setResult(RESULT_OK, intent);
            Log.d(TAG, "returnResult: " + mImageList);
            finish();
        } else {
            finish();//直接结束
        }
    }

    @Override
    public void onBackPressed() {
        returnResult();//返回结果
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera.start();//启动
    }

    @Override
    protected void onPause() {
        mCamera.stop();//关闭
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        mSuccessToast.cancel();//取消
        super.onDestroy();
    }
}
