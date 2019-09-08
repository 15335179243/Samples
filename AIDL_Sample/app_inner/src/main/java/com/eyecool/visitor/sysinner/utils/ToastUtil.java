package com.eyecool.visitor.sysinner.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Leslie on 2016/11/14 0014.
 * <p>
 * ------日期------维护人-----变更内容
 * 2016/11/14 0014    Leslie      变更内容
 */

public class ToastUtil {

    private static Toast toast;

    /**
     * 单例吐司方法
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);        //如果不为空，直接改变toast当前的文本
        }
        toast.show();
    }
}
