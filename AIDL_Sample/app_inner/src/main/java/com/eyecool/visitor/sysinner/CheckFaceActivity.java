package com.eyecool.visitor.sysinner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.eyecool.visitor.sys.IRemoteCallback;
import com.eyecool.visitor.sys.bean.CustomerInfo;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitor.sysinner.utils.BitmapUtil;
import com.eyecool.visitor.sysinner.utils.DataBaseUtil;
import com.eyecool.visitorsys.db.CustomerInfoDao;

import java.io.ByteArrayOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created date: 2017/6/23
 * Author:  Leslie
 */

public class CheckFaceActivity extends Activity {

    private static final String TAG = "CheckFaceActivity";

    @Bind(R.id.fl_video_container)
    FrameLayout flVideoContainer;
    @Bind(R.id.tv_result)
    TextView tvResult;
    @Bind(R.id.tv_checkFace_result)
    TextView tvCheckFaceResult;
    @Bind(R.id.imageView)
    ImageView imageView;
    private static final String SCENE_IMG_DIR_PATH = Environment.getExternalStorageDirectory() + "/Pictures/scene";
    private boolean hasFace = true;

    /**
     * 外屏通知内屏的回调
     */
    private IRemoteCallback.Stub remoteCallback = new IRemoteCallback.Stub() {
        @Override
        public boolean remoteServiceIsOpen(boolean isOpen) throws RemoteException {
            return false;
        }

        @Override
        public void sendCameraData2Client(String cameraData) throws RemoteException {
            Log.d(TAG, "sendCameraData2Client: ----------------------------收到摄像头回调数据了！");
            //处理摄像头数据
            //读取sd卡中的一帧帧数据
            byte[] dataFromSD = BitmapUtil.getDataFromSD(cameraData);
//            YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
            YuvImage image = new YuvImage(dataFromSD, ImageFormat.NV21, 640, 480, null);
            if (image != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compressToJpeg(new Rect(0, 0, 640, 480), 80, stream);

                Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());


                Canvas canvas = new Canvas();
                // 设置canvas画布背景为白色
                canvas.drawColor(Color.BLACK);
                // 在画布上绘制缩放之前的位图，以做对比
                //屏幕上的位置坐标是0,0
                canvas.drawBitmap(bmp, 0, 0, null);
                // 定义矩阵对象
                Matrix matrix = new Matrix();
                // 缩放原图
                matrix.postScale(1f, 1f);
                // 向左旋转45度，参数为正则向右旋转,如果是横屏的话，就不需要转了。

//                matrix.postRotate(90);
                //bmp.getWidth(), 500分别表示重绘后的位图宽高
                Bitmap dstbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
                // 在画布上绘制旋转后的位图
                //放在坐标为0,200的位置
                canvas.drawBitmap(dstbmp, 0, 200, null);

                //把bitmap发送给主线程，然后删除图片
                Message message = mHandler.obtainMessage();
                message.obj = dstbmp;
                message.what = Constants.CAMERA_FRAME_CODE;
                mHandler.sendMessage(message);
                String replace = cameraData.replace(Environment.getExternalStorageState() + "/Pictures/", "");
                BitmapUtil.deletePicOnSD(replace);
            }
        }

        @Override
        public void sendFaceRect2Service(float faceX, float faceY, float length) throws RemoteException {
            //收到人脸框数据，根据数据画框
            Message message = mHandler.obtainMessage();
            message.obj = new float[]{faceX, faceY, length};
            message.what = Constants.FACE_RECT_DATA;
            mHandler.sendMessage(message);
            hasFace = true;
        }

        @Override
        public void changeUI(int uiID) throws RemoteException {
        }

        @Override
        public void receiveFaceScore(int score) throws RemoteException {
            //收到 service 端发送过来的人脸比对分值
            Message message = mHandler.obtainMessage();
            message.arg1 = score;
            message.what = Constants.FACE_SCORE_DATA;
            mHandler.sendMessage(message);
        }

        @Override
        public void receiveFaceCheckError() throws RemoteException {
            mHandler.sendEmptyMessage(Constants.FACE_CHECK_ERROR);
        }

