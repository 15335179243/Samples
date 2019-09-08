package com.sdk;
         /*�豸��Ϣ*/

public class NETDEV_LOGIN_INFO_S {
	public int LoginType;                          /* 0:本地登录; 1:动态密码登录; 2:支持第三方设备; 3:云设备登录\*/

    /* ��̬�����¼�ֶ� */
    public int  lpUserID;                           /* �ƶ��˻���¼ID User login ID */
    public String szDeviceName;              /* Device name */
    public String szDevicePassword;          /* Device login password (optional) */
    public int dwT2UTimeout;                 /* P2P timeout (default: 15s) */
    public int dwConnectMode;                /* Connect Mode, 0:直连、打洞、转发；1：直连、打洞+转发；2：打洞；3：转发 */

    /* ���ص�¼�ֶ� */
    public String mDevIP;               /* �豸IP Device IP */
    public String mUserName;            /* �û��� User Name */
    public String mPassword;            /* ���� Password */
    public int dwDevPort;                           /* �豸�������˿� Device Server Port */
}
