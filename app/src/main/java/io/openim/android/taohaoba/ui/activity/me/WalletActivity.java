package io.openim.android.taohaoba.ui.activity.me;

import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.core.content.ContextCompat;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.BalanceBean;
import io.openim.android.taohaoba.databinding.ActivityWalletBinding;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.ui.dialog.RealNameAuthenticationDialog;
import io.openim.android.taohaoba.ui.dialog.WithdrawalDialog;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;


/**
 * 我的钱包
 */
public class WalletActivity extends BaseActivity<WalletVM, ActivityWalletBinding> implements WalletVM.ViewAction{

    private BalanceBean balanceBean;
//    private boolean isMasked = true;
    private BasePopupView withdrawalDialog;
    private BasePopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityWalletBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    protected void initView() {

        //会员钱包余额
        vm.getBalanceBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            balanceBean = it;
            checkView();
        });

        //提现
        vm.getUserWithdrawalMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            withdrawalDialog.dismiss();
            showToast("提现申请提交成功，请耐心等待");
            vm.getBalance();
        });

        showLoadingDialog();
        vm.getBalance();
    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvReceivingAccount.setOnClickListener(v->{
            if (balanceBean != null) {
                if (TextUtils.isEmpty(balanceBean.getUser().getAlipay())) {
                    if (!balanceBean.isIs_authentication()){
                        realNameAuthenticationDialog();
                        return;
                    }
                    startActivity(new Intent(this, ReceivingAccountActivity.class)
                            .putExtra("realname", balanceBean.getRealname()));//未绑定支付宝页面
                }else {
                    startActivity(new Intent(this, ReceivingAccountBActivity.class)
                            .putExtra("alipay", balanceBean.getUser().getAlipay()));//已绑定支付宝页面
                }
            }
        });

        //资产明细
        view.tvAssetDetails.setOnClickListener(v->{
            startActivity(new Intent(this, AssetDetailsActivity.class));
        });

        //提现记录
        view.tvWithdrawalRecord.setOnClickListener(v->{
            startActivity(new Intent(this, WithdrawalRecordActivity.class));
        });

        //提现
        view.tvWithdrawal.setOnClickListener(v->{
            if (balanceBean != null) {
                withdrawalDialog();
            }
        });

//        //查看余额
//        view.tvZhanghuyuer.setOnClickListener(v->{
//            isMasked = !isMasked;
//            checkView();
//        });
    }

    private void checkView(){
//        if (balanceBean == null) {
//            view.tvAmount.setText(!isMasked ? formatCurrency1("0") : maskText("0"));
//            view.tvBalanceAvailable.setText(!isMasked ? formatCurrency1("0") : maskText("0"));
//            view.tvBond.setText(!isMasked ? formatCurrency1("0") : maskText("0"));
//            view.tvBalanceLocked.setText(!isMasked ? formatCurrency1("0") : maskText("0"));
//        }else {
//            view.tvAmount.setText(!isMasked ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_available()) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())));
//            view.tvBalanceAvailable.setText(!isMasked ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_available()) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBalance_available())));
//            view.tvBond.setText(!isMasked ? formatCurrency1(balanceBean.getUser().getWallet().getBond()) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBond())));
//            view.tvBalanceLocked.setText(!isMasked ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_locked()) : maskText(String.valueOf(balanceBean.getUser().getWallet().getBalance_locked())));
//        }
//        Drawable newIcon = isMasked ? ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_look_f) : ContextCompat.getDrawable(getBaseContext(), R.mipmap.ic_look_t);
//        newIcon.setBounds(0, 0, newIcon.getIntrinsicWidth(), newIcon.getIntrinsicHeight());
//        view.tvZhanghuyuer.setCompoundDrawables(null, null, newIcon, null);

        view.tvAmount.setText(balanceBean != null ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_available()) : formatCurrency1("0"));
        view.tvBalanceAvailable.setText(balanceBean != null ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_available()) : formatCurrency1("0"));
        view.tvBond.setText(balanceBean != null ? formatCurrency1(balanceBean.getUser().getWallet().getBond()) : formatCurrency1("0"));
        view.tvBalanceLocked.setText(balanceBean != null ? formatCurrency1(balanceBean.getUser().getWallet().getBalance_locked()) : formatCurrency1("0"));
    }

    /**
     * 提现
     */
    private void withdrawalDialog(){
        withdrawalDialog = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new WithdrawalDialog(this, balanceBean.getUser().getWallet().getBalance_available()+"", new WithdrawalDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(Double current) {
                        showLoadingDialog();
                        vm.userWithdrawal(current);
                    }
                })).show();
    }

    /**
     * 公共弹窗
     */
    private void commonDialog(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new CommonDialog(this, "去认证", "您的账户尚未完成包赔认证，暂时无法进行提现操作哦。请尽快前往[认证中心]完成认证，以便顺利提现!", new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (isLogin(LoginThbActivity.class)) {
                            startActivity(new Intent(getBaseContext(), AuthenticationCenterActivity.class));
                        }
                    }
                })).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog();
        vm.getBalance();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (msg.contains("未通过包赔认证")) {
            if (withdrawalDialog != null) {
                withdrawalDialog.dismiss();
            }
            commonDialog();
        }else {
            showToast(msg);
        }
    }

    /**
     * 实名认证弹窗
     */
    private void realNameAuthenticationDialog(){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new RealNameAuthenticationDialog(this, new RealNameAuthenticationDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String name, String number) {
                        showLoadingDialog();
                        vm.realname(name, number);
                    }
                })).show();
    }
}
