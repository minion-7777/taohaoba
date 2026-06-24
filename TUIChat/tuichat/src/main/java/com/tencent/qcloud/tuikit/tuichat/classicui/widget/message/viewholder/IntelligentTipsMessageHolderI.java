package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageIBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.classicui.adapter.KeyValueAdapter;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.ChatView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.utils.Routes;

public class IntelligentTipsMessageHolderI extends MessageContentHolder {

    private final TextView titlel8;
    private final TextView operationl8;
    private final RecyclerView recyclerViewl8;
    private List<String> mListRoutesMain = new ArrayList<>();

    public IntelligentTipsMessageHolderI(View itemView) {
        super(itemView);
        titlel8 = itemView.findViewById(R.id.titlel8);
        operationl8 = itemView.findViewById(R.id.operationl8);
        recyclerViewl8 = itemView.findViewById(R.id.recyclerViewl8);

        mListRoutesMain.add(Routes.Main.PRODUCT_DETAILS);
        mListRoutesMain.add(Routes.Main.ORDER_DETAILS);
        mListRoutesMain.add(Routes.Main.CONFIRM_AN_ORDER);
        mListRoutesMain.add(Routes.Main.INPUT_INFORMATION);
        mListRoutesMain.add(Routes.Main.SUBMINT_ACCOUNT_INFORMATION);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_9;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageIBean) {
            IntelligentTipsMessageIBean tipsMsg = (IntelligentTipsMessageIBean) msg;

            titlel8.setText(tipsMsg.getTitle());

            KeyValueAdapter adapter = new KeyValueAdapter(tipsMsg.getContentJson());
            recyclerViewl8.setLayoutManager(new LinearLayoutManager(getRecyclerView().getContext()));
            recyclerViewl8.setAdapter(adapter);

            operationl8.setText(tipsMsg.getButton().getText());
            String pageName = tipsMsg.getButton().getUrl().getPageName();

            if(TUILogin.getLoginUser().equalsIgnoreCase(tipsMsg.getIm_buyer_idViewHolder()) && (ChatView.getPattern() == 2 || ChatView.getPattern() == 4) && ChatView.getOrderStatus() == 0){
                operationl8.setVisibility(VISIBLE);
            }else {
                operationl8.setVisibility(GONE);
            }
            operationl8.setOnClickListener(new View.OnClickListener() {
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
                            ARouter.getInstance().build(matchedRoutesPageName)
                                    .withInt("order_id", tipsMsg.getButton().getUrl().getParamId())
                                    .withBoolean("is_order_info", true)
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
