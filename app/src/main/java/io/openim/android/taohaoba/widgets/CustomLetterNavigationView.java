package io.openim.android.taohaoba.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import io.openim.android.taohaoba.R;

/**
 * 自定义字母导航栏
 *
 * 总的来说就四步
 * 1、测量控件尺寸{@link #onMeasure(int, int)}
 * 2、绘制显示内容（背景以及字符）{@link #onDraw(Canvas)}
 * 3、处理滑动事件{@link #onTouchEvent(MotionEvent)}
 *
 * @attr customTextColorDefault  //导航栏默认文字颜色
 * @attr customTextColorDown  //导航栏按下文字颜色
 * @attr customBackgroundColorDown  //导航栏按下背景颜色
 * @attr customLetterDivHeight  //导航栏内容高度间隔
 * @attr customTextSize  //导航栏文字尺寸
 * @attr customBackgroundAngle //导航栏背景角度
 */
public class CustomLetterNavigationView extends View {

    private static final String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"}; // 字母列表（可自定义）
    private Paint textPaint, bgPaint;
    private int selectedIndex = -1;
    private OnLetterSelectedListener listener;

    // 属性
    private int textSize;             // 字体大小（px）
    private int textColor;            // 默认字体颜色
    private int selectedTextColor;    // 选中字体颜色
    private int letterSpacing;        // 字母间距（px）
    private int selectedBgColor;      // 选中背景颜色
    private int itemSize;             // 新增：每个字母项的宽高（px）

    public CustomLetterNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 读取属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLetterNavigationView);
        textSize = a.getDimensionPixelSize(R.styleable.CustomLetterNavigationView_textSize, sp2px(14));
        textColor = a.getColor(R.styleable.CustomLetterNavigationView_textColor, Color.GRAY);
        selectedTextColor = a.getColor(R.styleable.CustomLetterNavigationView_selectedTextColor, Color.WHITE);
        letterSpacing = a.getDimensionPixelSize(R.styleable.CustomLetterNavigationView_letterSpacing, dp2px(8));
        selectedBgColor = a.getColor(R.styleable.CustomLetterNavigationView_selectedBgColor, Color.RED);
        a.recycle();

        // 初始化画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setColor(selectedBgColor);
        bgPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 动态计算每个字母项的宽高（确保宽高相同）
        int width = MeasureSpec.getSize(widthMeasureSpec);
        itemSize = width; // 每个字母项的宽高 = View 的宽度
        int totalHeight = itemSize * LETTERS.length + letterSpacing * (LETTERS.length - 1);
        setMeasuredDimension(width, totalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float currentY = 0;

        for (int i = 0; i < LETTERS.length; i++) {
            // 绘制选中背景（圆形）
            if (i == selectedIndex) {
                float centerX = itemSize / 2f;
                float centerY = currentY + itemSize / 2f;
                float radius = itemSize / 2f; // 圆形半径为宽高的一半
                canvas.drawCircle(centerX, centerY, radius, bgPaint);
            }

            // 设置字体颜色
            textPaint.setColor(i == selectedIndex ? selectedTextColor : textColor);

            // 绘制文本（居中）
            float baselineY = currentY + itemSize / 2f + (textPaint.getTextSize() - textPaint.descent()) / 2;
            canvas.drawText(LETTERS[i], itemSize / 2f, baselineY, textPaint);

            currentY += itemSize + letterSpacing;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int index = -1;
        float currentY = 0;

        for (int i = 0; i < LETTERS.length; i++) {
            if (y >= currentY && y <= currentY + itemSize) {
                index = i;
                break;
            }
            currentY += itemSize + letterSpacing;
        }

        if (index != -1 && index != selectedIndex) {
            selectedIndex = index;
            if (listener != null) {
                listener.onLetterSelected(LETTERS[index]);
            }
            invalidate();
        }
        return true;
    }
    // 新增方法：外部触发高亮
    public void highlightLetter(String letter) {
        for (int i = 0; i < LETTERS.length; i++) {
            if (LETTERS[i].equalsIgnoreCase(letter)) {
                selectedIndex = i;
                invalidate(); // 触发重绘
                break;
            }
        }
    }

    public interface OnLetterSelectedListener {
        void onLetterSelected(String letter);
    }

    public void setOnLetterSelectedListener(OnLetterSelectedListener listener) {
        this.listener = listener;
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics()
        );
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics()
        );
    }
}

