package top.systemsec.survey.mobel;

import android.widget.Toast;

import top.systemsec.survey.bean.SurveyBean;

public class NewSurveyModel {

    /**
     * 保存勘察
     */
    public void saveSurvey(SurveyBean surveyBean) {
        if (surveyBean != null) {
            surveyBean.save();//保存surveyBean
        }
    }

}
