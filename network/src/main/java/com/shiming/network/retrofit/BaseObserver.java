package com.shiming.network.retrofit;


import android.annotation.SuppressLint;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


/**
 * Created by shiming on 2017/4/3.
 *  http://v.juhe.cn/todayOnhistory/queryEvent.php?key=b15674dbd34ec00ded57b369dfdabd90&date=1/1
 *  聚合数据的接口
 * 订阅者
 */
public class BaseObserver<T extends SMResponse> implements Observer<T> {

    private static final String TAG="BaseObserver";

    private SubscriberListener mBaseSubscriber;

    public BaseObserver(SubscriberListener baseSubscriber){
        this.mBaseSubscriber=baseSubscriber;
    }


    @SuppressLint("TimberArgCount")
    @Override
    public void onError(Throwable e) {
        Timber.tag(TAG).i("onError:"+e);
        if (mBaseSubscriber!=null){
            mBaseSubscriber.onError(e);
        }
    }

    @Override
    public void onComplete() {
        Timber.tag(TAG).i("onComplete:");
        if (mBaseSubscriber!=null){
            mBaseSubscriber.onCompleted();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        //如果调用 d.dispose() 那么就不会忘下进行了
        Timber.tag(TAG).i("onSubscribe:"+d.isDisposed());
        if (mBaseSubscriber!=null) {
            mBaseSubscriber.onSubscribe(d);
        }
    }

    /**
     * 1025 登录失效，请重新登录
     * 1002 未登录或登录失效
     * @param response
     */
    @Override
    public void onNext(T response) {
        Timber.tag(TAG).i("onNext:");
        if (mBaseSubscriber!=null){
            if (response.error_code==0) {
                mBaseSubscriber.onSuccess(response);
            }else if (response.error_code==1025||response.error_code==1002){
                mBaseSubscriber.checkLogin(response.error_code,response.reason);
            }else {
                mBaseSubscriber.onFail(response.error_code+"",response.reason);
            }
        }

    }

}
