package com.shiming.hement.ui.iamgeloader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.shiming.hement.R;
import com.shiming.hement.ui.MainActivity;
import com.shiming.hement.ui.base.BaseActivity;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/5 14:55
 */

public class ImageLoaderActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        findViewById(R.id.btn_click_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageLoaderActivity.this,DemoActivity.class));
            }
        });
        findViewById(R.id.btn_click_me_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageLoaderActivity.this,TransitionOptionsActivity.class));
            }
        });
        findViewById(R.id.btn_click_me_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageLoaderActivity.this,ImageProgressActivity.class));
            }
        });
    }
}
