package top.systemsec.survey.view;

import android.content.res.Resources;

import top.systemsec.survey.base.IMVPBaseView;

public interface INewSurveyView extends IMVPBaseView {

    /**
     * 初始化街道
     *
     * @param streets
     */
    void initStreet(String[] streets);

    /**
     * 初始化街道
     *
     * @param polices
     */
    void initPolice(String[] polices);

    /**
     * 得到源
     *
     * @return
     */
    Resources getResources();

    /**
     * 得到站点名称
     *
     * @return
     */
    String getPointName();

    /**
     * 重新启动当前activity
     */
    void reStart();

}
