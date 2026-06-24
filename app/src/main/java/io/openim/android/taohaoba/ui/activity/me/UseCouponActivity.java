package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.CouponListBean;
import io.openim.android.taohaoba.databinding.ActivityUseCouponBinding;
import io.openim.android.taohaoba.vm.me.CouponVM;
import io.openim.android.taohaoba.vm.me.WalletVM;

/**
 * 使用优惠券
 */
public class UseCouponActivity extends BaseActivity<CouponVM, ActivityUseCouponBinding> implements WalletVM.ViewAction {

    private List<CouponListBean.ListDTO> list = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private Double goods_price, reparation_price, pattern_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(CouponVM.class);
        bindViewDataBinding(ActivityUseCouponBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        goods_price = getIntent().getDoubleExtra("goods_price", 0.0);//商品价格
        reparation_price = getIntent().getDoubleExtra("reparation_price", 0.0);//包赔价格
        pattern_price = getIntent().getDoubleExtra("pattern_price", 0.0);//买家服务费

        initViewRecycler();

        vm.getOrderAvailableListBeanMutableLiveData().observe(this, it -> {
            dismissLoadingDialog();
            if (it.getList() != null && it.getList().size() > 0) {
                list.addAll(it.getList());
                adapter.notifyDataSetChanged();
            }
            view.emptyLayout.setVisibility(list.size() > 0 ? GONE : VISIBLE);
        });

        getData();
    }

    protected void initListener() {

        view.toolbar.setTitleText("优惠券");
        view.toolbar.setOnVerificationListener(() -> {
            finish();
        });

        view.refreshIndex.setEnableRefresh(false);
        view.refreshIndex.setEnableLoadMore(false);

        //提交
        view.tvSubmit.setOnClickListener(v -> {
            // 构建 Intent 并传递优惠券数据
            CouponListBean.ListDTO coupon = null;
            if (selectedPosition != -1) {
                coupon = list.get(selectedPosition);
            }
            Intent resultIntent = new Intent();
            resultIntent.putExtra("user_coupon_id", selectedPosition == -1 ? null : coupon.getUser_coupon_id());
            resultIntent.putExtra("coupon_amount", selectedPosition == -1 ? null : coupon.getCoupon_amount());
            resultIntent.putExtra("coupon_type", selectedPosition == -1 ? null : coupon.getConpon_type());
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void getData(){
        showLoadingDialog();
        vm.getOrderAvailableList();
    }

    // 添加选中位置变量 (-1表示未选中)
    private int selectedPosition = -1;

    private void initViewRecycler(){
        adapter = new BaseQuickAdapter(R.layout.item_coupon, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                CouponListBean.ListDTO item = (CouponListBean.ListDTO) o;

                baseViewHolder.setText(R.id.tv_coupon_amount, item.getCoupon_amount());
                baseViewHolder.setText(R.id.tv_coupon_amount_minimum, "满"+item.getCoupon_amount_minimum().toString()+"可用");
                if (item.getConpon_type() == 1) {
                    baseViewHolder.setText(R.id.tv_coupon_name, item.getCoupon_name() + "（包赔券）");
                } else if (item.getConpon_type() == 2) {
                    baseViewHolder.setText(R.id.tv_coupon_name, item.getCoupon_name() + "（支付券）");
                } else if (item.getConpon_type() == 3) {
                    baseViewHolder.setText(R.id.tv_coupon_name, item.getCoupon_name() + "（服务费券）");
                } else if (item.getConpon_type() == 4) {
                    baseViewHolder.setText(R.id.tv_coupon_name, item.getCoupon_name() + "（商品费券）");
                }

                baseViewHolder.setText(R.id.tv_end_time, "有效期至"+item.getUser_coupon_end_time());
                baseViewHolder.setVisible(R.id.iv_select, true);
                // 根据选中状态显示选中图标
                baseViewHolder.setImageResource(R.id.iv_select, selectedPosition == baseViewHolder.getAdapterPosition() ? R.mipmap.ic_a_check_t : R.mipmap.ic_a_check_f);

                //优惠卷类型 1包赔卷 2支付卷 3服务费卷 4商品费卷
                if (item.getConpon_type() == 1){
                    //包赔卷
                    baseViewHolder.itemView.setEnabled(reparation_price >= Double.parseDouble(item.getCoupon_amount_minimum()) ? true : false);
                    baseViewHolder.setVisible(R.id.view, reparation_price < Double.parseDouble(item.getCoupon_amount_minimum()));
                }else if (item.getConpon_type() == 2){
                    //支付卷
//                    baseViewHolder.itemView.setEnabled(reparation_price >= item.getCoupon_amount_minimum() ? true : false);
                }else if (item.getConpon_type() == 3){
                    //服务费卷
                    baseViewHolder.itemView.setEnabled(pattern_price >= Double.parseDouble(item.getCoupon_amount_minimum()) ? true : false);
                    baseViewHolder.setVisible(R.id.view, pattern_price < Double.parseDouble(item.getCoupon_amount_minimum()));
                }else if (item.getConpon_type() == 4){
                    //商品费卷
                    baseViewHolder.itemView.setEnabled(goods_price >= Double.parseDouble(item.getCoupon_amount_minimum()) ? true : false);
                    baseViewHolder.setVisible(R.id.view, goods_price < Double.parseDouble(item.getCoupon_amount_minimum()));
                }

                baseViewHolder.itemView.setOnClickListener(v -> {
                    int currentPosition = baseViewHolder.getAdapterPosition();
                    if (selectedPosition == currentPosition) {
                        // 再次点击取消选中
                        selectedPosition = -1;
                    } else {
                        // 选中当前项（单选逻辑）
                        selectedPosition = currentPosition;
                    }
                    // 刷新列表以更新选中状态
                    adapter.notifyDataSetChanged();
                });
            }
        };
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
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
