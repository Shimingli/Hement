package com.shiming.hement.injection.component;


import com.shiming.hement.injection.ConfigPersistent;
import com.shiming.hement.injection.module.ActivityModule;

import dagger.Component;

/**
 * <p>
 *  daggger 的组件，它存在于整个应用的生命周期，但是它不会再配置更改中破坏
 *  使用
 *  Persistent 持久的
 *
 *  dependencies 依赖关系，一个 Component 依赖其他 Compoent 公开的依赖实例，用 Component 中的dependencies声明。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:15
 */
@ConfigPersistent
@Component(dependencies = ApplicationComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);
}
