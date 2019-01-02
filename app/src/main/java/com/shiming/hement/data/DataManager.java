package com.shiming.hement.data;

import android.content.Context;

import com.shiming.hement.data.local.DatabaseHelper;
import com.shiming.hement.data.local.PreferencesHelper;
import com.shiming.hement.data.model.TodayBean;
import com.shiming.hement.data.remote.IRemoteServer;
import com.shiming.network.retrofit.BaseObserver;
import com.shiming.network.retrofit.SMResponse;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/29 09:33
 */

public class DataManager {


    private final IRemoteServer mIRemoteServer;
    private final PreferencesHelper mPreferencesHelper;
    private final DatabaseHelper mDatabaseHelper;


    public DataManager(Context context) {
        mIRemoteServer = IRemoteServer.Creator.newHementService();
        mPreferencesHelper = new PreferencesHelper();
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void loadData(String key, String day, BaseObserver observer) {
        Observable<SMResponse<ArrayList<TodayBean>>> today = mIRemoteServer.getToday(key, day);
        today.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    /**
     * 从网络获取数据，缓存到数据库，然后从数据库中取数据
     * @return
     */
    public Observable<TodayBean> syncDBBean() {
        return mIRemoteServer.getToday("b15674dbd34ec00ded57b369dfdabd90", "1/1").concatMap(new Function<SMResponse<ArrayList<TodayBean>>, ObservableSource<? extends TodayBean>>() {
            @Override
            public ObservableSource<? extends TodayBean> apply(@NonNull SMResponse<ArrayList<TodayBean>> response)
                    throws Exception {
                return mDatabaseHelper.setDBData(response.result);
            }
        });
    }

    /**
     * 从数据库中取数据
     * @return
     */
    public Observable<List<TodayBean>> getDBBean() {
        return mDatabaseHelper.getDBData().distinct();
    }
}
