package com.sdk;

import android.view.Surface;

import java.util.ArrayList;


public class NetDEVSDK {
	static {
		System.loadLibrary("Curl");
		System.loadLibrary("RSA");
		System.loadLibrary("MP4");
		System.loadLibrary("MP2");
		System.loadLibrary("mXML");
		System.loadLibrary("NDRtmp");
		System.loadLibrary("CloudSDK");
		System.loadLibrary("RM_Module");
		System.loadLibrary("NDRender");
		System.loadLibrary("dspvideomjpeg");
		System.loadLibrary("NDPlayer");
		System.loadLibrary("Discovery");
        System.loadLibrary("NetDEVSDK");
        System.loadLibrary("NetDEVSDK_JNI");
    }

	//private OnNotifyListener mNotifyListener;
 	public static int lpUserID;       	/* User ID*/
	public static int gdwWinIndex;       	/* User ID*/
 	public static int glpcloudID;      	/* Cloud ID*/
	public static int lpDownloadID;      	/* Cloud ID*/
	public static String strDevName;
 	public static NETDEV_FINDDATA_S []m_astVodFile = new NETDEV_FINDDATA_S[1000];

	/**
	 * @enum tagNETDEVAlarmTypeEn
	 * @brief  Alarm Type Enumeration definition
	 * @attention None
	 */
	public class NETDEV_ALARM_TYPE_E
	{
		/* 有恢复类型的告警  Recoverable alarms */
		public static final int NETDEV_ALARM_MOVE_DETECT                        = 1;            /* 运动检测告警  Motion detection alarm */
		public static final int NETDEV_ALARM_VIDEO_LOST                         = 2;            /* 视频丢失告警  Video loss alarm */
		public static final int NETDEV_ALARM_VIDEO_TAMPER_DETECT                = 3;            /* 遮挡侦测告警  Tampering detection alarm */
		public static final int NETDEV_ALARM_INPUT_SWITCH                       = 4;            /* 输入开关量告警  boolean input alarm */
		public static final int NETDEV_ALARM_TEMPERATURE_HIGH                   = 5;            /* 高温告警  High temperature alarm */
		public static final int NETDEV_ALARM_TEMPERATURE_LOW                    = 6;            /* 低温告警  Low temperature alarm */
		public static final int NETDEV_ALARM_AUDIO_DETECT                       = 7;            /* 声音检测告警  Audio detection alarm */
		public static final int NETDEV_ALARM_DISK_ABNORMAL                      = 8;            /* 磁盘异常 Disk abnormal */
		public static final int NETDEV_ALARM_DISK_OFFLINE                       = 9;            /* 磁盘下线 Disk online (兼容以前版本,不维护) */
		public static final int NETDEV_ALARM_DISK_ONLINE                        = 10;           /* 磁盘上线 Disk online */
		public static final int NETDEV_ALARM_DISK_STORAGE_WILL_FULL             = 11;           /* 磁盘存储空间即将满 Disk StorageGoingfull */
		public static final int NETDEV_ALARM_DISK_STORAGE_IS_FULL               = 12;           /* 存储空间满 StorageIsfull */
		public static final int NETDEV_ALARM_DISK_RAID_DISABLED                 = 13;           /* 阵列损坏 RAIDDisabled */
		public static final int NETDEV_ALARM_DISK_RAID_DEGRADED                 = 14;           /* 阵列衰退 RAIDDegraded */
		public static final int NETDEV_ALARM_DISK_RAID_RECOVERED                = 15;           /* 阵列恢复正常 RAIDDegraded */

		/* NVR及接入设备状态上报  Status report of NVR and access device 100~199 */
		public static final int NETDEV_ALARM_REPORT_DEV_ONLINE                  = 100;          /* 设备上线  Device online */
		public static final int NETDEV_ALARM_REPORT_DEV_OFFLINE                 = 101;          /* 设备下线  Device offline */
		public static final int NETDEV_ALARM_REPORT_DEV_VIDEO_LOSS              = 102;          /* 视频丢失  Video loss */
		public static final int NETDEV_ALARM_REPORT_DEV_VIDEO_LOSS_RECOVER      = 103;          /* 视频丢失恢复  Video loss recover */
		public static final int NETDEV_ALARM_REPORT_DEV_REBOOT                  = 104;          /* 设备重启  Device restart */
		public static final int NETDEV_ALARM_REPORT_DEV_SERVICE_REBOOT          = 105;          /* 服务重启  Service restart */
		public static final int NETDEV_ALARM_REPORT_DEV_DELETE_CHL              = 106;          /* 通道删除  Delete channel */
		/* 实况业务异常上报  Live view exception report 200~299 */
		public static final int NETDEV_ALARM_NET_FAILED                         = 200;          /* 会话网络错误 Network error */
		public static final int NETDEV_ALARM_NET_TIMEOUT                        = 201;          /* 会话网络超时 Network timeout */
		public static final int NETDEV_ALARM_SHAKE_FAILED                       = 202;          /* 会话交互错误 Interaction error */
		public static final int NETDEV_ALARM_STREAMNUM_FULL                     = 203;          /* 流数已经满 Stream full */
		public static final int NETDEV_ALARM_STREAM_THIRDSTOP                   = 204;          /* 第三方停止流 Third party stream stopped */
		public static final int NETDEV_ALARM_FILE_END                           = 205;          /* 文件结束 File ended */
		public static final int NETDEV_ALARM_RTMP_CONNECT_FAIL                  = 206;          /* RTMP连接失败 */
		public static final int NETDEV_ALARM_RTMP_INIT_FAIL                     = 207;          /*RTMP初始化失败*/

