package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.ClickUtil.isFastClick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameGoodsListBean;
import io.openim.android.taohaoba.databinding.FragmentAccountNumberListBinding;
import io.openim.android.taohaoba.ui.activity.game.ProductDetailsActivity;
import io.openim.android.taohaoba.ui.adapter.GameAccountNumberListAdapter;
import io.openim.android.taohaoba.utils.DensityUtil;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.home.HomeVM;
import io.openim.android.taohaoba.widgets.CustomDecoration;

public class AccountNumberFragment extends BaseFragment<HomeVM> implements HomeVM.ViewAction {

    private GameAccountNumberListAdapter adapter;
    private List<GameGoodsListBean.ListDTO> list = new ArrayList<>();
    private int gameId;
    private int pagePosition;
    private int pageNum = 1;
    private int totalCount;

    public AccountNumberFragment(int pagePosition, int gameId) {
        this.gameId = gameId;
        this.pagePosition = pagePosition;
    }

    public AccountNumberFragment() {

    }

    private FragmentAccountNumberListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(HomeVM.class);
        BaseApp.inst().putVM(vm);
        EventBus.getDefault().register(this); // 注册订阅者
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountNumberListBinding.inflate(getLayoutInflater());
        initViewRecycler();
        initSubscribe();
        return binding.getRoot();
    }

    private void initSubscribe() {
        vm.getGameGoodsListBeanMutableLiveData(gameId).observe(getViewLifecycleOwner(), it->{
            if (pageNum == 1){
                list.clear();
            }
            totalCount = it.getCount();
            list.addAll(it.getList());
            adapter.notifyDataSetChanged();
            binding.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        pageNum = 1;
        getData();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equalsIgnoreCase(Constants.REFRESH)) {
            //刷新
            pageNum = 1;
            getData();
        } else if (event.getMessage().equals(Constants.LOADING1)){
            //加载
            if (PageUtil.calculateTotalPages(totalCount) <= pageNum) {
                return;
            }
            pageNum++;
            getData();
        }
    }

    private void getData() {
        vm.getGameGoodsList(gameId, pageNum, gameId);
    }


    private void initViewRecycler(){
        binding.rcRecycler.setNestedScrollingEnabled(false);
//        binding.rcRecycler.addItemDecoration(new CustomDecoration(getContext(), CustomDecoration.VERTICAL_LIST, R.drawable.shape_divider_love, DensityUtil.dipToPx(getContext(), 0)));
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 创建适配器
        adapter = new GameAccountNumberListAdapter(list);
        // 设置适配器
        binding.rcRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (isFastClick()) {
                    startActivity(new Intent(getContext(), ProductDetailsActivity.class)
                            .putExtra("goodsId", list.get(position).getId()));
                }
            }
        });
    }

    @Override
    public void err(String msg) {
        if (!TextUtils.isEmpty(msg) && msg.contains("网络")) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
        binding.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // 避免内存泄漏
    }
}
