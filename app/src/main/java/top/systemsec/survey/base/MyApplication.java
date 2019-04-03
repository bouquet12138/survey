package top.systemsec.survey.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by xiaohan on 2017/11/16.
 */

public class MyApplication extends Application{

    private static Context context;

    //当创建时
    @Override
    public void onCreate() {
       super.onCreate();
       context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }

}
