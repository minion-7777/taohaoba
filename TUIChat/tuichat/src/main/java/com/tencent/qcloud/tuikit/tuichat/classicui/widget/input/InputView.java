package com.tencent.qcloud.tuikit.tuichat.classicui.widget.input;

import static com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo.TYPE_C2C;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUIExtensionEventListener;
import com.tencent.qcloud.tuicore.interfaces.TUIExtensionInfo;
import com.tencent.qcloud.tuicore.interfaces.TUIValueCallback;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.tencent.qcloud.tuikit.timcommon.bean.ChatFace;
import com.tencent.qcloud.tuikit.timcommon.bean.ImGroupInfoBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.component.face.FaceManager;
import com.tencent.qcloud.tuikit.timcommon.interfaces.ChatInputMoreListener;
import com.tencent.qcloud.tuikit.timcommon.interfaces.OnFaceInputListener;
import com.tencent.qcloud.tuikit.timcommon.util.ActivityResultResolver;
import com.tencent.qcloud.tuikit.timcommon.util.FileUtil;
import com.tencent.qcloud.tuikit.timcommon.util.TIMCommonUtil;
import com.tencent.qcloud.tuikit.timcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.TUIChatConstants;
import com.tencent.qcloud.tuikit.tuichat.TUIChatService;
import com.tencent.qcloud.tuikit.tuichat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.CustomHelloMessage;
import com.tencent.qcloud.tuikit.tuichat.bean.DraftInfo;
import com.tencent.qcloud.tuikit.tuichat.bean.GoodsListBean;
import com.tencent.qcloud.tuikit.tuichat.bean.InputMoreItem;
import com.tencent.qcloud.tuikit.tuichat.bean.OrderListBeanConversation;
import com.tencent.qcloud.tuikit.tuichat.bean.ReplyPreviewBean;
import com.tencent.qcloud.tuikit.tuichat.bean.TextFilterBean;
import com.tencent.qcloud.tuikit.tuichat.bean.message.FileMessageBean;
import com.tencent.qcloud.tuikit.tuichat.classicui.adapter.GoodListAdapter;
import com.tencent.qcloud.tuikit.tuichat.classicui.adapter.OrderListAdapter;
import com.tencent.qcloud.tuikit.tuichat.classicui.interfaces.IChatLayout;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.input.inputmore.InputMoreFragment;
import com.tencent.qcloud.tuikit.tuichat.component.album.AlbumPicker;
import com.tencent.qcloud.tuikit.tuichat.component.album.VideoRecorder;
import com.tencent.qcloud.tuikit.tuichat.component.audio.AudioRecorder;
import com.tencent.qcloud.tuikit.tuichat.component.face.FaceFragment;
import com.tencent.qcloud.tuikit.tuichat.component.inputedittext.TIMMentionEditText;
import com.tencent.qcloud.tuikit.tuichat.config.GeneralConfig;
import com.tencent.qcloud.tuikit.tuichat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.tuichat.dialog.ReportDialog;
import com.tencent.qcloud.tuikit.tuichat.interfaces.AlbumPickerListener;
import com.tencent.qcloud.tuikit.tuichat.presenter.ChatPresenter;
import com.tencent.qcloud.tuikit.tuichat.repository.ThbApiService;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageBuilder;
import com.tencent.qcloud.tuikit.tuichat.util.ChatMessageParser;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatLog;
import com.tencent.qcloud.tuikit.tuichat.util.TUIChatUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;

/**
 *
 * Chat interface, send pictures, take pictures, video, file panels at the bottom
 */

