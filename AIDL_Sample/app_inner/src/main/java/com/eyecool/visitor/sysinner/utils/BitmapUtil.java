package com.eyecool.visitor.sysinner.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.eyecool.visitor.sysinner.views.FourAnglesFrameView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static android.R.attr.start;

/**
 * Created date: 2017/4/26
 * Author:  Leslie
 * 处理图片的工具类
 */

public class BitmapUtil {
    private static final String TAG = "BitmapUtil";
    private static FourAnglesFrameView rectView;


    /**
     * 把字节数组保存到本地文件中.这个方法用来保存摄像头的数据。
     *
     * @param bytes
     * @param filePath
     * @param
     */
    public static void saveByteToFile(byte[] bytes, String filePath) {

        //创建File对象，其中包含文件所在的目录以及文件的命名
        File file = new File(filePath);
        // 创建FileOutputStream对象
        FileOutputStream outputStream = null;
        // 创建BufferedOutputStream对象
        BufferedOutputStream bufferedOutputStream = null;
        try {
            // 如果文件存在则删除
            if (file.exists()) {
                file.delete();
            }
            // 在文件系统中根据路径创建一个新的空文件
            file.createNewFile();
            // 获取FileOutputStream对象
            outputStream = new FileOutputStream(file);
            // 获取BufferedOutputStream对象
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            // 往文件所在的缓冲输出流中写byte数据
            bufferedOutputStream.write(bytes);


            // 刷出缓冲输出流，该步很关键，要是不执行flush()方法，那么文件的内容是空的。
            bufferedOutputStream.flush();


        } catch (Exception e) {
            // 打印异常信息
            e.printStackTrace();
        } finally {


            long end = System.currentTimeMillis();
            long useTime = end - start;
            Log.d(TAG, "saveByteToFile: ---------------保存到本地耗时：" + (useTime));

            // 关闭创建的流对象
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 从位图中获取 RGB 值
     *
     * @param bitmap
     * @return
     */
    public static byte[] getRgbValuesFromBitmap(Bitmap bitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorFilter colorFilter = new ColorMatrixColorFilter(
                colorMatrix);
        Bitmap argbBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(argbBitmap);

        Paint paint = new Paint();

        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int componentsPerPixel = 3;
        int totalPixels = width * height;
        int totalBytes = totalPixels * componentsPerPixel;

        byte[] rgbValues = new byte[totalBytes];
        @ColorInt int[] argbPixels = new int[totalPixels];
        argbBitmap.getPixels(argbPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < totalPixels; i++) {
            @ColorInt int argbPixel = argbPixels[i];
            int red = Color.red(argbPixel);
            int green = Color.green(argbPixel);
            int blue = Color.blue(argbPixel);
            rgbValues[i * componentsPerPixel + 0] = (byte) red;
            rgbValues[i * componentsPerPixel + 1] = (byte) green;
            rgbValues[i * componentsPerPixel + 2] = (byte) blue;
        }

        return rgbValues;
    }

    public static void saveBitmap2SD(Bitmap bitmap, String fileName) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh_mm_ss_Sss");
        String date = sDateFormat.format(new java.util.Date());
        //截图存放位置是SD卡根目录下/capture里
        File dir = new File(Environment.getExternalStorageDirectory() + "/Pictures");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = null;

        if (file == null) {
            file = new File(dir, fileName + ".jpg");
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bos);                                  //100是图片保存的质量程度
            bos.flush();
            bos.close();
            Log.d(TAG, "saveBitmap2SD: ----------------------------crop保存成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从图片中截图人脸，并返回该人脸bitmap对象.
     * 扩展的规则就是以人脸坐标区域为中心扩展
     *
     * @param bitmap       原图
     * @param faceLoc      人脸坐标数组
     * @param zoomMultiple 截取范围的倍数(相对于人脸坐标区域),倍数不能小于1
     * @return
     */
    public static Bitmap cropBitmap(Bitmap bitmap, float[] faceLoc, float zoomMultiple) {


        /**
         * 改用新的方法计算出实际的放大区域，并且把人脸坐标绘制图中正确的位置上
         * 1.确定人脸坐标点的中心坐标(相对于原图)
         * 2.确定理论放大后区域的边长
         * 3.确定中心点到原图区域边界的距离
         * 4.四个方向距离跟中心点到理论放大区域边界距离进行 比较，并计算出合理距离
         * 5.根据实际边长计算出截取图片起点的坐标
         * 6.根据坐标从原图中截图
         * 7.计算出人脸坐标点的中心坐标(相对于剪切过的图片)
         * 8.画人脸框
         */

        //1.
        float[] faceRectCenterPoint = {faceLoc[0] + 0.5f * faceLoc[2], faceLoc[1] + 0.5f * faceLoc[2]};
        Log.d(TAG, "cropBitmap: -------------------人脸坐标：" + faceLoc[0] + "," + faceLoc[1] + "," + faceLoc[2]);
        Log.d(TAG, "cropBitmap: --------------人脸坐标中心点：" + faceRectCenterPoint[0] + "," + faceRectCenterPoint[1]);
        //2.
        float theorySlideLength = faceLoc[2] * zoomMultiple;
        float actualSlideLength = 0f;
        Log.d(TAG, "cropBitmap: ---------------------理论边长：" + theorySlideLength);

        //3.
        float left = faceRectCenterPoint[0];
        float top = faceRectCenterPoint[1];
        float right = bitmap.getWidth() - left;
        float bottom = bitmap.getHeight() - top;

        if (right < 0 || bottom < 0) {
            return null;
        }

        float halfTheorySlideLength = theorySlideLength / 2;
        float leftOffset = left - halfTheorySlideLength;
        float topOffset = top - halfTheorySlideLength;
        float rightOffset = right - halfTheorySlideLength;
        float bottomOffset = bottom - halfTheorySlideLength;

        Log.d(TAG, "cropBitmap: -------------------------left:" + left);
        Log.d(TAG, "cropBitmap: -------------------------top:" + top);
        Log.d(TAG, "cropBitmap: -------------------------right:" + right);
        Log.d(TAG, "cropBitmap: -------------------------bottom:" + bottom);
        Log.d(TAG, "cropBitmap: -------------------------leftOffset:" + leftOffset);
        Log.d(TAG, "cropBitmap: -------------------------topOffset:" + topOffset);
        Log.d(TAG, "cropBitmap: -------------------------rightOffset:" + rightOffset);
        Log.d(TAG, "cropBitmap: -------------------------bottomOffset:" + bottomOffset);

        //4.比较共分为2大种情况
        //4.1理论边界不会超过原图
        if (leftOffset >= 0 && topOffset >= 0 && rightOffset >= 0 && bottomOffset >= 0) {
            //理论区域就是实际区域
            Log.d(TAG, "cropBitmap: ---------------------------理论区域就是实际区域");
            actualSlideLength = theorySlideLength;
        } else {
            //4.2理论边界超过原图，共分成15种小情况
            //4.2.1理论区域完全超过原图
            if (leftOffset <= 0 && topOffset <= 0 && rightOffset <= 0 && bottomOffset <= 0) {
                //直接使用原图
                Log.d(TAG, "cropBitmap: ---------------------------4.2.1理论区域完全超过原图");
                return bitmap;
            }
            //4.2.2理论区域只有←超出原图
            if (leftOffset <= 0 && topOffset >= 0 && rightOffset >= 0 && bottomOffset >= 0) {
                actualSlideLength = theorySlideLength + leftOffset * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.2理论区域只有←超出原图");
            }
            //4.2.3理论区域只有↑超出原图
            if (leftOffset >= 0 && topOffset <= 0 && rightOffset >= 0 && bottomOffset >= 0) {
                actualSlideLength = theorySlideLength + topOffset * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.3理论区域只有↑超出原图");
            }
            //4.2.4理论区域只有→超出原图
            if (leftOffset >= 0 && topOffset >= 0 && rightOffset <= 0 && bottomOffset >= 0) {
                actualSlideLength = theorySlideLength + rightOffset * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.4理论区域只有→超出原图");
            }
            //4.2.5理论区域只有↓超出原图
            if (leftOffset >= 0 && topOffset >= 0 && rightOffset >= 0 && bottomOffset <= 0) {
                actualSlideLength = theorySlideLength + bottomOffset * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.5理论区域只有↓超出原图");
            }//4.2.6理论区域↖超出原图
            if (leftOffset <= 0 && topOffset <= 0 && rightOffset >= 0 && bottomOffset >= 0) {
                actualSlideLength = theorySlideLength + (leftOffset < topOffset ? leftOffset * 2.0f : topOffset * 2.0f);
                Log.d(TAG, "cropBitmap: ---------------------------4.2.6理论区域↖超出原图");
            }//4.2.7理论区域↗超出原图
            if (leftOffset >= 0 && topOffset <= 0 && rightOffset <= 0 && bottomOffset >= 0) {
                actualSlideLength = theorySlideLength + (rightOffset < topOffset ? rightOffset * 2.0f : topOffset * 2.0f);
                Log.d(TAG, "cropBitmap: ---------------------------4.2.7理论区域↗超出原图");
            }//4.2.8理论区域↙超出原图
            if (leftOffset <= 0 && topOffset >= 0 && rightOffset >= 0 && bottomOffset <= 0) {
                actualSlideLength = theorySlideLength + (leftOffset < bottomOffset ? leftOffset * 2.0f : bottomOffset * 2.0f);
                Log.d(TAG, "cropBitmap: ---------------------------4.2.8理论区域↙超出原图");
            }//4.2.9理论区域↘超出原图
            if (leftOffset >= 0 && topOffset >= 0 && rightOffset <= 0 && bottomOffset <= 0) {
                actualSlideLength = theorySlideLength + (rightOffset < bottomOffset ? rightOffset * 2.0f : bottomOffset * 2.0f);
                Log.d(TAG, "cropBitmap: ---------------------------4.2.9理论区域↙超出原图");
            }//4.2.10理论区域只有←边没有超出原图
            if (leftOffset >= 0 && topOffset <= 0 && rightOffset <= 0 && bottomOffset <= 0) {

                float min = Math.min(Math.min(topOffset, rightOffset), bottomOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.10理论区域只有←边没有超出原图");
            }//4.2.11理论区域只有→边没有超出原图
            if (leftOffset <= 0 && topOffset <= 0 && rightOffset >= 0 && bottomOffset <= 0) {
                float min = Math.min(Math.min(leftOffset, topOffset), bottomOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.11理论区域只有→边没有超出原图");
            }//4.2.12理论区域只有↑边没有超出原图
            if (leftOffset <= 0 && topOffset >= 0 && rightOffset <= 0 && bottomOffset <= 0) {
                float min = Math.min(Math.min(leftOffset, rightOffset), bottomOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.12理论区域只有↑边没有超出原图");
            }//4.2.13理论区域只有↓边没有超出原图
            if (leftOffset <= 0 && topOffset <= 0 && rightOffset <= 0 && bottomOffset >= 0) {
                float min = Math.min(Math.min(leftOffset, rightOffset), topOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.13理论区域只有↓边没有超出原图");
            }//4.2.14理论区域横向超出原图
            if (leftOffset <= 0 && topOffset >= 0 && rightOffset <= 0 && bottomOffset >= 0) {
                float min = Math.min(leftOffset, rightOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.14理论区域只有↓边没有超出原图");
            }//4.2.15理论区域竖向超出原图
            if (leftOffset >= 0 && topOffset <= 0 && rightOffset >= 0 && bottomOffset <= 0) {
                float min = Math.min(topOffset, bottomOffset);
                actualSlideLength = theorySlideLength + min * 2.0f;
                Log.d(TAG, "cropBitmap: ---------------------------4.2.15理论区域只有↓边没有超出原图");
            }

        }
        Log.d(TAG, "cropBitmap: ---------------------实际边长：" + actualSlideLength);
        //5.根据实际边长计算出截取图片起点的坐标
        int startX = (int) (faceRectCenterPoint[0] - 0.5f * actualSlideLength);
        int startY = (int) (faceRectCenterPoint[1] - 0.5f * actualSlideLength);
        Log.d(TAG, "cropBitmap: -----------------------------startX:" + startX);
        Log.d(TAG, "cropBitmap: -----------------------------startY:" + startY);
        //6.根据坐标从原图中截图
        Bitmap bmFace = Bitmap.createBitmap(bitmap, startX, startY, (int) actualSlideLength, (int) actualSlideLength);
        Paint paint = new Paint();
        Canvas canvas = new Canvas();
        canvas.drawBitmap(bmFace, 0, 0, paint);
        //7.计算出人脸坐标点的中心坐标(相对于剪切过的图片)
        faceRectCenterPoint[0] = actualSlideLength * 0.5f;
        faceRectCenterPoint[1] = actualSlideLength * 0.5f;
        //8.画人脸框到剪切的图片上
        //计算原始人脸坐标在截图中的坐标
        /*float faceRectStartX = faceRectCenterPoint[0] - 0.5f * theorySlideLength;
        float faceRectStartY = faceRectCenterPoint[1] - 0.5f * theorySlideLength;
        Canvas canvas1 = new Canvas(bmFace);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint1 = new Paint();
        paint1.setColor(Color.rgb(250, 128, 10));
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(2);
        paint1.setAntiAlias(true);
        float paddingValue = (actualSlideLength - faceLoc[2]) / 2;
        canvas1.drawRect(paddingValue,paddingValue,paddingValue + faceLoc[2],paddingValue + faceLoc[2], paint1);*/
        return bmFace;
    }

    /**
     * 把SD卡中的图片文件解析成缩略（根据现实图片的控件宽高）的bitmap对象
     *
     * @param filePath
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    public static Bitmap decodeThumbBitmapFormSDPicsFile(String filePath, int viewWidth, int viewHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;                                                       //设置为true，表示解析Bitmap对象，该对象不占内存
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        BitmapFactory.decodeFile(filePath, options);
        //设置缩放比例
        options.inSampleSize = computeScale(options, viewWidth, viewHeight);
        //设置为false，解析Bitmap对象加入到内存中
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算缩放比例(根据View对象的宽高)
     *
     * @param options
     * @param viewWidth
     * @param viewHeight
     * @return
     */
    public static int computeScale(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        int inSampleSize = 1;
        if (viewWidth == 0 || viewHeight == 0) {
            return inSampleSize;
        }
        int bitmapWidth = options.outWidth;
        int bitmapHeight = options.outHeight;

        //假如Bitmap的宽度或者高度大于我们设置图片的View的宽高，则计算缩放比例
        if (bitmapWidth > viewWidth || bitmapHeight > viewHeight) {
            int widthScale = Math.round((float) bitmapWidth / (float) viewWidth);
            int heightScale = Math.round((float) bitmapHeight / (float) viewHeight);

            //为了保证图片不缩放变形，取宽高比例最小的那个
            inSampleSize = widthScale < heightScale ? widthScale : heightScale;
        }
        Log.d("zkx", "-------------------------------------------缩放值：" + inSampleSize);
        return inSampleSize;
    }

    /**
     * 根据文件夹路径，和文件名。删除文件
     * @param dir
     * @param fileName
     */
    public static void deletePicOnSD(String dir, String fileName) {
        File file = new File(dir, fileName);
        file.delete();
    }

    /**
     * 根据文件路径，删除 SD 卡上的文件
     * @param filePath
     */
    public static void deletePicOnSD(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

    public static byte[] getDataFromSD(String filePath) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        try {
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 画人脸框
     * @param context
     * @param parentLayout
     * @param targetView
     * @param faceRect
     * @param srcImgWidth
     * @param srcImgHeight
     */
    public static void drawFaceRect(Context context,FrameLayout parentLayout, ImageView targetView, float[] faceRect, int srcImgWidth, int srcImgHeight){
        if (parentLayout.getChildCount() > 1) {
            if (rectView != null) {
                parentLayout.removeViewAt(1);
            }
        }


        //2.计算出人脸框左上角的坐标

        //计算控件显示图片和原图片的横向和竖向比例

        //2.计算出人脸框左上角的坐标
        //计算控件显示图片和原图片的横向和竖向比例

        float ratioHorizontal = (float) targetView.getWidth() / srcImgWidth;
        float ratioPortrait = (float) targetView.getHeight() / srcImgHeight;
        //3.计算人脸框左上角坐标，这个是相对于父控件的坐标，不是相对于窗口或者屏幕的坐标。使用相对于父控件的坐标好(用parentView.addView(rectView,1)方式时)
        float faceStartPX = 0 + faceRect[0] * ratioHorizontal;
        float faceStartPY = 0 + faceRect[1] * ratioPortrait;

        //3.计算人脸框右下角坐标

        float faceEndPX = faceStartPX + faceRect[2] * ratioHorizontal;
        float faceEndPY = faceStartPY + faceRect[2] * ratioPortrait;

        float lineLength = (faceEndPX - faceStartPX) * 0.2f;

        rectView = new FourAnglesFrameView(context, faceStartPX, faceStartPY, faceEndPX, faceEndPY, lineLength);

        parentLayout.addView(rectView, 1);
    }
}
