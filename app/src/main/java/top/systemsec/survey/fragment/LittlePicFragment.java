package top.systemsec.survey.fragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.InflaterInputStream;

import top.systemsec.survey.R;
import top.systemsec.survey.activity.PictureViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LittlePicFragment extends Fragment {

    protected View mView;

    private String mImagePath;//图片路径
    private PhotoView mImgView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_little_pic, container, false);
            initView();
        }
        return mView;
    }

    /**
     * 初始化view
     */
    private void initView() {
        mImgView = mView.findViewById(R.id.imgView);
        mImgView.enable();//启用
         Glide.with(getContext()).load(mImagePath).into(mImgView);//设置图片
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }
}
