package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.CouponListBean;
import io.openim.android.taohaoba.databinding.FragmentHelpCenterBinding;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.home.HomeVM;
import io.openim.android.taohaoba.vm.me.CouponVM;

/**
 * 优惠券Fragment
 */
public class CouponFragment extends BaseFragment<CouponVM> implements HomeVM.ViewAction {

    private int id;
    private List<CouponListBean.ListDTO> list = new ArrayList<>();
    private int totalCount;
    private int pageNum = 1;
    private BaseQuickAdapter adapter;

    public CouponFragment(int id) {
        this.id = id;
    }

    public CouponFragment() {

    }

    private FragmentHelpCenterBinding view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(CouponVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = FragmentHelpCenterBinding.inflate(getLayoutInflater());
        initSubscribe();
        return view.getRoot();
    }

    private void initSubscribe() {
        initViewRecycler();

        vm.getCouponListBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            dismissLoadingDialog();
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1) {
                list.clear();
            }
            totalCount = it.getTotal();
            list.addAll(it.getList());
            adapter.notifyDataSetChanged();
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        showLoadingDialog();
        getData();

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
        vm.getCouponList(pageNum, id);
    }

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_coupon, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                CouponListBean.ListDTO item = (CouponListBean.ListDTO) o;

                baseViewHolder.setText(R.id.tv_coupon_amount, item.getCoupon_amount());
                baseViewHolder.setText(R.id.tv_coupon_amount_minimum, "满"+item.getCoupon_amount_minimum().toString()+"可用");
                baseViewHolder.setText(R.id.tv_coupon_name, item.getCoupon_name());
                baseViewHolder.setText(R.id.tv_end_time, "有效期至\n"+item.getUser_coupon_end_time());

                baseViewHolder.setVisible(R.id.tv_but, true);
                baseViewHolder.setText(R.id.tv_but, id == 0 ? "立即使用" : id == 1 ? "已使用" : "已过期");
                baseViewHolder.setVisible(R.id.view, id != 0);

                baseViewHolder.getView(R.id.tv_but).setOnClickListener(v -> {
                    if (id == 0) {
                        //使用优惠券
                        EventBus.getDefault().post(new MessageEvent(Constants.GOHOME));
                        ActivityManager.finishAllExceptActivity(MainActivity.class);                    }
                });
            }
        };
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        view.rcRecycler.setAdapter(adapter);
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
        view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
    }

}
