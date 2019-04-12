package top.systemsec.survey.fragment;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import top.systemsec.survey.R;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.bean.ImageUploadState;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class LittlePicFragment extends Fragment {

    private static final String TAG = "LittlePicFragment";//加载成功
    protected View mView;

    private ImageUploadState mImagePath;//图片路径
    private PhotoView mImgView;

    private String mImageHead;//后台头

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

        File file = new File(mImagePath.getImagePath());

        RequestListener<Drawable> listener = new RequestListener<Drawable>() {
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
        };

        if (file.exists())
            Glide.with(getContext()).load(mImagePath.getImagePath()).
                    placeholder(R.drawable.image_loading).error(R.drawable.image_error_big).listener(listener).into(mImgView);//设置图片
        else {

            String imageUrl = ServerInfo.SERVER_IP + "/Exploit/upload/" + mImageHead + "/" + mImagePath.getImageUrl();
            Log.d(TAG, "loadFromWeb: imageUrl " + imageUrl);
            Glide.with(getContext()).load(imageUrl).placeholder(R.drawable.image_loading).error(R.drawable.image_error_big).
                    placeholder(R.drawable.image_loading).listener(listener).into(mImgView);//设置图片

        }

    }


    public ImageUploadState getImagePath() {
        return mImagePath;
    }

    public void setImagePath(ImageUploadState imagePath, String imageHead) {
        mImagePath = imagePath;
        mImageHead = imageHead;//图片头
    }
}
