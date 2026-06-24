package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.scaleXYAnimation;
import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.getui.gs.sdk.GsManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIGroupChatActivity;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import io.openim.android.ouiconversation.ui.ChatActivity;
//import io.openim.android.ouiconversation.utils.ImageZoomUtil;
import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsDetailsBean;
import io.openim.android.taohaoba.bean.TabEntity;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityProductDetailsBinding;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.activity.me.MyCollectionActivity;
import io.openim.android.taohaoba.ui.adapter.Fragment1Adapter;
import io.openim.android.taohaoba.ui.dialog.CommonDialog;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.ui.dialog.RealNameAuthenticationDialog;
import io.openim.android.taohaoba.ui.dialog.SettingDialog;
import io.openim.android.taohaoba.ui.fragment.ProductDescriptionFragment;
import io.openim.android.taohaoba.ui.fragment.ProductScreenshotFragment;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.vm.game.ProductDetailsVM;

/**
 * 商品详情
 */
@Route(path = Routes.Main.PRODUCT_DETAILS)
public class ProductDetailsActivity extends BaseActivity<ProductDetailsVM, ActivityProductDetailsBinding> implements ProductDetailsVM.ViewAction{

    private int goodsId;//商品id
    private int gameId;//
    private int patternId;//
    private GoodsDetailsBean detailsBean;
    private BaseQuickAdapter baseQuickAdapter;
    private BasePopupView popupView;
    private int updateType;
    private String mJump2TargetActivity="";
    private BasePopupView settingPopupView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(ProductDetailsVM.class);
        bindViewDataBinding(ActivityProductDetailsBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.rlTitle);

        initView();
    }

    private long lastClickTime = 0;

    private void initView() {

        try {
            GsManager.getInstance().onEvent("onClickProductDetails");
        } catch (Exception e) {
            e.printStackTrace();
        }

        goodsId = getIntent().getIntExtra("goodsId", 0);
        gameId = getIntent().getIntExtra("gameId", 0);
        patternId = getIntent().getIntExtra("patternId", 0);
        userId = mmkv.decodeString(PreferencesKey.userId, "");
        view.ivClose.setOnClickListener(v->{
            finish();
        });

        //商品详情
        vm.getGoodsDetailsBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            detailsBean = it;
            initData(it);
        });

        showLoadingDialog();
        new Handler().postDelayed(() -> {
            vm.getGoodsDetails(goodsId);
        }, 300);

        //是否收藏商品
        vm.getGoodsConcernMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            detailsBean.setIs_concern(!detailsBean.isIs_concern());
            view.ivGoodsCollect.setImageResource(detailsBean.isIs_concern() ? R.mipmap.ic_goods_collect_t : R.mipmap.ic_goods_collect_f);
            detailsBean.setFavorite_count(String.valueOf(detailsBean.isIs_concern() ? (Integer.parseInt(detailsBean.getFavorite_count()) +1) : (Integer.parseInt(detailsBean.getFavorite_count()) - 1)));
            view.tvGiveUp.setText(detailsBean.getFavorite_count());
        });

        //是否实名认证
        vm.getAuthInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            if (it.getUser_auth_info().getType()!=0) {
                //已实名认证
                vm.is_order_info(goodsId);
            }else {
                //未实名认证
                realNameAuthenticationDialog();
            }
        });

        //提交实名认证
        vm.getRealnameBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("已提交实名认证");
        });

        //是否有订单存在
        vm.getIsOrderInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
//            if (it.isIs_order_info()) {
                startActivity(new Intent(this, ConfirmAnOrderActivity.class)
                        .putExtra("order_id", it.isIs_order_info() ? it.getOrder_id() : 0)
                        .putExtra("goods_id", goodsId)
                        .putExtra("is_order_info", it.isIs_order_info()));
