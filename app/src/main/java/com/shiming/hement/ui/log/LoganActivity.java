package com.shiming.hement.ui.log;


import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64InputStream;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dianping.logan.Logan;
import com.dianping.logan.OnLoganProtocolStatus;
import com.dianping.logan.SendLogRunnable;
import com.orhanobut.logger.Logger;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;
import com.shiming.hement.ui.life_cycle_demo.ExtendSyncRxBus;
import com.shiming.hement.utils.AndroidAESEncryptor;
import com.shiming.hement.utils.MoreAES;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import timber.log.Timber;

/**
 * <p>
 * https://github.com/Meituan-Dianping/Logan
 * 美团的Logan
 * <p>
 * 为了节约用户手机空间大小，日志文件只保留最近7天的日志，过期会自动删除。
 * 在Android设备上Logan将日志保存在沙盒中，保证了日志文件的安全性。
 * <p>
 * 日志文件的安全性必须得到保障，不能随意被破解，更不能明文存储。Logan采用了流式加密的方式，使用对称密钥加密日志数据，存储到本地。
 * 同时在日志上传时，使用非对称密钥对对称密钥Key做加密上传，防止密钥Key被破解，从而在网络层保证日志安全。
 * </p>
 *  https://blog.csdn.net/u013762572/article/details/83118534  参考的博客
 *  How to use demo ？   请查看官方的Demo ：  https://github.com/Meituan-Dianping/Logan/wiki/How-to-use-demo
 * @author shiming
 * @version v1.0
 * @since 2019/1/15 10:24
 */

public class LoganActivity extends BaseActivity {

    private static final String TAG = "LoganActivity";
    private TextView mTvInfo;
    private com.shiming.hement.ui.log.RealSendLogRunnable mRealSendLogRunnable;
    private Button mSendBtn;
    private EditText mEditIp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_logan_layout);
        mTvInfo = findViewById(R.id.tv_file_des);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mEditIp = (EditText) findViewById(R.id.send_ip);
        System.out.println(TAG + "start------------------------------------");
        Logan.w(TAG, 1);
        Logan.w(TAG, 1);
        Logan.w("我要好好学习 天天向上——1", 1);
        Logan.w("我要好好学习 天天向上——2", 2);
        Logan.w("我要好好学习 天天向上——3", 3);
        Logan.w("我要好好学习 天天向上——4", 4);

        Logan.w(TAG + "test logan", 1);
        Logan.w("test logan", 3);
        Logan.w("test logan", 2);
        Logan.w("test logan", 4);
        Logan.w("test logan", 5);
        Logan.w("dersdetastedewst   没啥关系啊 感觉是 ", 5);




        //其中key为日期，value为日志文件大小（Bytes）。
        Map<String, Long> map = Logan.getAllFilesInfo();

        Logger.d(map);
        Logger.d(TAG);
        Logan.f();
//        mSendLogRunnable = new RealSendLogRunnable();
        String[] arr = {"2019-01-15"};
        Logan.setDebug(true);
        Logan.setOnLoganProtocolStatus(new OnLoganProtocolStatus() {
            @Override
            public void loganProtocolStatus(String cmd, int code) {
                Log.d(TAG, "clogan > cmd : " + cmd + " | " + "code : " + code);
            }
        });
        System.out.println(TAG + "end------------------------------------");

        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d = dataFormat.format(new Date(System.currentTimeMillis()));
        String[] temp = new String[1];
        temp[0] = d;
        loganSend();
        loganFilesInfo();
        mRealSendLogRunnable = new com.shiming.hement.ui.log.RealSendLogRunnable(this);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loganSend();
            }
        });
//        ExtendSyncRxBus
//        IronManLog
    }
    private void loganSend() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        String d = dataFormat.format(new Date(System.currentTimeMillis()));
        String[] temp = new String[1];
        temp[0] = d;
        String ip = mEditIp.getText().toString().trim();
        if (!TextUtils.isEmpty(ip)) {
            mRealSendLogRunnable.setIp(ip);
        }
        Timber.tag(TAG).w("上传日志的接受日期的 %s",temp);
//        temp[1]="2019-01-24";
        Logan.s(temp, mRealSendLogRunnable);
    }

    private void loganFilesInfo() {
        Map<String, Long> map = Logan.getAllFilesInfo();
        if (map != null) {
            StringBuilder info = new StringBuilder();
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                info.append("文件日期：").append(entry.getKey()).append("  文件大小（bytes）：").append(
                        entry.getValue()).append("\n");
            }
            mTvInfo.setText(info.toString());
        }
    }


    /**
     * 把16进制转化为字节数组
     *
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }


    public class RealSendLogRunnable extends SendLogRunnable {
        /**
         * 这个文件读取出来是 乱码的   通过手机拷贝出来也是乱码的
         *
         * @param logFile
         */
        @Override
        public void sendLog(File logFile) {
            MoreAES instances = MoreAES.getInstances();

//            AssetManager assets = getAssets();
//            String s = logFile.getAbsolutePath() + "shiming";
//            instances.decrypt(logFile.getPath(),logFile.getPath()+"shiming","0123456789012345".getBytes(),"0123456789012345".getBytes());
            BufferedReader reader = null;
            try {
                System.out.println("以行为单位读取文件内容，一次读一行");
                reader = new BufferedReader(new FileReader(logFile));
                String tempString = null;
                int line = 1;
                 //一次读一行，读入null时文件结束
                while ((tempString = reader.readLine()) != null) {
                    System.out.println("以行为单位读取文件内容，一次读一行  "+tempString);
                //把当前行号显示出来
                    String s = AndroidAESEncryptor.decrypt128("0123456789012345", tempString, "");
                    System.out.println("line " + line + ": " + tempString);
                    Logger.d(s);
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }

    }


    private void loganTest() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    for (int i = 0; i < 9; i++) {
                        Log.d(TAG, "times : " + i);
                        Logan.w(String.valueOf(i), 1);
                        Thread.sleep(5);
                    }
                    Log.d(TAG, "write log end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*
字段	含义	举例
c	log-content 日志的内容	long one
f	flag-key 日志的标记	1
l	local-time 日志的当地时间	1539611498547
n	threadname_key 写当前日志的线程名	main/thread-24
i	threadid_key 写当前日志的线程id	1
m	ismain_key 是否在主线程中运行	true/false
     */
}
