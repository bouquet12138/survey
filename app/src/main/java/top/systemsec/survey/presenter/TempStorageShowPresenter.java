package top.systemsec.survey.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import java.io.File;
import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.StreetAndPolice;

import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.mobel.NewSurveyModel;

import top.systemsec.survey.utils.LocalImageSave;
import top.systemsec.survey.view.ITempStorageShowView;

public class TempStorageShowPresenter extends MVPBasePresenter<ITempStorageShowView> {

    private static final String TAG = "TempStoragePresenter";

    private SurveyBean mSurveyBean;//站点bean
    private List<ImageUploadState> mImageUploadStates;//图片上传列表
    private int mNowUploadIndex = 0;//当前上传的索引

    private NewSurveyModel mNewSurveyModel = new NewSurveyModel();//模型

    private final int STREET_SUCCESS = 0;//成功
    private final int STREET_NET_ERROR = 1;//网络错误
    private final int STREET_COMPLETE = 2;//响应完成

    private final int IMAGE_SUCCESS = 3;//图片上传成功
    private final int IMAGE_NET_ERROR = 4;//网络错误

    private final int ALL_SUCCESS = 5;//所有信息上传成功
    private final int ALL_NET_ERROR = 6;//所有信息上传时网络错误

    private final int IMAGE_LOCAL_SUCCESS = 7;//图片本地缓存成功
    private final int IMAGE_LOCAL_ERROR = 8;//图片本地缓存失败

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case STREET_SUCCESS:
                    StreetAndPolice streetAndPolice = (StreetAndPolice) msg.obj;
                    String[] streets = streetAndPolice.getStreetList();//街道
                    String[] polices = streetAndPolice.getPoliceList();//警局

                    getView().initStreet(streets);//初始化街道
                    getView().initPolice(polices);//初始化警局
                    break;
                case STREET_NET_ERROR:
                    getView().showToast("网络错误");
                    String[] streets1 = getView().getResources().getStringArray(R.array.streets);//从本地读取街道
                    String[] polices1 = getView().getResources().getStringArray(R.array.polices);//从本地读取警局

                    getView().initStreet(streets1);//初始化街道
                    getView().initPolice(polices1);//初始化警局
                    break;
                case STREET_COMPLETE:
                    getView().hideLoading();//隐藏进度条
                    break;
                case IMAGE_SUCCESS:
                    String imageUrl = (String) msg.obj;
                    mImageUploadStates.get(mNowUploadIndex).setImageUrl(imageUrl);//设置图片Url
                    mNowUploadIndex++;
                    if (mNowUploadIndex >= mImageUploadStates.size()) {
                        mSurveyBean.setImgList(mImageUploadStates);//设置图片列表
                        upLoadAllInfo(mSurveyBean);//上传所有信息
                    } else {
                        upLoadImage();//继续上传
                    }
                    break;
                case IMAGE_NET_ERROR:
                    getView().showToast("网络错误,数据已缓存到本地");
                    mSurveyBean.setImgList(mImageUploadStates);//设置图片列表
                    getView().sysImageState(mImageUploadStates);//同步一下图片状态
                    saveSurvey(mSurveyBean);//存储一下
                    getView().hideLoading();//隐藏进度框
                    break;
                case ALL_SUCCESS:
                    getView().showToast("数据上传成功");
                    String number = (String) msg.obj;//得到编号
                    mSurveyBean.setNumber(number);//设置编号
                    mSurveyBean.setUpLoadOk(true);//上传成功
                    saveSurvey(mSurveyBean);//存储一下
                    getView().hideLoading();//隐藏进度框
                    getView().uploadOk();//上传成功
                    break;
                case ALL_NET_ERROR:
                    getView().showToast("网络错误数据上传失败，已缓存到本地");
                    saveSurvey(mSurveyBean);//存储一下
                    getView().hideLoading();//隐藏进度框
                    break;

