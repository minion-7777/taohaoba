package io.openim.android.taohaoba.ui.adapter;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
import io.openim.android.taohaoba.bean.GoodsConcernListBean;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 我的收藏
 */
public class MyCollectionAdapter extends BaseQuickAdapter<GoodsConcernListBean.ListDTO, BaseViewHolder> {

    private BaseQuickAdapter baseQuickAdapter;

    public MyCollectionAdapter(List<GoodsConcernListBean.ListDTO> data) {
        super(R.layout.item_my_collection,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GoodsConcernListBean.ListDTO s) {

        RecyclerView rc_recycler = baseViewHolder.getView(R.id.rc_recycler);
        RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);
        ImageView iv_tag = baseViewHolder.getView(R.id.iv_tag);
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
        baseViewHolder.setText(R.id.tv_hint, s.getTitle());
        baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), String.valueOf(s.getRetail_price())));
        baseViewHolder.setText(R.id.tv_time, TextUtils.isEmpty(s.getDevice_name()) ? s.getGame_service_name() : (s.getDevice_name()+","+s.getOperator_name()));
        iv_tag.setVisibility(s.getReview_status() == 5 || s.getReview_status() == 2 ? View.VISIBLE : View.INVISIBLE);
        iv_tag.setImageResource(s.getReview_status() == 5 ? R.mipmap.ic_coll_sold : R.mipmap.ic_coll_removed);

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

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);

        void onClickDelete(int position);
    }

    private OnItemClickListener mListener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

}