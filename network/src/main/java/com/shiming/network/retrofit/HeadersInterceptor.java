package com.shiming.network.retrofit;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.shiming.base.login.AccountManager;
import com.shiming.base.login.LoginAccount;
import com.shiming.network.BuildConfig;
import com.shiming.network.utils.NetworkDeviceUtils;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author shiming
 * @version 1.0.0
 * @date 2016/5/19
 */
public class HeadersInterceptor implements Interceptor {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private String ua;

    public HeadersInterceptor(Context context) {
        this.context = context;
    }

    //重写拦截方法，处理自定义的Cookies信息
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        initConfig();

        if (request.method().equals("GET")) {
            HttpUrl httpUrl = request.url().newBuilder()
                    .build();
            request = request.newBuilder().url(httpUrl).build();
        } else if (request.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldBody = (FormBody) request.body();
            for (int i = 0; i < oldBody.size(); i++) {
                newFormBody.addEncoded(oldBody.encodedName(i), oldBody.encodedValue(i));
            }
            request = request.newBuilder().post(newFormBody.build()).build();
        }
        // 如果 这里 报错 比如说 account.token
        LoginAccount account = AccountManager.getInstance().getAccount();
        if (account!=null) {
            request = request.newBuilder()
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Charset", "UTF-8,*;q=0.5")
                    .header("Cache-Control", "no-cache")
                    .header("version", BuildConfig.VERSION_NAME)
                    .header("ua", ua)
                    .header("Connection", "Keep-Alive")
                    .header("token",account.token)
                    .build();
        }else {
            request = request.newBuilder()
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Charset", "UTF-8,*;q=0.5")
                    .header("Cache-Control", "no-cache")
                    .header("version", BuildConfig.VERSION_NAME)
                    .header("ua", ua)
                    .header("Connection", "Keep-Alive")
                    .build();
        }
        Log.d(TAG, "request:" + request.url());
        return chain.proceed(request);
    }

    private String getUAInfo() {
        // shiming/App版本/手机系统平台(Android、ios)/手机系统版本/手机型号
        // /渠道号/子渠道号/网卡MAC/包名/屏高/屏宽/唯一标识/网络(0:2G;1:3G;2:4G;3:WIFI;-1:未知)
        StringBuffer sb = new StringBuffer();
        sb.append("ShopToolsApp/" + BuildConfig.VERSION_NAME + "/");
        sb.append("Android/" + android.os.Build.VERSION.RELEASE + "/");
        sb.append(android.os.Build.MODEL + "/");
        sb.append(getMacAddress() + "/");
        sb.append(context.getPackageName() + "/");
        sb.append(screenDisplayMetrics().heightPixels + "/");
        sb.append(screenDisplayMetrics().widthPixels + "/");
        sb.append(UUID.randomUUID() + "/");
        sb.append(NetworkDeviceUtils.getNetworkType(context));
        return sb.toString();
    }

    /**
     * 获取MAC地址
     *
     * @return
     */
    public static String getMacAddress() {
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (interfaces != null && interfaces.hasMoreElements()) {
            NetworkInterface iF = interfaces.nextElement();

            byte[] address = new byte[0];
            try {
                address = iF.getHardwareAddress();
            } catch (SocketException e) {
                e.printStackTrace();
            }
            if (address == null || address.length == 0) {
                continue;
            }

            StringBuilder buf = new StringBuilder();
            for (byte b : address) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            String mac = buf.toString();
            if ("wlan0".equals(iF.getName())) {
                return mac;
            }
            Log.d("---mac", "interfaceName=" + iF.getName() + ", mac=" + mac);
        }
        return "";
    }

    /**
     * 初始化配置信息
     */
    private void initConfig() {
        ua = getUAInfo();
    }

    /**
     * 返回屏幕DisplayMetrics
     *
     * @return 当前屏幕DisplayMetrics
     */
    public DisplayMetrics screenDisplayMetrics() {
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWm.getDefaultDisplay().getMetrics(metrics);
        // 有时候密度出错，所以通过Dpi来判断
        return metrics;
    }

}
