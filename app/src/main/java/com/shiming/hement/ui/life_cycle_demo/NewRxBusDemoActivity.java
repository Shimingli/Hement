package com.shiming.hement.ui.life_cycle_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shiming.hement.R;
import com.shiming.hement.lifecycle.SyncLifecycleObserver;
import com.shiming.hement.lifecycle.SyncResponseEventType;
import com.shiming.hement.lifecycle.SyncRxBus;
import com.shiming.hement.ui.base.BaseActivity;
import com.shiming.hement.utils.Events;

/**
 * <p>
 *  当我们需要在本地查询数据，比如说DB中查询一堆数据，但是呢？这个查询的动作可定是在后台线程，也要和生命周期绑定在一起的时候，
 *  我们就可以使用实现 LifecycleObserver 这个生命周期的组件
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/18 11:10
 */

public class NewRxBusDemoActivity extends BaseActivity {
    // 如果使用的多的话，可以放在BaseActivity中
    private SyncLifecycleObserver mSyncLifecycleObserver = new SyncLifecycleObserver();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus_layout);
        getLifecycle().addObserver(mSyncLifecycleObserver);

        findViewById(R.id.btn_mock_send_s_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events events = new Events("我是NewRxBusDemoActivity中发出成功的事件");
                SyncRxBus.getInstance().post(SyncResponseEventType.SUCCESS, events);
            }
        });
        findViewById(R.id.btn_mock_send_f_onclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Events events = new Events("我是NewRxBusDemoActivity中发出失败的事件");
                SyncRxBus.getInstance().post(SyncResponseEventType.FAILED, events);
            }
        });

    }
}
