// IClientService.aidl
package com.eyecool.visitor.sys;

// Declare any non-default types here with import statements
import com.eyecool.visitor.sys.IRemoteCallback;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sys.bean.CustomerInfo;
interface IClientService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    /* void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble, String aString);*/
    void changeServiceUI(int uiId);

    void registerRemoteCallback(IRemoteCallback callback);      //注册远程服务回调

    boolean unregisterRemoteCallback(IRemoteCallback callback);     //取消远程服务接口

    void notifyClientServiceUIIsOpen(boolean isOpen);

    void showResult(inout CustomerInfo customerInfo,int userType);

     /////////////////////////////////////////给 client 端发送数据，实际上是发送到了 client 端绑定的 service 端应用的服务那里

     void sendCameraData2Client(String cameraData);

     void sendFaceRect2Service(float faceX,float faceY,float length);

     void changeClientUI(int uiID);

     void sendFaceScore(int score);

     void sendFaceCheckError();

     void sendFaceCheckSuccessResult(inout IDCardInfo idCardInfo,String sceneImgPath,int matchScore);
}
