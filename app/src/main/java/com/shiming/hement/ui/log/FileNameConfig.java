package com.shiming.hement.ui.log;

import android.os.Environment;



import java.io.File;

/**
 * @author shiming
 * @date 2016/11/15
 */
public class FileNameConfig {

    //获取
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().toString();

    /**
     * 主目录
     */
    public static final String HOME_FOLDER = ROOT_DIR + File.separator + "hement";

    /**
     * log主目录
     */
    public static final String HOME_LOG_FOLDER = HOME_FOLDER + File.separator + "hement.log";
//    public static final String HOME_LOG_FOLDER =  File.separator + "hement.log";

    public static void initFolder() {
        FileUtils.createFolder(HOME_FOLDER, false);
        FileUtils.createFolder(HOME_LOG_FOLDER, false);
    }

}
