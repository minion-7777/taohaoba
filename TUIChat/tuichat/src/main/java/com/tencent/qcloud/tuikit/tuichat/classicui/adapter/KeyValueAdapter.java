package com.tencent.qcloud.tuikit.tuichat.classicui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageHBean;

import java.util.List;


public class KeyValueAdapter extends RecyclerView.Adapter<KeyValueAdapter.KeyValueViewHolder> {

    private List<IntelligentTipsMessageHBean.ContentJsonBean> list;

    public KeyValueAdapter(List<IntelligentTipsMessageHBean.ContentJsonBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public KeyValueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(io.openim.android.ouicore.R.layout.item_key_value, parent, false);
        return new KeyValueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeyValueViewHolder holder, int position) {
        IntelligentTipsMessageHBean.ContentJsonBean item = list.get(position);
        holder.tvKey.setText(item.getKey());
        holder.tvValue.setText(item.getValue());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class KeyValueViewHolder extends RecyclerView.ViewHolder {
        TextView tvKey,tvValue;

        public KeyValueViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKey = itemView.findViewById(io.openim.android.ouicore.R.id.tv_key);
            tvValue = itemView.findViewById(io.openim.android.ouicore.R.id.tv_value);
        }
    }
}
