package com.shiming.hement.ui.life_cycle_demo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;



import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/18 10:36
 */

public class HandleEventObserver implements LifecycleObserver {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private String TAG="HandleEventObserver";


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Timber.tag(TAG).d("onCreate lifecycle event.");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Timber.tag(TAG).d("onStart lifecycle event.");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Timber.tag(TAG).d("onResume lifecycle event.");
        disposables.add(ExtendSyncRxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object obj) throws Exception {
                        ExtendEvents extendEvents = (ExtendEvents) obj;
                        handlerEvents(extendEvents);
                    }


                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.tag(TAG).d("onResume lifecycle event.  Throwable=  %s",throwable);
                    }
                }));
    }
    protected void handlerEvents(ExtendEvents extendEvents) {

    }
    /*
    如果 在实际上我们 需要在任务中做耗时操作的时候
     */
//    private void handlerEvents(ExtendEvents extendEvents){
//        Timber.tag(TAG).d("received sync comment success event for comment %s", extendEvents);
//        disposables.add(Completable.fromAction(new Action() {
//            @Override
//            public void run() throws Exception {
//                doChildThreadWork();
//                Thread thread = Thread.currentThread();
//                Timber.tag(TAG).i(thread.toString());
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action() {
//                               @Override
//                               public void run() throws Exception {
//                                   Timber.tag(TAG).d("  success");
//                               }
//                           },
//                        new Consumer<Throwable>() {
//                            @Override
//                            public void accept(Throwable throwable) throws Exception {
//                                Timber.tag(TAG).e(throwable, "  error");
//                            }
//                        }));
//    }
//    /**
//     * 可以做些子线程的数据操作
//     */
//    private void doChildThreadWork() {
//
//    }



    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Timber.tag(TAG).d("onPause lifecycle event.");
        // 如果不在这里
        disposables.clear();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Timber.tag(TAG).d("onStop lifecycle event.");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Timber.tag(TAG).d("onDestroy lifecycle event.");
    }

    /**
     * 所有的事件都会输出
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
        Timber.tag(TAG).d("onAny 所有的事件都会输出");
    }
}
