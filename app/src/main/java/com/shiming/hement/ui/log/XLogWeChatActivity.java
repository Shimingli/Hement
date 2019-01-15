package com.shiming.hement.ui.log;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.shiming.hement.R;
import com.shiming.hement.ui.base.BaseActivity;

/**
 * <p>
 * https://github.com/elvishew/xLog
 * 微信的xLog
 * 简单、美观、强大、可扩展的 Android 和 Java 日志库，可同时在多个通道打印日志，如 Logcat、Console 和文件。如果你愿意，甚至可以打印到远程服务器（或其他任何地方）。
 * * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/15 10:24
 */

public class XLogWeChatActivity extends BaseActivity {

    private static final String TAG = "XLogWeChatActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_xlog_layout);
        String jsonString = "{\"name\": \"Elvis\", \"age\": 18}";
        String xmlString = "<team><member name=\"Elvis\"/><member name=\"Leon\"/></team>";
        XLog.d("Simple message");
        XLog.d("My name is %s", "Elvis");
        // XLog.d("An exception caught", new Exception());
        XLog.d(new Object());
        XLog.d(new String[]{"d", "d"});
        XLog.json(jsonString);
        XLog.xml(xmlString);

        //带边框的 XLog
        XLog.init(LogLevel.ALL, new LogConfiguration.Builder().b().build());
        XLog.d("Message");
        XLog.d("Message with argument: age=%s", 18);
        XLog.json(jsonString);
        XLog.xml(xmlString);
        XLog.st(5).d("Message with stack trace info");

        XLog.d("***********************************************************");
        XLog.t()    // 允许打印线程信息
                .st(3)  // 允许打印深度为3的调用栈信息
                .b()    // 允许打印日志边框
                .d("Simple message 1");

        XLog.tag("TEMP-TAG")
                .st(0) // 允许打印不限深度的调用栈信息
                 .d("Simple message 2");

        XLog.nt()   // 禁止打印线程信息
                .nst()  // 禁止打印调用栈信息
                .d("Simple message 3");

        XLog.b().d("Simple message 4");


       TextView logs= findViewById(R.id.tv_logs);
       logs.setText(str);
    }

    /*

   XLog 能干什么:

全局配置（TAG，各种格式化器...）或基于单条日志的配置
支持打印任意对象以及可自定义的对象格式化器
支持打印数组
支持打印无限长的日志（没有 4K 字符的限制）
XML 和 JSON 格式化输出
线程信息（线程名等，可自定义）
调用栈信息（可配置的调用栈深度，调用栈信息包括类名、方法名文件名和行号）
支持日志拦截器
保存日志文件（文件名和自动备份策略可灵活配置）
在 Android Studio 中的日志样式美观
简单易用，扩展性高
与其他日志库的不同:

优美的源代码，良好的文档
扩展性高，可轻松扩展和强化功能
轻量级，零依赖
     */



    String str="╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Simple message\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║My name is Elvis\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║java.lang.Object@fe93815\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.256 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║[d, d]\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║{\n" +
            "    ║    \"name\": \"Elvis\",\n" +
            "    ║    \"age\": 18\n" +
            "    ║}\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.258 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "    ║<team>\n" +
            "    ║    <member name=\"Elvis\"/>\n" +
            "    ║    <member name=\"Leon\"/>\n" +
            "    ║</team>\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.258 24101-24101/com.shiming.hement W/XLog: XLog is already initialized, do not initialize again\n" +
            "01-15 16:11:59.258 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Message\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Message with argument: age=18\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.259 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║{\n" +
            "    ║    \"name\": \"Elvis\",\n" +
            "    ║    \"age\": 18\n" +
            "    ║}\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.260 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "    ║<team>\n" +
            "    ║    <member name=\"Elvis\"/>\n" +
            "    ║    <member name=\"Leon\"/>\n" +
            "    ║</team>\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║\t├ com.shiming.hement.ui.log.XLogWeChatActivity.onCreate(XLogWeChatActivity.java:48)\n" +
            "    ║\t├ android.app.Activity.performCreate(Activity.java:7372)\n" +
            "    ║\t├ android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1218)\n" +
            "    ║\t├ android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3147)\n" +
            "    ║\t└ android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3302)\n" +
            "    ╟───────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "    ║Message with stack trace info\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║***********************************************************\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Thread: main\n" +
            "    ╟───────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "    ║\t├ com.shiming.hement.ui.log.XLogWeChatActivity.onCreate(XLogWeChatActivity.java:54)\n" +
            "    ║\t├ android.app.Activity.performCreate(Activity.java:7372)\n" +
            "    ║\t└ android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1218)\n" +
            "    ╟───────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "    ║Simple message 1\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.261 24101-24101/com.shiming.hement D/TEMP-TAG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║\t├ com.shiming.hement.ui.log.XLogWeChatActivity.onCreate(XLogWeChatActivity.java:58)\n" +
            "    ║\t├ android.app.Activity.performCreate(Activity.java:7372)\n" +
            "    ║\t├ android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1218)\n" +
            "    ║\t├ android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3147)\n" +
            "    ║\t├ android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3302)\n" +
            "    ║\t├ android.app.ActivityThread.-wrap12(Unknown Source:0)\n" +
            "    ║\t├ android.app.ActivityThread$H.handleMessage(ActivityThread.java:1891)\n" +
            "    ║\t├ android.os.Handler.dispatchMessage(Handler.java:108)\n" +
            "    ║\t├ android.os.Looper.loop(Looper.java:166)\n" +
            "    ║\t├ android.app.ActivityThread.main(ActivityThread.java:7425)\n" +
            "    ║\t├ java.lang.reflect.Method.invoke(Native Method)\n" +
            "    ║\t├ com.android.internal.os.Zygote$MethodAndArgsCaller.run(Zygote.java:245)\n" +
            "    ║\t└ com.android.internal.os.ZygoteInit.main(ZygoteInit.java:921)\n" +
            "    ╟───────────────────────────────────────────────────────────────────────────────────────────────────\n" +
            "    ║Simple message 2\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "01-15 16:11:59.261 24101-24101/com.shiming.hement D/X-LOG: ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Simple message 3\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ╔═══════════════════════════════════════════════════════════════════════════════════════════════════\n" +
            "    ║Simple message 4\n" +
            "    ╚═══════════════════════════════════════════════════════════════════════════════════════════════════\n";
}
