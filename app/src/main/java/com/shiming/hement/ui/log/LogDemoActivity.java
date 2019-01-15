package com.shiming.hement.ui.log;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/15 10:24
 */

public class LogDemoActivity extends BaseActivity {

    private static final String TAG = "LogDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_demo_layout);


        //Logger
        findViewById(R.id.tv_logger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogDemoActivity.this,LoggerActivity.class));
            }
        });

        //微信的xLog
        findViewById(R.id.tv_xlog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogDemoActivity.this,XLogWeChatActivity.class));
            }
        });

        //美团 Logan
        findViewById(R.id.tv_logan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogDemoActivity.this,LoganActivity.class));
            }
        });

    }

}
