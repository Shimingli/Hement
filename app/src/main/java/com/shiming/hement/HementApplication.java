package com.shiming.hement;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
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

import java.io.File;

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
            //也可以设置log一直开  todo  logger 也可以和Timber完美的融合
            Timber.plant(new DebugTree(){
                @Override
                protected void log(int priority, String tag, @NotNull String message, Throwable t) {
                    super.log(priority, tag, message, t);
                    //Logger.log(priority, tag, message, t);
                }
            });
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

        Logger.addLogAdapter(new AndroidLogAdapter());
        //或者如果你想要在正式版中禁止打日志
        XLog.init(BuildConfig.DEBUG ? LogLevel.ALL : LogLevel.NONE);
        LoganConfig config = new LoganConfig.Builder()
                .setCachePath(getApplicationContext().getFilesDir().getAbsolutePath())
                .setPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath()
                        + File.separator + "hement_logo")
                .setEncryptKey16("0123456789012345".getBytes()) //128位ase加密Key
                .setEncryptIV16("0123456789012345".getBytes()) //128位aes加密IV
                .build();
        Logan.init(config);
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
     * 记录重要信息以进行故障报告的树。
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
