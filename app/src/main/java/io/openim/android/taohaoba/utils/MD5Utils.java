package io.openim.android.taohaoba.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密工具类，提供将字符串转换为 MD5 加密后的十六进制字符串的功能。
 */
public class MD5Utils {

    /**
     * 对输入的字符串进行 MD5 加密。
     *
     * @param input 待加密的字符串
     * @return MD5 加密后的十六进制字符串，如果加密过程出错则返回 null
     */
    public static String md5(String input) {
        try {
            // 获取 MD5 算法实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将输入字符串转换为字节数组并进行哈希计算
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 处理算法不存在的异常
            throw new RuntimeException(e);
        }
    }

}
