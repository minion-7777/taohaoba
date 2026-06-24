package com.tencent.qcloud.tuikit.tuichat.dialog;

import android.content.Context;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.lxj.xpopup.core.BottomPopupView;
import com.tencent.qcloud.tuikit.tuichat.R;

/**
 * 举报弹窗
 */
public class ReportDialog extends BottomPopupView {

    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String code); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public ReportDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_report;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        RadioGroup radioGroup = findViewById(R.id.radioGroup); // 获取 RadioGroup 实例

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            int selectedId = radioGroup.getCheckedRadioButtonId(); // 获取选中项 ID
            if (selectedId == -1) {
                // 无选中项
                Toast.makeText(context, "请选择举报原因", Toast.LENGTH_SHORT).show();
                return;
            }

            if (verificationListener != null) {
                verificationListener.onSubmit(radioGroup.findViewById(selectedId).getTag().toString());
            }
        });
    }
}
