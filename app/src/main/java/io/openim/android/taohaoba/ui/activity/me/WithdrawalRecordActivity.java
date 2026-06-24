package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.bean.UserWithdrawalInfoBean;
import io.openim.android.taohaoba.databinding.ActivityWithdrawalRecordBinding;
import io.openim.android.taohaoba.ui.adapter.WithdrawalRecordAdapter;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 提现记录
 */
public class WithdrawalRecordActivity extends BaseActivity<WalletVM, ActivityWithdrawalRecordBinding> implements WalletVM.ViewAction{

    private List<UserWithdrawalInfoBean.ListDTO> list = new ArrayList<>();
    private WithdrawalRecordAdapter withdrawalRecordAdapter;
    private int totalCount;
    private int pageNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityWithdrawalRecordBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        initViewRecycler();

        vm.getUserWithdrawalInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            view.tvAmount.setText(String.valueOf(it.getSum() != null ? it.getSum() : 0));
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1) {
                list.clear();
            }
            totalCount = it.getTotal();
            list.addAll(it.getList());
            withdrawalRecordAdapter.notifyDataSetChanged();
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        getData();
    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.refreshIndex.setEnableLoadMore(true);
        view.refreshIndex.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getData();
            }
        });
        view.refreshIndex.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (PageUtil.calculateTotalPages(totalCount) <= pageNum) {
                    view.refreshIndex.finishLoadMore();//结束加载
                    return;
                }
                pageNum++;
                getData();
            }
        });
    }

    private void getData(){
        showLoadingDialog();
        vm.getUser_withdrawal_info(pageNum);
    }


    private void initViewRecycler(){
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 创建适配器
        withdrawalRecordAdapter = new WithdrawalRecordAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(withdrawalRecordAdapter);

    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
