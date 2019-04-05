package top.systemsec.survey.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.R;

public class PictureViewActivity extends AppCompatActivity {

    private static final String TAG = "PictureViewActivity";

    private boolean mHasDelete;//是否删除过图片

    private List<String> mImageList;
    private String mImageName;
    private int mImgIndex;

    private ImageView mBackImage;
    private ImageView mDeleteImg;
    private TextView mTitleTextView;
    private ViewPager mViewPager;

    private List<View> mViewList = new ArrayList<>();
    private MyPagerAdapter mMyPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_view);
        initData();
        initView();
        initAdapter();
        initListener();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mImageName = bundle.getString("imageName");
        mImageList = (List<String>) bundle.getSerializable("imageList");//图片列表
        mImgIndex = bundle.getInt("imgIndex");//图片索引

        Log.d(TAG, "initData: " + mImageList);
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mDeleteImg = findViewById(R.id.deleteImg);
        mViewPager = findViewById(R.id.viewPager);

        mTitleTextView = findViewById(R.id.titleTextView);
        mTitleTextView.setText(mImageName + (mImgIndex + 1) + "/" + mImageList.size());//标题

        for (int i = 0; i < mImageList.size(); i++) {
            View view = getLayoutInflater().inflate(R.layout.item_watch_image, null);
            mViewList.add(view);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mMyPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mMyPagerAdapter);//设置适配器
        mViewPager.setCurrentItem(mImgIndex);//设置图片位置
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackImage.setOnClickListener((v) -> {
            if (!mHasDelete)
                finish();//销毁Activity
            else {
                setResult(RESULT_OK, new Intent());//有删除图片
                finish();//结束activity
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mImgIndex = i + 1;//更新一下图片索引
                mTitleTextView.setText(mImageName + (mImgIndex + 1) + "/" + mImageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    //我的viewPager适配器
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//移除view
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            PhotoView photoView = view.findViewById(R.id.imgView);
            photoView.enable();//启用
            Glide.with(PictureViewActivity.this).load(mImageList.get(position)).into(photoView);//设置图片
            return view;
        }
    }


}