		/* 无布防24小时有效的告警  Valid alarms within 24 hours without arming schedule */
		public static final int NETDEV_ALARM_ALLTIME_FLAG_START                 = 400;          /* 无布防告警开始标记  Start marker of alarm without arming schedule */
		public static final int NETDEV_ALARM_STOR_ERR                           = 401;          /* 存储错误  Storage error */
		public static final int NETDEV_ALARM_STOR_DISOBEY_PLAN                  = 402;          /* 未按计划存储  Not stored as planned */

		/* 无恢复类型的告警  Unrecoverable alarms */
		public static final int NETDEV_ALARM_NO_RECOVER_FLAG_START              = 500;          /* 无恢复类型告警开始标记  Start marker of unrecoverable alarm */
		public static final int NETDEV_ALARM_DISK_ERROR                         = 501;          /* 磁盘错误  Disk error */
		public static final int NETDEV_ALARM_ILLEGAL_ACCESS                     = 502;          /* 非法访问  Illegal access */
		public static final int NETDEV_ALARM_LINE_CROSS                         = 503;          /* 越界告警  Line cross */
		public static final int NETDEV_ALARM_OBJECTS_INSIDE                     = 504;          /* 区域入侵  Objects inside */
		public static final int NETDEV_ALARM_FACE_RECOGNIZE                     = 505;          /* 人脸识别  Face recognize */
		public static final int NETDEV_ALARM_IMAGE_BLURRY                       = 506;          /* 图像虚焦  Image blurry */
		public static final int NETDEV_ALARM_SCENE_CHANGE                       = 507;          /* 场景变更  Scene change */
		public static final int NETDEV_ALARM_SMART_TRACK                        = 508;          /* 智能跟踪  Smart track */
		public static final int NETDEV_ALARM_LOITERING_DETECTOR                 = 509;          /* 徘徊检测  Loitering Detector */
		public static final int NETDEV_ALARM_ALLTIME_FLAG_END                   = 600;          /* 无布防告警结束标记  End marker of alarm without arming schedule */
		public static final int NETDEV_ALARM_MEDIA_CONFIG_CHANGE                = 601;          /* 编码参数变更 media configurationchanged */
		public static final int NETDEV_ALARM_REMAIN_ARTICLE                     = 602;          /*物品遗留告警  Remain article*/
		public static final int NETDEV_ALARM_PEOPLE_GATHER                      = 603;          /* 人员聚集告警 People gather alarm*/
		public static final int NETDEV_ALARM_ENTER_AREA                         = 604;          /* 进入区域 Enter area*/
		public static final int NETDEV_ALARM_LEAVE_AREA                         = 605;          /* 离开区域 Leave area*/
		public static final int NETDEV_ALARM_ARTICLE_MOVE                       = 606;          /* 物品搬移 Article move*/
		/* 告警恢复  Alarm recover */
		public static final int NETDEV_ALARM_RECOVER_BASE                       = 1000;         /* 告警恢复基数  Alarm recover base */
		public static final int NETDEV_ALARM_MOVE_DETECT_RECOVER                = 1001;         /* 运动检测告警恢复  Motion detection alarm recover */
		public static final int NETDEV_ALARM_VIDEO_LOST_RECOVER                 = 1002;         /* 视频丢失告警恢复  Video loss alarm recover */
		public static final int NETDEV_ALARM_VIDEO_TAMPER_RECOVER               = 1003;         /* 遮挡侦测告警恢复  Tampering detection alarm recover */
		public static final int NETDEV_ALARM_INPUT_SWITCH_RECOVER               = 1004;         /* 输入开关量告警恢复  Boolean input alarm recover */
		public static final int NETDEV_ALARM_TEMPERATURE_RECOVER                = 1005;         /* 温度告警恢复  Temperature alarm recover */
		public static final int NETDEV_ALARM_AUDIO_DETECT_RECOVER               = 1007;         /* 声音检测告警恢复  Audio detection alarm recover */
		public static final int NETDEV_ALARM_DISK_ABNORMAL_RECOVER              = 1008;         /* 磁盘异常恢复 Disk abnormal recover */
		public static final int NETDEV_ALARM_DISK_OFFLINE_RECOVER               = 1009;         /* 磁盘离线恢复 Disk online recover */
		public static final int NETDEV_ALARM_DISK_ONLINE_RECOVER                = 1010;         /* 磁盘上线恢复 Disk online recover */
		public static final int NETDEV_ALARM_DISK_STORAGE_WILL_FULL_RECOVER     = 1011;         /* 磁盘存储空间即将满恢复 Disk StorageGoingfull recover */
		public static final int NETDEV_ALARM_DISK_STORAGE_IS_FULL_RECOVER       = 1012;         /* 存储空间满恢复 StorageIsfull recover */
		public static final int NETDEV_ALARM_DISK_RAID_DISABLED_RECOVER         = 1013;         /* 阵列损坏恢复 RAIDDisabled recover */
		public static final int NETDEV_ALARM_DISK_RAID_DEGRADED_RECOVER         = 1014;         /* 阵列衰退恢复 RAIDDegraded recover */

		public static final int NETDEV_ALARM_STOR_ERR_RECOVER                   = 1201;         /* 存储错误恢复  Storage error recover */
		public static final int NETDEV_ALARM_STOR_DISOBEY_PLAN_RECOVER          = 1202;         /* 未按计划存储恢复  Not stored as planned recover */

