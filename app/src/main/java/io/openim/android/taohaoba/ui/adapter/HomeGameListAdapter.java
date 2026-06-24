package io.openim.android.taohaoba.ui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.RecommendListBean;
import io.openim.android.taohaoba.widgets.RoundImageView;

public class HomeGameListAdapter extends BaseQuickAdapter<RecommendListBean.GameDTO, BaseViewHolder> {

    public HomeGameListAdapter(List<RecommendListBean.GameDTO> data) {
        super(R.layout.item_game_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, RecommendListBean.GameDTO s) {
        baseViewHolder.setText(R.id.tv_text, s.getName());
        RoundImageView imageView = baseViewHolder.getView(R.id.iv_img);
        if (!TextUtils.isEmpty(s.getImage())) {
            Glide.with(baseViewHolder.itemView.getContext())
                    .load(s.getImage())
                    .apply(new RequestOptions()
                            .transform(new RoundedCorners(10)) // 圆角半径（单位：像素）
                            .placeholder(R.mipmap.ic_more)// 加载中的占位图
                            .error(R.mipmap.ic_more)// 加载失败的占位图
                            .centerCrop()// 图片裁剪方式
                    )
                    .into(imageView);
        }
    }
}