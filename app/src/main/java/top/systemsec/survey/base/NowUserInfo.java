package top.systemsec.survey.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import top.systemsec.survey.bean.UserBean;

public class NowUserInfo {

    private static UserBean sUserBean;//当前用户
    private static final String TAG = "NowUserInfo";

    /**
     * 得到当前用户
     *
     * @return
     */
    public static UserBean getUserBean() {

        if (sUserBean == null) {//内存被回收了 或者怎样
            SharedPreferences sharedPreferences = MyApplication.getContext().
                    getSharedPreferences("nowUser", Context.MODE_PRIVATE);
            String nowUserInfo = sharedPreferences.getString("nowUserInfo", "");
            Log.d(TAG, "getUserBean: " + nowUserInfo);
            if (!TextUtils.isEmpty(nowUserInfo)) {
                Gson gson = new Gson();
                try {
                    sUserBean = gson.fromJson(nowUserInfo, UserBean.class);//解析出
                } catch (JsonSyntaxException e) {
                }
            }
        }

        return sUserBean;
    }

    /**
     * 设置当前用户
     *
     * @param userBean
     */
    public static void setUserBean(UserBean userBean) {

        sUserBean = userBean;//应该在上面

        SharedPreferences sharedPreferences = MyApplication.getContext().
                getSharedPreferences("nowUser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nowUserInfo", new Gson().toJson(sUserBean));

        editor.apply();//应用一下
    }
}
