package com.shiming.hement.ui.life_cycle_demo;

import com.jakewharton.rxrelay2.PublishRelay;


import io.reactivex.Observable;

/**
 * <p>
 *  这样的使用的地方，假如
 *  PublishRelay 双向发布的
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/19 14:35
 */

public class ExtendSyncRxBus {

    private static ExtendSyncRxBus instance;
    private  PublishRelay<Object> relay;

    public static synchronized ExtendSyncRxBus getInstance() {
        if (instance == null) {
            synchronized (ExtendSyncRxBus.class) {
                if (instance == null) {
                    instance = new ExtendSyncRxBus();
                }
            }
        }
        return instance;
    }

    private ExtendSyncRxBus() {
        relay = PublishRelay.create();
    }
    public void post(Object event) {
        relay.accept(event);
    }

    public Observable<Object>  toObservable() {
        return relay;
    }
}
