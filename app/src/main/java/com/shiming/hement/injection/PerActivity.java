package com.shiming.hement.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * <p>
 * 范围注释，用于允许其生命周期应该与活动生命周期一致的对象被存储在正确的组件中。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:52
 */
@Scope//用来标注依赖注入对象的适用范围
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
