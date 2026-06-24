package com.tencent.qcloud.tuikit.tuichat.classicui.adapter;


import static com.tencent.qcloud.tuikit.tuichat.util.PriceFormatUtils.formatPrice;
import static com.tencent.qcloud.tuikit.tuichat.util.StringArrayUtil.convertToArray;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.bean.GoodsListBean;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;


public class GoodListAdapter extends RecyclerView.Adapter<GoodListAdapter.ViewHolderGoodsList> {

    private Activity activity;
    private List<GoodsListBean.GoodsDTO> list;

    public GoodListAdapter(Activity activity, List<GoodsListBean.GoodsDTO> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderGoodsList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_goods_conversation, parent, false);
        return new ViewHolderGoodsList(view);
    }

    // 定义多种点击事件的接口
    public interface OnItemActionListener {
        void onItemSendClick(int position);       // item内按钮点击
    }

    private OnItemActionListener listener;

    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGoodsList holder, int position) {
        GoodsListBean.GoodsDTO item = list.get(position);

        Glide.with(holder.itemView.getContext())
                .load(!TextUtils.isEmpty(item.getImage()) ? convertToArray(item.getImage())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(holder.iv_img);

        holder.tv_goods_no.setText(item.getGoods_no());
        holder.tv_title.setText(item.getTitle());
        holder.tv_tag2.setText(TextUtils.isEmpty(item.getGame_service_name()) ? (item.getDevice_name()+ "," +item.getOperator_name()) : item.getGame_service_name());
        holder.tv_price.setText(formatPrice(holder.itemView.getContext(), item.getRetail_price()));

        if (!TextUtils.isEmpty(item.getLabel())) {
            // 使用逗号拆分字符串
            String[] parts = item.getLabel().split(",");

            // 将拆分后的字符串添加到 List 中
            List<String> list = new ArrayList<>();
            for (String part : parts) {
                list.add(part);
            }

            BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter(R.layout.item_tag, list) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    baseViewHolder.setText(R.id.tv_tag1, o.toString());
                }
            };
            holder.rc_recycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext(), RecyclerView.HORIZONTAL, false));
            holder.rc_recycler.setAdapter(baseQuickAdapter);
        }

        // 按钮点击
        holder.tv_send.setOnClickListener(v -> {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemSendClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 获取指定位置的数据项
    public GoodsListBean.GoodsDTO getItem(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    static class ViewHolderGoodsList extends RecyclerView.ViewHolder {
        RoundImageView iv_img;
        TextView tv_goods_no,tv_title,tv_tag2,tv_price,tv_send;
        RecyclerView rc_recycler;
        public ViewHolderGoodsList(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_goods_no = itemView.findViewById(R.id.tv_goods_no);
            tv_title = itemView.findViewById(R.id.tv_title);
            rc_recycler = itemView.findViewById(R.id.rc_recycler);
            tv_tag2 = itemView.findViewById(R.id.tv_tag2);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_send = itemView.findViewById(R.id.tv_send);
        }
    }

}