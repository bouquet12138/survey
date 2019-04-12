package top.systemsec.survey.presenter;

import java.util.List;

import top.systemsec.survey.base.MVPBasePresenter;
import top.systemsec.survey.bean.SurveyBean;
import top.systemsec.survey.mobel.TempStorageModel;
import top.systemsec.survey.view.ITempStorageView;

public class TempStoragePresenter extends MVPBasePresenter<ITempStorageView> {

    private TempStorageModel mTempStorageModel = new TempStorageModel();//新建一个模型

    /**
     * 搜索
     */
    public void search() {
        if (!isViewAttached())//没有view绑定
            return;
        String keyWord = getView().getSearchInfo();//得到用户搜索信息
        List<SurveyBean> surveyBeans = mTempStorageModel.searchLocalSurveyBean(keyWord);//搜索一下
        if (surveyBeans == null || surveyBeans.size() == 0)
            getView().showNoData(true);//展示没数据
        else {
            getView().showNoData(false);//隐藏
        }
        getView().setSearchResult(surveyBeans);//设置搜索到的信息
    }

    /**
     * 刷新一下数据
     */
    public void refresh() {
        if (!isViewAttached())//没有view绑定
            return;
        String keyWord = getView().getSearchInfo();//得到用户搜索信息
        List<SurveyBean> surveyBeans = mTempStorageModel.refreshData(keyWord);//搜索一下
        if (surveyBeans == null || surveyBeans.size() == 0)
            getView().showNoData(true);//展示没数据
        else {
            getView().showNoData(false);//隐藏
        }
        getView().setSearchResult(surveyBeans);//设置搜索到的信息
    }

}
