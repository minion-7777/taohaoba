package com.tencent.qcloud.tuikit.tuichat.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA 非对称加密算法，加解密工具类，
 * 加密长度 不能超过 128 个字节。
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RSA {

    public static final String TAG = RSA.class.getSimpleName() + " --> ";

    /**
     * 编码
     */
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * 标准 jdk 加密填充方式，加解密算法/工作模式/填充方式
     */
    public static final String ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

    /**
     * RSA 加密算法
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * RSA 最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取 PublicKey 对象
     *
     * @param pubKey 公钥，X509 格式
     */
    public static PublicKey getPublicKey(String pubKey) {
        try {
            // 将公钥进行 Base64 解码
            byte[] publicKey = base64Decode(pubKey);
            // 创建 PublicKey 对象并返回
            return KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(
                    new X509EncodedKeySpec(publicKey));
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 获取 PrivateKey 对象
     *
     * @param prvKey 私钥，PKCS8 格式
     */
    public static PrivateKey getPrivateKey(String prvKey) {
        try {
            // 私钥数据
            byte[] privateKey =base64Decode(prvKey);
            // 创建 PrivateKey 对象并返回
            return KeyFactory.getInstance(KEY_ALGORITHM)
                    .generatePrivate(new PKCS8EncodedKeySpec(privateKey));
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 处理异常
     */
    private static void handleException(Exception e) {
        e.printStackTrace();
        Log.e(TAG, TAG + e);
    }
    /**
     * 使用公钥将数据进行分段加密
     *
     * @param data   要加密的数据
     * @return 加密后的 Base64 编码数据，加密失败返回 null
     */
    public static String encryptByPublicKey(Context context, String data) {
        try {
            byte[] bytes = data.getBytes(CHARSET_UTF8);
            // 创建 Cipher 对象
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            // 初始化 Cipher 对象，加密模式
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(getRSAKeyFromAssets(context,"rsa_public_key.pem")));
            int inputLen = bytes.length;
            // 保存加密的数据
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0, i = 0;
            byte[] cache;
            // 使用 RSA 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
                }
                // 将加密以后的数据保存到内存
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 将加密后的数据转换成 Base64 字符串
            return base64Encode(encryptedData);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 使用私钥将加密后的 Base64 字符串进行分段解密
     *
     * @param encryptBase64Data 加密后的 Base64 字符串
     * @return 解密后的明文，解密失败返回 null
     */
    public static String decryptByPrivateKey(Context context,String encryptBase64Data) {
        try {
            // 将要解密的数据，进行 Base64 解码
            byte[] encryptedData = base64Decode(encryptBase64Data);
            // 创建 Cipher 对象，用来解密
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            // 初始化 Cipher 对象，解密模式
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(getRSAKeyFromAssets(context,"pkcs8_rsa_private_key.pem")));
            int inputLen = encryptedData.length;
            // 保存解密的数据
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0, i = 0;
            byte[] cache;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                // 将解密后的数据保存到内存
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, CHARSET_UTF8);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 使用 私钥 将数据进行分段加密
     *
     * @param data   要加密的数据
     * @return 加密后的 Base64 编码数据，加密失败返回 null
     */
    public static String encryptByPrivateKey(Context context,String data) {
        try {
            byte[] bytes = data.getBytes(CHARSET_UTF8);
            // 创建 Cipher 对象
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            // 初始化 Cipher 对象，加密模式
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(getRSAKeyFromAssets(context,"pkcs8_rsa_private_key.pem")));
            int inputLen = bytes.length;
            // 保存加密的数据
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0, i = 0;
            byte[] cache;
            // 使用 RSA 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(bytes, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(bytes, offSet, inputLen - offSet);
                }
                // 将加密以后的数据保存到内存
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            out.close();
            // 将加密后的数据转换成 Base64 字符串
            return base64Encode(encryptedData);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }

    /**
     * 使用 公钥 将加密后的 Base64 字符串进行分段解密
     *
     * @param encryptBase64Data 加密后的 Base64 字符串
     * @return 解密后的明文，解密失败返回 null
     */
    public static String decryptByPublicKey(Context context,String encryptBase64Data) {
        try {
            // 将要解密的数据，进行 Base64 解码
            byte[] encryptedData = base64Decode(encryptBase64Data);
            // 创建 Cipher 对象，用来解密
            Cipher cipher = Cipher.getInstance(ECB_PKCS1_PADDING);
            // 初始化 Cipher 对象，解密模式
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(getRSAKeyFromAssets(context,"rsa_public_key.pem")));
            int inputLen = encryptedData.length;
            // 保存解密的数据
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0, i = 0;
            byte[] cache;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                // 将解密后的数据保存到内存
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();
            return new String(decryptedData, CHARSET_UTF8);
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }
    /**
     * 读取安卓 assets 目录下的 RSA 公/私钥
     */
    public static String getRSAKeyFromAssets(Context context, String fileName) {
        try {
            if (TextUtils.isEmpty(fileName)) return null;
            // 打开 assets 目录下的文件
            InputStream in = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            String line;
            // 每次读取一行
            while ((line = reader.readLine()) != null) {
                if (line.charAt(0) != '-') {
                    builder.append(line).append('\r');
                }
            }
            reader.close();
            in.close();
            return builder.toString();
        } catch (Exception e) {
            handleException(e);
        }
        return null;
    }
    /**
     * 将 Base64 字符串 解码成 字节数组
     */
    public static byte[] base64Decode(String data) {
        return Base64.decode(data, Base64.NO_WRAP);
    }

    /**
     * 将 字节数组 转换成 Base64 编码
     */
    public static String base64Encode(byte[] data) {
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    /**
     * 将字节数组转换成 int 类型
     */
    public static int byte2Int(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getInt();
    }

    /**
     * 将 int 转换成 byte 数组
     */
    public static byte[] int2byte(int data) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(data);
        return buffer.array();
    }
}
