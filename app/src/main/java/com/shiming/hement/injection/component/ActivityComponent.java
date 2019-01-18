package com.shiming.hement.injection.component;


import com.shiming.hement.ui.MainActivity;
import com.shiming.hement.ui.db.DBNetWorkDemoActivity;
import com.shiming.hement.ui.iamgeloader.ImageLoaderActivity;
import com.shiming.hement.ui.network.NetWorkActivity;
import com.shiming.hement.injection.PerActivity;
import com.shiming.hement.injection.module.ActivityModule;
import com.shiming.hement.ui.permission.RxPermissionsActivity;
import com.shiming.hement.ui.rxbusdemo.RxEventBusActivity;
import com.shiming.hement.ui.sp.SPreferencesActivity;

import dagger.Subcomponent;

/**
 * <p>
 * 此组件对跨应用程序的所有活动注入依赖关系。
 *  Component 和Subcomponent继承关系，一个 Component 继承（也可以叫扩展）某 Component 提供更多的依赖，SubComponent 就是继承关系的体现。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:48
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    /**
     * 注入activity
     * @param mainActivity
     */
    void inject(MainActivity mainActivity);

    /**
     * 每一个类都得单独的注入
     * @param baseActivity
     */
    void inject(NetWorkActivity baseActivity);

    void inject(SPreferencesActivity sPreferencesActivity);

    void inject(DBNetWorkDemoActivity dbDemoActivity);

    void inject(RxEventBusActivity rxEventBusActivity);

    void inject(RxPermissionsActivity rxPermissionsActivity);

    void inject(ImageLoaderActivity imageLoaderActivity);

    void inject(MainActivity mainActivity);
}
