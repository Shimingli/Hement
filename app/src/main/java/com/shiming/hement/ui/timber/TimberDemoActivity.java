package com.shiming.hement.ui.timber;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;

import timber.log.Timber;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.String.format;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/10 16:04
 */

 public  class TimberDemoActivity extends BaseActivity {

    private Button mButton;
    @SuppressLint({
            "LogNotTimber", //
            "StringFormatInTimber", //
            "ThrowableNotAtBeginning", //
            "BinaryOperationInTimber", //
            "TimberArgCount", //
            "TimberArgTypes", //
            "TimberTagLength", //
            "TimberExceptionLogging" //
    }) //
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timber_demo);

        // LogNotTimber
        Log.d(" TimberDemoActivity TAG", "msg");
        Log.d("TimberDemoActivity TAG", "msg", new Exception());
        android.util.Log.d("TimberDemoActivity TAG", "msg");
        android.util.Log.d("TimberDemoActivity TAG", "msg", new Exception());

        // StringFormatInTimber
        Timber.w(format("%s", getString()));
        Timber.w(format("%s", getString()));

        // ThrowableNotAtBeginning
        Timber.d("%s TimberDemoActivity", new Exception());

        // BinaryOperationInTimber
        String foo = "TimberDemoActivity foo";
        String bar = "TimberDemoActivity bar";
        Timber.d("foo" + "bar");
        Timber.d("foo" + bar);
        Timber.d(foo + "bar");
        Timber.d(foo + bar);

        // TimberArgCount
//        Timber.d("%s %s", "arg0");
        Timber.d("%s", "arg0", "arg1");
//        Timber.tag("tag").d("%s %s", "arg0");
        Timber.tag("TimberDemoActivity tag").d("%s", "arg0", "arg1");

        // TimberTagLength
        Timber.tag(" TimberDemoActivity abcdefghijklmnopqrstuvwx");
        Timber.tag("TimberDemoActivity abcdefghijklmnopqrstuvw" + "x");

        // TimberExceptionLogging
        Timber.d(new Exception(), new Exception().getMessage());
        Timber.d(new Exception(), "");
        Timber.d(new Exception(), null);
        Timber.d(new Exception().getMessage());


        Timber.tag(" TimberDemoActivity LifeCycles");
        Timber.d("TimberDemoActivity Activity Created");
        mButton = findViewById(R.id.btn_click_me);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.i("TimberDemoActivity 按钮的id %s 被点击的文字 '%s'.", mButton.getId(), mButton.getText());
                Toast.makeText(TimberDemoActivity.this, "查看日志信息", LENGTH_SHORT).show();
            }
        });
    }
    private String getString() {
        return "TimberDemoActivity foo";
    }
}
