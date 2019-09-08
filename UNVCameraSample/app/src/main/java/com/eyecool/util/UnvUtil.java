package com.eyecool.util;

import com.sdk.CPlayView;
import com.sdk.NETDEV_DEVICE_INFO_S;
import com.sdk.NETDEV_PREVIEWINFO_S;
import com.sdk.NetDEVSDK;

/**
 * @Author: 张开旭
 * @Date: 2018/9/17
 * @E-mail: zhangkaixu@eyecool.cn
 * @Ltd: 山东眼神智能科技有限公司(www.facekey.cn)
 * @Address: 山东省济南市高新区齐鲁软件园B座508
 * @Des: this is 宇视网络摄像头封装的工具类
 */
public class UnvUtil {

    public volatile static UnvUtil instance;

    private UnvUtil() {
    }

    public static UnvUtil getInstance() {
        if (instance == null) {
            instance = new UnvUtil();
        }
        return instance;
    }

    /**
     * 登录设备。此操作耗时 500ms 以上，建议在子线程执行。
     *
     * @param ip       设备 IP 地址
     * @param userName 用户名
     * @param pwd      密码
     * @return 返回登录ID
     */
    public int login(String ip, String userName, String pwd) {
        NetDEVSDK oNetDemo = new NetDEVSDK();
        oNetDemo.NETDEV_Init();
        NetDEVSDK.NETDEV_SetT2UPayLoad(800);

        NETDEV_DEVICE_INFO_S stDeviceInfo = new NETDEV_DEVICE_INFO_S();
        return NetDEVSDK.NETDEV_Login(ip, 80, userName, pwd, stDeviceInfo);
    }

    /**
     * 播放视频流。此操作耗时 500ms 以上，建议在子线程执行。播放前，所有的设备都要完成登录操作！
     * @param loginID 登录ID
     * @param playView 播放视频流的控件对象
     * @param playID 播放ID,从 0 开始，多路预览时，此数值逐次增加
     * @param winIndex 从 1 开始，多路预览时，此数值逐次增加。且不能使用同样的值！否则帧数据会在不同的view上面切换。
     */
    public void play(int loginID, CPlayView playView, int playID,int winIndex) {
        if (loginID != 0) {
            NETDEV_PREVIEWINFO_S stPreviewInfo = new NETDEV_PREVIEWINFO_S();
            stPreviewInfo.dwChannelID = 1;
            stPreviewInfo.dwLinkMode = 1;
            stPreviewInfo.dwStreamIndex = 1;
            playView.m_bCanDrawFrame = true;
            playView.m_dwWinIndex = winIndex;
            NetDEVSDK.NETDEV_StopRealPlay(playID, playView.m_dwWinIndex);
            NetDEVSDK.NETDEV_RealPlay(loginID, stPreviewInfo, playView.m_dwWinIndex);
        }
    }
}
