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
import io.openim.android.taohaoba.databinding.ActivityMyAfterSalesBinding;
import io.openim.android.taohaoba.ui.adapter.TabTitleAdapter;
import io.openim.android.taohaoba.ui.fragment.MyAfterSalesFragment;
import io.openim.android.taohaoba.vm.me.AfterSalesVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

public class MyAfterSalesActivity extends BaseActivity<AfterSalesVM, ActivityMyAfterSalesBinding> implements AfterSalesVM.ViewAction{


    private List<TabTitleBean> list = new ArrayList<>();

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AfterSalesVM.class);
        bindViewDataBinding(ActivityMyAfterSalesBinding.inflate(getLayoutInflater()));

        initView();
        initListener();
    }

    protected void initView() {
        adjustToolbarForStatusBar(view.toolbar);

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //进入后默认展示哪个页面
        int pageIndex = getIntent().getIntExtra("pageIndex", 0);

        list.add(new TabTitleBean("售后申请", 0));
        list.add(new TabTitleBean("待处理", 0));
        list.add(new TabTitleBean("处理中", 0));
        list.add(new TabTitleBean("已完成", 0));
        list.add(new TabTitleBean("已关闭", 0));

        view.rcRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 5));
        // 创建适配器
        TabTitleAdapter adapter = new TabTitleAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

        for (int i = 0; i < list.size(); i++) {
            MyAfterSalesFragment fragment = MyAfterSalesFragment.newInstance(i);
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
