package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
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
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.AfterSalesListBean;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.databinding.FragmentMyAfterSalesBinding;
import io.openim.android.taohaoba.databinding.FragmentMyBuyOrderListBinding;
import io.openim.android.taohaoba.ui.activity.game.ConfirmAnOrderActivity;
import io.openim.android.taohaoba.ui.activity.game.OrderDetailsActivity;
import io.openim.android.taohaoba.ui.activity.me.AfterSalesActivity;
import io.openim.android.taohaoba.ui.activity.me.AfterSalesDetailsActivity;
import io.openim.android.taohaoba.ui.activity.me.ViewVouchersActivity;
import io.openim.android.taohaoba.ui.adapter.MyBuyOrderListAdapter;
import io.openim.android.taohaoba.ui.dialog.AfterSalesDialog;
import io.openim.android.taohaoba.ui.dialog.CancellationOfOrderDialog;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.vm.me.AfterSalesVM;
import io.openim.android.taohaoba.vm.me.MyBuyOrderVM;
import io.openim.android.taohaoba.widgets.RoundImageView;

public class MyAfterSalesFragment extends BaseFragment<AfterSalesVM> implements AfterSalesVM.ViewAction{
    private List<OrderListBean.ListDTO> orderList = new ArrayList<>();
    private List<AfterSalesListBean.ListDTO> afterSalesList = new ArrayList<>();
    private int pageNum = 1;
    private int orderStatus;//0:售后申请  1：待处理  2：处理中  3：已完成  4：关闭
    private int totalCount;
    private BasePopupView popupView;
    private BaseQuickAdapter adapter, afteradapter;

    public static MyAfterSalesFragment newInstance(int orderStatus) {
        MyAfterSalesFragment fragment = new MyAfterSalesFragment();
        Bundle args = new Bundle();
        args.putInt("orderStatus", orderStatus);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentMyAfterSalesBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(AfterSalesVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
        // 获取传递的参数
        orderStatus = getArguments() != null ? getArguments().getInt("orderStatus") : 0;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyAfterSalesBinding.inflate(getLayoutInflater());
        initFragment();
        return binding.getRoot();
    }

    private void initFragment() {
        initListener();
        if (orderStatus == 0) {
            initOrderViewRecycler();
        }else {
            initAfterSalesViewRecycler();
        }

        //订单列表
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

        //售后列表
        vm.getAfterSalesListBeanMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
            dismissLoadingDialog();
            binding.refreshIndex.finishLoadMore();
            binding.refreshIndex.finishRefresh();
            if (pageNum == 1){
                afterSalesList.clear();
            }
            totalCount = it.getTotal();
            if (it.getList() != null){
                afterSalesList.addAll(it.getList());
                afteradapter.notifyDataSetChanged();
            }
            binding.emptyLayout.setVisibility(afterSalesList.size() > 0 ? GONE : VISIBLE);
        });

