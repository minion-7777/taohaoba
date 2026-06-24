package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;

import com.lxj.xpopup.core.BottomPopupView;

import io.openim.android.taohaoba.R;

/**
 * 综合排序弹窗
 */
public class ComprehensiveSortingDialog extends BottomPopupView {

    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(int sortType); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public ComprehensiveSortingDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_comprehensive_sorting;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        findViewById(R.id.tv_zhpx).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(0);
                dismiss();
            }
        });

        findViewById(R.id.tv_jgjx).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(1);
                dismiss();
            }
        });

        findViewById(R.id.tv_jgsx).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(2);
                dismiss();
            }
        });

        findViewById(R.id.tv_sjjx).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(3);
                dismiss();
            }
        });

        findViewById(R.id.tv_sjsx).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(4);
                dismiss();
            }
        });

    }
}
