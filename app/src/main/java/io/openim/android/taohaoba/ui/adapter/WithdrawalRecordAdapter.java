package io.openim.android.taohaoba.ui.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.UserWithdrawalInfoBean;

import java.util.List;

/**
 * 提现记录
 */
public class WithdrawalRecordAdapter extends BaseQuickAdapter<UserWithdrawalInfoBean.ListDTO, BaseViewHolder> {

    public WithdrawalRecordAdapter(List<UserWithdrawalInfoBean.ListDTO> data) {
        super(R.layout.item_withdrawal_record, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UserWithdrawalInfoBean.ListDTO s) {

        ImageView iv_img = baseViewHolder.getView(R.id.iv_img);
        iv_img.setImageResource(s.getStatus() == 5 || s.getStatus() == 4 ? R.mipmap.ic_not_pass : R.mipmap.ic_pass_through);

        baseViewHolder.setText(R.id.tv_name, s.getStatus_zh());
        baseViewHolder.setText(R.id.tv_time, s.getCreated_time());
        baseViewHolder.setText(R.id.tv_type, s.getType() == 1 ? "支付宝" : "银行卡");
        baseViewHolder.setText(R.id.tv_revenue_collection, formatCurrency(s.getAmount()+""));
        baseViewHolder.setText(R.id.tv_withdrawal_no, s.getWithdrawal_no());
        baseViewHolder.setText(R.id.tv_textname, "原因："+(TextUtils.isEmpty(s.getRemark()) ? "无" : s.getRemark()));

        baseViewHolder.getView(R.id.ll_type).setVisibility(s.getStatus() == 5 || s.getStatus() == 4 ? VISIBLE : GONE);
    }
}