package io.openim.android.ouicore.utils;

import android.app.Service;
import android.os.Vibrator;
import android.text.TextUtils;

import io.openim.android.ouicore.BuildConfig;
import io.openim.android.ouicore.R;
import io.openim.android.ouicore.base.BaseApp;

public class Constants {

    public static final String baseUrlThb = BuildConfig.DEBUG ? "http://47.107.77.24:8080/" : "https://api.taohao8.vip/";
    public static final String DEFAULT_HOST = BuildConfig.DEBUG ? "http://47.107.77.24/" : "https://imserver.taohao8.vip/";
    private static final String APP_AUTH = DEFAULT_HOST;

    public static String getAppAuthUrl() {
        String url = SharedPreferencesUtil.get(BaseApp.inst()).getString("APP_AUTH_URL");
        if (TextUtils.isEmpty(url)) return APP_AUTH;
        return url;
    }

    public static String getLogLevel() {
        String level = SharedPreferencesUtil.get(BaseApp.inst()).getString(Constants.K_LOG_LEVEL);
        if (TextUtils.isEmpty(level)) return "3";
        return level;
    }

    //存储音频的文件夹
//    public static final String AUDIO_DIR = IM.getStorageDir() + "/audio/";
//    //视频存储文件夹
//    public static final String VIDEO_DIR = IM.getStorageDir() + "/video/";
//    //图片存储文件夹
//    public static final String PICTURE_DIR = IM.getStorageDir() + "/picture/";
//    //文件夹
//    public static final String File_DIR = IM.getStorageDir() + "/file/";

    //二维码
    public static class QR {
        public static final String QR_ADD_FRIEND = "taohaoba://addFriend";
        public static final String QR_JOIN_GROUP = "taohaoba://joinGroup";
    }


    public static class Event {
        //转发选人
        public static final int FORWARD = 10002;
        //音视频通话
        public static final int CALLING_REQUEST_CODE = 10003;
        //用户信息更新
        public static final int USER_INFO_UPDATE = 10004;
        //设置背景
        public static final int SET_BACKGROUND = 10005;
        //群信息更新
        public static final int UPDATE_GROUP_INFO = 10006;
        //设置群通知
        public static final int SET_GROUP_NOTIFICATION = 10007;
        //插入了消息到本地
        public static final int INSERT_MSG = 10008;
        //群解散了
        public static final int DISSOLVE_GROUP = 10009;
    }

    public static final String K_ID = "Id";
    public static final String K_GROUP_ID = "group_id";
    public static final String K_IS_PERSON = "is_person";
    public static final String K_NOTICE = "notice";
    public static final String K_NAME = "name";
    public static final String K_DATA = "data";
    public static final String K_RESULT = "result";
    public static final String K_RESULT2 = "result2";
    public static final String K_FROM = "from";
    public static final String K_SIZE = "size";
    //语言
    public static final String K_LANGUAGE_SP = "language_sp";
    //上一次登录类型 0手机号 1邮箱
    public static final String K_LOGIN_TYPE = "k_login_type";
    //最大通话人数
    public static final int MAX_CALL_NUM = 9;
    //好友红点
    public static final String K_FRIEND_NUM = "k_friend_num";
    //群红点
    public static final String K_GROUP_NUM = "k_group_num";
    public static final String K_SET_BACKGROUND = "set_background";

    //邀请入群
    public static final String IS_INVITE_TO_GROUP = "isInviteToGroup";
    //移除群聊
    public static final String IS_REMOVE_GROUP = "isRemoveGroup";
    //选择群成员
    public static final String IS_SELECT_MEMBER = "isSelectMember";
    //@成员
    public static final String IS_AT_MEMBER = "isAtMember";
    //群通话
    public static final String IS_GROUP_CALL = "isGroupCall";
    //选择好友
    public static final String IS_SELECT_FRIEND = "isSelectFriend";
    //自定义消息类型
    public static final String K_CUSTOM_TYPE = "customType";
    //日志级别
    public static final String K_LOG_LEVEL = "logLevel";
    // 阅后即焚存储标识
    public static final String SP_Prefix_ReadVanish = "ReadVanish_";

    //登录成功
    public static final String LOGIN_SUCCESS = "loginSuccess";

    //刷新
    public static final String REFRESH = "Refresh";

    //加载
    public static final String LOADING1 = "loading";

    //跳转消息页
    public static final String GOMSG = "go_msg";

    //跳转首页页
    public static final String GOHOME = "go_home";

    //跳转消息页
    public static final String NOLOGIN = "no_login";

    //跳转消息页
    public static final String SENDMSG = "send_msg";

    //加载中
    public static final int LOADING = 201;

    public static final String TXPUSHKEY = "eUAGgA4441fD1x9DOcttx3tZCIfs80o7qIUXS84nTEIbaCIdajEVfkdhKEPWZnHd";

    public static class MsgType {
        //本地呼叫记录
        public static final int LOCAL_CALL_HISTORY = -110;
        public static final int callingInvite = 200;
        public static final int callingAccept = 201;
        public static final int callingReject = 202;
        public static final int callingCancel = 203;
        public static final int callingHungup = 204;
    }

    public static class MediaType {
        public static final String VIDEO = "video";
        public static final String AUDIO = "audio";
    }

    public static class ActivityResult {
        public static final int SET_REMARK = 1000000;
        public static final int DELETE_FRIEND = 1000001;
    }

    //播放提示音
    public static void playPrompt() {
        MediaPlayerUtil.INSTANCE.initMedia(BaseApp.inst(), R.raw.message_ring);
        MediaPlayerUtil.INSTANCE.playMedia();
    }

    //震动milliseconds毫秒
    public static void vibrate(long milliseconds) {
        Vibrator vib = (Vibrator) BaseApp.inst().getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(milliseconds);
    }
}
