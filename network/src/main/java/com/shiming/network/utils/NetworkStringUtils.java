package com.shiming.network.utils;


/**
 * Created by shiming on 2017/4/3.
 */
public class NetworkStringUtils {

    /**
     * 判断字符是否为null或空串.
     *
     * @param src 待判断的字符
     * @return
     */
    public static boolean isEmpty(String src) {
        return src == null || "".equals(src.trim())
                || "null".equalsIgnoreCase(src);
    }



}
