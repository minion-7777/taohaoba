package io.openim.android.taohaoba.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeAgoCalculatorUtil {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getTimeAgo(String dateTimeStr) {
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 解析输入时间为带时区的对象
        ZonedDateTime inputTime = LocalDateTime.parse(dateTimeStr, formatter)
                .atZone(ZoneId.systemDefault());
        // 获取当前时间（带时区）
        ZonedDateTime now = ZonedDateTime.now();

        // 计算时间差（自动处理时区与夏令时）
        Duration duration = Duration.between(inputTime, now);
        long totalMinutes = Math.abs(duration.toMinutes());

        // 判断时间方向（过去或未来）
        String direction = duration.isNegative() ? "后" : "前";

        // 按时间差分级显示
        if (totalMinutes >= 1440) {        // 超过1天
            long days = totalMinutes / 1440;
            return days + "天" + direction;
        } else if (totalMinutes >= 60) {  // 超过1小时但不足1天
            long hours = totalMinutes / 60;
            return hours + "小时" + direction;
        } else {                           // 按分钟显示
            return totalMinutes + "分钟" + direction;
        }
    }
}
