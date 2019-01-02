package com.shiming.hement.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shiming.base.ui.QMUIActivity;
import com.shiming.base.utils.QMUIDisplayHelper;
import com.shiming.base.utils.QMUIStatusBarHelper;

import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

import static com.shiming.base.BaseApplication.getContext;

/**
 * <p>
 *  抽象应用程序中的其他活动必须实现的活动。它处理Dagger组件的创建，并确保ConfigPersistentComponent的实例跨配置更改存活。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:04
 */

public class BaseActivity extends QMUIActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    /**
     * AtomicLong是作用是对长整形进行原子操作。 线程安全
     */
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    /**
     * java1.8中新加入了一个新的原子类LongAdder，该类也可以保证Long类型操作的原子性，
     * 相对于AtomicLong，LongAdder有着更高的性能和更好的表现，可以完全替代AtomicLong的来进行原子操作
     * 但是对 java的版本有要求，这里就不使用 LongAdder了
     */
   // private static final LongAdder NEXT_ID = new LongAdder();


    private long mActivityId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建ActivityComponent，如果配置更改后调用缓存的ConfigPersistentComponent，则重用它。
        mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();


        //状态栏的颜色
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }
    protected String getClassName(){
        return this.getClass().getSimpleName();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    /**
     * isChangingConfigurations()函数在是Api level 11（Android 3.0.x） 中引入的
     * 也就是用来检测当前的Activity是否 因为Configuration的改变被销毁了，然后又使用新的Configuration来创建该Activity。
     * 常见的案例就是 Android设备的屏幕方向发生变化，比如从横屏变为竖屏。
     */
    @Override
    protected void onDestroy() {
        //检查此活动是否处于销毁过程中，以便用新配置重新创建。
        if (!isChangingConfigurations()) {
            Timber.tag(getClassName()).i("销毁的configPersistentComponent id=%d",mActivityId);
        }
        super.onDestroy();
    }


    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

}
