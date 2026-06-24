package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.os.Bundle;
import android.text.TextUtils;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityRealNameAuthenticationBinding;
import io.openim.android.taohaoba.vm.me.AuthenticationCenterVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 实名认证
 */
public class RealNameAuthenticationActivity extends BaseActivity<AuthenticationCenterVM, ActivityRealNameAuthenticationBinding> implements AuthenticationCenterVM.ViewAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AuthenticationCenterVM.class);
        bindViewDataBinding(ActivityRealNameAuthenticationBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        int type = getIntent().getIntExtra("type", 2);
        if (type == 1) {
            String realname = getIntent().getStringExtra("realname");
            String number = getIntent().getStringExtra("number");
            view.etName.setText(realname);
            view.etIdNumber.setText(number);
            view.tvSubmit.setVisibility(GONE);

            view.etName.setFocusable(false);
            view.etName.setFocusableInTouchMode(false);

            view.etIdNumber.setFocusable(false);
            view.etIdNumber.setFocusableInTouchMode(false);
        }

        vm.getRealnameBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
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

            if (TextUtils.isEmpty(view.etName.getText().toString().trim())) {
                shakeAnimation(view.etName);
                showToast("请输入真实姓名");
                return;
            }

            if (TextUtils.isEmpty(view.etIdNumber.getText().toString().trim())) {
                shakeAnimation(view.etIdNumber);
                showToast("请输入身份证号");
                return;
            }

            showLoadingDialog();
            vm.realname(view.etName.getText().toString().trim(), view.etIdNumber.getText().toString().trim());
        });
    }


    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