        //取消申请
        vm.getCancelOrderForPostSaleMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
            pageNum = 1;
            getData();
        });

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
        if (orderStatus == 0){
            vm.getOrderList(orderStatus, pageNum, 3, 1);
        }else {
            vm.getAfterSalesList(orderStatus, pageNum, orderStatus);
        }
    }

    private void initOrderViewRecycler(){
        // 创建适配器
        adapter = new BaseQuickAdapter(R.layout.item_my_after_sales, orderList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                OrderListBean.ListDTO it = (OrderListBean.ListDTO) o;

                TextView tv_cancelAfterSales = baseViewHolder.getView(R.id.tv_cancelAfterSales);
                TextView tv_contactCustomerService = baseViewHolder.getView(R.id.tv_contactCustomerService);
                TextView tv_applyForAfterSales = baseViewHolder.getView(R.id.tv_applyForAfterSales);
                TextView tv_tag = baseViewHolder.getView(R.id.tv_tag);
                setv(tv_cancelAfterSales, tv_contactCustomerService, tv_applyForAfterSales);

                baseViewHolder.setText(R.id.tv_order_number, "订单编号："+it.getOrder_no());
                baseViewHolder.setText(R.id.tv_hint, it.getGoods_title());

                RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);
                Glide.with(baseViewHolder.itemView.getContext())
                        .load(!TextUtils.isEmpty(it.getGoods_image()) ? convertToArray(it.getGoods_image())[0] : "")
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_img);

                baseViewHolder.setText(R.id.tv_area_code, it.getGame_service_name());
                baseViewHolder.setText(R.id.tv_goodsPrice, PriceFormatUtils.formatCurrency(it.getGoods_price()));
                baseViewHolder.setText(R.id.tv_price, "实付款"+PriceFormatUtils.formatCurrency(it.getPayment_price()));
                tv_applyForAfterSales.setText("申请售后");

                if (it.getOrder_post_sale() == null) {
                    tv_tag.setText("交易成功");
                    tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_330eb272));
                    tv_tag.setTextColor(getContext().getColor(R.color.color_0EB272));
                    tv_applyForAfterSales.setVisibility(VISIBLE);
                }else {
                    switch (it.getOrder_post_sale().getOrder_post_sale_status()){
                        case 1:
                            tv_tag.setText("待处理");
                            tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_ff4646));
                            tv_tag.setTextColor(getContext().getColor(R.color.color_FF4646));
                            tv_cancelAfterSales.setVisibility(VISIBLE);
                            break;
                        case 2:
                            tv_tag.setText("处理中");
                            tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33eaca92));
                            tv_tag.setTextColor(getContext().getColor(R.color.color_EACA92));
                            tv_contactCustomerService.setVisibility(VISIBLE);
                            break;
                        case 3:
                            tv_tag.setText("已完成");
                            tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_330eb272));
                            tv_tag.setTextColor(getContext().getColor(R.color.color_0EB272));
                            tv_applyForAfterSales.setText("重新申请");
                            tv_applyForAfterSales.setVisibility(VISIBLE);
                            break;
                        case 4:
                            tv_tag.setText("已关闭");
                            tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33cbcbcb));
                            tv_tag.setTextColor(getContext().getColor(R.color.color_CBCBCB));
                            tv_applyForAfterSales.setText("重新申请");
                            tv_applyForAfterSales.setVisibility(VISIBLE);
                            break;
                    }
                }
                tv_applyForAfterSales.setOnClickListener(v->{
                    //申请售后
                    afterSalesDialog(it.getId(), it.getGoods_image(), it.getGoods_title(), it.getGame_service_name(), it.getGoods_price());
                });

                tv_cancelAfterSales.setOnClickListener(v->{
                    //取消申请
                    commonDialog(1, "确认要取消售后申请吗？", it.getOrder_post_sale().getId());
                });

                tv_contactCustomerService.setOnClickListener(v->{
                    //联系客服
                    Bundle param = new Bundle();
                    param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                    // 如果是 C2C 聊天，chatID 是对方的 UserID，如果是 Group 聊天，chatID 是 GroupID
                    param.putString(TUIConstants.TUIChat.CHAT_ID, it.getOrder_post_sale().getIm_group_id());
                    TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
                });

                baseViewHolder.itemView.setOnClickListener(v->{
                    //查看详情
                    if (it.getOrder_post_sale() == null) {
                        startActivity(new Intent(getContext(), OrderDetailsActivity.class)
                                .putExtra("order_id", it.getId())
                                .putExtra("orderType", 1));
                    }else {
                        startActivity(new Intent(getContext(), AfterSalesDetailsActivity.class)
                               .putExtra("post_sale_id", it.getOrder_post_sale().getId()));
                    }
                });
            }
        };
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 设置适配器
        binding.rcRecycler.setAdapter(adapter);

    }

    private void initAfterSalesViewRecycler(){
        // 创建适配器
        afteradapter = new BaseQuickAdapter(R.layout.item_my_after_sales, afterSalesList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                AfterSalesListBean.ListDTO it = (AfterSalesListBean.ListDTO) o;

                TextView tv_cancelAfterSales = baseViewHolder.getView(R.id.tv_cancelAfterSales);
                TextView tv_contactCustomerService = baseViewHolder.getView(R.id.tv_contactCustomerService);
                TextView tv_applyForAfterSales = baseViewHolder.getView(R.id.tv_applyForAfterSales);
                TextView tv_tag = baseViewHolder.getView(R.id.tv_tag);
                LinearLayout ll_operation = baseViewHolder.getView(R.id.ll_operation);
                setv(tv_cancelAfterSales, tv_contactCustomerService, tv_applyForAfterSales, ll_operation);

                baseViewHolder.setText(R.id.tv_order_number, "申请时间："+it.getCreated_time());
                baseViewHolder.setText(R.id.tv_hint, it.getOrder().getGoods_title());
                tv_applyForAfterSales.setText("申请售后");
                ll_operation.setVisibility(VISIBLE);

                switch (it.getOrder_post_sale_status()){
                    case 1:
                        tv_tag.setText("待处理");
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_ff4646));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_FF4646));
                        tv_cancelAfterSales.setVisibility(VISIBLE);
                        break;
                    case 2:
                        tv_tag.setText("处理中");
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33eaca92));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_EACA92));
                        tv_contactCustomerService.setVisibility(VISIBLE);
                        break;
                    case 3:
                        tv_tag.setText("已完成");
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_330eb272));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_0EB272));
                        ll_operation.setVisibility(GONE);
                        break;
                    case 4:
                        tv_tag.setText("已关闭");
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_CBCBCB));
                        ll_operation.setVisibility(GONE);
                        break;
                }

                RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);
                Glide.with(baseViewHolder.itemView.getContext())
                        .load(!TextUtils.isEmpty(it.getOrder().getGoods_image()) ? convertToArray(it.getOrder().getGoods_image())[0] : "")
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_img);

                baseViewHolder.setText(R.id.tv_area_code, it.getOrder().getGame_service_name());
                baseViewHolder.setText(R.id.tv_goodsPrice, PriceFormatUtils.formatCurrency(it.getOrder().getGoods_price()));
                baseViewHolder.setText(R.id.tv_price, "实付款"+PriceFormatUtils.formatCurrency(it.getOrder().getPayment_price()));

                tv_applyForAfterSales.setOnClickListener(v->{
                    //申请售后
                    afterSalesDialog(it.getOrder().getOrder_id(), it.getOrder().getGoods_image(), it.getOrder().getGoods_title(), it.getOrder().getGame_service_name(), it.getOrder().getGoods_price());
                });

                tv_cancelAfterSales.setOnClickListener(v->{
                    //取消申请
                    commonDialog(1, "确认要取消售后申请吗？", it.getId());
                });

                tv_contactCustomerService.setOnClickListener(v->{
                    //联系客服
                    Bundle param = new Bundle();
                    param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                    // 如果是 C2C 聊天，chatID 是对方的 UserID，如果是 Group 聊天，chatID 是 GroupID
                    param.putString(TUIConstants.TUIChat.CHAT_ID, it.getIm_group_id());
                    TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
                });

                baseViewHolder.itemView.setOnClickListener(v->{
                    //查看详情
                    startActivity(new Intent(getContext(), AfterSalesDetailsActivity.class)
                            .putExtra("post_sale_id", it.getId()));
                });
            }
        };
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 设置适配器
        binding.rcRecycler.setAdapter(afteradapter);

    }

    private void setv(View... views){
        for (View view : views) {
            view.setVisibility(GONE);
        }
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
    private void commonDialog(int type, String hint, int post_sale_id){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CommonDialog(getContext(), hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            showLoadingDialog();
                            vm.cancel_order_for_post_sale(orderStatus, post_sale_id);
                        }
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
    }
}
