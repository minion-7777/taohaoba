package com.tencent.qcloud.tuikit.tuiconversation.classicui.util;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;

import java.io.Serializable;

public class TUIConversationUtils {
    private static final String TAG = "TUIConversationUtils";

    public static void startChatActivity(ConversationInfo conversationInfo) {
        Bundle param = new Bundle();
        param.putInt(TUIConstants.TUIChat.CHAT_TYPE, conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);

        String chatId = conversationInfo.getId();
        if (TextUtils.isEmpty(chatId) && conversationInfo.isGroup()) {
            // getId() (groupID) was empty; try extracting from full conversationId (format: "group_<groupID>")
            String conversationId = conversationInfo.getConversationId();
            Log.w(TAG, "group chatId is empty, conversationId=" + conversationId);
            if (!TextUtils.isEmpty(conversationId) && conversationId.startsWith("group_")) {
                chatId = conversationId.substring("group_".length());
            }
        }
        Log.d(TAG, "startChatActivity isGroup=" + conversationInfo.isGroup() + " chatId=" + chatId);
        param.putString(TUIConstants.TUIChat.CHAT_ID, chatId);
        param.putString(TUIConstants.TUIChat.CHAT_NAME, conversationInfo.getTitle());
        if (conversationInfo.getDraft() != null) {
            param.putString(TUIConstants.TUIChat.DRAFT_TEXT, conversationInfo.getDraft().getDraftText());
            param.putLong(TUIConstants.TUIChat.DRAFT_TIME, conversationInfo.getDraft().getDraftTime());
        }
        param.putBoolean(TUIConstants.TUIChat.IS_TOP_CHAT, conversationInfo.isTop());
        param.putString(TUIConstants.TUIChat.FACE_URL, conversationInfo.getIconPath());
        if (conversationInfo.isGroup()) {
            param.putString(TUIConstants.TUIChat.GROUP_TYPE, conversationInfo.getGroupType());
            param.putSerializable(TUIConstants.TUIChat.AT_INFO_LIST, (Serializable) conversationInfo.getGroupAtInfoList());
        }
        if (conversationInfo.isGroup()) {
            TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
        } else {
            TUICore.startActivity(TUIConstants.TUIChat.C2C_CHAT_ACTIVITY_NAME, param);
        }
    }
}
