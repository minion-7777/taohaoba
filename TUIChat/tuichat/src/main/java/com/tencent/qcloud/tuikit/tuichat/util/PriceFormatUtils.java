package com.tencent.qcloud.tuikit.tuichat.util;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

import androidx.annotation.NonNull;

import java.text.DecimalFormat;

public class PriceFormatUtils {
    /**
     * 格式化价格，并设置不同字体大小
     *
     * @param context 上下文（用于获取 sp 单位）
     * @param price   价格（单位：分，如 200 表示 2.00 元）
     * @return SpannableString，如 "￥1288.00"（￥12sp，数字18sp）
     */
    public static SpannableString formatPrice(Context context, String price) {
        // 解析字符串为浮动小数
        double priceValue;
        try {
            priceValue = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            // 如果解析失败，返回原始文本或默认处理
            priceValue = 0.0;
        }

        // 转换为元单位，并格式化为 2 位小数
        String formattedPrice = String.format("%.2f", priceValue);
        String priceText = "￥" + formattedPrice;

        SpannableString spannableString = new SpannableString(priceText);

        // 设置 "￥" 的字体大小为 12sp
        spannableString.setSpan(
                new AbsoluteSizeSpan(spToPx(context, 12)),
                0, 1, // "￥" 的位置是 0~1
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // 设置数字部分的字体大小为 18sp
        spannableString.setSpan(
                new AbsoluteSizeSpan(spToPx(context, 18)),
                1, priceText.length(), // 数字部分从 1 开始到结尾
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        return spannableString;
    }

    /**
     * 格式化价格，保留两位小数
     * @param amount   价格（单位：分，如 20 表示 ￥20.00 元）
     */
    public static String formatCurrency(String amount) {
        try {
            // 将输入的字符串转换为 double
            double value = Double.parseDouble(amount);

            // 格式化为货币格式，保留两位小数
            DecimalFormat decimalFormat = new DecimalFormat("￥0.00");
            return decimalFormat.format(value);
        } catch (NumberFormatException e) {
            // 如果输入值不能转换为数字，返回默认的格式
            return "￥0.00";
        }
    }

    /**
     * 格式化价格，保留两位小数
     * @param amount   价格（单位：分，如 20 表示 ￥20.00 元）
     */
    public static String formatCurrency1(String amount) {
        try {
            // 将输入的字符串转换为 double
            double value = Double.parseDouble(amount);

            // 格式化为货币格式，保留两位小数
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(value);
        } catch (NumberFormatException e) {
            // 如果输入值不能转换为数字，返回默认的格式
            return "0.00";
        }
    }

    /**
     * 可选：设置不同颜色
     *
     * @param context      上下文
     * @param price        价格（分）
     * @param symbolColor  ￥符号的颜色
     * @param numberColor  数字的颜色
     * @return SpannableString
     */
//    public static SpannableString formatPriceWithColor(
//            Context context,
//            int price,
//            @ColorInt int symbolColor,
//            @ColorInt int numberColor
//    ) {
//        SpannableString spannableString = formatPrice(context, price);
//
//        // 设置 "￥" 的颜色
//        spannableString.setSpan(
//                new ForegroundColorSpan(symbolColor),
//                0, 1,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//        // 设置数字部分的颜色
//        spannableString.setSpan(
//                new ForegroundColorSpan(numberColor),
//                1, spannableString.length(),
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//        );
//
//        return spannableString;
//    }

    /**
     * 将 sp 转换为 px（用于 AbsoluteSizeSpan）
     */
    private static int spToPx(@NonNull Context context, float sp) {
        float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }
}
