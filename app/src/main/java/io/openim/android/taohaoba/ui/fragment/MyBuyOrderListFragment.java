package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import io.openim.android.taohaoba.ui.activity.game.ConfirmAnOrderActivity;
import io.openim.android.taohaoba.ui.activity.game.OrderDetailsActivity;
import io.openim.android.taohaoba.ui.activity.me.AfterSalesActivity;
import io.openim.android.taohaoba.ui.activity.me.ViewVouchersActivity;
import io.openim.android.taohaoba.ui.adapter.MyBuyOrderListAdapter;
import io.openim.android.taohaoba.ui.dialog.AfterSalesDialog;
import io.openim.android.taohaoba.ui.dialog.CancellationOfOrderDialog;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.MyBuyOrderVM;

/**
 * 我买的
 */
public class MyBuyOrderListFragment extends BaseFragment<MyBuyOrderVM> implements MyBuyOrderVM.ViewAction {

    private List<OrderListBean.ListDTO> orderList = new ArrayList<>();
    private MyBuyOrderListAdapter adapter;
    private int pageNum = 1;
    private int orderStatus;//0:全部  1：待付款  2：交易中  3：已完成  4：取消/退款
    private int totalCount;
    private BasePopupView popupView;

    public static MyBuyOrderListFragment newInstance(int orderStatus) {
        MyBuyOrderListFragment fragment = new MyBuyOrderListFragment();
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

        //订单列表
        vm.getOrderListBeanMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
//            dismissLoadingDialog();
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

        getData();

        vm.getBuySellGoodsSetMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{

        });

        if (orderStatus != 0) {
            vm.buy_sell_goods_set(orderStatus, 1, orderStatus);
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
//        showLoadingDialog();
        vm.getOrderList(orderStatus, pageNum, orderStatus, 1);
    }

    private void initViewRecycler(){
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 创建适配器
        adapter = new MyBuyOrderListAdapter(orderList);
        // 设置适配器
        binding.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderListBean.ListDTO bean = orderList.get(position);
                startActivity(new Intent(getContext(), OrderDetailsActivity.class)
                        .putExtra("order_id", bean.getId())
                        .putExtra("orderType", 1));
            }
        });

        adapter.setOnVerificationListener(new MyBuyOrderListAdapter.OnVerificationListener() {
            @Override
            public void onAfterSales(int order_id, String img, String title, String gameServiceName, String price) {
                //售后
                afterSalesDialog(order_id, img, title, gameServiceName, price);
            }

            @Override
            public void onViewVouchers(int id) {
                //查看凭证
                startActivity(new Intent(getContext(), ViewVouchersActivity.class)
                        .putExtra("orderId", id));
            }

            @Override
            public void onImmediatePayment(int id) {
                //立即支付
                startActivity(new Intent(getContext(), ConfirmAnOrderActivity.class)
                        .putExtra("order_id", id)
                        .putExtra("is_order_info", true));
            }

            @Override
            public void onCopyText(String str) {
                //复制订单号
                copyText(getContext(), str);
            }

            @Override
            public void onCancellationOfOrder(int id) {
                //取消订单
                cancellationOfOrderDialog(id);
            }

            @Override
            public void onContactCustomerService(String id, String name) {
                //联系客服
                Bundle param = new Bundle();
                param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                // 如果是 C2C 聊天，chatID 是对方的 UserID，如果是 Group 聊天，chatID 是 GroupID
                param.putString(TUIConstants.TUIChat.CHAT_ID, id);
                TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
            }

            @Override
            public void onConfirmVerificationNumber(int id) {
                //确认验号
                commonDialog(1, "确认验号吗？", id);
            }

            @Override
            public void onConfirmReceipt(int id) {
                //确认收货
                commonDialog(2, "确认换绑吗？", id);
            }
        });

    }

    /**
     * 申请售后
     */
    private void afterSalesDialog(int order_id, String img, String title, String gameServiceName, String price){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new AfterSalesDialog(getContext(), new AfterSalesDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String str) {
                        startActivity(new Intent(getContext(), AfterSalesActivity.class)
                                .putExtra("orderId",order_id)
                                .putExtra("reason", str)
                                .putExtra("img", img)
                                .putExtra("title", title)
                                .putExtra("gameServiceName", gameServiceName)
                                .putExtra("price", price));
                    }
                })).show();
    }

    /**
     * 公共弹窗
     * @param type 1确认验号 2确认收货 3取消订单 4修改价格
     */
    private void commonDialog(int type, String hint, int order_id){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CommonDialog(getContext(), hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            vm.setOrderStatus(orderStatus, order_id, 1, null, "");
                        }else if (type == 2) {
                            vm.setOrderStatus(orderStatus, order_id, 2, null, "");
                        }
                    }
                })).show();
    }

    /**
     * 取消订单弹窗
     */
    private void cancellationOfOrderDialog(int order_id){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CancellationOfOrderDialog(getContext(), new CancellationOfOrderDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String refund_content) {
                        vm.setOrderStatus(orderStatus, order_id, 3, null, refund_content);
                    }
                })).show();
    }


    @Override
    public void err(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
