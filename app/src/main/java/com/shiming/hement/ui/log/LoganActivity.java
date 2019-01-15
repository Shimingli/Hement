package com.shiming.hement.ui.log;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dianping.logan.Logan;
import com.dianping.logan.SendLogRunnable;
import com.orhanobut.logger.Logger;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * https://github.com/Meituan-Dianping/Logan
 * 美团的Logan
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/15 10:24
 */

public class LoganActivity extends BaseActivity {

    private static final String TAG = "LoganActivity";
    private RealSendLogRunnable mSendLogRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_logan_layout);

        System.out.println(TAG + "start------------------------------------");
        Logan.w(TAG, 1);
        Logan.w(TAG, 1);
        Logan.w("我要好好学习 天天向上", 1);

        Logan.w(TAG + "test logan", 1);
        Logan.w("test logan", 3);
        Logan.w("test logan", 2);
        Logan.w("test logan", 4);
        Logan.w("test logan", 5);

//


//
        //其中key为日期，value为日志文件大小（Bytes）。
        Map<String, Long> map = Logan.getAllFilesInfo();

        Logger.d(map);
        Logger.d(TAG);
        Logan.f();
        mSendLogRunnable = new RealSendLogRunnable();
        String[] arr = {"2019-01-15"};
        Logan.s(arr, mSendLogRunnable);


        System.out.println(TAG + "end------------------------------------");
    }

    public class RealSendLogRunnable extends SendLogRunnable {

        @Override
        public void sendLog(File logFile) {
            // logFile为预处理过后即将要上传的日志文件
            System.out.println(TAG + logFile.toString());
            try {
                //从文件地址中读取内容到程序中
                //1、建立连接
                InputStream is = new FileInputStream(logFile);
                //2、开始读取信息
                //先定义一个字节数组存放数据
                byte[] b = new byte[5];//把所有的数据读取到这个字节当中
                //完整的读取一个文件
                is.read(b);
                //read:返回的是读取的文件大小
                //最大不超过b.length，返回实际读取的字节个数
                System.out.println(Arrays.toString(b));//读取的是字节数组
                //把字节数组转成字符串
                System.out.println(new String(b));
                //关闭流
                is.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                //系统强制解决的问题：文件没有找到
                e.printStackTrace();
            } catch (IOException e) {
                //文件读写异常
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
//            InputStream input = null;
//            OutputStream output = null;
//            try {
//                try {
//                    input = new FileInputStream(logFile);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                output = new FileOutputStream("assets/"+logFile.getName());
//                byte[] buf = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = input.read(buf)) != -1) {
//                    output.write(buf, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    assert input != null;
//                    input.close();
//                    assert output != null;
//                    output.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }}
    }
}
