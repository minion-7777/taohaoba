package io.openim.android.taohaoba;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.v2.V2TIMLogListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKConfig;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.mmkv.MMKV;
import com.tencent.qcloud.tim.push.TIMPushListener;
import com.tencent.qcloud.tim.push.TIMPushManager;
import com.tencent.qcloud.tuicore.util.ToastUtil;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import org.greenrobot.eventbus.EventBus;

import io.openim.android.ouicore.BuildConfig;
import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.net.RXRetrofit.HttpConfig;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.L;
import io.openim.android.taohaoba.signature.GenerateTestUserSig;
import okhttp3.Request;


public class DemoApplication extends BaseApp {
    private static final String TAG = DemoApplication.class.getSimpleName();
    public static String clientid;
    private V2TIMSDKListener v2TIMSDKListener;

    @Override
    public void onCreate() {
        L.e(TAG, "-----onCreate------");
        super.onCreate();
        MultiDex.install(this);

        initARouter();
        initNet();
        initBugly();
        initIMSDKObserver();
        EmojiManager.install(new GoogleEmojiProvider());
        //音频播放
//        SPlayer.init(this);
        MMKV.initialize(this);

        TIMPushManager.getInstance().addPushListener(new TIMPushListener() {
            @Override
            public void onNotificationClicked(String ext) {
                Log.d(TAG, "onNotificationClicked =" + ext);
                // 获取 ext 自定义跳转

            }
        });
    }

    private void initARouter() {
        ARouter.init(this);
    }

    private void initBugly() {
        CrashReport.setAppChannel(this, BuildConfig.DEBUG ? "debug" : "release");
        CrashReport.initCrashReport(getApplicationContext(), "77fd4d7755", BuildConfig.DEBUG);
    }

    private void initNet() {
        N.init(new HttpConfig().setBaseUrl(Constants.getAppAuthUrl()).setDebug(BuildConfig.DEBUG)
            .addInterceptor(chain -> {
                String token = "";
                try {
                    token = BaseApp.inst().loginCertificate.chatToken;
                } catch (Exception ignored) {
                }
                Request request = chain.request().newBuilder()
                    .addHeader("token", token)
                    .addHeader("operationID", String.valueOf(System.currentTimeMillis()))
                    .build();
                return chain.proceed(request);
            }));
    }

    private void initIMSDKObserver() {

        // 初始化 config 对象
        V2TIMSDKConfig config = new V2TIMSDKConfig();
        // 指定 log 输出级别
        config.setLogLevel(V2TIMSDKConfig.V2TIM_LOG_INFO);
        // 指定 log 监听器
        config.setLogListener(new V2TIMLogListener() {
            @Override
            public void onLog(int logLevel, String logContent) {
                // logContent 为 SDK 日志内容
                L.e(TAG, "onLog: " + logContent);
            }
        });

        if (v2TIMSDKListener == null) {
            v2TIMSDKListener = new V2TIMSDKListener() {
                @Override
                public void onConnecting() {
                    Log.i(TAG, "onConnecting: 初始化中");
                }

                @Override
                public void onConnectSuccess() {
                    Log.i(TAG, "onConnectSuccess: 初始化成功");
                }

                @Override
                public void onConnectFailed(int code, String error) {
                    Log.e(TAG, "onConnectFailed: 初始化失败 " + code + " " + error);
                }

                @Override
                public void onUserSigExpired() {

                }

                @Override
                public void onKickedOffline() {
                    ToastUtil.toastShortMessage("账号在其它地方登录");
                    Log.i(TAG, "onKickedOffline: 被踢下线");
                    EventBus.getDefault().post(new MessageEvent(Constants.NOLOGIN));
                }
            };
        }

        V2TIMManager.getInstance().addIMSDKListener(v2TIMSDKListener);
        V2TIMManager.getInstance().initSDK(getApplicationContext(), GenerateTestUserSig.SDKAPPID, config);

    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                //全局设置主题颜色
                layout.setPrimaryColorsId(R.color.transparent, R.color.color_8F8F8F);
                //设置新的刷新主题
                // return new BezierRadarHeader(context);
                // .setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
                return new ClassicsHeader(context);
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context);
            }
        });
    }
}
