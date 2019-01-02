package com.shiming.network;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 14:23
 */

public interface IMvpView {

    /**
     * 数据失败的同一处理
     * @param errorCode
     * @param errorMsg
     */
    void getDataFail(String errorCode, String errorMsg);

    /**
     * 发成异常的同一处理
     * @param e
     */
    void onError(Throwable e);
}