                case IMAGE_LOCAL_SUCCESS:
                    mNowUploadIndex = 0;//当前上传索引
                    upLoadImage();
                    break;
                case IMAGE_LOCAL_ERROR:
                    getView().showToast("内存控件不足，保存失败");
                    mNowUploadIndex = 0;//当前上传索引
                    upLoadImage();
                    break;
            }
        }
    };

    /**
     * 得到街道警局信息
     */
    public void getStreetAndPoliceInfo() {
        if (!isViewAttached())//没有view绑定
            return;

        mNewSurveyModel.getStreetAndPolice(new OnGetInfoListener<StreetAndPolice>() {
            @Override
            public void onSuccess(StreetAndPolice info) {
                Message message = mHandler.obtainMessage();//获取信息
                message.what = STREET_SUCCESS;//成功
                message.obj = info;
                mHandler.sendMessage(message);//发送信息
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(STREET_NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(STREET_COMPLETE);//响应完成
            }
        });

    }

    /**
     * 上传图片
     *
     * @param surveyBean
     */
    public void upLoad(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        mSurveyBean = surveyBean;//提取到外面
        mImageUploadStates = surveyBean.getImgList();

        getView().showLoading("图片保存中..");//展示进度狂
        saveImg();//先将图片保存到本地
    }


    /**
     * 上传所有信息
     */
    private void upLoadAllInfo(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        getView().setLoadingHint("全部数据上传中..");
        Log.d(TAG, "upLoadAllInfo: 后台编号 " + surveyBean.getNumber());
        mNewSurveyModel.uploadSurveyInfo(surveyBean, true, new OnGetInfoListener<String>() {
            @Override
            public void onSuccess(String info) {
                Message message = mHandler.obtainMessage();//获取信息
                message.what = ALL_SUCCESS;//所有的都完成
                message.obj = info;//把信息传过去
                mHandler.sendMessage(message);//发送信息
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(ALL_NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
            }
        });
    }

    /**
     * 上传图片
     */
    private void upLoadImage() {

        for (; mNowUploadIndex < mImageUploadStates.size() && mImageUploadStates.get(mNowUploadIndex).isUploadOk(); mNowUploadIndex++)
            ;//索引加加
        getView().setLoadingHint("上传第" + (mNowUploadIndex + 1) + "张图片");

        if (mNowUploadIndex == mImageUploadStates.size()) {
            mSurveyBean.setImgList(mImageUploadStates);//设置图片列表
            upLoadAllInfo(mSurveyBean);//上传所有信息
            return;
        }

        File file = new File(mImageUploadStates.get(mNowUploadIndex).getImagePath());//得到图片路径

        mNewSurveyModel.uploadImage(file, new OnGetInfoListener<String>() {
            @Override
            public void onSuccess(String info) {
                Message message = mHandler.obtainMessage();
                message.what = IMAGE_SUCCESS;//图片上传成功
                message.obj = info;//信息
                mHandler.sendMessage(message);
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(IMAGE_NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 保存surveyBean
     *
     * @param surveyBean
     */
    private void saveSurvey(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        if (surveyBean != null) {
            surveyBean.setSaveTime();//设置保存时间
            mNewSurveyModel.saveSurveyUnique(surveyBean);//通过id保存一下
        }
    }

    /**
     * 保存图片
     */
    private void saveImg() {

        new Thread(() -> {

            String pointName = mSurveyBean.getPointName();//站点名称

            for (int i = 0; i < mImageUploadStates.size(); i++) {
                ImageUploadState imageState = mImageUploadStates.get(i);//得到第i个
                String fileHeadName = pointName + "_" + NowUserInfo.getUserBean().getName() + "_";
                int state = LocalImageSave.moveImageToAlbum(imageState.getImagePath(), pointName,
                        fileHeadName + imageState.getImageName());//本地存储一下
                if (state == LocalImageSave.STORAGE_CARD_DISABLED) {
                    mHandler.sendEmptyMessage(IMAGE_LOCAL_ERROR);//图片本地存储失败
                    return;
                }
            }
            mHandler.sendEmptyMessage(IMAGE_LOCAL_SUCCESS);//图片本地保存成功
        }).start();

    }


}
