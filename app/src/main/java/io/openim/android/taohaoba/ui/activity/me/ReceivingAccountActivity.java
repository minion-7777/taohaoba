package io.openim.android.taohaoba.ui.activity.me;


import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.os.Bundle;
import android.text.TextUtils;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityReceivingAccountBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 收款账户-未绑定
 */
public class ReceivingAccountActivity extends BaseActivity<WalletVM, ActivityReceivingAccountBinding> implements WalletVM.ViewAction{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityReceivingAccountBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        String realname = getIntent().getStringExtra("realname");
        view.etName.setText(realname);
        vm.getSetUserInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("提交成功");
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

        view.tvSubmit.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etAli.getText().toString().trim())) {
                shakeAnimation(view.etAli);
                showToast("请输入支付宝账号");
                return;
            }
            if (TextUtils.isEmpty(view.etAli1.getText().toString().trim())) {
                shakeAnimation(view.etAli1);
                showToast("请再次输入支付宝账号");
                return;
            }
            if (TextUtils.isEmpty(view.etName.getText().toString().trim())) {
                shakeAnimation(view.etName);
                showToast("请输入姓名");
                return;
            }
            if (!view.etAli.getText().toString().trim().equals(view.etAli1.getText().toString().trim())) {
                shakeAnimation(view.etAli1);
                showToast("支付宝账号不一致");
                return;
            }
            showLoadingDialog();
            vm.set_user_info(
                    7,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    view.etName.getText().toString().trim(),
                    view.etAli.getText().toString().trim(),
                    null);
        });
    }


    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
