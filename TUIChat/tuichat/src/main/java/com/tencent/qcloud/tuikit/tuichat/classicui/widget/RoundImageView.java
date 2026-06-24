package com.tencent.qcloud.tuikit.tuichat.classicui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatImageView;

import com.tencent.qcloud.tuikit.tuichat.R;

import io.openim.android.ouicore.utils.Common;

/**
 * 圆角图片
 */
public class RoundImageView extends AppCompatImageView {
    private float cornerRadius;
    private float[] radii = new float[8]; // 四个角的圆角：左上、右上、右下、左下

    public RoundImageView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        // 统一圆角：默认值从 10px 改为 10dp（通过 TypedValue 转换为像素）
        float defaultRadius = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10,
                context.getResources().getDisplayMetrics()
        );
        cornerRadius = a.getDimension(R.styleable.RoundImageView_cornerRadius, defaultRadius);

        // 单独圆角（如果未设置，则使用统一圆角）
        float topLeft = a.getDimension(R.styleable.RoundImageView_topLeftRadius, cornerRadius);
        float topRight = a.getDimension(R.styleable.RoundImageView_topRightRadius, cornerRadius);
        float bottomRight = a.getDimension(R.styleable.RoundImageView_bottomRightRadius, cornerRadius);
        float bottomLeft = a.getDimension(R.styleable.RoundImageView_bottomLeftRadius, cornerRadius);
        radii = new float[]{topLeft, topLeft, topRight, topRight, bottomRight, bottomRight, bottomLeft, bottomLeft};
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 方法 2：使用 BitmapShader（推荐，性能更好）
         drawWithShader(canvas);
    }

    private void drawWithShader(Canvas canvas) {
        if (getDrawable() == null) return;

        try {
            // 获取图片Bitmap
            Bitmap bitmap = ((BitmapDrawable) getDrawable()).getBitmap();
            if (bitmap == null) return;

            // 创建图片着色器（确保图片拉伸/压缩适配View）
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix matrix = new Matrix();
            float scaleX = (float) getWidth() / bitmap.getWidth();
            float scaleY = (float) getHeight() / bitmap.getHeight();
            matrix.setScale(scaleX, scaleY); // 缩放图片至View大小
            shader.setLocalMatrix(matrix);

            // 创建画笔并设置着色器
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setShader(shader);

            // 创建圆角路径（支持单独圆角）
            Path path = new Path();
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            path.addRoundRect(rect, radii, Path.Direction.CW); // 使用radii数组设置每个角的圆角

            // 绘制圆角图片
            canvas.drawPath(path, paint);
        } catch (Exception e) {
            // 异常时使用默认绘制（避免崩溃）
            super.draw(canvas);
        }
    }

    /**
     * 动态设置圆角半径（单位：dp）
     */
    public void setCornerRadiusDp(float dpValue) {
        cornerRadius = Common.dp2px(dpValue);
        // 更新所有角为统一圆角
        radii = new float[]{
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius,
                cornerRadius, cornerRadius
        };
        invalidate(); // 刷新绘制
    }
}
