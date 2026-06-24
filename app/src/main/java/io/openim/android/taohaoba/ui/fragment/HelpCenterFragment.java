package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.ArticlesIndexBean;
import io.openim.android.taohaoba.databinding.FragmentHelpCenterBinding;
import io.openim.android.taohaoba.ui.activity.me.HelpWebViewActivity;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.home.HomeVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 帮助中心
 */
public class HelpCenterFragment extends BaseFragment<WebViewVM> implements HomeVM.ViewAction {

    private String id;
    private List<ArticlesIndexBean.ListDTO> list = new ArrayList<>();
    private int totalCount;
    private int pageNum = 1;
    private BaseQuickAdapter adapter;

    public HelpCenterFragment(String id) {
        this.id = id;
    }

    public HelpCenterFragment() {

    }

    private FragmentHelpCenterBinding view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(WebViewVM.class);
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

        vm.getArticlesIndexBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
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
        vm.getArticlesIndex(String.valueOf(pageNum), id, "10");
    }

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_help_center, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                ArticlesIndexBean.ListDTO item = (ArticlesIndexBean.ListDTO) o;
                RoundImageView iv_image = baseViewHolder.getView(R.id.iv_image);

                baseViewHolder.setText(R.id.tv_title, item.getTitle());
                baseViewHolder.setText(R.id.tv_time, item.getCreated_time());

                Glide.with(baseViewHolder.itemView.getContext())
                        .load(!TextUtils.isEmpty(item.getCover_url()) ? item.getCover_url() : "")
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_image);

                baseViewHolder.itemView.setOnClickListener(v -> {
                    startActivity(new Intent(getContext(), HelpWebViewActivity.class)
                            .putExtra("category_id", item.getId()));
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
