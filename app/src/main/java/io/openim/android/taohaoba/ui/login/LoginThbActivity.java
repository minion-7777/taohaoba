package io.openim.android.taohaoba.ui.login;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.net.RXRetrofit.CustomOkHttpClient;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.utils.SinkHelper;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityLoginBinding;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;
import io.openim.android.taohaoba.utils.RSA;
import io.openim.android.taohaoba.vm.login.LoginVMThb;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginThbActivity extends BaseActivity<LoginVMThb, ActivityLoginBinding> implements LoginVMThb.ViewAction {
    private static final String TAG = "LoginThbActivity";
    public static final String FORM_LOGIN = "form_login";
    private int codeAndPwd = 1;
    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60000; // 60秒倒计时
    private boolean isTimerRunning = false;
    private boolean isAgree = false;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        BaseApp.inst().removeCacheVM(LoginVMThb.class);
        bindVM(LoginVMThb.class, true);
        bindViewDataBinding(ActivityLoginBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.ivReturn);
        setLightStatus();
        SinkHelper.get(this).setTranslucentStatus(null);
        initDate();
    }

    @Override
    public void jump() {
        finish();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    @Override
    public void succ(Object o) {

    }

    public void initDate() {
        EventBus.getDefault().register(this);
        protocolInit();
        initListener();

        view.ivReturn.setOnClickListener(v->{
            finish();
        });

        vm.getLoginLiveData().observe(view.getLifecycleOwner(), it->{
            dismissLoadingDialog();
            mmkv.encode(PreferencesKey.token, it.getToken());
            mmkv.encode(PreferencesKey.userId, it.getUserId());
            mmkv.encode(PreferencesKey.userSig, it.getImResponse().getData().getUserSig());
            mmkv.encode(PreferencesKey.imUserId, it.getImResponse().getData().getUserID());
            mmkv.encode(PreferencesKey.userPhone, view.etPhone.getText().toString());
            mmkv.encode(PreferencesKey.allowBeep,true);
            mmkv.encode(PreferencesKey.allowVibration, true);
            Log.d(TAG, "token: " + it.getToken());
            Log.d(TAG, "MMKV.defaultMMKV().decodeString(PreferencesKey.token)=" + MMKV.defaultMMKV().decodeString(PreferencesKey.token));
            //
            N.mRetrofitThb = null;
            N.mRetrofitThb = new Retrofit.Builder()
            .baseUrl(Constants.baseUrlThb)
                    .addConverterFactory(GsonConverterFactory.create())//添加gson转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加rxjava转换器
                    .client(CustomOkHttpClient.getClient())
                    .build();
        });

        vm.getLiveDataLoginCertificate().observe(view.getLifecycleOwner(), it->{
            dismissLoadingDialog();
            onLoginSuccess(new MessageEvent(Constants.LOGIN_SUCCESS));
        });

        vm.getSendSmsLiveData().observe(view.getLifecycleOwner(), it->{
            dismissLoadingDialog();
            startCountdown();
            view.tvSend.setEnabled(false);  // 禁用发送按钮
        });
    }

    protected void initListener() {
        view.tvPwdLogin.setOnClickListener(v->{
            codeAndPwd = codeAndPwd == 1 ? 2 : 1;
            view.tvPwdLogin.setText(codeAndPwd == 1 ? "密码登录" : "验证码登录");
            view.etVerificationCode.setVisibility(codeAndPwd == 1 ? VISIBLE : INVISIBLE);
            view.tvSend.setVisibility(codeAndPwd == 1 ? VISIBLE : INVISIBLE);
            view.etPwd.setVisibility(codeAndPwd == 2 ? VISIBLE : INVISIBLE);
            view.ivShowPwd.setVisibility(codeAndPwd == 2 ? VISIBLE : INVISIBLE);
        });

        // 设置长按监听器，阻止弹出上下文菜单
        view.etPwd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true; // 返回 true 表示消费掉长按事件，阻止弹出上下文菜单
            }
        });

        view.tvSend.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etPhone.getText().toString())) {
                shakeAnimation(view.etPhone);
                showToast("请输入手机号");
                return;
            }
            if (view.etPhone.getText().toString().length() != 11) {
                shakeAnimation(view.etPhone);
                showToast("请输入正确的手机号");
                return;
            }
            showLoadingDialog();
            vm.send_sms(view.etPhone.getText().toString());
        });

        // 同意协议
        view.ivCheck.setOnClickListener(v->{
            isAgree = !isAgree;
            view.ivCheck.setImageResource(isAgree ? R.mipmap.ic_check_t : R.mipmap.ic_check_f);
        });

        view.ivShowPwd.setOnClickListener(v -> {
            if (view.etPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                // 显示密码
                view.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                view.ivShowPwd.setImageResource(R.mipmap.ic_looks_t); // 替换为实际睁眼图标资源
            } else {
                // 隐藏密码
                view.etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                view.ivShowPwd.setImageResource(R.mipmap.ic_looks_f); // 替换为实际闭眼图标资源
            }
            // 保持光标在文本末尾
            view.etPwd.setSelection(view.etPwd.getText().length());
        });

        view.tvToLogin.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etPhone.getText().toString())) {
                shakeAnimation(view.etPhone);
                showToast("请输入手机号");
                return;
            }
            if (view.etPhone.getText().toString().length() != 11) {
                shakeAnimation(view.etPhone);
                showToast("请输入正确的手机号");
                return;
            }
            if (codeAndPwd == 1 && TextUtils.isEmpty(view.etVerificationCode.getText().toString())) {
                shakeAnimation(view.etVerificationCode);
                showToast("请输入验证码");
                return;
            }
            if (codeAndPwd == 1 && view.etVerificationCode.getText().toString().length() != 6) {
                shakeAnimation(view.etVerificationCode);
                showToast("请输入6位验证码");
                return;
            }
            if (codeAndPwd == 2 && TextUtils.isEmpty(view.etPwd.getText().toString())) {
                shakeAnimation(view.etPwd);
                showToast("请输入密码");
                return;
            }
            if (codeAndPwd == 2 && !TextUtils.isEmpty(view.etPwd.getText().toString())) {
                if (view.etPwd.getText().toString().length() < 8 || view.etPwd.getText().toString().length() > 20) {
                    shakeAnimation(view.etPwd);
                    showToast("密码位数是8到20位之间");
                    return;
                }
            }
            if (!isAgree) {
                shakeAnimation(view.ivCheck);
                showToast("请勾选协议");
                return;
            }

            showLoadingDialog();
            vm.login(codeAndPwd,
                    view.etPhone.getText().toString(),
                    codeAndPwd == 1 ? null : RSA.encryptByPublicKey(getBaseContext(),view.etPwd.getText().toString()),
                    codeAndPwd == 2 ? null : view.etVerificationCode.getText().toString());
        });
    }

    private void protocolInit() {
        String originalText = "我已阅读并同意《服务协议》和《隐私协议》";

        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);

        int startIndex1 = originalText.indexOf("《服务协议》");
        int endIndex1 = startIndex1 + "《服务协议》".length();

        int startIndex2 = originalText.indexOf("《隐私协议》");
        int endIndex2 = startIndex2 + "《隐私协议》".length();

        ClickableSpan clickableSpan1 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1000));
            }
        };

        ClickableSpan clickableSpan2 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1001));
            }
        };

        builder.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan2, startIndex2, endIndex2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        int blueColor = ContextCompat.getColor(this, R.color.color_EACA92);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex2, endIndex2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        view.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        view.tvAgreement.setHighlightColor(Color.TRANSPARENT);
        view.tvAgreement.setText(builder);
    }

    private static abstract class CustomClickableSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            // 去掉下划线
            ds.setUnderlineText(false);
        }
    }

    // 启动倒计时
    private void startCountdown() {
        if (isTimerRunning) {
            return; // 如果已经在倒计时中，直接返回
        }

        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.tvSend.setText(millisUntilFinished / 1000 + "秒后可重新发送");
            }

            @Override
            public void onFinish() {
                view.tvSend.setEnabled(true); // 启用发送按钮
                view.tvSend.setText("重新发送"); // 更改按钮文本
                isTimerRunning = false;
            }
        }.start();

        isTimerRunning = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // 释放倒计时资源
        }
    }

    // 必须为public方法且带有@Subscribe注解
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(MessageEvent event) {
        // 处理登录成功事件
        // 可以跳转到主页面等操作
        EventBus.getDefault().post(event);
        EventBus.getDefault().unregister(this);
    }

}
