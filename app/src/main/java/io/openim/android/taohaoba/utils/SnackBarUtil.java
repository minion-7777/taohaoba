package io.openim.android.taohaoba.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import io.openim.android.taohaoba.R;

public class SnackBarUtil {

    /**
     * 自定义 SnackBar 布局
     *
     * @param activity a
     * @param view     a
     * @param msg      a
     * @param tip      as
     */
    public static void show(Activity activity, View view, String msg, String tip) {
        if (!"huawei".equals(Build.MANUFACTURER.toLowerCase())) {
            return;
        }
        try {


            //获取示例 findViewById(android.R.id.content) //LENGTH_LONG/LENGTH_SHORT: 会自动消失 LENGTH_INDEFINITE: 需要手动点击消失
            Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);
            //设置 Snackbar 的深度，避免被其他控件遮挡
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                snackbar.getView().setElevation(0);
            }
            //设置背景透明，避免自带黑色背景影响
            snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
            //设置padding 取消自定义时黑色边框
            snackbar.getView().setPadding(0, 0, 0, 0);
            @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
            //设置SnackBar的显示位置
            //ViewGroup.LayoutParams layoutParams = snackbarLayout.getLayoutParams();

            FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT); // 将原来Snackbar的宽高传入新的LayoutParams
            flp.gravity = Gravity.CENTER | Gravity.TOP; // 设置显示位置
//            flp.topMargin = StatusBarCompat.getStatusBarHeight(activity);

            ((View) snackbarLayout).setLayoutParams(flp);
            //获取自定义布局
            //LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View inflate = LayoutInflater.from(activity).inflate(R.layout.snack_bar_layout, null);
            //获取布局内控件
            TextView textView = inflate.findViewById(R.id.snacl_bar_title);

            //设置文本
            textView.setText(msg);
            TextView textViewSub = inflate.findViewById(R.id.snacl_bar_tip);
            textViewSub.setText(tip);
            //将自定义布局添加到 Snackbar 中
            snackbarLayout.addView(inflate);
            //显示 因为只有华为上架出现这个问题，我做了个判断
//            if ("huawei".equals(Build.MANUFACTURER.toLowerCase())) {

                // 自定义时间长度为6000毫秒（6秒）
                snackbar.setDuration(5000);
                snackbar.show();
//            }
        } catch (Exception e) {

        }
    }

}