		public static final int NETDEV_ALARM_IMAGE_BLURRY_RECOVER               = 1506;         /* 图像虚焦告警恢复  Image blurry recover */
		public static final int NETDEV_ALARM_SMART_TRACK_RECOVER                = 1508;         /* 智能跟踪告警恢复  Smart track recover */
		public static final int NETDEV_ALARM_IP_CONFLICT                        = 1509;         /* IP冲突异常告警 ip conflict */
		public static final int NETDEV_ALARM_IP_CONFLICT_CLEARED                = 1510;         /* IP冲突异常告警恢复 ip conflict cleared */

		/* Smart信息 */
		public static final int NETDEV_ALARM_SMART_READ_ERROR_RATE          	= 1511;        /* 底层数据读取错误率 */
		public static final int NETDEV_ALARM_SMART_SPIN_UP_TIME                 = 1512;        /*  主轴起旋时间  */
		public static final int NETDEV_ALARM_SMART_START_STOP_COUNT             = 1513;        /* 启停计数 */
		public static final int NETDEV_ALARM_SMART_REALLOCATED_SECTOR_COUNT     = 1514;        /* 重映射扇区计数  */
		public static final int NETDEV_ALARM_SMART_SEEK_ERROR_RATE              = 1515;        /* 寻道错误率 */
		public static final int NETDEV_ALARM_SMART_POWER_ON_HOURS               = 1516;        /* 通电时间累计，出厂后通电的总时间，一般磁盘寿命三万小时 */
		public static final int NETDEV_ALARM_SMART_SPIN_RETRY_COUNT             = 1517;        /* 主轴起旋重试次数 */
		public static final int NETDEV_ALARM_SMART_CALIBRATION_RETRY_COUNT      = 1518;        /* 磁头校准重试计数 */
		public static final int NETDEV_ALARM_SMART_POWER_CYCLE_COUNT            = 1519;        /* 通电周期计数 */
		public static final int NETDEV_ALARM_SMART_POWEROFF_RETRACT_COUNT       = 1520;        /* 断电返回计数 */
		public static final int NETDEV_ALARM_SMART_LOAD_CYCLE_COUNT             = 1521;        /* 磁头加载计数 */
		public static final int NETDEV_ALARM_SMART_TEMPERATURE_CELSIUS          = 1522;        /* 温度 */
		public static final int NETDEV_ALARM_SMART_REALLOCATED_EVENT_COUNT      = 1523;        /* 重映射事件计数 */
		public static final int NETDEV_ALARM_SMART_CURRENT_PENDING_SECTOR       = 1524;        /* 当前待映射扇区计数 */
		public static final int NETDEV_ALARM_SMART_OFFLINE_UNCORRECTABLE        = 1525;        /* 脱机无法校正的扇区计数 */
		public static final int NETDEV_ALARM_SMART_UDMA_CRC_ERROR_COUNT         = 1526;        /* 奇偶校验错误率  */
		public static final int NETDEV_ALARM_SMART_MULTI_ZONE_ERROR_RATE        = 1527;        /* 多区域错误率 */

		public static final int NETDEV_ALARM_INVALID                            = 0xFFFF;      /* 无效值  Invalid value */
	}

 	/**
 	 * @enum tagNETDEVPlayControl
 	 * @brief Playback control commands Enumeration definition
 	 * @attention  None
 	 */
 	public class NETDEV_VOD_PLAY_CTRL_E{
 		public static final int NETDEV_PLAY_CTRL_PLAY           = 0;           	/* Play */
 		public static final int NETDEV_PLAY_CTRL_PAUSE          = 1;           	/* Pause */
 		public static final int NETDEV_PLAY_CTRL_RESUME         = 2;          	/* Resume */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYTIME    = 3;           	/* Obtain playing time */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYTIME    = 4;           	/* Configure playing time */
 		public static final int NETDEV_PLAY_CTRL_GETPLAYSPEED   = 5;          	/* Obtain playing speed */
 		public static final int NETDEV_PLAY_CTRL_SETPLAYSPEED   = 6;        	/* Configure playing speed */
 	}

