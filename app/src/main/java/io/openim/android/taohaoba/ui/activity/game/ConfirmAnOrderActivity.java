package io.openim.android.taohaoba.ui.activity.game;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.ClickUtil.isFastClick;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatPrice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getui.gs.sdk.GsManager;
import com.lxj.xpopup.XPopup;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;

import org.json.JSONObject;

import java.util.Map;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.OrderDetailsBean;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityConfirmAnOrderBinding;
import io.openim.android.taohaoba.ui.activity.me.UseCouponActivity;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;
import io.openim.android.taohaoba.ui.dialog.CheckDetailsDialog;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.utils.PayResult;
import io.openim.android.taohaoba.vm.me.OrderDetailsVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 确认订单
 */
@Route(path = Routes.Main.CONFIRM_AN_ORDER)
public class ConfirmAnOrderActivity extends BaseActivity<OrderDetailsVM, ActivityConfirmAnOrderBinding> implements OrderDetailsVM.ViewAction{

    private OrderDetailsBean detailsBean;
    private int order_id;
    private boolean isCheck = false;
    private static final int SDK_PAY_FLAG = 1;
    private int payType = 2;
    private boolean is_order_info;//是否有订单存在
    private int goods_id;
    private String userPhone;
    private boolean is_reparation = true;//是否购买包赔
//    private Double payment_price;//支付金额
    private int orderPlayType;
    private String group_id;
    private String group_name;
    private boolean isBalance;//是否使用组合支付
    private ActivityResultLauncher<Intent> resultLauncher;
    private Integer user_coupon_id, coupon_type = -1;
    private Double coupon_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(OrderDetailsVM.class);
        bindViewDataBinding(ActivityConfirmAnOrderBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        //开启沙箱支付
        if (BuildConfig.DEBUG) {
            EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        }

        initView();
        initListener();
        protocolInit();
    }

