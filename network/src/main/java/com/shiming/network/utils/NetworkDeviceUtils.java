package com.shiming.network.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 * Created by shiming on 2017/4/3.
 */
public class NetworkDeviceUtils {

    public static final int NETWORK_NONE = -1;
    public static final int NETWORK_WIFI = 0;
    public static final int NETWORK_UNINET = 1;
    public static final int NETWORK_UNIWAP = 2;
    public static final int NETWORK_WAP_3G = 3;
    public static final int NETWORK_NET_3G = 4;
    public static final int NETWORK_CMWAP = 5;
    public static final int NETWORK_CMNET = 6;
    public static final int NETWORK_CTWAP = 7;
    public static final int NETWORK_CTNET = 8;
    public static final int NETWORK_MOBILE = 9;

    private NetworkDeviceUtils() {
    }

    /**
     * 判断是否已连接到网络.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 检查网络接连类型.
     *
     * @param context
     * @return NetworkType.NONE: 无网络连接<br>
     * NetworkType.WIFI: 通过WIFI连接网络<br>
     * NetworkType.CMWAP: 通过移动, 联通GPRS连接网络<br>
     * NetworkType.CTWAP: 通过电信GPRS连接网络<br>
     */
    public static int checkNetWorkType(Context context) {
        //屏闭飞行模式
        /*if (isAirplaneModeOn(context)) {
            return NetworkType.NONE;
		}*/

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED) {
            return NetworkType.NET;
        }

        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState() == NetworkInfo.State.CONNECTED) {
            String type = connectivityManager.getActiveNetworkInfo()
                    .getExtraInfo();
            if (!NetworkStringUtils.isEmpty(type) && type.toLowerCase().indexOf("wap") >= 0) {
                if (type.toLowerCase().indexOf("ctwap") >= 0) {
                    return NetworkType.CTWAP;
                } else {
                    return NetworkType.CMWAP;
                }
            } else {
                return NetworkType.NET;
            }
        }

        return NetworkType.NONE;
    }

    /**
     * 检查网络接连类型.
     *
     * @param context
     * @return NETWORK_NONE: 无网络连接<br>
     * NETWORK_WIFI: 通过WIFI连接网络<br>
     * NETWORK_CMWAP: 通过移动, 联通wap/GPRS连接网络<br>
     * NETWORK_WAP_3G: 通过3G连接网络<br>
     * NETWORK_NET_3G: 通过3G连接网络<br>
     * NETWORK_CMWAP: 通过移动, 联通wap/GPRS连接网络<br>
     * NETWORK_CTWAP: 通过电信wap/GPRS连接网络<br>
     * NETWORK_CMNET: 通过移动, 联通GPRS连接网络<br>
     * NETWORK_CTNET: 通过电信GPRS连接网络<br>
     */
    public static int checkNetworkTypeAll(Context context) {
        //飞行模式
        if (isAirplaneModeOn(context)) {
            return NETWORK_NONE;
        }

        ConnectivityManager mag = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mag.getActiveNetworkInfo();
        //没有网络
        if (info == null) {
            return NETWORK_NONE;
        }

        if (info.getTypeName().equals("WIFI")) {
            return NETWORK_WIFI;
        } else {
            if (info.getExtraInfo().equals("uninet"))
                return NETWORK_UNINET;
            if (info.getExtraInfo().equals("uniwap"))
                return NETWORK_UNIWAP;
            if (info.getExtraInfo().equals("3gwap"))
                return NETWORK_WAP_3G;
            if (info.getExtraInfo().equals("3gnet"))
                return NETWORK_NET_3G;
            if (info.getExtraInfo().equals("cmwap"))
                return NETWORK_CMWAP;
            if (info.getExtraInfo().equals("cmnet"))
                return NETWORK_CMNET;
            if (info.getExtraInfo().equals("ctwap"))
                return NETWORK_CTWAP;
            if (info.getExtraInfo().equals("ctnet"))
                return NETWORK_CTNET;

            return NETWORK_MOBILE;
        }
    }


    /**
     * 判断手机是否处于飞行模式.
     *
     * @param context
     * @return
     */
    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 获取设备序列号.
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 判断手机SDCard是否已安装并可读写.
     *
     * @return
     */
    public static boolean isSDCardUsable() {
        return Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
                .getExternalStorageState());
    }

    /**
     * 隐藏某焦点控件弹出的软件键盘.
     *
     * @param context
     * @param view
     */
    public static void hideSoftInputFromWindow(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Service.INPUT_METHOD_SERVICE);
        IBinder binder = view.getWindowToken();
        inputMethodManager.hideSoftInputFromWindow(binder, 0);
    }

    /**
     * Get the size in bytes of a bitmap.
     *
     * @param bitmap
     * @return size in bytes
     */
    public static int getBitmapSize(Bitmap bitmap) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }*/
        // Pre HC-MR1
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * Check how much usable space is available at a given path.
     *
     * @param path The path to check
     * @return The space available in bytes
     */
    public static long getUsableSpace(File path) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }*/
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    /**
     * Get the memory class of this device (approx. per-app memory limit)
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @return The cache dir
     */
