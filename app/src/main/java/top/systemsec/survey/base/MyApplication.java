package top.systemsec.survey.base;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

import top.systemsec.survey.utils.CrashHandler;

/**
 * Created by xiaohan on 2017/11/16.
 */

public class MyApplication extends Application {

    private static Context context;

    //当创建时
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePal.initialize(this);//初始化
        CrashHandler.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }

}
