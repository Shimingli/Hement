package com.shiming.hement.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;
import com.shiming.hement.ui.db.DBNetWorkDemoActivity;
import com.shiming.hement.ui.fragmentdemo.FragmentDemoActivity;
import com.shiming.hement.ui.iamgeloader.ImageLoaderActivity;
import com.shiming.hement.ui.life_cycle_demo.ExtendEvents;
import com.shiming.hement.ui.life_cycle_demo.ExtendSyncRxBus;
import com.shiming.hement.ui.life_cycle_demo.NewExtendEventsActivity;
import com.shiming.hement.ui.life_cycle_demo.NewRxBusDemoActivity;
import com.shiming.hement.ui.log.LogDemoActivity;
import com.shiming.hement.ui.network.NetWorkActivity;
import com.shiming.hement.ui.network.NewNetWorkActivity;
import com.shiming.hement.ui.permission.RxPermissionsActivity;
import com.shiming.hement.ui.rxbusdemo.RxEventBusActivity;
import com.shiming.hement.ui.sp.SPreferencesActivity;
import com.shiming.hement.ui.timber.TimberDemoActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/18 14:52
 */

public class MainActivity extends BaseActivity {

    private TextView mDes;

    @SuppressLint({"CheckResult", "RxLeakedSubscription"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_net_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NetWorkActivity.class));
            }
        });

        findViewById(R.id.btn_sp_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SPreferencesActivity.class));
            }
        });
        findViewById(R.id.btn_net_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NetWorkActivity.class));
            }
        });
        findViewById(R.id.btn_rx_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RxPermissionsActivity.class));
            }
        });
        findViewById(R.id.btn_imageloader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ImageLoaderActivity.class));
            }
        });

        findViewById(R.id.btn_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FragmentDemoActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
            }
        });   findViewById(R.id.btn_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TimberDemoActivity.class));
            }
        });
        findViewById(R.id.btn_new_rxbus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewRxBusDemoActivity.class));
            }
        });findViewById(R.id.btn_new_net_work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewNetWorkActivity.class));
            }
        });
        findViewById(R.id.btn_new_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogDemoActivity.class));
            }
        });
        findViewById(R.id.btn_new_extend_events).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewExtendEventsActivity.class));
            }
        });


        findViewById(R.id.btn_db_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DBNetWorkDemoActivity.class));
            }
        });
        findViewById(R.id.btn_rx_bus_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RxEventBusActivity.class));
            }
        });



        float scale = getResources().getDisplayMetrics().density;
        //我手机上的 density3.0
        Logger.d("我手机上的 density" +scale);

        mDes = findViewById(R.id.tv_msg_events);

        ExtendSyncRxBus.getInstance().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(this.<ExtendEvents>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<ExtendEvents>() {
                    @Override
                    public void accept(ExtendEvents extendEvents) throws Exception {
                    mDes.setText(extendEvents.getCode()+(String)extendEvents.getContent());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

    }
}
