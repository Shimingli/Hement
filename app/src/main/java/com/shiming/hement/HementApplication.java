package com.shiming.hement;

import android.content.Context;


import com.shiming.base.BaseApplication;
import com.shiming.base.BuildConfig;

import com.shiming.base.ui.QMUISwipeBackActivityManager;
import timber.log.Timber;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 11:31
 */

public class HementApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        QMUISwipeBackActivityManager.init(this);
    }
    public static HementApplication get(Context context) {
        return (HementApplication) context.getApplicationContext();
    }


}
