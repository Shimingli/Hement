package com.shiming.hement.ui.rxbusdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;
import com.shiming.hement.utils.Events;
import com.shiming.hement.utils.RxEventBus;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/4 18:51
 */

public class RxEventBusActivity extends BaseActivity implements View.OnClickListener {

    RxEventBus mRxEventBus;

    private final CompositeDisposable disposables = new CompositeDisposable();
    private TextView mRxDes;
    private Button mRxBtn;
    int count=1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_event_demo);
        mRxDes = findViewById(R.id.tv_rx_des);

        disposables.add(mRxEventBus.toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object object) {
                        if (object instanceof Events.AutoEvent) {
                            mRxDes.setText("我是自动接收的事件，延迟2s");
                        } else if (object instanceof Events.TapEvent) {
                            count++;
                            mRxDes.setText("我是按钮点击的事件第"+count+"次" );
                        }
                    }
                }));
        mRxBtn = findViewById(R.id.btn_rx);
        mRxBtn.setOnClickListener(this);

    }

    /**
     * 模拟页面可见了，发送一个事件
     */
    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        mRxEventBus.post(new Events.AutoEvent());
                    }
                });
    }

    /**
     * 不要发出任何的事件当 Activity销毁了以后
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public void onClick(View v) {
        mRxEventBus.post(new Events.TapEvent());
    }
}
