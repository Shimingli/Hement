package com.shiming.hement.ui.network;

import com.shiming.hement.data.DataManager;
import com.shiming.hement.data.model.TodayBean;
import com.shiming.hement.data.remote.IRemoteServer;
import com.shiming.network.retrofit.BaseObserver;
import com.shiming.network.retrofit.SMResponse;
import com.shiming.network.retrofit.SubscriberListener;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/20 14:57
 */

public class NewNetWorkPresenter extends NewBasePresenter<NetWorkView> {


    private Disposable mDisposable;
    private final IRemoteServer mIRemoteServer;

    public NewNetWorkPresenter() {
        mIRemoteServer = IRemoteServer.Creator.newHementService();
    }

    @Override
    public void attachView(NetWorkView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
        Timber.tag(TAG).d("onDestroy lifecycle event. end ");
    }
    public void loadData(String key,String day){
        //检查View是否附着在上面，不在直接抛出异常
        checkViewAttached();
        //检查是否往下运行
        dispose(mDisposable);
        loadData(key,day,new BaseObserver<SMResponse<ArrayList<TodayBean>>>(new SubscriberListener<SMResponse<ArrayList<TodayBean>>>() {

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
    public void loadData(String key, String day, BaseObserver observer) {
        Observable<SMResponse<ArrayList<TodayBean>>> today = mIRemoteServer.getToday(key, day);
        today.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }
}
