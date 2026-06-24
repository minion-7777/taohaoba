package io.openim.android.taohaoba.ui.main;


import static io.openim.android.taohaoba.utils.BadgeNumberUtil.setBadgeNum;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.firebase.messaging.FirebaseMessaging;
import com.king.app.updater.AppUpdater;
import com.king.app.updater.callback.UpdateCallback;
import com.king.app.updater.http.OkHttpManager;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.push.TIMPushCallback;
import com.tencent.qcloud.tim.push.TIMPushManager;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;
import com.tencent.qcloud.tuikit.tuichat.config.classicui.TUIChatConfigClassic;
import com.tencent.qcloud.tuikit.tuiconversation.classicui.page.TUIConversationFragment;
import com.tencent.qcloud.tuikit.tuiconversation.config.TUIConversationConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.ouicore.entity.LoginCertificate;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.L;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.CategoryBean;
import io.openim.android.taohaoba.config.KeyConfig;
import io.openim.android.taohaoba.databinding.ActivityMainBinding;
import io.openim.android.taohaoba.signature.GenerateTestUserSig;
import io.openim.android.taohaoba.ui.dialog.AppUpgradeDialog;
import io.openim.android.taohaoba.ui.dialog.PermissionDescriptionDialog4Notice;
import io.openim.android.taohaoba.ui.dialog.SellNumberDialog;
import io.openim.android.taohaoba.ui.fragment.GameFragment;
import io.openim.android.taohaoba.ui.fragment.HomeFragment;
import io.openim.android.taohaoba.ui.fragment.MeFragment;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.vm.MainVM;
import io.openim.android.taohaoba.vm.login.LoginVMThb;

@Route(path = Routes.Main.HOME)
public class MainActivity extends BaseActivity<MainVM, ActivityMainBinding> implements LoginVMThb.ViewAction {
    private static final String TAG = "MainActivity";
    private int mCurrentTabIndex;
    private BaseFragment lastFragment, homeFragment, gameFragment, msgFragment, meFragment;
    private CategoryBean categoryBean;
    private static final int PERMISSION_CODE_NOTICE = 1001;
    private BasePopupView appUpgradeDialog;
    private AppUpgradeDialog upgradeDialog;
    private boolean isDownloading;
    private V2TIMConversationListener v2TIMConversationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bindVM(MainVM.class);
        bindViewDataBinding(ActivityMainBinding.inflate(getLayoutInflater()));
        super.onCreate(savedInstanceState);
        setLightStatus();
        EventBus.getDefault().register(this); // 注册订阅者

        homeFragment = HomeFragment.newInstance();
        gameFragment = GameFragment.newInstance();
        // 消息
        msgFragment = TUIConversationFragment.newInstance();
        meFragment = MeFragment.newInstance();

        if (null != homeFragment) {
            homeFragment.setPage(1);
        }
        if (null != gameFragment) {
            gameFragment.setPage(2);
        }
        if (null != msgFragment) {
            msgFragment.setPage(3);
        }
        if (null != meFragment) {
            meFragment.setPage(4);
        }
        click();
        listener();
        initView();

