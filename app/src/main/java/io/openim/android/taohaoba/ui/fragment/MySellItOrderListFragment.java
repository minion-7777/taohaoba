package io.openim.android.taohaoba.ui.fragment;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.databinding.FragmentMyBuyOrderListBinding;
import io.openim.android.taohaoba.ui.activity.game.OrderDetailsActivity;
import io.openim.android.taohaoba.ui.activity.game.SubmintAccountInformationActivity;
import io.openim.android.taohaoba.ui.adapter.MySellItOrderListAdapter;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.MyBuyOrderVM;

/**
 * 我卖的
 */
public class MySellItOrderListFragment extends BaseFragment<MyBuyOrderVM> implements MyBuyOrderVM.ViewAction {

    private List<OrderListBean.ListDTO> orderList = new ArrayList<>();
    private MySellItOrderListAdapter adapter;
    private int pageNum = 1;
    private int orderStatus;//0:全部  1：待付款  2：交易中  3：已完成  4：取消/退款
    private int totalCount;
    private ActivityResultLauncher<Intent> resultLauncher;

    public static MySellItOrderListFragment newInstance(int orderStatus) {
        MySellItOrderListFragment fragment = new MySellItOrderListFragment();
        Bundle args = new Bundle();
        args.putInt("orderStatus", orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentMyBuyOrderListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(MyBuyOrderVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
        // 获取传递的参数
        orderStatus = getArguments() != null ? getArguments().getInt("orderStatus") : 0;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyBuyOrderListBinding.inflate(getLayoutInflater());
        initFragment();
        return binding.getRoot();
    }

    private void initFragment() {
        initListener();
        initViewRecycler();

        //获取订单列表
        vm.getOrderListBeanMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
            dismissLoadingDialog();
            binding.refreshIndex.finishLoadMore();
            binding.refreshIndex.finishRefresh();
            if (pageNum == 1){
                orderList.clear();
            }
            totalCount = it.getTotal();
            if (it.getList() != null){
                orderList.addAll(it.getList());
                adapter.notifyDataSetChanged();
            }
            binding.emptyLayout.setVisibility(orderList.size() > 0 ? GONE : VISIBLE);
        });

        //修改订单状态和更改订单价格
        vm.getOrderStatusSetMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
            pageNum = 1;
            getData();
        });

        // 注册结果监听器
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        pageNum = 1;
                        getData();
                    }
                }
        );

        getData();

        vm.getBuySellGoodsSetMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{

        });

        if (orderStatus != 0) {
            vm.buy_sell_goods_set(orderStatus, 2, orderStatus);
        }
    }


    protected void initListener() {
        binding.refreshIndex.setEnableLoadMore(true);
        binding.refreshIndex.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                getData();
            }
        });
        binding.refreshIndex.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                if (PageUtil.calculateTotalPages(totalCount) <= pageNum) {
                    binding.refreshIndex.finishLoadMore();//结束加载
                    return;
                }
                pageNum++;
                getData();
            }
        });
    }

    private void getData() {
        showLoadingDialog();
        vm.getOrderList(orderStatus, pageNum, orderStatus, 2);
    }

    private void initViewRecycler(){
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 创建适配器
        adapter = new MySellItOrderListAdapter(orderList);
        // 设置适配器
        binding.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderListBean.ListDTO bean = orderList.get(position);
                startActivity(new Intent(getContext(), OrderDetailsActivity.class)
                        .putExtra("order_id", bean.getId())
                        .putExtra("orderType", 2));
            }
        });

        adapter.setOnVerificationListener(new MySellItOrderListAdapter.OnVerificationListener() {
            @Override
            public void onCopyText(String str) {
                //复制订单号
                copyText(getContext(), str);
            }

            @Override
            public void onContactCustomerService(String im_group_id, String name) {
                //联系客服
                Bundle param = new Bundle();
                param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                // 如果是 C2C 聊天，chatID 是对方的 UserID，如果是 Group 聊天，chatID 是 GroupID
                param.putString(TUIConstants.TUIChat.CHAT_ID, im_group_id);
                TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
            }

            @Override
            public void onPriceAdjustment(int id, String price, Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf) {
                //改价
                changePriceDialog(id, price, seller_service_ratio, seller_service_price, seller_amount_conf);
            }

            @Override
            public void onSendOutGoods(int orderId, int gameId, int goodsId) {
                 //发货
                Intent intent = new Intent(getContext(), SubmintAccountInformationActivity.class);
                intent.putExtra("orderId", orderId)
                        .putExtra("gameId", gameId)
                        .putExtra("goodsId", goodsId);
                resultLauncher.launch(intent); // 启动目标Activity[6,8](@ref)
            }

        });

    }

    private BasePopupView popupView;

    /**
     * 修改价格弹窗
     */
    private void changePriceDialog(int order_id, String price, Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new InputPriceDialog(getContext(), price, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        vm.setOrderStatus(orderStatus, order_id, 4, Double.valueOf(amount), "");
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
