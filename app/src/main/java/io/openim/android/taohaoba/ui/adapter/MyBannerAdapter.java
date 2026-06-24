package io.openim.android.taohaoba.ui.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.ui.activity.me.ActivityCenterActivity;
import io.openim.android.taohaoba.ui.activity.me.ActivityCenterWebViewActivity;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.widgets.RoundImageView;

public class MyBannerAdapter extends BannerAdapter<BannerImgBean.ListDTO,MyBannerAdapter.BannerViewHolder> {


    public MyBannerAdapter(List<BannerImgBean.ListDTO> datas) {
        super(datas);
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        RoundImageView imageView = new RoundImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return new BannerViewHolder(imageView);
    }

    @Override
    public void onBindView(BannerViewHolder holder, BannerImgBean.ListDTO data, int position, int size) {
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(holder.itemView).load(data.getPath()).apply(options).into(holder.imageView);
        //设置点击事件
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //跳转类型:8无跳转 1页面 2H5链接 3小程序页面
//                if (data.getJump_type() == 1) {
//                    //跳转详情页
//                    if (isLogin(LoginThbActivity.class))
//                        holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ActivityCenterActivity.class));
//                } else if (data.getJump_type() == 2) {
//                    //跳转H5页
//                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), ActivityCenterWebViewActivity.class)
//                            .putExtra("pageType", 1)
//                            .putExtra("activity_jump_url", data.getTarget_url()));
//                }
//            }
//        });
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView view) {
            super(view);
            this.imageView = view;
        }
    }

}
