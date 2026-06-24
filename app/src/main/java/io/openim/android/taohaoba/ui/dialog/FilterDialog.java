package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.AttachPopupView;

import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.FilterBean;
import io.openim.android.taohaoba.bean.TransactionConfBean;

/**
 * 筛选
 */
public class FilterDialog extends AttachPopupView {

    private Context context;
    private OnFilterChangeListener mListener;
    private List<TransactionConfBean> list;

    public FilterDialog(@NonNull Context context, List<TransactionConfBean> list, OnFilterChangeListener listener) {
        super(context);
        this.context = context;
        this.list = list;
        this.mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_filter;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        RecyclerView rc_recyclerView = findViewById(R.id.rc_recyclerView);
        BaseQuickAdapter mAdapter = new BaseQuickAdapter(R.layout.item_filter, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                TransactionConfBean bean = (TransactionConfBean) o;

                TextView tv_name = baseViewHolder.getView(R.id.tv_name);

                tv_name.setText(bean.getName());
                tv_name.setTextColor(context.getColor(bean.isCheck() ? R.color.color_EACA92 : R.color.white));

                baseViewHolder.itemView.setOnClickListener(v -> {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck(i == baseViewHolder.getPosition());
                    }
                    notifyDataSetChanged();
                    if (mListener != null) {
                        mListener.onItemClick(bean.getId(), bean.getName());
                    }
                });
            }
        };
        rc_recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rc_recyclerView.setAdapter(mAdapter);

    }

    public interface OnFilterChangeListener {
        void onItemClick(int id, String name);
    }

}
