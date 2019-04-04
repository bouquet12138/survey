package top.systemsec.survey.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import top.systemsec.survey.R;

public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.MyViewHolder> {

    //这两个静态变量来区分是normalView还是footView。
    private static final int NORMAL_VIEW = 0;
    private static final int FOOT_VIEW = 1;

    private List<String> mImagePaths;//图片路径
    private Context mContext;
    private int mMaxImageNum;//最大图片数

    /**
     * 构造方法
     */
    public ImageSelectAdapter(List<String> imagePaths, Context context, int maxImageNum) {
        mImagePaths = imagePaths;
        mContext = context;
        mMaxImageNum = maxImageNum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
            return new MyViewHolder(itemView, viewType);
        } else {
            View footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_add_image, parent, false);
            return new MyViewHolder(footView, viewType);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (getItemViewType(position) == NORMAL_VIEW) {
            String imagePath = mImagePaths.get(position);
            Glide.with(mContext).load(imagePath).into(holder.imageView);//设置图片
            holder.deleteImage.setOnClickListener((v) -> {
                mImagePaths.remove(position);
                notifyDataSetChanged();//唤醒数据更新
            });
        } else {
            holder.addImage.setOnClickListener((v) -> {
                if (mOnAddImageListener != null)
                    mOnAddImageListener.add(mMaxImageNum - mImagePaths.size());//可以添加的图片数
            });
        }


    }

    /**
     * item数量
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (mImagePaths.size() >= mMaxImageNum)//不能再添加了
            return mImagePaths.size();
        return mImagePaths == null ? 1 : mImagePaths.size() + 1;
    }

    /**
     * 得到view 的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {

        if (mImagePaths.size() >= mMaxImageNum)//不能再添加了
            return NORMAL_VIEW;//正常view

        if (position == getItemCount() - 1) {
            return FOOT_VIEW;
        }
        return NORMAL_VIEW;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;//展示图片的view
        ImageView deleteImage;//删除按钮
        ImageView addImage;//删除按钮

        public MyViewHolder(View itemView, int type) {
            super(itemView);
            if (type == NORMAL_VIEW) {
                imageView = itemView.findViewById(R.id.image_view);
                deleteImage = itemView.findViewById(R.id.delete);//删除按钮
            } else {
                addImage = itemView.findViewById(R.id.addImage);
            }
        }
    }

    /**
     * 添加图片的监听
     */
    public interface OnAddImageListener {
        void add(int num);
    }

    private OnAddImageListener mOnAddImageListener;//添加图片监听

    public void setOnAddImageListener(OnAddImageListener onAddImageListener) {
        mOnAddImageListener = onAddImageListener;
    }
}
