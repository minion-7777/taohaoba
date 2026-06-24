package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;

/**
 * 实人认证确认信息弹窗
 */
public class ConfirmInformationDialog extends CenterPopupView {

    private String name;
    private String number;
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

    public ConfirmInformationDialog(Context context, String name , String number, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.name = name;
        this.number = number;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_confirm_information;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        TextView et_name = findViewById(R.id.et_name);
        TextView et_id_number = findViewById(R.id.et_id_number);
        et_name.setText(name);
        et_id_number.setText(number);

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit();
                dismiss();
            }
        });

    }

}
