package top.systemsec.survey.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;

import top.systemsec.survey.base.MyApplication;

/**
 * Created by xiaohan on 2017/12/29.
 */

public class LocalImageSave {

    /**
     * 构造器私有工具类
     */
    private LocalImageSave() {
    }

    public static String sImagePath;//生成的图片路径

    public static final int IMAGE_DAMAGE = 1;//图片破损
    public static final int STORAGE_CARD_DISABLED = 2;//储存卡不可用
    public static final int SAVE_OK = 3;//储存成功
    public static final int SAVE_FAIL = 4;//保存失败

    private static final String TAG = "LocalImageSave";

    //用来存储图片并返回本地存储的路径 在app文件件夹下存储，
    public static String saveImage(Bitmap bitmap, String dirName) {
        if (bitmap != null) {
            String state = Environment.getExternalStorageState();

            if (!state.equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(MyApplication.getContext(), "储存卡不可用", Toast.LENGTH_SHORT).show();
                return null;
            }

            File dir = MyApplication.getContext().getExternalCacheDir();
            Log.d(TAG, "saveImage: ");
            Date date = new Date();
            Random random = new Random(date.getTime());
            String fileName = "survey" + random.nextInt(Integer.MAX_VALUE) + ".jpg";
            File headDir = new File(dir, dirName);
            if (!headDir.exists()) {
                headDir.mkdir();//创建文件夹
            }
            File file = new File(headDir, fileName);
            if (file.exists()) {
                fileName = "survey" + random.nextInt(Integer.MAX_VALUE) + ".jpg";
                file = new File(dir, fileName);//如果图片存在了，就换个名字
            }
            FileOutputStream fileOutputStream = null;
            try {
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return file.getAbsolutePath();
        }
        return null;
    }


    /**
     * 将图片移动到相册
     *
     * @param sourceFilePath 源文件
     * @param albumName      相册名
     * @param fileName
     * @return 储存成功与否
     */
    public static int moveImageToAlbum(String sourceFilePath, String albumName, String fileName) {

        if (TextUtils.isEmpty(sourceFilePath))
            return SAVE_FAIL;

        String fileSuffix = sourceFilePath.substring(sourceFilePath.lastIndexOf("."));
        Log.d(TAG, "moveImageToAlbum: " + fileSuffix);//文件后缀

        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return STORAGE_CARD_DISABLED;//储存卡不可用
        }

        File appRootDir = new File(Environment.getExternalStorageDirectory(), "勘察宝");//根文件

        if (!appRootDir.exists())//根文件不存在 创建一下
            appRootDir.mkdir();

        File dir = new File(appRootDir, albumName);//站点文件夹

        //文件夹不存在就创建
        if (!dir.exists())
            dir.mkdir();

        File newFile = new File(dir, fileName + fileSuffix);

        int num = 1;
        while (newFile.exists() && num < Integer.MAX_VALUE - 1) {//循环起名
            newFile = new File(dir, fileName + "(" + num + ")" + fileSuffix);//如果图片存在了，就换个名字
            num++;
        }


        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(newFile);
            Bitmap bitmap = BitmapFactory.decodeFile(sourceFilePath);//源路径
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            } else {
                return IMAGE_DAMAGE;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return SAVE_FAIL;
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();//关闭
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //TODO:这里可有可无主要是为了内存
       /* if (!sourceFilePath.contains("勘察宝")) {
            File file = new File(sourceFilePath);
            file.delete();//删除图片
        }*/

        //通知一下系统文件保存成功
        Uri uri = Uri.fromFile(newFile);
        MyApplication.getContext().sendBroadcast
                (new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        sImagePath = newFile.getAbsolutePath();//绝对路径
        return SAVE_OK;//保存成功
    }


}
