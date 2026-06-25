package io.openim.android.taohaoba.ui.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import android.text.TextUtils;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsSettingBean;
import io.openim.android.taohaoba.bean.RapidRecoveryBean;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityRapidRecoveryBinding;
import io.openim.android.taohaoba.vm.home.GoodsSettingVM;
import io.openim.android.taohaoba.widgets.CircleImageView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 快速回收
 */
public class RapidRecoveryActivity extends BaseActivity<GoodsSettingVM, ActivityRapidRecoveryBinding> implements GoodsSettingVM.ViewAction{
    private List<RapidRecoveryBean> beanList = new ArrayList<>();
    private int patternId;
    private int categoryId;
    private int gameId;
    private BaseQuickAdapter quickAdapter;
    private GoodsSettingBean settingBean;
    private String userId;
    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(GoodsSettingVM.class);
        bindViewDataBinding(ActivityRapidRecoveryBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initAdapter();
        initView();
        initListener();
    }

    private void initView() {
        userId = mmkv.decodeString(PreferencesKey.userId, "");

        patternId = getIntent().getIntExtra("patternId", 0);
        categoryId = getIntent().getIntExtra("categoryId", 0);
        gameId = getIntent().getIntExtra("gameId", 0);
        gameName = getIntent().getStringExtra("gameName");
        view.toolbar.setTitleText(gameName +"-选择号商");

    }

    private void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //获取游戏配置
        vm.getGoodsSettingBeanMutableLiveData().observe(this, it->{
            dismissLoadingDialog();
            settingBean = it;
            if (settingBean.getUserList() != null) {
                beanList.addAll(settingBean.getUserList());
                quickAdapter.notifyDataSetChanged();
            }
        });

        showLoadingDialog();
        vm.getGoodsSetting(categoryId, gameId, patternId);

        vm.getLiveDataCreateChatGroupBean().observe(this, it -> {
            dismissLoadingDialog();
            if (it == null || TextUtils.isEmpty(it.getIm_group_id())) {
                showToast("群聊创建失败，请稍后再试");
                return;
            }
            Bundle param = new Bundle();
            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_GROUP);
            param.putString(TUIConstants.TUIChat.CHAT_ID, it.getIm_group_id());
            if (it.getIs_first() == 1) {
                param.putString(Constants.SENDMSG, "我想售卖"+gameName+"游戏账号");
            }
            TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
            finish();
        });



    }

    private void initAdapter(){
        quickAdapter = new BaseQuickAdapter(R.layout.item_rapid_recovery, beanList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                RapidRecoveryBean bean = (RapidRecoveryBean) o;

                CircleImageView iv_head = baseViewHolder.getView(R.id.iv_head);
                TextView tv_but = baseViewHolder.getView(R.id.tv_but);
                tv_but.setBackground(getDrawable(userId.equalsIgnoreCase(String.valueOf(bean.getUser_id())) ? R.drawable.shape_radius22_5_9a9a9a : R.drawable.shape_radius22_5_ffd497_fafbf5));
                tv_but.setEnabled(!userId.equalsIgnoreCase(String.valueOf(bean.getUser_id())));
                baseViewHolder.setText(R.id.tv_name, bean.getUser().getNickname());
                Glide.with(baseViewHolder.itemView.getContext())
                        .load(bean.getUser().getAvatar())
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_profile_picture)// 加载中的占位图
                                .error(R.mipmap.ic_profile_picture)// 加载失败的占位图
                                .centerCrop()// 图片裁剪方式
                        )
                        .into(iv_head);

                tv_but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showLoadingDialog();
                        vm.createChatGroup(1, bean.getGame_id(), bean.getUser_id(), 0, 1, 2);
                    }
                });
            }
        };

        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        view.rcRecycler.setAdapter(quickAdapter);
    }


    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        toast(msg);
    }


}
