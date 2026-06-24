package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;

/**
 * 公共样式弹窗
 */
public class CommonDialog extends CenterPopupView {

    private String hint;
    private String confirmText;
    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public CommonDialog(Context context, String hint, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.hint = hint;
        this.verificationListener = listener;
    }

    public CommonDialog(Context context, String confirmText, String hint, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.confirmText = confirmText;
        this.hint = hint;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_common;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        TextView tv_hint = findViewById(R.id.tv_hint);
        TextView tv_confirm = findViewById(R.id.tv_confirm);

        if (!TextUtils.isEmpty(hint)) {
            tv_hint.setText(hint);
        }

        if (!TextUtils.isEmpty(confirmText)) {
            tv_confirm.setText(confirmText);
        }

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_confirm).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit();
                dismiss();
            }
        });

    }
}
