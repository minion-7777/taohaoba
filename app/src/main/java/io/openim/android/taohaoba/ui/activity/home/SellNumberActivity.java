package io.openim.android.taohaoba.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.databinding.ActivitySellNumberBinding;
import io.openim.android.taohaoba.utils.DensityUtil;
import io.openim.android.taohaoba.vm.game.GameVM;
import io.openim.android.taohaoba.vm.home.MySellVM;
import io.openim.android.taohaoba.widgets.CustomDecoration;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 卖账号
 */
public class SellNumberActivity extends BaseActivity<MySellVM, ActivitySellNumberBinding> implements MySellVM.ViewAction {

    private BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter;
    private List<PatternBean.PatternDTO> list = new ArrayList<>();
    private int categoryId;//商品类型id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewDataBinding(ActivitySellNumberBinding.inflate(getLayoutInflater()));
        bindVM(MySellVM.class);
        adjustToolbarForStatusBar(view.toolbar);

        categoryId = getIntent().getIntExtra("categoryId", 0);

        initListener();
        initView();
        initRec();
    }

    private void initListener(){
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvSubmit.setOnClickListener(v->{
            finish();
        });

    }

    private void initView() {
        vm.getSellImgBeanMutableLiveData().observe(this, it->{
            if (it != null && it.getList() != null && it.getList().size() > 0) {
                Glide.with(this)
                        .load(it.getList().get(0).getPath())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(view.ivImg);
            }
        });

        vm.getPatternBeanMutableLiveData().observe(this, it->{
            list.addAll(it.getPattern());
            baseQuickAdapter.notifyDataSetChanged();
        });

        vm.getMy_want_buy_img();
        vm.getPattern();
    }

    private void initRec(){
        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_sell_number, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                PatternBean.PatternDTO bean = (PatternBean.PatternDTO) o;
                baseViewHolder.setText(R.id.tv_text1, bean.getName());

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (bean.getType() == 1) {
                        //自由交易
                        startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                                .putExtra("pageIndex", 5)
                                .putExtra("title", bean.getName())
                                .putExtra("patternId", bean.getId())
                                .putExtra("categoryId", categoryId)
                                .putExtra("seller_service_ratio", bean.getSeller_service_ratio())
                                .putExtra("seller_service_price", bean.getSeller_service_price()));
                    }else if (bean.getType() == 2){
                        //快速回收
                        startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                                .putExtra("pageIndex", 3)
                                .putExtra("title", bean.getName())
                                .putExtra("patternId", bean.getId())
                                .putExtra("categoryId", categoryId));
                    }
                });
            }
        };
        view.rcRecycler.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.shape_divider_love, DensityUtil.dipToPx(this, 0)));
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        view.rcRecycler.setAdapter(baseQuickAdapter);
    }

    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
