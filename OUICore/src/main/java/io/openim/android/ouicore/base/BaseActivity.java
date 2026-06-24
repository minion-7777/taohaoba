package io.openim.android.ouicore.base;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.tencent.mmkv.MMKV;

import io.openim.android.ouicore.R;
import io.openim.android.ouicore.base.vm.injection.Easy;
import io.openim.android.ouicore.net.RXRetrofit.N;

import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.DisplayUtil;
import io.openim.android.ouicore.utils.LanguageUtil;
import io.openim.android.ouicore.utils.SinkHelper;
import io.openim.android.ouicore.widget.LoadingDialog;
import io.openim.android.ouicore.widget.WaitDialog;


@Deprecated
public class BaseActivity<T extends BaseViewModel, A extends ViewDataBinding> extends AppCompatActivity implements IView {
    //是否释放资源
    private boolean isRelease = true;
    //已经释放资源
    private boolean released = false;
    private boolean isFaster = false;

    protected T vm;
    protected A view;
    private String vmCanonicalName;
    private WaitDialog waitDialog;
    protected MMKV mmkv;

    private LoadingDialog loadingDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.push(this);
        requestedOrientation();
        super.onCreate(savedInstanceState);
        if (null != vm) {
            vm.viewCreate();
        }
        setLightStatus();
        if (waitDialog != null) {
            waitDialog.dismiss();
            waitDialog = null;
        }
        mmkv = MMKV.defaultMMKV();
        waitDialog = new WaitDialog(this);
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        //多语言适配
//        super.attachBaseContext(LanguageUtil.getNewLocalContext(newBase));
//    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        // 兼容androidX在部分手机切换语言失败问题
        if (overrideConfiguration != null) {
            int uiMode = overrideConfiguration.uiMode;
            overrideConfiguration.setTo(getBaseContext().getResources().getConfiguration());
            overrideConfiguration.uiMode = uiMode;
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }


    protected void requestedOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected void bindViewDataBinding(A viewDataBinding) {
        bindViewDataBinding(viewDataBinding, true);
    }

    protected void bindViewDataBinding(A viewDataBinding, boolean lifecycleOwner) {
        view = viewDataBinding;
        setContentView(view.getRoot());
        if (lifecycleOwner) {
            view.setLifecycleOwner(this);
        }
    }

    @Deprecated
    protected void bindVM(Class<T> vm) {
        this.vm = new ViewModelProvider(this).get(vm);
        vmCanonicalName = this.vm.getClass().getCanonicalName();
        bind();
    }

    @Deprecated
    protected void bindVM(Class<T> vm, boolean shareVM) {
        bindVM(vm);
        if (shareVM) {
            BaseApp.inst().putVM(this.vm);
        }
    }

    private void bind() {
        if (null == vm) return;
        vm.setContext(this);
        vm.setIView(this);
    }

