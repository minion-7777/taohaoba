package com.tencent.qcloud.tuikit.tuichat.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.tencent.qcloud.tuikit.tuichat.R;


/**
 * 图片点击放大查看工具类
 * 使用示例：
 * ImageZoomUtil.attach(context, imageView, R.drawable.your_image);
 */
public class ImageZoomUtil {

    /**
     * 绑定点击放大功能（默认图片）
     * @param context   上下文
     * @param thumbView 缩略图ImageView
     * @param imageRes  大图资源ID
     */
    public static void attach(final Context context, ImageView thumbView, final int imageRes) {
        thumbView.setOnClickListener(v -> showZoomView(context, imageRes));
    }

    /**
     * 绑定点击放大功能（网络图片）
     * @param context   上下文
     * @param thumbView 缩略图ImageView
     * @param imageUrl  大图URL（需自行处理图片加载，如Glide）
     */
    public static void attach(final Context context, ImageView thumbView, final String imageUrl) {
        thumbView.setOnClickListener(v -> showZoomView(context, imageUrl));
    }

    /**
     * 显示放大后的图片视图
     */
    public static void showZoomView(Context context, Object imageSource) {
        if (!(context instanceof Activity)) return;
        Activity activity = (Activity) context;

        // 1. 创建父容器（全屏半透明背景）
        RelativeLayout container = new RelativeLayout(context);
        container.setBackgroundColor(Color.parseColor("#99000000"));

        // 2. 添加可缩放的PhotoView
        PhotoView photoView = new PhotoView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        photoView.setLayoutParams(params);

        // 加载图片（根据类型区分）
        if (imageSource instanceof Integer) {
            photoView.setImageResource((Integer) imageSource);
        } else if (imageSource instanceof String) {
            Glide.with(context).load((String) imageSource).into(photoView);
        }

        // 3. 添加关闭按钮
        ImageView closeButton = new ImageView(context);
        closeButton.setImageResource(R.mipmap.ic_menu_close_clear_cancel);
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        closeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        closeParams.topMargin = 150;
        closeParams.rightMargin = 100;
        closeButton.setLayoutParams(closeParams);

        // 4. 组合视图
        container.addView(photoView);
        container.addView(closeButton);

        // 5. 通过WindowManager添加为顶层窗口（显示在Dialog上层）
        WindowManager windowManager = activity.getWindowManager();
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL; // 应用面板类型，无需权限
        wmParams.format = PixelFormat.TRANSLUCENT;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        windowManager.addView(container, wmParams);

        // 6. 点击关闭时移除视图（而非隐藏）
        View.OnClickListener dismissListener = v -> {
            try {
                windowManager.removeView(container);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        closeButton.setOnClickListener(dismissListener);
        container.setOnClickListener(dismissListener);
        photoView.setOnClickListener(dismissListener);
    }
}
