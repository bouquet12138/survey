package top.systemsec.survey.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.Item;
import com.zhihu.matisse.internal.entity.UncapableCause;

import java.util.Set;

import top.systemsec.survey.R;

public class NewSurveyActivity extends AppCompatActivity implements View.OnClickListener {

    private final int REQUEST_CODE_CHOOSE = 0;//选择图片
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;//请求读取本地图片

    private ImageView mBackImg;//返回按钮
    private TextView mLocText;
    private EditText mNumberEdit;
    private EditText mNameEdit;
    private EditText mDetailAddressEdit;
    private EditText mLongitude;
    private EditText mLatitudeEdit;
    private Spinner mStreetSpinner;
    private Spinner mPoliceSpinner;

    private ImageView mAddImage;//添加图片

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

        mBackImg = findViewById(R.id.backImage);//返回按钮
        mLocText = findViewById(R.id.locText);//定位的

        mNumberEdit = findViewById(R.id.numberEdit);
        mNameEdit = findViewById(R.id.nameEdit);
        mDetailAddressEdit = findViewById(R.id.detailAddressEdit);
        mLongitude = findViewById(R.id.longitude);
        mLatitudeEdit = findViewById(R.id.latitudeEdit);
        mStreetSpinner = findViewById(R.id.streetSpinner);
        mPoliceSpinner = findViewById(R.id.policeSpinner);

        mAddImage = findViewById(R.id.addImage);//TODO:等着删去

    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mLocText.setOnClickListener(this);//获取定位
        mBackImg.setOnClickListener(this);//返回按钮监听
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果还没有获取到读取本地文件的权限
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
     * 从相册选择图片
     */
    public void selectImage() {
        Matisse.from(NewSurveyActivity.this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(8)
                .addFilter(new Filter() {
                    @Override
                    protected Set<MimeType> constraintTypes() {
                        return null;
                    }

                    @Override
                    public UncapableCause filter(Context context, Item item) {
                        return null;
                    }
                })
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();
                break;
        }
    }
}
