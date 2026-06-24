package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsDetailsBean;
import io.openim.android.taohaoba.databinding.FragmentProductDescriptionBinding;
import io.openim.android.taohaoba.utils.DensityUtil;
import io.openim.android.taohaoba.vm.game.ProductDetailsVM;
import io.openim.android.taohaoba.widgets.CustomDecoration;

/**
 * 商品描述
 */
public class ProductDescriptionFragment extends BaseFragment<ProductDetailsVM> implements ProductDetailsVM.ViewAction {

    private FragmentProductDescriptionBinding binding;
    private List<GoodsDetailsBean.ContentDTO> content = new ArrayList<>();
    private GoodsDetailsBean detailsBean;

    public ProductDescriptionFragment(GoodsDetailsBean detailsBean) {
        this.detailsBean = detailsBean;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(ProductDetailsVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductDescriptionBinding.inflate(getLayoutInflater());
        initView();
        return binding.getRoot();
    }

    private void initView() {
        binding.clzhly.setVisibility(detailsBean.getIs_account_source_required() == 1 ? VISIBLE : GONE);
        binding.clecsm.setVisibility(detailsBean.getIs_authentication_required() == 1 ? VISIBLE : GONE);
        binding.clfcm.setVisibility(detailsBean.getIs_indulge_required() == 1 ? VISIBLE : GONE);

        binding.tvName1.setText(detailsBean.getGame_name());
        binding.tvName2.setText(detailsBean.getGoods_no());
        binding.tvName3.setText(detailsBean.getCategory_name());
        binding.tvName4.setText(detailsBean.getSubmit_time());
        binding.tvName5.setText(detailsBean.getIs_authentication() == 1 ? "可二次实名" : "不可二次实名");
        binding.tvName6.setText(detailsBean.getIs_indulge() == 1 ? "有" : "无");
        if (detailsBean.getIs_account_source() == 1) {
            binding.tvName7.setText("自己注册");
        }else if (detailsBean.getIs_account_source() == 2) {
            binding.tvName7.setText("本平台购买");
        }else if (detailsBean.getIs_account_source() == 3) {
            binding.tvName7.setText("其他平台购买");
        }
        for (GoodsDetailsBean.ContentDTO contentDTO : detailsBean.getContent()) {
            if (!TextUtils.isEmpty(contentDTO.getValue())){
                content.add(contentDTO);
            }
        }
        BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter(R.layout.item_product_description, content) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                GoodsDetailsBean.ContentDTO bean = (GoodsDetailsBean.ContentDTO) o;
                baseViewHolder.setText(R.id.tv_title, bean.getKey());
                baseViewHolder.setText(R.id.tv_name, bean.getValue());
            }
        };
        binding.rcRecycler.addItemDecoration(new CustomDecoration(getContext(), CustomDecoration.VERTICAL_LIST, R.drawable.shape_divider_love, DensityUtil.dipToPx(getContext(), 0)));
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rcRecycler.setAdapter(baseQuickAdapter);

        binding.tvName2.setOnClickListener(v -> {
            copyText(getContext(), detailsBean.getGoods_no());
        });
    }

    @Override
    public void err(String msg) {

    }
}
