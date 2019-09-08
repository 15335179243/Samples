package com.eyecool.util;

import java.math.BigDecimal;

/**
 * @Author: 张开旭
 * @Date: 2018/6/25
 * @E-mail: zhangkaixu@eyecool.cn
 * @Ltd: 山东眼神智能科技有限公司(www.facekey.cn)
 * @Address: 山东省济南市高新区齐鲁软件园B座508
 * @Des: this is 数学计算的工具类
 */
public class ArithmeticUtil {
    public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

    /**
     * 将分为单位的转换为元 （除100）
     *
     * @param amount
     * @return
     * @throws Exception
     */
    public static BigDecimal coin2Yuan(String amount) {
        if (!amount.matches(CURRENCY_FEN_REGEX)) {
            throw new RuntimeException("金额格式错误|"+amount);
        }
        return BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100));
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
     *
     * @param value 要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * int[] 转换为 byte[]
     *
     * @param values
     * @return
     */
    public static byte[] intsToBytes(int[] values) {
        byte[] src = new byte[4 * values.length];
        for (int i = 0; i < values.length; i++) {
            src[i + 3] = (byte) ((values[i] >> 24) & 0xFF);
            src[i + 2] = (byte) ((values[i] >> 16) & 0xFF);
            src[i + 1] = (byte) ((values[i] >> 8) & 0xFF);
            src[i + 0] = (byte) (values[i] & 0xFF);
        }
        return src;
    }

    /**
     * 计算 2 个数的比
     *
     * @param numberA
     * @param numberB
     * @return
     */
    public static int[] computeRadio(int numberA, int numberB) {
        int[] resultNums = new int[2];
        int greatestCommonDivisor = computeGreatestCommonDivisor(numberA, numberB);
        if (greatestCommonDivisor != 0) {
            resultNums[0] = numberA / greatestCommonDivisor;
            resultNums[1] = numberB / greatestCommonDivisor;
        }
        return resultNums;
    }

    /**
     * 计算 2 个数的最大公约数
     *
     * @param numberA
     * @param numberB
     * @return 如果返回0, 就是传入的数不正确
     */
    private static int computeGreatestCommonDivisor(int numberA, int numberB) {
        if (numberA == 0 || numberB == 0) {
            return 0;
        }
        int min;
        if (numberA < numberB) {
            min = numberA;
        } else {
            min = numberB;
        }
        //遍历 min,找公约数，最后满足条件的就是最大公约数[要遍历完]
        int maxDivisor = 1;
        for (int i = 1; i <= min; i++) {
            if (numberA % i == 0) {
                if (numberB % i == 0) {
                    //找到了一个公约数
                    maxDivisor = i;
                }
            }
        }
        return maxDivisor;
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 获取 2 个数的比
     *
     * @param valueA           除数
     * @param valueB           被除数
     * @param significantDigit 保留有效数字位数
     * @return 返回 double 类型的数字
     */
    public static double getRatio(long valueA, long valueB, int significantDigit) {
        double radioD = (double) valueA / valueB;
        BigDecimal bg = new BigDecimal(radioD);
        double radioResult = bg.setScale(significantDigit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return radioResult;
    }

    public static String getRatioPercent(long valueA, long valueB, int significantDigit) {
        double radioD = (double) valueA * 100 / valueB;
        BigDecimal bg = new BigDecimal(radioD);
        double radioResult = bg.setScale(significantDigit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return radioResult + "%";
    }


    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
