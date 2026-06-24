package io.openim.android.taohaoba.ui.adapter;

import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;

import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.TransactionBean;

/**
 * 资产明细
 */
public class AssetDetailsAdapter extends BaseQuickAdapter<TransactionBean.ListDTO, BaseViewHolder> {

    public AssetDetailsAdapter(List<TransactionBean.ListDTO> data) {
        super(R.layout.item_asset_details, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, TransactionBean.ListDTO s) {

        ImageView iv_img = baseViewHolder.getView(R.id.iv_img);

        baseViewHolder.setText(R.id.tv_name, s.getType_zh());
        baseViewHolder.setText(R.id.tv_account_number, s.getCreated_time());
        ;
        baseViewHolder.setText(R.id.tv_revenue_collection, (s.getChange_type() == 1 ? "+" : "-")+formatCurrency(s.getAmount()));
        baseViewHolder.setTextColor(R.id.tv_revenue_collection, baseViewHolder.itemView.getContext().getColor(s.getChange_type() == 1 ? R.color.white : R.color.color_EACA92));
//        baseViewHolder.setText(R.id.tv_balance, s.getChange_type() == 1 ? formatCurrency1(s.getBefore_amount()) : formatCurrency1(s.getAfter_amount()));
        baseViewHolder.setText(R.id.tv_balance, formatCurrency1(s.getBefore_amount()));

        if (s.getType() == 3){
            iv_img.setImageResource(R.mipmap.ic_withdrawal);
        }else {
            iv_img.setImageResource(s.getChange_type() == 1 ? R.mipmap.ic_income_received : R.mipmap.ic_disbursement);
        }
    }
}