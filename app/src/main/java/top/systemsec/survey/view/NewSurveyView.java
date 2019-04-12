package top.systemsec.survey.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.adapter.ImageSelectAdapter;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.custom_view.SpinnerView;

public class NewSurveyView extends LinearLayout {

    private ImageView mBackImage;//返回按钮

    /**
     * 点位置信息
     */
    private TextView mLocText;

    private ScrollView mScrollView;//滚动view
    private ViewGroup mNumberLinear;//编号

    private EditText mPointNameEdit;
    private EditText mDetailAddressEdit;
    private EditText mLongitude;
    private EditText mLatitudeEdit;
    private SpinnerView mStreetSpinner;
    private SpinnerView mPoliceSpinner;

    /**
     * 摄像头信息
     */
    private RadioButton mPoleRadio;
    private RadioButton mWallRadio;

    private EditText mPoleHighEdit;
    private EditText mCrossArmNumEdit;
    private EditText mDirEdit1;
    private EditText mDirEdit2;
    private EditText mFaceRecNumEdit;
    private EditText mFaceLightNumEdit;
    private EditText mCarNumRecNumEdit;
    private EditText mGlobalNumEdit;

    /**
     * 摄像头信息
     */
    private RecyclerView mEnvImgRecyclerView;
    private RecyclerView mOverallViewRecyclerView;
    private RecyclerView mCloseShotRecyclerView;
    private RecyclerView mGpsImgRecyclerView;
    private RecyclerView mSceneImgRecyclerView;

    /**
     * 备注信息
     */
    private EditText mRemarkEdit;

    private ImageSelectAdapter mImageSelectAdapter;
    private ImageSelectAdapter mImageSelectAdapter1;
    private ImageSelectAdapter mImageSelectAdapter2;
    private ImageSelectAdapter mImageSelectAdapter3;
    private ImageSelectAdapter mImageSelectAdapter4;

    public NewSurveyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化view
     */
    public void initView() {

        mBackImage = findViewById(R.id.backImage);//返回按钮

        mScrollView = findViewById(R.id.scrollView);//滚动View

        mLocText = findViewById(R.id.locText);
        mNumberLinear = findViewById(R.id.numberLinear);//编号linear
        mNumberLinear.setVisibility(GONE);//编号不可见

        mPointNameEdit = findViewById(R.id.pointNameEdit);
        mDetailAddressEdit = findViewById(R.id.detailAddressEdit);
        mLongitude = findViewById(R.id.longitude);
        mLatitudeEdit = findViewById(R.id.latitudeEdit);
        mStreetSpinner = findViewById(R.id.streetSpinner);
        mPoliceSpinner = findViewById(R.id.policeSpinner);

        mPoleRadio = findViewById(R.id.poleRadio);//立杆安装
        mWallRadio = findViewById(R.id.wallRadio);//壁挂安装

        mPoleHighEdit = findViewById(R.id.poleHighEdit);
        mCrossArmNumEdit = findViewById(R.id.crossArmNumEdit);
        mDirEdit1 = findViewById(R.id.dirEdit1);
        mDirEdit2 = findViewById(R.id.dirEdit2);
        mFaceRecNumEdit = findViewById(R.id.faceRecNumEdit);
        mFaceLightNumEdit = findViewById(R.id.faceLightNumEdit);
        mCarNumRecNumEdit = findViewById(R.id.carNumRecNumEdit);
        mGlobalNumEdit = findViewById(R.id.globalNumEdit);

        mEnvImgRecyclerView = findViewById(R.id.envImgRecyclerView);
        mOverallViewRecyclerView = findViewById(R.id.overallViewRecyclerView);
        mCloseShotRecyclerView = findViewById(R.id.closeShotRecyclerView);
        mGpsImgRecyclerView = findViewById(R.id.gpsImgRecyclerView);
        mSceneImgRecyclerView = findViewById(R.id.sceneImgRecyclerView);

        mRemarkEdit = findViewById(R.id.remarkEdit);
    }

