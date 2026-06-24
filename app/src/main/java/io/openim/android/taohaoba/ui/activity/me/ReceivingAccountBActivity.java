package io.openim.android.taohaoba.ui.activity.me;


import android.os.Bundle;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityReceivingAccountBBinding;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 收款账户-已绑定
 */
public class ReceivingAccountBActivity extends BaseActivity<WalletVM, ActivityReceivingAccountBBinding> implements WalletVM.ViewAction{

    private String alipay;
    private BasePopupView popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityReceivingAccountBBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        alipay = getIntent().getStringExtra("alipay");
        view.tvAccountNumber.setText(alipay);

        vm.getSetUserInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("解绑成功");
            finish();
        });

    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvUnbind.setOnClickListener(v->{
            commonDialog();
        });

    }

    private void commonDialog(){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new CommonDialog(this, "确定要解绑支付宝吗？", new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        showLoadingDialog();
                        vm.set_user_info(
                                8,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                null,
                                alipay,
                                null);
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