        onMessageEvent(new MessageEvent(Constants.LOGIN_SUCCESS));
    }

    private void listener() {
        view.tvMsgCount.setVisibility(View.GONE);

        V2TIMManager.getConversationManager().getTotalUnreadMessageCount(new V2TIMValueCallback<Long>() {
            @Override
            public void onSuccess(Long aLong) {
                //设置APP消息角标
                setBadgeNum(getBaseContext(), Math.toIntExact(aLong));
                if (aLong > 0 && isLogin()) {
                    BaseApp.inst().totalUnreadMsgCount = Math.toIntExact(aLong);
                    view.tvMsgCount.setVisibility(View.VISIBLE);
                    view.tvMsgCount.setText(aLong >= 100 ? "99+" : String.valueOf(aLong));
                } else {
                    view.tvMsgCount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(int code, String desc) {
                Log.i(TAG, "failure, code:" + code + ", desc:" + desc);
            }
        });

        if (v2TIMConversationListener == null) {
            v2TIMConversationListener = new V2TIMConversationListener() {
                @Override
                public void onSyncServerStart() {
                    Log.e(TAG, "同步服务器会话开始");
                }

                @Override
                public void onSyncServerFinish() {
                    Log.e(TAG, "同步服务器会话完成");
                }

                @Override
                public void onSyncServerFailed() {
                    Log.e(TAG, "同步服务器会话失败");
                }

                @Override
                public void onTotalUnreadMessageCountChanged(long totalUnreadCount) {
                    super.onTotalUnreadMessageCountChanged(totalUnreadCount);
                    Log.e(TAG, "总未读消息数更新 " + totalUnreadCount);
                    //设置APP消息角标
                    setBadgeNum(getBaseContext(), Math.toIntExact(totalUnreadCount));
                    if (totalUnreadCount > 0 && isLogin()) {
                        BaseApp.inst().totalUnreadMsgCount = Math.toIntExact(totalUnreadCount);
                        view.tvMsgCount.setVisibility(View.VISIBLE);
                        view.tvMsgCount.setText(totalUnreadCount >= 100 ? "99+" : String.valueOf(totalUnreadCount));
                    } else {
                        view.tvMsgCount.setVisibility(View.GONE);
                    }
                }

            };
        }
        V2TIMManager.getConversationManager().addConversationListener(v2TIMConversationListener);

        if(hasNotificationPermission()){
            startNoticeService();
        }else {
            if (mmkv != null && !mmkv.decodeBool(KeyConfig.NOTIFICATION_PERMISSION)) {
                return;
            }
            checkNoticePermission();
        }

        TUIConversationConfig.getInstance().setShowUserStatus(true);
        TUIChatConfigClassic.setEnableTypingIndicator(true);
    }

    /**
     * 获取商品类型
     */
    private void initView() {

        //获取商品类型
        vm.getCategoryBeanMutableLiveData().observe(this, it->{
            categoryBean = it;
//            startActivity(new Intent(this, SellNumberActivity.class).putExtra("categoryId", categoryBean.getCategory().get(0).getId()));
            onDialog(categoryBean.getCategory().get(0).getId());
        });


        //版本更新获取
        vm.getVersionManageBeanMutableLiveData().observe(this, it->{
            //<更新  >=不更新
            if (BuildConfig.VERSION_CODE < Integer.valueOf(it.getInfo().getVersion_code())) {
                Upgrade(it.getInfo().getDownload_url(), it.getInfo().getIs_force_update() == 1, Integer.valueOf(it.getInfo().getVersion_code()), it.getInfo().getVersion_name(), it.getInfo().getDescription());
            }
        });

        vm.get_version_manage(BuildConfig.FLAVOR);

    }

    private void onDialog(int categoryId){
        new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new SellNumberDialog(this, categoryId, new SellNumberDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {

                    }
                })).show();
    }

    /**
     * 版本更新
     * @param apkUrl
     * @param forcedUpgrade
     * @param apkVersionCode
     * @param apkVersionName
     * @param apkDescription
     */
    private void Upgrade(String apkUrl, boolean forcedUpgrade, int apkVersionCode, String apkVersionName, String apkDescription){
        upgradeDialog = new AppUpgradeDialog(this, forcedUpgrade, apkVersionName, apkDescription, new AppUpgradeDialog.OnVerificationListener() {
            @Override
            public void onSubmit() {
                if (isDownloading) {
                    showToast("正在下载中...");
                    return;
                }
                isDownloading = false;
                AppUpdater appUpdater = new AppUpdater.Builder(getBaseContext())
                        .setUrl(apkUrl)
                        .setFilename("taohaoba.apk")
                        .setInstallApk(true)
                        .setNotificationIcon(R.mipmap.ic_launcher)
                        .setShowPercentage(true)
                        .setVersionCode(apkVersionCode)
                        .build();
                appUpdater.setHttpManager(OkHttpManager.getInstance()) // 使用OkHttp的实现进行下载
                        .setUpdateCallback(new UpdateCallback() { // 更新回调
                            @Override
                            public void onDownloading(boolean isDownloading) {
                                // 下载中：isDownloading为true时，表示已经在下载，即之前已经启动了下载；为false时，表示当前未开始下载，即将开始下载
                            }

                            @Override
                            public void onStart(String url) {
                                // 开始下载
                                isDownloading = true;
                                showToast("开始下载");
                            }

                            @Override
                            public void onProgress(long progress, long total, boolean isChanged) {
                                if (isChanged) {
                                    if (appUpgradeDialog.isShow()) {
                                        updateProgress(progress, total);
                                    }
                                }
                                // 下载进度更新：建议在isChanged为true时，才去更新界面的进度；因为实际的进度变化频率很高
                            }

                            @Override
                            public void onFinish(File file) {
                                // 下载完成
                                isDownloading = false;
                                showToast("下载完成");
                            }

                            @Override
                            public void onError(Exception e) {
                                // 下载失败
                                isDownloading = false;
                                showToast("下载失败");
                            }

                            @Override
                            public void onCancel() {
                                // 取消下载
                                isDownloading = false;
                                showToast("取消下载");
                            }
                        }).start();
            }
        });

        appUpgradeDialog = new XPopup.Builder(this)
                .isViewMode(false)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asCustom(upgradeDialog).show();
    }

    private void updateProgress(long progress, long total) {
        if (progress > 0) {
            int currProgress = (int) (progress * 1.0f / total * 100.0f);
            upgradeDialog.setProgress(currProgress);
        } else {
            L.i(TAG, "正在获取下载数据…");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void click() {
        switchFragment(homeFragment);
        clickStyle(1);
        view.llHome.setOnClickListener(v->{
            switchFragment(homeFragment);
            clickStyle(1);
        });
        view.llGame.setOnClickListener(v->{
            switchFragment(gameFragment);
            clickStyle(2);
        });
        view.ivSellNumber.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class)) {
                vm.getCategory();
            }
        });
        view.rlMsg.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class)) {
                LoginCertificate certificate = LoginCertificate.getCache(BaseApp.inst());
                if (null != certificate) {
                    switchFragment(msgFragment);
                    clickStyle(3);
                } else {
                    startActivity(new Intent(this, LoginThbActivity.class));
                }
            }
        });
        view.llMe.setOnClickListener(v->{
            if (isLogin(LoginThbActivity.class)) {
                switchFragment(meFragment);
                clickStyle(4);
            }

        });
    }


    @Override
    public void jump() {

    }

    @Override
    public void err(String msg) {

    }

    @Override
    public void succ(Object o) {

    }

    @Override
    public void initDate() {

    }

    /**
     * 修改导航按钮点击后的样式
     */
    private void clickStyle(int index) {
        view.ivHome.setImageDrawable(getDrawable(index == 1 ? R.mipmap.ic_home_t : R.mipmap.ic_home_f));
        view.ivGame.setImageDrawable(getDrawable(index == 2 ? R.mipmap.ic_game_t : R.mipmap.ic_game_f));
        view.ivMsg.setImageDrawable(getDrawable(index == 3 ? R.mipmap.ic_msg_t : R.mipmap.ic_msg_f));
        view.ivMe.setImageDrawable(getDrawable(index == 4 ? R.mipmap.ic_me_t : R.mipmap.ic_me_f));
        view.tvHome.setTextColor(getColor(index == 1 ? R.color.color_FFD79E : R.color.color_8F8F8F));
        view.tvGame.setTextColor(getColor(index == 2 ? R.color.color_FFD79E : R.color.color_8F8F8F));
        view.tvMsg.setTextColor(getColor(index == 3 ? R.color.color_FFD79E : R.color.color_8F8F8F));
        view.tvMe.setTextColor(getColor(index == 4 ? R.color.color_FFD79E : R.color.color_8F8F8F));
    }

    /**
     * 切换Fragment
     */
    private void switchFragment(BaseFragment fragment) {
        try {
            if (fragment != null && !fragment.isVisible() && mCurrentTabIndex != fragment.getPage()) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.add(R.id.fragment_container, fragment);
                }
                if (lastFragment != null) {
                    transaction.hide(lastFragment);
                }
                transaction.show(fragment).commit();
                lastFragment = fragment;
                mCurrentTabIndex = lastFragment.getPage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.getMessage().equalsIgnoreCase(Constants.LOGIN_SUCCESS)) {
//            vm.initDate();
            listener();
        } else if (event.getMessage().equals(Constants.GOMSG)){
            new Handler().postDelayed(() -> {
                switchFragment(msgFragment);
                clickStyle(3);
            }, 200);
        } else if (event.getMessage().equals(Constants.NOLOGIN)){
            runOnUiThread(()->{
                view.tvMsgCount.setVisibility(View.GONE);
                BaseApp.inst().totalUnreadMsgCount = 0;
                TUILogin.logout(new TUICallback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "LogOutOnSuccess");
                    }

                    @Override
                    public void onError(int errorCode, String errorMessage) {
                        Log.d(TAG, "LogOutOnError: "+errorCode+errorMessage);
                    }
                });
                mmkv.clearAll();
                FirebaseMessaging.getInstance().deleteToken();
                ActivityManager.finishAllExceptActivity();
                launchActivity(MainActivity.class);
            });
        }else if (event.getMessage().equals(Constants.GOHOME)){
            new Handler().postDelayed(() -> {
                switchFragment(homeFragment);
                clickStyle(1);
            }, 200);
        }
    }

    /**
     * 权限说明弹窗
     */
    private void initPop(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new PermissionDescriptionDialog4Notice(this, new PermissionDescriptionDialog4Notice.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        // Android 8.0及以上
                        // 跳转到当前应用的通知设置
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                        intent.putExtra(Settings.EXTRA_APP_PACKAGE, "com.ranranlanyan.taohaoba");
                        startActivity(intent);
                    }

                    @Override
                    public void onReject() {
                        mmkv.encode(KeyConfig.NOTIFICATION_PERMISSION, false);
                    }
                })).show();
    }

    //获取通知权限
    private void checkNoticePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        PERMISSION_CODE_NOTICE
                );
            } else {
                startNoticeService();
            }
        } else {
            startNoticeService();
        }
    }

    private void startNoticeService() {
        TIMPushManager.getInstance().registerPush(getBaseContext(), GenerateTestUserSig.SDKAPPID, Constants.TXPUSHKEY, new TIMPushCallback() {
            @Override
            public void onSuccess(Object data) {
                Log.i(TAG, "registerPush success腾讯推送初始化成功");
            }

            @Override
            public void onError(int errCode, String errMsg, Object data) {
                Log.i(TAG, "registerPush error腾讯推送初始化失败"+errCode+","+errMsg);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE_NOTICE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startNoticeService();
            } else {
                // 用户拒绝权限
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    // 用户仅拒绝但未勾选"不再询问"[1,9](@ref)
                    initPop();
                } else {
                    // 用户勾选"不再询问"或永久拒绝[3,6](@ref)
                    Log.d(TAG, "NoPermissionsResult");
                }
            }
        }
    }

    private boolean hasNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;
        }
        // Android 13以下默认有权限
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this); // 避免内存泄漏
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitTime <= 2000) {
                finish();
            } else {
                mExitTime = System.currentTimeMillis();
                showToast("再按一次退出应用");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}