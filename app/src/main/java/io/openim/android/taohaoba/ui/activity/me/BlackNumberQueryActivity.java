package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.databinding.ActivityBlackNumberQueryBinding;
import io.openim.android.taohaoba.ui.dialog.BlackNumberQueryDialog;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 黑号查询
 */
public class BlackNumberQueryActivity extends BaseActivity<WalletVM, ActivityBlackNumberQueryBinding> implements WalletVM.ViewAction{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityBlackNumberQueryBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    protected void initView() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvSubmit.setOnClickListener(v->{
            initPop();
        });

    }

    protected void initListener() {

    }


    /**
     * 黑号查询弹窗
     */
    private void initPop(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new BlackNumberQueryDialog(this, "", new BlackNumberQueryDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String code) {

                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
