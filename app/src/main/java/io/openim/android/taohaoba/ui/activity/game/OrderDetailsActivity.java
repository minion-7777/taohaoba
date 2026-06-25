package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatPrice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIGroupChatActivity;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.OrderDetailsBean;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityOrderDetailsBinding;
import io.openim.android.taohaoba.ui.activity.me.MyCollectionActivity;
import io.openim.android.taohaoba.ui.activity.me.ViewVouchersActivity;
import io.openim.android.taohaoba.ui.dialog.CancellationOfOrderDialog;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.utils.TimeUtil;
import io.openim.android.taohaoba.vm.me.OrderDetailsVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 订单详情
 */
@Route(path = Routes.Main.ORDER_DETAILS)
@RequiresApi(api = Build.VERSION_CODES.O)
public class OrderDetailsActivity extends BaseActivity<OrderDetailsVM, ActivityOrderDetailsBinding> implements OrderDetailsVM.ViewAction {

    private OrderDetailsBean detailsBean;
    private int orderType; //1我买的  2我卖的
    private int order_id;
    private long time;
    private ActivityResultLauncher<Intent> resultLauncher;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(OrderDetailsVM.class);
        bindViewDataBinding(ActivityOrderDetailsBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    private void initView() {
        order_id = getIntent().getIntExtra("order_id", 0);
        orderType = getIntent().getIntExtra("orderType", 0);

        userId = mmkv.decodeString(PreferencesKey.userId, "");

        //订单详情
        vm.getOrderDetailsBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            detailsBean = it;
            initData();
        });

