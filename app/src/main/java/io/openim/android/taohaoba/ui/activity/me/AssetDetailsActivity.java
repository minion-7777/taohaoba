package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.FilterBean;
import io.openim.android.taohaoba.bean.TransactionBean;
import io.openim.android.taohaoba.bean.TransactionConfBean;
import io.openim.android.taohaoba.databinding.ActivityAssetDetailsBinding;
import io.openim.android.taohaoba.ui.adapter.AssetDetailsAdapter;
import io.openim.android.taohaoba.ui.dialog.FilterDialog;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.WalletVM;

/**
 * 资产明细
 */
public class AssetDetailsActivity extends BaseActivity<WalletVM, ActivityAssetDetailsBinding> implements WalletVM.ViewAction{

    private List<TransactionBean.ListDTO> list = new ArrayList<>();
    private AssetDetailsAdapter adapter;
    private int pageNum = 1;

    private List<TransactionConfBean> filterBeanList = new ArrayList<>();
    private BasePopupView settingPopupView;
    private int type = 0;//0:全部 1：交易出账  2：交易进账 3：余额提现 4：充值  5：退款  6：违约金  7：包赔支出  8：包赔赔款 9：其他
    private int totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityAssetDetailsBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.rlTitle);

        initView();
        initListener();
    }

    protected void initView() {

        initViewRecycler();

        vm.getTransactionConfBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            if (it != null) {
                TransactionConfBean bean = new TransactionConfBean();
                bean.setCheck(true);
                bean.setId(0);
                bean.setName("全部明细");
                filterBeanList.add(bean);
                filterBeanList.addAll(it);
            }
        });

        vm.getTransactionBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1) {
                list.clear();
            }
            totalCount = it.getTotal();
            list.addAll(it.getList());
            adapter.notifyDataSetChanged();
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        vm.transaction_conf();

        getData();
    }

    protected void initListener() {

        view.ivReturn.setOnClickListener(v->{
            finish();
        });

        view.tvType.setOnClickListener(v->{
            pop();
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

    private void getData() {
        showLoadingDialog();
        vm.getTransaction(pageNum, type);
    }

    private void pop(){
        if (settingPopupView == null) {
            // 使用自定义的弹窗
            settingPopupView = new XPopup.Builder(this).hasShadowBg(false)
                    .atView(view.tvType)
                    .navigationBarColor(Color.BLACK) // 设置导航栏背景色为黑色
                    .isLightNavigationBar(true) // 导航栏图标设为白色（适配黑色背景）
                    .asCustom(new FilterDialog(this, filterBeanList, new FilterDialog.OnFilterChangeListener() {
                        @Override
                        public void onItemClick(int id, String name) {
                            pageNum = 1;
                            type = id;
                            getData();
                            view.tvType.setText(name);
                            settingPopupView.dismiss();
                        }
                    }));
        }
        settingPopupView.show();
    }


    private void initViewRecycler(){

        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 创建适配器
        adapter = new AssetDetailsAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
