package top.systemsec.survey.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaohan on 2017/11/22.
 */

public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}