 	/**
 	 * @enum tagNETDEVVodPlayStatus
 	 * @brief Playback and download status Enumeration definition
 	 * @attention None
 	 */
 	public class NETDEV_VOD_PLAY_STATUS_E
 	{
		/** 播放状态  Play status */
		public static final int NETDEV_PLAY_STATUS_16_BACKWARD          = 0;            /* 16倍速后退播放  Backward at 16x speed */
		public static final int NETDEV_PLAY_STATUS_8_BACKWARD           = 1;            /* 8倍速后退播放  Backward at 8x speed */
		public static final int NETDEV_PLAY_STATUS_4_BACKWARD           = 2;            /* 4倍速后退播放  Backward at 4x speed */
		public static final int NETDEV_PLAY_STATUS_2_BACKWARD           = 3;            /* 2倍速后退播放  Backward at 2x speed */
		public static final int NETDEV_PLAY_STATUS_1_BACKWARD           = 4;            /* 正常速度后退播放  Backward at normal speed */
		public static final int NETDEV_PLAY_STATUS_HALF_BACKWARD        = 5;            /* 1/2倍速后退播放  Backward at 1/2 speed */
		public static final int NETDEV_PLAY_STATUS_QUARTER_BACKWARD     = 6;            /* 1/4倍速后退播放  Backward at 1/4 speed */
		public static final int NETDEV_PLAY_STATUS_QUARTER_FORWARD      = 7;            /* 1/4倍速播放  Play at 1/4 speed */
		public static final int NETDEV_PLAY_STATUS_HALF_FORWARD         = 8;            /* 1/2倍速播放  Play at 1/2 speed */
		public static final int NETDEV_PLAY_STATUS_1_FORWARD            = 9;            /* 正常速度前进播放  Forward at normal speed */
		public static final int NETDEV_PLAY_STATUS_2_FORWARD            = 10;           /* 2倍速前进播放  Forward at 2x speed */
		public static final int NETDEV_PLAY_STATUS_4_FORWARD            = 11;           /* 4倍速前进播放  Forward at 4x speed */
		public static final int NETDEV_PLAY_STATUS_8_FORWARD            = 12;           /* 8倍速前进播放  Forward at 8x speed */
		public static final int NETDEV_PLAY_STATUS_16_FORWARD           = 13;           /* 16倍速前进播放  Forward at 16x speed */
		public static final int NETDEV_PLAY_STATUS_2_FORWARD_IFRAME     = 14;           /* 2倍速前进播放(I帧) Forward at 2x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_4_FORWARD_IFRAME     = 15;           /* 4倍速前进播放(I帧) Forward at 4x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_8_FORWARD_IFRAME     = 16;           /* 8倍速前进播放(I帧) Forward at 8x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_16_FORWARD_IFRAME    = 17;           /* 16倍速前进播放(I帧) Forward at 16x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_2_BACKWARD_IFRAME    = 18;           /* 2倍速后退播放(I帧) Backward at 2x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_4_BACKWARD_IFRAME    = 19;           /* 4倍速后退播放(I帧) Backward at 4x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_8_BACKWARD_IFRAME    = 20;           /* 8倍速后退播放(I帧) Backward at 8x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_16_BACKWARD_IFRAME   = 21;           /* 16倍速后退播放(I帧) Backward at 16x speed(I frame) */
		public static final int NETDEV_PLAY_STATUS_INTELLIGENT_FORWARD  = 22;           /* 智能播放 Intelligent forward */
		public static final int NETDEV_PLAY_STATUS_1_FRAME_FORWD        = 23;           /* 单帧前进播放  Forward at 1 frame speed */
		public static final int NETDEV_PLAY_STATUS_1_FRAME_BACK         = 24;           /* 单帧后退播放 Backward at 1 frame speed */

		public static final int NETDEV_PLAY_STATUS_40_FORWARD           = 25;           /* 40倍速前进播放 Forward at 40x speed*/

		public static final int NETDEV_PLAY_STATUS_32_FORWARD_IFRAME    = 26;           /* 32倍速抽帧前进播放 Forward at 32x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_32_BACKWARD_IFRAME   = 27;           /* 32倍速抽帧后退播放 Backward at 32x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_64_FORWARD_IFRAME    = 28;           /* 64倍速抽帧前进播放 Forward at 64x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_64_BACKWARD_IFRAME   = 29;           /* 64倍速抽帧后退播放 Backward at 64x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_128_FORWARD_IFRAME   = 30;           /* 128倍速抽帧前进播放 Forward at 128x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_128_BACKWARD_IFRAME  = 31;           /* 128倍速抽帧后退播放 Backward at 128x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_256_FORWARD_IFRAME   = 32;           /* 256倍速抽帧前进播放 Forward at 256x speed(I frame)*/
		public static final int NETDEV_PLAY_STATUS_256_BACKWARD_IFRAME  = 33;           /* 256倍速抽帧后退播放 Backward at 256x speed(I frame)*/

		public static final int NETDEV_PLAY_STATUS_32_FORWARD           = 34;           /* 32倍速前进播放 */
		public static final int NETDEV_PLAY_STATUS_32_BACKWARD          = 35;           /* 32倍速后退播放 */
 	}

 	public class NETDEV_VOD_PTZ_CMD_E
 	{
 		public static final int NETDEV_PTZ_FOCUSNEAR_STOP       = 0x0201;       	/* Focus near stop */
 		public static final int NETDEV_PTZ_FOCUSNEAR            = 0x0202;       	/* Focus near */
 		public static final int NETDEV_PTZ_FOCUSFAR_STOP        = 0x0203;       	/* Focus far stop */
 		public static final int NETDEV_PTZ_FOCUSFAR             = 0x0204;       	/* Focus far */
 		public static final int NETDEV_PTZ_ZOOMTELE             = 0x0302;       	/* Zoom in */
 		public static final int NETDEV_PTZ_ZOOMWIDE             = 0x0304;       	/* Zoom out */
 		public static final int NETDEV_PTZ_TILTUP               = 0x0402;       	/* Tilt up */
 		public static final int NETDEV_PTZ_TILTDOWN             = 0x0404;       	/* ilt down */
 		public static final int NETDEV_PTZ_PANRIGHT             = 0x0502;       	/* Pan right */
 		public static final int NETDEV_PTZ_PANLEFT              = 0x0504;      		/* Pan left */
 		public static final int NETDEV_PTZ_LEFTUP               = 0x0702;       	/* Move up left */
 		public static final int NETDEV_PTZ_LEFTDOWN             = 0x0704;       	/* Move down left */
 		public static final int NETDEV_PTZ_RIGHTUP              = 0x0802;       	/* Move up right */
 		public static final int NETDEV_PTZ_RIGHTDOWN            = 0x0804;       	/* Move down right */
 		public static final int NETDEV_PTZ_ALLSTOP              = 0x0901;       	/* All-stop command word */
 	}

