package com.shiming.hement.utils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/4 18:48
 */

public class RxEventBus {
    private final BackpressureStrategy mBackpressureStrategy = BackpressureStrategy.BUFFER;
    private final PublishSubject<Object> mBusSubject;

    public RxEventBus() {
        mBusSubject = PublishSubject.create();
    }
    public void post(Object event) {
        mBusSubject.onNext(event);
    }
    public Observable<Object> toObservable() {
        return mBusSubject;
    }
    public Flowable<Object> observable() {
        return mBusSubject.toFlowable(mBackpressureStrategy);
    }
    public <T> Flowable<T> filteredObservable(final Class<T> eventClass) {
        return mBusSubject.ofType(eventClass).toFlowable(mBackpressureStrategy);
    }
}
