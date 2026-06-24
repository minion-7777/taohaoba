package io.openim.android.taohaoba.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.taohaoba.R;

public class BadgeNumberUtil {

    private static final String TAG = "BadgeNumberUtil";

    public static void setBadgeNum(Context context, int num) {
        if (getMobileType().equals("Xiaomi")) { // 小米、红米
            setXiaoMiBadgeNum(context, num);
        }else if (getMobileType().equals("HONOR") // 荣耀
                || getMobileType().equals("HUAWEI") // 华为
                || getMobileType().equals("NOVA"))  // nova
        {
            setHUAWEIIconBadgeNum(context, num);
        } else if (getMobileType().equals("vivo")) { // vivo
            setVIVOBadgeNumber(context, num);
        } else if (getMobileType().equals("OPPO")) { // OPPO
            setOPPOBadgeNumber(context, num);
        }
    }

    /**
     * 小米手机创建通知信息并创建角标
     *
     * @param context
     * @param num
     */
    public static void setXiaoMiBadgeNum(Context context, int num) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "badge_channel";
        // 创建通知渠道 (Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "角标通知", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true); // 关键：启用角标显示
            nm.createNotificationChannel(channel);
        }

        // 获取启动应用的 Intent
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // 创建 PendingIntent
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        // 构建通知
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("淘号8")
                .setContentText("您有" + num + "条新消息")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setNumber(num)
                .setAutoCancel(false)
                .setPriority(NotificationCompat.PRIORITY_MIN) // 最低优先级（可能不显示）
                .setVisibility(NotificationCompat.VISIBILITY_SECRET) // 隐藏内容
                .setSilent(true) // 静默通知（无声音、无震动）
                .setContentIntent(pendingIntent) // 设置点击意图
                .build();

        // MIUI 反射设置角标数量
        try {
            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
            method.invoke(extraNotification, num); // 动态设置角标数字
        } catch (Exception e) {
            e.printStackTrace();
        }

        nm.notify(0, notification); // 发送通知
        if (num == 0) {
            nm.cancel(0); // 立即取消通知（部分 MIUI 版本仍会保留角标）
        }
    }


    /**
     * 华为消息角标
     * @param context
     * @param count
     */
    public static void setHUAWEIIconBadgeNum(Context context,int count)  {
        Log.e(TAG, "--------sethuaweiBadgeNum----------");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        mBuilder.setContentTitle("淘号8");
        mBuilder.setTicker("test");
        mBuilder.setContentText("您有" + count + "条新消息");

        //点击set 后，app退到桌面等待3s看效果（有的launcher当app在前台设置未读数量无效）
        final Notification notification = mBuilder.build();

        setNotification(notification, 0, context);
        Bundle bunlde = new Bundle();
        bunlde.putString("package", context.getPackageName());
        bunlde.putString("class", getLauncherClassName(context));
        bunlde.putInt("badgenumber", count);
        context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
    }

    protected static void setNotification(Notification notification, int notificationId, Context context) {
        if (notification != null) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);
        }
    }

    protected static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }

        return info.activityInfo.name;
    }

    /**
     * OPPO消息角标
     * @param context
     * @param number
     */
    public static void setOPPOBadgeNumber(Context context, int number) {
        Log.e(TAG, "--------setoppoBadgeNum----------");
        try {
            if (number == 0) {
                number = -1;
            }
            Intent intent = new Intent("com.oppo.unsettledevent");
            intent.putExtra("pakeageName", context.getPackageName());
            intent.putExtra("number", number);
            intent.putExtra("upgradeNumber", number);
            if (canResolveBroadcast(context, intent)) {
                context.sendBroadcast(intent);
            } else {
                try {
                    Bundle extras = new Bundle();
                    extras.putInt("app_badge_count", number);
                    context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean canResolveBroadcast(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
        return receivers != null && receivers.size() > 0;
    }

    /**
     * vivo消息角标
     * @param context
     * @param number
     */
    public static void setVIVOBadgeNumber(Context context, int number) {
        Log.e(TAG, "--------setvivoBadgeNum----------");
        try {
            Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            String launchClassName = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().getClassName();
            intent.putExtra("className", launchClassName);
            intent.putExtra("notificationNum", number);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 手机类型 华为、小米、vivo、oppo
     * @desc 获取手机类型
     */
    public static String getMobileType() {
        return Build.MANUFACTURER;
    }

}
