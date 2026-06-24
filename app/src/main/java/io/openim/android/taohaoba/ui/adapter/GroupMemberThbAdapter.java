package io.openim.android.taohaoba.ui.adapter;

import static android.view.View.GONE;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tencent.qcloud.tuikit.tuichat.bean.ImGroupInfoBean;

import java.util.List;

import io.openim.android.ouicore.widget.AvatarImage;
import io.openim.android.taohaoba.R;

public class GroupMemberThbAdapter extends BaseQuickAdapter<ImGroupInfoBean.ImGroupDTO.MembersDTO, BaseViewHolder> {

    public GroupMemberThbAdapter(List<ImGroupInfoBean.ImGroupDTO.MembersDTO> data) {
        super(R.layout.item_group_member_thb, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ImGroupInfoBean.ImGroupDTO.MembersDTO item) {
        AvatarImage avatar = baseViewHolder.getView(R.id.avatar);
        TextView identity = baseViewHolder.getView(R.id.identity);
        TextView nickName = baseViewHolder.getView(R.id.nickName);
        TextView action = baseViewHolder.getView(R.id.action);

        if (item.getRole_type() == 3 || item.getRole_type() == 4) {
            action.setVisibility(View.VISIBLE);
            action.setOnClickListener(v -> {
                onClickListener.onChatWithCustomerServicePrivately(item.getUser_name());
            });
        }

        if (item.getRole_type() == 1) {
            identity.setText("卖家");
            identity.setBackground(baseViewHolder.itemView.getContext().getDrawable(R.drawable.shape_radius2_5_3798ea));
        } else if (item.getRole_type() == 2) {
            identity.setText("买家");
            identity.setBackground(baseViewHolder.itemView.getContext().getDrawable(R.drawable.shape_radius2_5_0eb272));
        } else if (item.getRole_type() == 3 || item.getRole_type() == 4) {
            identity.setText("官方客服");
            identity.setBackground(baseViewHolder.itemView.getContext().getDrawable(R.drawable.shape_radius2_5_fe5555));
        }else {
            identity.setVisibility(GONE);
            nickName.setText("用户");
        }

        avatar.load(item.getAvatar());
//        nickName.setText(item.getNickname());
    }

    public interface OnClickListener {
        void onChatWithCustomerServicePrivately(String id); // 私聊客服
    }

    private OnClickListener onClickListener;

    // 设置回调监听器
    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }
}