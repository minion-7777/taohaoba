package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityWebviewBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 活动详情
 */
public class ActivityCenterWebViewActivity extends BaseActivity<WebViewVM, ActivityWebviewBinding> implements WalletVM.ViewAction {

    private AgentWeb mAgentWeb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WebViewVM.class);
        bindViewDataBinding(ActivityWebviewBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        //1000为服务协议而1001是隐私协议,1002卖家出售协议,1003平台虚拟物品交易规则
        int pageType = getIntent().getIntExtra("pageType", 0);
        String activity_code = getIntent().getStringExtra("activity_code");
        String activity_name = getIntent().getStringExtra("activity_name");
        String activity_jump_url = getIntent().getStringExtra("activity_jump_url");
        String jwtToken = mmkv.decodeString("token", "");
        Log.i("ActivityCenterWebViewActivity",jwtToken);
        view.toolbar.setTitleText(TextUtils.isEmpty(activity_name) ? "" : activity_name);

        if (pageType == 0) {
            url = activity_jump_url + "?activity_code=" + activity_code+"&token="+jwtToken;
        }else if (pageType == 1) {
            url = activity_jump_url + "&token="+jwtToken;
        }

        // 步骤 1：初始化 AgentWeb
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(view.llWebView,
                        new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator() // 显示进度条
                .createAgentWeb()
                .ready()
                .go(url);
        // 1. 清理当前WebView的所有缓存
        mAgentWeb.clearWebCache();

        Log.i("ActivityCenterWebViewActivity","活动地址---->"+ url);
    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAgentWeb != null) {
            if (!mAgentWeb.back()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

}