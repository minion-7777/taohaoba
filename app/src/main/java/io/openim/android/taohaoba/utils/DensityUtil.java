package io.openim.android.taohaoba.utils;

import android.content.Context;
import android.os.Build;

/**
 * @Description：
 */
public class DensityUtil {

    private DensityUtil() {
        // 私有构造函数，防止实例化
    }

    /**
     * 将 dp 转换为 px
     *
     * @param context 上下文
     * @param dpValue dp 值
     * @return 对应的 px 值
     */
    public static int dpToPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将 px 转换为 dp
     *
     * @param context 上下文
     * @param pxValue px 值
     * @return 对应的 dp 值
     */
    public static int pxToDp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将 sp 转换为 px
     *
     * @param context 上下文
     * @param spValue sp 值
     * @return 对应的 px 值
     */
    public static int spToPx(Context context, float spValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 将 px 转换为 sp
     *
     * @param context 上下文
     * @param pxValue px 值
     * @return 对应的 sp 值
     */
    public static int pxToSp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                return context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return 0;
    }

    public static int dipToPx(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dip * density);
    }

}
