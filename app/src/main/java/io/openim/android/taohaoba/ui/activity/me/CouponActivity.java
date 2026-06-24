package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.TabTitleBean;
import io.openim.android.taohaoba.databinding.ActivityMyBuyOrderListBinding;
import io.openim.android.taohaoba.ui.adapter.TabTitleAdapter;
import io.openim.android.taohaoba.ui.fragment.CouponFragment;
import io.openim.android.taohaoba.vm.me.CouponVM;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 优惠券页
 */
public class CouponActivity extends BaseActivity<CouponVM, ActivityMyBuyOrderListBinding> implements WalletVM.ViewAction {

    private List<TabTitleBean> list = new ArrayList<>();

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(CouponVM.class);
        bindViewDataBinding(ActivityMyBuyOrderListBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        view.toolbar.setTitleText("优惠券");
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        list.add(new TabTitleBean("未使用", 0));
        list.add(new TabTitleBean("使用记录", 1));
        list.add(new TabTitleBean("已过期", 2));

        view.rcRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        // 创建适配器
        TabTitleAdapter adapter = new TabTitleAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

        for (int i = 0; i < list.size(); i++) {
            CouponFragment fragment = new CouponFragment(i);
            fragment.setPage(i);
            fragments.add(fragment);
        }
        adapter.setCheck(0);
        switchFragment(fragments.get(0));

        //rcRecycler点击监听
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                adapter.setCheck(position);
                switchFragment(fragments.get(position));
            }
        });
    }

    protected void initListener() {

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
    }

    @Override
    public void err(String msg) {

    }

}

