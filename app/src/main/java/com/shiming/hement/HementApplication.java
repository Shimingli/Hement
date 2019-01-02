package com.shiming.hement;

import android.content.Context;
import android.util.Log;


import com.shiming.base.BaseApplication;
import com.shiming.base.BuildConfig;

import com.shiming.base.ui.QMUISwipeBackActivityManager;
import com.shiming.hement.injection.component.ApplicationComponent;
import com.shiming.hement.injection.component.DaggerApplicationComponent;
import com.shiming.hement.injection.module.ApplicationModule;
import com.shiming.hement.lifecycle.SyncResponseEventType;
import com.shiming.hement.lifecycle.SyncRxBus;
import com.shiming.hement.utils.Events;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import timber.log.Timber;

import static android.util.Log.INFO;
import static timber.log.Timber.DebugTree;


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

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            //也可以设置log一直开
            Timber.plant(new DebugTree());
        } else {
            //上线的话  就关闭有些不必要的日记输出
            Timber.plant(new CrashReportingTree());

        }
        QMUISwipeBackActivityManager.init(this);
        //打印tag为类名
        Timber.v("---HementApplication ---");
        // TODO: 2018/12/19  这个事件是不会输出的   
        Events events = new Events("我是HementApplication中发出，但是我接受不到");
        SyncRxBus.getInstance().post(SyncResponseEventType.SUCCESS, events);
    }

    public static HementApplication get(Context context) {
        return (HementApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    /**
     * 需要用特定测试组件替换组件
     *
     * @param applicationComponent
     */
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static final class CrashReportingTree extends Timber.Tree {

        @Override
        protected boolean isLoggable(@Nullable String tag, int priority) {
            return priority >=INFO;
    }

        @Override
        protected void log(int priority, @org.jetbrains.annotations.Nullable String tag, @NotNull String message, @org.jetbrains.annotations.Nullable Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            FakeCrashLibrary.log(priority, tag, message);
            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }

        }
    }
}
