package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageJBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;

public class IntelligentTipsMessageHolderJ extends MessageContentHolder {

    private final TextView titlel;

    public IntelligentTipsMessageHolderJ(View itemView) {
        super(itemView);
        titlel = itemView.findViewById(R.id.titlel);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_10;
    }

    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageJBean) {
            IntelligentTipsMessageJBean tipsMsg = (IntelligentTipsMessageJBean) msg;
            titlel.setText(tipsMsg.getTitle());
        }
    }

}
