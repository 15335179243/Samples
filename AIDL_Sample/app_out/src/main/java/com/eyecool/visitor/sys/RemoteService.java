package com.eyecool.visitor.sys;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sys.interfaces.Constant;
import com.eyecool.visitor.sys.utils.MyLock;

/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public class RemoteService extends Service {
    private static final String TAG = "RemoteService";

    //该服务同时给 本应用 和 内屏 使用
    private PigonService mPigonService = new PigonService();
    //外屏绑定此服务是，传递过来的 handler 实例用这个变量接受
    private Messenger messenger;

    //通知 client 端的回调
    private final RemoteCallbackList<IRemoteCallback> remoteCallbackList = new RemoteCallbackList<>();
    private int mRegisteredCallbackCount;
    private MyLock lock = new MyLock();


    /**
     * 通知 client 端，远程服务绑定成功与否
     *
     * @param success
     */
    private synchronized void notifyClientRemoteIsSuccess(boolean success) {

        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).remoteServiceIsOpen(success);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    /**
     * 给客户端发送摄像头数据
     *
     * @param cameraData
     */
    private synchronized void sendCameraData2Client2(String cameraData) {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).sendCameraData2Client(cameraData);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private synchronized void sendFaceRectData2Client(float[] faceRect) {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).sendFaceRect2Service(faceRect[0], faceRect[1], faceRect[2]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private synchronized void changeClientUI2(int uiID) {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                Log.d(TAG, "changeClientUI: --------------------内屏跳转-2！");
                remoteCallbackList.getBroadcastItem(i).changeUI(uiID);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private synchronized void sendFaceScore2Client(int score) {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).receiveFaceScore(score);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private synchronized void sendFaceCheckError2Client() {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).receiveFaceCheckError();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    private synchronized void sendFaceCheckSuccessResult2Client(IDCardInfo idCardInfo, String sceneImgPath, int matchScore) {
        int len = remoteCallbackList.beginBroadcast();
        for (int i = 0; i < len; i++) {
            try {
                remoteCallbackList.getBroadcastItem(i).receiveFaceCheckSuccessResult(idCardInfo, sceneImgPath, matchScore);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        remoteCallbackList.finishBroadcast();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //获取绑定服务是传递的 messenger
        messenger = intent.getParcelableExtra("messenger");
        return mPigonService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        remoteCallbackList.kill();
    }

    private static class CallbackCookie {
        boolean isConnected;
    }

    //自定义内部服务类
    private class PigonService extends IClientService.Stub {
        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            boolean b = false;
            try {
                b = super.onTransact(code, data, reply, flags);
                Log.d(TAG, "onTransact: ------------------b = " + b);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return true;
        }

        /*@Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            try {
                super.onTransact(code, data, reply, flags);
            } catch (RuntimeException e){
                e.printStackTrace();
            }
            return super.onTransact(code, data, reply, flags);
//            return true;
        }*/

        @Override
        public void changeServiceUI(int uiId) throws RemoteException {
            //在这里收到 client 端传递来的信息，然后用 messenger 发送给 activity 中的 handler
            Message message = Message.obtain();
            message.obj = uiId;
            Log.d(TAG, "changeServiceUI: --------------------改变 UI！");
            switch (uiId) {

                case Constant.CHANGE_SERVICE_UI_FINISH:
                    message.what = Constant.CHANGE_SERVICE_UI_FINISH;
                    break;
                case Constant.CHANGE_SERVICE_UI_MAINACTIVITY:
                    message.what = Constant.CHANGE_SERVICE_UI_MAINACTIVITY;
                    break;
                case Constant.CHANGE_SERVICE_UI_CHECK_FACE_ACTIVITY:
                    message.what = Constant.CHANGE_SERVICE_UI_CHECK_FACE_ACTIVITY;
                    break;
                case Constant.CHANGE_SERVICE_UI_CHECK_RESULT_ACTIVITY:
                    message.what = Constant.CHANGE_SERVICE_UI_CHECK_RESULT_ACTIVITY;
                    break;
            }
            messenger.send(message);
        }

        @Override
        public void registerRemoteCallback(IRemoteCallback callback) throws RemoteException {
            remoteCallbackList.register(callback, new CallbackCookie());
            mRegisteredCallbackCount++;
        }

        @Override
        public boolean unregisterRemoteCallback(IRemoteCallback callback) throws RemoteException {
            remoteCallbackList.unregister(callback);
            mRegisteredCallbackCount--;
            if (mRegisteredCallbackCount < 0) {
                mRegisteredCallbackCount = 0;
            }
            return mRegisteredCallbackCount == 0;
        }

        @Override
        public void notifyClientServiceUIIsOpen(boolean isOpen) throws RemoteException {
            notifyClientRemoteIsSuccess(isOpen);
        }

        @Override
        public void showResult(CustomerInfo customerInfo, int userType) throws RemoteException {
            //内屏传递过来的展示数据
            Message message = Message.obtain();
            message.arg1 = userType;
            message.obj = customerInfo;
            message.what = Constant.SHOW_RESULT_CODE;
            messenger.send(message);
        }

        @Override
        public void sendCameraData2Client(String cameraData) throws RemoteException {
            sendCameraData2Client2(cameraData);
        }

        @Override
        public void sendFaceRect2Service(float faceX, float faceY, float length) throws RemoteException {
            sendFaceRectData2Client(new float[]{faceX, faceY, length});
        }

        @Override
        public void changeClientUI(int uiID) throws RemoteException {
            Log.d(TAG, "changeClientUI: --------------------内屏跳转！-1");
            changeClientUI2(uiID);
        }

        @Override
        public void sendFaceScore(int score) throws RemoteException {
            //这个远程的被 client 端绑定的服务，收到人脸比对分值后用 返回给 client 端回调对象发送出去
            sendFaceScore2Client(score);
            Log.d(TAG, "sendFaceScore: ---------------------比分：" + score);
        }

        @Override
        public void sendFaceCheckError() throws RemoteException {
            sendFaceCheckError2Client();
            Log.d(TAG, "sendFaceCheckError: --------------------认证失败！");
        }

        @Override
        public void sendFaceCheckSuccessResult(IDCardInfo idCardInfo, String sceneImgPath, int matchScore) throws RemoteException {
            sendFaceCheckSuccessResult2Client(idCardInfo, sceneImgPath, matchScore);
            Log.d(TAG, "sendFaceCheckSuccessResult: ----------------------认证成功！");

        }
    }

}
