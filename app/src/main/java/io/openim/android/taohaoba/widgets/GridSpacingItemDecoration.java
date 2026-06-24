package io.openim.android.taohaoba.widgets;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spanCount;     // 列数
    private final int spacing;       // 间距（像素）
    private final boolean includeEdge; // 是否包含边缘间距

    public GridSpacingItemDecoration(int spanCount, int spacingPx, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacingPx;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // 当前Item位置
        int column = position % spanCount;                  // 当前Item所在列

        if (includeEdge) {
            // 包含边缘间距的情况
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) { // 第一行
                outRect.top = spacing;
            }
            outRect.bottom = spacing;   // 所有Item底部间距
        } else {
            // 不包含边缘间距的情况
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;
            if (position >= spanCount) {
                outRect.top = spacing; // 非第一行顶部间距
            }
        }
    }
}
