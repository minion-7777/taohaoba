package io.openim.android.taohaoba.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class ClickUtil {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 输入框禁止输入中文
     * @param editText
     */
    public static void setInputFilter(EditText... editText) {
        // 通过正则表达式排除中文字符
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 判断源字符中是否包含中文字符
                if (source != null && source.length() > 0) {
                    // 匹配中文字符的正则表达式
                    if (source.toString().matches(".*[\\u4e00-\\u9fa5].*")) {
                        return ""; // 如果包含中文，返回空字符串，禁止输入
                    }
                }
                return null; // 允许输入的字符
            }
        };

        // 将过滤器应用到 EditText
        for (EditText text : editText) {
            text.setFilters(new InputFilter[]{filter});
        }
    }
}
