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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsManagementListBean;
import io.openim.android.taohaoba.databinding.FragmentMyBuyOrderListBinding;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.activity.game.ProductDetailsActivity;
import io.openim.android.taohaoba.ui.activity.game.PublishProductOneActivity;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.vm.me.MyBuyOrderVM;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 商品管理
 */
public class CommodityManagementListFragment extends BaseFragment<MyBuyOrderVM> implements MyBuyOrderVM.ViewAction {

    private List<GoodsManagementListBean.GoodsDTO> orderList = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private int pageNum = 1;
    private int orderStatus;//审查状态 0：审核中 1：已上架 2：已下架  3:已删除 4：审核失败  5:已售罄  (没有不传此参数)
    private int totalCount;
    private BasePopupView popupView;


    public static CommodityManagementListFragment newInstance(int orderStatus) {
        CommodityManagementListFragment fragment = new CommodityManagementListFragment();
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
        if (orderStatus == 0){
            orderStatus = -1;
        }else if (orderStatus == 1){
            orderStatus = 0;
        }else if (orderStatus == 2){
            orderStatus = 1;
        }else if (orderStatus == 3){
            orderStatus = 2;
        }else if (orderStatus == 4){
            orderStatus = 4;
        }
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

        //获取商品列表
        vm.getGoodsManagementListBeanMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
//            dismissLoadingDialog();
            binding.refreshIndex.finishLoadMore();
            binding.refreshIndex.finishRefresh();
            if (pageNum == 1){
                orderList.clear();
            }
            totalCount = it.getTotal();
            if (it.getGoods() != null){
                orderList.addAll(it.getGoods());
                adapter.notifyDataSetChanged();
            }
            binding.emptyLayout.setVisibility(orderList.size() > 0 ? GONE : VISIBLE);
        });

        //商品下架或更改价格
        vm.getGoodsUpdateMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{
            toast("修改成功")  ;
            pageNum = 1;
            getData();
        });

        vm.getBuySellGoodsSetMutableLiveData(orderStatus).observe(getViewLifecycleOwner(), it->{

        });

        if (orderStatus != -1 && orderStatus != 5) {
            int type = 0;
            if (orderStatus == 0){
                type = 3;
            }else if (orderStatus == 1){
                type = 1;
            }else if (orderStatus == 2){
                type = 2;
            }else if (orderStatus == 4){
                type = 4;
            }
            vm.buy_sell_goods_set(orderStatus, 3, type);
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
        vm.getGoodsManagementList(orderStatus, pageNum, orderStatus == 3 ? 4 : orderStatus);
    }

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_my_buy_order_list, orderList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                GoodsManagementListBean.GoodsDTO bean = (GoodsManagementListBean.GoodsDTO) o;

                TextView tv_contactCustomerService = baseViewHolder.getView(R.id.tv_contactCustomerService);
                TextView tv_removeFromTheShelf = baseViewHolder.getView(R.id.tv_removeFromTheShelf);
                TextView tv_PriceAdjustment = baseViewHolder.getView(R.id.tv_PriceAdjustment);
                TextView tv_edit = baseViewHolder.getView(R.id.tv_edit);
                TextView tv_shelves = baseViewHolder.getView(R.id.tv_shelves);
                TextView tv_delete = baseViewHolder.getView(R.id.tv_delete);
                TextView tv_remark = baseViewHolder.getView(R.id.tv_remark);
                TextView tv_tag = baseViewHolder.getView(R.id.tv_tag);

                setv(tv_contactCustomerService, tv_removeFromTheShelf, tv_PriceAdjustment, tv_edit, tv_contactCustomerService, tv_shelves, tv_delete, tv_remark);

                baseViewHolder.setText(R.id.tv_order_number, "商品编号："+bean.getGoods_no());
                baseViewHolder.setText(R.id.tv_hint, bean.getTitle());
                baseViewHolder.setText(R.id.tv_game_name, bean.getGame_name());
                baseViewHolder.setText(R.id.tv_area_code, TextUtils.isEmpty(bean.getGame_service_name()) ? ((TextUtils.isEmpty(bean.getDevice_name()) ? "" : bean.getDevice_name()+",")+(TextUtils.isEmpty(bean.getOperator_name()) ? "" : bean.getOperator_name())) : bean.getGame_service_name());
                baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), TextUtils.isEmpty(bean.getRetail_price()) ? "0" : bean.getRetail_price()));

                baseViewHolder.getView(R.id.tv_order_number).setOnClickListener(v->{
                    //复制
                    copyText(getContext(), bean.getGoods_no());
                });

                RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);

                Glide.with(baseViewHolder.itemView.getContext())
                        .load(!TextUtils.isEmpty(bean.getImage()) ? convertToArray(bean.getImage())[0] : "")
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_img);

                //审查状态 0：审核中 1：已上架 2：已下架  3:已删除 4：审核失败 5:以售罄
                switch (bean.getReview_status()) {
                    case 0:
                        tv_tag.setText("审核中");
                        tv_contactCustomerService.setVisibility(VISIBLE);
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_332ea4ff));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_2EA4FF));
                        break;
                    case 1:
                        tv_tag.setText("已上架");
                        tv_removeFromTheShelf.setVisibility(VISIBLE);
                        tv_PriceAdjustment.setVisibility(VISIBLE);
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33eaca92));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_EACA92));
                        break;
                    case 2:
                        tv_tag.setText("已下架");
                        tv_shelves.setVisibility(VISIBLE);
                        tv_PriceAdjustment.setVisibility(VISIBLE);
                        tv_delete.setVisibility(VISIBLE);
                        if (bean.getPattern_type() == 1) {
                            tv_edit.setVisibility(VISIBLE);
                        }
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_CBCBCB));
                        break;
                    case 3:
                        break;
                    case 4:
                        tv_tag.setText("审核失败");
                        tv_contactCustomerService.setVisibility(VISIBLE);
                        tv_delete.setVisibility(VISIBLE);
                        if (bean.getPattern_type() == 1) {
                            tv_edit.setVisibility(VISIBLE);
                        }
                        tv_remark.setVisibility(VISIBLE);
                        tv_remark.setText("失败原因:"+bean.getRemark());
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_ff4646));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_FF4646));
                        break;
                    case 5:
                        tv_tag.setText("已售罄");
                        tv_contactCustomerService.setVisibility(VISIBLE);
                        tv_tag.setBackground(getContext().getDrawable(R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tv_tag.setTextColor(getContext().getColor(R.color.color_CBCBCB));
                        break;
                }

                tv_contactCustomerService.setOnClickListener(v->{
                    //联系客服
                    if (isLogin(LoginThbActivity.class)) {
                        Parameter parameter = new Parameter();
                        parameter.add("cs_group_id", 2);
                        parameter.add("game_id", bean.getGame_id());

                        N.APIThb(OpenIMService.class)
                                .assignCustomerService(parameter.buildJsonBody())
                                .compose(N.IOMain())
                                .map(OpenIMService.turnThb(String.class))
                                .subscribe(new NetObserver<String>(getContext()) {
                                    @Override
                                    public void onSuccess(String it) {
                                        Intent intent = new Intent(getContext(), TUIC2CChatActivity.class);
                                        intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
                                        intent.putExtra(TUIConstants.TUIChat.CHAT_ID, it);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }

                                    @Override
                                    protected void onFailure(Throwable e) {
                                        toast(e.getMessage());
                                    }
                                });
                    }
                });

                tv_removeFromTheShelf.setOnClickListener(v->{
                    //下架
                    commonDialog(1, "确定下架该商品吗？", bean.getId());
                });

                tv_PriceAdjustment.setOnClickListener(v->{
                    //改价
                    changePriceDialog(bean.getId(), bean.getRetail_price(), bean.getSeller_service_ratio(), bean.getSeller_service_price(), bean.getSeller_amount_conf());
                });

                tv_edit.setOnClickListener(v->{
                    //编辑
                    startActivity(new Intent(getContext(), PublishProductOneActivity.class)
                            .putExtra("goodsId", bean.getId())
                            .putExtra("categoryId", bean.getCategory_id())
                            .putExtra("gameId", bean.getGame_id())
                            .putExtra("patternId", bean.getPattern_id())
                            .putExtra("gameName", bean.getGame_name())
                            .putExtra("operateType", 2));
                });

                tv_delete.setOnClickListener(v->{
                    //删除
                    commonDialog(3, "确定删除该商品吗？", bean.getId());
                });

                tv_shelves.setOnClickListener(v->{
                    //上架
                    commonDialog(4, "确定上架该商品吗？", bean.getId());
                });

                baseViewHolder.itemView.setOnClickListener(v->{
                    startActivity(new Intent(getContext(), ProductDetailsActivity.class)
                            .putExtra("goodsId", bean.getId())
                            .putExtra("gameId", bean.getGame_id())
                            .putExtra("patternId", bean.getPattern_id()));
                });
            }
        };
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rcRecycler.setAdapter(adapter);

    }

    private static void setv(View... views){
        for (View view : views) {
            view.setVisibility(GONE);
        }
    }

    /**
     * 公共弹窗
     * @param type 1下架 4上架 3删除
     */
    private void commonDialog(int type, String hint, int goodsId){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CommonDialog(getContext(), hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            vm.goods_update(orderStatus, goodsId, 1, null);
                        }else if (type == 4) {
                            vm.goods_update(orderStatus, goodsId, 4, null);
                        }else if (type == 3) {
                            vm.goods_update(orderStatus, goodsId, 3, null);
                        }
                    }
                })).show();
    }

    /**
     * 修改价格弹窗
     */
    private void changePriceDialog(int goodsId, String price, Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf){
        popupView = new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new InputPriceDialog(getContext(), price, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        vm.goods_update(orderStatus, goodsId, 2, Double.valueOf(amount));
                    }
                })).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        pageNum = 1;
        getData();
    }

    @Override
    public void err(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