	public class NETDEV_TIME_ZONE_E
	{
		public static final int NETDEV_TIME_ZONE_W1200 = 0;              /* W12 */
		public static final int NETDEV_TIME_ZONE_W1100 = 1;              /* W11 */
		public static final int NETDEV_TIME_ZONE_W1000 = 2;              /* W10 */
		public static final int NETDEV_TIME_ZONE_W0900 = 3;              /* W9 */
		public static final int NETDEV_TIME_ZONE_W0800 = 4;              /* W8 */
		public static final int NETDEV_TIME_ZONE_W0700 = 5;              /* W7 */
		public static final int NETDEV_TIME_ZONE_W0600 = 6;              /* W6 */
		public static final int NETDEV_TIME_ZONE_W0500 = 7;              /* W5 */
		public static final int NETDEV_TIME_ZONE_W0430 = 8;              /* W4:30 */
		public static final int NETDEV_TIME_ZONE_W0400 = 9;              /* W4 */
		public static final int NETDEV_TIME_ZONE_W0330 = 10;             /* W3:30 */
		public static final int NETDEV_TIME_ZONE_W0300 = 11;             /* W3 */
		public static final int NETDEV_TIME_ZONE_W0200 = 12;             /* W2 */
		public static final int NETDEV_TIME_ZONE_W0100 = 13;             /* W1 */
		public static final int NETDEV_TIME_ZONE_0000  = 14;             /* W0 */
		public static final int NETDEV_TIME_ZONE_E0100 = 15;             /* E1 */
		public static final int NETDEV_TIME_ZONE_E0200 = 16;             /* E2 */
		public static final int NETDEV_TIME_ZONE_E0300 = 17;             /* E3 */
		public static final int NETDEV_TIME_ZONE_E0330 = 18;             /* E3:30 */
		public static final int NETDEV_TIME_ZONE_E0400 = 19;             /* E4 */
		public static final int NETDEV_TIME_ZONE_E0430 = 20;             /* E4:30 */
		public static final int NETDEV_TIME_ZONE_E0500 = 21;             /* E5 */
		public static final int NETDEV_TIME_ZONE_E0530 = 22;             /* E5:30 */
		public static final int NETDEV_TIME_ZONE_E0545 = 23;             /* E5:45 */
		public static final int NETDEV_TIME_ZONE_E0600 = 24;             /* E6 */
		public static final int NETDEV_TIME_ZONE_E0630 = 25;             /* E6:30 */
		public static final int NETDEV_TIME_ZONE_E0700 = 26;             /* E7 */
		public static final int NETDEV_TIME_ZONE_E0800 = 27;             /* E8 */
		public static final int NETDEV_TIME_ZONE_E0900 = 28;             /* E9 */
		public static final int NETDEV_TIME_ZONE_E0930 = 29;             /* E9:30 */
		public static final int NETDEV_TIME_ZONE_E1000 = 30;             /* E10 */
		public static final int NETDEV_TIME_ZONE_E1100 = 31;             /* E11 */
		public static final int NETDEV_TIME_ZONE_E1200 = 32;             /* E12 */
		public static final int NETDEV_TIME_ZONE_E1300 = 33;             /* E13 */
		public static final int NETDEV_TIME_ZONE_W0930 = 34;              /* W9:30 */
		public static final int NETDEV_TIME_ZONE_E0830 = 35;             /* E8:30 */
		public static final int NETDEV_TIME_ZONE_E0845 = 36;             /* E8:45 */
		public static final int NETDEV_TIME_ZONE_E1030 = 37;             /* E10:30 */
		public static final int NETDEV_TIME_ZONE_E1245 = 38;             /* E12:45 */
		public static final int NETDEV_TIME_ZONE_E1400 = 39;             /* E14 */

		public static final int NETDEV_TIME_ZONE_INVALID = 0xFF;         /* Invalid value */
	}

 	static AlarmCallBack_PF pfAlarmCallBack;
 	static ExceptionCallBack_PF pfExceptionCallBack;
	static DecodeAudioCallBack_PF pfDecodeAudioCallBack = null;

	/**
	* Callback function to receive alarm information
	* @param [IN] iUserID              	User login ID
	* @param [IN] iChannelID           	Channel number
	* @param [IN] iAlarmType          	Alarm type
	* @param [IN] tAlarmTime           	Alarm time
	* @param [IN] strName              	Alarm source name
	* @param [IN] iBufLen             	Length of structure for alarm information
	* @note*/
	/*public void alarmCallBack(int iUserID,int iChannelID, int iAlarmType, int tAlarmTime, String strName,int iBufLen) {
		pfAlarmCallBack.alarmCallBack(iUserID, iChannelID, iAlarmType, tAlarmTime, strName, iBufLen) ;
    }

	public native int  NETDEV_SetAlarmCallBack(int iUserID, String strAlarmMessCallBack, int iUserData);
	public int  NETDEV_Android_SetAlarmCallBack(int iUserID, AlarmCallBack_PF strAlarmMessCallBack, int iUserData){
		pfAlarmCallBack = strAlarmMessCallBack;
		return NETDEV_SetAlarmCallBack(iUserID,"alarmCallBack",0);
	}*/

	public void alarmCallBack(int iUserID, int iAlarmSrcType, int iChannelID, int dwIndex, int iAlarmType, int tAlarmTime, String strName) {
		pfAlarmCallBack.alarmCallBack(iUserID, iAlarmSrcType, iChannelID, dwIndex, iAlarmType, tAlarmTime, strName) ;
	}

	public static native int  NETDEV_SetAlarmCallBackV2(int iUserID, String strAlarmMessCallBack, int iUserData);
	public static int  NETDEV_Android_SetAlarmCallBack(int iUserID, AlarmCallBack_PF strAlarmMessCallBack, int iUserData){
		pfAlarmCallBack = strAlarmMessCallBack;
		return NETDEV_SetAlarmCallBackV2(iUserID,"alarmCallBack",0);
	}

