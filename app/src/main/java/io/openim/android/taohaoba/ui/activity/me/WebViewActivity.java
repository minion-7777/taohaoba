package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebViewClient;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityAboutUsBinding;
import io.openim.android.taohaoba.databinding.ActivityWebviewBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 协议加载
 */
public class WebViewActivity extends BaseActivity<WebViewVM, ActivityWebviewBinding> implements WalletVM.ViewAction {

    private AgentWeb mAgentWeb;

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
        int category_id = getIntent().getIntExtra("category_id",0);
        switch (category_id){
            case 1000:
                view.toolbar.setTitleText("服务协议");
                break;
            case 1001:
                view.toolbar.setTitleText("隐私协议");
                break;
            case 1002:
                view.toolbar.setTitleText("卖家出售协议");
                break;
            case 1003:
                view.toolbar.setTitleText("平台虚拟物品交易规则");
                break;
        }
        vm.getAgreementBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            String htmlContent = "<html><body>"+it.getArticles().getContent()+"</body></html>";
            // 步骤 1：初始化 AgentWeb
            mAgentWeb = AgentWeb.with(this)
                    .setAgentWebParent(view.llWebView,
                            new LinearLayout.LayoutParams(-1, -1))
                    .useDefaultIndicator() // 显示进度条
                    .createAgentWeb()
                    .ready().get();
            // 步骤 2：加载 HTML 字符串
            WebView webView = mAgentWeb.getWebCreator().getWebView();
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
        });

        vm.get_agreement(category_id);
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

    }

}