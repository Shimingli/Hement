package com.shiming.network.retrofit;

import android.content.Context;


import com.shiming.network.BuildConfig;
import com.shiming.network.cookie.PersistentCookieJar;
import com.shiming.network.cookie.cache.SetCookieCache;
import com.shiming.network.cookie.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *  Created by shiming  on 2017/4/3.
 */
public class SMRetrofit {

    private static SMRetrofit mRetrofit;
    private  Context mContext;
    //是否https的链接
    private static  boolean mIsHttps;
    private String mServerAddressFormal;
    private static final int DEFAULT_TIMEOUT = 15;
    private OkHttpClient mClient;
    private Retrofit mBuild;

    private SMRetrofit(Context context){
        //为什么要取这个context的对象，因为这个生命周期比较短，百度
        mContext = context.getApplicationContext();
        initRetrofit();
    }

    private void initRetrofit() {
        mServerAddressFormal = BuildConfig.SERVER_ADDRESS_PERSONAL;
        initRetrofit(mServerAddressFormal);
    }

    private void initRetrofit(String serverAddressFormal) {
        // TODO: 2018/11/27   这个log 有什么的作用
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
               HttpLoggingInterceptor.Logger.DEFAULT.log(message);
            }
        });
        logging.redactHeader("Authorization");
        logging.redactHeader("Cookie");
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        /* ------------------------   */

        File cache = new File(mContext.getCacheDir(), "cache");
        int cacheSize=10*1024*1024;
        Cache cache1 = new Cache(cache, cacheSize);
        mClient = new OkHttpClient.Builder().cache(cache1)
              .addInterceptor(new HeadersInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .cookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext)))
                .build();
        mBuild = new Retrofit.Builder()
                .baseUrl(serverAddressFormal)
                //通过GsonConverterFactory为Retrofit添加Gson支持
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mClient)
                .build();
    }

    public static SMRetrofit getInstance(Context context){
        if (mRetrofit==null||mIsHttps){
            synchronized (SMRetrofit.class){
                if (mRetrofit==null||mIsHttps){
                    mRetrofit=new SMRetrofit(context);
                }
            }
        }
        return mRetrofit;
    }


    public Retrofit getBuild(){
        return mBuild;
    }


    public static <T> T getService(Context context,Class<T> servcie){
        return SMRetrofit.getInstance(context).getBuild().create(servcie);
    }
}
