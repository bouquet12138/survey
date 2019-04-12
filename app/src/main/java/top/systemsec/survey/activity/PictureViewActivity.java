package top.systemsec.survey.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.BaseActivity;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.dialog.DeleteImageDialog;
import top.systemsec.survey.fragment.LittlePicFragment;

public class PictureViewActivity extends BaseActivity {

    private static final String TAG = "PictureViewActivity";

    private boolean mHasDelete;//是否删除过图片

    private boolean mShowDelete;//是否展示删除
    private String mImageName;
    private int mImgIndex;


    private ImageView mBackImage;
    private ImageView mDeleteImg;
    private TextView mTitleTextView;
    private ViewPager mViewPager;
    private DeleteImageDialog mDeleteImageDialog;//删除图片对话框

    private List<LittlePicFragment> mFragments = new ArrayList<>();//碎片
    private MyFragmentAdapter mMyFragmentAdapter;


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

        mShowDelete = bundle.getBoolean("showDelete", true);//默认是展示
        mImageName = bundle.getString("imageName");
        List<ImageUploadState> imageList = (List<ImageUploadState>) bundle.getSerializable("imageList");//图片列表
        mImgIndex = bundle.getInt("imgIndex");//图片索引
        String imgHead = bundle.getString("imgHead");//图片头

        Log.d(TAG, "initData: " + imageList);

        for (int i = 0; i < imageList.size(); i++) {
            LittlePicFragment littlePicFragment = new LittlePicFragment();
            littlePicFragment.setImagePath(imageList.get(i), imgHead);
            mFragments.add(littlePicFragment);//添加碎片
        }

    }

    /**
     * 初始化View
     */
    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mDeleteImg = findViewById(R.id.deleteImg);
        mViewPager = findViewById(R.id.viewPager);

        if (!mShowDelete)
            mDeleteImg.setVisibility(View.GONE);//不可见

        mTitleTextView = findViewById(R.id.titleTextView);
        mDeleteImageDialog = new DeleteImageDialog(PictureViewActivity.this);

        mTitleTextView.setText(mImageName + (mImgIndex + 1) + "/" + mFragments.size());//标题

    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        mMyFragmentAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyFragmentAdapter);//设置适配器
        mViewPager.setCurrentItem(mImgIndex);//设置图片位置
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        mBackImage.setOnClickListener((v) -> {
            back();
        });
        if (mShowDelete)//如果不展示也不设置监听
            mDeleteImg.setOnClickListener((v) -> {
                mDeleteImageDialog.show();//删除图片对话框显示
            });

        mDeleteImageDialog.setOnDeleteClickListener(() -> {
            mFragments.remove(mImgIndex);
            mImgIndex = mImgIndex != 0 ? mImgIndex = mImgIndex - 1 : 0;
            mHasDelete = true;//已经删过了
            mMyFragmentAdapter.notifyDataSetChanged();//唤醒数据更新
            mViewPager.setCurrentItem(mImgIndex);//设置当前索引
            if (mFragments.size() == 0) {
                mTitleTextView.setText(mImageName);
                mDeleteImg.setEnabled(false);//不可用
            } else
                mTitleTextView.setText(mImageName + (mImgIndex + 1) + "/" + mFragments.size());

            mDeleteImageDialog.dismiss();//对话框消失
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mImgIndex = i;//更新一下图片索引
                mTitleTextView.setText(mImageName + (mImgIndex + 1) + "/" + mFragments.size());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    //我的viewPager适配器
    class MyFragmentAdapter extends FragmentStatePagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

    /**
     * 按下返回键
     */
    @Override
    public void onBackPressed() {
        back();
    }

    /**
     * 返回
     */
    private void back() {
        if (!mHasDelete)
            finish();//销毁Activity
        else {
            Intent intent = new Intent();
            List<ImageUploadState> imageList = new ArrayList<>();//图片路径
            for (LittlePicFragment littlePicFragment : mFragments)
                imageList.add(littlePicFragment.getImagePath());//添加图片路径
            intent.putExtra("imageList", (Serializable) imageList);
            setResult(RESULT_OK, intent);//有删除图片
            finish();//结束activity
        }
    }

}
