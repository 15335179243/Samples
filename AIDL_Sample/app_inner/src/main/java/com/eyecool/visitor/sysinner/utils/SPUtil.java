package com.eyecool.visitor.sysinner.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created date: 2017/6/15
 * Author:  Leslie
 * 操作 sp 文件的工具类
 */

public class SPUtil {

    /**
     * 保存数据到 SP 文件中
     * @param context
     * @param spFileName
     * @param key
     * @param value
     */
    public static void saveDataAsSP(Context context, String spFileName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spFileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 从本地的sp文件中获取数据（根据key）
     *
     * @param context
     * @param spFileName
     * @param key
     * @return 如果没有值，返回null
     */
    public static String getDataFromSP(Context context, String spFileName, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(spFileName, MODE_PRIVATE);
        if (sharedPreferences != null) {
            String value = sharedPreferences.getString(key, null);
            return value;
        }
        return null;
    }

}
