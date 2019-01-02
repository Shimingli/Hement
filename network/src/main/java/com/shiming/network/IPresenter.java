package com.shiming.network;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 14:24
 */

public interface IPresenter<V extends IMvpView> {

    /**
     * 和view结合
     * @param mvpView
     */
    void attachView(V mvpView);

    /**
     * 和view 断开
     */
    void detachView();
}
