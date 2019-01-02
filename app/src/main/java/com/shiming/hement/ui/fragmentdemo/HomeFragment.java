package com.shiming.hement.ui.fragmentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiming.base.ui.QMUIFragment;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseFragment;

import static com.shiming.hement.ui.fragmentdemo.FragmentDemoActivity.createArchTestIntent;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/12/6 16:04
 */

public class HomeFragment extends BaseFragment {
    TextView mTitleTv;
    private static final String ARG_INDEX = "arg_index";
    private TextView mTitle;
    private Button mBtn1;
    private Button mBtn2;
    private static final int REQUEST_CODE = 1;
    @Override
    protected View onCreateView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_arch_test, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        final int index = args == null ? 1 : args.getInt(ARG_INDEX);
        getActivity().findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
        mTitleTv = getActivity().findViewById(R.id.text);
        mTitle = getActivity().findViewById(R.id.title);
        mBtn1 = getActivity().findViewById(R.id.btn);
        mBtn2 = getActivity().findViewById(R.id.btn_1);
        mTitleTv.setText(String.valueOf(index));
        mTitle.setText(String.valueOf(index));

        final int next = index + 1;
        final boolean destroyCurrent = next % 3 == 0;
        String btnText = destroyCurrent ? "开启新的fragment，同时销毁旧的" : "开启新的fragment，不销毁旧的";
        mBtn1.setText(btnText);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QMUIFragment fragment = newInstance(next);
                if (destroyCurrent) {
                    startFragmentAndDestroyCurrent(fragment);
                } else {
                    startFragmentForResult(fragment, REQUEST_CODE);
                }
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
                Intent intent = createArchTestIntent(getContext());
                startActivity(intent);
            }
        });
    }

    public static HomeFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