    protected void setLightStatus() {
//        Window window = getWindow();
//        //After LOLLIPOP not translucent status bar
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //Then call setStatusBarColor.
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }
    }


    public void toBack(View view) {
        finish();
    }

    boolean touchClearFocus = true;

    public void setTouchClearFocus(boolean touchClearFocus) {
        this.touchClearFocus = touchClearFocus;
    }

    @Deprecated
    public void bindVMByCache(Class<T> vm) {
        try {
            this.vm = Easy.find(vm);
            isRelease = false;
            bind();
        } catch (Exception ignore) {
        }
    }

    @Deprecated
    public void removeCacheVM() {
        if (null != vm) {
            vm.context.clear();
            vm.releaseRes();
            BaseApp.inst().removeCacheVM(vm.getClass());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bind();
        if (null != vm) vm.viewResume();
    }


    @Override
    protected void onPause() {
        exeFaster();
        if (null != vm) {
            vm.viewPause();
            releaseRes();
        }
        super.onPause();
    }

    protected void fasterDestroy() {

    }

    private void releaseRes() {
        if (vm == null) return;
        if (isFinishing() && isRelease && !released) {
            released = true;
            vm.releaseRes();
        }
    }

    @Override
    protected void onDestroy() {
        ActivityManager.remove(this);
        exeFaster();
        N.clearDispose(this);
        releaseRes();
        if (waitDialog != null) {
            waitDialog.dismiss();
            waitDialog = null;
        }
        super.onDestroy();
    }

    private void exeFaster() {
        if (isFinishing() && !isFaster) {
            isFaster = true;
            fasterDestroy();
        }
    }


    /**
     * 点击非获取焦点EditText隐藏键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!touchClearFocus) return super.dispatchTouchEvent(ev);
        View v = getCurrentFocus();
        if (v instanceof EditText) {
            Rect outRect = new Rect();
            v.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                v.clearFocus(); //在根布局添加focusableInTouchMode="true"
                InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 沉侵式状态栏
     */
    public void sink(View view) {
        setLightStatus();
        SinkHelper.get(this).setTranslucentStatus(view);
    }

    public void sink() {
        sink(view.getRoot());
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Object body) {

    }

    @Override
    public void toast(String tips) {
        Toast.makeText(this, tips, Toast.LENGTH_LONG).show();
    }

    @Override
    public void close() {
        finish();
    }

    public void showWaiting() {
        if (waitDialog == null) return;
        if (!waitDialog.isShowing()) {
            waitDialog.setNotDismiss();
            waitDialog.show();
        }
    }

    public void cancelWaiting() {
        if (waitDialog == null) return;
        if (waitDialog.isShowing()) {
            waitDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 是否登录
     * @return
     */
    protected boolean isLogin(Class c){
        if (TextUtils.isEmpty(MMKV.defaultMMKV().decodeString("token"))) {
            startActivity(new Intent(this, c));
            return false;
        }
        return true;
    }

    /**
     * 是否登录
     * @return
     */
    protected boolean isLogin(){
        if (TextUtils.isEmpty(MMKV.defaultMMKV().decodeString("token"))) {
            return false;
        }
        return true;
    }

    protected A getViewDataBinding(){
        return view;
    }

    protected T getViewModel(){
        return vm;
    }

    private Toast toast;
    protected void showToast(String message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // 动态调整 Toolbar 外边距（暴露给子类调用）
    protected void adjustToolbarForStatusBar(View toolbar) {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                // 获取状态栏高度
                int statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top;

                // 动态设置 Toolbar 的顶部外边距
                ViewGroup.LayoutParams params = view.getLayoutParams();
                if (params instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) params;
                    marginParams.topMargin = statusBarHeight; // 设置上边距
                    view.setLayoutParams(marginParams);
                } else {
                    // 如果父容器不支持 MarginLayoutParams，则回退到 padding
                    view.setPadding(
                            view.getPaddingLeft(),
                            statusBarHeight,
                            view.getPaddingRight(),
                            view.getPaddingBottom()
                    );
                }

                return insets;
            }
        });
    }

    /**
     * 复制文本
     * @param str
     */
    protected void copyText(String str){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", str);
        clipboard.setPrimaryClip(clip);
        showToast("复制成功");
    }

    /**
     *  明文和密文转换
     * @param originalText
     * @return
     */
    protected String maskText(String originalText) {
        return "*****";
    }

    protected void launchActivity(Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    protected void launchActivity(Intent targetActivity) {
        startActivity(targetActivity);
    }


    protected void showLoadingDialog() {
        if (loadingDialog != null) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
                loadingDialog.setLoadingDialogCancelable(true);
            }
        } else {
            loadingDialog = new LoadingDialog(this, R.style.DialogStyle);
            loadingDialog.show();
        }
    }

    protected void showLoadingDialog(Boolean flag) {
        if (loadingDialog != null) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
                loadingDialog.setLoadingDialogCancelable(flag);
            }
        } else {
            loadingDialog = new LoadingDialog(this, R.style.DialogStyle);
            loadingDialog.show();
        }
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    static float fontScale = 1f;

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        return DisplayUtil.getResources(this,resources,fontScale);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(DisplayUtil.attachBaseContext(newBase,fontScale));
    }

    public void setFontScale(float fontScale) {
        this.fontScale = fontScale;
        DisplayUtil.recreate(this);
    }
}
