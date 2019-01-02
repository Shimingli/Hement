package com.shiming.hement.ui.network;

import android.content.Context;

import com.shiming.hement.data.DataManager;
import com.shiming.hement.data.model.TodayBean;
import com.shiming.network.BasePresenter;
import com.shiming.network.retrofit.BaseObserver;
import com.shiming.network.retrofit.SMResponse;
import com.shiming.network.retrofit.SubscriberListener;

import java.util.ArrayList;


import io.reactivex.disposables.Disposable;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 17:26
 */

public class NetWorkPresenter  extends BasePresenter<NetWorkView> {

    private final DataManager mDataManager;

    private Disposable mDisposable;


    public NetWorkPresenter(Context context) {
        mDataManager = new DataManager(context);
    }

    @Override
    public void attachView(NetWorkView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }
    public void loadData(String key,String day){
        //检查View是否附着在上面，不在直接抛出异常
        checkViewAttached();
        //检查是否往下运行
        dispose(mDisposable);
        mDataManager.loadData(key,day,new BaseObserver<SMResponse<ArrayList<TodayBean>>>(new SubscriberListener<SMResponse<ArrayList<TodayBean>>>() {

            @Override
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                mDisposable = disposable;
            }

            @Override
            public void onSuccess(SMResponse<ArrayList<TodayBean>> response) {
                getMvpView().getDataSuccess(response.result);
            }

            @Override
            public void onFail(String errorCode, String errorMsg) {
                getMvpView().getDataFail(errorCode,errorMsg);
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().onError(e);
            }
        }));

    }

}
