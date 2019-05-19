package top.systemsec.survey.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


import java.io.File;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.ServerInfo;
import top.systemsec.survey.bean.ImageUploadState;


public class ImageFixAdapter extends RecyclerView.Adapter<ImageFixAdapter.MyViewHolder> {

    private static final String TAG = "ImageFixAdapter";

    private List<ImageUploadState> mImagePaths;//图片路径
    private String mImageHead;//图片头

    private Context mContext;

    private String mImageTitle;//这一组图片的名称
    private int mWidth;

    /**
     * 构造器
     *
     * @param imagePaths
     * @param context
     */
    public ImageFixAdapter(List<ImageUploadState> imagePaths, String imageHead, Context context, String imageTitle, int width) {
        mImagePaths = imagePaths;
        mImageHead = imageHead;//记录图片头
        mContext = context;
        mImageTitle = imageTitle;
        mWidth = width;//记录一下宽度
    }

    /**
     * 构造方法
     */


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);

        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();//布局管理
        layoutParams.width = mWidth / 4;
        layoutParams.height = mWidth / 4;
        itemView.setLayoutParams(layoutParams);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String imagePath = mImagePaths.get(position).getImagePath();//图片路径

        File file = new File(imagePath);

        if (file.exists()) {
            Glide.with(mContext).load(imagePath).error(R.drawable.image_error)
                    .into(holder.imageView);//设置图片
            Log.d(TAG, "onBindViewHolder: 图片存在 ");
        } else {
            String imageUrl = ServerInfo.SERVER_IP + "/Exploit/upload/thu/thu" + mImagePaths.get(position).getImageUrl();//图片路径
            Log.d(TAG, "loadFromWeb: imagePth " + imageUrl);
            Glide.with(mContext).load(imageUrl).placeholder(R.drawable.image_loading).error(R.drawable.image_error_big).into(holder.imageView);//设置图片
        }

        holder.deleteImage.setVisibility(View.GONE);//删除按钮不可见

        holder.imageView.setOnClickListener((v) -> {
            if (mOnImageClickListener != null)
                mOnImageClickListener.onClick(mImageTitle, mImagePaths, position);//图片点击监听
        });

    }

    /**
     * item数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mImagePaths == null ? 0 : mImagePaths.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;//展示图片的view
        ImageView deleteImage;//删除按钮

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            deleteImage = itemView.findViewById(R.id.delete);//删除按钮

        }
    }

    /**
     * 图片点击监听
     */
    public interface OnImageClickListener {
        void onClick(String imageTitle, List<ImageUploadState> imgPath, int imgIndex);
    }

    private OnImageClickListener mOnImageClickListener;

    /**
     * 设置图片点击监听
     *
     * @param onImageClickListener
     */
    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

}
