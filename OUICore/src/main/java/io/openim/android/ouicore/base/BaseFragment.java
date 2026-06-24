package io.openim.android.ouicore.base;

import static com.luck.picture.lib.utils.ToastUtils.showToast;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.tencent.mmkv.MMKV;

import io.openim.android.ouicore.R;
import io.openim.android.ouicore.widget.LoadingDialog;

public class BaseFragment<T extends BaseViewModel> extends Fragment implements IView {

    private int page;
    private View rootLayout;
    protected MMKV mmkv;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    protected T vm;
    private LoadingDialog loadingDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (null != vm) {
            vm.viewCreate();
        }
        mmkv = MMKV.defaultMMKV();
        super.onCreate(savedInstanceState);
    }

    protected void bindVM(Class<T> vm) {
        this.vm = new ViewModelProvider(this).get(vm);
        this.vm.setContext(getContext());
        this.vm.setIView(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().isFinishing() && null != vm) {
            vm.releaseRes();
        }
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onSuccess(Object body) {

    }

    private Toast toast;

    @Override
    public void toast(String tips) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(getContext(), tips, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void close() {

    }

    // 动态调整 Toolbar 内边距（暴露给子类调用）
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
    protected void copyText(Context context, String str){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", str);
        clipboard.setPrimaryClip(clip);
        showToast(context, "复制成功");
    }

    /**
     * 是否登录
     * @return
     */
    protected boolean isLogin(Class c){
        if (TextUtils.isEmpty(mmkv.defaultMMKV().decodeString("token"))) {
            startActivity(new Intent(getContext(), c));
            return false;
        }
        return true;
    }

    /**
     * 是否登录
     * @return
     */
    protected boolean isLogin(){
        if (TextUtils.isEmpty(mmkv.defaultMMKV().decodeString("token"))) {
            return false;
        }
        return true;
    }

    /**
     *  明文和密文转换
     * @param originalText
     * @return
     */
    protected String maskText(String originalText) {
        return "*****";
    }

    protected void showLoadingDialog() {
        if (loadingDialog != null) {
            if (!loadingDialog.isShowing()) {
                loadingDialog.show();
                loadingDialog.setLoadingDialogCancelable(true);
            }
        } else {
            loadingDialog = new LoadingDialog(getContext(), R.style.DialogStyle);
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
            loadingDialog = new LoadingDialog(getContext(), R.style.DialogStyle);
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
}
