package com.shiming.base.login;

/**
 * Created by shiming on 16/11/19.
 */

public enum PreferenceFileNames {
    //app 默认的配置
    APP_CONFIG("app_config"),
    // 登录信息的保存
    USER_CONFIG("user_config"),
    //测试
    TEXT("text");

    private String value;

    PreferenceFileNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PreferenceFileNames typeOfValue(String value) {
        for (PreferenceFileNames e : values()) {
            if (value.equals(e.getValue())) {
                return e;
            }
        }
        return APP_CONFIG;
    }

}
