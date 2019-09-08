package com.eyecool.visitor.sysinner;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.util.Log;

import com.eyecool.visitor.sys.IClientService;
import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitor.sysinner.interfaces.OnRemoteServiceConnectedListener;
import com.eyecool.visitor.sysinner.utils.DataBaseUtil;

/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public class App extends Application {
    private static final String TAG = "App";
    public static App appInstance;
    public IClientService clientService;
    private CustomerInfo mCustomerInfo;
    private IDCardInfo mIdCardInfo;
    private Bitmap mSceneImg;
    private Bitmap mIdCardImg;
    private boolean remoteServiceIsOpen = false;

    private OnRemoteServiceConnectedListener connectedListener;


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            clientService = IClientService.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: -------------客户端应用绑定了远程服务！");
            Log.d(TAG, "onServiceConnected: ------------------clientService = " + (clientService == null));
            if (connectedListener != null){
                connectedListener.connectSuccess();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: -------------------------客户端应用远程绑定失败！");
            if (connectedListener != null){
                connectedListener.connectSuccess();
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = (App) this.getApplicationContext();

        //初始化数据库,并添加一条模拟信息
        DataBaseUtil.initDataBase(Constants.DATA_BASE_NAME, appInstance);
//        DataBaseUtil.generateOneCustomerInfo();

        //绑定远程服务
        final Intent intent = new Intent();
        intent.setAction(getString(R.string.remote_service_action));
        intent.setPackage(getString(R.string.remote_service_package_name));
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unbindService(connection);
    }

    public static App getInstance(){

        return appInstance;
    }

    public boolean getRemoteServiceIsOpen() {
        return remoteServiceIsOpen;
    }

    public void setRemoteServiceIsOpen(boolean remoteServiceIsOpen) {
        this.remoteServiceIsOpen = remoteServiceIsOpen;
    }

    public IClientService getClientService(){
        return this.clientService;
    }

    public void setOnRemoteConnectListener(OnRemoteServiceConnectedListener listener){
        this.connectedListener = listener;
    }


    public CustomerInfo getmCustomerInfo() {
        return mCustomerInfo;
    }

    public void setmCustomerInfo(CustomerInfo mCustomerInfo) {
        this.mCustomerInfo = mCustomerInfo;
    }

    public Bitmap getmSceneImg() {
        return mSceneImg;
    }

    public void setmSceneImg(Bitmap mSceneImg) {
        this.mSceneImg = mSceneImg;
    }

    public Bitmap getmIdCardImg() {
        return mIdCardImg;
    }

    public void setmIdCardImg(Bitmap mIdCardImg) {
        this.mIdCardImg = mIdCardImg;
    }

    public IDCardInfo getmIdCardInfo() {
        return mIdCardInfo;
    }

    public void setmIdCardInfo(IDCardInfo mIdCardInfo) {
        this.mIdCardInfo = mIdCardInfo;
    }
}
