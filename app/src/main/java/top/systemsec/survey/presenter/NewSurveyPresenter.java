package top.systemsec.survey.presenter;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.base.OnGetInfoListener;
import top.systemsec.survey.bean.StreetAndPolice;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.mobel.NewSurveyModel;
import top.systemsec.survey.view.INewSurveyView;

public class NewSurveyPresenter extends MVPBasePresenter<INewSurveyView> {

    private final int SUCCESS = 0;//成功
    private final int NET_ERROR = 1;//网络错误
    private final int COMPLETE = 2;//响应完成

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (!isViewAttached())
                return;
            switch (msg.what) {
                case SUCCESS:
                    StreetAndPolice streetAndPolice = (StreetAndPolice) msg.obj;
                    String[] streets = streetAndPolice.getStreetList();//街道
                    String[] polices = streetAndPolice.getPoliceList();//警局

                    getView().initStreet(streets);//初始化街道
                    getView().initPolice(polices);//初始化警局
                    break;
                case NET_ERROR:
                    getView().showToast("网络错误");
                    String[] streets1 = getView().getResources().getStringArray(R.array.streets);//从本地读取街道
                    String[] polices1 = getView().getResources().getStringArray(R.array.polices);//从本地读取警局

                    getView().initStreet(streets1);//初始化街道
                    getView().initPolice(polices1);//初始化警局
                    break;
                case COMPLETE:
                    getView().hideLoading();//隐藏进度条
                    break;
            }
        }
    };

    private NewSurveyModel mNewSurveyModel = new NewSurveyModel();//模型

    /**
     * 保存surveyBean
     *
     * @param surveyBean
     */
    public void saveSurvey(SurveyBean surveyBean) {
        if (surveyBean != null) {
            mNewSurveyModel.saveSurvey(surveyBean);//保存一下
            getView().showToast("数据保存成功");//数据保存成功
        }
    }

    /**
     * 得到街道警局信息
     */
    public void getStreetAndPoliceInfo() {
        if (!isViewAttached())//没有view绑定
            return;

        //getView().showLoading("数据加载中");

        mNewSurveyModel.getStreetAndPolice(new OnGetInfoListener<StreetAndPolice>() {
            @Override
            public void onSuccess(StreetAndPolice info) {
                Message message = mHandler.obtainMessage();//获取信息
                message.what = SUCCESS;//成功
                message.obj = info;
                mHandler.sendMessage(message);//发送信息
            }

            @Override
            public void onFail() {
                mHandler.sendEmptyMessage(NET_ERROR);//网络错误
            }

            @Override
            public void onComplete() {
                mHandler.sendEmptyMessage(COMPLETE);//响应完成
            }
        });

    }

}
