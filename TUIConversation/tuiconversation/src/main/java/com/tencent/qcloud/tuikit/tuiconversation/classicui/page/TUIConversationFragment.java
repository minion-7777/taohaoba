package com.tencent.qcloud.tuikit.tuiconversation.classicui.page;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.interfaces.TUIExtensionEventListener;
import com.tencent.qcloud.tuicore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.tuicore.util.ScreenUtil;
import com.tencent.qcloud.tuikit.timcommon.component.action.PopActionClickListener;
import com.tencent.qcloud.tuikit.timcommon.component.action.PopDialogAdapter;
import com.tencent.qcloud.tuikit.timcommon.component.action.PopMenuAction;
import com.tencent.qcloud.tuikit.timcommon.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tuikit.timcommon.util.LayoutUtil;
import com.tencent.qcloud.tuikit.tuiconversation.R;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationGroupBean;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;
import com.tencent.qcloud.tuikit.tuiconversation.classicui.interfaces.OnConversationAdapterListener;
import com.tencent.qcloud.tuikit.tuiconversation.classicui.util.TUIConversationUtils;
import com.tencent.qcloud.tuikit.tuiconversation.classicui.widget.ConversationLayout;
import com.tencent.qcloud.tuikit.tuiconversation.commonutil.ConversationUtils;
import com.tencent.qcloud.tuikit.tuiconversation.commonutil.TUIConversationLog;
import com.tencent.qcloud.tuikit.tuiconversation.config.classicui.TUIConversationConfigClassic;
import com.tencent.qcloud.tuikit.tuiconversation.presenter.ConversationPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.utils.Routes;

public class TUIConversationFragment extends BaseFragment {
    private static final String TAG = TUIConversationFragment.class.getSimpleName();

    private View mBaseView;
    private ConversationLayout mConversationLayout;
    private ListView mConversationPopList;
    private PopDialogAdapter mConversationPopAdapter;
    private PopupWindow mConversationPopWindow;
    private List<PopMenuAction> mConversationPopActions = new ArrayList<>();

