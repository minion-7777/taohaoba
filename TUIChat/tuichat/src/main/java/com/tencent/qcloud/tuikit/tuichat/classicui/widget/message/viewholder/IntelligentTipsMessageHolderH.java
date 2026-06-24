package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageHBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.classicui.adapter.KeyValueAdapter;

public class IntelligentTipsMessageHolderH extends MessageContentHolder {

    private final TextView titlel7;
    private final RecyclerView recyclerViewl7;

    public IntelligentTipsMessageHolderH(View itemView) {
        super(itemView);
        titlel7 = itemView.findViewById(R.id.titlel7);
        recyclerViewl7 = itemView.findViewById(R.id.recyclerViewl7);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_8;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageHBean) {
            IntelligentTipsMessageHBean tipsMsg = (IntelligentTipsMessageHBean) msg;

            titlel7.setText(tipsMsg.getTitle());

            KeyValueAdapter adapter = new KeyValueAdapter(tipsMsg.getContentJson());
            recyclerViewl7.setLayoutManager(new LinearLayoutManager(getRecyclerView().getContext()));
            recyclerViewl7.setAdapter(adapter);

        }
    }

}
