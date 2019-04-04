package top.systemsec.survey.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;


import java.util.List;

import top.systemsec.survey.GifSizeFilter;
import top.systemsec.survey.Glide4Engine;
import top.systemsec.survey.R;
import top.systemsec.survey.view.NewSurveyView;


public class NewSurveyActivity extends AppCompatActivity {

    private static final String TAG = "NewSurveyActivity";

    private final int REQUEST_CODE_CHOOSE = 0;//选择图片
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;//请求读取本地图片

    private NewSurveyView mNewSurveyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_survey);
        initView();
        initListener();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mNewSurveyView = findViewById(R.id.newSurveyView);//新勘察
        mNewSurveyView.initView();//初始化view
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
            }
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
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();//从本地选择图片
                } else {
                    Toast.makeText(this, "用户拒绝权限，无法读取本地图片", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /**
     * 从相册选择图片 权限
     */
    private void selectImageAuthor() {
        if (ContextCompat.checkSelfPermission(NewSurveyActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 如果用户已经拒绝了当前权限,shouldShowRequestPermissionRationale返回true，此时我们需要进行必要的解释和处理
            if (ActivityCompat.shouldShowRequestPermissionRationale(NewSurveyActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(NewSurveyActivity.this, "用户拒绝了权限，不能读取本地图片", Toast.LENGTH_SHORT).show();
            } else {
                //请求权限
                ActivityCompat.requestPermissions(NewSurveyActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        } else {
            selectImage();//从本地选择图片
        }
    }

    /**
     * 从相册选择图片
     */
    public void selectImage() {
        Matisse.from(NewSurveyActivity.this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.example.survey.sample.fileprovider"))
                .maxSelectable(8)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {

            List<String> pathList = Matisse.obtainPathResult(data);
            Log.d(TAG, "onActivityResult: pathList " + pathList);
        }
    }

}
