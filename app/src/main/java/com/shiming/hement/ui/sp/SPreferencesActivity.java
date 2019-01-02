package com.shiming.hement.ui.sp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.shiming.base.login.PreferenceFileNames;
import com.shiming.base.login.PreferenceKeys;
import com.shiming.hement.R;
import com.shiming.hement.data.DataManager;
import com.shiming.hement.ui.base.BaseActivity;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * <p>
 *  使用本地储存的Demo
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/4 14:05
 */

public class SPreferencesActivity extends BaseActivity {

    @Inject
    DataManager mDataManager;

    private TextView mTextView;

    int i=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        activityComponent().inject(this);
        //对于Timber %s是针对字符的，%d是针对数字的
        Timber.tag(getClassName()).i("mDataManager   =%s"+mDataManager);

        mTextView = findViewById(R.id.tv_sp_des);
        String string = mDataManager.getPreferencesHelper().getString(PreferenceFileNames.TEXT, PreferenceKeys.TEXT);
        mTextView.setText(string);
        findViewById(R.id.btn_mock_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                mDataManager.getPreferencesHelper().saveValue(PreferenceFileNames.TEXT, PreferenceKeys.TEXT,"我一共点击了"+i+"次");
                String string = mDataManager.getPreferencesHelper().getString(PreferenceFileNames.TEXT, PreferenceKeys.TEXT);
                Timber.tag(getClassName()).i("string   =%s",string);
                mTextView.setText(string);
            }
        });
    }
}
