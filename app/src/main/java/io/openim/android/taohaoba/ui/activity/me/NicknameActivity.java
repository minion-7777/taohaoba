package io.openim.android.taohaoba.ui.activity.me;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityNicknameBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 修改昵称
 */
public class NicknameActivity extends BaseActivity<WalletVM, ActivityNicknameBinding> implements WalletVM.ViewAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityNicknameBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        vm.getSetUserInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("修改成功");
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

        view.etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                view.tvHint.setText("限制"+s.length()+"/8个字符");
            }
        });

        view.tvSend.setOnClickListener(v->{
            view.etNickname.setText("");
        });

        view.tvSubmit.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etNickname.getText().toString().trim())) {
                shakeAnimation(view.etNickname);
                showToast("请输入昵称");
                return;
            }
            showLoadingDialog();
            vm.set_user_info(
                    2,
                    null,
                    view.etNickname.getText().toString().trim(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
        });
    }


    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
