package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;
import io.openim.android.taohaoba.R;

/**
 * 手机验证码验证弹窗
 */
public class SecurityVerificationDialog extends CenterPopupView {

    private String phone;
    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSend(TextView view); // 验证成功回调
        void onSubmit(String code); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public SecurityVerificationDialog(Context context, String phone, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.phone = phone;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_security_verification;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        EditText et_verification_code = findViewById(R.id.et_verification_code);
        TextView tv_send = findViewById(R.id.tv_send);
        TextView tv_hint = findViewById(R.id.tv_hint);

        tv_hint.setText("本次操作需要进行安全验证，请点击获取验证码，并使用"+phone+"接收验证码进行验证");

        tv_send.setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSend(tv_send);
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                if (TextUtils.isEmpty(et_verification_code.getText().toString().trim())) {
                    Toast.makeText(context, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                verificationListener.onSubmit(et_verification_code.getText().toString().trim());
                dismiss();
            }
        });

    }
}