	public void exceptionCallBack(int iUserID,int iExceptionType,int iExceptionHandle) {
		pfExceptionCallBack.exceptionCallBack(iUserID, iExceptionType, iExceptionHandle) ;
	}

	public static native int  NETDEV_SetExceptionCallBack(String strExceptionCallBack, int iUserData);
	public static int  NETDEV_Android_SetExceptionCallBack(ExceptionCallBack_PF strExceptionCallBack, int iUserData){
		pfExceptionCallBack = strExceptionCallBack;
		return NETDEV_SetExceptionCallBack("exceptionCallBack",0);
	}

 	/**
 	* SDK initialization
 	* @return 1 means success, and any other value means failure.
 	* @note Thread not safe
 	*/
 	public  native int NETDEV_Init();

 	/**
 	* DK uninitialization
 	* @return  1 means success, and any other value means failure.
 	* @note Thread not safe
 	*/
 	public static native int NETDEV_Cleanup();

 	/**
 	*  User login
 	* @param [IN]  DevIP         IP Device IP
 	* @param [IN]  DevPort       Device server port
 	* @param [IN]  UserName      Username
 	* @param [IN]  Password      Password
 	* @param [OUT] oDeviceInfo   device information
 	* @return  Returned user login ID. 0 indicates failure, and other values indicate the user ID.
 	* @note
 	*/
	public static native int NETDEV_Login(String DevIP, int DevPort, String Username, String Password, NETDEV_DEVICE_INFO_S oDeviceInfo);

	public static native int NETDEV_LoginV2(int lpUerID, NETDEV_LOGIN_INFO_S oLoginInfo);

	public static native String NETDEV_GetStreamUrl(int lpUserID, int dwChannelID, int dwStreamType);

	public static native int NETDEV_FastRealPlayByUrl(int lpUerID, String StreamUrl, NETDEV_PREVIEWINFO_S oPreviewInfo, int dwWinIndex);

	public static native String NETDEV_GetReplayUrl(int lpUserID, int dwChannelID, int dwStreamType);

	public static native int NETDEV_FastPlayBackByUrl(int lpUerID, String StreamUrl, NETDEV_PLAYBACKCOND_S pstPlayBackInfo, int dwWinIndex);

    public static native int NETDEV_CaptureNoPreview(int lpUerID, int dwChannelID, int dwStreamType, String pszFileName, int dwCaptureMode);
	/**
	*  User logout
	* @param [IN] lpUserID    用户登录User login ID
	* @return 1 means success, and any other value means failure.
	* @note
	*/
    public static native int NETDEV_Logout(int lpUerID);

    /**
    * Query channel capabilities
    * @param [IN]    lpUserID           User login ID
    * @param [INOUT] pdwChlCount        Number of channels
    * @return ArrayList    List of channel capabilities.
    * @note
    */
    public static native ArrayList<NETDEV_VIDEO_CHL_DETAIL_INFO_S> NETDEV_QueryVideoChlDetailList(int lpUserID, int dwChlCount);

    /**
    *   Get error code
    * @return Error codes
    */
    public static native int NETDEV_GetLastError();

    /**
    *  Modify image display ratio
    */
    public static native void NETDEV_SetRenderSurface(Surface view);

    /**
    * Start Live view
    * @param [IN]  lpUserID             User login ID
    * @param [IN]  oPreviewInfo       	see enumeration
    * @return  Returned user login ID. 0 indicates failure, and other values indicate the user ID.
    * @note
    */
    public  static native int NETDEV_RealPlay(int lpUerID,NETDEV_PREVIEWINFO_S oPreviewInfo, int dwWinIndex);

    /**
    *  Stop Live view
    * @param [IN]  lpPlayID     ID Preview ID
    * @return 1 means success, and any other value means failure.
    * @note  Stop the live view started by NETDEV_RealPlay
    */
    public  static native int NETDEV_StopRealPlay(int lpPlayID, int dwWinIndex);

    /**
    * Live view snapshot
    * @param [IN]  lpPlayID         Preview\playback handle
    * @param [IN]  pszFileName      File path to save images (including file name)
    * @param [IN]  dwCaptureMode    Image saving format, see #NETDEV_PICTURE_FORMAT_E
    * @return means success, and any other value means failure.
    * @note  File format suffix is not required in the file name
    */
    public static native int NETDEV_CapturePicture(int lpPlayID, String pszFileName, int dwCaptureMode);

	public static native int NETDEV_SaveRealData(int lpPlayID, String pszFileName, int dwFormat);
	public static native int NETDEV_StopSaveRealData(int lpPlayID);

	/***
	 * Get System Time
	 * @param [IN] lpUserID
	 * @param [IN] stSystemTimeInfo
	 * @return
	 */
	public static native int NETDEV_GetSystemTimeCfg(int lpUserID, NETDEV_TIME_CFG_S stSystemTimeInfo);

	/***
	 * Set System Time
	 * @param [IN] lpUserID
	 * @param [OUT] stSystemTimeInfo
	 * @return
	 */
	public static native int NETDEV_SetSystemTimeCfg(int lpUserID, NETDEV_TIME_CFG_S stSystemTimeInfo);

