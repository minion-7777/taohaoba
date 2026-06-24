package io.openim.android.taohaoba.ui.activity.me;


import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;

import android.os.Bundle;
import android.util.Log;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.bean.ViewVouchersBean;
import io.openim.android.taohaoba.databinding.ActivityViewVouchersBinding;
import io.openim.android.taohaoba.vm.me.OrderDetailsVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 查看凭证
 */
public class ViewVouchersActivity extends BaseActivity<OrderDetailsVM, ActivityViewVouchersBinding> implements OrderDetailsVM.ViewAction{

    private int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(OrderDetailsVM.class);
        bindViewDataBinding(ActivityViewVouchersBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        orderId = getIntent().getIntExtra("orderId", 0);

    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        vm.getViewVouchersBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            initData(it);
        });

        showLoadingDialog();
        vm.order_reparation_info(orderId);

    }

    private void initData(ViewVouchersBean it) {
        view.tvSsyx.setText(it.getGame().getName());
        view.tvYxzh.setText(it.getGoods().getAccount());
        view.tvSpbh.setText(it.getGoods().getGoods_no());
        view.tvSpjg.setText(formatCurrency(it.getGoods_price()));
        view.tvJywcsj.setText(it.getDeal_time());
        view.tvMjx.setText(it.getUser().getWei_chat());
        view.tvMjlxsj.setText(it.getGoods().getConnect());
        view.tvBpfwxx.setText("在游戏账号："+it.getGoods().getAccount()+"未出现转手给他人(换绑手机同视为转手)的情况下，若发生恶意找回申诉的情况，平台在7天以内未能成功帮助买家追回账号，需向买家支付RMB"+formatCurrency1(it.getGoods_price())+"元赔偿金。");
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