        //修改订单状态和更改订单价格
        vm.getOrderStatusSetMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            showLoadingDialog();
            vm.getOrderDetails(order_id);
        });

        showLoadingDialog();
        vm.getOrderDetails(order_id);

        // 注册结果监听器
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        showLoadingDialog();
                        vm.getOrderDetails(order_id);
                    }
                }
        );
    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvOrderNumber.setOnClickListener(v->{
            if (!TextUtils.isEmpty(detailsBean.getGoods_no())) {
                copyText(detailsBean.getGoods_no());
            }
        });

        view.tvOrderNumberCount.setOnClickListener(v->{
            if (!TextUtils.isEmpty(detailsBean.getOrder_no())) {
                copyText(detailsBean.getOrder_no());
            }
        });

        view.tvTime.setOnCountdownListener(() -> {
            runOnUiThread(this::showLoadingDialog);
            view.tvTime.stop();
            vm.getOrderDetails(order_id);
        });

        //取消订单
        view.tvCancellationOfOrder.setOnClickListener(v->{
            cancellationOfOrderDialog(order_id);
        });

        //联系客服
        view.tvContactCustomerService.setOnClickListener(v->{
            if (TextUtils.isEmpty(detailsBean.getIm_group_id())) {
                showToast("群聊尚未创建，请稍后再试");
                return;
            }
            ActivityManager.finishActivity(ActivityManager.isExist(TUIGroupChatActivity.class));
            Bundle param = new Bundle();
            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
            param.putString(TUIConstants.TUIChat.CHAT_ID, detailsBean.getIm_group_id());
            TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
        });

        //立即支付
        view.tvImmediatePayment.setOnClickListener(v->{
            startActivity(new Intent(this, ConfirmAnOrderActivity.class)
                    .putExtra("order_id", order_id)
                    .putExtra("is_order_info", true));
        });

        //确认验号
        view.tvConfirmVerificationNumber.setOnClickListener(v->{
            commonDialog(1, "确认验号吗？", order_id);
        });

        //确认收货
        view.tvConfirmReceipt.setOnClickListener(v->{
            commonDialog(2, "确认换绑吗？", order_id);
        });

        //查看凭证
        view.tvViewVouchers.setOnClickListener(v->{
            startActivity(new Intent(this, ViewVouchersActivity.class)
                    .putExtra("orderId", order_id));
        });

        //再次购买
        view.tvBuyAgain.setOnClickListener(v->{
            if (detailsBean != null)
                startActivity(new Intent(this, ProductDetailsActivity.class)
                        .putExtra("goodsId", detailsBean.getGoods_id()));
        });

        //改价
        view.tvPriceAdjustment.setOnClickListener(v->{
            if (detailsBean != null)
                changePriceDialog(order_id, String.valueOf(detailsBean.getGoods_price()), detailsBean.getSeller_service_ratio(), detailsBean.getSeller_service_price(), detailsBean.getSeller_amount_conf());
        });

        //发货
        view.tvSendOutGoods.setOnClickListener(v->{
            if (detailsBean != null) {
                Intent intent = new Intent(getBaseContext(), SubmintAccountInformationActivity.class);
                intent.putExtra("orderId", order_id)
                        .putExtra("gameId", detailsBean.getGame_id())
                        .putExtra("goodsId", detailsBean.getGoods_id());
                resultLauncher.launch(intent); // 启动目标Activity[6,8](@ref)
            }
        });

    }


    private void initData() {

        //支付状态 0：待支付 1：支付成功 2：支付失败 3：待发货 4：已发货 5：等待验号 6：已验号 7: 待确认收货 8：已完成 9：已取消 10：待退款 11：退款中 12：已退款 13：退款失败
        view.tvOrderState.setText(detailsBean.getStatus_zh());
        view.tvTime.setVisibility(GONE);
        setv(view.tvCancellationOfOrder, view.tvContactCustomerService, view.tvPriceAdjustment, view.tvSendOutGoods, view.tvImmediatePayment, view.tvConfirmVerificationNumber, view.tvConfirmReceipt, view.tvViewVouchers);
        if (orderType == 1) {
            switch (detailsBean.getStatus()) {
                case 0:
                    view.tvOrderState.setTextColor(Color.parseColor("#FF4646"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_obligation), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("请在30分钟内付款，超时系统将取消订单");
                    view.tvTime.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(detailsBean.getPlace_time())) {
                        time = TimeUtil.calculateRemainingSeconds(detailsBean.getPlace_time(), detailsBean.getUnpaid_conf_time());
                        if (time > 0) {
                            view.tvTime.init(time);
                            view.tvTime.start();
                        }
                    }
                    view.tvCancellationOfOrder.setVisibility(VISIBLE);
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    view.tvImmediatePayment.setVisibility(VISIBLE);
                    break;
                case 1:
                case 3:
                    view.tvOrderState.setTextColor(Color.parseColor("#EACA92"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_daifahuo), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("等待卖家发货");
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 4:
                case 5:
                    view.tvOrderState.setTextColor(Color.parseColor("#FF9212"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_jiaoyizhong), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("倒计时结束后，将自动确认验号");
                    view.tvTime.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(detailsBean.getSend_time())) {
                        time = TimeUtil.calculateRemainingSeconds(detailsBean.getSend_time(), detailsBean.getVerify_conf_time());
                        if (time > 0) {
                            view.tvTime.init(time);
                            view.tvTime.start();
                        }
                    }
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    view.tvConfirmVerificationNumber.setVisibility(detailsBean.getIs_customer_service_confirms_account() == 0 ? GONE : VISIBLE);
                    break;
                case 6:
                case 7:
                    view.tvOrderState.setTextColor(Color.parseColor("#FF9212"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_jiaoyizhong), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("倒计时结束后，将自动确认换绑");
                    view.tvTime.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(detailsBean.getVerify_time())) {
                        time = TimeUtil.calculateRemainingSeconds(detailsBean.getVerify_time(), detailsBean.getTake_conf_time());
                        if (time > 0) {
                            view.tvTime.init(time);
                            view.tvTime.start();
                        }
                    }
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    view.tvConfirmReceipt.setVisibility(VISIBLE);
                    break;
                case 8:
                    view.tvOrderState.setTextColor(Color.parseColor("#0EB272"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_jiaoyiwanchen), null, null, null); // 设置 drawableLeft 图片
                    if (detailsBean.getIs_reparation() == 1) {
                        view.tvOrderHint.setText("订单已购买包赔服务，出现找回情况可点击【联系客服】按钮协助处理。");
                        view.tvViewVouchers.setVisibility(VISIBLE);
                    } else {
                        view.tvOrderHint.setText("订单已完成");
                    }
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 9:
                    view.tvOrderState.setTextColor(Color.parseColor("#CBCBCB"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_yiquxiao), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("订单已被取消");
                    view.llBottom.setVisibility(GONE);
                    break;
                case 12:
                    view.tvOrderState.setTextColor(Color.parseColor("#CBCBCB"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_yiquxiao), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("已退回给买家");
                    view.llBottom.setVisibility(GONE);
                    break;
                case 13:

                    break;
            }
        }else {
            switch (detailsBean.getStatus()) {
                case 0:
                    view.tvOrderState.setTextColor(Color.parseColor("#FF4646"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_obligation), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("倒计时结束后，订单将自动关闭");
                    view.tvTime.setVisibility(VISIBLE);
                    if (!TextUtils.isEmpty(detailsBean.getPlace_time())) {
                        time = TimeUtil.calculateRemainingSeconds(detailsBean.getPlace_time(), detailsBean.getUnpaid_conf_time());
                        if (time > 0) {
                            view.tvTime.init(time);
                            view.tvTime.start();
                        }
                    }
                    view.tvPriceAdjustment.setVisibility(VISIBLE);
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 1:
                case 3:
                    view.tvOrderState.setTextColor(Color.parseColor("#EACA92"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_daifahuo), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("买家已付款，请尽快发货");
                    view.tvSendOutGoods.setVisibility(VISIBLE);
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    view.tvOrderState.setTextColor(Color.parseColor("#FF9212"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_jiaoyizhong), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("商品正在交易中，请配合客服完成换绑。");
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 8:
                    view.tvOrderState.setTextColor(Color.parseColor("#0EB272"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_jiaoyiwanchen), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("当前商品已售出，快去提取您的收益吧。");
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 9:
                    view.tvOrderState.setTextColor(Color.parseColor("#CBCBCB"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_yiquxiao), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("订单已被取消");
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 12:
                    view.tvOrderState.setTextColor(Color.parseColor("#CBCBCB"));
                    view.tvOrderState.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.mipmap.ic_order_yiquxiao), null, null, null); // 设置 drawableLeft 图片
                    view.tvOrderHint.setText("已退回给买家");
                    view.tvContactCustomerService.setVisibility(VISIBLE);
                    break;
                case 13:

                    break;
                default:
                    break;
            }
        }

        view.tvOrderNumber.setText("商品编号："+detailsBean.getGoods_no());
        String[] imageviewParts = detailsBean.getGoods_image().split(",");
        Glide.with(this)
                .load(imageviewParts[0])
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(view.ivOrderGoodsImg);
        view.tvOrderGoodsName.setText(detailsBean.getGoods_title());
        view.tvGameName.setText(detailsBean.getGame_name());
        if (TextUtils.isEmpty(detailsBean.getDevice_name())) {
            view.tvOrderGoodsAreaCode.setText(detailsBean.getGame_service_name());
        }else {
            view.tvOrderGoodsAreaCode.setText(detailsBean.getDevice_name() + "." + detailsBean.getOperator_name());
        }
        view.tvPrice.setText(formatPrice(getBaseContext(), String.valueOf(detailsBean.getGoods_price())));
        view.tvOrderProductAmountCount.setText(formatCurrency(String.valueOf(detailsBean.getGoods_price())));
        if (userId.equalsIgnoreCase(detailsBean.getBuy_user_id())){
            //买家
            view.tvOrderCompensationFeeCount.setVisibility(VISIBLE);
            view.tvOrderCompensationFee.setVisibility(VISIBLE);
            view.tvOrderPlatformServiceFeeCount.setText(formatCurrency(String.valueOf(detailsBean.getPattern_price())));
            view.tvOrderCompensationFeeCount.setText(formatCurrency(String.valueOf(detailsBean.getReparation_price())));
            view.tvOrderTotalPriceCount.setText(formatPrice(getBaseContext(), String.valueOf(detailsBean.getPayment_price())));
            if (detailsBean.getCoupon_amount_deduct() != null && detailsBean.getCoupon_amount_deduct() > 0) {
                view.tvOrderCouponFeeCount.setVisibility(VISIBLE);
                view.tvOrderCouponFee.setVisibility(VISIBLE);
                view.tvOrderCouponFeeCount.setText("-"+formatCurrency(String.valueOf(detailsBean.getCoupon_amount_deduct())));
            }
        }else {
            //卖家
            view.tvOrderPlatformServiceFeeCount.setText(formatCurrency(String.valueOf(detailsBean.getSeller_service_fee())));
            view.tvOrderTotalPriceCount.setText(formatPrice(getBaseContext(), String.valueOf(detailsBean.getGoods_price() - detailsBean.getSeller_service_fee())));
        }
        view.tvOrderNumberCount.setText(detailsBean.getOrder_no());
        view.tvOrderTime.setText(detailsBean.getPlace_time());

        view.clPayTime.setVisibility(TextUtils.isEmpty(detailsBean.getPay_time()) ? GONE : VISIBLE);
        view.tvPayTime.setText(TextUtils.isEmpty(detailsBean.getPay_time()) ? "" : detailsBean.getPay_time());

        view.clDealTime.setVisibility(TextUtils.isEmpty(detailsBean.getDeal_time()) ? GONE : VISIBLE);
        view.tvDealTime.setText(TextUtils.isEmpty(detailsBean.getDeal_time()) ? "" : detailsBean.getDeal_time());

        view.clCancelTime.setVisibility(TextUtils.isEmpty(detailsBean.getCancel_time()) && TextUtils.isEmpty(detailsBean.getRefund_time()) ? GONE : VISIBLE);
        view.tvCancelTime.setText(TextUtils.isEmpty(detailsBean.getRefund_time()) ? detailsBean.getCancel_time() : detailsBean.getRefund_time());
        view.tvCancelTimeTxt.setText(TextUtils.isEmpty(detailsBean.getRefund_time()) ? "取消时间" : "退款时间");
    }

    private void setv(View... views){
        for (View view : views) {
            view.setVisibility(GONE);
        }
    }

    private BasePopupView popupView;

    /**
     * 公共弹窗
     * @param type 1确认验号 2确认收货 3取消订单 4修改价格
     */
    private void commonDialog(int type, String hint, int order_id){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new CommonDialog(this, hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            vm.setOrderStatus(order_id, 1, null, "");
                        }else if (type == 2) {
                            vm.setOrderStatus(order_id, 2, null, "");
                        }
                    }
                })).show();
    }


    /**
     * 取消订单弹窗
     */
    private void cancellationOfOrderDialog(int order_id){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new CancellationOfOrderDialog(this, new CancellationOfOrderDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String refund_content) {
                        vm.setOrderStatus(order_id, 3, null, refund_content);
                    }
                })).show();
    }

    /**
     * 修改价格弹窗
     */
    private void changePriceDialog(int order_id, String price, Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new InputPriceDialog(this, price, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        vm.setOrderStatus(order_id, 4, Double.valueOf(amount), "");
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.tvTime.stop();
    }
}
