package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;

/**
 * APP隐私政策弹窗
 */
public class PrivacyDialog extends CenterPopupView {

    private Context context;
    private TextView tvAgreement;

    // 定义回调接口
    public interface OnVerificationListener {
        void onCancel(); // 验证成功回调
        void onSubmit(); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public PrivacyDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_privacy;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        TextView tv_cancel = findViewById(R.id.tv_cancel);
        TextView tv_submit = findViewById(R.id.tv_submit);
        tvAgreement = findViewById(R.id.tvAgreement);

        tv_cancel.setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onCancel();
                dismiss();
            }
        });

        tv_submit.setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit();
                dismiss();
            }
        });

        protocolInit();

    }

    private void protocolInit() {
        String originalText = "个人信息保护提示\n\t\t欢迎使用淘号8!\n\t\t我们将通过《服务协议》和《隐私协议》帮助您了解我们为您提供的服务、我们将如何处理个人信息以及您享有的权力。我们会严格按照相关法律法规要求，采取各种安全措施来保护您的个人信息。\n1.为了保障软件的安全运行，我们会申请收集您的设备信息、IP地址、MAC地址。\n2.上传或拍摄图片以及进行认证时，需要使用您的存储、相机权限。\n3.当您进行包赔认证时，我们需要申请位置信息、通讯录权限。\n4.我们尊重您的选择权，您可以访问、修改、删除您的个人信息并管理您的授权，我们也为您提供注销、投诉渠道。";

        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);

        int startIndex1 = originalText.indexOf("《服务协议》");
        int endIndex1 = startIndex1 + "《服务协议》".length();

        int startIndex2 = originalText.indexOf("《隐私协议》");
        int endIndex2 = startIndex2 + "《隐私协议》".length();

        ClickableSpan clickableSpan1 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra("category_id", 1000));
            }
        };

        ClickableSpan clickableSpan2 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra("category_id", 1001));
            }
        };

        builder.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(clickableSpan2, startIndex2, endIndex2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        int blueColor = ContextCompat.getColor(context, R.color.color_EACA92);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex2, endIndex2, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT);
        tvAgreement.setText(builder);
    }

    private static abstract class CustomClickableSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            // 去掉下划线
            ds.setUnderlineText(false);
        }
    }
}
