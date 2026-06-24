package io.openim.android.taohaoba.ui.dialog;

import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;

import android.content.Context;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;

import io.openim.android.taohaoba.R;

/**
 * 查看明细
 */
public class CheckDetailsDialog extends BottomPopupView {

    private String goodsPrice;
    private String patternPrice;
    private String reparationPrice;
    private String reparationCoupon;
    private String total;
    private int coupon_type;

    public CheckDetailsDialog(Context context, String goodsPrice, String patternPrice, String reparationPrice, String reparationCoupon, String total, int coupon_type) {
        super(context);
        this.goodsPrice = goodsPrice;
        this.patternPrice = patternPrice;
        this.reparationPrice = reparationPrice;
        this.reparationCoupon = reparationCoupon;
        this.total = total;
        this.coupon_type = coupon_type;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_check_details;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        TextView tv_goods_price = findViewById(R.id.tv_goods_price);
        TextView tv_pattern_price = findViewById(R.id.tv_pattern_price);
        TextView tv_reparation_price = findViewById(R.id.tv_reparation_price);
        TextView tv_reparation_coupon = findViewById(R.id.tv_reparation_coupon);
        TextView tv_e = findViewById(R.id.tv_e);
        TextView tv_total = findViewById(R.id.tv_total);
        tv_goods_price.setText(formatCurrency(goodsPrice));
        tv_pattern_price.setText(formatCurrency(patternPrice));
        tv_reparation_price.setText(formatCurrency(reparationPrice));
        tv_reparation_coupon.setText("-"+formatCurrency(reparationCoupon));
        tv_total.setText(formatCurrency(total));
        //优惠卷类型 1包赔卷 2支付卷 3服务费卷 4商品费卷
        if (coupon_type == 1) {
            tv_e.setText("包赔优惠券");
        } else if (coupon_type == 2) {
            tv_e.setText("支付优惠券");
        } else if (coupon_type == 3) {
            tv_e.setText("服务费优惠券");
        } else if (coupon_type == 4) {
            tv_e.setText("商品费优惠券");
        }


        findViewById(R.id.iv_close).setOnClickListener(v->{
            dismiss();
        });

    }
}
