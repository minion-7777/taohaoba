package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.ArticlesCategoryBean;
import io.openim.android.taohaoba.bean.TabEntity;
import io.openim.android.taohaoba.databinding.ActivityHelpCenterBinding;
import io.openim.android.taohaoba.ui.fragment.HelpCenterFragment;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;

/**
 * 帮助中心
 */
public class HelpCenterActivity extends BaseActivity<WebViewVM, ActivityHelpCenterBinding> implements WalletVM.ViewAction {

    private List<BaseFragment> fragments = new ArrayList<>();
    private BaseFragment lastFragment;
    private int mCurrentTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WebViewVM.class);
        bindViewDataBinding(ActivityHelpCenterBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        vm.getArticlesCategoryBeanMutableLiveData().observe(this, it -> {
            if (it.getList() != null && !it.getList().isEmpty()) {
                initTabData(it.getList());
            }
        });
        vm.getArticlesCategory();
    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(() -> {
            finish();
        });
    }

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();


    //集合添加数据,创建fragment,适配器适配
    private void initTabData(List<ArticlesCategoryBean.ListDTO> gameList) {

        for (int i = 0; i < gameList.size(); i++) {
            nameList.add(gameList.get(i).getName());
            HelpCenterFragment fragment = new HelpCenterFragment(gameList.get(i).getId());
            fragment.setPage(i);
            fragments.add(fragment);
        }
        for (String s : nameList) {
            mTabEntities.add(new TabEntity(s));
        }

        view.tbLayout1.setTabData(mTabEntities);

        switchFragment(fragments.get(0));

        view.tbLayout1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // 隐藏所有已添加的Fragment
        for (BaseFragment f : fragments) {
            if (f.isAdded()) transaction.hide(f);
        }
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment, "TAG_" + fragment.getPage());
        }
        transaction.show(fragment).commit();
        lastFragment = fragment;
        mCurrentTabIndex = fragment.getPage();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
