package com.tencent.qcloud.tuikit.tuichat.util;

import java.util.ArrayList;
import java.util.List;

public class StringArrayUtil {

    /**
     * 将逗号分隔字符串转换为String数组
     * @param input 输入字符串，如："1,2,3" 或 "1"
     * @return 非null的String数组
     */
    public static String[] convertToArray(String input) {
//        if (input == null || input.isEmpty()) {
//            return new String[0]; // 空值安全处理[1,4](@ref)
//        }
//
//        return Arrays.stream(input.split(",")) // 核心分割逻辑[2,8](@ref)
//                .map(String::trim)          // 去除首尾空格[5](@ref)
//                .filter(s -> !s.isEmpty())  // 过滤空元素[6](@ref)
//                .toArray(String[]::new);    // 转换为数组[7](@ref)
        if (input == null || input.isEmpty()) {
            return new String[0];
        }
        List<String> resultList = new ArrayList<>();
        int start = 0;
        int end;
        String delimiter = ",http";

        while ((end = input.indexOf(delimiter, start)) != -1) { // [3](@ref)
            // 截取当前片段（自动包含原内容）
            resultList.add(input.substring(start, end).startsWith("http") ? input.substring(start, end) : "http"+input.substring(start, end));
            start = end + delimiter.length(); // 跳过分隔符
        }
        // 处理最后一个片段（无尾部分隔符）
        if (start < input.length()) {
            resultList.add(input.substring(start).startsWith("http") ? input.substring(start) : "http"+input.substring(start));
        }
        return resultList.toArray(new String[0]);
    }

    /**
     * 增强版（处理超长字符串）
     */
    public static String[] convertToArrayFast(String input) {
        if (input == null) return new String[0];

        List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ',') {
                addNonEmpty(list, input.substring(start, i));
                start = i + 1;
            }
        }
        addNonEmpty(list, input.substring(start));
        return list.toArray(new String[0]); // 避免正则开销[5](@ref)
    }

    private static void addNonEmpty(List<String> list, String s) {
        String trimmed = s.trim();
        if (!trimmed.isEmpty()) list.add(trimmed);
    }
}
