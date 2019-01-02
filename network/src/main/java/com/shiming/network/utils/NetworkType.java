package com.shiming.network.utils;

/**
 * Created by shiming on 2017/4/3.
 */
public class NetworkType {
    /**
     * 无网络连接
     */
    public static final int NONE = -1;

    /**
     * 通过WIFI连接网络
     */
    public static final int NET = 0;

    /**
     * 通过移动, 联通GPRS连接网络
     */
    public static final int CMWAP = 1;

    /**
     * 通过电信GPRS连接网络
     */
    public static final int CTWAP = 2;

}