    /**
     * PTZ control operation (preview required)
     * @param [IN]  lpPlayHandle         Live preview handle
     * @param [IN]  dwPTZCommand         PTZ control commands, see #NETDEV_PTZ_E
     * @param [IN]  dwSpeed              Speed of PTZ control, which is configured according to the speed control value of different decoders. Value ranges from 1 to 9.
     * @return TRUE means success, and any other value means failure.
     * @note
     */
     public static native int NETDEV_PTZControl(int lpPlayID, int dwPTZCommand, int dwSpeed);

    /**
    * Get PTZ preset List
    * @param [IN]  lpUserID         	User login ID
    * @param [IN]  dwChannelID       	Channel ID
    * @param [OUT] dwPresetIDList       Preset ID list
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_GetPTZPresetList(int lpUserID, int dwChannelID, int[] dwPresetIDList);

    /**
    * PTZ preset operation (preview required)
    * @param [IN]  lpPlayHandle         Live preview handle
    * @param [IN]  dwPTZPresetCmd 		Preset Control commond
    * @param [IN]  pszPresetName     	Preset name
    * @param [IN]  dwPresetID           Preset number (starting from 0). Up to 255 presets are supported.
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_PTZPreset(int lpPlayID, int  dwPTZPresetCmd, String szPresetName, int dwPresetID);

    /**
    * PTZ preset operation (preview not required)
    * @param [IN]  lpUserID             User login ID
    * @param [IN]  dwChannelID          Channel number
    * @param [IN]  dwPTZPresetCmd       PTZ preset operation commands, see NETDEV_PTZ_PRESETCMD_E
    * @param [IN]  pszPresetName        Preset name
    * @param [IN]  dwPresetID           Preset number (starting from 0). Up to 255 presets are supported.
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_PTZPreset_Other(int lpUserID, int dwChannelID, int  dwPTZPresetCmd, String szPresetName, int dwPresetID);

    /**
     * User login to cloud accounts
     * @param [IN]  CloudUrl       	Cloud server URL
     * @param [IN]  CloudUser       Cloud account name
     * @param [IN]  Cloudpassword   Cloud account password
     * @return  Returned user ID. 0 means failure, any other value is a user ID.
     * @note
     */
     public static native int NETDEV_LoginCloud(String CloudUrl, String CloudUser, String Cloudpassword);


    /**
    *  Cloud device login with dynamic password
    * @param [IN]  lpUserID             Cloud account login ID
    * @param [IN]  CloudDevInfo         Cloud device login info
    * @param [OUT] cloudDeviceInfo      device info
    * @return  Returned user ID. 0 means failure, any other value is a user ID.
    * @note
        1、pCloudInfo 中szDevicePassword字段不需填写。The szDevicePassword field in pCloudInfo needs not to be filled in.
    */
    public static native int NETDEV_LoginByDynamic(int lpCloudID, NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo, NETDEV_DEVICE_INFO_S cloudDeviceInfo);

    /**
    * 云端设备登录 Cloud device login
    * @param [IN]  lpUserID             Cloud account login ID
    * @param [IN]  CloudDevInfo         Cloud device login info
    * @param [OUT] cloudDeviceInfo      device info
    * @return Returned user ID. 0 means failure, any other value is a user ID.
    * @note
        1、The szDevicePassword field in pCloudInfo must be filled in.
    */
    public static native int NETDEV_LoginCloudDev(int lpCloudID, NETDEV_CLOUD_DEV_LOGIN_S CloudDevInfo, NETDEV_DEVICE_INFO_S cloudDeviceInfo);

    /**
    * Query device list under a cloud account
    * @param [IN]  lpUserID            User login ID
    * @return  0 means failure, any other value will be used as parameter of functions including NETDEV_FindNextCloudDevInfo and NETDEV_FindCloseDevList.
    * @note
    */
    public static native int NETDEV_FindCloudDevList(int lpCloudID);

	public static native int[] NETDEV_QuickSearch(int lUserID, int dwChannelID, NETDEV_MONTH_INFO_S oMonthInfo, NETDEV_MONTH_STATUS_S oMonthStatus);

    /**
    *   Obtain info about detected devices one by one
    * @param [IN]  lpCloudID         Search handle
    * @param [OUT] pstDevInfo        Pointer to saved device info
    * @return  1 means success, and any other value means failure.
    * @note returned failure indicates the end of search.
    */
    public static native int NETDEV_FindNextCloudDevInfo(int lpFindID,NETDEV_CLOUD_DEV_INFO_S clouddeviceinfo);

    /**
    *  Stop search and release resource
    * @param [IN] lpCloudID  File search handle
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int  NETDEV_FindCloseCloudDevList(int lpCloudID);

    /**
    * Query recording files according to file type and time
    * @param [IN]  lpUserID      	User login ID
    * @param [IN]  pstFindCond  	Search condition
    * @return Recording search service number. 0 means failure. Other values are used as the handle parameters of functions like NETDEV_FindClose.
    * @note
    */
    public static native int NETDEV_FindFile(int lpUserID, NETDEV_FILECOND_S pstFindCond);

    /**
    * Obtain the information of found files one by one.
    * @param [IN]  lpFindHandle     File search handle
    * @param [OUT] pstFindData      Pointer to save file information
    * @return TRUE means success, and any other value means failure.
    * @note  A returned failure indicates the end of search.
    */
    public static native int NETDEV_FindNextFile(int lpFindHandle, NETDEV_FINDDATA_S pstFindData);

