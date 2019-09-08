package com.sdk;

/**
 * Live preview information
 */
public class NETDEV_PREVIEWINFO_S {
    /**
     * Channel ID. the value can be modified at the interface
     */
	public int dwChannelID;
    /**
     * Stream type. 0: 主码流  1: 子码流  2: 第三码流
     */
    public int dwStreamIndex;
    /**
     * Transport protocol. 1: TCP   2: UDP
     */
    public int dwLinkMode;
    public int dwFluency;
    public int dwStreamMode;
    public int dwLiveMode;
    public int dwDisTributeCloud;
}