//    public static File getDiskCacheDir() {
//    	return getDiskCacheDir(getApplicationContext());
//    }



    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @return The cache dir
     */
    public static File getDiskCacheDir(Context context) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        File cacheDir = null;
        if (isSDCardUsable()) {
            cacheDir = new File(Environment.getExternalStorageDirectory(),
                    ".shiming");
        } else {
            cacheDir = context.getFilesDir();
        }

        if (cacheDir != null && !cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        return cacheDir;
    }

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @return The cache dir
     */
//    public static File getDiskVoiceCacheDir(Context context) {
//        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
//        // otherwise use internal cache dir
//		File cacheDir = null;
//		if (isSDCardUsable()) {
//			cacheDir = new File(Environment.getExternalStorageDirectory(),
//					context.getResources().getString( R.string.default_voice_folder_name));
//		} else {
//			cacheDir = context.getFilesDir();
//		}
//
//    	if (cacheDir != null && !cacheDir.exists()) {
//    		cacheDir.mkdirs();
//    	}
//
//    	return cacheDir;
//    }

    /**
     * 获取CPU信息.
     *
     * @return "CPU核心个数 x CPU频率"
     */
    public static String getCpuInfo() {
        return getCpuCoreCount() + " x " + getCpuFrequency();
    }

    /**
     * 获取CPU核心个数.
     *
     * @return
     */
    private static int getCpuCoreCount() {
        int coreCount = 1;
        try {
            String cpuDiePath = "/sys/devices/system/cpu";
            File dir = new File(cpuDiePath);
            String[] cpuFiles = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return Pattern.matches("cpu\\d{1}", name);
                }
            });
            if (cpuFiles != null && cpuFiles.length > 0) {
                coreCount = cpuFiles.length;
            }
        } catch (Exception e) {
//			DebugUtils.error(e.getMessage(), e);
        }
        return coreCount;
    }

    /**
     * 获取CPU频率.
     *
     * @return
     */
    private static String getCpuFrequency() {
        String cpuFreq = "";
        BufferedReader bufferedReader = null;
        try {
            String[] args = {"/system/bin/cat",
                    "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
            ProcessBuilder cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            cpuFreq = bufferedReader.readLine();
            // convert from Kb to Gb
            float tempFreq = Float.valueOf(cpuFreq.trim());
            cpuFreq = tempFreq / (1000 * 1000) + "Gb";
            return cpuFreq;
        } catch (Exception e) {
            return NetworkStringUtils.isEmpty(cpuFreq) ? "N/A" : cpuFreq + "Kb";
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore.
                }
            }
        }
    }

    /**
     * 获得系统总内存大小.
     *
     * @param context
     * @return
     */
    public static String getSystemTotalMemory(Context context) {
        // 系统内存信息文件
        String memInfoFilePath = "/proc/meminfo";
        String firstLine;
        String[] arrayOfString;
        long initialMemory = 0;
        BufferedReader localBufferedReader = null;
        try {
            FileReader localFileReader = new FileReader(memInfoFilePath);
            localBufferedReader = new BufferedReader(
                    localFileReader, 10240);
            // 读取meminfo第一行, 系统总内存大小
            firstLine = localBufferedReader.readLine();
            arrayOfString = firstLine.split("\\s+");
            // 获得系统总内存, 单位是KB, 乘以1024转换为Byte
            initialMemory = Long.valueOf(arrayOfString[1].trim()) * 1024;
        } catch (Exception e) {
//			DebugUtils.error(e.getMessage(), e);
            // ignore.
        } finally {
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        // 内存大小规格化, Byte转换为KB或者MB
        return Formatter.formatFileSize(context, initialMemory);
    }

    /**
     * 获取系统当前可用内存.
     *
     * @param context
     * @return
     */
    public static String getSystemAvailMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        // 内存大小规格化, Byte转换为KB或者MB
        return Formatter.formatFileSize(context, memoryInfo.availMem);
    }

    /**
     * 读取RAW文件内容
     *
     * @param resid
     * @param encoding
     * @return
     */
    public static String getRawFileContent(int resid, String encoding, Context mContext) {
        InputStream is = null;
        Context context = mContext;
        try {
            is = context.getResources().openRawResource(resid);
        } catch (Exception e) {
        }
        if (is != null) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int current = 0;

            try {
                while ((current = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, current);
                }
                return buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return "";
    }

    /**
     * 网络(0:2G;1:3G;2:4G;3:WIFI;-1:未知)
     *
     * @param context
     * @return
     */
    public static int getNetworkType(Context context) {
        int strNetworkType = -1;

        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = 3;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();

                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = 0;
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = 1;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = 2;
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = 2;
                        } else {
                            strNetworkType = -1;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }

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
        }
        return "";
    }

}
