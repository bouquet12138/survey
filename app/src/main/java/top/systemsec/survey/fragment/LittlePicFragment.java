package top.systemsec.survey.fragment;


import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.zip.InflaterInputStream;

import top.systemsec.survey.R;
import top.systemsec.survey.activity.PictureViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LittlePicFragment extends Fragment {

    private static final String TAG = "LittlePicFragment";//加载成功
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
        Glide.with(getContext()).load(mImagePath).error(R.drawable.image_error_big).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mImgView.enable();//启用
                Log.d(TAG, "onResourceReady: ");
                return false;
            }
        }).into(mImgView);//设置图片


    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }
}
