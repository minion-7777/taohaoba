package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.widgets.RadioGroupEx;

/**
 * 取消订单弹窗
 */
public class CancellationOfOrderDialog extends CenterPopupView {

    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String refund_content); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public CancellationOfOrderDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_cancellation_of_order;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        RadioGroupEx radioGroup = findViewById(R.id.radioGroup);

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                    verificationListener.onSubmit(radioButton.getText().toString());
                    dismiss();
                }else {
                    Toast.makeText(context, "请选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
