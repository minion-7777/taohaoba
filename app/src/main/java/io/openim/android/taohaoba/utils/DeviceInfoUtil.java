package io.openim.android.taohaoba.utils;

import android.os.Build;

/**
 * @author: ChenQing
 * @date: 2025/1/13
 * @description：
 */
public class DeviceInfoUtil {

    /**
     * 获取手机型号
     * @return
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机制造商
     * @return
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取Android系统版本
     * @return
     */
    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }
}
