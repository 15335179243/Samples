package com.eyecool.visitor.sys.interfaces;

import com.eyecool.visitor.sys.bean.IDCardInfo;

/**
 * Created date: 2017/6/16
 * Author:  Leslie
 * 读二代身份证的监听
 */

public interface OnIDCardReadListener {

    /**
     * 读取成功
     * @param info 身份证信息
     */
    abstract void readSuccess(IDCardInfo info);

    abstract void replaceIDCard();

    abstract void readError();
}
