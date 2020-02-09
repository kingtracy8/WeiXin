package com.charles.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by trcay on 2020/2/8.
 */
public class CheckUtil {

    public static final String token = "charles";

    /**
     * 检验signature
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature,String timestamp,String nonce) throws Exception {

        String[] arr = new String[]{token,timestamp,nonce};

        //排序
        Arrays.sort(arr);

        //字符串拼接
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }

        //进行SHA1加密
        String temp = shaEncode(String.valueOf(content));


        return temp.equals(signature);
    }

    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

}
