package io.openim.android.taohaoba.ui.activity.me;

import android.content.Intent;
import android.os.Bundle;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.databinding.ActivityAboutUsBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 修改密码
 */
public class AboutUsActivity extends BaseActivity<WalletVM, ActivityAboutUsBinding> implements WalletVM.ViewAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityAboutUsBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        view.tvVersion.setText((BuildConfig.DEBUG ? "测试" : "") + "版本号v" + BuildConfig.VERSION_NAME);
    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvPrivacyPolicy.setOnClickListener(v->{
            startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1001));
        });

        view.tvServiceAgreement.setOnClickListener(v->{
            startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1000));
        });
    }


    @Override
    public void err(String msg) {

    }

}
