package com.camera.mirror;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.Window;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity implements TextureView.SurfaceTextureListener {
    private static final String TAG = "MainActivity";
    private TextureView cameraView;
    private byte[] frameData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        cameraView = findViewById(R.id.cameraView);
        cameraView.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        /**
         *支持分辨率: 640 * 480
          支持分辨率: 320 * 240
          支持分辨率: 800 * 600
          支持分辨率: 1280 * 720
          支持分辨率: 1280 * 1024
          支持分辨率: 1600 * 1200
          支持分辨率: 1920 * 1080
          支持分辨率: 2048 * 1536
         */
        Camera camera = Camera.open(0);
        try {
            camera.setPreviewTexture(surface);
            Camera.Parameters params = camera.getParameters();
            //设置相机显示角度
            camera.setDisplayOrientation(90);
            camera.setParameters(params);
            camera.startPreview();
            Camera.Size size = params.getPreviewSize();

            List<Camera.Size> sizes = params.getSupportedPictureSizes();
            for (Camera.Size size1 : sizes) {
                Log.d(TAG, "支持分辨率: " + size1.width + " * " + size1.height);
            }

            Log.d(TAG, "预览分辨率: " + size.height + " * " + size.width);
            Matrix matrix = new Matrix();
            matrix.setScale(-1, 1, 540, 0); cameraView.setTransform(matrix);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    Camera.PreviewCallback mPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            frameData = data;
        }
    };

    public byte[] getFrameData() {
        return frameData;
    }
}
