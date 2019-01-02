package com.shiming.hement.ui.db;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shiming.hement.R;
import com.shiming.hement.data.DataManager;
import com.shiming.hement.data.model.TodayBean;
import com.shiming.hement.ui.base.BaseActivity;

import java.util.List;
import io.reactivex.Observer;
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
 * @since 2018/12/4 15:34
 */

public class DBNetWorkDemoActivity extends BaseActivity {


    DataManager mDataManager;

    private TextView mShowDBText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_demo);
        mShowDBText = findViewById(R.id.tv_dp_des);
        mDataManager=new DataManager(this);
        mDataManager.syncDBBean().subscribeOn(Schedulers.io()).subscribe(new Observer<TodayBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                Thread thread = Thread.currentThread();
                Timber.tag(getClassName()+"开始储存数据了 onSubscribe "+d.isDisposed()).i(thread.toString());
            }

            @Override
            public void onNext(TodayBean todayBean) {
                Thread thread = Thread.currentThread();
                Timber.tag(getClassName()+"开始储存数据了 onNext ").i(thread.toString());
                Timber.tag(getClassName()+"开始储存数据了 onNext ").i(new Gson().toJson(todayBean));
            }

            @Override
            public void onError(Throwable e) {
                Thread thread = Thread.currentThread();
                Timber.tag(getClassName()+"开始储存数据了 onError ").i(e.toString());
                Timber.tag(getClassName()+"开始储存数据了 onError ").i(thread.toString());
            }

            @Override
            public void onComplete() {
                Thread thread = Thread.currentThread();
                Timber.tag(getClassName()+"开始储存数据了 onComplete ").i(thread.toString());
            }
        });

        findViewById(R.id.btn_mock_db_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataManager.getDBBean()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<List<TodayBean>>() {

                            @Override
                    public void onSubscribe(Disposable d) {
                        Timber.tag(getClassName()).i("onSubscribe");
                    }

                    @Override
                    public void onNext(List<TodayBean> todayBeans) {
                        Timber.tag(getClassName()).i("onNext");
                        Timber.tag(getClassName()).i(new Gson().toJson(todayBeans));
                        mShowDBText.setText(new Gson().toJson(todayBeans));

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(getClassName()).i("onError");
                    }

                    @Override
                    public void onComplete() {
                        Timber.tag(getClassName()).i("onComplete");
                    }
                });
            }
        });
    }
}