	public static native int NETDEV_SetPictureFluency(int lpUserID, int dwFluency);
    /**
    * Close file search and release resources
    * @param [IN] lpFindHandle  File search handle
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_FindClose(int lpFindHandle);

    /**
     * add a cloud device to a cloud account
     * @param [IN]  lpUserID             User login ID
     * @param [IN]  szDevRegisterCode    device register code
     * @param [IN]  szDevName           device name
     * @param [OUT] stDevBase           basic information of cloud device
     * @return TRUE means success, and any other value means failure.
     * @note
     */
     public static native int NETDEV_AddCloudDevice(int lpUserID, String szDevRegisterCode, String szDevName, NETDEV_CLOUD_DEV_BASE_INFO_S stDevBase);

     /**
     * delete a cloud device under a cloud account
     * @param [IN]  lpUserID            User login ID
     * @param [IN]  szDevUserName       cloud device ID
     * @return TRUE means success, and any other value means failure.
     * @note
     */
     public static native int NETDEV_DeleteCloudDevice(int lpUserID,String szDevUserName);

    /**
    * Play back recording by time.
    * @param [IN] lpUserID          User login ID
    * @param [IN] pstPlayBackCond   Pointer to playback-by-time structure, see LPNETDEV_PLAYBACKCOND_S
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_PlayBackByTime(int lpUserID, NETDEV_PLAYBACKCOND_S pstPlayBackInfo, int dwWinIndex);

    /**
    * Stop playback service
    * @param [IN] lpPlayHandle   Playback handle
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_StopPlayBack(int lpPlayHandle, int dwWinIndex);

    /**
    * Control recording playback status.
    * @param [IN]  lpPlayHandle     Playback or download handle
    * @param [IN]  dwControlCode    Command for controlling recording playback status, see NETDEV_VOD_PLAY_CTRL_E
    * @param [INOUT]  lpBuffer     Pointer to input/output parameters. For playing speed, see NETDEV_VOD_PLAY_STATUS_E. The type of playing time: INT64.
    * @return TRUE means success, and any other value means failure.
    * @note When playing, pause or resume videos, set IpBuffer as NULL.
    */
    public static native int NETDEV_PlayBackControl(int lpPlayHandle, int dwControlCode, NETDEV_PLAYBACKCONTROL_S lpBuffer);

    /**
    * Download recordings by time
    * @param [IN]  lpUserID          	User login ID
    * @param [IN]  pstPlayBackCond   	Pointer to playback-by-time structure, see LPNETDEV_PLAYBACKCOND_S
    * @param [IN]  *pszSaveFileName    	Downloaded file save path on PC, must be an absolute path (including file name)
    * @param [IN]  dwFormat         	Recording file saving format
    * @return Download handle. 0 means failure. Other values are used as the handle parameters of functions like NETDEV_StopGetFile.
    * @note
    */
    public static native int NETDEV_DownloadFile(int lpUserID, NETDEV_PLAYBACKCOND_S lpBuffer, String szSaveFileName, int dwFormat);

    /**
    * Stop downloading recording files
    * @param [IN]  lpPlayHandle  Playback handle
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_StopDownloadFile(int lpPlayHandle);

    /**
    * Get device basic info
    * @param [IN]  lpUserID          	User login ID
    * @param [IN]  dwChannelID          Channel ID
    * @param [OUT]  stDevinfo           Device Info
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_GetDeviceInfo(int lpUserID, int dwChannelID, NETDEV_DEVICE_BASICINFO_S stDevinfo);

    public static native int initialize();
    public static native int rendererRender(int dwWinIndex);
    public static native int setRendererViewport(int w, int h);
    public static native int initializeRenderer(int dwWinIndex);
	public static native int Scale(float scaleRatio, float x, float y, int dwWinIndex);


    public static native int NETDEV_SetClientID(String strClientID);

	public void DecodeAudioDataCallBack(int lpVoiceComHandle, byte[] voiceData, int length, int u32WaveFormat)
    {
    	pfDecodeAudioCallBack.DecodeAudioDataCallBack(lpVoiceComHandle, voiceData, length, u32WaveFormat);
    }

    /**
    * Start input voice server
    * @param [IN]  lpUserID                 User login ID
    * @param [IN]  dwChannelID              Channel ID
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_StartInputVoiceSrv(int lpUserID,int dwChannelID);
	public static int NETDEV_Android_StartInputVoiceSrv(int lpUserID,int dwChannelID)
	{
		pfDecodeAudioCallBack = new DecodeAudioCallBack_PF();
		return NETDEV_StartInputVoiceSrv(lpUserID,dwChannelID);
	}

    /**
    * Stop input voice server
    * @param [IN]  lpVoiceComHandle         Two-way audio handle
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_StopInputVoiceSrv(int lpVoiceComHandle);
	public static int NETDEV_Android_StopInputVoiceSrv(int lpVoiceComHandle) {
		pfDecodeAudioCallBack.release();
		return NETDEV_StopInputVoiceSrv(lpVoiceComHandle);
	}

    /**
    * input voice Data
    * @param [IN]  lpVoiceComHandle         Two-way audio handle
    * @param [IN]  lpDataBuf         		Data buffer
    * @param [IN]  dwDataLen         		Data length
    * @param [IN]  pstVoiceParam         	Voice param
    * @return TRUE means success, and any other value means failure.
    * @note
    */
    public static native int NETDEV_InputVoiceData(int lpVoiceComHandle,byte[] lpDataBuf,int dwDataLen,NETDEV_AUDIO_SAMPLE_PARAM_S stVoiceParam);

	/**
	* set T2U Payload
	* @param [IN]   dwT2UPayload         [101-1500] ，default: 1500
	* @return TRUE means success, and any other value means failure.
	*/
    public static native int NETDEV_SetT2UPayLoad(int dwT2UPayload);
}