public class InputView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private static final String TAG = InputView.class.getSimpleName();

    private static final int STATE_NONE_INPUT = -1;
    private static final int STATE_SOFT_INPUT = 0;
    private static final int STATE_VOICE_INPUT = 1;
    private static final int STATE_FACE_INPUT = 2;
    private static final int STATE_ACTION_INPUT = 3;

    /**
     *
     * Voice/text switch input controls
     */
    protected ImageView mAudioInputSwitchButton;
    protected boolean mAudioInputDisable;

    /**
     *
     * emoji button
     */
    protected ImageView mEmojiInputButton;
    protected boolean mEmojiInputDisable;

    /**
     *
     * more button
     */
    protected ImageView mMoreInputButton;
    protected Object mMoreInputEvent;
    protected boolean mMoreInputDisable;

    /**
     *
     * message send button
     */
    protected TextView mSendTextButton;

    /**
     *
     * voice send button
     */
    protected Button mSendAudioButton;

    /**
     *
     * input text
     */
    protected TIMMentionEditText mTextInput;
    private boolean mIsSending = false;

    protected FragmentActivity mActivity;
    protected View mInputMoreView;
    protected ImageView mChatboxInterruptView;
    protected ChatInfo mChatInfo;
    protected List<InputMoreItem> mInputMoreActionList = new ArrayList<>();

    private FaceFragment mFaceFragment;
    private ChatInputHandler mChatInputHandler;
    private MessageHandler mMessageHandler;
    private FragmentManager mFragmentManager;
    private InputMoreFragment mInputMoreFragment;
    private IChatLayout mChatLayout;
    private boolean mSendEnable;
    private int mCurrentState;
    private int mLastMsgLineCount;
    private float mStartRecordY;
    private String mInputContent;
    private OnInputViewListener mOnInputViewListener;

    private Map<String, String> atUserInfoMap = new HashMap<>();
    private String displayInputString;

    private ChatPresenter presenter;

    private boolean isReplyModel = false;
    private boolean isQuoteModel = false;
    private ReplyPreviewBean replyPreviewBean;
    private View replyPreviewBar;
    private ImageView replyCloseBtn;
    private TextView replyTv;
    private View quotePreviewBar;
    private TextView quoteTv;
    private ImageView quoteCloseBtn;
    private boolean isShowCustomFace = true;
    private ChatInputMoreListener chatInputMoreListener;
    private BasePopupView popupView;
    private int pageIndexGoodsList;
    private int totalCountGoodsList;
    private int goodsListShowType = 0;//0-all,3-mobile,2-pc
    private int pageIndexOrdersList;
    private int totalCountOrdersList;
    private int ordersListShowType = 0;//0-all,1-mobile,2-pc

    public InputView(Context context) {
        super(context);
        initViews();
    }

    public InputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public InputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void setPresenter(ChatPresenter presenter) {
        this.presenter = presenter;
    }

    private void initViews() {
        mActivity = (FragmentActivity) getContext();
        inflate(mActivity, R.layout.chat_input_layout, this);
        mInputMoreView = findViewById(R.id.more_groups);
        mChatboxInterruptView = findViewById(R.id.chatbot_interrupt_button);
        Drawable drawable = mChatboxInterruptView.getDrawable();
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, 0xE0000000);
            mChatboxInterruptView.setImageDrawable(drawable);
        }
        mSendAudioButton = findViewById(R.id.chat_voice_input);
        mAudioInputSwitchButton = findViewById(R.id.voice_input_switch);
        mEmojiInputButton = findViewById(R.id.face_btn);
        mMoreInputButton = findViewById(R.id.more_btn);
        mSendTextButton = findViewById(R.id.send_btn);
        mTextInput = findViewById(R.id.chat_message_input);
        replyPreviewBar = findViewById(R.id.reply_preview_bar);
        replyTv = replyPreviewBar.findViewById(R.id.reply_text);
        replyCloseBtn = replyPreviewBar.findViewById(R.id.reply_close_btn);
        quotePreviewBar = findViewById(R.id.quote_preview_bar);
        quoteTv = quotePreviewBar.findViewById(R.id.reply_text);
        quoteCloseBtn = quotePreviewBar.findViewById(R.id.reply_close_btn);

        int iconSize = getResources().getDimensionPixelSize(R.dimen.chat_input_icon_size);
        ViewGroup.LayoutParams layoutParams = mEmojiInputButton.getLayoutParams();
        layoutParams.width = iconSize;
        layoutParams.height = iconSize;
        mEmojiInputButton.setLayoutParams(layoutParams);

        layoutParams = mAudioInputSwitchButton.getLayoutParams();
        layoutParams.width = iconSize;
        layoutParams.height = iconSize;
        mAudioInputSwitchButton.setLayoutParams(layoutParams);

        layoutParams = mMoreInputButton.getLayoutParams();
        layoutParams.width = iconSize;
        layoutParams.height = iconSize;
        mMoreInputButton.setLayoutParams(layoutParams);

        mIsSending = false;

        init();

    }

    protected void init() {
        mAudioInputSwitchButton.setOnClickListener(this);
        mEmojiInputButton.setOnClickListener(this);
        mMoreInputButton.setOnClickListener(this);
        mSendTextButton.setOnClickListener(this);
        mTextInput.addTextChangedListener(this);
        mTextInput.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (presenter != null) {
                        presenter.scrollToNewestMessage();
                    }
                    showSoftInput();
                }
                return false;
            }
        });

        mTextInput.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if ((isQuoteModel || isReplyModel) && TextUtils.isEmpty(mTextInput.getText().toString())) {
                        exitReply();
                    }
                }
                return false;
            }
        });

        mTextInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return false;
            }
        });

        mTextInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (!focus && mChatInputHandler != null) {
                    mChatInputHandler.onUserTyping(false, V2TIMManager.getInstance().getServerTime());
                }
            }
        });

        mSendAudioButton.setOnTouchListener(new OnTouchListener() {
            private boolean readyToCancel = false;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartRecordY = motionEvent.getY();
                        startRecordAudio();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (motionEvent.getY() - mStartRecordY < -100) {
                            readyToCancel = true;
                            readyToCancelRecordAudio();
                        } else {
                            if (readyToCancel) {
                                continueRecordAudio();
                            }
                            readyToCancel = false;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (readyToCancel) {
                            cancelRecordAudio();
                        } else {
                            stopRecordAudio();
                        }
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

        mTextInput.setOnMentionInputListener(new TIMMentionEditText.OnMentionInputListener() {
            @Override
            public void onMentionCharacterInput(String tag) {
                if ((tag.equals(TIMMentionEditText.TIM_MENTION_TAG) || tag.equals(TIMMentionEditText.TIM_MENTION_TAG_FULL))
                    && TUIChatUtils.isGroupChat(mChatLayout.getChatInfo().getType())) {
                    if (mOnInputViewListener != null) {
                        mOnInputViewListener.onStartGroupMemberSelectActivity();
                    }
                }
            }
        });

        replyCloseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                exitReply();
            }
        });

        quoteCloseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                exitReply();
            }
        });

    }

    public void addInputText(String name, String id) {
        if (id == null || id.isEmpty()) {
            return;
        }

        ArrayList<String> nameList = new ArrayList<String>() {
            { add(name); }
        };
        ArrayList<String> idList = new ArrayList<String>() {
            { add(id); }
        };

        updateAtUserInfoMap(nameList, idList);
        if (mTextInput != null) {
            Map<String, String> displayAtNameMap = new HashMap<>();
            displayAtNameMap.put(TIMMentionEditText.TIM_MENTION_TAG + displayInputString, id);
            mTextInput.setMentionMap(displayAtNameMap);
            int selectedIndex = mTextInput.getSelectionEnd();
            if (selectedIndex != -1) {
                String insertStr = TIMMentionEditText.TIM_MENTION_TAG + displayInputString;
                String text = mTextInput.getText().insert(selectedIndex, insertStr).toString();
                FaceManager.handlerEmojiText(mTextInput, text, true);
                mTextInput.setSelection(selectedIndex + insertStr.length());
            }
            showSoftInput();
        }
    }

    private void initChatbot() {
        if (TIMCommonUtil.isChatbot(mChatInfo.getId())) {
            disableAudioInput(true);
            disableEmojiInput(true);
            mMoreInputDisable = true;
            mMoreInputButton.setVisibility(GONE);
            presenter.isChatbotMessageFinished.observe((LifecycleOwner) getContext(), isChatbotMessageStopped -> {
                if (isChatbotMessageStopped) {
                    mChatboxInterruptView.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(mTextInput.getText())) {
                        mSendTextButton.setVisibility(VISIBLE);
                    }
                } else {
                    mChatboxInterruptView.setVisibility(View.VISIBLE);
                    mSendTextButton.setVisibility(GONE);
                }
            });
            mChatboxInterruptView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.sendChatbotInterruptMessage();
                }
            });
        }
    }

    private void startRecordAudio() {
        AudioRecorder.startRecord(new AudioRecorder.AudioRecorderCallback() {
            @Override
            public void onStarted() {
                if (mChatInputHandler != null) {
                    mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_START);
                }
                mSendAudioButton.setText(TUIChatService.getAppContext().getString(R.string.release_end));
            }

            @Override
            public void onFailed(int errorCode, String errorMessage) {
                if (errorCode == AudioRecorder.ERROR_CODE_MIC_IS_BEING_USED || errorCode == TUIConstants.TUICalling.ERROR_STATUS_IN_CALL) {
                    ToastUtil.toastLongMessage(TUIChatService.getAppContext().getString(R.string.chat_mic_is_being_used_cant_record));
                } else {
                    ToastUtil.toastLongMessage(TUIChatService.getAppContext().getString(R.string.chat_record_audio_failed));
                }
                TUIChatLog.e(TAG, "record audio failed, errorCode " + errorCode + ", errorMessage " + errorMessage);
                if (mChatInputHandler != null) {
                    mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_FAILED);
                }
                mSendAudioButton.setText(TUIChatService.getAppContext().getString(R.string.hold_say));
            }

            @Override
            public void onFinished(String outputPath) {
                int duration = AudioRecorder.getDuration(outputPath);
                mSendAudioButton.setText(TUIChatService.getAppContext().getString(R.string.hold_say));
                if (duration < 1000) {
                    mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_TOO_SHORT);
                    return;
                }
                if (mChatInputHandler != null) {
                    mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_STOP);
                }
                sendAudioMessage(outputPath, duration);
            }

            @Override
            public void onCanceled() {
                if (mChatInputHandler != null) {
                    mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_CANCEL);
                }
                mSendAudioButton.setText(TUIChatService.getAppContext().getString(R.string.hold_say));
            }
        });
    }

    private void sendAudioMessage(String outputPath, int duration) {
        if (mMessageHandler != null) {
            mMessageHandler.sendMessage(ChatMessageBuilder.buildAudioMessage(outputPath, duration));
        }
    }

    private void readyToCancelRecordAudio() {
        if (mChatInputHandler != null) {
            mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_READY_TO_CANCEL);
        }
    }

    private void continueRecordAudio() {
        if (mChatInputHandler != null) {
            mChatInputHandler.onRecordStatusChanged(ChatInputHandler.RECORD_CONTINUE);
        }
    }

    private void cancelRecordAudio() {
        AudioRecorder.cancelRecord();
    }

    private void stopRecordAudio() {
        AudioRecorder.stopRecord();
    }

    public void updateInputText(ArrayList<String> names, ArrayList<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        updateAtUserInfoMap(names, ids);
        if (mTextInput != null) {
            Map<String, String> displayAtNameList = getDisplayAtNameMap(names, ids);
            mTextInput.setMentionMap(displayAtNameList);

            int selectedIndex = mTextInput.getSelectionEnd();
            if (selectedIndex != -1) {
                String text = mTextInput.getText().insert(selectedIndex, displayInputString).toString();
                FaceManager.handlerEmojiText(mTextInput, text, true);
                mTextInput.setSelection(selectedIndex + displayInputString.length());
            }

            // Afterwards @, the soft keyboard is to be displayed. Activity does not have onResume, so the soft keyboard cannot be displayed
            ThreadUtils.postOnUiThreadDelayed(new Runnable() {
                @Override
                public void run() {
                    showSoftInput();
                }
            }, 200);
        }
    }

    private Map<String, String> getDisplayAtNameMap(List<String> names, List<String> ids) {
        Map<String, String> displayNameList = new HashMap<>();
        String mentionTag = TIMMentionEditText.TIM_MENTION_TAG;
        if (mTextInput != null) {
            Editable editable = mTextInput.getText();
            int selectionIndex = mTextInput.getSelectionEnd();
            if (editable != null && selectionIndex > 0) {
                String text = editable.toString();
                if (!TextUtils.isEmpty(text)) {
                    mentionTag = text.substring(selectionIndex - 1, selectionIndex);
                }
            }
        }

        for (int i = 0; i < ids.size(); i++) {
            if (i == 0) {
                if (TextUtils.isEmpty(names.get(0))) {
                    displayNameList.put(mentionTag + ids.get(0) + " ", ids.get(0));
                } else {
                    displayNameList.put(mentionTag + names.get(0) + " ", ids.get(0));
                }
                continue;
            }
            String str = TIMMentionEditText.TIM_MENTION_TAG;
            if (TextUtils.isEmpty(names.get(i))) {
                str += ids.get(i);
            } else {
                str += names.get(i);
            }
            str += " ";
            displayNameList.put(str, ids.get(i));
        }
        return displayNameList;
    }

    private void updateAtUserInfoMap(ArrayList<String> names, ArrayList<String> ids) {
        displayInputString = "";

        for (int i = 0; i < ids.size(); i++) {
            atUserInfoMap.put(ids.get(i), names.get(i));

            // for display
            if (TextUtils.isEmpty(names.get(i))) {
                displayInputString += ids.get(i);
                displayInputString += " ";
                displayInputString += TIMMentionEditText.TIM_MENTION_TAG;
            } else {
                displayInputString += names.get(i);
                displayInputString += " ";
                displayInputString += TIMMentionEditText.TIM_MENTION_TAG;
            }
        }

        if (!displayInputString.isEmpty()) {
            displayInputString = displayInputString.substring(0, displayInputString.length() - 1);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mTextInput.removeTextChangedListener(this);
        atUserInfoMap.clear();

        if (mChatInputHandler != null) {
            mChatInputHandler.onUserTyping(false, V2TIMManager.getInstance().getServerTime());
        }
    }

    protected void startSendPhoto() {
        TUIChatLog.i(TAG, "startSendPhoto");
        AlbumPicker.pickMedia(mInputMoreFragment.getActivity(), new AlbumPickerListener() {
            @Override
            public void onFinished(Uri originalUri, Uri transcodeUri) {
                sendPhotoVideoMessage(originalUri, transcodeUri);
            }

            @Override
            public void onProgress(Uri originalUri, int progress)  {
                presenter.updateMessageProgress(originalUri, progress);
            }

            @Override
            public void onOriginalMediaPicked(Uri originalUri) {
                presenter.addPlaceholderMessage(originalUri);
                hideSoftInput();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void sendPhotoVideoMessage(Uri uri) {
        presenter.sendPhotoVideoMessages(mInputMoreFragment.getActivity(), uri, null);
        hideSoftInput();
    }

    private void sendPhotoVideoMessage(Uri original, Uri transcodeUri) {
        presenter.sendPhotoVideoMessages(mInputMoreFragment.getActivity(), original, transcodeUri);
        ThreadUtils.runOnUiThread(this::hideSoftInput);
    }

    protected void takePhoto() {
        TUIChatLog.i(TAG, "takePhoto");

        VideoRecorder.openCamera(mInputMoreFragment, new TUIValueCallback<Uri>() {

            @Override
            public void onSuccess(Uri uri) {
                sendPhotoVideoMessage(uri);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                TUIChatLog.e(TAG, "takePhoto errorCode: " + errorCode + " errorMessage: " + errorMessage);
            }
        });
    }

    protected void recordVideo() {
        TUIChatLog.i(TAG, "openVideoRecord");

        VideoRecorder.openVideoRecorder(mInputMoreFragment, new TUIValueCallback<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                sendPhotoVideoMessage(uri);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                TUIChatLog.i(TAG, "openVideoRecord errorCode: " + errorCode + " errorMessage: " + errorMessage);
            }
        });
    }

    public void setChatInputHandler(ChatInputHandler handler) {
        this.mChatInputHandler = handler;
    }

    public void setMessageHandler(MessageHandler handler) {
        this.mMessageHandler = handler;
    }

    public void setOnInputViewListener(OnInputViewListener listener) {
        this.mOnInputViewListener = listener;
    }

    public void setChatInputMoreListener(ChatInputMoreListener chatInputMoreListener) {
        this.chatInputMoreListener = chatInputMoreListener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.voice_input_switch) {
            if (mCurrentState == STATE_FACE_INPUT || mCurrentState == STATE_ACTION_INPUT) {
                mCurrentState = STATE_VOICE_INPUT;
                mInputMoreView.setVisibility(View.GONE);
                mEmojiInputButton.setImageResource(R.drawable.action_face_selector);
            } else if (mCurrentState == STATE_SOFT_INPUT) {
                mCurrentState = STATE_VOICE_INPUT;
            } else {
                mCurrentState = STATE_SOFT_INPUT;
            }
            if (mCurrentState == STATE_VOICE_INPUT) {
                mSendAudioButton.setVisibility(VISIBLE);
                mTextInput.setVisibility(GONE);
                mAudioInputSwitchButton.setImageResource(R.drawable.chat_input_keyboard);
                hideInputMoreLayout();
                hideSoftInput();
            } else {
                mAudioInputSwitchButton.setImageResource(R.drawable.action_audio_selector);
                mSendAudioButton.setVisibility(GONE);
                mTextInput.setVisibility(VISIBLE);
                showSoftInput();
            }
        } else if (view.getId() == R.id.face_btn) {
            mAudioInputSwitchButton.setImageResource(R.drawable.action_audio_selector);
            if (mCurrentState == STATE_VOICE_INPUT) {
                mCurrentState = STATE_NONE_INPUT;
                mSendAudioButton.setVisibility(GONE);
                mTextInput.setVisibility(VISIBLE);
            }
            if (mCurrentState == STATE_FACE_INPUT) {
                mCurrentState = STATE_SOFT_INPUT;
                mEmojiInputButton.setImageResource(R.drawable.action_face_selector);
                mTextInput.setVisibility(VISIBLE);
                showSoftInput();
            } else {
                mCurrentState = STATE_FACE_INPUT;
                mEmojiInputButton.setImageResource(R.drawable.chat_input_keyboard);
                showFaceViewGroup();
            }
        } else if (view.getId() == R.id.more_btn) {
            hideSoftInput();
            if (mMoreInputEvent instanceof View.OnClickListener) {
                ((View.OnClickListener) mMoreInputEvent).onClick(view);
            } else if (mMoreInputEvent instanceof BaseInputFragment) {
                showCustomInputMoreFragment();
            } else {
                if (mCurrentState == STATE_ACTION_INPUT) {
                    mCurrentState = STATE_NONE_INPUT;
                    mInputMoreView.setVisibility(View.VISIBLE);
                } else {
                    showInputMoreLayout();
                    mCurrentState = STATE_ACTION_INPUT;
                    mAudioInputSwitchButton.setImageResource(R.drawable.action_audio_selector);
                    mEmojiInputButton.setImageResource(R.drawable.action_face_selector);
                    mSendAudioButton.setVisibility(GONE);
                    mTextInput.setVisibility(VISIBLE);
                }
            }
        } else if (view.getId() == R.id.send_btn) {
            sendTextMessage();
        }
    }

    private void sendTextMessage() {
        if (mSendEnable) {
            if (!mTextInput.getText().toString().isEmpty()) {
                Parameter parameter = new Parameter();
                parameter.add("text", mTextInput.getText().toString());

                N.APIThb(ThbApiService.class)
                        .text_filter(parameter.buildJsonBody())
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(TextFilterBean.class))
                        .subscribe(new NetObserverThb<TextFilterBean>(getContext()) {
                            @Override
                            public void onSuccess(TextFilterBean it) {
                                if (it != null && !it.getFiltered_text().isEmpty()) {
                                    if (mMessageHandler != null) {
                                        if (mChatLayout == null) {
                                            mMessageHandler.sendMessage(ChatMessageBuilder.buildTextMessage(it.getFiltered_text()));
                                        } else {
                                            if ((isQuoteModel || isReplyModel) && replyPreviewBean != null) {
                                                if (TUIChatUtils.isGroupChat(mChatLayout.getChatInfo().getType()) && !mTextInput.getMentionIdList().isEmpty()) {
                                                    List<String> atUserList = new ArrayList<>(mTextInput.getMentionIdList());
                                                    mMessageHandler.sendMessage(ChatMessageBuilder.buildAtReplyMessage(it.getFiltered_text(), atUserList, replyPreviewBean));
                                                } else {
                                                    mMessageHandler.sendMessage(ChatMessageBuilder.buildReplyMessage(it.getFiltered_text(), replyPreviewBean));
                                                }
                                                exitReply();
                                            } else {
                                                if (TUIChatUtils.isGroupChat(mChatLayout.getChatInfo().getType()) && !mTextInput.getMentionIdList().isEmpty()) {
                                                    //  When sending, get the ID list from the map by getting the nickname list that matches the @ in the input box.
                                                    List<String> atUserList = new ArrayList<>(mTextInput.getMentionIdList());
                                                    if (atUserList.isEmpty()) {
                                                        mMessageHandler.sendMessage(ChatMessageBuilder.buildTextMessage(it.getFiltered_text()));
                                                    } else {
                                                        mMessageHandler.sendMessage(ChatMessageBuilder.buildTextAtMessage(atUserList, it.getFiltered_text()));
                                                    }
                                                } else {
                                                    mMessageHandler.sendMessage(ChatMessageBuilder.buildTextMessage(it.getFiltered_text()));
                                                }
                                            }
                                        }
                                    }
                                    mIsSending = true;
                                    mTextInput.setText("");
                                }
                            }

                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }

    public void showSoftInput() {
        TUIChatLog.i(TAG, "showSoftInput");
        mCurrentState = STATE_SOFT_INPUT;
        mTextInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mTextInput, 0);
        ThreadUtils.postOnUiThreadDelayed(new Runnable() {
            @Override
            public void run() {
                hideInputMoreLayout();
                mAudioInputSwitchButton.setImageResource(R.drawable.action_audio_selector);
                mEmojiInputButton.setImageResource(R.drawable.chat_input_face);
                mSendAudioButton.setVisibility(GONE);
                mTextInput.setVisibility(VISIBLE);
                Context context = getContext();
                if (context instanceof Activity) {
                    Window window = ((Activity) context).getWindow();
                    if (window != null) {
                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mChatInputHandler != null) {
                            mChatInputHandler.onInputAreaClick();
                        }
                    }
                }, 100);
            }
        }, 180);
    }

    public void hideSoftInput() {
        TUIChatLog.i(TAG, "hideSoftInput");
        mTextInput.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mTextInput.getWindowToken(), 0);
        Context context = getContext();
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            if (window != null) {
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            }
        }
    }

    public void onEmptyClick() {
        if (mCurrentState == STATE_SOFT_INPUT) {
            hideSoftInput();
            mCurrentState = STATE_SOFT_INPUT;
            mEmojiInputButton.setImageResource(R.drawable.action_face_selector);
            mAudioInputSwitchButton.setImageResource(R.drawable.action_audio_selector);
            mSendAudioButton.setVisibility(GONE);
            mTextInput.setVisibility(VISIBLE);
        }
        hideInputMoreLayout();

    }

    public void disableShowCustomFace(boolean disable) {
        isShowCustomFace = !disable;
    }

    private void showFaceViewGroup() {
        TUIChatLog.i(TAG, "showFaceViewGroup");
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getSupportFragmentManager();
        }
        if (mFaceFragment == null) {
            mFaceFragment = new FaceFragment();
        }
        hideSoftInput();
        mInputMoreView.setVisibility(View.VISIBLE);
        mTextInput.requestFocus();
        mFaceFragment.setShowCustomFace(isShowCustomFace);
        mFaceFragment.setBackgroundColor(getResources().getColor(R.color.tuichat_face_view_bg));
        mFaceFragment.setListener(new OnFaceInputListener() {
            @Override
            public void onDeleteClicked() {
                mTextInput.getInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            }

            @Override
            public void onEmojiClicked(String emojiKey) {
                int index = mTextInput.getSelectionStart();
                Editable editable = mTextInput.getText();
                editable.insert(index, emojiKey);
                FaceManager.handlerEmojiText(mTextInput, editable, true);
            }

            @Override
            public void onSendClicked() {
                sendTextMessage();
            }

            @Override
            public void onFaceClicked(ChatFace face) {
                TUIMessageBean messageBean = ChatMessageBuilder.buildFaceMessage(face.getFaceGroup().getGroupID(), face.getFaceKey());
                mMessageHandler.sendMessage(messageBean);
            }
        });
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mFaceFragment).commitAllowingStateLoss();
        if (mChatInputHandler != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mChatInputHandler.onInputAreaClick();
                }
            }, 100);
        }
    }

    private void showCustomInputMoreFragment() {
        TUIChatLog.i(TAG, "showCustomInputMoreFragment");
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getSupportFragmentManager();
        }
        BaseInputFragment fragment = (BaseInputFragment) mMoreInputEvent;
        hideSoftInput();
        mInputMoreView.setVisibility(View.VISIBLE);
        mFragmentManager.beginTransaction().replace(R.id.more_groups, fragment).commitAllowingStateLoss();
        if (mChatInputHandler != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mChatInputHandler.onInputAreaClick();
                }
            }, 100);
        }
    }

    private void showInputMoreLayout() {
        TUIChatLog.i(TAG, "showInputMoreLayout");
        if (mFragmentManager == null) {
            mFragmentManager = mActivity.getSupportFragmentManager();
        }
        if (mInputMoreFragment == null) {
            mInputMoreFragment = new InputMoreFragment();
        }

        assembleActions();
        mInputMoreFragment.setActions(mInputMoreActionList);
        hideSoftInput();
        mInputMoreView.setVisibility(View.VISIBLE);
        mFragmentManager.beginTransaction().replace(R.id.more_groups, mInputMoreFragment).commitAllowingStateLoss();
        if (mChatInputHandler != null) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mChatInputHandler.onInputAreaClick();
                }
            }, 100);
        }
    }

    private void hideInputMoreLayout() {
        mInputMoreView.setVisibility(View.GONE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mInputContent = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(s.toString().trim())) {
            mSendEnable = false;
            mSendTextButton.setVisibility(GONE);
            showMoreInputButton(View.VISIBLE);
            if (mChatInputHandler != null) {
                mChatInputHandler.onUserTyping(false, V2TIMManager.getInstance().getServerTime());
            }
        } else {
            mSendEnable = true;
            showSendTextButton();
            showMoreInputButton(View.GONE);
            if (mTextInput.getLineCount() != mLastMsgLineCount) {
                mLastMsgLineCount = mTextInput.getLineCount();
                if (mChatInputHandler != null) {
                    mChatInputHandler.onInputAreaClick();
                }
            }
            if (!TextUtils.equals(mInputContent, mTextInput.getText().toString())) {
                FaceManager.handlerEmojiText(mTextInput, mTextInput.getText(), true);
            }
        }

        if (mChatInputHandler != null && !mIsSending) {
            mChatInputHandler.onUserTyping(true, V2TIMManager.getInstance().getServerTime());
        }

        if (mIsSending) {
            mIsSending = false;
        }
    }

    public void setDraft() {
        if (mChatInfo == null) {
            TUIChatLog.e(TAG, "set drafts error :  chatInfo is null");
            return;
        }
        if (mTextInput == null) {
            TUIChatLog.e(TAG, "set drafts error :  textInput is null");
            return;
        }

        String draftText = mTextInput.getText().toString();
        if ((isQuoteModel || isReplyModel) && replyPreviewBean != null) {
            Gson gson = new Gson();
            Map<String, String> draftMap = new HashMap<>();
            draftMap.put("content", draftText);
            draftMap.put("reply", gson.toJson(replyPreviewBean));
            draftText = gson.toJson(draftMap);
        }
        if (presenter != null) {
            presenter.setDraft(draftText);
        }
    }

    public void appendText(String text) {
        if (mChatInfo == null) {
            TUIChatLog.e(TAG, "appendText error :  chatInfo is null");
            return;
        }
        if (mTextInput == null) {
            TUIChatLog.e(TAG, "appendText error :  textInput is null");
            return;
        }
        String draftText = mTextInput.getText().toString();
        draftText += text;
        mTextInput.setText(draftText);
        mTextInput.setSelection(mTextInput.getText().length());
    }

    public void setChatInfo(ChatInfo chatInfo) {
        mChatInfo = chatInfo;
        if (chatInfo != null) {
            DraftInfo draftInfo = chatInfo.getDraft();
            if (draftInfo != null && !TextUtils.isEmpty(draftInfo.getDraftText()) && mTextInput != null) {
                Gson gson = new Gson();
                HashMap draftJsonMap;
                String content = draftInfo.getDraftText();
                try {
                    draftJsonMap = gson.fromJson(draftInfo.getDraftText(), HashMap.class);
                    if (draftJsonMap != null) {
                        content = (String) draftJsonMap.get("content");
                        String draftStr = (String) draftJsonMap.get("reply");
                        ReplyPreviewBean bean = gson.fromJson(draftStr, ReplyPreviewBean.class);
                        if (bean != null) {
                            showReplyPreview(bean);
                        }
                    }
                } catch (JsonSyntaxException e) {
                    TUIChatLog.e(TAG, " getCustomJsonMap error ");
                }

                mTextInput.setText(content);
                mTextInput.setSelection(mTextInput.getText().length());
            }

            if (!TextUtils.isEmpty(getChatInfo().getSendMsg())) {
                if (mMessageHandler != null) {
                    mMessageHandler.sendMessage(ChatMessageBuilder.buildTextMessage(getChatInfo().getSendMsg()));
                }
            }
        }
        initChatbot();
    }

    public void setChatLayout(IChatLayout chatLayout) {
        mChatLayout = chatLayout;
    }

    protected void assembleActions() {
        mInputMoreActionList.clear();

        List<Integer> excludeItems = new ArrayList<>();
        TUIChatConfigClassic.ChatInputMoreDataSource dataSource = TUIChatConfigClassic.getChatInputMoreDataSource();
        if (dataSource != null) {
            excludeItems.addAll(dataSource.inputBarShouldHideItemsInMoreMenuOfInfo(mChatInfo));
            mInputMoreActionList.addAll(dataSource.inputBarShouldAddNewItemToMoreMenuOfInfo(mChatInfo));
        }

        InputMoreItem actionUnit;
        if (TUIChatConfigClassic.isShowInputBarAlbum()
                && getChatInfo().isEnableAlbum()
                && !excludeItems.contains(TUIChatConfigClassic.ALBUM)) {
            actionUnit = new InputMoreItem() {
                @Override
                public void onAction(String chatInfoId, int chatType) {
                    startSendPhoto();
                }
            };
            actionUnit.setIconResId(R.drawable.ic_more_picture);
            actionUnit.setName(getString(R.string.pic));
            actionUnit.setPriority(1000);
            mInputMoreActionList.add(actionUnit);
        }

        if (TUIChatConfigClassic.isShowInputBarTakePhoto()
                && getChatInfo().isEnableTakePhoto()
                && !excludeItems.contains(TUIChatConfigClassic.TAKE_PHOTO)) {
            actionUnit = new InputMoreItem() {
                @Override
                public void onAction(String chatInfoId, int chatType) {
                    takePhoto();
                }
            };
            actionUnit.setIconResId(R.drawable.ic_more_camera);
            actionUnit.setName(getString(R.string.photo));
            actionUnit.setPriority(900);
            mInputMoreActionList.add(actionUnit);
        }

        if (TUIChatConfigClassic.isShowInputBarRecordVideo()
                && getChatInfo().isEnableRecordVideo()
                && !excludeItems.contains(TUIChatConfigClassic.RECORD_VIDEO)) {
            actionUnit = new InputMoreItem() {
                @Override
                public void onAction(String chatInfoId, int chatType) {
                    recordVideo();
                }
            };
            actionUnit.setIconResId(R.drawable.ic_more_video);
            actionUnit.setPriority(800);
            actionUnit.setName(getString(R.string.video));
            mInputMoreActionList.add(actionUnit);
        }

        /*************************/

        actionUnit = new InputMoreItem() {
            @Override
            public void onAction(String chatInfoId, int chatType) {
                sendGoods();
            }
        };
        actionUnit.setIconResId(R.drawable.ic_chat_goods);
        actionUnit.setPriority(700);
        actionUnit.setName("发送商品");
        mInputMoreActionList.add(actionUnit);

        /*************************/

        actionUnit = new InputMoreItem() {
            @Override
            public void onAction(String chatInfoId, int chatType) {
                sendOrder(mActivity);
            }
        };
        actionUnit.setIconResId(R.drawable.ic_chat_order);
        actionUnit.setPriority(600);
        actionUnit.setName("发送订单");
        mInputMoreActionList.add(actionUnit);

        /*************************/

        actionUnit = new InputMoreItem() {
            @Override
            public void onAction(String chatInfoId, int chatType) {
                setReport(mActivity);
            }
        };
        actionUnit.setIconResId(R.drawable.ic_chat_lh);
        actionUnit.setPriority(500);
        actionUnit.setName("举报");
        mInputMoreActionList.add(actionUnit);

        List<InputMoreItem> extensionList = getExtensionInputMoreList();
        mInputMoreActionList.addAll(extensionList);

        Collections.sort(mInputMoreActionList, new Comparator<InputMoreItem>() {
            @Override
            public int compare(InputMoreItem o1, InputMoreItem o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
    }


    private String getString(int stringID) {
        return getResources().getString(stringID);
    }

    private List<InputMoreItem> getExtensionInputMoreList() {
        List<InputMoreItem> list = new ArrayList<>();
        List<Integer> excludeItems = new ArrayList<>();
        TUIChatConfigClassic.ChatInputMoreDataSource dataSource = TUIChatConfigClassic.getChatInputMoreDataSource();
        if (dataSource != null) {
            excludeItems.addAll(dataSource.inputBarShouldHideItemsInMoreMenuOfInfo(mChatInfo));
        }
        Map<String, Object> param = new HashMap<>();
        param.put(TUIConstants.TUIChat.Extension.InputMore.CONTEXT, getContext());
        if (TYPE_C2C == mChatInfo.getType()) {
            param.put(TUIConstants.TUIChat.Extension.InputMore.USER_ID, mChatInfo.getId());
        } else {
            param.put(TUIConstants.TUIChat.Extension.InputMore.GROUP_ID, mChatInfo.getId());
        }
        if (mChatInfo.getType() == ChatInfo.TYPE_GROUP && TUIChatUtils.isTopicGroup(mChatInfo.getId())) {
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_VIDEO_CALL, true);
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_VOICE_CALL, true);
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_ROOM, true);
        } else {
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_VIDEO_CALL,
                !TUIChatConfigClassic.isShowInputBarVideoCall() || !getChatInfo().isEnableVideoCall()
                    || excludeItems.contains(TUIChatConfigClassic.VIDEO_CALL));
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_VOICE_CALL,
                !TUIChatConfigClassic.isShowInputBarAudioCall() || !getChatInfo().isEnableAudioCall()
                    || excludeItems.contains(TUIChatConfigClassic.AUDIO_CALL));
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_ROOM,
                !TUIChatConfigClassic.isShowInputBarRoomKit() || !getChatInfo().isEnableRoom() || excludeItems.contains(TUIChatConfigClassic.ROOM_KIT));
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_GROUP_NOTE,
                !TUIChatConfigClassic.isShowInputBarGroupNote() || !getChatInfo().isEnableGroupNote()
                    || excludeItems.contains(TUIChatConfigClassic.GROUP_NOTE));
            param.put(TUIConstants.TUIChat.Extension.InputMore.FILTER_POLL,
                !TUIChatConfigClassic.isShowInputBarPoll() || !getChatInfo().isEnablePoll() || excludeItems.contains(TUIChatConfigClassic.POLL));
        }
        param.put(TUIConstants.TUIChat.Extension.InputMore.INPUT_MORE_LISTENER, chatInputMoreListener);
        List<TUIExtensionInfo> extensionList = TUICore.getExtensionList(TUIConstants.TUIChat.Extension.InputMore.CLASSIC_EXTENSION_ID, param);
        for (TUIExtensionInfo extensionInfo : extensionList) {
            if (extensionInfo != null) {
                String name = extensionInfo.getText();
                int icon = (int) extensionInfo.getIcon();
                int priority = extensionInfo.getWeight();
                InputMoreItem unit = new InputMoreItem() {
                    @Override
                    public void onAction(String chatInfoId, int chatType) {
                        TUIExtensionEventListener extensionListener = extensionInfo.getExtensionListener();
                        if (extensionListener != null) {
                            extensionListener.onClicked(null);
                        }
                    }
                };
                unit.setName(name);
                unit.setIconResId(icon);
                unit.setPriority(priority);
                list.add(unit);
            }
        }
        return list;
    }

    public void disableAudioInput(boolean disable) {
        mAudioInputDisable = disable;
        if (disable) {
            mAudioInputSwitchButton.setVisibility(GONE);
        } else {
            mAudioInputSwitchButton.setVisibility(VISIBLE);
        }
    }

    public void disableEmojiInput(boolean disable) {
        mEmojiInputDisable = disable;
        if (disable) {
            mEmojiInputButton.setVisibility(GONE);
        } else {
            mEmojiInputButton.setVisibility(VISIBLE);
        }
    }

    public void disableMoreInput(boolean disable) {
        mMoreInputDisable = disable;
        if (disable) {
            mMoreInputButton.setVisibility(GONE);
            mSendTextButton.setVisibility(VISIBLE);
        } else {
            mMoreInputButton.setVisibility(VISIBLE);
            mSendTextButton.setVisibility(GONE);
        }
    }

    public void replaceMoreInput(BaseInputFragment fragment) {
        mMoreInputEvent = fragment;
    }

    public void replaceMoreInput(OnClickListener listener) {
        mMoreInputEvent = listener;
    }

    public EditText getInputText() {
        return mTextInput;
    }

    protected void showMoreInputButton(int visibility) {
        if (mMoreInputDisable) {
            return;
        }
        mMoreInputButton.setVisibility(visibility);
    }

    protected void showSendTextButton() {
        boolean isChatbotMessageFinished = true;
        if (presenter != null) {
            isChatbotMessageFinished = presenter.isChatbotMessageFinished.getValue();
        }
        if (isChatbotMessageFinished) {
            mSendTextButton.setVisibility(VISIBLE);
        } else {
            mSendTextButton.setVisibility(GONE);
        }
    }

    public void showReplyPreview(ReplyPreviewBean previewBean) {
        exitReply();
        replyPreviewBean = previewBean;
        String replyMessageAbstract = previewBean.getMessageAbstract();
        String msgTypeStr = ChatMessageParser.getMsgTypeStr(previewBean.getMessageType());
        CharSequence text = previewBean.getMessageSenderName() + " : " + msgTypeStr + " " + replyMessageAbstract;
        text = FaceManager.emojiJudge(text);
        // If replying to a text message, the middle part of the file name is displayed in abbreviated form
        if (previewBean.isReplyMessage()) {
            isReplyModel = true;
            replyTv.setText(text);
            replyPreviewBar.setVisibility(View.VISIBLE);
        } else {
            isQuoteModel = true;
            quoteTv.setText(text);
            quotePreviewBar.setVisibility(View.VISIBLE);
        }

        if (previewBean.getOriginalMessageBean() instanceof FileMessageBean) {
            replyTv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            quoteTv.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        } else {
            replyTv.setEllipsize(TextUtils.TruncateAt.END);
            quoteTv.setEllipsize(TextUtils.TruncateAt.END);
        }

        if (mMessageHandler != null) {
            mMessageHandler.scrollToEnd();
        }

        showSoftInput();
    }

    public void exitReply() {
        isReplyModel = false;
        replyPreviewBean = null;
        replyPreviewBar.setVisibility(View.GONE);
        isQuoteModel = false;
        quotePreviewBar.setVisibility(View.GONE);
        updateChatBackground();
    }

    private void updateChatBackground() {
        if (mOnInputViewListener != null) {
            mOnInputViewListener.onUpdateChatBackground();
        }
    }

    protected void showEmojiInputButton(int visibility) {
        if (mEmojiInputDisable) {
            return;
        }
        mEmojiInputButton.setVisibility(visibility);
    }

    public ChatInfo getChatInfo() {
        return mChatInfo;
    }

    public interface MessageHandler {
        void sendMessage(TUIMessageBean msg);

        default void sendMessages(List<TUIMessageBean> messageBeans) {}

        void scrollToEnd();
    }

    public interface ChatInputHandler {
        int RECORD_START = 1;
        int RECORD_STOP = 2;
        int RECORD_CANCEL = 3;
        int RECORD_TOO_SHORT = 4;
        int RECORD_FAILED = 5;
        int RECORD_CONTINUE = 6;
        int RECORD_READY_TO_CANCEL = 7;

        void onInputAreaClick();

        void onRecordStatusChanged(int status);

        void onUserTyping(boolean status, long time);
    }

    public interface OnInputViewListener {
        void onStartGroupMemberSelectActivity();

        void onUpdateChatBackground();
    }

    private void sendGoods() {
        List<GoodsListBean.GoodsDTO> adapterListAll = new ArrayList<>();

        //页码
        pageIndexGoodsList = 1;

        // 创建Dialog实例
        Dialog customDialogGoodsList = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        customDialogGoodsList.setContentView(R.layout.dialog_send_goods);
        // 设置点击外部不消失
        customDialogGoodsList.setCancelable(true);

        SmartRefreshLayout refreshIndexGoodsList =  customDialogGoodsList.findViewById(R.id.refreshIndex);
        EditText et_search =  customDialogGoodsList.findViewById(R.id.et_search);
        TextView tv_search =  customDialogGoodsList.findViewById(R.id.tv_search);
        TextView game_all =  customDialogGoodsList.findViewById(R.id.game_all);
        TextView game_mobile =  customDialogGoodsList.findViewById(R.id.game_mobile);
        TextView game_pc =  customDialogGoodsList.findViewById(R.id.game_pc);
        TextView emptyLayoutGoodsList =  customDialogGoodsList.findViewById(R.id.emptyLayout);
        RecyclerView recycler_view_all =  customDialogGoodsList.findViewById(R.id.recycler_view_all);
        ImageView iv_close =  customDialogGoodsList.findViewById(R.id.iv_close);

        // 创建适配器
        GoodListAdapter adapterRecyclerViewAll = new GoodListAdapter((Activity) getContext(), adapterListAll);
        recycler_view_all.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        // 设置适配器
        recycler_view_all.setAdapter(adapterRecyclerViewAll);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogGoodsList.dismiss();
            }
        });

        game_all.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
        game_all.setTextColor(getResources().getColor(R.color.color_402802));
        game_mobile.setBackground(null);
        game_mobile.setTextColor(getResources().getColor(R.color.color_D6D6D6));
        game_pc.setBackground(null);
        game_pc.setTextColor(getResources().getColor(R.color.color_D6D6D6));
        emptyLayoutGoodsList.setVisibility(View.VISIBLE);
        recycler_view_all.setVisibility(GONE);

        game_all.setOnClickListener(c -> {
            goodsListShowType = 0;
            pageIndexGoodsList = 1;
            game_all.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
            game_all.setTextColor(getResources().getColor(R.color.color_402802));
            game_mobile.setBackground(null);
            game_mobile.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            game_pc.setBackground(null);
            game_pc.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
        });
        game_mobile.setOnClickListener(c -> {
            goodsListShowType = 3;
            pageIndexGoodsList = 1;
            game_all.setBackground(null);
            game_all.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            game_mobile.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
            game_mobile.setTextColor(getResources().getColor(R.color.color_402802));
            game_pc.setBackground(null);
            game_pc.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
        });
        game_pc.setOnClickListener(c -> {
            goodsListShowType = 2;
            pageIndexGoodsList = 1;
            game_all.setBackground(null);
            game_all.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            game_mobile.setBackground(null);
            game_mobile.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            game_pc.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
            game_pc.setTextColor(getResources().getColor(R.color.color_402802));
            getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
        });

        getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, null, adapterRecyclerViewAll);

        adapterRecyclerViewAll.setOnItemActionListener(new GoodListAdapter.OnItemActionListener() {
            @Override
            public void onItemSendClick(int position) {
                GoodsListBean.GoodsDTO goodsDTO = adapterRecyclerViewAll.getItem(position);
                customDialogGoodsList.dismiss();
                sendCustomizeMsgGoods(mChatInfo.getType() == TYPE_C2C?true:false, 110,2, goodsDTO);
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取所有匹配项
                adapterListAll.clear();
                pageIndexGoodsList = 1;
                getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
            }
        });

        refreshIndexGoodsList.setEnableLoadMore(true);
        refreshIndexGoodsList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndexGoodsList = 1;
                getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
            }
        });
        refreshIndexGoodsList.setOnLoadMoreListener(new com.scwang.smart.refresh.layout.listener.OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (calculateTotalPages(totalCountGoodsList) <= pageIndexGoodsList) {
                    refreshIndexGoodsList.finishLoadMore();//结束加载
                    return;
                }
                pageIndexGoodsList++;
                getGoodsGoodsList(refreshIndexGoodsList, adapterListAll, emptyLayoutGoodsList, recycler_view_all, et_search.getText().toString().toLowerCase(), adapterRecyclerViewAll);
            }
        });

        customDialogGoodsList.show();
        // 设置窗口属性
        Window window = customDialogGoodsList.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    private void sendOrder(Context context) {
        if (mChatInfo.getType() == TYPE_C2C) {
            List<OrderListBeanConversation.ListDTO> adapterListAllOrders = new ArrayList<>();
            //页码
            pageIndexOrdersList = 1;

            // 创建Dialog实例
            Dialog customDialogOrdersList = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            customDialogOrdersList.setContentView(R.layout.dialog_send_order);
            // 设置点击外部不消失
            customDialogOrdersList.setCancelable(true);

            ImageView iv_closeOrders = customDialogOrdersList.findViewById(R.id.iv_close);
            SmartRefreshLayout refreshIndexOrdersList =  customDialogOrdersList.findViewById(R.id.refreshIndex);
            EditText et_searchOrders =  customDialogOrdersList.findViewById(R.id.et_search);
            TextView tv_searchOrders =  customDialogOrdersList.findViewById(R.id.tv_search);
            TextView game_allOrders =  customDialogOrdersList.findViewById(R.id.order_all);
            TextView game_mobileOrders =  customDialogOrdersList.findViewById(R.id.order_mobile);
            TextView game_pcOrders =  customDialogOrdersList.findViewById(R.id.order_pc);
            TextView emptyLayoutOrdersList =  customDialogOrdersList.findViewById(R.id.emptyLayout);
            RecyclerView recycler_view_allOrders =  customDialogOrdersList.findViewById(R.id.recycler_view_all);

            iv_closeOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialogOrdersList.dismiss();
                }
            });

            // 创建适配器
            OrderListAdapter adapterRecyclerViewAllOrders = new OrderListAdapter((Activity) context, adapterListAllOrders);
            // 设置适配器
            recycler_view_allOrders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
            recycler_view_allOrders.setAdapter(adapterRecyclerViewAllOrders);

            game_allOrders.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
            game_allOrders.setTextColor(getResources().getColor(R.color.color_402802));
            game_mobileOrders.setBackground(null);
            game_mobileOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            game_pcOrders.setBackground(null);
            game_pcOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
            emptyLayoutOrdersList.setVisibility(View.VISIBLE);
            recycler_view_allOrders.setVisibility(View.VISIBLE);

            game_allOrders.setOnClickListener(c -> {
                ordersListShowType = 0;
                pageIndexOrdersList = 1;
                game_allOrders.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
                game_allOrders.setTextColor(getResources().getColor(R.color.color_402802));
                game_mobileOrders.setBackground(null);
                game_mobileOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                game_pcOrders.setBackground(null);
                game_pcOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
            });
            game_mobileOrders.setOnClickListener(c -> {
                ordersListShowType = 3;
                pageIndexOrdersList = 1;
                game_allOrders.setBackground(null);
                game_allOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                game_mobileOrders.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
                game_mobileOrders.setTextColor(getResources().getColor(R.color.color_402802));
                game_pcOrders.setBackground(null);
                game_pcOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
            });
            game_pcOrders.setOnClickListener(c -> {
                ordersListShowType = 2;
                pageIndexOrdersList = 1;
                game_allOrders.setBackground(null);
                game_allOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                game_mobileOrders.setBackground(null);
                game_mobileOrders.setTextColor(getResources().getColor(R.color.color_D6D6D6));
                game_pcOrders.setBackground(getResources().getDrawable(R.drawable.shape_radius15_ffd497_fafbf5));
                game_pcOrders.setTextColor(getResources().getColor(R.color.color_402802));
                getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
            });

            getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, null, adapterRecyclerViewAllOrders);

            adapterRecyclerViewAllOrders.setOnItemActionListener(new OrderListAdapter.OnItemActionListener() {
                @Override
                public void onItemSendClick(int position) {
                    OrderListBeanConversation.ListDTO ordersDTO = adapterRecyclerViewAllOrders.getItem(position);
                    customDialogOrdersList.dismiss();
                    sendCustomizeMsgOrders(mChatInfo.getType() == TYPE_C2C?true:false, 110,3, ordersDTO);
                }
            });

            tv_searchOrders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 获取所有匹配项
                    adapterListAllOrders.clear();
                    pageIndexOrdersList = 1;
                    getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
                }
            });

            refreshIndexOrdersList.setEnableLoadMore(true);
            refreshIndexOrdersList.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    pageIndexOrdersList = 1;
                    getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
                }
            });

            refreshIndexOrdersList.setOnLoadMoreListener(new com.scwang.smart.refresh.layout.listener.OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    if (calculateTotalPages(totalCountOrdersList) <= pageIndexOrdersList) {
                        refreshIndexOrdersList.finishLoadMore();//结束加载
                        return;
                    }
                    pageIndexOrdersList++;
                    getOrderOrderList(refreshIndexOrdersList, adapterListAllOrders, emptyLayoutOrdersList, recycler_view_allOrders, et_searchOrders.getText().toString().toLowerCase(), adapterRecyclerViewAllOrders);
                }
            });
            customDialogOrdersList.show();
            // 设置窗口属性
            Window windowOrdersList = customDialogOrdersList.getWindow();
            if (windowOrdersList != null) {
                windowOrdersList.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                windowOrdersList.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }else {//群聊
            Parameter parameter = new Parameter();
            parameter.add("groupID", mChatInfo.getId());
            N.APIThb(ThbApiService.class)
                    .getGroupInfoByImGroupId(parameter.buildJsonBody())
                    .compose(N.IOMain())
                    .map(ThbApiService.turnThb(ImGroupInfoBean.class))
                    .subscribe(new NetObserverThb<ImGroupInfoBean>(getContext()) {
                        @Override
                        public void onSuccess(ImGroupInfoBean imGroupBean) {
                            Log.d(TAG,"imGroupBean.getImGroup().toString()="+imGroupBean.getImGroup().toString());
                            Parameter parameterSendCustomMsg = new Parameter();
                            parameterSendCustomMsg.add("send_id", TUILogin.getUserId());
                            parameterSendCustomMsg.add("recv_id", "");
                            parameterSendCustomMsg.add("content_type", 110);
                            parameterSendCustomMsg.add("sessionType", 3);
                            parameterSendCustomMsg.add("im_group_id", mChatInfo.getId());
                            parameterSendCustomMsg.add("intelligentType", 3);
                            parameterSendCustomMsg.add("platform", 2);
                            parameterSendCustomMsg.add("data", "");
                            parameterSendCustomMsg.add("good_id", 0);
                            parameterSendCustomMsg.add("order_id", imGroupBean.getImGroup().getOrder_id());
                            N.APIThb(ThbApiService.class)
                                    .sendCustomizeMsg(parameterSendCustomMsg.buildJsonBody())
                                    .compose(N.IOMain())
                                    .map(ThbApiService.turnThb(String.class))
                                    .subscribe(new NetObserverThb<String>(getContext()) {
                                        @Override
                                        public void onSuccess(String it) {
                                        }

                                        @Override
                                        protected void onFailure(BaseTHB baseTHB) {
                                            Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        protected void onFailure(BaseTHB baseTHB) {
                            Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setReport(Context context) {
        popupView = new XPopup.Builder(context)
                .isViewMode(true)
                .asCustom(new ReportDialog(context, new ReportDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String code) {
                        Parameter parameter = new Parameter();
                        parameter.add("report_reason", Integer.valueOf(code));
                        parameter.add(mChatInfo.getType() == TYPE_C2C ? "user_im_id" : "im_group_id", mChatInfo.getType() == TYPE_C2C ? mChatInfo.getLocateMessage().getUserId() : mChatInfo.getId());
                        //提交举报
                        N.APIThb(ThbApiService.class)
                                .submitReport(parameter.buildJsonBody())
                                .compose(N.IOMain())
                                .map(ThbApiService.turnThb(Object.class))
                                .subscribe(new NetObserverThb<Object>(context) {
                                    @Override
                                    public void onSuccess(Object it) {
                                        popupView.dismiss();
                                        Toast.makeText(context, "提交成功，我们会尽快处理", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    protected void onFailure(BaseTHB baseTHB) {
                                        Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })).show();
    }


    /**
     * 根据总条数获取总页码数
     * @param count
     * @return
     */
    public static int calculateTotalPages(int count) {
        if (count <= 0) return 0;
        int pageSize = 10;
        return (count + pageSize - 1) / pageSize;
    }

    /**
     * 获取我的商品列表
     */
    private void getGoodsGoodsList(SmartRefreshLayout refreshIndexGoodsList, List<GoodsListBean.GoodsDTO> adapterListAll, TextView emptyLayoutGoodsList, RecyclerView recycler_view_all, String content, GoodListAdapter adapterRecyclerViewAll){
        Parameter parameterGoodList = new Parameter();
        parameterGoodList.add("page", pageIndexGoodsList);
        parameterGoodList.add("review_status", 1);//发布商品状态  0：审核中 1：已上架 2：已下架  3:审核失败
        if (mChatInfo.getType() != TYPE_C2C) {
            parameterGoodList.add("pattern_id", 1);//过滤交易模式 0全部 1寄售
        }
        parameterGoodList.add("keyword", content);//关键词查询 title,goods_no
        parameterGoodList.add("game_type_id", goodsListShowType);//0全部其他对应游戏类型id 2pc 3手游
        N.APIThb(ThbApiService.class)
                .goodsGoodsList(parameterGoodList.buildJsonBody())
                .compose(N.IOMain())
                .map(ThbApiService.turnThb(GoodsListBean.class))
                .subscribe(new NetObserverThb<GoodsListBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsListBean goodsListBean) {
                        refreshIndexGoodsList.finishRefresh();
                        refreshIndexGoodsList.finishLoadMore();
                        if(pageIndexGoodsList == 1){
                            adapterListAll.clear();
                        }
                        totalCountGoodsList = goodsListBean.getTotal();
                        if (goodsListBean.getGoods() != null) {
                            adapterListAll.addAll(goodsListBean.getGoods());
                        }
                        adapterRecyclerViewAll.notifyDataSetChanged();
                        emptyLayoutGoodsList.setVisibility(adapterListAll.size() == 0 ? View.VISIBLE : GONE);
                        recycler_view_all.setVisibility(adapterListAll.size() == 0 ? GONE : View.VISIBLE);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        Toast.makeText(getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 获取订单列表
     */
    private void getOrderOrderList(SmartRefreshLayout refreshIndexOrdersList, List<OrderListBeanConversation.ListDTO> adapterListAllOrders, TextView emptyLayoutGoodsList, RecyclerView recycler_view_all, String content, OrderListAdapter adapterRecyclerViewAll){
        Parameter parameterOrderList = new Parameter();
        parameterOrderList.add("page", pageIndexOrdersList);
        parameterOrderList.add("order_status", 0);//0:全部  1：待付款  2：交易中  3：已完成  4：取消/退款
        parameterOrderList.add("user_type", 0);// 0 全部 1：买家页面订单列表   2：卖家订单页面列表
        parameterOrderList.add("game_type_id", ordersListShowType);//0全部其他对应游戏类型id 2pc 3手游
        parameterOrderList.add("keyword", content);
        N.APIThb(ThbApiService.class)
                .orderOrderList(parameterOrderList.buildJsonBody())
                .compose(N.IOMain())
                .map(ThbApiService.turnThb(OrderListBeanConversation.class))
                .subscribe(new NetObserverThb<OrderListBeanConversation>(getContext()) {
                               @Override
                               public void onSuccess(OrderListBeanConversation orderListBean) {
                                   refreshIndexOrdersList.finishRefresh();
                                   refreshIndexOrdersList.finishLoadMore();
                                   totalCountOrdersList = orderListBean.getTotal();
                                   if(pageIndexOrdersList == 1){
                                       adapterListAllOrders.clear();
                                   }
                                   if (orderListBean.getList() != null) {
                                       adapterListAllOrders.addAll(orderListBean.getList());
                                   }
                                   adapterRecyclerViewAll.notifyDataSetChanged();
                                   emptyLayoutGoodsList.setVisibility(adapterListAllOrders.size() == 0 ? View.VISIBLE : GONE);
                                   recycler_view_all.setVisibility(adapterListAllOrders.size() == 0 ? GONE : View.VISIBLE);
                               }

                               @Override
                               protected void onFailure(BaseTHB baseTHB) {
                                   Toast.makeText(getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                               }
                           }
                );
    }


    public void sendCustomizeMsgGoods(boolean isSingleChat, int content_type, int intelligentType, GoodsListBean.GoodsDTO goodsDTO){
        if(isSingleChat){//单聊
            if (goodsDTO == null) {
                Toast.makeText(getContext(), "商品信息为空", Toast.LENGTH_SHORT).show();
            } else {
                Parameter parameterSendCustomMsg = new Parameter();
                parameterSendCustomMsg.add("send_id", TUILogin.getUserId());
                parameterSendCustomMsg.add("recv_id", mChatInfo.getId());
                parameterSendCustomMsg.add("content_type", content_type);
                parameterSendCustomMsg.add("sessionType", 1);
                parameterSendCustomMsg.add("im_group_id", "");
                parameterSendCustomMsg.add("intelligentType", intelligentType);
                parameterSendCustomMsg.add("platform", 2);
                parameterSendCustomMsg.add("data", "");
                parameterSendCustomMsg.add("good_id", goodsDTO.getId());
                parameterSendCustomMsg.add("order_id", 0);
                N.APIThb(ThbApiService.class)
                        .sendCustomizeMsg(parameterSendCustomMsg.buildJsonBody())
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(String.class))
                        .subscribe(new NetObserverThb<String>(getContext()) {
                            @Override
                            public void onSuccess(String it) {
                            }

                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else {//群聊
            if (goodsDTO == null) {
                Toast.makeText(getContext(), "商品信息为空", Toast.LENGTH_SHORT).show();
            } else {
                Parameter parameterSendCustomMsg = new Parameter();
                parameterSendCustomMsg.add("send_id", TUILogin.getUserId());
                parameterSendCustomMsg.add("recv_id", "");
                parameterSendCustomMsg.add("content_type", content_type);
                parameterSendCustomMsg.add("sessionType", 3);
                parameterSendCustomMsg.add("im_group_id", mChatInfo.getId());
                parameterSendCustomMsg.add("intelligentType", intelligentType);
                parameterSendCustomMsg.add("platform", 2);
                parameterSendCustomMsg.add("data", "");
                parameterSendCustomMsg.add("good_id", goodsDTO.getId());
                parameterSendCustomMsg.add("order_id", 0);
                N.APIThb(ThbApiService.class)
                        .sendCustomizeMsg(parameterSendCustomMsg.buildJsonBody())
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(String.class))
                        .subscribe(new NetObserverThb<String>(getContext()) {
                            @Override
                            public void onSuccess(String it) {
                            }

                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
    public void sendCustomizeMsgOrders(boolean isSingleChat, int content_type, int intelligentType, OrderListBeanConversation.ListDTO orderDTO){
        if(isSingleChat){//单聊
            if (orderDTO == null) {
                Toast.makeText(getContext(), "订单信息为空", Toast.LENGTH_SHORT).show();
            } else {
                Parameter parameterSendCustomMsg = new Parameter();
                parameterSendCustomMsg.add("send_id", TUILogin.getUserId());
                parameterSendCustomMsg.add("recv_id", mChatInfo.getId());
                parameterSendCustomMsg.add("content_type", content_type);
                parameterSendCustomMsg.add("sessionType", 1);
                parameterSendCustomMsg.add("im_group_id", "");
                parameterSendCustomMsg.add("intelligentType", intelligentType);
                parameterSendCustomMsg.add("platform", 2);
                parameterSendCustomMsg.add("data", "");
                parameterSendCustomMsg.add("good_id", 0);
                parameterSendCustomMsg.add("order_id", orderDTO.getId());
                N.APIThb(ThbApiService.class)
                        .sendCustomizeMsg(parameterSendCustomMsg.buildJsonBody())
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(String.class))
                        .subscribe(new NetObserverThb<String>(getContext()) {
                            @Override
                            public void onSuccess(String it) {
                            }

                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else {//群聊
            if (orderDTO == null) {
                Toast.makeText(getContext(), "订单信息为空", Toast.LENGTH_SHORT).show();
            } else {
                Parameter parameterSendCustomMsg = new Parameter();
                parameterSendCustomMsg.add("send_id", TUILogin.getUserId());
                parameterSendCustomMsg.add("recv_id", "");
                parameterSendCustomMsg.add("content_type", content_type);
                parameterSendCustomMsg.add("sessionType", 3);
                parameterSendCustomMsg.add("im_group_id", mChatInfo.getId());
                parameterSendCustomMsg.add("intelligentType", intelligentType);
                parameterSendCustomMsg.add("platform", 2);
                parameterSendCustomMsg.add("data", "");
                parameterSendCustomMsg.add("good_id", 0);
                parameterSendCustomMsg.add("order_id", orderDTO.getId());
                N.APIThb(ThbApiService.class)
                        .sendCustomizeMsg(parameterSendCustomMsg.buildJsonBody())
                        .compose(N.IOMain())
                        .map(ThbApiService.turnThb(String.class))
                        .subscribe(new NetObserverThb<String>(getContext()) {
                            @Override
                            public void onSuccess(String it) {
                            }

                            @Override
                            protected void onFailure(BaseTHB baseTHB) {
                                Toast.makeText(getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                            }

                        });
            }
        }
    }

}
