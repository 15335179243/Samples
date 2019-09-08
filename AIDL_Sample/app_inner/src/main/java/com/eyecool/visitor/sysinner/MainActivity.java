package com.eyecool.visitor.sysinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eyecool.visitor.sys.IRemoteCallback;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitor.sysinner.interfaces.OnRemoteServiceConnectedListener;
import com.eyecool.visitor.sysinner.utils.ToastUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity implements OnRemoteServiceConnectedListener {
    private static final String TAG = "MainActivity";
    @Bind(R.id.tv_mainPageTitle)
    TextView tvMainPageTitle;
    @Bind(R.id.tv_warm)
    TextView tvWarm;
    private Context mContext;
    private boolean remoteServiceIsOpen = false;
    private IRemoteCallback.Stub remoteCallback = new IRemoteCallback.Stub() {
        @Override
        public boolean remoteServiceIsOpen(boolean isOpen) throws RemoteException {
            return false;
        }

        @Override
        public void sendCameraData2Client(String cameraData) throws RemoteException {

        }

        @Override
        public void sendFaceRect2Service(float faceX, float faceY, float length) throws RemoteException {

        }

        @Override
        public void changeUI(int uiID) throws RemoteException {
            Log.d(TAG, "changeClientUI: --------------------内屏跳转！- 3");
            if (uiID == Constants.CHANGE_CHECK_FACE_UI_CODE) {
                startActivity(new Intent(mContext, CheckFaceActivity.class));
            }
        }

        @Override
        public void receiveFaceScore(int score) throws RemoteException {

        }

        @Override
        public void receiveFaceCheckError() throws RemoteException {
        }

        @Override
        public void receiveFaceCheckSuccessResult(IDCardInfo idCardInfo, String sceneImgPath, int matchScore) throws RemoteException {

        }
    };
    private File fileClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
/*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);*/

        App.getInstance().setOnRemoteConnectListener(this);
        Log.d(TAG, "onCreate: --------------------------界面创建");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: -----------------------------界面可见");
        File file = new File(Environment.getExternalStorageDirectory(), "service_file");
        if (file.exists()) {
            remoteServiceIsOpen = true;
        }
        fileClient = new File(Environment.getExternalStorageDirectory(), "client_file");
        if (!fileClient.exists()) {
            fileClient.mkdir();
        }
        App.getInstance().setRemoteServiceIsOpen(remoteServiceIsOpen);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fileClient.exists()) {
            fileClient.delete();
        }
        try {
            App.getInstance().getClientService().unregisterRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onDestroy: -------------------------销毁界面！");
        System.exit(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!remoteServiceIsOpen) {
            finish();
            return;
        } else {
            finish();
            //并且通知远程端也关闭界面
            try {
                App.getInstance().getClientService().changeServiceUI(0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick({R.id.bt_main_back, R.id.bt_main_authenticate, R.id.bt_mian_query, R.id.bt_main_setting})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_main_back:

                if (!remoteServiceIsOpen) {
                    finish();
                    return;
                }

                //关闭此界面，并且通知外屏也要关闭界面
                try {
                    App.getInstance().getClientService().changeServiceUI(0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.bt_main_authenticate:
                startCheckFaceUI();
                break;
            case R.id.bt_mian_query:
                startActivity(new Intent(mContext, VisitorRecordActivity.class));
                break;
            case R.id.bt_main_setting:
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    /**
     * 开启比对人脸界面
     */
    private void startCheckFaceUI() {

        if (!remoteServiceIsOpen) {
            ToastUtil.showToast(mContext, "请开启外屏应用！");
            return;
        }
        if (App.getInstance().getClientService() == null) {
            ToastUtil.showToast(mContext, "正在连接外屏应用...");
            return;
        }


        //进入比对界面,并且通知外屏进入比对界面
        try {
            App.getInstance().getClientService().changeServiceUI(2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//                finish();
        startActivity(new Intent(mContext, CheckFaceActivity.class));
    }

    @Override
    public void connectSuccess() {
        try {
            Log.d(TAG, "connectSuccess: --------------------连接远程服务成功！");
            App.getInstance().getClientService().registerRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectError() {

    }
}
