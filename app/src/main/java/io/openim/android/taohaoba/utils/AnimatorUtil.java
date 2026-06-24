package io.openim.android.taohaoba.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

public class AnimatorUtil {

    /**
     * 按钮点击时 X 轴和 Y 轴同时缩放动画
     * @param view 要执行动画的 View
     */
    public static void scaleXYAnimation(View view) {
        // 让按钮在 X 轴和 Y 轴方向先缩小到0.8倍，再恢复
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.8f, 1f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.8f, 1f);
        scaleXAnim.setDuration(300);
        scaleYAnim.setDuration(300);
        scaleXAnim.setInterpolator(new BounceInterpolator());
        scaleYAnim.setInterpolator(new BounceInterpolator());
        scaleXAnim.start();
        scaleYAnim.start();
    }

    /**
     * 按钮点击时透明度动画
     * @param view 要执行动画的 View
     */
    public static void alphaAnimation(View view) {
        // 让按钮透明度从 1 变为 0.5 再恢复到 1
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.5f, 1f);
        alphaAnim.setDuration(300);
        alphaAnim.start();
    }

    /**
     * 按钮点击时旋转动画
     * @param view 要执行动画的 View
     */
    public static void rotateAnimation(View view) {
        // 让按钮旋转 360 度
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        rotateAnim.setDuration(500);
        rotateAnim.start();
    }

    /**
     * 按钮点击时晃动动画
     * @param view 要执行动画的 View
     * @param counts 晃动次数
     */
    public static void shakeAnimation(View view) {
        // 让按钮在 X 轴方向左右晃动
        ObjectAnimator shakeAnim = ObjectAnimator.ofFloat(view, "translationX", 0f, 10f);
        shakeAnim.setDuration(500);
        // 设置循环插值器，实现晃动效果
        shakeAnim.setInterpolator(new CycleInterpolator(4));
        shakeAnim.start();
    }

    /**
     * 按钮点击时放大→缩小→恢复正常的动画
     * @param view 要执行动画的 View
     * @param scaleUp 放大倍数（如1.2f表示放大到120%）
     * @param scaleDown 缩小倍数（如0.9f表示缩小到90%）
     * @param duration 总动画时长（毫秒）
     */
    public static void scaleUpDownAnimation(View view, float scaleUp, float scaleDown, long duration) {
        // 缩放序列：正常(1f) → 放大(scaleUp) → 缩小(scaleDown) → 恢复正常(1f)
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 1f, scaleUp, scaleDown, 1f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 1f, scaleUp, scaleDown, 1f);

        scaleXAnim.setDuration(duration);
        scaleYAnim.setDuration(duration);
        // 使用加速减速插值器，动画更自然
        scaleXAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        // 同时播放X和Y轴动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleXAnim, scaleYAnim);
        animatorSet.start();
    }

    // 简化版（使用默认参数）
    public static void scaleUpDownAnimation(View view) {
        // 默认：放大到120% → 缩小到90% → 恢复，总时长500ms
        scaleUpDownAnimation(view, 1.2f, 0.9f, 500);
    }

}
