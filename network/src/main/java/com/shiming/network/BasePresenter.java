package com.shiming.network;


import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 14:26
 */

public class BasePresenter<V extends IMvpView> implements IPresenter<V> {


    private V mMvpView;

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
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