//            }else {
//                startActivity(new Intent(this, ConfirmAnOrderActivity.class)
//                        .putExtra("goods_id", goodsId)
//                        .putExtra("is_order_info", it.isIs_order_info()));
//            }
        });

        view.ivGoTop.setOnClickListener(v -> {
            if (System.currentTimeMillis() - lastClickTime > 500) {
                view.slScroll.smoothScrollTo(0, 0);
                lastClickTime = System.currentTimeMillis();
            }
        });

        view.ivFavorites.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class)) {
                //我的收藏
                ActivityManager.finishActivity(ActivityManager.isExist(MyCollectionActivity.class));
                startActivity(new Intent(this, MyCollectionActivity.class));
            }
        });

        view.ivGoodsImg.setOnClickListener(v->{
//            ImageZoomUtil.showZoomView(this, !TextUtils.isEmpty(detailsBean.getImage()) ? convertToArray(detailsBean.getImage())[0] : "");
        });

        //创建群聊
        vm.getCreateChatGroupMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            if (mJump2TargetActivity.equalsIgnoreCase("ConfirmAnOrderActivity")) {
//                startActivity(new Intent(this, ConfirmAnOrderActivity.class)
//                        .putExtra("order_id", it.getOrder_id()));
            } else if (mJump2TargetActivity.equalsIgnoreCase("ChatActivity")) {
                Bundle param = new Bundle();
                param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
                // 如果是 C2C 聊天，chatID 是对方的 UserID，如果是 Group 聊天，chatID 是 GroupID
                param.putString(TUIConstants.TUIChat.CHAT_ID, it.getIm_group_id());
                TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
            } else {
                toast("没有发现目标页面");
            }
        });


        //商品下架或更改价格
        vm.getGoodsUpdateMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            toast("修改成功")  ;
            if (updateType == 3){
                finish();
            }else {
                vm.getGoodsDetails(goodsId);
            }
        });

        //立即购买
        view.tvBuyNow.setOnClickListener(v->{
//            scaleXYAnimation(v);
            if (isLogin(LoginThbActivity.class)) {
                showLoadingDialog();
                mJump2TargetActivity = "ConfirmAnOrderActivity";
                vm.auth_info();
            }
        });

        //聊一聊
        view.llLetsTalk.setOnClickListener(v->{
//            scaleXYAnimation(v);
            if (isLogin(LoginThbActivity.class)) {
                showLoadingDialog();
                mJump2TargetActivity = "ChatActivity";
                vm.create_chat_group(1, goodsId, 1, 2);
            }
        });

        //改价
        view.tvPriceAdjustment.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class)) {
                updateType = 2;
                changePriceDialog(goodsId, detailsBean.getRetail_price(), detailsBean.getPattern().getSeller_service_ratio(), detailsBean.getPattern().getSeller_service_price(), detailsBean.getPattern().getSeller_amount_conf());
            }
        });

        //下架
        view.tvUndercarriage.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class))
                commonDialog(1, "确定下架该商品吗？", goodsId);
        });

        //上架
        view.tvBridged.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class))
                commonDialog(4, "确定上架该商品吗？", goodsId);
        });

        //编辑
        view.tvModify.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class))
                if (detailsBean != null) {
                    startActivity(new Intent(this, PublishProductOneActivity.class)
                            .putExtra("goodsId", goodsId)
                            .putExtra("categoryId", detailsBean.getCategory_id())
                            .putExtra("gameId", detailsBean.getGame_id())
                            .putExtra("patternId", detailsBean.getPattern_id())
                            .putExtra("gameName", detailsBean.getGame_name())
                            .putExtra("operateType", 2));
                }
        });

        //删除
        view.tvDelete.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class))
                commonDialog(3, "确定删除该商品吗？", goodsId);
        });

        //收藏
        view.ivGoodsCollect.setOnClickListener(v->{
            if(isLogin(LoginThbActivity.class)) {
                if (detailsBean != null) {
                    vm.goods_concern(detailsBean.isIs_concern() ? 0 : 1, goodsId);
                }
            }
        });

        //更多
        view.ivGoodsMore.setOnClickListener(v->{
            pop();
        });

        //联系客服
        view.ivModifyKefu.setOnClickListener(v->{
            showLoadingDialog();
            //联系客服
            if (isLogin(LoginThbActivity.class)) {
                Parameter parameter = new Parameter();
                parameter.add("cs_group_id", 2);
                parameter.add("game_id", detailsBean.getGame_id());


                N.APIThb(OpenIMService.class)
                        .assignCustomerService(parameter.buildJsonBody())
                        .compose(N.IOMain())
                        .map(OpenIMService.turnThb(String.class))
                        .subscribe(new NetObserver<String>(getBaseContext()) {
                            @Override
                            public void onSuccess(String it) {
                                dismissLoadingDialog();
                                Intent intent = new Intent(getBaseContext(), TUIC2CChatActivity.class);
                                intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
                                intent.putExtra(TUIConstants.TUIChat.CHAT_ID, it);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            protected void onFailure(Throwable e) {
                                dismissLoadingDialog();
                                toast(e.getMessage());
                            }
                        });
            }
        });

        //加入群聊
        view.tvGoQqGroup.setOnClickListener(v->{
            joinQQGroup("1051872570");
        });

        //复制群号
        view.tvGroupCode.setOnClickListener(v->{
            copyText(view.tvGroupCode.getText().toString());
            toast("复制成功");
        });

    }

    /**
     * 设置数据
     */
    private void initData(GoodsDetailsBean detailsBean) {
        setv(view.tvModify, view.tvPriceAdjustment, view.tvDelete, view.tvUndercarriage, view.tvBridged);

        Glide.with(this)
                .load(!TextUtils.isEmpty(detailsBean.getImage()) ? convertToArray(detailsBean.getImage())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(view.ivGoodsImg);

        view.tvPrice.setText(PriceFormatUtils.formatPrice(this, detailsBean.getRetail_price()));
        view.tvHint.setText(detailsBean.getGoods_no()+" "+detailsBean.getTitle());
        view.tvServiceName.setText(TextUtils.isEmpty(detailsBean.getUser_goods_service().getGame_service_name()) ? (detailsBean.getUser_goods_service().getDevice_name()+","+detailsBean.getUser_goods_service().getOperator_name()) : detailsBean.getUser_goods_service().getGame_service_name());
        view.tvViewCount.setText(TextUtils.isEmpty(detailsBean.getView_count()) ? "0" : String.valueOf(detailsBean.getView_count()));
        view.tvGiveUp.setText(TextUtils.isEmpty(detailsBean.getFavorite_count()) ? "0" : String.valueOf(detailsBean.getFavorite_count()));

        if (!TextUtils.isEmpty(detailsBean.getLabel())) {
            // 使用逗号拆分字符串
            String[] parts = detailsBean.getLabel().split(",");
            // 将拆分后的字符串添加到 List 中
            List<String> list = new ArrayList<>();
            for (String part : parts) {
                list.add(part);
            }
            baseQuickAdapter = new BaseQuickAdapter(R.layout.item_tag, list) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    baseViewHolder.setText(R.id.tv_tag1, o.toString());
                }
            };
            view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            view.rcRecycler.setAdapter(baseQuickAdapter);
        }

        view.tvDescribe.setText(!TextUtils.isEmpty(detailsBean.getText()) ? detailsBean.getText() : "暂无商品描述");
        if (updateType == 0) {
            initTabView();
        }

        view.clModifyDetails.setVisibility(!TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(detailsBean.getUser_id()) ? VISIBLE : GONE);
        view.clGoodsDetails.setVisibility(!TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(detailsBean.getUser_id()) ? GONE : VISIBLE);
        view.ivFavorites.setVisibility(!TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(detailsBean.getUser_id()) ? GONE : VISIBLE);
        view.ivGoodsMore.setVisibility(!TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(detailsBean.getUser_id()) ? GONE : VISIBLE);
        if (!TextUtils.isEmpty(userId) && userId.equalsIgnoreCase(detailsBean.getUser_id())) {
            view.tvPriceAdjustment.setVisibility(GONE);
            view.tvUndercarriage.setVisibility(GONE);
            view.tvBridged.setVisibility(GONE);
            view.tvDelete.setVisibility(GONE);
            view.tvModify.setVisibility(GONE);
            //审查状态 0：审核中 1：已上架 2：已下架  3:已删除 4：审核失败 5:以售罄
            if (detailsBean.getReview_status() == 0) {

            }else if (detailsBean.getReview_status() == 1) {
                view.tvPriceAdjustment.setVisibility(VISIBLE);
                view.tvUndercarriage.setVisibility(VISIBLE);
            }else if (detailsBean.getReview_status() == 2) {
                view.tvBridged.setVisibility(VISIBLE);
                view.tvDelete.setVisibility(VISIBLE);
                if (detailsBean.getPattern().getType() == 1) {
                    view.tvModify.setVisibility(VISIBLE);
                }
            }else if (detailsBean.getReview_status() == 3) {

            }else if (detailsBean.getReview_status() == 4) {
                view.tvBridged.setVisibility(VISIBLE);
                if (detailsBean.getPattern().getType() == 1) {
                    view.tvModify.setVisibility(VISIBLE);
                }
            }else if (detailsBean.getReview_status() == 5) {

            }
        }else {
            view.tvYSQ.setVisibility(detailsBean.getReview_status() != 1 ? VISIBLE : GONE);
            view.tvBuyNow.setVisibility(detailsBean.getReview_status() != 1 ? GONE : VISIBLE);
            view.llLetsTalk.setVisibility(detailsBean.getReview_status() != 1 ? GONE : VISIBLE);
            view.ivGoodsCollect.setVisibility(detailsBean.getReview_status() != 1 ? GONE : VISIBLE);
        }

        view.ivGoodsCollect.setImageResource(detailsBean.isIs_concern() ? R.mipmap.ic_goods_collect_t : R.mipmap.ic_goods_collect_f);

    }

    private static void setv(View... views){
        for (View view : views) {
            view.setVisibility(GONE);
        }
    }

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    //集合添加数据,创建fragment,适配器适配
    private void initTabView() {

        mTabEntities.add(new TabEntity("商品截图"));
        mTabEntities.add(new TabEntity("商品描述"));
        view.tbLayout.setTabData(mTabEntities);

        ProductScreenshotFragment productScreenshotFragment = new ProductScreenshotFragment(detailsBean.getImage());
        ProductDescriptionFragment productDescriptionFragment = new ProductDescriptionFragment(detailsBean);

        fragments.add(productScreenshotFragment);
        fragments.add(productDescriptionFragment);

        switchFragment(fragments.get(0));

        view.tbLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private List<BaseFragment> fragments = new ArrayList<>();

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

    /**
     * 公共弹窗
     * @param type 1下架 4上架 3删除
     */
    private void commonDialog(int type, String hint, int goodsId){
        updateType = type;
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new CommonDialog(this, hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            vm.goods_update(goodsId, 1, null);
                        }else if (type == 4) {
                            vm.goods_update(goodsId, 4, null);
                        }else if (type == 3) {
                            vm.goods_update(goodsId, 3, null);
                        }
                    }
                })).show();
    }

    /**
     * 修改价格弹窗
     */
    private void changePriceDialog(int goodsId, String price, Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new InputPriceDialog(this, price, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        showLoadingDialog();
                        vm.goods_update(goodsId, 2, Double.valueOf(amount));
                    }
                })).show();
    }

    /**
     * 实名认证弹窗
     */
    private void realNameAuthenticationDialog(){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new RealNameAuthenticationDialog(this, new RealNameAuthenticationDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String name, String number) {
                        showLoadingDialog();
                        vm.realname(name, number);
                    }
                })).show();
    }

    /**
     * 更多
     */
    private void pop(){
        // 使用自定义的弹窗
        settingPopupView = new XPopup.Builder(this).hasShadowBg(false)
                .atView(view.ivGoodsMore)
                .navigationBarColor(Color.BLACK) // 设置导航栏背景色为黑色
                .isLightNavigationBar(true) // 导航栏图标设为白色（适配黑色背景）
                .asCustom(new SettingDialog(this, BaseApp.inst().totalUnreadMsgCount, new SettingDialog.OnFilterChangeListener() {
                    @Override
                    public void onItem1Click() {
                        ActivityManager.finishAllExceptActivity(MainActivity.class);
                    }

                    @Override
                    public void onItem2Click() {
                        if (isLogin(LoginThbActivity.class)) {
                            EventBus.getDefault().post(new MessageEvent(Constants.GOMSG));
                            ActivityManager.finishAllExceptActivity(MainActivity.class);
                        }
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    /**
     * 唤醒QQ加入群聊
     * @param groupUin QQ群号（纯数字，如"123456789"）
     */
    public void joinQQGroup(String groupUin) {
        // QQ加群URL Scheme（固定格式）
        String qqGroupUrl = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin="
                + groupUin + "&card_type=group&source=qrcode";

        try {
            // 创建Intent
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(qqGroupUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 非Activity环境需加此Flag

            // 检查是否安装QQ
            if (BaseApp.inst().getPackageManager().queryIntentActivities(intent,
                    PackageManager.MATCH_DEFAULT_ONLY).size() > 0) {
                BaseApp.inst().startActivity(intent); // 唤醒QQ
            } else {
                toast("请先安装QQ应用"); // 未安装QQ时提示
            }
        } catch (Exception e) {
            toast("唤醒QQ失败，请重试");
            e.printStackTrace();
        }
    }

}
