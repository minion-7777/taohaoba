package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.AfterSalesDetailsBean;
import io.openim.android.taohaoba.databinding.ActivityAfterSalesDetailsBinding;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.vm.me.AfterSalesVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 售后详情
 */
public class AfterSalesDetailsActivity extends BaseActivity<AfterSalesVM, ActivityAfterSalesDetailsBinding> implements AfterSalesVM.ViewAction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AfterSalesVM.class);
        bindViewDataBinding(ActivityAfterSalesDetailsBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    private void initView() {
        vm.getAfterSalesDetailsBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it -> {
            dismissLoadingDialog();
            initViewData(it);
        });

        showLoadingDialog();
        vm.post_sale_info(getIntent().getIntExtra("post_sale_id", 0));
    }

    private void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });
    }

    private void initViewData(AfterSalesDetailsBean it){
        view.tvStatus.setText(it.getOrder_post_sale_status() == 1 ? "售后申请已提交，等待客服审核中" : it.getOrder_post_sale_status() == 2 ? "售后申请已通过审核，客服正在处理中，请保持通讯畅通" : it.getOrder_post_sale_status() == 3 ? "售后申请已处理完毕" : "售后申请已取消");
        view.clAfterSalesFeedback.setVisibility(it.getOrder_post_sale_status() == 3? VISIBLE : GONE);
        view.tvProcessingResults.setText(it.getOrder_post_sale_handle());

        //是否进行包赔 0否 1是
        if (it.getOrder_post_sale_is_reparation() == 0){
            view.tvCompensationAmount.setText(PriceFormatUtils.formatCurrency("0"));
        }else {
            //包赔审核 0待审核 1审核通过 2审核不通过
            if (it.getOrder_post_sale_reparation_examine() == 0){
                view.tvCompensationAmount.setText("审核中，工作人员将在3-7个工作日内完成审核");
            }else if (it.getOrder_post_sale_reparation_examine() == 1){
                view.tvCompensationAmount.setText(PriceFormatUtils.formatCurrency(it.getOrder_post_sale_reparation_price()));
            }else if (it.getOrder_post_sale_reparation_examine() == 2){
                view.tvCompensationAmount.setText("审核不通过："+it.getOrder_post_sale_reparation_notes());
            }
        }

        view.tvAfterSalesType.setText(it.getOrder_post_sale_reason() == 1? "实名问题" : it.getOrder_post_sale_reason() == 2? "账号被找回" : it.getOrder_post_sale_reason() == 3? "换绑问题" : it.getOrder_post_sale_reason() == 4? "账号封禁冻结" : "其他");
        view.tvMobilePhone.setText(it.getOrder_post_sale_phone());

        view.tvCompletionTimeTxt.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_wx_code()) ? VISIBLE : GONE);
        view.tvContactWeChatTxt.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_wx_code()) ? VISIBLE : GONE);
        view.tvContactWeChat.setText(it.getOrder_post_sale_wx_code());
        view.tvApplicationTime.setText(it.getCreated_time());
        view.tvProcessingTimeTxt.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_acceptance_time()) ? VISIBLE : GONE);
        view.tvProcessingTime.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_acceptance_time()) ? VISIBLE : GONE);
        view.tvProcessingTime.setText(it.getOrder_post_sale_acceptance_time());
        view.tvCompletionTimeTxt.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_complete_time()) ? VISIBLE : GONE);
        view.tvCompletionTime.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_complete_time()) ? VISIBLE : GONE);
        view.tvCompletionTime.setText(it.getOrder_post_sale_complete_time());
        view.tvCancelTimeTxt.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_close_time()) ? VISIBLE : GONE);
        view.tvCancelTime.setVisibility(!TextUtils.isEmpty(it.getOrder_post_sale_close_time()) ? VISIBLE : GONE);
        view.tvCancelTime.setText(it.getOrder_post_sale_close_time());

        view.tvOrderNumber.setText("商品编号："+it.getOrder().getGoods_no());
        Glide.with(getBaseContext())
                .load(!TextUtils.isEmpty(it.getOrder().getGoods_image()) ? convertToArray(it.getOrder().getGoods_image())[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(view.ivOrderGoodsImg);
        view.tvOrderGoodsName.setText(it.getOrder().getGoods_title());
        view.tvOrderGoodsAreaCode.setText(it.getOrder().getGame_service_name());
        view.tvGoodsPrice.setText(PriceFormatUtils.formatCurrency(it.getOrder().getGoods_price()));
        view.tvPrice.setText("实付款"+PriceFormatUtils.formatCurrency(it.getOrder().getPayment_price()));

        view.tvOrderNumberCount.setText(it.getOrder().getOrder_no());
        view.tvOrderTime.setText(it.getOrder().getPlace_time());
        view.tvDealTime.setText(it.getOrder().getDeal_time());
        view.tvTransactionType.setText(it.getOrder().getPenalty_name());
        view.tvIsBaopei.setText(it.getOrder().getIs_reparation() == 1? "是" : "否");

        view.tvOrderNumber.setOnClickListener(v-> {
            copyText(it.getOrder().getGoods_no());
        });

        view.tvOrderNumberCount.setOnClickListener(v-> {
            copyText(it.getOrder().getOrder_no());
        });
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