    private void initView() {
        is_order_info = getIntent().getBooleanExtra("is_order_info", true);
        userPhone = mmkv.decodeString(PreferencesKey.userPhone);

        //订单详情
        vm.getOrderDetailsBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            detailsBean = it;
            is_reparation = it.getIs_reparation() == 1;
            group_id = it.getIm_group_id();
            group_name = it.getIm_group_name();
            is_order_info = true;
            initData();
        });

        //订单支付
        vm.getOrderPayMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            if (payType == 1 && (detailsBean.getIs_combined_payment() == 0 && !isBalance)) {
                showToast("支付成功");
                try {
                    GsManager.getInstance().onEvent("pay", new JSONObject().put("pay_amount", totalPrice).put("pay_type", payType == 2 ? "zfb" : "balance"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ActivityManager.finishAllExceptActivity(MainActivity.class);
                if (!TextUtils.isEmpty(group_id)) {
                    Bundle param = new Bundle();
                    param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                    param.putString(TUIConstants.TUIChat.CHAT_ID, group_id);
                    TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
                }
            }else {
                initPay(it);
            }
        });

        //虚拟订单详情展示
        vm.getVirtualSaveBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            detailsBean = it;
            initData();
        });

        //创建订单和群聊
        vm.getPlaceOrderBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            order_id = it.getOrder_id();
            orderPlayType = 1;
            group_id = it.getIm_group().getIm_group_id();
            group_name = it.getIm_group().getGroup_name();
            vm.getOrderPlay(it.getOrder_id(), view.etOrderNumberCount.getText().toString().trim(), 1, detailsBean.getIs_combined_payment() == 1 || isBalance ? 2 : payType, is_reparation ? 1 : 0, totalPrice, detailsBean.getIs_combined_payment() == 1 || isBalance ? 1 : 0);
        });

        if (is_order_info) {
            order_id = getIntent().getIntExtra("order_id", 0);
            showLoadingDialog();
            vm.getOrderDetails(order_id);
        }else {
            goods_id = getIntent().getIntExtra("goods_id", 0);
            showLoadingDialog();
            vm.virtual_save(goods_id);
        }

        // 注册结果监听器
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        // 处理返回数据
                        // 先判断是否存在该Extra，存在才获取（避免默认值）
                        if (data.hasExtra("user_coupon_id")) {
                            user_coupon_id = data.getIntExtra("user_coupon_id", 0);
                        }
                        coupon_amount = TextUtils.isEmpty(data.getStringExtra("coupon_amount")) ? null : Double.parseDouble(data.getStringExtra("coupon_amount"));
                        if (data.hasExtra("coupon_type")) {
                            //优惠卷类型 1包赔卷 2支付卷 3服务费卷 4商品费卷
                            coupon_type = data.getIntExtra("coupon_type", 0);
                        }
                        setPaymentPrice(payType);
                    }
                }
        );
    }

    private double totalPrice = 0;

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //是否购买包赔
        view.ivBgC.setOnClickListener(v->{
            if (is_order_info) {
                return;
            }
            if (detailsBean != null) {
                is_reparation = !is_reparation;
                //如果取消包赔，且是包赔卷，清空优惠券
                if (!is_reparation && coupon_type == 1) {
                    user_coupon_id = null;
                }
                setPaymentPrice(payType);
            }
        });

        //选择优惠券
        view.tvSelectCoupon.setOnClickListener(v->{
            if (is_order_info) {
                return;
            }
            if (detailsBean != null) {
                Intent intent = new Intent(this, UseCouponActivity.class);
                intent.putExtra("goods_price", detailsBean.getGoods_price());//商品价格
                intent.putExtra("reparation_price", is_reparation ? detailsBean.getReparation_price() : 0);//包赔价格
                intent.putExtra("pattern_price", detailsBean.getPattern_price());//买家服务费
                resultLauncher.launch(intent); // 启动目标Activity[6,8](@ref)
            }
        });

        //支付宝
        view.radioButton1.setOnClickListener(v->{
            payType = 2;
            setPaymentPrice(payType);
        });

        //余额
        view.radioButton2.setOnClickListener(v->{
            payType = 1;
            setPaymentPrice(payType);
        });

        //查看明细
        view.clCheckDetails.setOnClickListener(v->{
            checkDetailsDialog();
        });

        //同意协议
        view.llCheck.setOnClickListener(v->{
            isCheck = !isCheck;
            view.ivCheck.setImageResource(isCheck ? R.mipmap.ic_check_t : R.mipmap.ic_check_f);
        });

        //支付
        view.tvPay.setOnClickListener(v->{

            if (isFastClick()) {
//                if (is_reparation) {
//                    payment_price = detailsBean.getGoods_price()+detailsBean.getPattern_price()+detailsBean.getReparation_price()-(coupon_amount != null ? coupon_amount : 0);
//                }else {
//                    payment_price = detailsBean.getGoods_price()+detailsBean.getPattern_price();
//                }

                if (TextUtils.isEmpty(view.etOrderNumberCount.getText().toString().trim())) {
                    shakeAnimation(view.etOrderNumberCount);
                    showToast("请填写联系方式");
                    return;
                }
                if (view.etOrderNumberCount.getText().toString().trim().length() != 11) {
                    shakeAnimation(view.etOrderNumberCount);
                    showToast("请输入正确的联系方式");
                    return;
                }
                if (!isCheck) {
                    shakeAnimation(view.ivCheck);
                    showToast("请勾选协议");
                    return;
                }
                showLoadingDialog();
                if (is_order_info) {
                    orderPlayType = 1;
                    if (payType == 2){
                        vm.getOrderPlay(order_id, view.etOrderNumberCount.getText().toString().trim(), 1, payType, is_reparation ? 1 : 0, totalPrice, 0);
                    }else {
                        if (detailsBean.getFreeze_price() != null && (detailsBean.getFreeze_price() == 0 || detailsBean.getFreeze_price() >= totalPrice)) {
                            detailsBean.setIs_combined_payment(0);
                            vm.getOrderPlay(order_id, view.etOrderNumberCount.getText().toString().trim(), 1, payType, is_reparation ? 1 : 0, totalPrice, 0);
                        } else {
                            detailsBean.setIs_combined_payment(1);
                            vm.getOrderPlay(order_id, view.etOrderNumberCount.getText().toString().trim(), 1, detailsBean.getIs_combined_payment() == 1 || isBalance ? 2 : payType, is_reparation ? 1 : 0, totalPrice, detailsBean.getIs_combined_payment() == 1 || isBalance ? 1 : 0);
                        }
                    }
                }else {
                    vm.place_order(goods_id, view.etOrderNumberCount.getText().toString().trim(), is_reparation ? 1 : 0, 1, user_coupon_id);
                }
            }

        });

    }

    private void setPaymentPrice(int payType){
        //"coupon_type"//优惠卷类型 1包赔卷 2支付卷 3服务费卷 4商品费卷
        if (detailsBean != null) {

            //计算优惠券能优惠的金额
            if (coupon_type == 1) {
                //包赔卷
                coupon_amount = is_reparation ? (coupon_amount != null && coupon_amount <= detailsBean.getReparation_price() ? coupon_amount : detailsBean.getReparation_price()) : 0;
            }else if (coupon_type == 2) {
                //支付卷
                coupon_amount = coupon_amount != null && coupon_amount <= (detailsBean.getGoods_price()+detailsBean.getPattern_price()+(is_reparation ? detailsBean.getReparation_price() : 0)) ? coupon_amount : (detailsBean.getGoods_price()+detailsBean.getPattern_price()+(is_reparation ? detailsBean.getReparation_price() : 0));
            }else if (coupon_type == 3) {
                //服务费卷
                coupon_amount = coupon_amount != null && coupon_amount <= detailsBean.getPattern_price() ? coupon_amount : detailsBean.getPattern_price();
            }else if (coupon_type == 4) {
                //商品费卷
                coupon_amount = coupon_amount != null && coupon_amount <= detailsBean.getGoods_price() ? coupon_amount : detailsBean.getGoods_price();
            }

            //减商品费卷
            double goodsPrice = coupon_type == 4 ? (detailsBean.getGoods_price() - coupon_amount) : detailsBean.getGoods_price();
            //减服务费卷
            double patternPrice = coupon_type == 3 ? (detailsBean.getPattern_price() - coupon_amount) : detailsBean.getPattern_price();
            //减包赔卷(is_reparation是否选择了包赔)
            double reparationPrice = is_reparation ? (coupon_type == 1 ? (detailsBean.getReparation_price() - coupon_amount) : detailsBean.getReparation_price()) : 0;
            //减支付券
            double payPrice = coupon_type == 2 ? coupon_amount : 0;

            //计算总金额
            totalPrice = goodsPrice + patternPrice + reparationPrice - payPrice;

            if (payType == 1) {
                isBalance = detailsBean.getBalance_available() > 0 && detailsBean.getBalance_available() < totalPrice;
            }else {
                isBalance = false;
            }
        }
        view.ivBgC.setImageResource(is_reparation ? R.mipmap.ic_c_order_bg_a : R.mipmap.ic_check_f);
        view.tvSelectCoupon.setText(coupon_amount != null && coupon_amount > 0 ? ("-" + formatCurrency(coupon_amount.toString())) : "");
        if (is_order_info && (coupon_amount == null || coupon_amount == 0)) {
            view.tvSelectCoupon.setHint("暂无可用");
        }
        view.tvJointReduction.setText("共减" + formatCurrency(coupon_amount != null && coupon_amount > 0 ? coupon_amount.toString() : "0"));
        view.tvOrderTotalPriceCount.setText(formatPrice(getBaseContext(), String.valueOf(totalPrice)));
        view.tvTotal.setText(formatCurrency(String.valueOf(totalPrice)));
        view.radioButton2.setText((isBalance ? ("余额(可用"+formatCurrency(String.valueOf(detailsBean.getBalance_available()))+") (需组合支付)") : "余额(可用"+formatCurrency(String.valueOf(detailsBean.getBalance_available()))+")"));
    }

    /**
     * 唤起支付
     * @param orderInfo 订单信息
     */
    private void initPay(String orderInfo){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(ConfirmAnOrderActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showToast("支付成功");
                        try {
                            GsManager.getInstance().onEvent("pay", new JSONObject().put("pay_amount", totalPrice).put("pay_type", payType == 2 ? "zfb" : "balance"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ActivityManager.finishAllExceptActivity(MainActivity.class);
                        if (!TextUtils.isEmpty(group_id)) {
                            Bundle param = new Bundle();
                            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                            param.putString(TUIConstants.TUIChat.CHAT_ID, group_id);
                            TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        vm.goods_stock_update(order_id);
                        showLoadingDialog();
                        vm.getOrderDetails(order_id);
                        showToast("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private void initData() {

        user_coupon_id = detailsBean.getUser_coupon_id();
        coupon_amount = detailsBean.getCoupon_amount_deduct();
        coupon_type = detailsBean.getConpon_type() != null ? detailsBean.getConpon_type() : -1;

        String[] imageviewParts = detailsBean.getGoods_image().split(",");
        Glide.with(this)
                .load(imageviewParts[0])
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(view.ivOrderGoodsImg);
        view.tvOrderGoodsName.setText(detailsBean.getGoods_no()+" "+detailsBean.getGoods_title());
        if (TextUtils.isEmpty(detailsBean.getDevice_name())) {
            view.tvOrderGoodsAreaCode.setText(detailsBean.getGame_service_name());
        }else {
            view.tvOrderGoodsAreaCode.setText(detailsBean.getDevice_name() + "." + detailsBean.getOperator_name());
        }
        view.tvPrice.setText(formatPrice(getBaseContext(), String.valueOf(detailsBean.getGoods_price())));
        view.tvOrderProductAmountCount.setText(formatCurrency(String.valueOf(detailsBean.getGoods_price())));
        view.tvOrderPlatformServiceFeeCount.setText(formatCurrency(String.valueOf(detailsBean.getPattern_price())));

        view.tvOrderCompensationFeeB.setText("账号若发生找回，可获得全额赔付"+formatCurrency(String.valueOf(detailsBean.getGoods_price()))+"元");
        view.tvOrderCompensationFeeC.setText(formatCurrency(String.valueOf(detailsBean.getReparation_price())));

        view.etOrderNumberCount.setText(TextUtils.isEmpty(detailsBean.getBuy_user_contact()) ? userPhone : detailsBean.getBuy_user_contact());
        view.tvHint.setText("包赔费用为商品价格的"+detailsBean.getReparation_info_ratio()+"%，最低"+formatCurrency1(String.valueOf(detailsBean.getReparation_info_price()))+"元。在本平台购买包赔的账号提供包赔证明。\n没有转手的账号，被找回，平台售后7天内无法追回账号将赔付号价全款给买家。");

        setPaymentPrice(payType);

        view.tvPayHint.setVisibility(detailsBean.getFreeze_price() != null && detailsBean.getFreeze_price() > 0 ? View.VISIBLE : View.GONE);
        view.tvPayHint.setText("已使用余额支付"+formatCurrency(String.valueOf(detailsBean.getFreeze_price()))+"元(取消订单可退回，若订单金额小于当前金额，使用余额支付后会退回多余金额。若使用支付宝需全额支付)");
    }

    /**
     * 协议
     */
    private void protocolInit() {
        String originalText = "提交订单即代表您已阅读并同意《平台虚拟物品交易规则》";

        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);

        int startIndex1 = originalText.indexOf("《平台虚拟物品交易规则》");
        int endIndex1 = startIndex1 + "《平台虚拟物品交易规则》".length();

        ClickableSpan clickableSpan1 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1003));
            }
        };

        builder.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        int blueColor = ContextCompat.getColor(this, R.color.color_EACA92);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        view.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        view.tvAgreement.setHighlightColor(Color.TRANSPARENT);
        view.tvAgreement.setText(builder);
    }

    private static abstract class CustomClickableSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            // 去掉下划线
            ds.setUnderlineText(false);
        }
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (orderPlayType == 1) {
            showLoadingDialog();
            vm.getOrderDetails(order_id);
        }
        showToast(msg);
        if (msg.contains("商品不存在")) {
            finish();
        }
    }

    private void checkDetailsDialog(){
        new XPopup.Builder(this)
                .navigationBarColor(Color.BLACK) // 设置导航栏背景色为黑色
                .isLightNavigationBar(true) // 导航栏图标设为白色（适配黑色背景）
                .asCustom(new CheckDetailsDialog(this, detailsBean.getGoods_price().toString(), detailsBean.getPattern_price().toString(), is_reparation ? detailsBean.getReparation_price().toString() : "0", coupon_amount != null ? coupon_amount.toString() : "0", String.valueOf(totalPrice), coupon_type))
                .show();
    }
}
