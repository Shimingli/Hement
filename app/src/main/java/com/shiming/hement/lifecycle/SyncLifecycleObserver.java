package com.shiming.hement.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.widget.Toast;

import com.shiming.hement.utils.Events;

import org.w3c.dom.Comment;

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
 * @since 2018/12/18 10:44
 */

public class SyncLifecycleObserver implements LifecycleObserver {

    private static final String TAG = "SyncLifecycleObserver";

    private final CompositeDisposable disposables = new CompositeDisposable();


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
        disposables.add(SyncRxBus.getInstance().toObservable()
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        handleSyncResponse(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.tag(TAG).d("onResume lifecycle event.  Throwable=  %s",throwable);
                    }
                }));
    }

    private void handleSyncResponse(SyncResponse response) {
        if (response.eventType == SyncResponseEventType.SUCCESS) {
            onSyncSuccess(response.comment);
        } else {
            onSyncFailed(response.comment);
        }
    }

    private void onSyncSuccess(Events comment) {
        Timber.tag(TAG).d("received sync comment success event for comment %s", comment);
        disposables.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                doWhat();
                Thread thread = Thread.currentThread();
                Timber.tag(TAG).i(thread.toString());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                                   Timber.tag(TAG).d("  success");
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.tag(TAG).e(throwable, "  error");
                            }
                        }));
    }

    /**
     * 可以做些子线程的数据操作
     */
    private void doWhat() {

    }
    private void onSyncFailed(Events comment) {
        Timber.tag(TAG).d("received sync comment failed event for comment %s", comment);
        disposables.add(Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                doWhat();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                                   Timber.tag(TAG).d("  success");
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.tag(TAG).e(throwable, "  error");
                            }
                        }));
    }

    /**
     * 12-19 15:35:52.317 29721-29721/com.shiming.hement D/SyncLifecycleObserver: received sync comment success event for comment Events=我是NewRxBusDemoActivity中发出成功的事件
     * 12-19 15:35:52.318 29721-29834/com.shiming.hement I/SyncLifecycleObserver: Thread[RxCachedThreadScheduler-2,5,main]
     * 12-19 15:35:52.318 29721-29721/com.shiming.hement D/SyncLifecycleObserver: received sync comment success event for comment Events=我是NewRxBusDemoActivity中发出成功的事件
     * 12-19 15:35:52.319 29721-29835/com.shiming.hement I/SyncLifecycleObserver: Thread[RxCachedThreadScheduler-3,5,main]
     * 12-19 15:35:52.320 29721-29721/com.shiming.hement D/SyncLifecycleObserver: received sync comment success event for comment Events=我是NewRxBusDemoActivity中发出成功的事件
     * 12-19 15:35:52.321 29721-29834/com.shiming.hement I/SyncLifecycleObserver: Thread[RxCachedThreadScheduler-2,5,main]
     * 12-19 15:35:52.323 29721-29721/com.shiming.hement D/SyncLifecycleObserver:   success
     * 12-19 15:35:52.330 29721-29721/com.shiming.hement D/SyncLifecycleObserver:   success
     */
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
