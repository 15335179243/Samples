package com.eyecool.visitor.sys.utils;

import android.os.Environment;
import android.util.Log;

import com.eyecool.visitor.sys.App;
import com.eyecool.visitor.sys.bean.IDCardInfo;
import com.eyecool.visitor.sys.interfaces.Constant;
import com.eyecool.visitor.sys.interfaces.OnIDCardReadListener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import cn.com.aratek.idcard.IDCard;
import cn.com.aratek.idcard.IDCardReader;
import cn.com.aratek.util.Result;


/**
 * Created date: 2017/6/16
 * Author:  Leslie
 * 度二代身份证的工具类
 */

public class IDCardUtil {
    private static final String TAG = "IDCardUtil";
    public static OnIDCardReadListener mCardReadListener;
    /**
     * 读取二代身份证信息
     * @return 1：读取成功，从 App.currentIdCard 中取结果;2:重新放置身份证;3:读卡失败
     */
    public static int readIDCardInfo() {
        Result res = App.getInstance().getIdCardReader().read();
        Log.d(TAG, "readCard: -------------------res = " + res.error);

        if (res.error == IDCardReader.RESULT_OK) {
            IDCard idCard = (IDCard) res.data;
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd", Locale.CHINA);
            IDCardInfo idCardInfo = new IDCardInfo(idCard.getName()
                    , idCard.getSex().toString()
                    , idCard.getNationality().toString()
                    , df.format(idCard.getBirthday())
                    , idCard.getAddress()
                    , idCard.getNumber()
                    , idCard.getAuthority()
                    , df.format(idCard.getValidFrom()) + " - " + (idCard.getValidTo() == null ? "长期" : df.format(idCard.getValidTo()))
                    , idCard.isSupportFingerprint() ? "存在" : "不存在"
                    , idCard.getPhoto()
                    ,Environment.getExternalStorageDirectory() + "/Pictures/idCard/" + idCard.getNumber() + ".jpg");
            mCardReadListener.readSuccess(idCardInfo);
            App.getInstance().setmIdCardInfo(idCardInfo);
            App.getInstance().setCurrentIdCard(idCard);
            //读到卡后就直接 把身份证证件照片存到本地
            BitmapUtil.saveBitmap2SD(idCard.getPhoto(), Environment.getExternalStorageDirectory() + "/Pictures/idCard",idCard.getNumber() + ".jpg");
            return Constant.READ_IDCARD_STATE_OK;
        } else if (res.error == IDCardReader.NO_CARD) {
            mCardReadListener.replaceIDCard();
            return Constant.READ_IDCARD_STATE_REPLACE;
        } else {
            mCardReadListener.readError();
            return Constant.READ_IDCARD_STATE_ERROR;
        }
    }

    public static void openDevice(){
        int error;
        if ((error = App.getInstance().getIdCardReader().powerOn()) != IDCardReader.RESULT_OK) {
            Log.d(TAG, "openDevice: --------身份证阅读机具上电失败！错误码：" + error);
        }
        if ((error = App.getInstance().getIdCardReader().open()) != IDCardReader.RESULT_OK) {
            Log.d(TAG, "openDevice: --------身份证阅读机具打开失败！错误码：" + error);
        } else {
            Result res = App.getInstance().getIdCardReader().getSN();
            Log.d(TAG, "openDevice: --------二代身份证阅读模块序列号：" + res);
        }
    }

    public static void closeDevice(){
        int error;
        if ((error = App.getInstance().getIdCardReader().close()) != IDCardReader.RESULT_OK) {
            Log.d(TAG, "closeDevice: ------------------身份证阅读器关闭失败！---错误码：" + error);
        } else {
            Log.d(TAG, "closeDevice: ------------------身份证阅读器关闭成功！");
        }
        if ((error = App.getInstance().getIdCardReader().powerOff()) != IDCardReader.RESULT_OK) {
            Log.d(TAG, "closeDevice: ------------------身份证阅读机具断电失败！错误码：" + error);
        }
    }

    public static void setResultListener(OnIDCardReadListener cardReadListener){
        mCardReadListener = cardReadListener;
    }
}
