package com.shiming.network.down.down;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;


import com.shiming.network.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/3/15 10:14
 */
public class HttpDownManager {

    private final String InsmBaseUrl;
    private final boolean mIsDebug;
    private HashMap<String, ProgressDownSubscriber> subMap;
    private volatile static HttpDownManager INSTANCE;
    private Handler handler;
    private OkHttpClient mClient;
    private String saveDir = Environment.getExternalStorageDirectory() + "/xingfugo/";

    private HttpDownManager() {
        subMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());
        InsmBaseUrl = BuildConfig.SERVER_ADDRESS_PERSONAL;
        mIsDebug = BuildConfig.DEBUG;
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 开始下载
     */
    public void startDown(final String url, HttpDownOnNextListener httpDownOnNextListener ) {
        /*添加回调处理类*/
        ProgressDownSubscriber subscriber = new ProgressDownSubscriber(handler,httpDownOnNextListener);
        if (mIsDebug) {
            //配置消息头和打印日志等
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mClient = new OkHttpClient.Builder()
                    .addInterceptor(new DownloadInterceptor(subscriber))
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build();
        }else{
            mClient = new OkHttpClient.Builder()
                    .addInterceptor(new DownloadInterceptor(subscriber))
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .build();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(InsmBaseUrl)
                .build();


        HttpDownService httpService = retrofit.create(HttpDownService.class);
        /*得到rx对象-上一次下載的位置開始下載*/
        httpService.download(url)
                /*指定线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, Object>() {
                    @Override
                    public Object apply(ResponseBody responseBody) throws Exception {
                        writeCaches(responseBody,url);
                        return null;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);

    }


    public void writeCaches(ResponseBody response, String url) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;
        // 储存下载文件的目录
        String savePath = isExistDir(saveDir);
        File file = null;
        try {
            is = response.byteStream();
            long total = response.contentLength();
            file = new File(savePath, getNameFromUrl(url));
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
                // 下载中
//                listener.onDownloading(progress);
            }
            fos.flush();
            // 下载完成
//            listener.onDownloadSuccess(file);
        } catch (Exception e) {
//            listener.onDownloadFailed(e);
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
            }
        }
    }
    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    public static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
