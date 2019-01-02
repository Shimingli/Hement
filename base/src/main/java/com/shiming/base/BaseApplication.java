package com.shiming.base;

import android.app.Application;
import android.content.Context;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/29 15:22
 */

public class BaseApplication extends Application {
    private static BaseApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
    public static BaseApplication getInstance() {
        return mInstance;
    }


    public static Context getContext() {
        return mInstance.getApplicationContext();
    }
}
