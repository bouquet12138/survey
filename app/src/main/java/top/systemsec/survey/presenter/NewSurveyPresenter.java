package top.systemsec.survey.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.util.List;

import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.mobel.NewSurveyModel;
import top.systemsec.survey.view.INewSurveyView;

public class NewSurveyPresenter extends MVPBasePresenter<INewSurveyView> {
    private static final String TAG = "NewSurveyPresenter";

    private final int STREET_COMPLETE = 0;//街道响应完成

    private final int IMAGE_SUCCESS = 1;//图片上传成功
    private final int IMAGE_NET_ERROR = 2;//网络错误

    private final int ALL_SUCCESS = 3;//所有信息上传成功
    private final int ALL_NET_ERROR = 4;//所有信息上传时网络错误

    private final int TEMP_SUCCESS = 5;//暂存成功
    private final int TEMP_NET_ERROR = 6;//暂存失败

    private SurveyBean mSurveyBean;
    private List<ImageUploadState> mImageUploadStates;//图片上传列表
    private int mNowUploadIndex = 0;//当前上传的索引

    private NewSurveyModel mNewSurveyModel = new NewSurveyModel();//模型

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {

                case STREET_COMPLETE:
                    getView().hideLoading();//隐藏进度条
                    break;
                case IMAGE_SUCCESS:
                    String imageUrl = (String) msg.obj;
                    Log.d(TAG, "handleMessage: " + imageUrl);
                    mImageUploadStates.get(mNowUploadIndex).setImageUrl(imageUrl);//设置图片Url
                    mNowUploadIndex++;
                    if (mNowUploadIndex >= mImageUploadStates.size()) {
                        mSurveyBean.setImgList(mImageUploadStates);//设置图片列表
                        upLoadAllInfo(mSurveyBean);//上传所有信息
                    } else {
                        upLoadImage(mNowUploadIndex);//继续上传
                    }
                    break;
                case IMAGE_NET_ERROR:
                    getView().showToast("网络错误,数据已缓存到本地");
                    mSurveyBean.setImgList(mImageUploadStates);//设置图片列表
                    saveSurvey(mSurveyBean);//存储一下
                    break;
                case ALL_SUCCESS:
                    getView().showToast("数据上传成功");
                    String id = (String) msg.obj;//得到编号
                    mSurveyBean.setNumber(id);//设置编号
                    mSurveyBean.setUpLoadOk(true);//上传成功
                    saveSurvey(mSurveyBean);//存储一下
                    break;
                case ALL_NET_ERROR:
                    getView().showToast("网络错误数据上传失败，已缓存到本地");
                    saveSurvey(mSurveyBean);//存储一下
                    break;
                case TEMP_SUCCESS:
                    String number = (String) msg.obj;//得到编号
                    mSurveyBean.setNumber(number);//设置编号
                    saveSurvey(mSurveyBean);//存储一下
                    break;
                case TEMP_NET_ERROR:
                    getView().showToast("网络错误数据暂存到本地");
                    saveSurvey(mSurveyBean);//存储一下
                    break;
            }
        }
    };

    /**
     * 暂存一下数据
     *
     * @param surveyBean
     */
    public void tempSave(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;

        mSurveyBean = surveyBean;//存储一下
        getView().showLoading("数据暂存中..");

        mNewSurveyModel.uploadSurveyInfo(surveyBean, false, new OnGetInfoListener<String>() {
            @Override
            public void onSuccess(String info) {
                Message message = mHandler.obtainMessage();//获取信息
                message.what = TEMP_SUCCESS;//暂存成功
                message.obj = info;//把信息传过去
                mHandler.sendMessage(message);//发送信息
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(TEMP_NET_ERROR);//空信息
            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 上传所有信息
     */
    private void upLoadAllInfo(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        getView().setLoadingHint("全部数据上传中..");
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
     * 保存surveyBean
     *
     * @param surveyBean
     */
    private void saveSurvey(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        if (surveyBean != null) {

            surveyBean.setSaveTime();//设置保存时间
            mNewSurveyModel.saveSurveyToLocal(surveyBean);//保存一下
            getView().hideLoading();//隐藏进度框
            getView().reStart();//清除数据重新开始
        }
    }

    /**
     * 上传图片
     *
     * @param surveyBean
     */
    public void upLoadImage(SurveyBean surveyBean) {
        if (!isViewAttached())
            return;
        mSurveyBean = surveyBean;//提取到外面
        mImageUploadStates = surveyBean.getImgList();

        mNowUploadIndex = 0;//当前上传索引
        getView().showLoading("图片上传中");
        upLoadImage(0);
    }

    private void upLoadImage(int index) {

        getView().setLoadingHint("上传第" + (index + 1) + "张图片");

        File file = new File(mImageUploadStates.get(index).getImagePath());//得到图片路径
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


    //region 得到街道警局信息 不需要获取了
  /*  public void getStreetAndPoliceInfo() {
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

    }*/
    //endregion
}
