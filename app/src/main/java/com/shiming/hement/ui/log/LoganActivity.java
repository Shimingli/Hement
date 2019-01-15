package com.shiming.hement.ui.log;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;

/**
 * <p>
 *  https://github.com/Meituan-Dianping/Logan
 *  美团的Logan
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/15 10:24
 */

public class LoganActivity extends BaseActivity {

    private static final String TAG = "LoganActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_logan_layout);

    }

}
