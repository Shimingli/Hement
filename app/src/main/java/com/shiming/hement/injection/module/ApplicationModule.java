package com.shiming.hement.injection.module;

import android.app.Application;
import android.content.Context;

import com.shiming.hement.HementApplication;
import com.shiming.hement.data.remote.IRemoteServer;
import com.shiming.hement.injection.ApplicationContext;
import com.shiming.hement.utils.RxEventBus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * <p>
 * 提供应用程序级依赖关系。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:54
 */

@Module
public class ApplicationModule {

    protected final HementApplication mApplication;

    public ApplicationModule(HementApplication application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }


    @Provides
    @Singleton
    IRemoteServer provideRibotsService() {
        return IRemoteServer.Creator.newHementService();
    }
}
