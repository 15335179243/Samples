package com.eyecool.visitor.sys.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.eyecool.visitor.sys.view.FourAnglesFrameView;
import com.smartshino.face.SsNow;
import com.smartshino.face.Stfaceattr;
import com.techshino.facerecognize.algorithm.FaceRecognize;
import com.techshino.facerecognize.interfaces.FaceInterface;

/**
 * Created date: 2017/4/28
 * Author:  Leslie
 * 人脸识别的工具类，基于SsNow
 * 该类使用前提：1.module下的libs中放入arm54-v8a,armeabi,armeabi-v7a,mips,mips64等架构的libSsNow.so文件(单独使用算法.so时)
 *                如果使用了别的.so文件，如海康sdk的.so文件，那就把海康的.so文件和SsNow算法的.so文件都放到jniLibs/armeabi-v7a下面
 *              2.需要jar包：tesofacelib-camera.jar
 *              3./src/main/assets/下面放入Face.dat
 *              4.module下的build.gradle中android节点下的defaultConfig节点里面新开一个ndk节点。加入这句话ndk { abiFilters "armeabi-v7a" }
 * 使用步骤：1.SsNowFaceRecognition.init()
 *          2.SsNowFaceRecognition.drawFaceFrame(flContainer, ivTest, thumbBitmap);
 */

public class SsNowFaceRecognition {

    private static final String TAG = "SsNowFaceRecognition";

    private static FaceRecognize faceRecognize;
    private static long[] multithreadSupport = new long[2];
    private static SsNow ssNow;
    private static Stfaceattr stfaceattr;
    public static int[] hFattr;
    private static Context faceContext;
    private static FourAnglesFrameView rectView;

    /**
     * 人脸识别初始化，这个调用一次就行了
     *
     * @param context
     */
    public static void init(Context context) {
        faceContext = context;
        //初始化人脸识别对象
        if (faceRecognize == null) {
            faceRecognize = FaceRecognize.getInstance();
        }
        try {
            long time0 = System.currentTimeMillis();
            //这步耗时在400ms左右
            faceRecognize.TESO_Init(context, multithreadSupport);
            long time1 = System.currentTimeMillis();
            Log.d(TAG, "initFaceRecognize: ----------------TESO_Init 耗用时间 = " + (time1 - time0));

            //获取算法
            ssNow = FaceRecognize.getInstance().getFaceRecognizeAlgorithm().getAlgorithm();
            long time2 = System.currentTimeMillis();
            Log.d(TAG, "initFaceRecognize: ----------------SsNow初始化耗用时间 = " + (time2 - time1));

            //处理图片，得到关键点信息
            if (stfaceattr == null) {
                stfaceattr = new Stfaceattr();
            }

        } catch (FaceInterface.FaceException e) {
            e.printStackTrace();
        }
    }

    /**
     * 画人脸框
     * @param parentView
     * @param targetView
     * @param bitmap
     */
    public static boolean drawFaceFrame(ViewGroup parentView, View targetView, Bitmap bitmap) {
        if (parentView.getChildCount() > 2) {
            if (rectView != null) {
                parentView.removeViewAt(2);
            }
        }
        //换照片时，要加上这句话才能重新识别
        hFattr = stfaceattr.gethFattr();

        //处理图片，从图片中获取人脸坐标
        long time1 = System.currentTimeMillis();
        int[] coordinateFromBitmap = getCoordinateFromBitmap(bitmap);
        long time2 = System.currentTimeMillis();
        if (coordinateFromBitmap == null) {
        Log.d(TAG, "drawFaceFrame: --------------------获取人脸坐标失败耗时：" + (time2 - time1));
            return false;
        }
        Log.d(TAG, "drawFaceFrame: --------------------获取人脸坐标耗时：" + (time2 - time1));


        //根据关键点信息，把框加到照片上去

        //2.计算出人脸框左上角的坐标
        //计算控件显示图片和原图片的横向和竖向比例

        float ratioHorizontal = (float) targetView.getWidth() / bitmap.getWidth();
        float ratioPortrait = (float) targetView.getHeight() / bitmap.getHeight();
        //3.计算人脸框左上角坐标，这个是相对于父控件的坐标，不是相对于窗口或者屏幕的坐标。使用相对于父控件的坐标好(用parentView.addView(rectView,1)方式时)
        float faceStartPX = 0 + coordinateFromBitmap[0] * ratioHorizontal;
        float faceStartPY = 0 + coordinateFromBitmap[1] * ratioPortrait;

        //3.计算人脸框右下角坐标

        float faceEndPX = faceStartPX + coordinateFromBitmap[2] * ratioHorizontal;
        float faceEndPY = faceStartPY + coordinateFromBitmap[2] * ratioPortrait;

        float lineLength = (faceEndPX - faceStartPX) * 0.2f;
        rectView = new FourAnglesFrameView(faceContext,faceStartPX, faceStartPY, faceEndPX, faceEndPY,lineLength);
        parentView.addView(rectView,2);
        return true;
    }

    /**
     * 从位图对象中获取人脸坐标
     *
     * @param faceBitmap
     * @return
     */
    private static int[] getCoordinateFromBitmap(Bitmap faceBitmap) {
        int[] coordinate = new int[4];
        if (ssNow.Discover(hFattr, faceBitmap, 0, 0) >= 0) {
            for (int i = 0; i < hFattr.length; i++) {
//                Log.d(TAG, "getCoordinateFromBitmap: ---------hFattr-" + i + " = " + hFattr[i]);
                if (i > 0 && i < 5) {
                    coordinate[i - 1] = hFattr[i];
                    Log.d(TAG, "getCoordinateFromBitmap: --------------coordinate-" + (i - 1) + " = " + hFattr[i]);
                }
            }
            return coordinate;
        } else {
//            ToastUtil.showToast(faceContext, "无法识别！");
            return null;
        }
    }

    /**
     * 获取人脸图片特征值
     * @param faceBitmap
     * @return
     */
    public static String getFaceFeature(Bitmap faceBitmap){
        try {
            String faceFeature = faceRecognize.TESO_GetFaceFeature(faceContext, faceBitmap, multithreadSupport[1]);
            return faceFeature;
        } catch (FaceInterface.FaceException e) {
            Log.d(TAG, "getFaceFeature: ---------------FaceInterface.FaceException = " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 比较特征值
     * @param feature1
     * @param feature2
     * @return
     */
    public static int getScore(String feature1,String feature2){
        try {
            int score = faceRecognize.TESO_CompareFeature(faceContext, feature1, feature2, multithreadSupport[1]);
            return score;
        } catch (FaceInterface.FaceException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
