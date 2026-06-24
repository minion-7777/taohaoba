package io.openim.android.taohaoba.ui.activity.me;


import android.os.Bundle;
import android.text.TextUtils;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityChangePhoneNumberBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;

/**
 * 修改手机号
 */
public class ChangePhoneNumberActivity extends BaseActivity<WalletVM, ActivityChangePhoneNumberBinding> implements WalletVM.ViewAction{

    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityChangePhoneNumberBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        code = getIntent().getStringExtra("code");

    }

    protected void initListener() {

        view.tvSubmit.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etNewPhone.getText().toString().trim())) {
                showToast("请输入新密码");
                return;
            }
            if (TextUtils.isEmpty(view.etConfirmNewPhone.getText().toString().trim())) {
                showToast("请再次确认新密码");
                return;
            }
            if (!view.etNewPhone.getText().toString().trim().equals(view.etConfirmNewPhone.getText().toString().trim())) {
                showToast("密码不一致");
                return;
            }
            vm.set_user_info(
                    3,
                    null,
                    null,
                    null,
                    null,
                    view.etNewPhone.getText().toString().trim(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    code);
        });
    }

    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
