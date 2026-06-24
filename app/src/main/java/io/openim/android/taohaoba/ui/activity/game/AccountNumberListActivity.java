package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.bean.FiltersBean;
import io.openim.android.taohaoba.bean.GameConfigurationListBean;
import io.openim.android.taohaoba.bean.GameFilterBean;
import io.openim.android.taohaoba.bean.GoodsListBean;
import io.openim.android.taohaoba.databinding.ActivityAccountNumberListBinding;
import io.openim.android.taohaoba.ui.adapter.AccountNumberListAdapter;
import io.openim.android.taohaoba.ui.dialog.ComprehensiveSortingDialog;
import io.openim.android.taohaoba.ui.dialog.GameDistrictServerDialog;
import io.openim.android.taohaoba.ui.dialog.GameFilterDialog;
import io.openim.android.taohaoba.ui.dialog.PriceRangeDialog;
import io.openim.android.taohaoba.utils.FilterUtils;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.game.AccountNumberVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 账号列表
 */
public class AccountNumberListActivity extends BaseActivity<AccountNumberVM, ActivityAccountNumberListBinding> implements AccountNumberVM.ViewAction {

    private int gameId;//游戏id
    private int gameTypeId;//游戏类型id

    private Integer sort_type;//默认为0   1 ： 价格降序  2：价格升序  3：时间降序 4：时间升序
    private Integer device_id;//设备id
    private Integer operator_id;//运营商id
    private String game_server_id;//游戏服务区id
    private Float tall_float;//最高价格
    private Float lower_float;//最低价格
    private String title;//搜索内容
    private int pageNum = 1;
    private AccountNumberListAdapter adapter;

