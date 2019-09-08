package com.eyecool.visitor.sysinner.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public class SysUtil {

    /**
     *
     * @param context
     * @param packageName
     * @return
     *
     * 注意：需要配置权限<uses-permission android:name="android.permission.GET_TASKS" />
     */
    public static int getAppStatus(Context context,String packageName){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(20);

        //判断程序是否在栈顶
        if (list.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {
            //判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : list) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            return 3;//栈里找不到，返回3
        }

    }
}
