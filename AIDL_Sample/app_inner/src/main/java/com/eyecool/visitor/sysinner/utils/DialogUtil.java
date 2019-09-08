package com.eyecool.visitor.sysinner.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created date: 2017/7/4
 * Author:  Leslie
 */

public class DialogUtil {

    public static void showMultiChoiceDialog(Context context, final String[] choicesStr,String title){
        final ArrayList<Integer> choices = new ArrayList<>();
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < choices.size(); i++) {
                            Log.d(TAG, "onClick: -------------------你喜欢：" + choicesStr[choices.get(i)]);
                        }
                    }
                })
                .setMultiChoiceItems(choicesStr, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            choices.add(which);
                        } else {
                            choices.remove(which);
                        }
                    }
                }).create();
        dialog.show();
    }
}