        @Override
        public void receiveFaceCheckSuccessResult(IDCardInfo idCardInfo, String sceneImgPath, int matchScore) throws RemoteException {
            sceneCapture = sceneImgPath;
            App.getInstance().setmSceneImg(BitmapFactory.decodeFile(sceneImgPath));
            Message message = mHandler.obtainMessage();
            message.arg1 = matchScore;
            message.obj = idCardInfo;
            message.what = Constants.FACE_CHECK_SUCCESS;
            mHandler.sendMessage(message);
            Log.d(TAG, "receiveFaceCheckSuccessResult: ------------------matchScore = " + matchScore);

        }

    };

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.CAMERA_FRAME_CODE:
                    frameImg = (Bitmap) msg.obj;
                    imageView.setImageBitmap(frameImg);

                    //如果没有人脸了，就移除
                    if (!hasFace) {
                        if (flVideoContainer.getChildCount() > 1) {

                            flVideoContainer.removeViewAt(1);
                        }
                    }
                    break;
                case Constants.FACE_RECT_DATA:
                    //往 imageView 上画人脸框
                    float[] faceRect = (float[]) msg.obj;
                    BitmapUtil.drawFaceRect(getApplicationContext(), flVideoContainer, imageView, faceRect, 640, 480);
                    hasFace = false;
                    break;
                case Constants.FACE_SCORE_DATA:
                    tvResult.setVisibility(View.VISIBLE);
                    tvCheckFaceResult.setVisibility(View.VISIBLE);
                    tvCheckFaceResult.setText("比对结果： " + msg.arg1);
                    break;
                case Constants.FACE_CHECK_ERROR:
                    tvResult.setVisibility(View.INVISIBLE);
                    tvCheckFaceResult.setText(getString(R.string.idcard_face_match_retry));
//                    tvCheckFaceResult.setText("验证失败，请重试！");
//                    ToastUtil.showToast(CheckFaceActivity.this,"请提示访客重新刷卡认证！");
                    break;
                case Constants.FACE_CHECK_SUCCESS:                                              //认证比对成功，然后到数据库中查找此人
                    IDCardInfo idCardInfo = (IDCardInfo) msg.obj;
                    App.getInstance().setmIdCardInfo(idCardInfo);
                    CustomerInfo customerInfo = DataBaseUtil.query(customerInfoDao, idCardInfo.getIdcardNumber());
                    DataBaseUtil.modify(idCardInfo.getIdcardNumber());
                    int userType = 0;
                    Intent intent = new Intent(mContext, CheckResultActivity.class);
                    intent.putExtra("match_score", msg.arg1 + "");
                    intent.putExtra("scene_img_path",sceneCapture);
                    if (customerInfo != null) {
                        App.getInstance().setmCustomerInfo(customerInfo);
                        //1.开启比对结果界面2.同时通知 "外屏应用" 显示 比对结果界面


                        /*try {
                            App.getInstance().getClientService().changeServiceUI(3);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
*/
                        userType = Integer.parseInt(customerInfo.getUser_type());
                        intent.putExtra("user_type", userType);
                    } else {
                        //这个人是未登记状态.通知外屏显示未登记状态
                        userType = Constants.USER_TYPE_UNKNOWN;
                        intent.putExtra("user_type",userType);
                    }

                    //2...
                    try {
                        App.getInstance().getClientService().showResult(customerInfo,userType);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    //1...
                    finish();
                    startActivity(intent);
                    break;
            }
        }
    };
    private Bitmap frameImg;
    private CustomerInfoDao customerInfoDao;
    private String sceneCapture = null;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_checkface);
        ButterKnife.bind(this);

        mContext = this;
        try {
            App.getInstance().getClientService().registerRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        customerInfoDao = DataBaseUtil.session.getCustomerInfoDao();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvResult.setVisibility(View.INVISIBLE);
        tvCheckFaceResult.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            App.getInstance().getClientService().unregisterRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //内屏回到待机界面，同时通知外屏回到待机界面
        try {
            App.getInstance().getClientService().changeServiceUI(1);
            App.getInstance().getClientService().unregisterRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        finish();
    }

    @OnClick(R.id.bt_checkFace_back)
    public void onClick() {
        //内屏回到待机界面，同时通知外屏回到待机界面
        try {
            App.getInstance().getClientService().changeServiceUI(1);
            App.getInstance().getClientService().unregisterRemoteCallback(remoteCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        finish();
    }

}

