package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.ActivityCenterBean;
import io.openim.android.taohaoba.bean.ArticlesCategoryBean;
import io.openim.android.taohaoba.bean.ArticlesIndexBean;
import io.openim.android.taohaoba.bean.TabEntity;
import io.openim.android.taohaoba.databinding.ActivityActivityCenterBinding;
import io.openim.android.taohaoba.databinding.ActivityHelpCenterBinding;
import io.openim.android.taohaoba.ui.fragment.HelpCenterFragment;
import io.openim.android.taohaoba.utils.PageUtil;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.vm.me.WebViewVM;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 活动中心
 */
public class ActivityCenterActivity extends BaseActivity<WebViewVM, ActivityActivityCenterBinding> implements WalletVM.ViewAction {

    private List<ActivityCenterBean.ListDTO> list = new ArrayList<>();
    private int totalCount;
    private int pageNum = 1;
    private BaseQuickAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WebViewVM.class);
        bindViewDataBinding(ActivityActivityCenterBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        initViewRecycler();

        vm.getActivityCenterBeanMutableLiveData().observe(this, it -> {
            dismissLoadingDialog();
            view.refreshIndex.finishLoadMore();
            view.refreshIndex.finishRefresh();
            if (pageNum == 1) {
                list.clear();
            }
            totalCount = it.getTotal();
            if (it.getList() != null && it.getList().size() > 0) {
                list.addAll(it.getList());
                adapter.notifyDataSetChanged();
            }
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        getData();
    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(() -> {
            finish();
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
        vm.getActivityList(pageNum, "all");
    }

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_activity_center, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                ActivityCenterBean.ListDTO item = (ActivityCenterBean.ListDTO) o;
                RoundImageView iv_image = baseViewHolder.getView(R.id.iv_image);
                TextView tv_t = baseViewHolder.getView(R.id.tv_t);

                baseViewHolder.setText(R.id.tv_title, item.getActivity_name());
                baseViewHolder.setText(R.id.tv_time, item.getActivity_end_time_formatted());

                tv_t.setBackground(getDrawable(item.getCurrent_status().equals("ongoing") ? R.drawable.shape_radius15_ffd497_fafbf5 : R.drawable.shape_radius15_999999));
                tv_t.setEnabled(item.getCurrent_status().equals("ongoing"));

                if (item.getCurrent_status().equals("upcoming")){
                    baseViewHolder.setText(R.id.tv_current_status, "未开始");
                }else if (item.getCurrent_status().equals("ongoing")){
                    baseViewHolder.setText(R.id.tv_current_status, "进行中");
                }else if (item.getCurrent_status().equals("ended")){
                    baseViewHolder.setText(R.id.tv_current_status, "已结束");
                }

                Glide.with(baseViewHolder.itemView.getContext())
                        .load(!TextUtils.isEmpty(item.getActivity_img_url()) ? item.getActivity_img_url() : "")
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_image);

                tv_t.setOnClickListener(v -> {
                    startActivity(new Intent(getContext(), ActivityCenterWebViewActivity.class)
                            .putExtra("activity_code", item.getActivity_code())
                            .putExtra("activity_name", item.getActivity_name())
                            .putExtra("pageType", 0)
                            .putExtra("activity_jump_url", item.getActivity_jump_url()));
                });
            }
        };
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        view.rcRecycler.setAdapter(adapter);
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
        }
        view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
    }
}
