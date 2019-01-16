package com.shiming.hement.utils;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/16 10:51
 */

public class AESUtils {
        private static int BUFFER_SIZE = 8192;
        /**
         * 从指定文件获取数据解密后存储到另外一个文件
         *
         * @param filePath  需解锁文件路径
         * @param destFilePath  存储文件路径
         */
        public static void decrypt(String filePath, String destFilePath) {
            MoreAES aes = MoreAES.getInstances();
            String keyPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/key.dat";
            byte[] byteArrays = getBytesFromDat(keyPath);

            String ivPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/iv.dat";
            byte[] ivArray = getBytesFromDat(ivPath);

            aes.decrypt(filePath, destFilePath, byteArrays,ivArray);
        }
        /**
         * 获取密钥的byte数组
         * @param keyPath
         * @return
         */
        private static byte[] getBytesFromDat(String keyPath) {
            FileInputStream fis = null;
            ByteArrayOutputStream baos = null;
            byte[] byteArrays = null;
            try {
                fis = new FileInputStream(keyPath);
                byte[] b = new byte[BUFFER_SIZE];
                int len;
                baos = new ByteArrayOutputStream();
                while ((len = fis.read(b)) != -1) {
                    baos.write(b, 0, len);
                }
                byteArrays = baos.toByteArray();

            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {}
                }

                if (baos != null) {
                    try {
                        baos.flush();
                    } catch (IOException e) {}

                    try {
                        baos.close();
                    } catch (IOException e) {}

                }
            }
            return byteArrays;
        }

}
