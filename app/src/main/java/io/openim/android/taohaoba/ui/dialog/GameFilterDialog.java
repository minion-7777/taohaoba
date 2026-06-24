package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameFilterBean;
import io.openim.android.taohaoba.ui.adapter.GameFilterAdapter;

public class GameFilterDialog extends BottomPopupView {

    private List<GameFilterBean.ListDTO> gameFilterBeanList;
    private Context context;
    private int spacingInPx;
    private int position;
    private BaseQuickAdapter baseQuickAdapter;

    // 定义回调接口
    public interface OnListener {
        void onSubmit(List<GameFilterBean.ListDTO> gameFilterBeanList); // 验证成功回调
    }

    private OnListener onListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnListener listener) {
        this.onListener = listener;
    }

    public GameFilterDialog(@NonNull Context context, List<GameFilterBean.ListDTO> gameFilterBeanList, OnListener listener) {
        super(context);
        this.context = context;
        this.gameFilterBeanList = gameFilterBeanList;
        this.onListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_game_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RecyclerView rc_recycler1 = findViewById(R.id.rc_recycler1);
        RecyclerView rc_recycler2 = findViewById(R.id.rc_recycler2);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_submit = findViewById(R.id.tv_submit);

        findViewById(R.id.iv_close).setOnClickListener(v -> dismiss());

        spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_game_filter1, gameFilterBeanList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                GameFilterBean.ListDTO it = (GameFilterBean.ListDTO) o;
                baseViewHolder.setText(R.id.tv_title, it.getName());
                baseViewHolder.setTextColor(R.id.tv_title, position == baseViewHolder.getLayoutPosition() ? baseViewHolder.itemView.getContext().getColor(R.color.color_EACA92) : baseViewHolder.itemView.getContext().getColor(R.color.white));
                baseViewHolder.itemView.setBackgroundResource(position == baseViewHolder.getLayoutPosition() ? R.color.color_201D19 : R.color.color_3C3C3C);
                baseViewHolder.setVisible(R.id.view, position == baseViewHolder.getLayoutPosition());
                baseViewHolder.itemView.setOnClickListener(v -> {
                    position = baseViewHolder.getLayoutPosition();
                    notifyDataSetChanged();
                    if (rc_recycler2.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager layoutManager = (LinearLayoutManager) rc_recycler2.getLayoutManager();
                        layoutManager.scrollToPositionWithOffset(position, 0);
                    }
                });

            }
        };
        rc_recycler1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL , false));
        rc_recycler1.setAdapter(baseQuickAdapter);

        //****************************/

        GameFilterAdapter quickAdapter = new GameFilterAdapter(gameFilterBeanList, spacingInPx);
        rc_recycler2.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rc_recycler2.setAdapter(quickAdapter);

        rc_recycler2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager == null) return;

                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();

                // 仅当位置变化时更新选中状态，避免重复刷新
                if (firstVisiblePosition != position) {
                    position = firstVisiblePosition;
                    baseQuickAdapter.notifyDataSetChanged();
                }
            }
        });

        tv_cancel.setOnClickListener(v -> {
            for (GameFilterBean.ListDTO listDTO : gameFilterBeanList) {
                for (GameFilterBean.NameListDTO nameListDTO : listDTO.getNameList()) {
                    nameListDTO.setSelected(false);
                }
                listDTO.setMinValue("");
                listDTO.setMaxValue("");
                for (GameFilterBean.ListDTO child : listDTO.getChildren()) {
                    for (GameFilterBean.NameListDTO nameListDTO : child.getNameList()) {
                        nameListDTO.setSelected(false);
                    }
                    child.setTag("and");
                    child.setAllSelected(false);
                    child.setChecked(false);
                }
            }
            quickAdapter.notifyDataSetChanged();
        });

        tv_submit.setOnClickListener(v -> {
            if (onListener != null) {
                onListener.onSubmit(gameFilterBeanList);
            }
            dismiss();
        });

    }

}
