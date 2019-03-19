package com.shiming.network.down.down;


import android.os.Handler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/15 10:14
 */

public class ProgressDownSubscriber<T> implements DownloadProgressListener, Observer<T> {

    private Handler mHandler;

    private  HttpDownOnNextListener mHttpDownOnNextListener;

    public ProgressDownSubscriber(Handler handler, HttpDownOnNextListener httpDownOnNextListener) {
        mHandler= handler;
        mHttpDownOnNextListener=httpDownOnNextListener;
    }


    /**
     * 完成，隐藏ProgressDialog
     */

    @Override
    public void onComplete() {
        if(mHttpDownOnNextListener!=null){
            mHttpDownOnNextListener.onComplete();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if(mHttpDownOnNextListener!=null){
            mHttpDownOnNextListener.onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        if(mHttpDownOnNextListener!=null){
            mHttpDownOnNextListener.onError(e);
        }
    }

    @Override
    public void update(final long read, final long count, boolean done) {
        if(mHttpDownOnNextListener!=null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mHttpDownOnNextListener.updateProgress(read, count);
                }
            });

        }
    }

}