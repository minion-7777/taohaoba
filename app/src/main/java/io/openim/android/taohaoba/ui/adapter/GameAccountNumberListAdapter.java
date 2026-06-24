package io.openim.android.taohaoba.ui.adapter;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameGoodsListBean;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.utils.TimeAgoCalculatorUtil;
import io.openim.android.taohaoba.widgets.CircleImageView;
import io.openim.android.taohaoba.widgets.RoundImageView;

public class GameAccountNumberListAdapter extends BaseQuickAdapter<GameGoodsListBean.ListDTO, BaseViewHolder> {

    private BaseQuickAdapter baseQuickAdapter;

    public GameAccountNumberListAdapter(List<GameGoodsListBean.ListDTO> data) {
        super(R.layout.item_game_account_number,data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GameGoodsListBean.ListDTO it) {

        RecyclerView rc_recycler = baseViewHolder.getView(R.id.rc_recycler);
        RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);
//        CircleImageView iv_head = baseViewHolder.getView(R.id.iv_head);

        Glide.with(baseViewHolder.itemView.getContext())
                .load(!TextUtils.isEmpty(it.getImage()) ? convertToArray(it.getImage())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(iv_img);

//        Glide.with(baseViewHolder.itemView.getContext())
//                .load(!TextUtils.isEmpty(it.getUser().getAvatar()) ? it.getUser().getAvatar() : "")
//                .apply(new RequestOptions()
//                        .placeholder(R.mipmap.ic_profile_picture)// 加载中的占位图
//                        .error(R.mipmap.ic_profile_picture)// 加载失败的占位图
//                        .centerCrop()// 图片裁剪方式
//                )
//                .into(iv_head);

        baseViewHolder.setText(R.id.tv_hint, it.getGoods_no()+" "+it.getTitle());

        baseViewHolder.setText(R.id.tv_view_count, TextUtils.isEmpty(it.getView_count()) ? "0" : String.valueOf(it.getView_count()));

        baseViewHolder.setText(R.id.tv_give_up, TextUtils.isEmpty(it.getFavorite_count()) ? "0" : String.valueOf(it.getFavorite_count()));

//        baseViewHolder.setText(R.id.tv_name, TextUtils.isEmpty(it.getUser().getNickname()) ? "" : it.getUser().getNickname());

        baseViewHolder.setText(R.id.tv_time, TimeAgoCalculatorUtil.getTimeAgo(it.getCreated_time())+"发布");

        baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), it.getRetail_price()));

        if (!TextUtils.isEmpty(it.getLabel())) {
            // 使用逗号拆分字符串
            String[] parts = it.getLabel().split(",");

            // 将拆分后的字符串添加到 List 中
            List<String> list = new ArrayList<>();
            for (String part : parts) {
                list.add(part);
            }

            baseQuickAdapter = new BaseQuickAdapter(R.layout.item_tag, list) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    baseViewHolder.setText(R.id.tv_tag1, o.toString());
                }
            };
            rc_recycler.setLayoutManager(new LinearLayoutManager(baseViewHolder.itemView.getContext(), RecyclerView.HORIZONTAL, false));
            rc_recycler.setAdapter(baseQuickAdapter);
        }

    }
}