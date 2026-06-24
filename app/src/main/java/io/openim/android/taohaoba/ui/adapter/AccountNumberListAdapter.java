package io.openim.android.taohaoba.ui.adapter;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsListBean;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.widgets.RoundImageView;

public class AccountNumberListAdapter extends BaseQuickAdapter<GoodsListBean.GoodsDTO, BaseViewHolder> {

    private BaseQuickAdapter baseQuickAdapter;

    public AccountNumberListAdapter(List<GoodsListBean.GoodsDTO> data) {
        super(R.layout.item_game_account_number_list, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GoodsListBean.GoodsDTO s) {
        RecyclerView rc_recycler = baseViewHolder.getView(R.id.rc_recycler);
        RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);

        if (!TextUtils.isEmpty(s.getImage())) {
            Glide.with(baseViewHolder.itemView.getContext())
                    .load(convertToArray(s.getImage())[0])
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                            .error(R.mipmap.ic_default_image)// 加载失败的占位图
                            .centerCrop()// 图片裁剪方式
                    )
                    .into(iv_img);
        }

        baseViewHolder.setText(R.id.tv_hint, TextUtils.isEmpty(s.getTitle()) ? s.getGoods_no()+"" : s.getGoods_no()+" "+s.getTitle());
        baseViewHolder.setText(R.id.tv_service_name, TextUtils.isEmpty(s.getGame_service_name()) ? (s.getDevice_name()+","+s.getOperator_name()) : s.getGame_service_name());
        baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), s.getRetail_price()));
        baseViewHolder.setText(R.id.tv_view_count, TextUtils.isEmpty(s.getView_count()) ? "0" : String.valueOf(s.getView_count()));
        baseViewHolder.setText(R.id.tv_give_up, TextUtils.isEmpty(s.getFavorite_count()) ? "0" : String.valueOf(s.getFavorite_count()));

        if (!TextUtils.isEmpty(s.getLabel())) {
            // 使用逗号拆分字符串
            String[] parts = s.getLabel().split(",");

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