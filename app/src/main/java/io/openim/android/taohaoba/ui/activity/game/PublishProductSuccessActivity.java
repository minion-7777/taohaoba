package io.openim.android.taohaoba.ui.activity.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.taohaoba.databinding.ActivityPublishProductSuccessBinding;
import io.openim.android.taohaoba.ui.activity.me.CommodityManagementListActivity;
import io.openim.android.taohaoba.ui.activity.me.SettingActivity;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.vm.home.GoodsSettingVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 发布商品成功
 */
public class PublishProductSuccessActivity extends BaseActivity<GoodsSettingVM, ActivityPublishProductSuccessBinding> implements GoodsSettingVM.ViewAction{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(GoodsSettingVM.class);
        bindViewDataBinding(ActivityPublishProductSuccessBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    private void initView() {


    }

    private void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvJxfb.setOnClickListener(v->{
            finish();
        });

        view.tvCkyfb.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class)) {
                launchActivity(new Intent(this, CommodityManagementListActivity.class)
                        .putExtra("pageIndex", 0));
            }
            finish();
        });

        view.tvFhsy.setOnClickListener(v->{
            ActivityManager.finishAllExceptActivity(MainActivity.class);
        });
    }


    @Override
    public void err(String msg) {

    }
}
