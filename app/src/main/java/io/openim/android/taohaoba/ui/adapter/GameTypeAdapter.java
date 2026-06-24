package io.openim.android.taohaoba.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameListBean;

import java.util.List;

/**
 * 游戏类型
 */
public class GameTypeAdapter extends BaseQuickAdapter<GameListBean.GameTypeDTO, BaseViewHolder> {

    private Context context;
    public GameTypeAdapter(Context context, List<GameListBean.GameTypeDTO> data) {
        super(R.layout.item_game_type, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GameListBean.GameTypeDTO s) {

        TextView tv_name = baseViewHolder.getView(R.id.tv_name);
        tv_name.setText(s.getName());
        tv_name.setTextColor(context.getColor(s.isCheck() ? R.color.color_402802 : R.color.color_8F8F8F));
        tv_name.setBackground(context.getDrawable(s.isCheck() ? R.drawable.shape_radius15_ffd497_fafbf5 : R.drawable.shape_radius15_line_d6d6d6));

        tv_name.setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onItemCheck(s, baseViewHolder.getPosition());
            }
        });
    }

    public interface OnVerificationListener {
        void onItemCheck(GameListBean.GameTypeDTO s, int position);
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }
}