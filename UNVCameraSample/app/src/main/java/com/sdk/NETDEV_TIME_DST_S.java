package com.sdk;

public class NETDEV_TIME_DST_S {
	public int dwYear;               /* Year */
    public int dwMonth;              /* Month(1~12)*/
    public int dwWeekInMonth;        /* 1 for the first week and 5 for the last week in the month */
    public int dwDayInWeek;          /* 0 for Sunday and 6 for Saturday see enumeration NETDEV_DAY_IN_WEEK_E */
    public int dwHour;               /* Hour */
}
