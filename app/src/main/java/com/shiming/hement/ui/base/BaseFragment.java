package com.shiming.hement.ui.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shiming.base.ui.QMUIFragment;
import com.shiming.base.utils.QMUIDisplayHelper;
import com.shiming.base.utils.QMUIStatusBarHelper;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseFragment extends QMUIFragment {


    public BaseFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
    }

    @Override
    protected int backViewInitOffset() {
        return QMUIDisplayHelper.dp2px(getContext(), 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        String simpleName = this.getClass().getSimpleName();
        System.out.println(simpleName+"--------------------onResume--------------------");
    }

}
