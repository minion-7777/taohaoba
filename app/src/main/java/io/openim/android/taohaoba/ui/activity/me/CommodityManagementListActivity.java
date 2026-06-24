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
import io.openim.android.taohaoba.ui.fragment.CommodityManagementListFragment;
import io.openim.android.taohaoba.vm.me.MyBuyOrderVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;


/**
 *  商品管理列表
 */
public class CommodityManagementListActivity extends BaseActivity<MyBuyOrderVM, ActivityMyBuyOrderListBinding> {

    private List<TabTitleBean> list = new ArrayList<>();

    private List<BaseFragment> fragments = new ArrayList<>();
    private BaseFragment lastFragment;
    private int mCurrentTabIndex;
    private TabTitleAdapter adapter;
    private int pageIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(MyBuyOrderVM.class);
        bindViewDataBinding(ActivityMyBuyOrderListBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
    }

    protected void initView() {
        adjustToolbarForStatusBar(view.toolbar);
        view.toolbar.setTitleText("商品管理");
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //进入后默认展示哪个页面
        pageIndex = getIntent().getIntExtra("pageIndex", 0);

        list.add(new TabTitleBean("全部", 0));
        list.add(new TabTitleBean("审核中", 0));
        list.add(new TabTitleBean("已上架", 0));
        list.add(new TabTitleBean("已下架", 0));
        list.add(new TabTitleBean("审核失败", 0));
        list.add(new TabTitleBean("已售罄", 0));

        view.rcRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 6));
//        view.rcRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), HORIZONTAL, false));
        // 创建适配器
        adapter = new TabTitleAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

        for (int i = 0; i < list.size(); i++) {
            CommodityManagementListFragment fragment = CommodityManagementListFragment.newInstance(i);
            fragment.setPage(i);
            fragments.add(fragment);
        }

        adapter.setCheck(pageIndex);
        switchFragment(fragments.get(pageIndex));

        //rcRecycler点击监听
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter, @NonNull View view, int position) {
                adapter.setCheck(position);
                switchFragment(fragments.get(position));
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


}
