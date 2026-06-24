package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;

/**
 * APP升级弹窗
 */
public class AppUpgradeDialog extends CenterPopupView {

    private boolean forcedUpgrade;
    private String apkVersionName;
    private String apkDescription;
    private Context context;
    private ProgressBar progressBar;
    private TextView tv_progress;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public AppUpgradeDialog(Context context, boolean forcedUpgrade, String apkVersionName, String apkDescription, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.forcedUpgrade = forcedUpgrade;
        this.apkVersionName = apkVersionName;
        this.apkDescription = apkDescription;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_app_upgrade;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        LinearLayout ll_bottom = findViewById(R.id.ll_bottom);
        TextView tv_apkVersionName = findViewById(R.id.tv_apkVersionName);
        TextView tv_apkDescription = findViewById(R.id.tv_apkDescription);
        progressBar = findViewById(R.id.progressBar);
        tv_progress = findViewById(R.id.tv_progress);

        tv_apkVersionName.setText("发现新版本v"+apkVersionName+"可以下载啦!");
        tv_apkDescription.setText(apkDescription);

        ll_bottom.setVisibility(forcedUpgrade ? GONE : VISIBLE);
        ll_bottom.setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit();
            }
        });

    }

    public void setProgress(int progress){
        progressBar.setProgress(progress);
        tv_progress.setText(progress+"%");
    }
}
