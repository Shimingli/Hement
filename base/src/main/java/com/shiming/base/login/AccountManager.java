package com.shiming.base.login;


import android.text.TextUtils;

import com.shiming.base.utils.ObjectStringConverter;
import com.shiming.base.utils.PreferenceUtil;


/**
 *  Created by shiming on 2016/11/28.
 *  保存登录信息到本地
 */

public class AccountManager {

    private static AccountManager accountManager;
    private LoginAccount zaAccount;

    private AccountManager() {

    }

    public static AccountManager getInstance() {
        if (accountManager == null) {
            synchronized (AccountManager.class) {
                if (accountManager == null) {
                    accountManager = new AccountManager();
                }
            }
        }
        return accountManager;
    }

    public void setAccount(LoginAccount account) {
        this.zaAccount = account;
            String accountStr = ObjectStringConverter.objectToString(account);
        PreferenceUtil.saveValue(PreferenceFileNames.USER_CONFIG, PreferenceKeys.USER_INFO, accountStr);
    }

    public LoginAccount getAccount() {
        if (zaAccount == null) {
            try {
                String accountStr = PreferenceUtil.getString(PreferenceFileNames.USER_CONFIG, PreferenceKeys.USER_INFO, "");
                if (!TextUtils.isEmpty(accountStr)) {
                    zaAccount = (LoginAccount) ObjectStringConverter.stringToObject(accountStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
                zaAccount = new LoginAccount();
            }
        }
        return zaAccount;
    }
}
