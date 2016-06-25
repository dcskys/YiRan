package com.dc.androidtool.utils.control;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器  ，随时随地退出程序 ,通常运用在BaseActivity
 */
public class ActivityCollector {

    //创建一个活动集合
    public static List<Activity> activities = new ArrayList<Activity>();


    public static void addActivity(Activity activity){

        activities.add(activity);
    }


    public static void removeActivity(Activity activity){
        activities.remove(activity);

    }

    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }

    }

}
