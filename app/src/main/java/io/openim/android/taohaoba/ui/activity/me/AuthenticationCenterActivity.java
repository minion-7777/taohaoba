package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.bean.AuthInfoBean;
import io.openim.android.taohaoba.databinding.ActivityAuthenticationCenterBinding;
import io.openim.android.taohaoba.vm.me.AuthenticationCenterVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;


/**
 * 认证中心
 */
public class AuthenticationCenterActivity extends BaseActivity<AuthenticationCenterVM, ActivityAuthenticationCenterBinding> implements AuthenticationCenterVM.ViewAction {

    private AuthInfoBean infoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AuthenticationCenterVM.class);
        bindViewDataBinding(ActivityAuthenticationCenterBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    protected void initView() {

        vm.getAuthInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            infoBean = it;
            initData();
        });

    }

    private void initData() {

        switch (infoBean.getUser_auth_info().getType()){
            case 0:
                view.tvSmrzView.setText("未认证");
                view.tvSmrzTag.setVisibility(INVISIBLE);
                view.tvSrrzView.setText("未认证");
                view.tvSrrzTag.setVisibility(INVISIBLE);
                view.tvBprzView.setText("未认证");
                view.tvBprzTag.setVisibility(INVISIBLE);
                break;
            case 1:
                view.tvSmrzView.setText("已认证");
                view.tvSmrzTag.setVisibility(VISIBLE);
                view.tvWarn.setVisibility(GONE);
                if (infoBean.getUser_auth_info().getStatus() == 3){
                    view.tvSrrzView.setText("审核中");
                    view.tvSrrzTag.setVisibility(INVISIBLE);
                }else if (infoBean.getUser_auth_info().getStatus() == 4){
                    view.tvSrrzView.setText("已认证");
                    view.tvSrrzTag.setVisibility(VISIBLE);
                }else if (infoBean.getUser_auth_info().getStatus() == 5){
                    view.tvSrrzView.setText("认证失败");
                    view.tvSrrzTag.setVisibility(INVISIBLE);
                }
                view.tvBprzView.setText("未认证");
                view.tvBprzTag.setVisibility(INVISIBLE);
                break;
            case 2:
                view.tvSmrzView.setText("已认证");
                view.tvSmrzTag.setVisibility(VISIBLE);
                view.tvWarn.setVisibility(GONE);
                view.tvSrrzView.setText("已认证");
                view.tvSrrzTag.setVisibility(VISIBLE);
                if (infoBean.getUser_auth_info().getStatus() == 6){
                    view.tvBprzView.setText("未认证");
                    view.tvBprzTag.setVisibility(INVISIBLE);
                }else if (infoBean.getUser_auth_info().getStatus() == 7){
                    view.tvBprzView.setText("审核中");
                    view.tvBprzTag.setVisibility(INVISIBLE);
                    view.tvBprzText3.setVisibility(GONE);
                }else if (infoBean.getUser_auth_info().getStatus() == 9){
                    view.tvBprzView.setText("审核失败");
                    view.tvBprzTag.setVisibility(INVISIBLE);
                    view.tvBprzText3.setVisibility(VISIBLE);
                    view.tvBprzText3.setText("失败原因:"+infoBean.getUser_auth_info().getRemark());
                }else if (infoBean.getUser_auth_info().getStatus() == 8){
                    view.tvBprzView.setText("已认证");
                    view.tvBprzTag.setVisibility(VISIBLE);
                }
                break;
            case 3:
                view.tvSmrzView.setText("已认证");
                view.tvSmrzTag.setVisibility(VISIBLE);
                view.tvWarn.setVisibility(GONE);
                view.tvSrrzView.setText("已认证");
                view.tvSrrzTag.setVisibility(VISIBLE);
                view.tvBprzView.setText("已认证");
                view.tvBprzTag.setVisibility(VISIBLE);
                break;
        }
    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });
        view.tvSmrzView.setOnClickListener(v->{
            if (infoBean != null){
                startActivity(new Intent(this, RealNameAuthenticationActivity.class)
                        .putExtra("realname",infoBean.getUser_auth_info().getRealname())
                        .putExtra("number",infoBean.getUser_auth_info().getNumber())
                        .putExtra("type",infoBean.getUser_auth_info().getType() != 0 ? 1 : 2));
            }
        });

        view.tvSrrzView.setOnClickListener(v->{
            if (infoBean != null) {
                startActivity(new Intent(this, RealPersonAuthenticationActivity.class)
                        .putExtra("front_img",infoBean.getUser_auth_info().getFront_img())
                        .putExtra("back_img",infoBean.getUser_auth_info().getBack_img())
                        .putExtra("type",infoBean.getUser_auth_info().getType() >= 2 ? 1 : 2));
            }
        });

        view.tvBprzView.setOnClickListener(v->{
            if (infoBean != null) {
                if ((infoBean.getUser_auth_info().getType() == 2 && infoBean.getUser_auth_info().getStatus() >= 6) || infoBean.getUser_auth_info().getType() == 3) {
                    startActivity(new Intent(this, CompensationCertificationActivity.class)
                            .putExtra("type",infoBean.getUser_auth_info().getStatus())
                            .putExtra("reparation_image",infoBean.getUser_auth_bool().isReparation() == 6 ? "" : infoBean.getUser_auth_info().getReparation_image())
                            .putExtra("emergency_contact",infoBean.getUser_auth_info().getEmergency_contact())
                            .putExtra("emergency_phone",infoBean.getUser_auth_info().getEmergency_phone())
                            .putExtra("address",infoBean.getUser_auth_info().getAddress())
                            .putExtra("contacts",infoBean.getUser_auth_info().getContacts()));
                }else {
                    showToast("请先完成实人认证");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingDialog();
        vm.auth_info();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
