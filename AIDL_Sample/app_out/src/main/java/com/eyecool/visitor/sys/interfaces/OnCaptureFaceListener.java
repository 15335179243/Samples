package com.eyecool.visitor.sys.interfaces;

import android.graphics.Bitmap;

/**
 * Created date: 2017/6/15
 * Author:  Leslie
 */

public interface OnCaptureFaceListener {

    void captureFace(Bitmap faceBitmap, float[] faceRect);

   void captureFrame(String videoStr);

    void captureFrameHasCorrect(Bitmap correctBitmap);
}
