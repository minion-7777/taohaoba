package io.openim.android.taohaoba.ui.fragment;

import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.BalanceBean;
import io.openim.android.taohaoba.databinding.FragmentMeBinding;
import io.openim.android.taohaoba.ui.activity.me.ActivityCenterActivity;
import io.openim.android.taohaoba.ui.activity.me.AuthenticationCenterActivity;
import io.openim.android.taohaoba.ui.activity.me.BlackNumberQueryActivity;
import io.openim.android.taohaoba.ui.activity.me.CommodityManagementListActivity;
import io.openim.android.taohaoba.ui.activity.me.CouponActivity;
import io.openim.android.taohaoba.ui.activity.me.HelpCenterActivity;
import io.openim.android.taohaoba.ui.activity.me.MyAfterSalesActivity;
import io.openim.android.taohaoba.ui.activity.me.MyBuyOrderListActivity;
import io.openim.android.taohaoba.ui.activity.me.MyCollectionActivity;
import io.openim.android.taohaoba.ui.activity.me.MySellItOrderListActivity;
import io.openim.android.taohaoba.ui.activity.me.SettingActivity;
import io.openim.android.taohaoba.ui.activity.me.WalletActivity;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.ui.dialog.WithdrawalDialog;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.vm.me.WalletVM;

public class MeFragment extends BaseFragment<WalletVM> implements WalletVM.ViewAction{

    private FragmentMeBinding binding;
    private BalanceBean balanceBean;
//    private boolean isMasked;
    private BasePopupView withdrawalDialog;

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(WalletVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMeBinding.inflate(getLayoutInflater());
        initFragment();
        return binding.getRoot();
    }

    private void initFragment() {
        adjustToolbarForStatusBar(binding.clHead);

        //我买的订单列表
        binding.llBuyQuanbu.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyBuyOrderListActivity.class)
                        .putExtra("pageIndex", 0));
        });

        binding.llBuyDaifukuan.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyBuyOrderListActivity.class)
                        .putExtra("pageIndex", 1));
        });

        binding.llBuyJiaoyizhong.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyBuyOrderListActivity.class)
                        .putExtra("pageIndex", 2));
        });

        binding.llBuyYiwancheng.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyBuyOrderListActivity.class)
                        .putExtra("pageIndex", 3));
        });

        binding.llBuyYiquxiao.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyBuyOrderListActivity.class)
                        .putExtra("pageIndex", 4));
        });

        //我卖的订单列表
        binding.llSellQuanbu.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MySellItOrderListActivity.class)
                        .putExtra("pageIndex", 0));
        });

        binding.llSellDaifukuan.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MySellItOrderListActivity.class)
                        .putExtra("pageIndex", 1));
        });

        binding.llSellJiaoyizhong.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MySellItOrderListActivity.class)
                        .putExtra("pageIndex", 2));
        });

        binding.llSellYiwancheng.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MySellItOrderListActivity.class)
                        .putExtra("pageIndex", 3));
        });

        binding.llSellYiquxiao.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MySellItOrderListActivity.class)
                        .putExtra("pageIndex", 4));
        });

        //我的商品列表
        binding.llQuanbu.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 0));
        });

        binding.llYishangjia.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 2));
        });

        binding.llYixiajia.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 3));
        });

        binding.llShenhezhong.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 1));
        });

        binding.llShenheshibai.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 4));
        });

        //设置
        binding.ivSetting.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), SettingActivity.class));
        });

        //联系客服
        binding.llLiangxikefu.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
//                startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 1));
                vm.assignCustomerService();
        });

//        //黑号查询
//        binding.llHeihaochaxun.setOnClickListener(v->{
//            if (isLogin(LoginThbActivity.class))
//                startActivity(new Intent(getContext(), BlackNumberQueryActivity.class));
//        });

        //我的钱包
        binding.clWallet.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), WalletActivity.class));
        });

        //我的收藏
        binding.llShoucang.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
        });

        //提现
        binding.tvWithdrawal.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                if (balanceBean != null) {
                    withdrawalDialog();
                }
        });

        //认证中心
        binding.llRenzhengzhongxin.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), AuthenticationCenterActivity.class));
        });

        //查看余额
