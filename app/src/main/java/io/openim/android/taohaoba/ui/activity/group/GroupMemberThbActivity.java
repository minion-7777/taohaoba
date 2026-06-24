package io.openim.android.taohaoba.ui.activity.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.bean.ImGroupInfoBean;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.databinding.ActivityGroupMemberBinding;
//import io.openim.android.taohaoba.ui.adapter.GroupMemberThbAdapter;
import io.openim.android.taohaoba.ui.adapter.GroupMemberThbAdapter;
import io.openim.android.taohaoba.vm.me.GroupMemberVMThb;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 群成员
 */

@Route(path = Routes.Main.GROUP_MEMBER_THB)
public class GroupMemberThbActivity extends BaseActivity<GroupMemberVMThb, ActivityGroupMemberBinding> implements GroupMemberVMThb.ViewAction {
    private List<ImGroupInfoBean.ImGroupDTO.MembersDTO> list = new ArrayList<>();
    private GroupMemberThbAdapter adapter;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        bindVM(GroupMemberVMThb.class);
        bindViewDataBinding(ActivityGroupMemberBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        vm.groupId =getIntent().getStringExtra("groupId");
        vm.getSuperGroupMemberList();

        view.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        vm.getLiveDataImGroupInfoBean().observe(this, it -> {
            if(it != null){
                view.tvGroupName.setText(it.getImGroup().getGroup_name());
                view.tvGroupId.setText(it.getImGroup().getIm_group_id());
                view.tvTime.setText(it.getImGroup().getCreated_time());

                if (it.getImGroup().getMembers() != null) {
                    list.addAll(it.getImGroup().getMembers());
                    adapter = new GroupMemberThbAdapter(list);
                    view.recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.setOnClickListener(new GroupMemberThbAdapter.OnClickListener() {
                        @Override
                        public void onChatWithCustomerServicePrivately(String id) {
                            Intent intent = new Intent(getBaseContext(), TUIC2CChatActivity.class);
                            intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
                            intent.putExtra(TUIConstants.TUIChat.CHAT_ID, id);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                }
            }
        });


    }

    @Override
    public void err(String msg) {

    }

}
