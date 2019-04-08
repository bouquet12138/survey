package top.systemsec.survey.mobel;

import android.text.TextUtils;

import org.litepal.LitePal;

import java.util.List;

import top.systemsec.survey.bean.SurveyBean;

public class TempStorageModel {

    /**
     * 查找
     *
     * @param keyWord
     * @return
     */
    public List<SurveyBean> searchLocalSurveyBean(String keyWord) {

        if (TextUtils.isEmpty(keyWord))
            return LitePal.findAll(SurveyBean.class);//返回所有
        else {
            return LitePal.where("pointName like ?", keyWord + "%").find(SurveyBean.class);
        }
    }

}
