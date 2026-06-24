package com.tencent.qcloud.tuikit.tuichat.classicui.widget;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.getDrawable;
import static com.tencent.qcloud.tuikit.tuichat.util.PriceFormatUtils.formatPrice;
import static com.tencent.qcloud.tuikit.tuichat.util.StringArrayUtil.convertToArray;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.tencent.imsdk.v2.V2TIMGroupAtInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.mmkv.MMKV;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.TUIThemeManager;
import com.tencent.qcloud.tuicore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.SelectionHelper;
import com.tencent.qcloud.tuikit.timcommon.component.TitleBarLayout;
import com.tencent.qcloud.tuikit.timcommon.component.UnreadCountTextView;
import com.tencent.qcloud.tuikit.timcommon.component.dialog.TUIKitDialog;
import com.tencent.qcloud.tuikit.timcommon.component.interfaces.ITitleBarLayout;
import com.tencent.qcloud.tuikit.timcommon.component.interfaces.IUIKitCallback;
import com.tencent.qcloud.tuikit.timcommon.interfaces.ChatInputMoreListener;
import com.tencent.qcloud.tuikit.timcommon.interfaces.OnChatPopActionClickListener;
import com.tencent.qcloud.tuikit.timcommon.util.TIMCommonUtil;
import com.tencent.qcloud.tuikit.timcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.AuthInfoBean;
import com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.GroupApplyInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.ImGroupInfoBean;
import com.tencent.qcloud.tuikit.tuichat.bean.MessageTyping;
import com.tencent.qcloud.tuikit.tuichat.bean.ReplyPreviewBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.CallingMessageBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.ReplyMessageBean;
import com.tencent.qcloud.tuikit.tuichat.classicui.component.noticelayout.NoticeLayout;
import com.tencent.qcloud.tuikit.tuichat.classicui.interfaces.IChatLayout;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIBaseChatFragment;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.input.InputView;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.input.ShortcutView;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.MessageAdapter;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.MessageRecyclerView;
import com.tencent.qcloud.tuikit.tuichat.component.audio.AudioPlayer;
import com.tencent.qcloud.tuikit.tuichat.component.audio.AudioRecorder;
import com.tencent.qcloud.tuikit.tuichat.component.pinned.GroupPinnedView;
import com.tencent.qcloud.tuikit.tuichat.component.progress.ProgressPresenter;
import com.tencent.qcloud.tuikit.tuichat.config.ShortcutMenuConfig;
import com.tencent.qcloud.tuikit.tuichat.config.TUIChatConfigs;
import com.tencent.qcloud.tuikit.tuichat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.tuichat.dialog.CommonDialog;
import com.tencent.qcloud.tuikit.tuichat.dialog.RealNameAuthenticationDialog;
import com.tencent.qcloud.tuikit.tuichat.interfaces.OnEmptySpaceClickListener;
import com.tencent.qcloud.tuikit.tuichat.interfaces.OnGestureScrollListener;
import com.tencent.qcloud.tuikit.tuichat.interfaces.TotalUnreadCountListener;
import com.tencent.qcloud.tuikit.tuichat.presenter.C2CChatPresenter;
import com.tencent.qcloud.tuikit.tuichat.presenter.ChatPresenter;
import com.tencent.qcloud.tuikit.tuichat.presenter.GroupChatPresenter;
import com.tencent.qcloud.tuikit.tuichat.repository.ThbApiService;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.tuichat.util.RSA;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.utils.Routes;

public class ChatView extends LinearLayout implements IChatLayout {
    private static final String TAG = "ChatView";

    // Limit the number of messages forwarded one by one
    private static final int FORWARD_MSG_NUM_LIMIT = 30;

    protected MessageAdapter mAdapter;
    private ForwardSelectActivityListener mForwardSelectActivityListener;
    private TotalUnreadCountListener unreadCountListener;

    private AnimationDrawable mVolumeAnim;
    private Runnable mTypingRunnable = null;
    private ChatInfo mChatInfo;
    public ChatPresenter.TypingListener mTypingListener = new ChatPresenter.TypingListener() {
        @Override
        public void onTyping(int status) {
            if (!TUIChatConfigClassic.isEnableTypingIndicator()) {
                return;
            }

            if (mChatInfo == null) {
                return;
            }

            TUIChatLog.d(TAG, "mTypingListener status= " + status);
            String oldTitle = mChatInfo.getChatName();
            if (status == 1) {
                getTitleBar().getMiddleTitle().setText(R.string.typing);

                if (mTypingRunnable == null) {
                    mTypingRunnable = new Runnable() {
                        @Override
                        public void run() {
                            getTitleBar().getMiddleTitle().setText(oldTitle);
                        }
                    };
                }
                getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
                getTitleBar().getMiddleTitle().postDelayed(mTypingRunnable, TUIChatConstants.TYPING_PARSE_MESSAGE_INTERVAL * 1000);
            } else if (status == 0) {
                getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
                getTitleBar().getMiddleTitle().setText(oldTitle);
            } else {
                TUIChatLog.e(TAG, "parseTypingMessage error status =" + status);
            }
        }
    };

    protected FrameLayout topExtensionLayout;
    protected GroupPinnedView groupPinnedView;
    protected FrameLayout mCustomView;
    protected NoticeLayout mGroupApplyLayout;
    protected View mRecordingGroup;
    protected ImageView mRecordingIcon;
    protected TextView mRecordingTips;
    protected TextView recordCountDownTv;
    private TitleBarLayout mTitleBar;
    private ImageView chatBackgroundView;
    private MessageRecyclerView mMessageRecyclerView;
    private InputView mInputView;
    private View flInputFloatLayout;
    private ViewGroup shortcutContainer;
    private NoticeLayout mNoticeLayout;
    private LinearLayout mJumpMessageLayout;
    private ImageView mArrowImageView;
    private TextView mJumpMessageTextView;
    private boolean mJumpNewMessageShow;
    private boolean mJumpGroupAtInfoShow;
    private boolean mClickLastMessageShow;
    private Timer recordCountDownTimer;

    private LinearLayout mForwardLayout;
    private View mForwardOneButton;
    private View mForwardMergeButton;
    private View mDeleteButton;
    private long lastTypingTime = 0;
    private boolean isSupportTyping = false;

    private ChatPresenter presenter;
    private int scrollDirection = 0;
    private ChatInputMoreListener chatInputMoreListener;
    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener; // 添加布局监听器成员变量
    private View root_layout;
    private String im_buyer_id = "";
    private static ImGroupInfoBean infoBean;
    private ConstraintLayout ctop;
    private RoundImageView ivImage;
    private TextView tvTag;
    private TextView tvImmediatePayment;
    private TextView tvConfirmVerificationNumber;
    private TextView tvConfirmReceipt;
    private TextView tvSendOutGoods;
    private TextView tvHint;
    private TextView tvPrice;
    private static int orderStatus, pattern, goodsId;
    private V2TIMSimpleMsgListener v2TIMSimpleMsgListener;
    private int intelligentType;

    public static int getOrderStatus() {
        return orderStatus;
    }

    public static int getPattern() {
        return pattern;
    }
    public static int getGoodsId() {
        return goodsId;
    }

    public static ImGroupInfoBean getImGroupInfoBean() {
        return infoBean;
    }

    public ChatView(Context context) {
        super(context);
        initViews();
    }

    public ChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    //记录原始窗口高度
    private int previousKeyboardHeight = 0;

