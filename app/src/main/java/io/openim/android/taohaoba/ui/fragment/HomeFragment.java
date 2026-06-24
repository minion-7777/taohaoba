package io.openim.android.taohaoba.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.CategoryBean;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.bean.RecommendListBean;
import io.openim.android.taohaoba.bean.TabEntity;
import io.openim.android.taohaoba.databinding.FragmentHomeBinding;
import io.openim.android.taohaoba.ui.activity.game.AccountNumberListActivity;
import io.openim.android.taohaoba.ui.activity.home.CustomerServiceCenterActivity;
import io.openim.android.taohaoba.ui.activity.home.SearchActivity;
import io.openim.android.taohaoba.ui.activity.me.ActivityCenterActivity;
import io.openim.android.taohaoba.ui.activity.me.ActivityCenterWebViewActivity;
import io.openim.android.taohaoba.ui.activity.me.HelpWebViewActivity;
import io.openim.android.taohaoba.ui.adapter.HomeGameListAdapter;
import io.openim.android.taohaoba.ui.adapter.MyBannerAdapter;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.vm.home.HomeVM;

public class HomeFragment extends BaseFragment<HomeVM> implements HomeVM.ViewAction {

    private HomeGameListAdapter adapter;
    private List<RecommendListBean.GameDTO> gameList = new ArrayList<>();
    private CategoryBean categoryBean;

    private FragmentHomeBinding binding;
    private List<BaseFragment> fragments = new ArrayList<>();
    private BaseFragment lastFragment;
    private int mCurrentTabIndex;
    private BannerImgBean bannerImgBean;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(HomeVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        initFragment();
        return binding.getRoot();
    }