    private ConversationPresenter presenter;
    private RelativeLayout layout_msg_title;
    private TextView conversationAll;
    private TextView conversationSingle;
    private TextView conversationGroup;
    private int type;
    private TextView tvNoPermission;
    private ImageView ivPushSetting;
    private ImageView ivMsgCleanAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TUIConversationLog.d(TAG, "onCreateView");
        mBaseView = inflater.inflate(R.layout.conversation_fragment, container, false);
        initView();
        return mBaseView;
    }

    private boolean hasNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return ContextCompat.checkSelfPermission(
                    getContext(),
                    Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;
        }
        // Android 13以下默认有权限
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setFocus(true);
        TUIConversationLog.d(TAG, "onResume");
        if (isLogin()) {
            mmkv.encode("notification_permission", hasNotificationPermission());
        }
        tvNoPermission.setVisibility(mmkv.decodeBool("notification_permission") ? GONE : VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.setFocus(false);
        TUIConversationLog.d(TAG, "onPause");
    }

    public static TUIConversationFragment newInstance() {
        TUIConversationFragment fragment = new TUIConversationFragment();
        return fragment;
    }

    private void initView() {
        
        mConversationLayout = mBaseView.findViewById(R.id.conversation_layout);
        layout_msg_title = mBaseView.findViewById(R.id.layout_msg_title);
        conversationAll = mBaseView.findViewById(R.id.conversation_all);
        conversationSingle = mBaseView.findViewById(R.id.conversation_single);
        conversationGroup = mBaseView.findViewById(R.id.conversation_group);
        tvNoPermission = mBaseView.findViewById(R.id.tvNoPermission);
        ivPushSetting = mBaseView.findViewById(R.id.iv_push_setting);
        ivMsgCleanAll = mBaseView.findViewById(R.id.iv_msg_clean_all);

        adjustToolbarForStatusBar(layout_msg_title);

        presenter = new ConversationPresenter();
        presenter.setConversationListener();
        presenter.setShowType(ConversationPresenter.SHOW_TYPE_CONVERSATION_LIST_WITH_FOLD);
        presenter.setConversationGroupType(ConversationGroupBean.CONVERSATION_GROUP_TYPE_DEFAULT);
        mConversationLayout.setPresenter(presenter);

        mConversationLayout.initDefault(type);
        updateConversationViews(type);

        mConversationLayout.getConversationList().setOnConversationAdapterListener(new OnConversationAdapterListener() {
            @Override
            public void onItemClick(View view, int viewType, ConversationInfo conversationInfo) {
                
                if (conversationInfo.isMarkFold()) {
                    mConversationLayout.clearUnreadStatusOfFoldItem();
                    startFoldedConversationActivity();
                } else {
                    TUIConversationUtils.startChatActivity(conversationInfo);
                }
            }

            @Override
            public void onItemLongClick(View view, ConversationInfo conversationInfo) {
                showItemPopMenu(view, conversationInfo);
            }

            @Override
            public void onConversationChanged(List<ConversationInfo> dataSource) {
                if (dataSource == null) {
                    return;
                }
                ConversationInfo conversationInfo = dataSource.get(0);
                if (conversationInfo == null) {
                    return;
                }
            }
        });

        restoreConversationItemBackground();

        conversationAll.setOnClickListener(v->{
            if (type == 0)return;
            type = 0;
            mConversationLayout.initDefault(type);
            updateConversationViews(type);
        });

        conversationSingle.setOnClickListener(v->{
            if (type == 1)return;
            type = 1;
            mConversationLayout.initDefault(type);
            updateConversationViews(type);
        });

        conversationGroup.setOnClickListener(v->{
            if (type == 2)return;
            type = 2;
            mConversationLayout.initDefault(type);
            updateConversationViews(type);
        });

        tvNoPermission.setOnClickListener(v -> {
            // Android 8.0及以上
            // 跳转到当前应用的通知设置
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getActivity().getPackageName());
            startActivity(intent);
        });

        ivMsgCleanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                V2TIMManager.getConversationManager().cleanConversationUnreadMessageCount("", 0, 0, new V2TIMCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("imsdk", "success");
                    }
                    @Override
                    public void onError(int code, String desc) {
                        Log.i("imsdk", "failure, code:" + code + ", desc:" + desc);
                    }
                });

            }
        });

        ivPushSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(Routes.Main.PUSH_SETTING).navigation();
            }
        });
    }

    /**
     * 根据会话类型更新视图状态
     * @param type 会话类型，0 表示全部，1 表示单聊，2 表示群聊
     */
    private void updateConversationViews(int type) {
        int selectedBg = R.drawable.shape_radius15_ffd497_fafbf5;
        int selectedTextColor = R.color.color_402802;
        int unselectedTextColor = R.color.color_D6D6D6;

        setViewStyle(conversationAll, type == 0, selectedBg, selectedTextColor, unselectedTextColor);
        setViewStyle(conversationSingle, type == 1, selectedBg, selectedTextColor, unselectedTextColor);
        setViewStyle(conversationGroup, type == 2, selectedBg, selectedTextColor, unselectedTextColor);
    }

    /**
     * 设置视图的背景和文字颜色
     * @param view 要设置样式的视图
     * @param isSelected 是否被选中
     * @param selectedBg 选中时的背景资源 ID
     * @param selectedTextColor 选中时的文字颜色资源 ID
     * @param unselectedTextColor 未选中时的文字颜色资源 ID
     */
    private void setViewStyle(TextView view, boolean isSelected, int selectedBg, int selectedTextColor, int unselectedTextColor) {
        if (isSelected) {
            view.setBackground(AppCompatResources.getDrawable(requireContext(), selectedBg));
            view.setTextColor(ContextCompat.getColor(requireContext(), selectedTextColor));
        } else {
            view.setBackground(null);
            view.setTextColor(ContextCompat.getColor(requireContext(), unselectedTextColor));
        }
    }

    public void restoreConversationItemBackground() {
        if (mConversationLayout.getConversationList().getAdapter() != null && mConversationLayout.getConversationList().getAdapter().isClick()) {
            mConversationLayout.getConversationList().getAdapter().setClick(false);
            mConversationLayout.getConversationList().getAdapter().notifyItemChanged(
                mConversationLayout.getConversationList().getAdapter().getCurrentPosition());
        }
    }

    private PopMenuAction getMarkUnreadPopMenuAction(boolean markUnread) {
        PopMenuAction markUnreadAction = new PopMenuAction();
        markUnreadAction.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                mConversationLayout.markConversationUnread((ConversationInfo) data, markUnread);
            }
        });
        if (markUnread) {
            markUnreadAction.setActionName(getResources().getString(R.string.mark_unread));
        } else {
            markUnreadAction.setActionName(getResources().getString(R.string.mark_read));
        }
        markUnreadAction.setWeight(900);
        return markUnreadAction;
    }

    private PopMenuAction getDeletePopMenuAction() {
        PopMenuAction action = new PopMenuAction();
        action.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                TUIKitDialog tipsDialog =
                    new TUIKitDialog(getContext())
                        .builder()
                        .setCancelable(true)
                        .setCancelOutside(true)
                        .setTitle(getContext().getString(R.string.conversation_delete_tips))
                        .setDialogWidth(0.75f)
                        .setPositiveButton(getContext().getString(com.tencent.qcloud.tuicore.R.string.sure),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mConversationLayout.deleteConversation((ConversationInfo) data);
                                }
                            })
                        .setNegativeButton(getContext().getString(com.tencent.qcloud.tuicore.R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        });
                tipsDialog.show();
            }
        });
        action.setActionName(getResources().getString(R.string.chat_delete));
        action.setWeight(700);
        return action;
    }

    private void showItemPopMenu(View view, final ConversationInfo conversationInfo) {
        mConversationPopActions.clear();

        PopMenuAction hideAction = new PopMenuAction();
        hideAction.setActionName(getResources().getString(R.string.not_display));
        hideAction.setWeight(800);
        hideAction.setActionClickListener(new PopActionClickListener() {
            @Override
            public void onActionClick(int index, Object data) {
                ConversationInfo conversationInfo = (ConversationInfo) data;
                if (conversationInfo.isMarkFold()) {
                    mConversationLayout.hideFoldedItem(true);
                } else {
                    mConversationLayout.markConversationHidden(conversationInfo);
                }
            }
        });
        mConversationPopActions.add(hideAction);

        PopMenuAction markUnreadAction = null;
        PopMenuAction deleteAction = null;

        if (!conversationInfo.isMarkFold()) {
            if (conversationInfo.getUnRead() > 0) {
                markUnreadAction = getMarkUnreadPopMenuAction(false);
            } else {
                if (conversationInfo.isMarkUnread()) {
                    markUnreadAction = getMarkUnreadPopMenuAction(false);
                } else {
                    markUnreadAction = getMarkUnreadPopMenuAction(true);
                }
            }

            deleteAction = getDeletePopMenuAction();
            mConversationPopActions.add(markUnreadAction);
            mConversationPopActions.add(deleteAction);
            mConversationPopActions.addAll(addMoreConversationAction(conversationInfo));
        }

        View itemPop = LayoutInflater.from(getActivity()).inflate(R.layout.conversation_pop_menu_layout, null);
        mConversationPopList = itemPop.findViewById(R.id.pop_menu_list);
        mConversationPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuAction action = mConversationPopActions.get(position);
                if (action.getActionClickListener() != null) {
                    action.getActionClickListener().onActionClick(position, conversationInfo);
                }
                mConversationPopWindow.dismiss();
                restoreConversationItemBackground();
            }
        });
        TUIConversationConfigClassic.ConversationMenuItemDataSource dataSource = TUIConversationConfigClassic.getConversationMenuItemDataSource();
        if (dataSource != null) {
            List<Integer> excludeList = dataSource.conversationShouldHideItemsInMoreMenu(conversationInfo);
            if (excludeList != null && !excludeList.isEmpty()) {
                if (excludeList.contains(TUIConversationConfigClassic.HIDE)) {
                    mConversationPopActions.remove(hideAction);
                }
                if (excludeList.contains(TUIConversationConfigClassic.DELETE)) {
                    mConversationPopActions.remove(deleteAction);
                }
            }
            List<PopMenuAction> conversationPopMenuItems = dataSource.conversationShouldAddNewItemsToMoreMenu(conversationInfo);
            if (conversationPopMenuItems != null && !conversationPopMenuItems.isEmpty()) {
                mConversationPopActions.addAll(conversationPopMenuItems);
            }
        }
        Collections.sort(mConversationPopActions, new Comparator<PopMenuAction>() {
            @Override
            public int compare(PopMenuAction o1, PopMenuAction o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });
        mConversationPopAdapter = new PopDialogAdapter();
        mConversationPopList.setAdapter(mConversationPopAdapter);
        mConversationPopAdapter.setDataSource(mConversationPopActions);
        mConversationPopWindow = new PopupWindow(itemPop, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mConversationPopWindow.setBackgroundDrawable(new ColorDrawable());
        mConversationPopWindow.setOutsideTouchable(true);
        int width = ConversationUtils.getListUnspecifiedWidth(mConversationPopAdapter, mConversationPopList);
        mConversationPopWindow.setWidth(width);
        mConversationPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                restoreConversationItemBackground();
            }
        });
        int x = view.getWidth() / 2;
        if (LayoutUtil.isRTL()) {
            x = -x;
        }
        int y = -view.getHeight() / 3;
        int popHeight = ScreenUtil.dip2px(45) * 3;
        if (y + popHeight + view.getY() + view.getHeight() > mConversationLayout.getBottom()) {
            y = y - popHeight;
        }
        mConversationPopWindow.showAsDropDown(view, x, y, Gravity.TOP | Gravity.START);
    }

    private void startFoldedConversationActivity() {
        Intent intent = new Intent(getActivity(), TUIFoldedConversationActivity.class);
        startActivity(intent);
    }

    protected List<PopMenuAction> addMoreConversationAction(ConversationInfo conversationInfo) {
        List<PopMenuAction> settingsList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIConversation.CONTEXT, getContext());
        param.put(TUIConstants.TUIConversation.KEY_CONVERSATION_INFO, conversationInfo);
        List<TUIExtensionInfo> extensionList = TUICore.getExtensionList(TUIConstants.TUIConversation.Extension.ConversationPopMenu.CLASSIC_EXTENSION_ID, param);
        for (TUIExtensionInfo extensionInfo : extensionList) {
            String text = extensionInfo.getText();
            if (!TextUtils.isEmpty(text)) {
                PopMenuAction action = new PopMenuAction();
                action.setActionClickListener(new PopActionClickListener() {
                    @Override
                    public void onActionClick(int index, Object data) {
                        TUIExtensionEventListener listener = extensionInfo.getExtensionListener();
                        if (listener != null) {
                            listener.onClicked(null);
                        }
                    }
                });
                action.setWeight(extensionInfo.getWeight());
                action.setActionName(text);
                settingsList.add(action);
            }
        }

        return settingsList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TUIConversationLog.d(TAG, "onDestroy");
        if (presenter != null) {
            presenter.destroy();
            presenter = null;
        }
    }
}
