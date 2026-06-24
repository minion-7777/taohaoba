package io.openim.android.taohaoba.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import io.openim.android.taohaoba.R;

/**
 * 圆形图片
 */
public class CircleImageView extends AppCompatImageView {
    private int borderWidth = 0;      // 边框宽度
    private int borderColor = Color.WHITE; // 边框颜色
    private int viewSize;              // 控件实际宽高（正方形）
    private BitmapShader bitmapShader;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Matrix matrix = new Matrix();

    public CircleImageView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 读取自定义属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_border_width, 0);
        borderColor = a.getColor(R.styleable.CircleImageView_border_color, Color.WHITE);
        a.recycle();

        // 强制图片缩放模式为 CENTER_CROP
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 强制宽高相等（确保圆形）
        int size = Math.min(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(size, size);
        viewSize = size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            super.onDraw(canvas);
            return;
        }

        try {
            Bitmap bitmap = getBitmapFromDrawable(getDrawable());
            if (bitmap == null) return;

            // 1. 创建 BitmapShader
            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            // 2. 计算缩放比例（CENTER_CROP 模式）
            float scale;
            int bitmapSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
            scale = viewSize / (float) bitmapSize;
            matrix.setScale(scale, scale);
            bitmapShader.setLocalMatrix(matrix);

            // 3. 绘制圆形图片
            paint.setShader(bitmapShader);
            int center = viewSize / 2;
            int radius = center - borderWidth;
            canvas.drawCircle(center, center, radius, paint);

            // 4. 绘制边框（如果 borderWidth > 0）
            if (borderWidth > 0) {
                borderPaint.setStyle(Paint.Style.STROKE);
                borderPaint.setColor(borderColor);
                borderPaint.setStrokeWidth(borderWidth);
                canvas.drawCircle(center, center, radius, borderPaint);
            }
        } catch (Exception e) {
            super.onDraw(canvas); // 异常时回退默认绘制
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable != null) {
            // 处理 VectorDrawable 或其他类型
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    //------------------------- 动态设置方法 -------------------------
    public void setBorderWidth(int width) {
        borderWidth = width;
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        invalidate();
    }
}
