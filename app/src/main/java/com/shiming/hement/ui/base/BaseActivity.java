package com.shiming.hement.ui.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.view.inputmethod.InputMethodManager;

import com.shiming.base.ui.QMUIActivity;
import com.shiming.base.utils.QMUIDisplayHelper;
import com.shiming.base.utils.QMUIStatusBarHelper;
import com.shiming.hement.HementApplication;
import com.shiming.hement.injection.component.ActivityComponent;
import com.shiming.hement.injection.component.ConfigPersistentComponent;
import com.shiming.hement.injection.component.DaggerConfigPersistentComponent;
import com.shiming.hement.injection.module.ActivityModule;
import com.shiming.hement.ui.life_cycle_demo.ExtendEvents;

import java.util.concurrent.atomic.AtomicLong;

import timber.log.Timber;

import static com.shiming.base.BaseApplication.getContext;

/**
 * <p>
 * 抽象应用程序中的其他活动必须实现的活动。它处理Dagger组件的创建，并确保ConfigPersistentComponent的实例跨配置更改存活。
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 10:04
 */

public class BaseActivity extends QMUIActivity  {

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

    /**
     * LongSparseArray是android里为<Long,Object> 这样的Hashmap而专门写的类,目的是提高效率，其核心是折半查找函数（binarySearch）。
     * SparseArray仅仅提高内存效率，而不是提高执行效率
     * ，所以也决定它只适用于android系统（内存对android项目有多重要）SparseArray不需要开辟内存空间来额外存储外部映射，从而节省内存。
     */
    // https://www.jianshu.com/p/a5f638bafd3b   常用集合的原理分析 Dagger does not support injection into private fields
    private static final LongSparseArray<ConfigPersistentComponent> sComponentsMap = new LongSparseArray<>();

    private long mActivityId;

    private ActivityComponent mActivityComponent;
   // 以前需要这样做，现在不需要了
//    private LifecycleRegistry registry = new LifecycleRegistry(this);
    //    @Override
//    public Lifecycle getLifecycle() {
//        return registry;
//    }
    /*为了使用RxBus start   */
//    private final BehaviorSubject<ActivityEvent> lifecycleSubject = BehaviorSubject.create();
//
//    @Override
//    @NonNull
//    @CheckResult
//    public final Observable<ActivityEvent> lifecycle() {
//        return lifecycleSubject.hide();
//    }
//
//    @Override
//    @NonNull
//    @CheckResult
//    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event) {
//        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
//    }
//
//    @Override
//    @NonNull
//    @CheckResult
//    public final <T> LifecycleTransformer<T> bindToLifecycle() {
//        return RxLifecycleAndroid.bindActivity(lifecycleSubject);
//    }
//
//    @Override
//    @CallSuper
//    protected void onStart() {
//        super.onStart();
//        lifecycleSubject.onNext(ActivityEvent.START);
//    }
//
//    @Override
//    @CallSuper
//    protected void onResume() {
//        super.onResume();
//        lifecycleSubject.onNext(ActivityEvent.RESUME);
//    }
//
//    @Override
//    @CallSuper
//    protected void onPause() {
//        lifecycleSubject.onNext(ActivityEvent.PAUSE);
//        super.onPause();
//    }
//
//    @Override
//    @CallSuper
//    protected void onStop() {
//        lifecycleSubject.onNext(ActivityEvent.STOP);
//        super.onStop();
//    }

    /**
     * isChangingConfigurations()函数在是Api level 11（Android 3.0.x） 中引入的
     * 也就是用来检测当前的Activity是否 因为Configuration的改变被销毁了，然后又使用新的Configuration来创建该Activity。
     * 常见的案例就是 Android设备的屏幕方向发生变化，比如从横屏变为竖屏。
     */
    @Override
    //@CallSuper
    protected void onDestroy() {
        //lifecycleSubject.onNext(ActivityEvent.DESTROY);
        //检查此活动是否处于销毁过程中，以便用新配置重新创建。
        if (!isChangingConfigurations()) {
            Timber.tag(getClassName()).i("销毁的configPersistentComponent id=%d", mActivityId);
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }
    /*为了使用RxBus end  */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        lifecycleSubject.onNext(ActivityEvent.CREATE);

        //创建ActivityComponent，如果配置更改后调用缓存的ConfigPersistentComponent，则重用它。
        mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();

        ConfigPersistentComponent configPersistentComponent = sComponentsMap.get(mActivityId, null);
        if (null == configPersistentComponent) {
            Timber.tag(getClassName()).i("创建新的configPersistentComponent id=%d", mActivityId);
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .applicationComponent(HementApplication.get(this).getComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));

        //状态栏的颜色
        QMUIStatusBarHelper.setStatusBarLightMode(this);

//        getLifecycle().addObserver(new HandleEventObserver(){
//            @SuppressLint("SetTextI18n")
//            @Override
//            protected void handlerEvents(ExtendEvents extendEvents){
//                handlerEvent(extendEvents);
//            }
//        });
    }


    protected String getClassName() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }


    public ActivityComponent activityComponent() {
        return mActivityComponent;
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }


    /**
     * 隐藏键盘
     */
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public void handlerEvent(ExtendEvents extendEvents){

    }
}
