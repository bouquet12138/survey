package top.systemsec.survey.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.base.MVPBaseActivity;

/**
 * 权限申请的地方
 */
public abstract class NewSurveyPermissionActivity extends MVPBaseActivity {

    protected final int PERMISSIONS_READ_CONTACTS = 0;//读取本地图片
    protected final int PERMISSIONS_LOCATION_CODE = 1;//定位权限

    /**
     * 从相册选择图片 权限
     */
    protected void selectImageAuthor() {
        List<String> permissionList = new ArrayList<>();//权限组
        if (ContextCompat.checkSelfPermission(NewSurveyPermissionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);//读取权限
        if (ContextCompat.checkSelfPermission(NewSurveyPermissionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);//写权限
       /* if (ContextCompat.checkSelfPermission(NewSurveyPermissionActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(Manifest.permission.CAMERA);//相机权限*/

        if (permissionList.size() != 0) {
            // 如果用户已经拒绝了当前权限,shouldShowRequestPermissionRationale返回true，此时我们需要进行必要的解释和处理
            for (String str : permissionList) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(NewSurveyPermissionActivity.this, str)) {
                    Toast.makeText(NewSurveyPermissionActivity.this, "用户拒绝了权限，不能读取本地图片，" +
                            "请到设置界面打开", Toast.LENGTH_SHORT).show();
                    return;//返回出去
                }
            }
            ActivityCompat.requestPermissions(NewSurveyPermissionActivity.this,
                    permissionList.toArray(new String[permissionList.size()]), PERMISSIONS_READ_CONTACTS);//读取相册

        } else {
            selectImage();//从本地选择图片
        }
    }

    /**
     * 选择相片
     */
    protected abstract void selectImage();

    /**
     * 获取定位
     */
    protected void obtainLocAuthor() {

        //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(NewSurveyPermissionActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(NewSurveyPermissionActivity.this, "请前往设置界面打开定位权限", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_LOCATION_CODE);//获取定位权限
            }

        } else {
            requestLocation();//请求定位
        }
    }

    /**
     * 请求权限的响应
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_READ_CONTACTS: {
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
            case PERMISSIONS_LOCATION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) // 权限被用户同意。执形我们想要的操作
                    requestLocation();//请求定位
                else
                    Toast.makeText(this, "用户拒绝权限不能获取定位", Toast.LENGTH_SHORT).show();

            }
            break;

        }
    }

    /**
     * 请求定位
     */
    protected abstract void requestLocation();

}
