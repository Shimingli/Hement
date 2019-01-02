package com.shiming.hement.injection.component;

import android.app.Application;
import android.content.Context;

import com.shiming.hement.data.DataManager;
import com.shiming.hement.data.local.DatabaseHelper;
import com.shiming.hement.data.local.PreferencesHelper;
import com.shiming.hement.data.remote.IRemoteServer;
import com.shiming.hement.injection.ApplicationContext;
import com.shiming.hement.injection.module.ApplicationModule;
import com.shiming.hement.utils.RxEventBus;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 11:10
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    @ApplicationContext
    Context context();

    Application application();

    IRemoteServer remoteServer();


    PreferencesHelper preferencesHelper();

    DatabaseHelper databaseHelper();

    DataManager dataManager();

    RxEventBus eventBus();

}