    private List<GoodsListBean.GoodsDTO> goodsListBeans = new ArrayList<>();
    private int totalCount;
    private GameConfigurationListBean gameConfigurationListBean;
    private BasePopupView popupView;
    private List<GameFilterBean.ListDTO> gameFilterList;
    private BasePopupView basePopupView;
    private List<FiltersBean> filtersBeans = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AccountNumberVM.class);
        bindViewDataBinding(ActivityAccountNumberListBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();

    }

    private void initView() {
        gameId = getIntent().getIntExtra("gameId", 0);
        gameTypeId = getIntent().getIntExtra("gameTypeId", 0);
        String gameName = getIntent().getStringExtra("gameName");

        view.toolbar.setTitleText(gameName);

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        initViewRecycler();

        vm.getGameConfigurationListBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            gameConfigurationListBean = it;
        });

        vm.getGoodsListBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1){
                goodsListBeans.clear();
            }
            totalCount = it.getCount();
            if (it.getGoods() != null) {
                goodsListBeans.addAll(it.getGoods());
                adapter.notifyDataSetChanged();
            }
            view.emptyLayout.setVisibility(goodsListBeans.size() > 0 ? GONE : VISIBLE);
        });

        vm.getGameFilterBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            if (it != null && it.getList() != null) {
                gameFilterList = it.getList();
            }
        });

        vm.getGameConfigurationList(gameId);
        vm.get_filter_categories(gameId);

        initListener();
        new Handler().postDelayed(() -> {
            getData();
        }, 100);

    }

    protected void initListener() {
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

        view.tvType.setOnClickListener(v->{
            if (gameConfigurationListBean != null)
                onGameTypeDialog();
        });

        view.tvZhpx.setOnClickListener(v->{
                onGameSortDialog();
        });

        view.tvPriceSorting.setOnClickListener(v->{
            onGamePriceRangeDialog();
        });

        view.tvFilter.setOnClickListener(v->{
            onFilterDialog();
        });

        view.tvSearch.setOnClickListener(v->{
            title = view.etSearch.getText().toString();
            pageNum = 1;
            getData();
        });
    }

    /**
     * 筛选
     */
    private void onFilterDialog() {
        if (gameFilterList == null) {
            return;
        }
        if (basePopupView == null){
            basePopupView = new XPopup.Builder(this)
                    .isViewMode(true)
                    .moveUpToKeyboard(false)
                    .asCustom(new GameFilterDialog(this, gameFilterList, it->{

                        filtersBeans.clear();

                        for (GameFilterBean.ListDTO listDTO : it) {
                            if (listDTO.getField_type() == 3 || listDTO.getField_type() == 4) {
                                //单选  多选
                                if (FilterUtils.hasSelectedItems(listDTO.getNameList())){
                                    FiltersBean filtersBean = new FiltersBean();
                                    filtersBean.setId(listDTO.getId().toString());
                                    filtersBean.setValue(FilterUtils.getSelectedNamesAsString(listDTO.getNameList()));
                                    filtersBeans.add(filtersBean);
                                }
                            }else if (listDTO.getField_type() == 6) {
                                //价格区间
                                if (FilterUtils.hasPriceRange(listDTO)){
                                    FiltersBean filtersBean = new FiltersBean();
                                    filtersBean.setId(listDTO.getId().toString());
                                    filtersBean.setValue((TextUtils.isEmpty(listDTO.getMinValue()) ? "" : listDTO.getMinValue()) + "," + (TextUtils.isEmpty(listDTO.getMaxValue()) ? "" : listDTO.getMaxValue()));
                                    filtersBeans.add(filtersBean);
                                }

                            }else if (listDTO.getField_type() == 7) {
                                //分类字段ㅑ
                                for (GameFilterBean.ListDTO child : listDTO.getChildren()) {
                                    if (FilterUtils.hasSelectedItems(child.getNameList())){
                                        FiltersBean filtersBean = new FiltersBean();
                                        filtersBean.setId(listDTO.getId().toString());
                                        filtersBean.setLogic(child.getTag());
                                        filtersBean.setValue(FilterUtils.getSelectedNamesAsString(child.getNameList()));
                                        filtersBeans.add(filtersBean);
                                    }
                                }
                            }
                        }
                        pageNum = 1;
                        getData();
                    }));
        }
        basePopupView.show();
    }

    private void getData(){
        vm.getGoodsList(title, gameId, pageNum, sort_type, device_id, operator_id, game_server_id, tall_float, lower_float, filtersBeans);
    }

    private void initViewRecycler(){
        view.rcRecycler.setNestedScrollingEnabled(false);
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 创建适配器
        adapter = new AccountNumberListAdapter(goodsListBeans);
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getBaseContext(), ProductDetailsActivity.class)
                        .putExtra("goodsId", goodsListBeans.get(position).getId()));
            }
        });

    }

    /**
     * 游戏类型
     */
    private void onGameTypeDialog(){
        if (popupView == null) {
            popupView = new XPopup.Builder(this)
                    .isViewMode(true)
                    .asCustom(new GameDistrictServerDialog(this, 2, gameConfigurationListBean, new GameDistrictServerDialog.OnVerificationListener() {
                        @Override
                        public void onSubmit(int game_typeId, int deviceId, String game_serverId, int operatorId, String game_typeName, String deviceName, String game_serverName, String operatorName) {
                            device_id = deviceId;
                            game_server_id = game_serverId;
                            operator_id = operatorId;
                            pageNum = 1;
                            getData();
                        }
                    }));
        }
        popupView.show();
    }

    /**
     * 排序弹窗
     */
    private void onGameSortDialog(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new ComprehensiveSortingDialog(this, new ComprehensiveSortingDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(int sortType) {
                        sort_type = sortType;
                        pageNum = 1;
                        getData();
                    }
                })).show();
    }


    /**
     * 价格区间弹窗
     */
    private void onGamePriceRangeDialog(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new PriceRangeDialog(this, new PriceRangeDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(Float minPrice, Float maxPrice) {
                        tall_float = maxPrice;
                        lower_float = minPrice;
                        pageNum = 1;
                        getData();
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
