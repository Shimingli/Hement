package com.shiming.hement.injection.module;

import android.app.Activity;
import android.content.Context;

import com.shiming.hement.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * <p>
 *  我们定义一个类，用@Module来注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的依赖
 *  在modules中，我们定义的方法是用@Provides这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:30
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    /**
     * 加上 Activity 其实就是加入Qualifier（限定符）的作用相当于起了个区分的别名
     * 因为mActivity 属于 Activity 也是Context
     * @return
     */
    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
