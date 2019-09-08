package com.eyecool.visitor.sys.interfaces;

import com.eyecool.visitor.sys.utils.MyLock;

/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public interface Constant {


    //监听是否读取到二代身份证的线程锁对象
    MyLock READ_IDCARD_THREAD_LOCK = new MyLock();

    int CHANGE_SERVICE_UI_FINISH = 0;
    int CHANGE_SERVICE_UI_MAINACTIVITY = 1;
    int CHANGE_SERVICE_UI_CHECK_FACE_ACTIVITY = 2;
    int CHANGE_CLIENT_UI_CHECK_FACE_ACTIVITY = 2;
    int CHANGE_SERVICE_UI_CHECK_RESULT_ACTIVITY = 3;

    int SHOW_RESULT_CODE = 4;





    //请重新放置身份证
    int READ_IDCARD_SUCCESS_CODE = 1000;
    int PLEASE_REPLACE_IDCARD_CODE = 1001;

    //读身份证失败
    int READ_ICDARD_ERROR_CODE = 1002;

    //身份证和现场的人是一个人
    int IDCARD_SCENEFACE_OK_CODE = 1003 ;

    //身份证和现场的人不是一个人
    int IDCARD_SCENEFACE_ERROR_CODE = 1004;

    //读取身份证的 3 种情况
    int READ_IDCARD_STATE_OK = 1;
    int READ_IDCARD_STATE_REPLACE = 2;
    int READ_IDCARD_STATE_ERROR = 3;

    int CAMERA_BACK_ID = 0;
    int CAMERA_FRONT_ID = 1;

    //截取人脸图片缓存个数
    int CACHE_CAPTURE_FACE_COUNT = 3;

    float CAPTURE_FACE_ZOOM_TIMES = 3F;

    //人脸比对分默认值
    int FACE_MATCH_SCORE_DEFAULT_VALUE = 80;

    //最大比对次数
    int FACE_MATCH_MAX_TIMES = 5;

    //以下是比对结果的 6 种结果

    int RESULT_STATE_LOGIN_OK = 1;
    int RESULT_STATE_UNBOOK = 2;
    int RESULT_STATE_UNLOGIN = 3;
    int RESULT_STATE_BLACK_LIST = 4;
    int RESULT_STATE_TEACHER_IO = 5;
    int RESULT_STATE_STU_IO = 6;
    int RESULT_STATE_REFUSE = 7;

    //未登记，即数据库中查不到此人
    int RESULT_STATE7 = 7;

    //访客类型
    int USER_TYPE_STU = 11;
    int USER_TYPE_PARENT = 22;
    int USER_TYPE_TEACHER = 33;
    int USER_TYPE_BLACKLIST = 44;
    int USER_TYPE_UNKNOWN = 55;

}