    public void initData(SurveyBean surveyBean) {
        mPointNameEdit.setText(surveyBean.getPointName());//站点名称
        if (!TextUtils.isEmpty(surveyBean.getPointName()))
            mPointNameEdit.setSelection(surveyBean.getPointName().length());//光标位置
        mDetailAddressEdit.setText(surveyBean.getDetailAddress());//详细地址
        mLongitude.setText(surveyBean.getLongitude());//经度
        mLatitudeEdit.setText(surveyBean.getLatitude());//纬度
        mStreetSpinner.setNowStr(surveyBean.getStreet());//街道
        mPoliceSpinner.setNowStr(surveyBean.getPolice());//警局

        //摄像头信息
        int installType = surveyBean.getCameraInstallType();

        if (installType == 2)
            mWallRadio.setChecked(true);//选中壁挂安装

        mPoleHighEdit.setText(surveyBean.getPoleHigh() + "");
        mCrossArmNumEdit.setText(surveyBean.getCrossArmNum() + "");//横臂数


        mDirEdit1.setText(surveyBean.getDir1());
        mDirEdit2.setText(surveyBean.getDir2());

        mFaceRecNumEdit.setText(surveyBean.getFaceRecNum() + "");//人脸识别数
        mFaceLightNumEdit.setText(surveyBean.getFaceLightNum() + "");//人脸补光灯数

        mCarNumRecNumEdit.setText(surveyBean.getCarNumRecNum() + "");//车牌识别监控头
        mGlobalNumEdit.setText(surveyBean.getGlobalNum() + "");//环球监控数

        mRemarkEdit.setText(surveyBean.getRemark());//备注
    }

    /**
     * 清除数据
     * 慎用
     */
    public void clearData() {
        mPoleHighEdit.setText("");//立杆高度
        mCrossArmNumEdit.setText("");//横臂数
        mFaceRecNumEdit.setText("");//人脸识别数
        mFaceLightNumEdit.setText("");//人脸补光灯数

        mCarNumRecNumEdit.setText("");//车牌识别监控头
        mGlobalNumEdit.setText("");//环球监控数
        mPointNameEdit.setText("");//设一下
    }

    /**
     * 隐藏定位
     */
    public void hideLocation() {
        mLocText.setVisibility(GONE);//定位不可见
    }

    /**
     * 初始化点击监听
     *
     * @param onClickListener
     */
    public void initClickListener(OnClickListener onClickListener) {
        mBackImage.setOnClickListener(onClickListener);

        mLocText.setOnClickListener(onClickListener);//定位
    }

    /**
     * 初始化街道
     *
     * @param streets 街道集合
     */
    public void initStreets(String[] streets) {
        mStreetSpinner.setData(streets);
    }

    /**
     * 初始化派出所
     *
     * @param polices 派出所集合
     */
    public void initPolices(String[] polices) {
        mPoliceSpinner.setData(polices);
    }

    /**
     * 初始化图片适配器
     */
    public void initImageAdapter(List<ImageUploadState> imagePaths) {
        mImageSelectAdapter = new ImageSelectAdapter(imagePaths, getContext(), "环境照", 0, 8);
        mEnvImgRecyclerView.setAdapter(mImageSelectAdapter);
        mEnvImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
    }

