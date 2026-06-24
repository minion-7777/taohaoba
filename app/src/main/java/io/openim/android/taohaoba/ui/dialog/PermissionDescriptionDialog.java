package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;

import com.lxj.xpopup.core.CenterPopupView;
import io.openim.android.taohaoba.R;

/**
 * 权限说明弹窗
 */
public class PermissionDescriptionDialog extends CenterPopupView {

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

    public PermissionDescriptionDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_permission_description;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit();
                dismiss();
            }
        });

    }
}
