package io.openim.android.taohaoba.ui.activity.me;


import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.ouicore.widget.WaitDialog;
import io.openim.android.taohaoba.databinding.ActivityChangePasswordBinding;
import io.openim.android.taohaoba.utils.RSA;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity<WalletVM, ActivityChangePasswordBinding> implements WalletVM.ViewAction {

    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60000; // 60秒倒计时
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityChangePasswordBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        String phone = getIntent().getStringExtra("phone");
        view.etPhone.setText(phone);
        //发送验证码
        vm.getSendSmsLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("验证码发送成功");
            startCountdown();
            view.tvSend.setEnabled(false);  // 禁用发送按钮
        });

        //修改
        vm.getSetUserInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("修改成功");
            WaitDialog waitDialog = new WaitDialog(this);
            waitDialog.show();
//            OpenIMClient.getInstance().logout(new OnBase<String>() {
//                @Override
//                public void onError(int code, String error) {
//                    waitDialog.dismiss();
//                }
//                @Override
//                public void onSuccess(String data) {
//                    waitDialog.dismiss();
//                    EventBus.getDefault().post(new MessageEvent(Constants.NOLOGIN));
//                }
//            });
        });

    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        // 设置长按监听器，阻止弹出上下文菜单
        view.etNewPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true; // 返回 true 表示消费掉长按事件，阻止弹出上下文菜单
            }
        });

        // 设置长按监听器，阻止弹出上下文菜单
        view.etConfirmNewPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true; // 返回 true 表示消费掉长按事件，阻止弹出上下文菜单
            }
        });

        //发送验证码
        view.tvSend.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etPhone.getText().toString().trim())) {
                shakeAnimation(view.etPhone);
                showToast("请输入手机号码");
                return;
            }
            if (view.etPhone.getText().toString().length() != 11) {
                shakeAnimation(view.etPhone);
                showToast("请输入正确的手机号码");
                return;
            }

            showLoadingDialog();
            vm.send_sms(view.etPhone.getText().toString().trim());

        });

        view.tvSubmit.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etPhone.getText().toString().trim())) {
                shakeAnimation(view.etPhone);
                showToast("请输入手机号");
                return;
            }
            if (view.etPhone.getText().toString().length() != 11) {
                shakeAnimation(view.etPhone);
                showToast("请输入正确的手机号码");
                return;
            }
            if (TextUtils.isEmpty(view.etVerificationCode.getText().toString().trim())) {
                shakeAnimation(view.etVerificationCode);
                showToast("请输入验证码");
                return;
            }
            if (view.etVerificationCode.getText().toString().length() != 6) {
                shakeAnimation(view.etVerificationCode);
                showToast("请输入6位数的验证码");
                return;
            }
            if (TextUtils.isEmpty(view.etNewPassword.getText().toString().trim())) {
                shakeAnimation(view.etNewPassword);
                showToast("请输入新密码");
                return;
            }
            if (!TextUtils.isEmpty(view.etNewPassword.getText().toString())) {
                if (view.etNewPassword.getText().toString().length() < 8 || view.etNewPassword.getText().toString().length() > 20) {
                    shakeAnimation(view.etNewPassword);
                    showToast("密码位数是8到20位之间");
                    return;
                }
            }
            if (TextUtils.isEmpty(view.etConfirmNewPassword.getText().toString().trim())) {
                shakeAnimation(view.etConfirmNewPassword);
                showToast("请再次确认新密码");
                return;
            }
            if (!TextUtils.isEmpty(view.etConfirmNewPassword.getText().toString())) {
                if (view.etConfirmNewPassword.getText().toString().length() < 8 || view.etConfirmNewPassword.getText().toString().length() > 20) {
                    shakeAnimation(view.etConfirmNewPassword);
                    showToast("密码位数是8到20位之间");
                    return;
                }
            }
            if (!view.etNewPassword.getText().toString().trim().equals(view.etConfirmNewPassword.getText().toString().trim())) {
                shakeAnimation(view.etConfirmNewPassword);
                showToast("密码不一致");
                return;
            }

            showLoadingDialog();
            vm.set_user_info(
                    4,
                    null,
                    null,
                    RSA.encryptByPublicKey(getBaseContext(),view.etNewPassword.getText().toString().trim()),
                    RSA.encryptByPublicKey(getBaseContext(),view.etConfirmNewPassword.getText().toString().trim()),
                    view.etPhone.getText().toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    view.etVerificationCode.getText().toString().trim());
        });
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

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
