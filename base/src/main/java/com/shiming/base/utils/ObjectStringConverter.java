package com.shiming.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by shiming on 16/11/19.
 */

public class ObjectStringConverter {

    /**
     * 将一个序列化的对象转换成字符串
     * @param object
     * @return
     */
    public static String objectToString(Object object){
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os= null;
        String bytesToHexString = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(object);
            bytesToHexString = bytesToHexString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytesToHexString;
    }

    /**
     * 将字符串转换成对象
     * @param objStr
     * @return
     */
    public static Object stringToObject(String objStr){
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            byte[] stringToBytes = StringToBytes(objStr);
            bis=new ByteArrayInputStream(stringToBytes);
            is = new ObjectInputStream(bis);
            return is.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static byte[] StringToBytes(String data){
        String hexString=data.toUpperCase().trim();
        if (hexString.length()%2!=0) {
            return null;
        }
        byte[] retData=new byte[hexString.length()/2];
        for(int i=0;i<hexString.length();i++)
        {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch3;
            if(hex_char1 >= '0' && hex_char1 <='9')
                int_ch3 = (hex_char1-48)*16;   //// 0 的Ascll - 48
            else if(hex_char1 >= 'A' && hex_char1 <='F')
                int_ch3 = (hex_char1-55)*16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch4;
            if(hex_char2 >= '0' && hex_char2 <='9')
                int_ch4 = (hex_char2-48); //// 0 的Ascll - 48
            else if(hex_char2 >= 'A' && hex_char2 <='F')
                int_ch4 = hex_char2-55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch3+int_ch4;
            retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * desc:将数组转为16进制
     * @param array
     * @return
     * modified:
     */
    private static String bytesToHexString(byte[] array) {
        if(array == null){
            return null;
        }
        if(array.length == 0){
            return "";
        }
        StringBuffer sb = new StringBuffer(array.length);
        String sTemp;
        for (int i = 0; i < array.length; i++) {
            sTemp = Integer.toHexString(0xFF & array[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
