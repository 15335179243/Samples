package com.eyecool.visitor.sys;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sys.interfaces.Constant;
import com.eyecool.visitor.sys.interfaces.OnCaptureFaceListener;
import com.eyecool.visitor.sys.interfaces.OnIDCardReadListener;
import com.eyecool.visitor.sys.utils.BitmapUtil;
import com.eyecool.visitor.sys.utils.CameraUtil;
import com.eyecool.visitor.sys.utils.IDCardUtil;
import com.eyecool.visitor.sys.utils.SsDuckFaceRecognition;
import com.eyecool.visitor.sys.utils.SsNowFaceRecognition;
import com.eyecool.visitor.sys.utils.TimeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements Runnable, OnCaptureFaceListener, OnIDCardReadListener {
    private static final String TAG = "MainActivity";
    @Bind(R.id.surfaceView)
    SurfaceView surfaceView;
    @Bind(R.id.fl_surfaceView_container)
    FrameLayout flSurfaceViewContainer;
    @Bind(R.id.tv_cue1)
    TextView tvCue1;
    @Bind(R.id.tv_cue2)
    TextView tvCue2;
    @Bind(R.id.rl_checkface_root_conatiner)
    RelativeLayout rlCheckfaceRootConatiner;
    @Bind(R.id.tv_Warm)
    TextView tvWarm;
    @Bind(R.id.tv_mainPageTitle)
    TextView tvMainPageTitle;
    @Bind(R.id.rl_main_root_container)
    RelativeLayout rlMainRootContainer;
    @Bind(R.id.rl_checkresut_conatiner)
    RelativeLayout rlCheckresutConatiner;
    //创建 IClientService 实例
    private IClientService mService;
    private List<Bitmap> tempSceneFaceImgs = new ArrayList<>();
    private Bitmap idCardImg = null;
    private static final String SCENE_IMG_DIR_PATH = Environment.getExternalStorageDirectory() + "/Pictures/scene";

    //创建服务连接对象
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IClientService.Stub.asInterface(service);


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ------------------------连接失败！");
        }
    };
    private Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage:--------------------服务端收到信息： " + msg.what);
            switch (msg.what) {

                case Constant.CHANGE_SERVICE_UI_MAINACTIVITY:                                   //收到 Client 端传递过来的消息
                    rlMainRootContainer.setVisibility(View.VISIBLE);
                    tvCue2.setText(getString(R.string.checkface_cue_2));
                    tvCue2.setTextColor(getResources().getColor(R.color.colorWhite));
                    if (pageNum == 3) {
                        rlCheckresutConatiner.removeViewAt(0);
                        rlCheckresutConatiner.setVisibility(View.INVISIBLE);
                        synchronized (Constant.READ_IDCARD_THREAD_LOCK) {
                            Constant.READ_IDCARD_THREAD_LOCK.notify();
                            idCardFaceFeature = null;
                            hasResult = false;
                            score = -1;
                            tempSceneFaceImgs.clear();
                        }
                    }
                    pageNum = 1;
                    CameraUtil.setPageNum(pageNum);
                    break;
                case Constant.CHANGE_SERVICE_UI_CHECK_FACE_ACTIVITY:                           //显示监测人脸界面
                    if (pageNum == 3) {
                        rlCheckresutConatiner.removeViewAt(0);
                        rlCheckresutConatiner.setVisibility(View.INVISIBLE);

                        synchronized (Constant.READ_IDCARD_THREAD_LOCK) {
                            Constant.READ_IDCARD_THREAD_LOCK.notify();
                            idCardFaceFeature = null;
                            hasResult = false;
                        }
                    } else {
                        initCheckFaceUI();
                    }

                    break;
                case Constant.SHOW_RESULT_CODE:                                                   //显示比对结果布局
                    pageNum = 3;
                    CameraUtil.setPageNum(pageNum);
                    rlCheckresutConatiner.setVisibility(View.VISIBLE);
                    //根据访客类型，显示不同布局;
                    switch (msg.arg1) {
                        case Constant.USER_TYPE_STU:
                            showSuccessView(msg, msg.arg1);

                            break;
                        case Constant.USER_TYPE_PARENT:
                            showSuccessView(msg, msg.arg1);

                            break;
                        case Constant.USER_TYPE_TEACHER:
                            showSuccessView(msg, msg.arg1);

                            break;
                        case Constant.USER_TYPE_BLACKLIST:
                            View.inflate(mContext, R.layout.blacklist_layout, rlCheckresutConatiner);
                            tvCue2.setText("");
                            break;
                        case Constant.USER_TYPE_UNKNOWN:                                         //未登记
                            View.inflate(mContext, R.layout.unlogin_layout, rlCheckresutConatiner);
                            tvCue2.setText("");
                            unLogin = true;
                            break;
                        case Constant.RESULT_STATE_REFUSE:
                            ImageView imageView = (ImageView) findViewById(R.id.iv_unlogin);
                            imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.refuse_outer));
                            break;
                    }


                    break;
                case Constant.CHANGE_SERVICE_UI_FINISH:
                    onDestroy();
                    break;
                case Constant.PLEASE_REPLACE_IDCARD_CODE:                                       //通知内屏，或者直接在外屏显示提醒信息
                    tvWarm.setText(getString(R.string.idcard_read_state_replace));
                    tvWarm.setTextColor(Color.rgb(250, 128, 10));
                    break;
                case Constant.READ_ICDARD_ERROR_CODE:
                    tvWarm.setText(getString(R.string.idcard_read_state_error));
                    tvWarm.setTextColor(Color.RED);
                    break;
                case Constant.READ_IDCARD_SUCCESS_CODE:
                    tvWarm.setTextColor(Color.GREEN);
                    //第一次读卡成功就切换到人脸比对的布局中，并且通知内屏跳转到人脸比对界面。如果内屏应用没有开启，就不要更新界面。
                    File fileClient = new File(Environment.getExternalStorageDirectory(), "client_file");
                    if (!fileClient.exists()) {
                    tvWarm.setText(getString(R.string.idcard_read_state_replace));
                        synchronized (Constant.READ_IDCARD_THREAD_LOCK) {
                            Constant.READ_IDCARD_THREAD_LOCK.notify();
                            idCardFaceFeature = null;
                        }
                    } else {
                        tvWarm.setText(getString(R.string.idcard_read_state_ok));
                        if (rlMainRootContainer.getVisibility() == View.VISIBLE) {
                            try {
                                mService.changeClientUI(Constant.CHANGE_CLIENT_UI_CHECK_FACE_ACTIVITY);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            initCheckFaceUI();
                        }
                    }

                    break;

            }
        }
    };
    private boolean unLogin = false;

    /**
     * 展示登记成功界面
     *
     * @param msg
     */
    private void showSuccessView(Message msg, int userType) {
        if (rlCheckresutConatiner.getChildCount() > 0) {
            rlCheckresutConatiner.removeViewAt(0);
        }
        View inflateView = View.inflate(mContext, R.layout.success_layout, rlCheckresutConatiner);
        ImageView ivSuccessImg = (ImageView) inflateView.findViewById(R.id.success_img);
        TextView tvParentInfo = (TextView) inflateView.findViewById(R.id.tv_parent_info);
        TextView tvTeacherInfo = (TextView) inflateView.findViewById(R.id.tv_teacher_info);
        TextView tvStuInfo = (TextView) inflateView.findViewById(R.id.tv_stu_info);
        TextView tvResultState = (TextView) inflateView.findViewById(R.id.tv_result_state);


        CustomerInfo customerInfo = (CustomerInfo) msg.obj;
        ivSuccessImg.setImageBitmap(BitmapFactory.decodeFile(customerInfo.getFeature_img()));


        if (unLogin) {
            //如果之前是未登记状态，显示信息最多一条
            switch (userType) {
                case Constant.USER_TYPE_PARENT:
                    tvParentInfo.setText("家长姓名： " + customerInfo.getName());
                    tvTeacherInfo.setText("");
                    tvStuInfo.setText("");
                    break;
                case Constant.USER_TYPE_STU:
                    tvParentInfo.setText("");
                    tvTeacherInfo.setText("");
                    tvStuInfo.setText("学生信息： " + customerInfo.getVisitor_stu());

                    break;
                case Constant.USER_TYPE_TEACHER:
                    tvParentInfo.setText("");
                    tvStuInfo.setText("");
                    tvTeacherInfo.setText("教师信息： " + customerInfo.getStu_teacher());
                    break;
            }
        } else {
            switch (userType) {
                case Constant.USER_TYPE_PARENT:
                    tvParentInfo.setText("家长姓名： " + customerInfo.getName());
                    tvTeacherInfo.setText("教师信息： " + customerInfo.getStu_teacher() + "," + customerInfo.getPosition());
                    tvStuInfo.setText("学生信息： " + customerInfo.getVisitor_stu() + "," + customerInfo.getVisitor_stu_gender() + "," + customerInfo.getSt_class());
                    break;
                case Constant.USER_TYPE_STU:
                    tvParentInfo.setVisibility(View.GONE);
                    tvTeacherInfo.setText("教师信息： " + customerInfo.getStu_teacher() + "," + customerInfo.getPosition());
                    tvStuInfo.setText("学生信息： " + customerInfo.getVisitor_stu() + "," + customerInfo.getSt_class());
                    tvResultState.setBackgroundResource(R.drawable.success_outer2);
                    break;
                case Constant.USER_TYPE_TEACHER:
                    tvParentInfo.setVisibility(View.GONE);
                    tvStuInfo.setVisibility(View.GONE);
                    tvTeacherInfo.setText("教工信息： " + customerInfo.getStu_teacher()+ "," + customerInfo.getPosition());
                    tvResultState.setBackgroundResource(R.drawable.success_outer2);
                    break;
            }

        }

        unLogin = false;
        tvCue2.setText("");
    }

    private boolean uiIsVisialble = true;
    //当前身份证照片的特征值，注意这个和现场照片比对完特征值后就重新 赋值为 null
    private String idCardFaceFeature = null;
    private int score;
    private int checkFaceCount = 0;
    private boolean hasResult = false;
    private int pageNum = 0;

    private void initCheckFaceUI() {
        pageNum = 2;
        rlMainRootContainer.setVisibility(View.GONE);
        CameraUtil.setCaptureListener(this);
        CameraUtil.setPageNum(pageNum);
    }

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //初始化操作
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        uiIsVisialble = true;
        //外屏界面开启的时候就创建一个文件 service_file

        file = new File(Environment.getExternalStorageDirectory(), "service_file");
        if (!file.exists()) {
            file.mkdir();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        IDCardUtil.openDevice();
    }


    @Override
    protected void onPause() {
        uiIsVisialble = false;
        IDCardUtil.closeDevice();
        if (file.exists()) {

            file.delete();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        if (file.exists()) {
            file.delete();
        }
        System.exit(0);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (rlMainRootContainer.getVisibility() == View.GONE) {
            rlMainRootContainer.setVisibility(View.VISIBLE);
            tvWarm.setText("");
        } else {
            finish();
        }
    }

    private void init() {
        mContext = this;
        //1.绑定服务
        final Intent intent = new Intent();
        intent.setAction(getString(R.string.remote_service_action));
        intent.setPackage(getString(R.string.remote_service_package_name));
        intent.putExtra("messenger", new Messenger(mHandler));
        bindService(intent, connection, BIND_AUTO_CREATE);

        SsDuckFaceRecognition.init(mContext);
        CameraUtil.init(flSurfaceViewContainer, surfaceView, mContext, null, null);
        CameraUtil.setPageNum(pageNum);
        IDCardUtil.setResultListener(this);
        new Thread(this).start();

    }

    @Override
    public void run() {
        while (uiIsVisialble) {

            //如果得不到特征值就要重新读取身份证.
            Log.d(TAG, "run: --------------------------------------------读卡和比对特征值的线程 is running...");
            Log.d(TAG, "run: -----------------------------------tempSceneFaceImgs.size = " + tempSceneFaceImgs.size());
            Log.d(TAG, "run: ------------------------------------------hasResult = " + hasResult);
            Log.d(TAG, "run: ------------------------------------------score = " + score);
            if (idCardFaceFeature == null) {
                switch (IDCardUtil.readIDCardInfo()) {
                    case 1:
                        mHandler.sendEmptyMessage(Constant.READ_IDCARD_SUCCESS_CODE);
                        idCardImg = App.getAppInstance().getmIdCardInfo().getIdCardImg();
                        idCardFaceFeature = SsNowFaceRecognition.getFaceFeature(idCardImg);
                        if (idCardFaceFeature != null) {
                            App.getInstance().setmSceneFeatureValue(idCardFaceFeature);
                        }
                        break;
                    case 2:
                        if (idCardFaceFeature == null) {
                            mHandler.sendEmptyMessage(Constant.PLEASE_REPLACE_IDCARD_CODE);
                        }
                        break;
                    case 3:
                        mHandler.sendEmptyMessage(Constant.READ_ICDARD_ERROR_CODE);
                        break;
                }
            } else {
                if (tempSceneFaceImgs.size() > 0 && !hasResult) {
                    //提取现场照片的特征值，然后比对
                    String tempFaceFeature = SsNowFaceRecognition.getFaceFeature(tempSceneFaceImgs.get(0));
                    if (tempFaceFeature != null) {
                        score = SsNowFaceRecognition.getScore(idCardFaceFeature, tempFaceFeature);
                        if (score < Constant.FACE_MATCH_SCORE_DEFAULT_VALUE) {
                            checkFaceCount++;
                        } else {
                            //存储现场照片到 SD 卡中
                            String sceneImgName = App.getInstance().getmIdCardInfo().getIdcardNumber() + "_" + TimeUtil.getTimeOfDay2() + ".jpg";
                            BitmapUtil.saveBitmap2SD(tempSceneFaceImgs.get(0), SCENE_IMG_DIR_PATH, sceneImgName);
                            //认证比对成功，就发送给 client 端。信息：1.身份证信息;2.现场照片;3.比对分值
                            try {
                                mService.sendFaceCheckSuccessResult(App.getAppInstance().getmIdCardInfo()
                                        , SCENE_IMG_DIR_PATH + "/" + sceneImgName
                                        , score);
                                hasResult = true;
                                checkFaceCount = 0;

                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }

                        }

                        //超过设定次数不通过，就提示重试。并且把结果发送给client端
                        if (checkFaceCount >= Constant.FACE_MATCH_MAX_TIMES) {
                            try {
                                mService.sendFaceCheckError();
                                idCardFaceFeature = null;
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvCue2.setText(getString(R.string.checkface_cue_2_error));
                                    tvCue2.setTextColor(getResources().getColor(R.color.colorRed));

                                }
                            });

                            checkFaceCount = 0;

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        mService.sendFaceScore(score);

                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                    tvCue2.setTextColor(getResources().getColor(R.color.colorWhite));
                                    tvCue2.setText(getString(R.string.checkface_cue_2));
                                }
                            });

                        }
                        Log.d(TAG, "run: ----------------------比对分值：" + score);
                    }
                } else {

                }

            }

            synchronized (Constant.READ_IDCARD_THREAD_LOCK) {
                try {
                    if (hasResult) {

                        Constant.READ_IDCARD_THREAD_LOCK.wait();
                    } else {
                        Constant.READ_IDCARD_THREAD_LOCK.wait(300);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void captureFace(Bitmap faceBitmap, float[] faceRect) {
        Log.d(TAG, "captureFace: ----------------------发现人脸了！");
        tempSceneFaceImgs.add(0, faceBitmap);
        if (tempSceneFaceImgs.size() > 3) {
            tempSceneFaceImgs.remove(3);
        }
        try {
            if (rlMainRootContainer.getVisibility() != View.VISIBLE) {

                mService.sendFaceRect2Service(faceRect[0], faceRect[1], faceRect[2]);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void captureFrame(String videoStr) {
        try {
            Log.d(TAG, "captureFrame: -----------------截图！");
            if (rlMainRootContainer.getVisibility() != View.VISIBLE) {

                mService.sendCameraData2Client(videoStr);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void captureFrameHasCorrect(Bitmap correctBitmap) {
        //如果不画人脸框，内屏应用的视频帧率能达到 15帧，否则6帧左右。
        App.getInstance().setCurrentCaptureFaceImg(correctBitmap);

    }

    @Override
    public void readSuccess(IDCardInfo info) {

    }

    @Override
    public void replaceIDCard() {

    }

    @Override
    public void readError() {

    }
}
