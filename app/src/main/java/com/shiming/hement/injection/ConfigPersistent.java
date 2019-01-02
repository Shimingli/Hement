package com.shiming.hement.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * <p>
 *  Scope 是用来确定注入的实例的生命周期的，
 *  如果没有使用 Scope 注解，Component 每次调用 Module 中的 provide 方法或 Inject 构造函数生成的工厂时都会创建一个新的实例，
 *  而使用 Scope 后可以复用之前的依赖实例。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:21
 */
@Scope //  Scope 的用法，@Scope是元注解，是用来标注自定义注解的
@Retention(RetentionPolicy.RUNTIME)
public  @interface ConfigPersistent {
}
