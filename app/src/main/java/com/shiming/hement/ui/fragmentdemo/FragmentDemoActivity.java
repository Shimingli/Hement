package com.shiming.hement.ui.fragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseFragment;
import com.shiming.hement.ui.base.BaseFragmentActivity;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/6 15:53
 */

public class FragmentDemoActivity extends BaseFragmentActivity {

    private static final String KEY_FRAGMENT = "key_fragment";
    private static final int VALUE_FRAGMENT_ARCH_TEST = 2;

    @Override
    protected int getContextViewId() {
        return R.id.fragmentdemoactivity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            BaseFragment fragment = getFirstFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(getContextViewId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * 这里特殊处理，这个非常有意思
     * 主要来解决跳转通知栏的消息
     * @return
     */
    private BaseFragment getFirstFragment() {
        Intent intent = getIntent();
        int ret = intent.getIntExtra(KEY_FRAGMENT, 0);
        BaseFragment fragment;
        if (ret == VALUE_FRAGMENT_ARCH_TEST) {
            fragment = new HomeFragment();
        }else {
            fragment = new HomeFragment();
        }
        return fragment;
    }

    public static Intent createArchTestIntent(Context context) {
        Intent intent = new Intent(context, FragmentDemoActivity.class);
        intent.putExtra(KEY_FRAGMENT, VALUE_FRAGMENT_ARCH_TEST);
        return intent;
    }
}
