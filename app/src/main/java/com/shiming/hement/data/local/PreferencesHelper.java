package com.shiming.hement.data.local;


import android.content.Context;

import com.shiming.base.login.PreferenceFileNames;
import com.shiming.base.login.PreferenceKeys;
import com.shiming.base.utils.PreferenceUtil;


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

public class PreferencesHelper {

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
