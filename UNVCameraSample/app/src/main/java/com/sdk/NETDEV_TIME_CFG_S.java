package com.sdk;

public class NETDEV_TIME_CFG_S {
	public int dwTimeZone;    /*时区 TimeZone*/
    //public NETDEV_TIME_S stTime;       /*时间 Time*/

    public int dwYear;                     /*   Year */
    public int dwMonth;                      /*  Month */
    public int dwDay;                        /*   Day */
    public int dwHour;                       /*   Hour */
    public int dwMinute;                     /*   Minute */
    public int dwSecond;                     /*   Second */
    public int bEnableDST;

	public NETDEV_TIME_DST_CFG_S stTimeDSTCfg;
}
