package com.tencent.qcloud.tuikit.tuichat.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;
import com.tencent.qcloud.tuikit.tuichat.R;

/**
 * 实名认证弹窗
 */
public class RealNameAuthenticationDialog extends CenterPopupView {

    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String name, String number); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public RealNameAuthenticationDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_real_name_authentication;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        EditText et_name = findViewById(R.id.et_name);
        EditText et_id_number = findViewById(R.id.et_id_number);

        findViewById(R.id.iv_close).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                Toast.makeText(context, "请输入真实姓名", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_id_number.getText().toString().trim())) {
                Toast.makeText(context, "请输入身份证号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (verificationListener != null) {
                verificationListener.onSubmit(et_name.getText().toString().trim(), et_id_number.getText().toString().trim());
                dismiss();
            }
        });

    }
}