    private void initViews() {
        inflate(getContext(), R.layout.tuichat_chat_layout, this);

        mTitleBar = findViewById(R.id.chat_title_bar);
        root_layout = findViewById(R.id.root_layout);
        chatBackgroundView = findViewById(R.id.chat_background_view);
        mMessageRecyclerView = findViewById(R.id.chat_message_layout);
        mInputView = findViewById(R.id.chat_input_layout);
        mInputView.setChatLayout(this);
        boolean enableMainPageInputBar = TUIChatConfigClassic.isShowInputBar();
        mInputView.setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
        mRecordingGroup = findViewById(R.id.voice_recording_view);
        mRecordingIcon = findViewById(R.id.recording_icon);
        mRecordingTips = findViewById(R.id.recording_tips);
        recordCountDownTv = findViewById(R.id.voice_count_down_tv);
        mGroupApplyLayout = findViewById(R.id.chat_group_apply_layout);
        mNoticeLayout = findViewById(R.id.chat_notice_layout);
        mCustomView = findViewById(R.id.custom_layout);
        mCustomView.setVisibility(GONE);
        topExtensionLayout = findViewById(R.id.chat_top_extension_layout);
        topExtensionLayout.setVisibility(GONE);

        groupPinnedView = findViewById(R.id.group_pinned_message_view);

        flInputFloatLayout = findViewById(R.id.fl_input_float);
        shortcutContainer = findViewById(R.id.shortcut_container);

        mForwardLayout = findViewById(R.id.forward_layout);
        mForwardOneButton = findViewById(R.id.forward_one_by_one_button);
        mForwardMergeButton = findViewById(R.id.forward_merge_button);
        mDeleteButton = findViewById(R.id.delete_button);

        mJumpMessageLayout = findViewById(R.id.jump_message_layout);
        mJumpMessageTextView = findViewById(R.id.jump_message_content);
        mArrowImageView = findViewById(R.id.arrow_icon);


        ctop = findViewById(R.id.ctop);
        ivImage = findViewById(R.id.iv_image);
        tvTag = findViewById(R.id.tv_tag);
        tvImmediatePayment = findViewById(R.id.tv_immediatePayment);
        tvConfirmVerificationNumber = findViewById(R.id.tv_confirmVerificationNumber);
        tvConfirmReceipt = findViewById(R.id.tv_confirmReceipt);
        tvSendOutGoods = findViewById(R.id.tv_sendOutGoods);
        tvHint = findViewById(R.id.tv_hint);
        tvPrice = findViewById(R.id.tv_price);

        mJumpNewMessageShow = false;
        hideJumpMessageLayouts();

        mTitleBar.getMiddleTitle().setEllipsize(TextUtils.TruncateAt.END);
        lastTypingTime = 0;

        // 添加软键盘监听逻辑
        globalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 创建一个 Rect 用于存储当前可见的窗口区域
                Rect r = new Rect();
                // 获取当前窗口可见区域（不含系统装饰栏、键盘等）
                root_layout.getWindowVisibleDisplayFrame(r);

                // 获取当前根布局高度
                int screenHeight = root_layout.getRootView().getHeight();
                // 可见区域与屏幕总高度之差即为软键盘占用的高度（如果有键盘弹出的话）
                int keyboardHeight = screenHeight - r.bottom;

                // 为避免误差，只有当键盘高度大于一定阈值时认为键盘弹出
                if (keyboardHeight > screenHeight * 0.15) {
                    // 键盘弹出状态
                    if (previousKeyboardHeight != keyboardHeight) {
                        previousKeyboardHeight = keyboardHeight;
                        // 调整固定 View 的位置，使其正好位于键盘上方
                        adjustFixedViewPosition(keyboardHeight);
                    }
                } else {
                    // 键盘隐藏状态，则恢复固定 View 到原始位置
                    if (previousKeyboardHeight != 0) {
                        previousKeyboardHeight = 0;
                        resetFixedViewPosition();
                    }
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
    }

    /**
     * 方法名称：adjustFixedViewPosition
     * 功能描述：动态设置固定 View 的底部 margin（或平移），使其位于键盘上方
     * 参数：
     *   - keyboardHeight：当前键盘高度
     */
    private void adjustFixedViewPosition(int keyboardHeight) {
        // 获取固定 View 的当前 LayoutParams
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputView.getLayoutParams();
        // 将固定 View 的 bottom margin 设为键盘高度值（或者稍作偏移，确保和键盘紧密贴合）
        params.bottomMargin = keyboardHeight-40;
        mInputView.setLayoutParams(params);
        // 如果需要动画效果，也可以使用 fixedView.setTranslationY() 平移 View
    }

    /**
     * 方法名称：resetFixedViewPosition
     * 功能描述：恢复固定 View 的原始位置，即底部 margin 还原为 0
     */
    private void resetFixedViewPosition() {
        // 恢复固定 View 的 bottom margin 为 0
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputView.getLayoutParams();
        params.bottomMargin = 0;
        mInputView.setLayoutParams(params);
    }

    public void displayBackToLastMessages() {
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_down_icon));
        mJumpMessageTextView.setText(getContext().getString(R.string.back_to_lastmessage));
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatInfo chatInfo = getChatInfo();
                presenter.locateLastMessage(chatInfo.getId(), chatInfo.getType() == ChatInfo.TYPE_GROUP, new IUIKitCallback<Void>() {
                    @Override
                    public void onSuccess(Void data) {
                        hideJumpMessageLayouts();
                    }

                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        hideJumpMessageLayouts();
                        ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
                    }
                });
                hideJumpMessageLayouts();
                mClickLastMessageShow = true;
            }
        });
    }

    public void displayBackToNewMessages(String messageId, int count) {
        mJumpNewMessageShow = true;
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_down_icon));
        mJumpMessageTextView.setText(String.valueOf(count) + getContext().getString(R.string.back_to_newmessage));
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locateOriginMessage(messageId);
                presenter.markMessageAsRead(mChatInfo);
                mJumpNewMessageShow = false;
                presenter.resetNewMessageCount();
            }
        });
    }

    public void displayBackToAtMessages(V2TIMGroupAtInfo groupAtInfo) {
        mJumpGroupAtInfoShow = true;
        mJumpMessageLayout.setVisibility(VISIBLE);
        mArrowImageView.setBackgroundResource(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_jump_recent_up_icon));
        if (groupAtInfo.getAtType() == V2TIMGroupAtInfo.TIM_AT_ALL) {
            mJumpMessageTextView.setText(getContext().getString(R.string.back_to_atmessage_all));
        } else {
            mJumpMessageTextView.setText(getContext().getString(R.string.back_to_atmessage_me));
        }
        mJumpMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                locateOriginMessageBySeq(groupAtInfo.getSeq());
                hideJumpMessageLayouts();
                mJumpGroupAtInfoShow = false;
            }
        });
    }

    public void hideJumpMessageLayouts() {
        mJumpMessageLayout.setVisibility(GONE);
    }

    public void hideBackToAtMessages() {
        if (mJumpGroupAtInfoShow) {
            mJumpGroupAtInfoShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void initGroupAtInfoLayout() {
        if (mChatInfo != null) {
            List<V2TIMGroupAtInfo> groupAtInfos = mChatInfo.getAtInfoList();
            if (groupAtInfos != null && groupAtInfos.size() > 0) {
                V2TIMGroupAtInfo groupAtInfo = groupAtInfos.get(0);
                if (groupAtInfo != null) {
                    displayBackToAtMessages(groupAtInfo);
                } else {
                    mJumpGroupAtInfoShow = false;
                    hideJumpMessageLayouts();
                }
            } else {
                TUIChatLog.d(TAG, "initGroupAtInfoLayout groupAtInfos == null");
                mJumpGroupAtInfoShow = false;
                hideJumpMessageLayouts();
            }
        } else {
            TUIChatLog.d(TAG, "initGroupAtInfoLayout mChatInfo == null");
            mJumpGroupAtInfoShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void locateOriginMessageBySeq(long seq) {
        presenter.locateMessageBySeq(mChatInfo.getId(), seq, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                hideJumpMessageLayouts();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                hideJumpMessageLayouts();
                ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            }
        });
    }

    private void locateOriginMessage(String originMsgId) {
        if (TextUtils.isEmpty(originMsgId)) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            return;
        }
        presenter.locateMessage(originMsgId, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {
                hideJumpMessageLayouts();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                hideJumpMessageLayouts();
                ToastUtil.toastShortMessage(getContext().getString(R.string.locate_origin_msg_failed_tip));
            }
        });
        hideJumpMessageLayouts();
    }

    public void setPresenter(ChatPresenter presenter) {
        this.presenter = presenter;
        mMessageRecyclerView.setPresenter(presenter);
        mInputView.setPresenter(presenter);
        presenter.setMessageListAdapter(mAdapter);
        mAdapter.setPresenter(presenter);
        presenter.setMessageRecycleView(mMessageRecyclerView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            SelectionHelper.resetSelected();
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setChatInfo(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        if (chatInfo == null) {
            return;
        }
        mInputView.setChatInfo(chatInfo);
        setChatName();
        setChatHandler();

        if (!TUIChatUtils.isC2CChat(chatInfo.getType())) {
            loadApplyList();
            mGroupApplyLayout.setOnNoticeClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(TUIChatConstants.GROUP_ID, chatInfo.getId());
                    TUICore.startActivity(getContext(), "GroupApplyManagerActivity", bundle);
                }
            });
        }

        mMessageRecyclerView.setOnGestureScrollListener(new OnGestureScrollListener() {
            @Override
            public void onScroll(MotionEvent m1, MotionEvent m2, float distanceX, float distanceY) {
                if (distanceY < 0) {
                    scrollDirection = -1;
                } else if (distanceY > 0) {
                    scrollDirection = 1;
                } else {
                    scrollDirection = 0;
                }
            }
        });

        mMessageRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getMessageLayout().getLayoutManager();
                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                sendMsgReadReceipt(firstVisiblePosition, lastVisiblePosition);
                notifyMessageDisplayed(firstVisiblePosition, lastVisiblePosition);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                SelectionHelper.resetSelected();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (scrollDirection == -1) {
                        if (!mMessageRecyclerView.canScrollVertically(-1)) {
                            mAdapter.showLoading();
                            loadMessages(TUIChatConstants.GET_MESSAGE_FORWARD);
                        }
                    } else if (scrollDirection == 1) {
                        if (!mMessageRecyclerView.canScrollVertically(1)) {
                            loadMessages(TUIChatConstants.GET_MESSAGE_BACKWARD);
                            displayBackToLastMessage(false);
                            displayBackToNewMessage(false, "", 0);
                            presenter.resetCurrentChatUnreadCount();
                        }
                    }
                    scrollDirection = 0;

                    if (mMessageRecyclerView.isDisplayJumpMessageLayout()) {
                        displayBackToLastMessage(true);
                    } else {
                        displayBackToLastMessage(false);
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    hideBackToAtMessages();
                }
            }
        });

        loadPinnedMessage();
        loadMessages(
            chatInfo.getLocateMessage(), chatInfo.getLocateMessage() == null ? TUIChatConstants.GET_MESSAGE_FORWARD : TUIChatConstants.GET_MESSAGE_TWO_WAY);
        setTotalUnread();
        initExtension();
        initChatbot();
        setShortcutView();

        if (mChatInfo.getType() == ChatInfo.TYPE_GROUP) {
            v2TIMSimpleMsgListener = new V2TIMSimpleMsgListener() {
                @Override
                public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                    super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
                    Log.i(TAG, "onRecvGroupCustomMessage: " + msgID + "，群ID：" + groupID + "，发送者：" + sender.getNickName() + "，自定义数据：" + new String(customData));
                    JSONObject jsonObject =  JSONObject.parseObject(new String(customData));
                    intelligentType = jsonObject.getIntValue("intelligentType");
                    //intelligentType == 10 中介担保修改用户身份（买家/买家）
                    if (intelligentType == 5 || intelligentType == 1 || intelligentType == 9 || intelligentType == 8 || intelligentType == 10) {
                        getGroupInfoByImGroupId();
                    }
                }
            };
            V2TIMManager.getInstance().addSimpleMsgListener(v2TIMSimpleMsgListener);
            getGroupInfoByImGroupId();
        }
    }

    private void initExtension() {
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.CHAT_ID, mChatInfo.getId());
        param.put(TUIConstants.TUIChat.CHAT_TYPE, mChatInfo.getType());
        List<TUIExtensionInfo> extensionInfoList = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.ChatView.GET_CONFIG_PARAMS, param);
        if (extensionInfoList != null && extensionInfoList.size() > 0) {
            TUIExtensionInfo extensionInfo = extensionInfoList.get(0);
            if (extensionInfo.getData() != null) {
                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.MESSAGE_NEED_READ_RECEIPT)) {
                    boolean needReadReceipt = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.MESSAGE_NEED_READ_RECEIPT);
                    mChatInfo.setNeedReadReceipt(needReadReceipt);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_VIDEO_CALL)) {
                    boolean enableVideoCall = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_VIDEO_CALL);
                    mChatInfo.setEnableVideoCall(enableVideoCall);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_AUDIO_CALL)) {
                    boolean enableAudioCall = (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_AUDIO_CALL);
                    mChatInfo.setEnableAudioCall(enableAudioCall);
                }

                if (extensionInfo.getData().containsKey(TUIConstants.TUIChat.Extension.ChatView.ENABLE_CUSTOM_HELLO_MESSAGE)) {
                    boolean enableCustomHelloMessage =
                        (Boolean) extensionInfo.getData().get(TUIConstants.TUIChat.Extension.ChatView.ENABLE_CUSTOM_HELLO_MESSAGE);
                    mChatInfo.setEnableCustomHelloMessage(enableCustomHelloMessage);
                }
            }
        }

        HashMap<String, Object> raiseExtensionParam = new HashMap<>();
        raiseExtensionParam.put(TUIChatConstants.CHAT_INFO, mChatInfo);
        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.InputViewFloatLayer.CLASSIC_EXTENSION_ID, flInputFloatLayout, raiseExtensionParam);

        // chat top extension
        Map<String, Object> topExtensionParam = new HashMap<>();
        topExtensionParam.put(
            TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE, TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.VIEW_TYPE_CLASSIC);
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.CHAT_ID, mChatInfo.getId());
        topExtensionParam.put(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.IS_GROUP, ChatInfo.TYPE_GROUP == mChatInfo.getType());
        TUICore.raiseExtension(TUIConstants.TUIChat.Extension.ChatViewTopAreaExtension.EXTENSION_ID, topExtensionLayout, topExtensionParam);
    }

    private void initChatbot() {
        if (TIMCommonUtil.isChatbot(mChatInfo.getId())) {
            mChatInfo.setNeedReadReceipt(false);
            mChatInfo.setEnableVideoCall(false);
            mChatInfo.setEnableAudioCall(false);
            mChatInfo.setPopMenuEnableCopy(true);
            mChatInfo.setPopMenuEnableDelete(true);
            mChatInfo.setPopMenuEnableForward(true);
            mChatInfo.setPopMenuEnableExtension(false);
            mChatInfo.setPopMenuEnableQuote(false);
            mChatInfo.setPopMenuEnableReply(false);
            mChatInfo.setPopMenuEnableRevoke(false);
            mChatInfo.setPopMenuEnableMultiSelect(false);
            mChatInfo.setPopMenuEnableInfo(false);
        }
    }

    private void setShortcutView() {
        ShortcutMenuConfig.ChatShortcutViewDataSource dataSource = ShortcutMenuConfig.getShortcutViewDataSource();
        if (dataSource != null) {
            List<ShortcutMenuConfig.TUIChatShortcutMenuData> dataList = dataSource.itemsInShortcutViewOfInfo(mChatInfo);
            if (dataList != null && !dataList.isEmpty()) {
                shortcutContainer.setVisibility(VISIBLE);
            } else {
                shortcutContainer.setVisibility(GONE);
                return;
            }
            Drawable drawable = dataSource.shortcutViewBackgroundOfInfo(mChatInfo);
            if (drawable != null) {
                shortcutContainer.setBackground(drawable);
            }
            ShortcutView shortcutView = new ShortcutView(getContext());
            shortcutView.setDataList(dataList);
            shortcutContainer.addView(shortcutView);
        }
    }

    private void setChatName() {
        if (!TextUtils.isEmpty(mChatInfo.getChatName())) {
            getTitleBar().setTitle(mChatInfo.getChatName(), ITitleBarLayout.Position.MIDDLE);
        } else {
            presenter.getChatName(mChatInfo.getId(), new IUIKitCallback<String>() {
                @Override
                public void onSuccess(String data) {
                    mChatInfo.setChatName(data);
                    getTitleBar().setTitle(mChatInfo.getChatName(), ITitleBarLayout.Position.MIDDLE);
                }

                @Override
                public void onError(String module, int errCode, String errMsg) {
                    getTitleBar().setTitle(mChatInfo.getId(), ITitleBarLayout.Position.MIDDLE);
                }
            });
        }
    }

    private void sendMsgReadReceipt(int firstPosition, int lastPosition) {
        if (mAdapter == null || presenter == null) {
            return;
        }
        List<TUIMessageBean> tuiMessageBeans = mAdapter.getItemList(firstPosition, lastPosition);
        presenter.sendMessageReadReceipt(tuiMessageBeans, new IUIKitCallback<Void>() {
            @Override
            public void onSuccess(Void data) {}

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                }
            }
        });
    }

    private void markCallingMsgRead(int firstPosition, int lastPosition) {
        if (mAdapter == null || presenter == null) {
            return;
        }
        List<CallingMessageBean> tuiMessageBeans = new ArrayList<CallingMessageBean>();
        for (TUIMessageBean bean : mAdapter.getItemList(firstPosition, lastPosition)) {
            if (bean instanceof CallingMessageBean) {
                tuiMessageBeans.add((CallingMessageBean) bean);
            }
        }

        presenter.markCallingMsgRead(tuiMessageBeans);
    }

    private void notifyMessageDisplayed(int firstPosition, int lastPosition) {
        // *******************************

        // *******************************
        markCallingMsgRead(firstPosition, lastPosition);
        // *******************************
        // *******************************

        if (mAdapter == null || presenter == null) {
            return;
        }
        for (TUIMessageBean bean : mAdapter.getItemList(firstPosition, lastPosition)) {
            Map<String, Object> param = new HashMap<>();
            param.put(TUIConstants.TUIChat.MESSAGE_BEAN, bean);
            TUICore.notifyEvent(TUIConstants.TUIChat.EVENT_KEY_MESSAGE_EVENT, TUIConstants.TUIChat.EVENT_SUB_KEY_DISPLAY_MESSAGE_BEAN, param);
        }
    }

    private void setTotalUnread() {
        UnreadCountTextView unreadCountTextView = mTitleBar.getUnreadCountTextView();
        unreadCountTextView.setPaintColor(getResources().getColor(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_unread_dot_bg_color)));
        unreadCountTextView.setTextColor(getResources().getColor(TUIThemeManager.getAttrResId(getContext(), R.attr.chat_unread_dot_text_color)));
        long unreadCount = 0;
        Object result = TUICore.callService(TUIConstants.TUIConversation.SERVICE_NAME, TUIConstants.TUIConversation.METHOD_GET_TOTAL_UNREAD_COUNT, null);
        if (result != null && result instanceof Long) {
            unreadCount =
                (long) TUICore.callService(TUIConstants.TUIConversation.SERVICE_NAME, TUIConstants.TUIConversation.METHOD_GET_TOTAL_UNREAD_COUNT, null);
        }
        updateUnreadCount(unreadCountTextView, unreadCount);
        unreadCountListener = new TotalUnreadCountListener() {
            @Override
            public void onTotalUnreadCountChanged(long totalUnreadCount) {
                updateUnreadCount(unreadCountTextView, totalUnreadCount);
            }
        };
        TUIChatService.getInstance().addUnreadCountListener(unreadCountListener);
    }

    private void updateUnreadCount(UnreadCountTextView unreadCountTextView, long count) {
        if (count <= 0) {
            unreadCountTextView.setVisibility(GONE);
        } else {
            unreadCountTextView.setVisibility(VISIBLE);
            String unreadCountStr = count + "";
            if (count > 99) {
                unreadCountStr = "99+";
            }
            unreadCountTextView.setText(unreadCountStr);
        }
    }

    private void setChatHandler() {
        if (presenter instanceof GroupChatPresenter) {
            presenter.setChatNotifyHandler(new ChatPresenter.ChatNotifyHandler() {
                @Override
                public void onGroupForceExit() {
                    ChatView.this.onExitChat();
                }

                @Override
                public void onGroupNameChanged(String newName) {
                    ChatView.this.onGroupNameChanged(newName);
                }

                @Override
                public void onApplied(int size) {
                    ChatView.this.onApplied(size);
                }

                @Override
                public void onExitChat(String chatId) {
                    ChatView.this.onExitChat();
                }
            });
        } else if (presenter instanceof C2CChatPresenter) {
            presenter.setChatNotifyHandler(new ChatPresenter.ChatNotifyHandler() {
                @Override
                public void onExitChat(String chatId) {
                    ChatView.this.onExitChat();
                }

                @Override
                public void onFriendNameChanged(String newName) {
                    ChatView.this.onFriendNameChanged(newName);
                }
            });
        }
    }

    private void loadApplyList() {
        presenter.loadApplyList(new IUIKitCallback<List<GroupApplyInfo>>() {
            @Override
            public void onSuccess(List<GroupApplyInfo> data) {
                if (data != null && data.size() > 0) {
                    mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, data.size()));
                    mGroupApplyLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatLog.e(TAG, "loadApplyList onError: " + errMsg);
            }
        });
    }

    public void onExitChat() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).finish();
        }
    }

    public void onGroupNameChanged(String newName) {
        getTitleBar().setTitle(newName, ITitleBarLayout.Position.MIDDLE);
    }

    public void onFriendNameChanged(String newName) {
        getTitleBar().setTitle(newName, ITitleBarLayout.Position.MIDDLE);
    }

    public void onApplied(int size) {
        if (size <= 0) {
            mGroupApplyLayout.setVisibility(View.GONE);
        } else {
            mGroupApplyLayout.getContent().setText(getContext().getString(R.string.group_apply_tips, size));
            mGroupApplyLayout.setVisibility(View.VISIBLE);
        }
    }

    public void loadPinnedMessage() {
        if (presenter instanceof GroupChatPresenter) {
            groupPinnedView.setGroupChatPresenter((GroupChatPresenter) presenter);
            ((GroupChatPresenter) presenter).setGroupPinnedView(groupPinnedView);
            ((GroupChatPresenter) presenter).loadPinnedMessage(getChatInfo().getId());
        }
    }

    public void loadMessages(TUIMessageBean lastMessage, int type) {
        if (presenter != null) {
            presenter.loadMessage(type, lastMessage);
        }
    }

    @Override
    public void loadMessages(int type) {
        if (type == TUIChatConstants.GET_MESSAGE_FORWARD) {
            loadMessages(mAdapter.getItemCount() > 0 ? mAdapter.getFirstMessageBean() : null, type);
        } else if (type == TUIChatConstants.GET_MESSAGE_BACKWARD) {
            loadMessages(mAdapter.getItemCount() > 0 ? mAdapter.getLastMessageBean() : null, type);
        }
    }

    public LinearLayout getForwardLayout() {
        return mForwardLayout;
    }

    public View getForwardOneButton() {
        return mForwardOneButton;
    }

    public View getDeleteButton() {
        return mDeleteButton;
    }

    public View getForwardMergeButton() {
        return mForwardMergeButton;
    }

    @Override
    public InputView getInputLayout() {
        return mInputView;
    }

    @Override
    public MessageRecyclerView getMessageLayout() {
        return mMessageRecyclerView;
    }

    @Override
    public NoticeLayout getNoticeLayout() {
        return mNoticeLayout;
    }

    public FrameLayout getCustomView() {
        return mCustomView;
    }

    @Override
    public ChatInfo getChatInfo() {
        return mChatInfo;
    }

    @Override
    public TitleBarLayout getTitleBar() {
        return mTitleBar;
    }

    private void initListener() {
        getMessageLayout().setPopActionClickListener(new OnChatPopActionClickListener() {
            @Override
            public void onCopyClick(TUIMessageBean msg) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                if (clipboard == null || msg == null) {
                    return;
                }
                String copyContent = msg.getSelectText();
                ClipData clip = ClipData.newPlainText("message", copyContent);
                clipboard.setPrimaryClip(clip);

                if (!TextUtils.isEmpty(copyContent)) {
                    String copySuccess = getResources().getString(R.string.copy_success_tip);
                    ToastUtil.toastShortMessage(copySuccess);
                }
            }

            @Override
            public void onSendMessageClick(TUIMessageBean msg, boolean retry) {
                sendMessage(msg, retry);
            }

            @Override
            public void onDeleteMessageClick(TUIMessageBean msg) {
                TUIKitDialog tipsDialog =
                    new TUIKitDialog(getContext())
                        .builder()
                        .setCancelable(true)
                        .setCancelOutside(true)
                        .setTitle(getContext().getString(R.string.chat_delete_msg_tip))
                        .setDialogWidth(0.75f)
                        .setPositiveButton(getContext().getString(com.tencent.qcloud.tuicore.R.string.sure),
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteMessage(msg);
                                }
                            })
                        .setNegativeButton(getContext().getString(com.tencent.qcloud.tuicore.R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        });
                tipsDialog.show();
            }

            @Override
            public void onRevokeMessageClick(TUIMessageBean msg) {
                revokeMessage(msg);
            }

            @Override
            public void onMultiSelectMessageClick(TUIMessageBean msg) {
                multiSelectMessage(msg);
            }

            @Override
            public void onForwardMessageClick(TUIMessageBean msg) {
                forwardMessage(msg);
            }

            @Override
            public void onReplyMessageClick(TUIMessageBean msg) {
                replyMessage(msg);
            }

            @Override
            public void onQuoteMessageClick(TUIMessageBean msg) {
                quoteMessage(msg);
            }

            @Override
            public void onSpeakerModeSwitchClick(TUIMessageBean msg) {
                boolean enableSpeakerMode = TUIChatConfigs.getGeneralConfig().isEnableSoundMessageSpeakerMode();
                TUIChatConfigClassic.setPlayingSoundMessageViaSpeakerByDefault(!enableSpeakerMode);
                AudioPlayer.getInstance().setSpeakerMode();
                if (enableSpeakerMode) {
                    ToastUtil.toastShortMessage(getResources().getString(R.string.chat_speaker_mode_off_tip));
                } else {
                    ToastUtil.toastShortMessage(getResources().getString(R.string.chat_speaker_mode_on_tip));
                }
            }
        });
        getMessageLayout().setChatDelegate(new MessageRecyclerView.ChatDelegate() {
            @Override
            public void displayBackToNewMessage(boolean display, String messageId, int count) {
                ChatView.this.displayBackToNewMessage(display, messageId, count);
            }

            @Override
            public void loadMessageFinish() {
                initGroupAtInfoLayout();
            }

            @Override
            public void scrollMessageFinish() {
                if (mClickLastMessageShow && mMessageRecyclerView != null) {
                    mClickLastMessageShow = false;
                }
            }

            @Override
            public void hideSoftInput() {
                getInputLayout().hideSoftInput();
            }
        });
        getMessageLayout().setEmptySpaceClickListener(new OnEmptySpaceClickListener() {
            @Override
            public void onClick() {
                getInputLayout().onEmptyClick();
            }
        });

        getInputLayout().setChatInputHandler(new InputView.ChatInputHandler() {
            @Override
            public void onInputAreaClick() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (presenter != null) {
                            presenter.scrollToNewestMessage();
                        }
                    }
                });
            }

            @Override
            public void onRecordStatusChanged(int status) {
                switch (status) {
                    case RECORD_START:
                        showRecordingAnim();
                        startCountDown();
                        break;
                    case RECORD_STOP:
                    case RECORD_CANCEL:
                        stopRecordingAnim();
                        break;
                    case RECORD_READY_TO_CANCEL:
                        showRecordingPauseAnim();
                        break;
                    case RECORD_TOO_SHORT:
                    case RECORD_FAILED:
                        stopAbnormally(status);
                        break;
                    case RECORD_CONTINUE:
                        showContinueAnim();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onUserTyping(boolean status, long curTime) {
                if (!TUIChatConfigClassic.isEnableTypingIndicator()) {
                    return;
                }

                if (!isSupportTyping) {
                    if (!isSupportTyping(curTime)) {
                        TUIChatLog.d(TAG, "onUserTyping trigger condition not met");
                        return;
                    } else {
                        isSupportTyping = true;
                    }
                }

                if (!status) {
                    sendTypingStatusMessage(false);
                    return;
                }

                if (lastTypingTime != 0) {
                    if ((curTime - lastTypingTime) < TUIChatConstants.TYPING_SEND_MESSAGE_INTERVAL) {
                        return;
                    }
                }

                lastTypingTime = curTime;
                sendTypingStatusMessage(true);
            }

            private void showRecordingAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        AudioPlayer.getInstance().stopPlay();
                        mRecordingGroup.setVisibility(View.VISIBLE);
                        mRecordingIcon.setImageResource(R.drawable.recording_volume);
                        mVolumeAnim = (AnimationDrawable) mRecordingIcon.getDrawable();
                        mVolumeAnim.start();
                        mRecordingTips.setTextColor(Color.WHITE);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.down_cancle_send));
                    }
                });
            }

            private void showContinueAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingIcon.setImageResource(R.drawable.recording_volume);
                        mVolumeAnim = (AnimationDrawable) mRecordingIcon.getDrawable();
                        mVolumeAnim.start();
                        mRecordingTips.setTextColor(Color.WHITE);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.down_cancle_send));
                    }
                });
            }

            private void stopRecordingAnim() {
                stopCountDown();
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mVolumeAnim != null) {
                            mVolumeAnim.stop();
                        }
                        mRecordingGroup.setVisibility(View.GONE);
                    }
                });
            }

            private void stopAbnormally(final int status) {
                stopCountDown();
                post(new Runnable() {
                    @Override
                    public void run() {
                        if (mVolumeAnim != null) {
                            mVolumeAnim.stop();
                        }
                        mRecordingIcon.setImageResource(R.drawable.ic_volume_dialog_length_short);
                        mRecordingTips.setTextColor(Color.WHITE);
                        if (status == RECORD_TOO_SHORT) {
                            mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.say_time_short));
                        } else {
                            mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.record_fail));
                        }
                    }
                });
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingGroup.setVisibility(View.GONE);
                    }
                }, 500);
            }

            private void showRecordingPauseAnim() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        mRecordingIcon.setImageResource(R.drawable.ic_volume_dialog_cancel);
                        mRecordingTips.setText(TUIChatService.getAppContext().getString(R.string.up_cancle_send));
                    }
                });
            }
        });
        chatInputMoreListener = new ChatInputMoreListener() {
            @Override
            public String sendMessage(TUIMessageBean msg, IUIKitCallback<TUIMessageBean> callback) {
                return ChatView.this.sendMessage(msg, false, callback);
            }
        };
        mInputView.setChatInputMoreListener(chatInputMoreListener);
    }

    private void displayBackToNewMessage(boolean display, String messageId, int count) {
        if (display) {
            displayBackToNewMessages(messageId, count);
        } else {
            mJumpNewMessageShow = false;
            hideJumpMessageLayouts();
        }
    }

    private void displayBackToLastMessage(boolean display) {
        if (mJumpNewMessageShow) {
            return;
        }
        if (display) {
            if (mAdapter != null) {
                displayBackToLastMessages();
            } else {
                TUIChatLog.e(TAG, "displayJumpLayout mAdapter is null");
            }
        } else {
            hideJumpMessageLayouts();
        }
    }

    private void startCountDown() {
        stopCountDown();
        recordCountDownTimer = new Timer();
        RecordCountDownTask task = new RecordCountDownTask();
        task.setTextView(recordCountDownTv);
        recordCountDownTimer.schedule(task, 0, 1000);
    }

    private void stopCountDown() {
        if (recordCountDownTimer != null) {
            recordCountDownTimer.cancel();
            recordCountDownTimer = null;
        }
    }

    public boolean isSupportTyping(long time) {
        return presenter.isSupportTyping(time);
    }

    @Override
    public void initDefault(TUIBaseChatFragment fragment) {
        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });
        getInputLayout().setMessageHandler(new InputView.MessageHandler() {
            @Override
            public void sendMessage(TUIMessageBean msg) {
                if (msg instanceof ReplyMessageBean) {
                    ChatView.this.sendReplyMessage(msg, false);
                } else {
                    ChatView.this.sendMessage(msg, false);
                }
            }

            @Override
            public void sendMessages(List<TUIMessageBean> messageBeans) {
                presenter.sendMessages(messageBeans);
            }

            @Override
            public void scrollToEnd() {
                ChatView.this.scrollToEnd();
            }
        });
        if (getMessageLayout().getAdapter() == null) {
            mAdapter = new MessageAdapter();
            mAdapter.setFragment(fragment);
            mMessageRecyclerView.setAdapter(mAdapter);
        }
        setCustomTopView();
        initListener();
        resetForwardState("");
    }

    private void setCustomTopView() {
        // Set custom view of chat interface as security prompt
        View customNoticeLayout = TUIChatConfigs.getNoticeLayoutConfig().getCustomNoticeLayout();
        FrameLayout customView = getCustomView();
        if (customNoticeLayout != null && customView.getVisibility() == View.GONE) {
            ViewParent viewParent = customNoticeLayout.getParent();
            if (viewParent instanceof ViewGroup) {
                ViewGroup parentView = (ViewGroup) viewParent;
                parentView.removeAllViews();
            }
            customView.addView(customNoticeLayout);
            customView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setParentLayout(Object parentContainer) {}

    public void scrollToEnd() {
        getMessageLayout().scrollToEnd();
    }

    protected void deleteMessage(TUIMessageBean msg) {
        presenter.deleteMessage(msg);
    }

    protected void deleteMessages(final List<Integer> positions) {
        presenter.deleteMessages(positions);
    }

    protected void deleteMessageInfos(final List<TUIMessageBean> msgIds) {
        presenter.deleteMessageInfos(msgIds);
    }

    protected boolean checkFailedMessageInfos(final List<TUIMessageBean> msgIds) {
        return presenter.checkFailedMessageInfos(msgIds);
    }

    protected void revokeMessage(TUIMessageBean msg) {
        presenter.revokeMessage(msg);
    }

    protected void multiSelectMessage(TUIMessageBean msg) {
        if (mAdapter != null) {
            mInputView.hideSoftInput();
            mAdapter.setShowMultiSelectCheckBox(true);
            mAdapter.setItemChecked(msg, !msg.hasRiskContent());
            mAdapter.notifyDataSetChanged();

            setTitleBarWhenMultiSelectMessage();
        }
    }

    public void forwardMessage(TUIMessageBean msg) {
        if (mAdapter != null) {
            mInputView.hideSoftInput();
            mAdapter.setItemChecked(msg, true);
            mAdapter.notifyDataSetChanged();
            showForwardDialog(false, true);
        }
    }

    public void forwardText(String text) {
        showForwardTextDialog(text);
    }

    // Reply Message need MessageRootId
    protected void replyMessage(TUIMessageBean messageBean) {
        ReplyPreviewBean replyPreviewBean = ChatMessageBuilder.buildReplyPreviewBean(messageBean);
        mInputView.showReplyPreview(replyPreviewBean);
    }

    // Quote Message not need MessageRootId
    protected void quoteMessage(TUIMessageBean messageBean) {
        ReplyPreviewBean replyPreviewBean = ChatMessageBuilder.buildReplyPreviewBean(messageBean);
        replyPreviewBean.setMessageRootID(null);
        mInputView.showReplyPreview(replyPreviewBean);
    }

    private void resetTitleBar(String leftTitle) {
        getTitleBar().getRightGroup().setVisibility(VISIBLE);

        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().getLeftIcon().setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(leftTitle)) {
            getTitleBar().setTitle(leftTitle, ITitleBarLayout.Position.LEFT);
        } else {
            getTitleBar().setTitle("", TitleBarLayout.Position.LEFT);
        }
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }
            }
        });

        getForwardLayout().setVisibility(GONE);
        boolean enableMainPageInputBar = TUIChatConfigClassic.isShowInputBar();
        getInputLayout().setVisibility(enableMainPageInputBar ? VISIBLE : GONE);
    }

    private void resetForwardState(String leftTitle) {
        if (mAdapter != null) {
            mAdapter.setShowMultiSelectCheckBox(false);
            mAdapter.notifyDataSetChanged();
        }

        resetTitleBar(leftTitle);
    }

    private void setTitleBarWhenMultiSelectMessage() {
        getTitleBar().getRightGroup().setVisibility(GONE);

        boolean enableForwardMessage = true;
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.USER_ID, mChatInfo.getId());
        List<TUIExtensionInfo> extensionList = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.CLASSIC_EXTENSION_ID, param);
        for (TUIExtensionInfo info : extensionList) {
            Map<String, Object> extensionMap = info.getData();
            if (!extensionMap.isEmpty()) {
                Object enableForwardMessageObj = extensionMap.get(TUIConstants.TUIChat.Extension.MultiSelectMessageBar.ENABLE_FORWARD_MESSAGE);
                if (enableForwardMessageObj instanceof Boolean) {
                    enableForwardMessage = (Boolean) enableForwardMessageObj;
                    break;
                }
            }
        }

        if (!enableForwardMessage) {
            mForwardOneButton.setVisibility(View.GONE);
            mForwardMergeButton.setVisibility(View.GONE);
        }

        getTitleBar().getLeftGroup().setVisibility(View.VISIBLE);
        getTitleBar().getLeftIcon().setVisibility(GONE);
        final CharSequence leftTitle = getTitleBar().getLeftTitle().getText();
        getTitleBar().setTitle(getContext().getString(com.tencent.qcloud.tuicore.R.string.cancel), TitleBarLayout.Position.LEFT);
        getTitleBar().setOnLeftClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetForwardState(leftTitle.toString());
            }
        });
        getInputLayout().setVisibility(GONE);
        getForwardLayout().setVisibility(VISIBLE);
        getForwardOneButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForwardDialog(true, true);
            }
        });
        getForwardMergeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForwardDialog(true, false);
            }
        });
        getDeleteButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TUIMessageBean> msgIds = mAdapter.getSelectedItem();

                if (msgIds == null || msgIds.isEmpty()) {
                    ToastUtil.toastShortMessage("please select message!");
                    return;
                }

                deleteMessageInfos(msgIds);

                resetForwardState(leftTitle.toString());
            }
        });
    }

    private void showForwardDialog(boolean isMultiSelect, boolean isOneByOne) {
        if (mAdapter == null) {
            return;
        }

        final List<TUIMessageBean> messageInfoList = mAdapter.getSelectedItem();

        if (messageInfoList == null || messageInfoList.isEmpty()) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.forward_tip));
            return;
        }

        if (checkFailedMessageInfos(messageInfoList)) {
            ToastUtil.toastShortMessage(getContext().getString(R.string.forward_failed_tip));
            return;
        }

        for (TUIMessageBean tuiMessageBean : messageInfoList) {
            if (!tuiMessageBean.isEnableForward()) {
                ToastUtil.toastShortMessage(getContext().getString(R.string.forward_group_note_or_poll_failed_tip));
                return;
            }
        }

        if (!isMultiSelect) {
            mAdapter.setShowMultiSelectCheckBox(false);
        }

        if (isOneByOne) {
            if (messageInfoList.size() > FORWARD_MSG_NUM_LIMIT) {
                showForwardLimitDialog(messageInfoList);
            } else {
                startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_ONE_BY_ONE, messageInfoList);
                resetForwardState("");
            }
        } else {
            startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
            resetForwardState("");
        }
    }

    private void showForwardTextDialog(String text) {
        if (mForwardSelectActivityListener != null) {
            mForwardSelectActivityListener.forwardText(text);
        }
    }

    private void showForwardLimitDialog(final List<TUIMessageBean> messageInfoList) {
        TUIKitDialog tipsDialog = new TUIKitDialog(getContext())
                                      .builder()
                                      .setCancelable(true)
                                      .setCancelOutside(true)
                                      .setTitle(getContext().getString(R.string.forward_oneByOne_limit_number_tip))
                                      .setDialogWidth(0.75f)
                                      .setPositiveButton(getContext().getString(R.string.forward_mode_merge),
                                          new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startSelectForwardActivity(TUIChatConstants.FORWARD_MODE_MERGE, messageInfoList);
                                                  resetForwardState("");
                                              }
                                          })
                                      .setNegativeButton(getContext().getString(com.tencent.qcloud.tuicore.R.string.cancel), new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {}
                                      });
        tipsDialog.show();
    }

    private void startSelectForwardActivity(int mode, List<TUIMessageBean> messageBeans) {
        if (mForwardSelectActivityListener != null) {
            mForwardSelectActivityListener.forwardMessages(mode, messageBeans);
        }
    }

    public void setForwardSelectActivityListener(ForwardSelectActivityListener listener) {
        this.mForwardSelectActivityListener = listener;
    }

    private String sendMessage(TUIMessageBean messageBean, boolean retry, IUIKitCallback<TUIMessageBean> callback) {
        return presenter.sendMessage(messageBean, retry, false, new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                TUIChatUtils.callbackOnSuccess(callback, data);
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToEnd();
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatUtils.callbackOnError(callback, errCode, errMsg);
                String toastMsg = errMsg;
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                    if (messageBean.isNeedReadReceipt()) {
                        toastMsg = getResources().getString(R.string.chat_message_read_receipt)
                            + getResources().getString(com.tencent.qcloud.tuicore.R.string.TUIKitErrorUnsupporInterfaceSuffix);
                    }
                }
                ToastUtil.toastLongMessage(toastMsg);
            }

            @Override
            public void onProgress(Object data) {
                TUIChatUtils.callbackOnProgress(callback, data);
                ProgressPresenter.updateProgress(messageBean.getId(), (Integer) data);
            }
        });
    }

    @Override
    public void sendMessage(TUIMessageBean msg, boolean retry) {
        sendMessage(msg, retry, null);
    }

    public void sendReplyMessage(TUIMessageBean msg, boolean retry) {
        presenter.sendMessage(msg, retry, false, new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        scrollToEnd();
                    }
                });
                presenter.modifyRootMessageToAddReplyInfo((ReplyMessageBean) data, new IUIKitCallback<Void>() {
                    @Override
                    public void onError(String module, int errCode, String errMsg) {
                        TUIChatLog.e(TAG, "modify message failed code = " + errCode + " message = " + errMsg);
                    }
                });
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                String toastMsg = errCode + ", " + errMsg;
                if (errCode == TUIConstants.BuyingFeature.ERR_SDK_INTERFACE_NOT_SUPPORT) {
                    showNotSupportDialog();
                    if (msg.isNeedReadReceipt()) {
                        toastMsg = getResources().getString(R.string.chat_message_read_receipt)
                            + getResources().getString(com.tencent.qcloud.tuicore.R.string.TUIKitErrorUnsupporInterfaceSuffix);
                    }
                }
                ToastUtil.toastLongMessage(toastMsg);
            }
        });
    }

    public void setChatBackground(Drawable drawable) {
        chatBackgroundView.post(() -> {
            int vw = chatBackgroundView.getWidth();
            int vh = chatBackgroundView.getHeight();
            int dw = drawable.getIntrinsicWidth();
            int dh = drawable.getIntrinsicHeight();
            float scale;
            float dx = 0;
            float dy = 0;

            if (dw * vh > vw * dh) {
                scale = (float) vh / (float) dh;
                dx = (vw - dw * scale) * 0.5f;
            } else {
                scale = (float) vw / (float) dw;
                dy = (vh - dh * scale) * 0.5f;
            }
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postTranslate(Math.round(dx), Math.round(dy));
            chatBackgroundView.setImageMatrix(matrix);
            chatBackgroundView.setImageDrawable(drawable);
        });
    }

    public void sendTypingStatusMessage(boolean status) {
        if (mChatInfo == null || TextUtils.isEmpty(getChatInfo().getId())) {
            TUIChatLog.e(TAG, "sendTypingStatusMessage receiver is invalid");
            return;
        }

        Gson gson = new Gson();
        MessageTyping typingMessageBean = new MessageTyping();
        typingMessageBean.setTypingStatus(status);
        String data = gson.toJson(typingMessageBean);
        TUIChatLog.d(TAG, "sendTypingStatusMessage data = " + data);
        TUIMessageBean msg = ChatMessageBuilder.buildCustomMessage(data, "", null);

        presenter.sendTypingStatusMessage(msg, mChatInfo.getId(), new IUIKitCallback<TUIMessageBean>() {
            @Override
            public void onSuccess(TUIMessageBean data) {
                TUIChatLog.d(TAG, "sendTypingStatusMessage onSuccess:" + data.getId());
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                TUIChatLog.d(TAG, "sendTypingStatusMessage fail:" + errCode + "=" + errMsg);
            }
        });
    }

    public void exitChat() {
        getTitleBar().getMiddleTitle().removeCallbacks(mTypingRunnable);
        AudioRecorder.cancelRecord();
        AudioPlayer.getInstance().stopPlay();
        presenter.markMessageAsRead(mChatInfo, false);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        V2TIMManager.getInstance().removeSimpleMsgListener(v2TIMSimpleMsgListener);
        mCustomView.removeAllViews();
        exitChat();
        // 移除布局监听器避免内存泄漏
        if (globalLayoutListener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                getViewTreeObserver().removeOnGlobalLayoutListener(globalLayoutListener);
            } else {
                getViewTreeObserver().removeGlobalOnLayoutListener(globalLayoutListener);
            }
        }
    }

    public interface ForwardSelectActivityListener {
        void forwardMessages(int mode, List<TUIMessageBean> messageBeans);

        void forwardText(String text);
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        // You will go to Chat from other interfaces, and you must also report a read receipt
        if (visibility == VISIBLE) {
            if (getMessageLayout() == null) {
                return;
            }
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getMessageLayout().getLayoutManager();
            if (linearLayoutManager == null) {
                return;
            }
            int firstVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            sendMsgReadReceipt(firstVisiblePosition, lastVisiblePosition);
            if (presenter != null) {
                presenter.markMessageAsRead(mChatInfo);
            }
        }
    }

    private void showNotSupportDialog() {
        String string = getResources().getString(R.string.chat_im_flagship_edition_update_tip, getResources().getString(R.string.chat_message_read_receipt));
        String buyingGuidelines = getResources().getString(R.string.chat_buying_guidelines);
        int buyingGuidelinesIndex = string.lastIndexOf(buyingGuidelines);
        final int foregroundColor = getResources().getColor(TUIThemeManager.getAttrResId(getContext(), com.tencent.qcloud.tuicore.R.attr.core_primary_color));
        SpannableString spannedString = new SpannableString(string);
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(foregroundColor);
        spannedString.setSpan(colorSpan2, buyingGuidelinesIndex, buyingGuidelinesIndex + buyingGuidelines.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (TextUtils.equals(TUIThemeManager.getInstance().getCurrentLanguage(), "zh")) {
                    openWebUrl(TUIConstants.BuyingFeature.BUYING_PRICE_DESC);
                } else {
                    openWebUrl(TUIConstants.BuyingFeature.BUYING_PRICE_DESC_EN);
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
        spannedString.setSpan(clickableSpan2, buyingGuidelinesIndex, buyingGuidelinesIndex + buyingGuidelines.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        TUIKitDialog.TUIIMUpdateDialog.getInstance()
            .createDialog(getContext())
            .setShowOnlyDebug(true)
            .setMovementMethod(LinkMovementMethod.getInstance())
            .setHighlightColor(Color.TRANSPARENT)
            .setCancelable(true)
            .setCancelOutside(true)
            .setTitle(spannedString)
            .setDialogWidth(0.75f)
            .setDialogFeatureName(TUIConstants.BuyingFeature.BUYING_FEATURE_MESSAGE_RECEIPT)
            .setPositiveButton(getResources().getString(R.string.chat_no_more_reminders),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().dismiss();
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().setNeverShow(true);
                    }
                })
            .setNegativeButton(getResources().getString(R.string.chat_i_know),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TUIKitDialog.TUIIMUpdateDialog.getInstance().dismiss();
                    }
                })
            .show();
    }

    private void openWebUrl(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri contentUrl = Uri.parse(url);
        intent.setData(contentUrl);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    static class RecordCountDownTask extends TimerTask {
        WeakReference<TextView> textView;
        int count = 60;

        public void setTextView(TextView textView) {
            this.textView = new WeakReference<>(textView);
        }

        @Override
        public void run() {
            ThreadUtils.runOnUiThread(() -> {
                if (count < 0) {
                    return;
                }
                if (textView != null && textView.get() != null) {
                    textView.get().setText(count + "''");
                    count--;
                }
            });
        }
    }



    private void initCardView(){
        if (!TextUtils.isEmpty(infoBean.getImGroup().getGoods_image())) {
            Glide.with(getContext())
                    .load(convertToArray(infoBean.getImGroup().getGoods_image())[0])
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                            .error(R.mipmap.ic_default_image)// 加载失败的占位图
                            .centerCrop()// 图片裁剪方式
                    )
                    .into(ivImage);
        }

        tvHint.setText(infoBean.getImGroup().getGoods_name());
        tvPrice.setText(formatPrice(getContext(), (infoBean.getImGroup().getOrder_id() != null && infoBean.getImGroup().getOrder_id() != 0) ? infoBean.getImGroup().getGoods_price() : infoBean.getImGroup().getRetail_price()));

        //支付状态 0：待支付 1：支付成功 2：支付失败 3：待发货 4：已发货 5：等待验号 6：已验号 7: 待确认收货 8：已完成 9：已取消 10：待退款 11：退款中 12：已退款 13：退款失败
        if (im_buyer_id.equalsIgnoreCase(TUILogin.getUserId())) {
            if (infoBean.getImGroup().getOrder_id() == 0) {
                //没下单
                tvTag.setText("立即购买");
                tvTag.setVisibility(INVISIBLE);
                tvImmediatePayment.setText("立即购买");
                setViewVisibility(infoBean.getImGroup().getReview_status() == 1 ? tvImmediatePayment : null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
            }else if (infoBean.getImGroup().getOrder_id() != 0){
                //已下单
                tvTag.setVisibility(VISIBLE);
                switch (infoBean.getImGroup().getOrder_status()){
                    case 0:
                        tvTag.setText("待支付");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_ff4646));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_FF4646));
                        setViewVisibility(tvImmediatePayment, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 1:
                    case 3:
                        tvTag.setText("待发货");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33eaca92));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_EACA92));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 4:
                    case 5:
                        tvTag.setText("待验号");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33ff9212));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_FF9212));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 6:
                    case 7:
                        tvTag.setText("待换绑");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33ff9212));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_FF9212));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 8:
                        tvTag.setText("已完成");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_330eb272));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_0EB272));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 9:
                        tvTag.setText("已取消");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_CBCBCB));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 12:
                        tvTag.setText("已退款");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_CBCBCB));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                }
            }
        }else {
            if (infoBean.getImGroup().getOrder_id() == 0) {
                //没下单
                tvTag.setVisibility(INVISIBLE);
                tvTag.setText("待付款");
                setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
            }else if (infoBean.getImGroup().getOrder_id() != 0){
                //已下单
                tvTag.setVisibility(VISIBLE);
                switch (infoBean.getImGroup().getOrder_status()){
                    case 0:
                        tvTag.setText("待付款");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_ff4646));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_FF4646));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 1:
                    case 3:
                        tvTag.setText("待发货");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33eaca92));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_EACA92));
                        setViewVisibility(tvSendOutGoods, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        tvTag.setText("交易中");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33ff9212));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_FF9212));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 8:
                        tvTag.setText("已完成");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_330eb272));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_0EB272));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 9:
                        tvTag.setText("已取消");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_CBCBCB));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    case 12:
                        tvTag.setText("已退款");
                        tvTag.setBackground(getDrawable(getContext(), R.drawable.shape_semicircle_radius10_33cbcbcb));
                        tvTag.setTextColor(getColor(getContext(), R.color.color_CBCBCB));
                        setViewVisibility(null, tvImmediatePayment, tvConfirmVerificationNumber, tvConfirmReceipt, tvSendOutGoods);
                        break;
                    default:
                        break;
                }
            }
        }

        tvImmediatePayment.setOnClickListener(v->{
            if (infoBean.getImGroup().getOrder_id() == 0) {
                //没有订单
                N.APIThb(ThbApiService.class)
                        .auth_info()
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(AuthInfoBean.class))
                        .subscribe(new NetObserverThb<AuthInfoBean>(v.getContext()) {
                            @Override
                            public void onSuccess(AuthInfoBean it) {
                                if (it.getUser_auth_info().getType()!=0) {
                                    ARouter.getInstance().build(Routes.Main.CONFIRM_AN_ORDER)
                                            .withInt("goods_id", infoBean.getImGroup().getGood_id())
                                            .withBoolean("is_order_info", false)
                                            .navigation();
                                }else {
                                    //未实名认证
                                    realNameAuthenticationDialog(v.getContext());
                                }
                            }
                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(v.getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                            }
                        });

            }else {
                //有订单
                ARouter.getInstance().build(Routes.Main.CONFIRM_AN_ORDER)
                        .withInt("order_id", infoBean.getImGroup().getOrder_id())
                        .withBoolean("is_order_info", true)
                        .navigation();
            }
        });
        tvConfirmVerificationNumber.setOnClickListener(v->{
            commonDialog(1, "确认验号吗？", infoBean.getImGroup().getOrder_id());
        });
        tvConfirmReceipt.setOnClickListener(v->{
            commonDialog(2, "确认换绑吗？", infoBean.getImGroup().getOrder_id());
        });
        tvSendOutGoods.setOnClickListener(v->{
            //提交账号信息
            ARouter.getInstance().build(Routes.Main.SUBMINT_ACCOUNT_INFORMATION)
                    .withInt("gameId", infoBean.getImGroup().getGame_id())
                    .withInt("goodsId", infoBean.getImGroup().getGood_id())
                    .withInt("orderId", infoBean.getImGroup().getOrder_id())
                    .withString("imGroupId", infoBean.getImGroup().getIm_group_id())
                    .withString("imGroupOwnerUserID", infoBean.getImGroup().getIm_owner_id())
                    .navigation();
        });

        ctop.setOnClickListener(v->{
            if (infoBean != null && infoBean.getImGroup() != null && infoBean.getImGroup().getOrder_id() != null && infoBean.getImGroup().getOrder_id() != 0) {
                ARouter.getInstance().build(Routes.Main.ORDER_DETAILS)
                        .withInt("order_id", infoBean.getImGroup().getOrder_id())
                        .withInt("orderType", im_buyer_id.equalsIgnoreCase(TUILogin.getUserId()) ? 1 : 2)
                        .navigation();
            }else {
                ARouter.getInstance().build(Routes.Main.PRODUCT_DETAILS)
                        .withInt("goodsId", infoBean.getImGroup().getGood_id())
                        .navigation();
            }
        });

    }


    /**
     * 公共弹窗
     * @param type 1确认验号 2确认收货 3取消订单 4修改价格
     */
    private void commonDialog(int type, String hint, int order_id){
        new XPopup.Builder(getContext())
                .isViewMode(true)
                .asCustom(new CommonDialog(getContext(), hint, new CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            setOrderStatus(order_id, 1, null, "");
                        }else if (type == 2) {
                            setOrderStatus(order_id, 2, null, "");
                        }
                    }
                })).show();
    }

    private void setViewVisibility(View view, View... views){
        for (View view1 : views) {
            view1.setVisibility(GONE);
        }
        if (view != null) {
            view.setVisibility(VISIBLE);
        }
    }

    /**
     * 实名认证弹窗
     */
    public static void realNameAuthenticationDialog(Context context){
        new XPopup.Builder(context)
                .isViewMode(false)
                .asCustom(new RealNameAuthenticationDialog(context, new RealNameAuthenticationDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String name, String number) {
                        Parameter parameter = new Parameter();
                        parameter.add("realname", name);
                        parameter.add("number", RSA.encryptByPublicKey(context,number));
                        N.APIThb(ThbApiService.class)
                                .realname(parameter.buildJsonBody())
                                .compose(N.IOMain())
                                .map(ThbApiService.turnThb(Object.class))
                                .subscribe(new NetObserverThb<Object>(context) {
                                    @Override
                                    public void onSuccess(Object it) {
                                        Toast.makeText(context,"提交成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    protected void onFailure(BaseTHB baseTHB) {
                                        Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })).show();
    }

    private void getGroupInfoByImGroupId() {
        Parameter parameter = new Parameter();
        parameter.add("groupID", mChatInfo.getId());
        N.APIThb(ThbApiService.class)
                .getGroupInfoByImGroupId(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(ThbApiService.turnThb(ImGroupInfoBean.class))
                .subscribe(new NetObserverThb<ImGroupInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(ImGroupInfoBean it) {
                        infoBean = it;
                        pattern = it.getImGroup().getPattern();
                        orderStatus = it.getImGroup().getOrder_status();
                        goodsId = it.getImGroup().getGood_id();
                        im_buyer_id = it.getImGroup().getBuyer_user_name();
                        MMKV.defaultMMKV().encode("im_owner_id", it.getImGroup().getIm_owner_id());
                        MMKV.defaultMMKV().encode("im_seller_id", it.getImGroup().getSeller_user_name());
                        MMKV.defaultMMKV().encode("im_buyer_id", it.getImGroup().getBuyer_user_name());
                        MMKV.defaultMMKV().encode("im_members", JSONObject.toJSONString(it.getImGroup().getMembers()));

                        //1 平台寄售 2快速回收
                        if ((it.getImGroup().getGood_id() != null && it.getImGroup().getGood_id() != 0) || (it.getImGroup().getOrder_id() != null && it.getImGroup().getOrder_id() != 0)) {
                            ctop.setVisibility(VISIBLE);
                            initCardView();
                        }

                        if (intelligentType == 10 && mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        Toast.makeText(getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * @param order_id      订单id
     * @param type          操纵类型 1:确认验号 2：确认收货 3：取消订单 4：修改价格
     * @param amount        修改的价格
     * @param refund_content 取消原因
     */
    private void setOrderStatus(int order_id, int type, Double amount, String refund_content) {
        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);
        parameter.add("type", type);
        parameter.add("amount", amount);
        parameter.add("refund_content", refund_content);
        parameter.add("platform", 2);

        N.APIThb(ThbApiService.class)
                .setOrderStatus(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(ThbApiService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {

                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        Toast.makeText(getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
