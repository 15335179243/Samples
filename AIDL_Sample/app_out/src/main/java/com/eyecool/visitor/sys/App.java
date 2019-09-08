package com.eyecool.visitor.sys;

import android.app.Application;
import android.graphics.Bitmap;

import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sys.utils.SsNowFaceRecognition;
import com.eyecool.visitor.sys.bean.CustomerInfo;

import cn.com.aratek.idcard.IDCard;
import cn.com.aratek.idcard.IDCardReader;

/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public class App extends Application {
    private static final String TAG = "App";
    public static App appInstance;
    private Bitmap currentCaptureFaceImg;
    private IDCardReader idCardReader;
    private IDCardInfo mIdCardInfo;
    private  IDCard currentIdCard;
    private  Bitmap mSceneImg;
    private CustomerInfo mCustomerInfo;
    private  String mSceneFeatureValue;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = (App) this.getApplicationContext();
        idCardReader = IDCardReader.getInstance();
        SsNowFaceRecognition.init(appInstance);

    }


    public static App getInstance(){

        return appInstance;
    }

    public Bitmap getCurrentCaptureFaceImg() {
        return currentCaptureFaceImg;
    }

    public void setCurrentCaptureFaceImg(Bitmap currentCaptureFaceImg) {
        this.currentCaptureFaceImg = currentCaptureFaceImg;
    }

    public IDCardReader getIdCardReader() {
        return idCardReader;
    }

    public void setIdCardReader(IDCardReader idCardReader) {
        this.idCardReader = idCardReader;
    }

    public static App getAppInstance() {
        return appInstance;
    }

    public static void setAppInstance(App appInstance) {
        App.appInstance = appInstance;
    }

    public IDCardInfo getmIdCardInfo() {
        return mIdCardInfo;
    }

    public void setmIdCardInfo(IDCardInfo mIdCardInfo) {
        this.mIdCardInfo = mIdCardInfo;
    }

    public IDCard getCurrentIdCard() {
        return currentIdCard;
    }

    public void setCurrentIdCard(IDCard currentIdCard) {
        this.currentIdCard = currentIdCard;
    }

    public Bitmap getmSceneImg() {
        return mSceneImg;
    }

    public void setmSceneImg(Bitmap mSceneImg) {
        this.mSceneImg = mSceneImg;
    }

    public CustomerInfo getmCustomerInfo() {
        return mCustomerInfo;
    }

    public void setmCustomerInfo(CustomerInfo mCustomerInfo) {
        this.mCustomerInfo = mCustomerInfo;
    }

    public String getmSceneFeatureValue() {
        return mSceneFeatureValue;
    }

    public void setmSceneFeatureValue(String mSceneFeatureValue) {
        this.mSceneFeatureValue = mSceneFeatureValue;
    }
}
