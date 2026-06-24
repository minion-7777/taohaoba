package io.openim.android.taohaoba;


import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.getui.gs.ias.core.GsConfig;
import com.getui.gs.sdk.GsManager;
import com.getui.gs.sdk.IGtcIdCallback;
import com.lxj.xpopup.XPopup;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;

import org.json.JSONObject;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.config.KeyConfig;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivitySplashBinding;
import io.openim.android.taohaoba.signature.GenerateTestUserSig;
import io.openim.android.taohaoba.ui.dialog.PrivacyDialog;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.utils.SharedPreferencesUtil;
import io.openim.android.taohaoba.vm.MainVM;
import io.openim.android.taohaoba.vm.login.LoginVMThb;

@Route(path = Routes.Main.SPLASH)
public class SplashActivity extends BaseActivity<MainVM, ActivitySplashBinding> implements LoginVMThb.ViewAction{

    private CountDownTimer groupTimer = null;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(MainVM.class);
        bindViewDataBinding(ActivitySplashBinding.inflate(getLayoutInflater()));

        initView();
    }


    private void initView() {
        setLightStatus();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN // 隐藏状态栏
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // 隐藏导航栏（可选）
        decorView.setSystemUiVisibility(uiOptions);

        vm.getPosterImgMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            if (it != null && it.getList() != null && it.getList().size() > 0) {
                view.ivImg1.setVisibility(GONE);
                Glide.with(getBaseContext())
                        .load(it.getList().get(0).getPath())
                        .apply(new RequestOptions()
                        )
                        .into(view.ivImg);

            }
        });

        vm.get_poster_img();

//        UserLogic userLogic = Easy.find(UserLogic.class);
//        if (userLogic.isCacheUser()) {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        } else{
////            startActivity(new Intent(this, LoginActivity.class));
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }

        if (SharedPreferencesUtil.getBoolean(getBaseContext(), KeyConfig.IS_PRIVACY, false)) {
            countDown();
            initPush();
        }else {
            initPop();
        }
    }

    /**
     * 延时3秒进入主页
     */
    private void countDown() {
        if (groupTimer != null) {
            return;
        }
        groupTimer = new CountDownTimer(3000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        };
        groupTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (groupTimer != null) {
            groupTimer.cancel();
        }
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
     * 隐私弹窗
     */
    private void initPop(){
        mmkv.encode(KeyConfig.NOTIFICATION_PERMISSION, true);
        new XPopup.Builder(this)
                .isViewMode(false)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .navigationBarColor(Color.BLACK) // 设置导航栏背景色为黑色
                .isLightNavigationBar(true) // 导航栏图标设为白色（适配黑色背景）
                .asCustom(new PrivacyDialog(this, new PrivacyDialog.OnVerificationListener() {
                    @Override
                    public void onCancel() {
                        finish();
                    }

                    @Override
                    public void onSubmit() {
                        SharedPreferencesUtil.saveBoolean(getBaseContext(), KeyConfig.IS_PRIVACY, true);
                        initPush();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));
                        finish();
                    }
                })).show();
    }

    /**
     * 初始化个推sdk
     */
    private void initPush(){
//        PushManager.getInstance().preInit(this);
//        PushManager.getInstance().initialize(this);
        GsConfig.setInstallChannel(BuildConfig.FLAVOR);
        // 设置gtcid回调
        GsManager.getInstance().setGtcIdCallback(new IGtcIdCallback() {
            @Override
            public void onGetGtcId(String gtcId) {
                Log.d("onGetGtcId", "onGetGtcId: " + gtcId);
            }
        });
        GsManager.getInstance().preInit(this);
        GsManager.getInstance().init(getBaseContext());

        // 进入主页面
        String eventId = "enter_main_page";
        GsManager.getInstance().onEvent(eventId);

        String userPhone = mmkv.decodeString(PreferencesKey.userPhone);
        String userSig = mmkv.decodeString(PreferencesKey.userSig);

        if (isLogin()) {
            TUILogin.login(getBaseContext(), GenerateTestUserSig.SDKAPPID, userPhone, userSig, new TUICallback() {
                @Override
                public void onSuccess() {
                    Log.d("imsdk", "登录成功");
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    Log.i("imsdk", "登录失败, code:" + errorCode + ", desc:" + errorMessage);
                }
            });
        }

    }

}
