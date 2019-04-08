package top.systemsec.survey.presenter;

import android.widget.Toast;

import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.mobel.NewSurveyModel;
import top.systemsec.survey.view.INewSurveyView;

public class NewSurveyPresenter extends MVPBasePresenter<INewSurveyView> {

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

}
