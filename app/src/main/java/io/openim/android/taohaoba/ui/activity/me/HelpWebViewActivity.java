package io.openim.android.taohaoba.ui.activity.me;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.databinding.ActivityWebviewBinding;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 帮助中心详情
 */
public class HelpWebViewActivity extends BaseActivity<WebViewVM, ActivityWebviewBinding> implements WalletVM.ViewAction {

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
        String id = getIntent().getStringExtra("id");

        if (TextUtils.isEmpty(id)) {
            vm.getArticlesDetailsBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
                view.toolbar.setTitleText(it.getArticles().getTitle());
                String htmlContent = "<html>" +
                        "<head><style>img { max-width: 100%; height: auto; }</style></head>" + // 新增CSS样式
                        "<body style='background-color: #201D19; color: white;'>" +
                        it.getArticles().getContent() +
                        "</body></html>";
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

            vm.getArticlesDetails(category_id);
        } else {

            vm.getImgBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
//                view.toolbar.setTitleText(it.getInfo().getArticle_content());
                String htmlContent = "<html>" +
                        "<head><style>img { max-width: 100%; height: auto; }</style></head>" + // 新增CSS样式
                        "<body style='background-color: #201D19; color: white;'>" +
                        it.getInfo().getArticle_content() +
                        "</body></html>";
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

            vm.get_tag_info(id);
        }

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