//        binding.tvZhanghuyuer.setOnClickListener(v->{
//            isMasked = !isMasked;
//            if (balanceBean == null) {
//                binding.tvAmount.setText(isMasked ? formatCurrency1("0") : maskText("0"));
//            }else {
//                binding.tvAmount.setText(isMasked ? formatCurrency1(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())));
//            }
//            Drawable newIcon = isMasked ? ContextCompat.getDrawable(getContext(), R.mipmap.ic_look_t) : ContextCompat.getDrawable(getContext(), R.mipmap.ic_look_f);
//            newIcon.setBounds(0, 0, newIcon.getIntrinsicWidth(), newIcon.getIntrinsicHeight());
//            binding.tvZhanghuyuer.setCompoundDrawables(null, null, newIcon, null);
//        });

        //售后中心
        binding.llShouhouzhoxin.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), MyAfterSalesActivity.class));
        });

        binding.llHelpCenter.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), HelpCenterActivity.class));
        });


        //会员钱包余额
        vm.getBalanceBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            balanceBean = it;
            binding.tvCollectCount.setText(String.valueOf(it.getUser_concern() != null ? it.getUser_concern() : 0));
            binding.tvName.setText(TextUtils.isEmpty(it.getUser().getNickname()) ? "" : it.getUser().getNickname());
            Glide.with(getContext())
                    .load(it.getUser().getAvatar())
                    .apply(new RequestOptions()
                            .centerCrop()// 图片裁剪方式
                            .placeholder(R.mipmap.ic_profile_picture)// 加载中的占位图
                            .error(R.mipmap.ic_profile_picture)// 加载失败的占位图
                    )
                    .into(binding.ivHead);

//            binding.tvAmount.setText(isMasked ? formatCurrency1(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())));
            binding.tvAmount.setText(formatCurrency1(balanceBean.getUser().getWallet().getBalance_available()));
        });


        //未读数
        vm.getBuySellGoodsBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            binding.llBuyDaifukuan.setCount(it.getBuy_order_count().getPending_count());
            binding.llBuyJiaoyizhong.setCount(it.getBuy_order_count().getTrade_count());
            binding.llBuyYiwancheng.setCount(it.getBuy_order_count().getComplete_count());
            binding.llBuyYiquxiao.setCount(it.getBuy_order_count().getCancel_count());

            binding.llSellDaifukuan.setCount(it.getSell_order_count().getPending_count());
            binding.llSellJiaoyizhong.setCount(it.getSell_order_count().getTrade_count());
            binding.llSellYiwancheng.setCount(it.getSell_order_count().getComplete_count());
            binding.llSellYiquxiao.setCount(it.getSell_order_count().getCancel_count());

            binding.llYishangjia.setCount(it.getUser_goods_count().getSell_count());
            binding.llYixiajia.setCount(it.getUser_goods_count().getLower_count());
            binding.llShenhezhong.setCount(it.getUser_goods_count().getPending_count());
            binding.llShenheshibai.setCount(it.getUser_goods_count().getFail_count());
        });

        //提现
        vm.getUserWithdrawalMutableLiveData().observe(getViewLifecycleOwner(), it->{
            withdrawalDialog.dismiss();
            toast("提现申请提交成功，请耐心等待");
        });

        //分配客服
        vm.getAssignCustomerServiceMutableLiveData().observe(getViewLifecycleOwner(), it->{
            Intent intent = new Intent(getContext(), TUIC2CChatActivity.class);
            intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
            intent.putExtra(TUIConstants.TUIChat.CHAT_ID, it);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        //活动中心
        binding.llHuodonzhoxin.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), ActivityCenterActivity.class));
        });

        //优惠券
        binding.cYouhuiquan.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class))
                startActivity(new Intent(getContext(), CouponActivity.class));
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        vm.getBalance();
        vm.buy_sell_goods();
    }

    /**
     * 提现
     */
    private void withdrawalDialog(){
        withdrawalDialog = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new WithdrawalDialog(getContext(), balanceBean.getUser().getWallet().getBalance_available(), new WithdrawalDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(Double current) {
                        vm.userWithdrawal(current);
                    }
                })).show();
    }

    /**
     * 公共弹窗
     */
    private void commonDialog(){
        BasePopupView popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CommonDialog(getContext(), "去认证", "您的账户尚未完成包赔认证，暂时无法进行提现操作哦。请尽快前往[认证中心]完成认证，以便顺利提现!", new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (isLogin(LoginThbActivity.class)) {
                            startActivity(new Intent(getContext(), AuthenticationCenterActivity.class));
                        }
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        if (msg.contains("未通过包赔认证")) {
            if (withdrawalDialog != null) {
                withdrawalDialog.dismiss();
            }
            commonDialog();
        }else {
            toast(msg);
        }
    }

    // 在目标 Fragment 中重写 onHiddenChanged()
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 相当于 onResume() 的逻辑
            if (balanceBean == null) {
                vm.getBalance();
                vm.buy_sell_goods();
            }
        }
    }
}
