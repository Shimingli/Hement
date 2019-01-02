package com.shiming.hement.ui.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shiming.base.login.PreferenceFileNames;
import com.shiming.base.login.PreferenceKeys;
import com.shiming.hement.R;
import com.shiming.hement.ui.MainActivity;
import com.shiming.hement.ui.base.BaseActivity;
import com.shiming.hement.data.DataManager;
import com.shiming.hement.data.model.TodayBean;

import java.util.ArrayList;
import timber.log.Timber;


/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2018/11/28 15:23
 */

public class NetWorkActivity extends BaseActivity implements NetWorkView, View.OnClickListener {

    String key="b15674dbd34ec00ded57b369dfdabd90";

    NetWorkPresenter mMainPresenter;

    private Button mBtn;
    private EditText mDay;
    private EditText mMonth;
    private RecyclerView mRecyclerView;
    private SMAdapter mSmAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work);
        mMainPresenter=new NetWorkPresenter(this);
        Timber.tag(getClassName()).i("mMainPresenter   =%s",mMainPresenter);
        mMainPresenter.attachView(this);
        initView();
        initListener();
    }
    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mMonth = (EditText) findViewById(R.id.et_month);
        mDay = (EditText) findViewById(R.id.et_day);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    }
    private void initListener() {
        mBtn.setOnClickListener(this);
        mSmAdapter = new SMAdapter(this, null);
        mRecyclerView.setAdapter(mSmAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getDataFail(String errorCode, String errorMsg) {
        Toast.makeText(this,errorMsg+errorCode,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onError(Throwable e) {
        Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void getDataSuccess(ArrayList<TodayBean> result) {
        String s = new Gson().toJson(result);
        Timber.tag(getClassName()).i(s);
        Thread thread = Thread.currentThread();
        Timber.tag(getClassName()).i(thread.toString());
        mSmAdapter.addData(result);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mMonth.getText())||TextUtils.isEmpty(mDay.getText())){
            Toast.makeText(NetWorkActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
        }else {
            mMainPresenter.loadData(key,mMonth.getText()+"/"+mDay.getText());
        }
    }
}
