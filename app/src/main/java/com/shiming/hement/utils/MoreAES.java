package com.shiming.hement.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>
 *  https://blog.csdn.net/bluegodisplay/article/details/51240951
 * </p>
 *
 * @author shiming
 * @version v1.0
 * @since 2019/1/16 11:00
 */

public class MoreAES {

        private static String TYPE = "AES";

        private static int BUFFER_SIZE = 1024;

        private static MoreAES moreAes;

        private MoreAES(){}

        public synchronized static MoreAES getInstances(){
            if(moreAes == null){
                moreAes = new MoreAES();
            }
            return moreAes;
        }

        private static Cipher getCipher(int mode, byte[] key, byte[] iv) {
            // mode =Cipher.DECRYPT_MODE or Cipher.ENCRYPT_MODE
            Cipher mCipher;

            try {
                mCipher = Cipher.getInstance(TYPE + "/CBC/PKCS5Padding");

                Key keySpec = new SecretKeySpec(key, "AES");
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                mCipher.init(mode, keySpec, ivSpec);

                return mCipher;
            }catch (InvalidKeyException e) {
                e.printStackTrace();
            }catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         *
         * 解密文件
         * @param srcFile
         * @param destFile
         * @param privateKey
         */

        public void decrypt(String srcFile, String destFile, byte[] privateKey,byte[] privateIv) {

            byte[] readBuffer = new byte[BUFFER_SIZE];

            Cipher deCipher = getCipher(Cipher.DECRYPT_MODE, privateKey,privateIv);

            if (deCipher == null)
                return; // init failed.

            CipherInputStream fis = null;

            BufferedOutputStream fos = null;

            int size;

            try {

                fis = new CipherInputStream(new BufferedInputStream(new FileInputStream(srcFile)), deCipher);

                fos = new BufferedOutputStream(new FileOutputStream(mkdirFiles(destFile)));

                while ((size = fis.read(readBuffer, 0, BUFFER_SIZE)) >= 0) {
                    fos.write(readBuffer, 0, size);

                }

                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {}
                }

                if (fos != null) {
                    try {
                        fos.flush();
                    } catch (IOException e) {}

                    try {
                        fos.close();
                    } catch (IOException e) {}

                }
            }
        }

        /**
         * 加密文件
         * @param srcFile
         * @param destFile
         * @param privateKey
         */

        public void crypt(String srcFile, String destFile, byte[] privateKey,byte[] privateIv) {

            byte[] readBuffer = new byte[BUFFER_SIZE];

            Cipher enCipher = getCipher(Cipher.ENCRYPT_MODE, privateKey,privateIv);

            if (enCipher == null)
                return; // init failed.

            CipherOutputStream fos = null;

            BufferedInputStream fis = null;

            int size;

            try {

                fos = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(destFile)), enCipher);

                fis = new BufferedInputStream(new FileInputStream(mkdirFiles(srcFile)));

                while ((size = fis.read(readBuffer, 0, BUFFER_SIZE)) >= 0) {

                    fos.write(readBuffer, 0, size);
                }

                fos.flush();

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            } finally {

                if (fis != null) {

                    try {
                        fis.close();
                    } catch (IOException e) {
                    }

                }

                if (fos != null) {

                    try {
                        fos.flush();
                    } catch (IOException e) {
                    }

                    try {
                        fos.close();
                    } catch (IOException e) {
                    }

                }

            }

        }

        private static File mkdirFiles(String filePath) throws IOException {

            File file = new File(filePath);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            file.createNewFile();

            return file;
        }

}