    private void initFragment() {
        adjustToolbarForStatusBar(binding.rlTitle);
        initViewRecycler();
        initData();
        initSubscribe();

        binding.llCustomerService.setOnClickListener(v->{
//            startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 1));
            if (isLogin(LoginThbActivity.class))
                vm.assignCustomerService();
        });

        binding.flSearch.setOnClickListener(v->{
            startActivity(new Intent(getContext(), SearchActivity.class));
        });

        binding.llChooseAndBuy.setOnClickListener(v->{
            startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 2));
        });

        binding.llValuation.setOnClickListener(v->{
            startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 4));
        });

        binding.llIntermediaryGuarantee.setOnClickListener(v->{
            startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 7));
        });

        //账号回收
        binding.llRecycle.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class)) {
                vm.getCategory();
            }
        });

        binding.refreshIndex.setEnableLoadMore(true);
        binding.refreshIndex.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (gameList.size() == 0) {
                    vm.getRecommendList();
                }
                if (bannerImgBean == null) {
                    vm.get_banner_img();
                }
                EventBus.getDefault().post(new MessageEvent(Constants.REFRESH));
                new Handler().postDelayed(() -> {
                    binding.refreshIndex.finishRefresh();
                }, 1000);
            }
        });
        binding.refreshIndex.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                EventBus.getDefault().post(new MessageEvent(Constants.LOADING1));
                new Handler().postDelayed(() -> {
                    binding.refreshIndex.finishLoadMore();
                }, 1000);
            }
        });

    }

    protected void initSubscribe() {
        //首页游戏推荐列表
        vm.getRecommendListLiveData().observe(getViewLifecycleOwner(), it->{
            gameList.addAll(getFirstSevenItems(it.getGame()));
            RecommendListBean.GameDTO bean = new RecommendListBean.GameDTO();
            bean.setId(0);
            bean.setName("更多");
            gameList.add(bean);
            adapter.notifyDataSetChanged();
            initTabData(it.getGame());
        });

        //轮播图
        vm.getBannerImgLiveData().observe(getViewLifecycleOwner(), it->{
            bannerImgBean = it;
            initViewBanner(it.getList());
        });

        //获取商品类型
        vm.getCategoryBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            categoryBean = it;
            vm.getPattern();
        });

        //获取交易模式
        vm.getPatternBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            for (PatternBean.PatternDTO patternDTO : it.getPattern()) {
                if (patternDTO.getType_zh().equalsIgnoreCase("快速卖号")){
                    startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                            .putExtra("pageIndex", 3)
                            .putExtra("title", patternDTO.getName())
                            .putExtra("patternId", patternDTO.getId())
                            .putExtra("categoryId", categoryBean.getCategory().get(0).getId()));
                }
            }
        });

        //分配客服
        vm.getAssignCustomerServiceMutableLiveData().observe(getViewLifecycleOwner(), it->{
            Intent intent = new Intent(getContext(), TUIC2CChatActivity.class);
            intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
            intent.putExtra(TUIConstants.TUIChat.CHAT_ID, it);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    protected void initData() {
        vm.getRecommendList();
        vm.get_banner_img();
    }

    public static List<RecommendListBean.GameDTO> getFirstSevenItems(List<RecommendListBean.GameDTO> list) {
        // 判断 List 的大小，如果大于 7 条数据，截取前 7 条数据
        if (list.size() > 7) {
            return new ArrayList<>(list.subList(0, 7));  // subList(0, 7) 获取从索引 0 到索引 6 的子列表
        }
        return new ArrayList<>(list);  // 如果小于等于 7 条数据，返回原列表的副本
    }

    private void initViewBanner(List<BannerImgBean.ListDTO> images){
        binding.banner.setAdapter(new MyBannerAdapter(images))
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))
                .setOnBannerListener(
                        new OnBannerListener() {
                            @Override
                            public void OnBannerClick(Object data, int position) {
                                //跳转类型:8无跳转 1页面 2H5链接 3小程序页面
                                if (images.get(position).getJump_type() == 1) {
                                    //跳转详情页
                                    if (isLogin(LoginThbActivity.class))
                                        startActivity(new Intent(getContext(), ActivityCenterActivity.class));
                                } else if (images.get(position).getJump_type() == 2) {
                                    //跳转H5页
                                    if (isLogin(LoginThbActivity.class))
                                        startActivity(new Intent(getContext(), ActivityCenterWebViewActivity.class)
                                                .putExtra("pageType", 1)
                                                .putExtra("activity_name", images.get(position).getActivity_name())
                                                .putExtra("activity_jump_url", images.get(position).getTarget_url()));
                                } else if (images.get(position).getJump_type() == 4) {
                                    //跳转H5页
                                    if (isLogin(LoginThbActivity.class))
                                        startActivity(new Intent(getContext(), HelpWebViewActivity.class)
                                                .putExtra("id", images.get(position).getId()));
                                }
                            }
                        }
                );
    }

    private void initViewRecycler(){

        binding.rcRecycler.setNestedScrollingEnabled(false);
        binding.rcRecycler.setLayoutManager(new GridLayoutManager(getContext(),4, RecyclerView.VERTICAL,false));
        // 创建适配器
        adapter = new HomeGameListAdapter(gameList);
        // 设置适配器
        binding.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (gameList.get(position).getId() == 0)
                    startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class).putExtra("pageIndex", 2));
                else
                    startActivity(new Intent(getContext(), AccountNumberListActivity.class)
                            .putExtra("gameId", gameList.get(position).getId())
                            .putExtra("gameTypeId", gameList.get(position).getGame_type_id())
                            .putExtra("gameName", gameList.get(position).getName()));
            }
        });

    }

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();


    //集合添加数据,创建fragment,适配器适配
    private void initTabData(List<RecommendListBean.GameDTO> gameList) {

        for (int i = 0; i < gameList.size(); i++) {
            nameList.add(gameList.get(i).getName());
            AccountNumberFragment fragment = new AccountNumberFragment(i, gameList.get(i).getId());
            fragment.setPage(i);
            fragments.add(fragment);
        }
        for (String s : nameList) {
            mTabEntities.add(new TabEntity(s));
        }

        binding.tbLayout1.setTabData(mTabEntities);

        switchFragment(fragments.get(0));

        binding.tbLayout1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(fragments.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
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

    @Override
    public void err(String msg) {
        if (!TextUtils.isEmpty(msg) && msg.contains("网络")) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
