package top.systemsec.survey.view;

import java.util.List;

import top.systemsec.survey.base.IMVPBaseView;
import top.systemsec.survey.bean.SurveyBean;

public interface ITempStorageView extends IMVPBaseView {

    /**
     * 得到用户要搜索的信息
     *
     * @return
     */
    String getSearchInfo();

    /**
     * 设置搜索结果
     */
    void setSearchResult(List<SurveyBean> surveyBeans);

}
