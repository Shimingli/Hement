package com.shiming.hement.ui.network;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.shiming.network.IMvpView;
import com.shiming.network.IPresenter;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 14:26
 */

public class NewBasePresenter<V extends IMvpView> implements LifecycleObserver {


    public static final String TAG = "NewBasePresenter";
    private V mMvpView;


    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Timber.tag(TAG).d("onDestroy lifecycle event.start ");
        detachView();
    }

    /**
     * 由于在每次这个方法都会去执行，所以可以这样进行
     * @param owner
     * @param event
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {

    }
    public void detachView() {
        mMvpView = null;
    }
    /**
     * 判断是否还在连接在一起的
     * @return
     */
    public boolean isViewAttached() {
        return mMvpView != null;
    }

    /**
     * 获取View
     * @return
     */
    public V getMvpView() {
        return mMvpView;
    }

    /**
     * 检查View是否附着
     */
    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("在绑定数据之前一定要绑定视图");
        }
    }

    /**
     * 这是Observer 中的 onServer ,当我们调用这个方法，直接就不会走到 onNext中去
     * @param disposable
     */
    public  void dispose(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
