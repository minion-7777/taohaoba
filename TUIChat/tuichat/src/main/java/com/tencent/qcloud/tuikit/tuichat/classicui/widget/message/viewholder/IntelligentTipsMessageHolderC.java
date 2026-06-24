package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import static com.tencent.qcloud.tuikit.tuichat.util.PriceFormatUtils.formatPrice;
import static com.tencent.qcloud.tuikit.tuichat.util.StringArrayUtil.convertToArray;

import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageCBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Routes;

public class IntelligentTipsMessageHolderC extends MessageContentHolder {
    private final LinearLayout layoutl3;
    private final RoundImageView iconl3;
    private final TextView titlel3;
    private final TextView timel3;
    private final TextView statel3;
    private final TextView contentl3;
    private final TextView pricel3;
    private final TextView operationl3;
    private List<String> mListRoutesMain = new ArrayList<>();

    public IntelligentTipsMessageHolderC(View itemView) {
        super(itemView);
        layoutl3 = itemView.findViewById(R.id.layoutl3);
        iconl3 = itemView.findViewById(R.id.iconl3);
        titlel3 = itemView.findViewById(R.id.titlel3);
        timel3 = itemView.findViewById(R.id.timel3);
        statel3 = itemView.findViewById(R.id.statel3);
        contentl3 = itemView.findViewById(R.id.contentl3);
        pricel3 = itemView.findViewById(R.id.pricel3);
        operationl3 = itemView.findViewById(R.id.operationl3);

        mListRoutesMain.add(Routes.Main.PRODUCT_DETAILS);
        mListRoutesMain.add(Routes.Main.ORDER_DETAILS);
        mListRoutesMain.add(Routes.Main.CONFIRM_AN_ORDER);
        mListRoutesMain.add(Routes.Main.INPUT_INFORMATION);
        mListRoutesMain.add(Routes.Main.SUBMINT_ACCOUNT_INFORMATION);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_3;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageCBean) {
            IntelligentTipsMessageCBean tipsMsg = (IntelligentTipsMessageCBean) msg;

            titlel3.setText(tipsMsg.getTitle());
            timel3.setText("交易时间："+tipsMsg.getSecondTitle().getTime());
            statel3.setText(tipsMsg.getSecondTitle().getState());
            if (!TextUtils.isEmpty(tipsMsg.getSecondTitle().getState_type_color())) {
                statel3.setTextColor(Color.parseColor(tipsMsg.getSecondTitle().getState_type_color()));
            }
            contentl3.setText(tipsMsg.getContent());
            pricel3.setText(formatPrice(getRecyclerView().getContext(), String.valueOf(tipsMsg.getPrice())));
            if (!TextUtils.isEmpty(tipsMsg.getIcon())) {
                Glide.with(getRecyclerView().getContext())
                        .load(convertToArray(tipsMsg.getIcon())[0])
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iconl3);
            }

            operationl3.setText(tipsMsg.getButton().getText());
            String pageName = tipsMsg.getButton().getUrl().getPageName();
            layoutl3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(pageName)){
                        boolean isMatch= false;
                        String matchedRoutesPageName = "";
                        for (int i = 0; i < mListRoutesMain.size(); i++) {
                            if (mListRoutesMain.get(i).contains(pageName)){
                                isMatch = true;
                                matchedRoutesPageName = mListRoutesMain.get(i);
                                break;
                            }
                        }
                        if (isMatch){
                            try {
                                Class<?> targetActivityClass = Class.forName("io.openim.android.taohaoba.ui.activity.game.OrderDetailsActivity");
                                ActivityManager.finishActivity(ActivityManager.isExist(targetActivityClass));
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }

                            ARouter.getInstance().build(matchedRoutesPageName)
                                    .withInt("order_id", tipsMsg.getButton().getUrl().getParamId())
                                    .withInt("orderType", (TUILogin.getUserId().equalsIgnoreCase(tipsMsg.getIm_buyer_idViewHolder())) ? 1 : 2)
                                    .navigation();
                        }else {
                            Toast.makeText(getRecyclerView().getContext(), "app版本过低，请升级。", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
    }

}
