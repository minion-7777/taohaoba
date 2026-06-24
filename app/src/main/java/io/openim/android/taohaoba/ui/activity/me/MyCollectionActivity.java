package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsConcernListBean;
import io.openim.android.taohaoba.databinding.ActivityMyCollectionBinding;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.activity.game.ProductDetailsActivity;
import io.openim.android.taohaoba.ui.adapter.MyCollectionAdapter;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 我的收藏
 */
public class MyCollectionActivity extends BaseActivity<WalletVM, ActivityMyCollectionBinding> implements WalletVM.ViewAction{

    private List<GoodsConcernListBean.ListDTO> list = new ArrayList<>();
    private int pageNum = 1;
    private MyCollectionAdapter adapter;
    private int mAdapterPosition;
    private int totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityMyCollectionBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        initViewRecycler();
        vm.getGoodsConcernListBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1) {
                list.clear();
            }
            totalCount = it.getTotal();
            if (it.getList() != null) {
                list.addAll(it.getList());
                adapter.notifyDataSetChanged();
            }
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        //取消收藏
        vm.getGoodsConcernMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            list.remove(mAdapterPosition);
            adapter.notifyDataSetChanged();
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

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
        vm.goods_concern_list(pageNum);
    }


    private void initViewRecycler(){

        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //添加侧滑菜单
        view.rcRecycler.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int position) {
                // 创建菜单项
                SwipeMenuItem deleteItem = new SwipeMenuItem(getBaseContext())
                        .setBackground(R.color.transparent)
                        .setImage(R.mipmap.ic_coll_customer_service)
                        .setWidth(170)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                rightMenu.addMenuItem(deleteItem); // 在Item左侧添加一个菜单

                SwipeMenuItem deleteItem1 = new SwipeMenuItem(getBaseContext())
                        .setBackground(R.color.transparent)
                        .setImage(R.mipmap.ic_coll_delete)
                        .setWidth(170)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                rightMenu.addMenuItem(deleteItem1); // 在Item左侧添加一个菜单
            }
        });
        //侧滑菜单点击事件
        view.rcRecycler.setOnItemMenuClickListener(new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
                menuBridge.closeMenu();
                mAdapterPosition = adapterPosition;
                // 菜单在Item中的Position： 0联系客服 1删除
                int menuPosition = menuBridge.getPosition();
                if (menuPosition == 0) {
                    //联系客服
                    if (isLogin(LoginThbActivity.class)) {
                        showLoadingDialog();
                        Parameter parameter = new Parameter();
                        parameter.add("cs_group_id", 2);
                        parameter.add("game_id", list.get(adapterPosition).getGame_id());

                        N.APIThb(OpenIMService.class)
                                .assignCustomerService(parameter.buildJsonBody())
                                .compose(N.IOMain())
                                .map(OpenIMService.turnThb(String.class))
                                .subscribe(new NetObserverThb<String>(getBaseContext()) {
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
                                    protected void onFailure(BaseTHB e) {
                                        dismissLoadingDialog();
                                        toast(e.error);
                                    }
                                });
                    }
                }
                if (menuPosition == 1) {
                    showLoadingDialog();
                    vm.goods_concern(0, list.get(adapterPosition).getId());
                }
            }
        });
        // 创建适配器
        adapter = new MyCollectionAdapter(list);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (list.get(position).getReview_status() == 1) {
                    ActivityManager.finishActivity(ActivityManager.isExist(ProductDetailsActivity.class));
                    startActivity(new Intent(getBaseContext(), ProductDetailsActivity.class)
                            .putExtra("goodsId", list.get(position).getId()));
                }
            }
        });
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
