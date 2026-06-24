package io.openim.android.taohaoba.ui.activity.me;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.databinding.ActivityAccountCancellationBinding;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 账号注销
 */
public class AccountCancellationActivity extends BaseActivity<WalletVM, ActivityAccountCancellationBinding> implements WalletVM.ViewAction {

    private BasePopupView popupView;
    private boolean isAgree = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivityAccountCancellationBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        protocolInit();

        //账户注销
        vm.getUserDisableMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            imLogout();
        });

    }

    protected void initListener() {

        // 设置下划线（保留原文本样式）
        view.tvDdxx.setPaintFlags(view.tvDdxx.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        // 同意协议
        view.ivCheck.setOnClickListener(v->{
            isAgree = !isAgree;
            view.ivCheck.setImageResource(isAgree ? R.mipmap.ic_check_t : R.mipmap.ic_check_f);
        });

        view.tvAccountCancellation.setOnClickListener(v->{
            if (!isAgree) {
                showToast("请勾选协议");
                return;
            }
            onDialog();
        });
    }

    /**
     *  注销账户确认弹窗
     */
    private void onDialog(){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new io.openim.android.taohaoba.ui.dialog.CommonDialog(this, "您确定要注销账户吗？", new io.openim.android.taohaoba.ui.dialog.CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        showLoadingDialog();
                        vm.user_disable();
                    }
                })).show();
    }

    private void imLogout(){
//        OpenIMClient.getInstance().logout(new OnBase<String>() {
//            @Override
//            public void onError(int code, String error) {
//                dismissLoadingDialog();
//            }
//            @Override
//            public void onSuccess(String data) {
//                dismissLoadingDialog();
//                FirebaseMessaging.getInstance().deleteToken();
//                mmkv.clearAll();
//                IMUtil.logout((AppCompatActivity) AccountCancellationActivity.this, MainActivity.class);
//                ActivityManager.finishAllExceptActivity();
//                launchActivity(MainActivity.class);
//            }
//        });
    }

    private void protocolInit() {
        String originalText = "我已认真阅读《淘号8注销协议》并接受所有条款";

        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);

        int startIndex1 = originalText.indexOf("《淘号8注销协议》");
        int endIndex1 = startIndex1 + "《淘号8注销协议》".length();

        ClickableSpan clickableSpan1 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1000000913));
            }
        };

        builder.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        int blueColor = ContextCompat.getColor(this, R.color.color_EACA92);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

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

    @Override
    public void err(String msg) {
        dismissLoadingDialog();

        view.tvHint.setText("提示：" + msg);
    }

}
