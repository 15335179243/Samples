// IRemoteCallback.aidl
package com.eyecool.visitor.sys;

// Declare any non-default types here with import statements
import com.eyecool.visitor.sys.bean.IDCardInfo;
interface IRemoteCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    /* void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
                double aDouble, String aString);*/
    boolean remoteServiceIsOpen(boolean isOpen);

    void sendCameraData2Client(String cameraData);

    void sendFaceRect2Service(float faceX,float faceY,float length);

    void changeUI(int uiID);

    void receiveFaceScore(int score);

    void receiveFaceCheckError();

    void receiveFaceCheckSuccessResult(inout IDCardInfo idCardInfo,String sceneImgPath,int matchScore);
}
