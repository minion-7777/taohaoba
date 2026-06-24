package io.openim.android.taohaoba.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author: ChenQing
 * @date: 2024/12/28
 * @description： NestedScrollView里面套ViewPager导致ViewPager不展示和出现空白部分
 */
public class DynamicHeightViewPager extends ViewPager {

    private int current;
    private int height = 0;
    /**
     * 保存position与对于的View（保存view对应的索引）
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<>();

    private boolean scrollble = true;

    public DynamicHeightViewPager(Context context) {
        super(context);
    }

    public DynamicHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return scrollble && super.onInterceptTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > current) {
            View child = mChildrenViews.get(current);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height = child.getMeasuredHeight();
        }
        if (mChildrenViews.size() != 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重新设置高度
     *
     * @param current
     */
    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }
            setLayoutParams(layoutParams);
        }
    }
    /**
     * 保存position与对于的View（保存View对应的索引）,需要自适应高度的一定要设置这个
     */
    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollble && super.onTouchEvent(ev);
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }

}
