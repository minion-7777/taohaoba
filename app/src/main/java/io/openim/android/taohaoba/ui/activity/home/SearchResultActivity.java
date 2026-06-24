package io.openim.android.taohaoba.ui.activity.home;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.IndexSearchBean;
import io.openim.android.taohaoba.databinding.ActivityAccountNumberListBinding;
import io.openim.android.taohaoba.ui.activity.game.ProductDetailsActivity;
import io.openim.android.taohaoba.ui.dialog.ComprehensiveSortingDialog;
import io.openim.android.taohaoba.ui.dialog.GameDistrictServerOneDialog;
import io.openim.android.taohaoba.ui.dialog.PriceRangeDialog;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.vm.home.SearchVM;
import io.openim.android.taohaoba.widgets.RoundImageView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 首页-搜索结果页
 */
public class SearchResultActivity extends BaseActivity<SearchVM, ActivityAccountNumberListBinding> implements SearchVM.ViewAction{

    private int sort_type;//默认为0   1 ： 价格降序  2：价格升序  3：时间降序 4：时间升序
    private int device_id;//设备id
    private int operator_id;//运营商id
    private int game_type_id;//游戏分类  端游 手游
    private Float tall_float;//最高价格
    private Float lower_float;//最低价格
    private String title="";//搜索内容
    private int pageNum = 1;

    private List<IndexSearchBean.GoodsDTO> goodsListBeans = new ArrayList<>();
    private int totalCount;
    private IndexSearchBean.DeviceOperatorDTO gameConfigurationListBean;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(SearchVM.class);
        bindViewDataBinding(ActivityAccountNumberListBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    private void initView() {
        title = getIntent().getStringExtra("searchResult");

        view.toolbar.setTitleText("搜索");

        view.etSearch.setText(title);

        view.tvFilter.setVisibility(GONE);

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        initViewRecycler();

        vm.getIndexSearchBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1){
                goodsListBeans.clear();
                gameConfigurationListBean = it.getDevice_operator();
            }
            totalCount = it.getTotal();
            if (it.getGoods() != null) {
                goodsListBeans.addAll(it.getGoods());
                adapter.notifyDataSetChanged();
            }
            view.emptyLayout.setVisibility(goodsListBeans.size() > 0 ? GONE : VISIBLE);
        });

        pageNum = 1;
        getData();

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

        view.tvSearch.setOnClickListener(v->{
            showLoadingDialog();
            title = view.etSearch.getText().toString();
            pageNum = 1;
            getData();
        });


        view.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 1. 隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // 2. 执行搜索逻辑
                String query = view.etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    showLoadingDialog();
                    title = view.etSearch.getText().toString();
                    pageNum = 1;
                    getData();
                } else {
                    shakeAnimation(view.etSearch);
                    showToast("请输入搜索内容");
                }
                return true; // 消费事件
            }
            return false;
        });
    }

    private void getData(){
        showLoadingDialog();
        vm.index_search(pageNum, sort_type, device_id, game_type_id, operator_id, tall_float, lower_float, title);
    }

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_game_account_number_list, goodsListBeans) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                IndexSearchBean.GoodsDTO it = (IndexSearchBean.GoodsDTO) o;

                RecyclerView rc_recycler = baseViewHolder.getView(R.id.rc_recycler);
                RoundImageView iv_img = baseViewHolder.getView(R.id.iv_img);

                if (!TextUtils.isEmpty(it.getImage())) {
                    Glide.with(baseViewHolder.itemView.getContext())
                            .load(convertToArray(it.getImage())[0])
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                    .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                    .centerCrop()// 图片裁剪方式
                            )
                            .into(iv_img);
                }

                baseViewHolder.setText(R.id.tv_hint, TextUtils.isEmpty(it.getTitle()) ? it.getGoods_no()+"" : it.getGoods_no()+" "+it.getTitle());
                baseViewHolder.setText(R.id.tv_service_name, TextUtils.isEmpty(it.getGame_service_name()) ? (it.getDevice_name()+","+it.getOperator_name()) : it.getGame_service_name());
                baseViewHolder.setText(R.id.tv_price, PriceFormatUtils.formatPrice(baseViewHolder.itemView.getContext(), String.valueOf(it.getRetail_price())));
                baseViewHolder.setText(R.id.tv_view_count, TextUtils.isEmpty(it.getView_count()) ? "0" : it.getView_count());
                baseViewHolder.setText(R.id.tv_give_up, TextUtils.isEmpty(it.getFavorite_count()) ? "0" : it.getFavorite_count());

                if (!TextUtils.isEmpty(it.getLabel())) {
                    // 使用逗号拆分字符串
                    String[] parts = it.getLabel().split(",");

                    // 将拆分后的字符串添加到 List 中
                    List<String> list = new ArrayList<>();
                    for (String part : parts) {
                        list.add(part);
                    }

                    BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter(R.layout.item_tag, list) {
                        @Override
                        protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                            baseViewHolder.setText(R.id.tv_tag1, o.toString());
                        }
                    };
                    rc_recycler.setLayoutManager(new LinearLayoutManager(baseViewHolder.itemView.getContext(), RecyclerView.HORIZONTAL, false));
                    rc_recycler.setAdapter(baseQuickAdapter);
                }

                baseViewHolder.itemView.setOnClickListener(v->{
                    startActivity(new Intent(getBaseContext(), ProductDetailsActivity.class)
                            .putExtra("goodsId", it.getId()));
                });
            }
        };
        view.rcRecycler.setNestedScrollingEnabled(false);
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 设置适配器
        view.rcRecycler.setAdapter(adapter);

    }

    /**
     * 游戏类型
     */
    private void onGameTypeDialog(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new GameDistrictServerOneDialog(this, gameConfigurationListBean, new GameDistrictServerOneDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(int gameTypeId, int deviceId, int operatorId) {
                        game_type_id = gameTypeId;
                        device_id = deviceId;
                        operator_id = operatorId;
                        pageNum = 1;
                        getData();
                    }
                })).show();
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
        dismissLoadingDialog();
        showToast(msg);
    }
}
