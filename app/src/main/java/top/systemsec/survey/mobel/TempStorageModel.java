package top.systemsec.survey.mobel;

import android.text.TextUtils;
import android.util.Log;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.SurveyBean;

public class TempStorageModel {

    private List<SurveyBean> mSurveyBeans = new ArrayList<>();//查一次就够了不用反复查

    private static final String TAG = "TempStorageModel";

    /**
     * 查找
     *
     * @param keyWord
     * @return
     */
    public List<SurveyBean> searchLocalSurveyBean(String keyWord) {

        if (mSurveyBeans.size() == 0) {//长度为空
            mSurveyBeans = LitePal.where("isUpLoadOk = ? and useId = ?", "0", NowUserInfo.getUserBean().getId() + "").find(SurveyBean.class);//未上传成功的
            Collections.reverse(mSurveyBeans);//倒叙反转一下

            Log.d(TAG, "searchLocalSurveyBean: " + mSurveyBeans);

        }

        if (TextUtils.isEmpty(keyWord))
            return mSurveyBeans;//返回所有
        else {
            List<SurveyBean> surveyBeans = new ArrayList<>();//新建一个集合

            for (SurveyBean surveyBean : mSurveyBeans) {
                if (surveyBean.getPointName().contains(keyWord))
                    surveyBeans.add(surveyBean);//添加
            }

            return surveyBeans;
        }
    }

    /**
     * 刷新数据
     */
    public List<SurveyBean> refreshData(String keyWord) {
        mSurveyBeans.clear();//清除数据
        return searchLocalSurveyBean(keyWord);//搜索
    }

}

