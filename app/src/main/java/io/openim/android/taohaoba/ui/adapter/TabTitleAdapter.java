package io.openim.android.taohaoba.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.TabTitleBean;

import java.util.List;

public class TabTitleAdapter extends BaseQuickAdapter<TabTitleBean, BaseViewHolder> {

    private int position = 0;

    public TabTitleAdapter(List<TabTitleBean> data) {
        super(R.layout.item_game_name_tab, data);
    }

    public void setCheck(int index){
        position = index;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, TabTitleBean tabTitleBean) {
        TextView tv_name = baseViewHolder.getView(R.id.tv_name);
        tv_name.setText(tabTitleBean.getName());
        tv_name.setTextColor(getContext().getColor(getItemPosition(tabTitleBean) == position ? R.color.color_EACA92 : R.color.color_D6D6D6));
        baseViewHolder.getView(R.id.view).setVisibility(getItemPosition(tabTitleBean) == position ? View.VISIBLE : View.INVISIBLE);
    }
}