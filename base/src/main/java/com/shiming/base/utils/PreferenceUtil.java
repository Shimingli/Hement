package com.shiming.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shiming.base.BaseApplication;
import com.shiming.base.login.PreferenceFileNames;
import com.shiming.base.login.PreferenceKeys;


/**
 * Created by shiming 16/10/27.
 */

public class PreferenceUtil {

    public static String getString(PreferenceFileNames fileNames, PreferenceKeys key, String defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        return sp.getString(key.getValue(), defaultValue);
    }

    public static String getString(PreferenceFileNames fileNames, PreferenceKeys key, String additional, String defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue() + additional, Context.MODE_PRIVATE);
        return sp.getString(key.getValue(), defaultValue);
    }

    public static boolean getBoolean(PreferenceFileNames fileNames, PreferenceKeys key, boolean defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        return sp.getBoolean(key.getValue(), defaultValue);
    }

    public static int getInt(PreferenceFileNames fileNames, PreferenceKeys key, int defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        return sp.getInt(key.getValue(), defaultValue);
    }

    public static float getFloat(PreferenceFileNames fileNames, PreferenceKeys key, float defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        return sp.getFloat(key.getValue(), defaultValue);
    }

    public static long getLong(PreferenceFileNames fileNames, PreferenceKeys key, long defaultValue) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        return sp.getLong(key.getValue(), defaultValue);
    }


    /**
     * 保存值  没有特加的条件
     * @param fileNames
     * @param key
     * @param value
     */
    public static void saveValue(PreferenceFileNames fileNames, PreferenceKeys key, Object value) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        if (value instanceof Boolean) {
            editor.putBoolean(key.getValue(), Boolean.parseBoolean(value.toString()));
        } else if (value instanceof String) {
            editor.putString(key.getValue(), value.toString());
        } else if (value instanceof Long) {
            editor.putLong(key.getValue(), Long.parseLong(value.toString()));
        } else if (value instanceof Float) {
            editor.putFloat(key.getValue(), Float.parseFloat(value.toString()));
        } else if (value instanceof Integer) {
            editor.putInt(key.getValue(), Integer.parseInt(value.toString()));
        }
        editor.apply();
    }

    /**
     * 保存值 key是特加条件
     * @param fileNames
     * @param key
     * @param additional
     * @param value
     */
    public static void saveValue(PreferenceFileNames fileNames, PreferenceKeys key, String additional, Object value) {
        SharedPreferences sp = BaseApplication.getInstance().getSharedPreferences(fileNames.getValue() + additional, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        if (value instanceof Boolean) {
            editor.putBoolean(key.getValue(), Boolean.parseBoolean(value.toString()));
        } else if (value instanceof String) {
            editor.putString(key.getValue(), value.toString());
        } else if (value instanceof Long) {
            editor.putLong(key.getValue(), Long.parseLong(value.toString()));
        } else if (value instanceof Float) {
            editor.putFloat(key.getValue(), Float.parseFloat(value.toString()));
        } else if (value instanceof Integer) {
            editor.putInt(key.getValue(), Integer.parseInt(value.toString()));
        }
        editor.apply();
    }
}
