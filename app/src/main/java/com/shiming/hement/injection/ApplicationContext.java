package com.shiming.hement.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * <p>
 * 加上 Activity 其实就是加入Qualifier（限定符）的作用相当于起了个区分的别名
 *    因为mActivity 属于 Activity 也是Context
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 11:08
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
