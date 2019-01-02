package com.shiming.hement.lifecycle;

import com.jakewharton.rxrelay2.PublishRelay;
import com.shiming.hement.utils.Events;

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

public class SyncRxBus {

    private static SyncRxBus instance;
    private final PublishRelay<SyncResponse> relay;

    public static synchronized SyncRxBus getInstance() {
        if (instance == null) {
            instance = new SyncRxBus();
        }
        return instance;
    }

    private SyncRxBus() {
        relay = PublishRelay.create();
    }

    public void post(SyncResponseEventType eventType, Events comment) {
        relay.accept(new SyncResponse(eventType, comment));
    }

    public Observable<SyncResponse> toObservable() {
        return relay;
    }
}
