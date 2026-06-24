package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import static com.tencent.qcloud.tuikit.tuichat.util.PriceFormatUtils.formatPrice;
import static com.tencent.qcloud.tuikit.tuichat.util.StringArrayUtil.convertToArray;

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
import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageBBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.utils.Routes;

public class IntelligentTipsMessageHolderB extends MessageContentHolder {
    private final RoundImageView iconl2;
    private final LinearLayout layoutl2;
    private TextView titlel1;
    private TextView contentl2;
    private TextView pricel2;
    private List<String> mListRoutesMain = new ArrayList<>();

    public IntelligentTipsMessageHolderB(View itemView) {
        super(itemView);
        titlel1 = itemView.findViewById(R.id.titlel1);
        iconl2 = itemView.findViewById(R.id.iconl2);
        contentl2 = itemView.findViewById(R.id.contentl2);
        pricel2 = itemView.findViewById(R.id.pricel2);
        layoutl2 = itemView.findViewById(R.id.layoutl2);

        mListRoutesMain.add(Routes.Main.PRODUCT_DETAILS);
        mListRoutesMain.add(Routes.Main.ORDER_DETAILS);
        mListRoutesMain.add(Routes.Main.CONFIRM_AN_ORDER);
        mListRoutesMain.add(Routes.Main.INPUT_INFORMATION);
        mListRoutesMain.add(Routes.Main.SUBMINT_ACCOUNT_INFORMATION);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageBBean) {
            IntelligentTipsMessageBBean tipsMsg = (IntelligentTipsMessageBBean) msg;

            titlel1.setText(tipsMsg.getTitle());
            contentl2.setText(tipsMsg.getContent());
            pricel2.setText(formatPrice(getRecyclerView().getContext(), String.valueOf(tipsMsg.getPrice())));

            if (!TextUtils.isEmpty(tipsMsg.getIcon())) {
                Glide.with(getRecyclerView().getContext())
                        .load(convertToArray(tipsMsg.getIcon())[0])
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iconl2);
            }

            layoutl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(tipsMsg.getUrl().getPageName())){
                        boolean isMatch= false;
                        String matchedRoutesPageName = "";
                        for (int i = 0; i < mListRoutesMain.size(); i++) {
                            if (mListRoutesMain.get(i).contains(tipsMsg.getUrl().getPageName())){
                                isMatch = true;
                                matchedRoutesPageName = mListRoutesMain.get(i);
                                break;
                            }
                        }
                        if (isMatch){
                            ARouter.getInstance().build(matchedRoutesPageName)
                                    .withInt("goodsId", tipsMsg.getUrl().getParamId())
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
