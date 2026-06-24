package com.tencent.qcloud.tuikit.tuichat.classicui.adapter;


import static com.tencent.qcloud.tuikit.tuichat.util.StringArrayUtil.convertToArray;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.bean.OrderListBeanConversation;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.RoundImageView;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolderOrdersList> {

    private Activity activity;
    private List<OrderListBeanConversation.ListDTO> list;

    public OrderListAdapter(Activity activity, List<OrderListBeanConversation.ListDTO> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolderOrdersList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_orders_conversation, parent, false);
        return new ViewHolderOrdersList(view);
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
    public void onBindViewHolder(@NonNull ViewHolderOrdersList holder, int position) {
        OrderListBeanConversation.ListDTO item = list.get(position);
        holder.tv_order_number.setText("商品编号：" + item.getOrder_no());
        holder.tv_state.setText(item.getStatus_zh());
        holder.tv_title.setText(item.getGoods_title());
        holder.tv_tag2.setText(item.getGame_service_name());
        holder.tv_price.setText("¥" + item.getGoods_price());
        // 按钮点击
        holder.tv_send.setOnClickListener(v -> {
            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener.onItemSendClick(position);
            }
        });

        Glide.with(holder.itemView.getContext())
                .load(!TextUtils.isEmpty(item.getGoods_image()) ? convertToArray(item.getGoods_image())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(holder.iv_img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // 获取指定位置的数据项
    public OrderListBeanConversation.ListDTO getItem(int position) {
        if (position >= 0 && position < list.size()) {
            return list.get(position);
        }
        return null;
    }

    static class ViewHolderOrdersList extends RecyclerView.ViewHolder {
        RoundImageView iv_img;
        TextView tv_order_number,tv_state,tv_title,tv_tag2,tv_price,tv_send;

        public ViewHolderOrdersList(@NonNull View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_order_number = itemView.findViewById(R.id.tv_order_number);
            tv_state = itemView.findViewById(R.id.tv_state);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_tag2 = itemView.findViewById(R.id.tv_tag2);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_send = itemView.findViewById(R.id.tv_send);
        }
    }

}