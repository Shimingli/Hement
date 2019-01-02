package com.shiming.hement.data.local;


import android.content.Context;

import com.shiming.base.login.PreferenceFileNames;
import com.shiming.base.login.PreferenceKeys;
import com.shiming.base.utils.PreferenceUtil;
import com.shiming.hement.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * <p>
 *  @Provides methods can only be present within a @Module or @ProducerModule
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/29 10:35
 */
@Singleton
public class PreferencesHelper {
    /**
     * 但是需要注意的是这种方式的依赖类一定要有一个没有参数的构造函数，不然会编译的时候就会报如下错误，
     * 这个也可以理解因为Dagger2 在生成代码的时候没有办法给构造函数传递参数。
     * This type supports members injection but cannot be implicitly provided.
     * @param context
     */
    @Inject
    public  PreferencesHelper(@ApplicationContext Context context) {

    }

    public void saveValue(PreferenceFileNames fileNames, PreferenceKeys key, Object value) {
        PreferenceUtil.saveValue(fileNames, key, value);
    }

    public String getString(PreferenceFileNames fileNames, PreferenceKeys key) {
        return PreferenceUtil.getString(fileNames, key, "");
    }

    public Boolean getBoolean(PreferenceFileNames fileNames, PreferenceKeys key) {
        return PreferenceUtil.getBoolean(fileNames, key, false);
    }

    public int getInt(PreferenceFileNames fileNames, PreferenceKeys key) {
        return PreferenceUtil.getInt(fileNames, key, 0);
    }

    public Float getFloat(PreferenceFileNames fileNames, PreferenceKeys key) {
        return PreferenceUtil.getFloat(fileNames, key, 0);
    }

    public Long getLong(PreferenceFileNames fileNames, PreferenceKeys key) {
        return PreferenceUtil.getLong(fileNames, key, 0L);
    }

}
