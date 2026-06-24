package io.openim.android.taohaoba.ui.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.widgets.RoundImageView;

import java.util.List;

public class MyBuyOrderListAdapter extends BaseQuickAdapter<OrderListBean.ListDTO, BaseViewHolder> {

    public MyBuyOrderListAdapter(List<OrderListBean.ListDTO> data) {
        super(R.layout.item_my_buy_order_list, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, OrderListBean.ListDTO s) {
        TextView tv_afterSales = baseViewHolder.getView(R.id.tv_afterSales);
        TextView tv_viewVouchers = baseViewHolder.getView(R.id.tv_viewVouchers);
        TextView tv_immediatePayment = baseViewHolder.getView(R.id.tv_immediatePayment);
        TextView tv_cancellationOfOrder = baseViewHolder.getView(R.id.tv_cancellationOfOrder);
        TextView tv_contactCustomerService = baseViewHolder.getView(R.id.tv_contactCustomerService);
        TextView tv_confirmVerificationNumber = baseViewHolder.getView(R.id.tv_confirmVerificationNumber);
        TextView tv_confirmReceipt = baseViewHolder.getView(R.id.tv_confirmReceipt);
        TextView tv_tag = baseViewHolder.getView(R.id.tv_tag);

        setv(tv_afterSales, tv_viewVouchers, tv_immediatePayment, tv_cancellationOfOrder, tv_contactCustomerService, tv_confirmVerificationNumber, tv_confirmReceipt);

        baseViewHolder.setText(R.id.tv_order_number, "订单编号："+s.getOrder_no());
        tv_tag.setText(s.getStatus_zh());
        baseViewHolder.setText(R.id.tv_hint, s.getGoods_title());
        baseViewHolder.setText(R.id.tv_game_name, s.getGame_name());
        baseViewHolder.setText(R.id.tv_area_code, s.getGame_service_name());
        baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), String.valueOf(s.getGoods_price())));

        baseViewHolder.getView(R.id.tv_order_number).setOnClickListener(v->{
            //复制
            verificationListener.onCopyText(s.getOrder_no());
        });

        RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);

        Glide.with(baseViewHolder.itemView.getContext())
                .load(!TextUtils.isEmpty(s.getGoods_image()) ? convertToArray(s.getGoods_image())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(iv_img);

        //支付状态 0：待支付 1：支付成功 2：支付失败 3：待发货 4：已发货 5：等待验号 6：已验号 7: 待确认收货 8：已完成 9：已取消 10：待退款 11：退款中 12：已退款 13：退款失败
        switch (s.getStatus()){
            case 0:
                tv_cancellationOfOrder.setVisibility(VISIBLE);
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_immediatePayment.setVisibility(VISIBLE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_ff4646));
                tv_tag.setTextColor(getContext().getColor(R.color.color_FF4646));
                break;
            case 1:
            case 3:
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33eaca92));
                tv_tag.setTextColor(getContext().getColor(R.color.color_EACA92));
                break;
            case 4:
            case 5:
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_confirmVerificationNumber.setVisibility(s.getIs_customer_service_confirms_account() == 0 ? GONE : VISIBLE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33ff9212));
                tv_tag.setTextColor(getContext().getColor(R.color.color_FF9212));
                break;
            case 6:
            case 7:
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_confirmReceipt.setVisibility(VISIBLE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33ff9212));
                tv_tag.setTextColor(getContext().getColor(R.color.color_FF9212));
                break;
            case 8:
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_viewVouchers.setVisibility(s.getIs_reparation() == 1 ? VISIBLE : GONE);
                tv_afterSales.setVisibility(s.getOrder_post_sale() == null ? VISIBLE : GONE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_330eb272));
                tv_tag.setTextColor(getContext().getColor(R.color.color_0EB272));
                break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
                tv_contactCustomerService.setVisibility(VISIBLE);
                tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33cbcbcb));
                tv_tag.setTextColor(getContext().getColor(R.color.color_CBCBCB));
                break;
        }

        tv_afterSales.setOnClickListener(v->{
            verificationListener.onAfterSales(s.getId(), s.getGoods_image(), s.getGoods_title(), s.getGame_service_name(), s.getGoods_price());
        });

        tv_viewVouchers.setOnClickListener(v->{
            verificationListener.onViewVouchers(s.getId());
        });

        tv_immediatePayment.setOnClickListener(v->{
            verificationListener.onImmediatePayment(s.getId());
        });

        tv_cancellationOfOrder.setOnClickListener(v->{
            verificationListener.onCancellationOfOrder(s.getId());
        });

        tv_contactCustomerService.setOnClickListener(v->{
            verificationListener.onContactCustomerService(s.getIm_group_id(), s.getIm_group_name());
        });

        tv_confirmVerificationNumber.setOnClickListener(v->{
            verificationListener.onConfirmVerificationNumber(s.getId());
        });

        tv_confirmReceipt.setOnClickListener(v->{
            verificationListener.onConfirmReceipt(s.getId());
        });
    }

    public interface OnVerificationListener {
        void onAfterSales(int order_id, String img, String title, String gameServiceName, String price); // 售后回调
        void onViewVouchers(int id);//查看凭证
        void onImmediatePayment(int id);//立即支付
        void onCopyText(String str);//复制订单号
        void onCancellationOfOrder(int id);//取消订单
        void onContactCustomerService(String id, String name);//联系客服
        void onConfirmVerificationNumber(int id);//确认验号
        void onConfirmReceipt(int id);//确认收货
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    private void setv(View... views){
        for (View view : views) {
            view.setVisibility(GONE);
        }
    }
}