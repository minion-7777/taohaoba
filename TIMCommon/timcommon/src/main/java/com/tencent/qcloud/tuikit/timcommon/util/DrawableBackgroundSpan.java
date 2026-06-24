package com.tencent.qcloud.tuikit.timcommon.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ReplacementSpan;

public class DrawableBackgroundSpan extends ReplacementSpan {
    private final Drawable background;
    private final int paddingLeft, paddingRight, paddingTop, paddingBottom;

    public DrawableBackgroundSpan(Drawable background, int padL, int padR, int padT, int padB) {
        this.background = background.mutate(); // 避免多位置共享状态
        this.paddingLeft = padL;
        this.paddingRight = padR;
        this.paddingTop = padT;
        this.paddingBottom = padB;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        // 计算文本宽度 + 内边距
        return (int) (paint.measureText(text, start, end) + paddingLeft + paddingRight);
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end,
                     float x, int top, int y, int bottom, Paint paint) {
        // 1. 计算文本实际宽度
        float textWidth = paint.measureText(text, start, end);

        // 2. 设置 Drawable 边界（含内边距）
        background.setBounds(
                (int) x,
                top - paddingTop,
                (int) (x + textWidth + paddingLeft + paddingRight),
                bottom + paddingBottom
        );

        // 3. 绘制背景
        background.draw(canvas);

        // 4. 绘制文本（水平居中）
        canvas.drawText(text, start, end, x + paddingLeft, y, paint);
    }
}
