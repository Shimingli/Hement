package com.shiming.network.retrofit;


import android.widget.Toast;

import com.shiming.base.BaseApplication;
import com.shiming.network.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 *  Created by shiming on 2017/4/3.
 */
public abstract class SubscriberListener<T> {

    public void onSubscribe(Disposable disposable){

    }
    public abstract void onSuccess(T response);
    //why  后台定义来改动
    public abstract void onFail(String errorCode,String errorMsg);

    /**
     * 这个异常子类最好去实现，因为发生了异常，需要菊花圈停止转动，等等 ui上的操作
     * @param e
     */
    public  void onError(Throwable e){
        if (e instanceof HttpException){
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            if (code==502||code==404){
                Toast.makeText(BaseApplication.getContext(), R.string.server_exception_please_try_again_later, Toast.LENGTH_SHORT).show();
            }else if (code==504){
                Toast.makeText(BaseApplication.getContext(), R.string.the_network_is_not_giving_strength, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BaseApplication.getContext(), R.string.no_network_connected, Toast.LENGTH_SHORT).show();
            }
        }else if (e instanceof UnknownHostException || e instanceof SocketException) {
            if (BaseApplication.getContext() != null)
                Toast.makeText(BaseApplication.getContext(), R.string.no_network_connected, Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            if (BaseApplication.getContext() != null)
                Toast.makeText(BaseApplication.getContext(), R.string.no_network_connected, Toast.LENGTH_SHORT).show();
        } else {
            if (BaseApplication.getContext() != null)
                Toast.makeText(BaseApplication.getContext(), R.string.no_network_connected, Toast.LENGTH_SHORT).show();
        }
    }

    public void onCompleted(){

    }
    //如果app需要检查你的账号是否在别的设备上登录，根据返回吗在这里做登录的操作
    //每当用户在app中请求了一次网络的请求的话，就会走到这个方法里，然后在这个方法中做统一的操作
    //比如退出app，重新登录。。。
    public  void checkLogin(int errorCode,String erroMsg){}
}
