package io.openim.android.taohaoba.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TimeUtil {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 计算剩余秒数
     * @param inputTime 输入时间字符串（格式：2025-05-09 13:05:44）
     * @param inputMinutes 输入的分钟数（如30）
     * @return 剩余秒数（非负数）
     */
    public static long calculateRemainingSeconds(String inputTime, int inputMinutes) {
        // 1. 解析输入时间为 LocalDateTime
        LocalDateTime startTime = LocalDateTime.parse(inputTime, FORMATTER);

        // 2. 获取当前系统时间
        LocalDateTime currentTime = LocalDateTime.now();

        // 3. 计算时间差（秒）
        Duration duration = Duration.between(startTime, currentTime);
        long intervalSeconds = Math.abs(duration.getSeconds()); // 取绝对值

        // 4. 计算剩余秒数
        long totalInputSeconds = inputMinutes * 60L;
        return Math.max(0, totalInputSeconds - intervalSeconds); // 确保非负
    }
}
