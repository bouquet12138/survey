package top.systemsec.survey.mobel;

import android.text.TextUtils;
import android.util.Log;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import top.systemsec.survey.base.NowUserInfo;
import top.systemsec.survey.bean.SurveyBean;

public class SiteManageModel {


    private List<SurveyBean> mSurveyBeans = new ArrayList<>();//查一次就够了不用反复查

    private static final String TAG = "SiteManageModel";

    /**
     * 查找站点管理 按时间倒叙
     *
     * @param keyWord
     * @return
     */
    public List<SurveyBean> searchLocalSurveyBean(String keyWord) {

        if (mSurveyBeans.size() == 0) {//长度为空
            mSurveyBeans = LitePal.where("isUpLoadOk = ? and useId = ?", "1", NowUserInfo.getUserBean().getId() + "").find(SurveyBean.class);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Collections.sort(mSurveyBeans, (SurveyBean o1, SurveyBean o2) -> {
                        try {
                            Date date1 = simpleDateFormat.parse(o1.getSubmitTime());
                            Date date2 = simpleDateFormat.parse(o1.getSubmitTime());

                            if (date1.before(date2))
                                return 1;
                            else
                                return -1;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
            );

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

}