    /**
     * 初始化全景照图片适配器
     */
    public void initImageAdapter1(List<ImageUploadState> imagePaths) {
        mImageSelectAdapter1 = new ImageSelectAdapter(imagePaths, getContext(), "全景照", 1, 2);
        mOverallViewRecyclerView.setAdapter(mImageSelectAdapter1);
        mOverallViewRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    /**
     * 初始化近照图片适配器
     */
    public void initImageAdapter2(List<ImageUploadState> imagePaths) {
        mImageSelectAdapter2 = new ImageSelectAdapter(imagePaths, getContext(), "近景照", 2, 2);
        mCloseShotRecyclerView.setAdapter(mImageSelectAdapter2);
        mCloseShotRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    /**
     * 初始化gps图片适配器
     */
    public void initImageAdapter3(List<ImageUploadState> imagePaths) {
        mImageSelectAdapter3 = new ImageSelectAdapter(imagePaths, getContext(), "gps照", 3, 1);
        mGpsImgRecyclerView.setAdapter(mImageSelectAdapter3);
        mGpsImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * 初始化现场照图片适配器
     */
    public void initImageAdapter4(List<ImageUploadState> imagePaths) {
        mImageSelectAdapter4 = new ImageSelectAdapter(imagePaths, getContext(), "现场画面照", 4, 1);
        mSceneImgRecyclerView.setAdapter(mImageSelectAdapter4);
        mSceneImgRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * 初始化添加图片的监听
     *
     * @param onAddImageListener
     */
    public void initAddImageListener(ImageSelectAdapter.OnAddImageListener onAddImageListener) {
        mImageSelectAdapter.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter1.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter2.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter3.setOnAddImageListener(onAddImageListener);
        mImageSelectAdapter4.setOnAddImageListener(onAddImageListener);
    }

    /**
     * 查看图片的监听
     */
    public void initWatchImageListener(ImageSelectAdapter.OnImageClickListener onImageClickListener) {
        mImageSelectAdapter.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter1.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter2.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter3.setOnImageClickListener(onImageClickListener);
        mImageSelectAdapter4.setOnImageClickListener(onImageClickListener);
    }

    public void notifyImgAdapter() {
        mImageSelectAdapter.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter1() {
        mImageSelectAdapter1.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter2() {
        mImageSelectAdapter2.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter3() {
        mImageSelectAdapter3.notifyDataSetChanged();//唤醒数据更新
    }

    public void notifyImgAdapter4() {
        mImageSelectAdapter4.notifyDataSetChanged();//唤醒数据更新
    }

    /**
     * 设置经度
     */
    public void setLongitude(String longitude) {
        mLongitude.setText(longitude);
        mLongitude.setSelection(longitude.length());
    }

    /**
     * 设置纬度
     */
    public void setLatitude(String latitude) {
        mLatitudeEdit.setText(latitude);
        mLatitudeEdit.setSelection(latitude.length());
    }

    /**
     * 得到信息
     */
    public SurveyBean getSurveyInfo() {

        String pointName = mPointNameEdit.getText().toString();//点位名称
        if (TextUtils.isEmpty(pointName)) {
            Toast.makeText(getContext(), "点位名称不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        String detailAddress = mDetailAddressEdit.getText().toString();//点位名称
        if (TextUtils.isEmpty(detailAddress)) {
            Toast.makeText(getContext(), "详细地址不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        String longitude = mLongitude.getText().toString();//点位名称
        if (TextUtils.isEmpty(longitude)) {
            Toast.makeText(getContext(), "经度不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        String latitude = mLatitudeEdit.getText().toString();//点位名称
        if (TextUtils.isEmpty(latitude)) {
            Toast.makeText(getContext(), "纬度不能为空", Toast.LENGTH_SHORT).show();
            return null;
        }

        String street = mStreetSpinner.getNowStr();

        if (TextUtils.isEmpty(street)) {//街道为空
            Toast.makeText(getContext(), "请选择街道", Toast.LENGTH_SHORT).show();
            return null;
        }

        String police = mPoliceSpinner.getNowStr();

        if (TextUtils.isEmpty(police)) {//街道为空
            Toast.makeText(getContext(), "请选择警局", Toast.LENGTH_SHORT).show();
            return null;
        }

        int installType = 1;
        if (mWallRadio.isChecked())
            installType = 2;

        String poleHighStr = mPoleHighEdit.getText().toString();
        float poleHigh;//立杆
        if (TextUtils.isEmpty(poleHighStr)) {
            Toast.makeText(getContext(), "立杆高度不能为空", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            try {
                poleHigh = Float.parseFloat(poleHighStr);//解析一下字符串
            } catch (NumberFormatException e) {
                mPoleHighEdit.requestFocus();//请求一下焦点
                Toast.makeText(getContext(), "请输入正确的立杆高度", Toast.LENGTH_SHORT).show();
                return null;//退出
            }
        }

        String crossArmNumStr = mCrossArmNumEdit.getText().toString();
        int crossArmNum;//横臂数量
        if (TextUtils.isEmpty(crossArmNumStr)) {
            Toast.makeText(getContext(), "横臂数不能为空", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            crossArmNum = Integer.parseInt(crossArmNumStr);//解析一下横臂数
        }

        String dir1 = mDirEdit1.getText().toString();
        String dir2 = mDirEdit2.getText().toString();

        String faceRecNumStr = mFaceRecNumEdit.getText().toString();
        int faceRecNum = 0;//人脸识别数
        if (!TextUtils.isEmpty(faceRecNumStr)) {
            faceRecNum = Integer.parseInt(faceRecNumStr);
        }

        String faceLightNumStr = mFaceLightNumEdit.getText().toString();
        int faceLightNum = 0;//人脸补光灯数
        if (!TextUtils.isEmpty(faceLightNumStr)) {
            faceLightNum = Integer.parseInt(faceLightNumStr);
        }

        String carRecNumStr = mCarNumRecNumEdit.getText().toString();
        int carRecNum = 0;//车牌识别
        if (!TextUtils.isEmpty(carRecNumStr)) {
            carRecNum = Integer.parseInt(carRecNumStr);
        }

        String globalNumStr = mGlobalNumEdit.getText().toString();
        int globalNum = 0;//环球监控头
        if (!TextUtils.isEmpty(globalNumStr)) {
            globalNum = Integer.parseInt(globalNumStr);
        }


        List<ImageUploadState> list = getImageUploadState(mImageSelectAdapter);
        if (list == null)
            return null;
        List<ImageUploadState> list1 = getImageUploadState(mImageSelectAdapter1);
        if (list1 == null)
            return null;
        List<ImageUploadState> list2 = getImageUploadState(mImageSelectAdapter2);
        if (list2 == null)
            return null;
        List<ImageUploadState> list3 = getImageUploadState(mImageSelectAdapter3);
        if (list3 == null)
            return null;
        List<ImageUploadState> list4 = getImageUploadState(mImageSelectAdapter4);
        if (list4 == null)
            return null;


        String remark = mRemarkEdit.getText().toString();

        List<ImageUploadState> imageList = new ArrayList<>();
        imageList.addAll(list);
        imageList.addAll(list1);
        imageList.addAll(list2);
        imageList.addAll(list3);
        imageList.addAll(list4);

        SurveyBean surveyBean = new SurveyBean(
                pointName, detailAddress, longitude, latitude, street, police,//点位信息
                installType, poleHigh, crossArmNum, dir1, dir2, faceRecNum, faceLightNum, carRecNum, globalNum,//摄像机信息
                imageList, remark);//备注

        return surveyBean;
    }

    /**
     * 得到图片上传信息 并判断状态
     *
     * @return
     */
    private List<ImageUploadState> getImageUploadState(ImageSelectAdapter imageSelectAdapter) {
        List<ImageUploadState> list = imageSelectAdapter.getImagePaths();
        String title = imageSelectAdapter.getImageTitle();//标题
        int maxNum = imageSelectAdapter.getMaxImageNum();//最大图片数
        if (list.size() != maxNum) {
            Toast.makeText(getContext(), title + "必须" + maxNum + "张", Toast.LENGTH_SHORT).show();
            return null;
        }
        for (ImageUploadState imageState : list) {
            File file = new File(imageState.getImagePath());
            if (!file.exists()) {
                Toast.makeText(getContext(), "请清除照破损图片", Toast.LENGTH_SHORT).show();
                mImageSelectAdapter.notifyDataSetChanged();
                mImageSelectAdapter1.notifyDataSetChanged();
                mImageSelectAdapter2.notifyDataSetChanged();
                mImageSelectAdapter3.notifyDataSetChanged();
                mImageSelectAdapter4.notifyDataSetChanged();//都唤醒一下
                return null;
            }
        }
        return list;
    }

    /**
     * 站点获取焦点
     */
    public void pointNameFocus() {
        mPointNameEdit.requestFocus();//请求焦点
        mPoleHighEdit.setText("");//文本为空
        mPointNameEdit.setHint("请输入点位名称");
        mScrollView.smoothScrollTo(0, 0);
    }

    /**
     * 滑动到顶部
     */
    public void scrollTop() {
        mScrollView.smoothScrollTo(0, 0);
    }

    /**
     * 得到站点名称
     *
     * @return 得到站点名
     */
    public String getPointName() {
        return mPointNameEdit.getText().toString();
    }


}
