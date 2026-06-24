package io.openim.android.ouicore.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import io.openim.android.ouicore.R;

/**
 * Description：
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(io.openim.android.ouicore.R.layout.dialog_loading);
        setCanceledOnTouchOutside(true);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
    }
    public void setLoadingDialogCancelable(Boolean flag){
        setCanceledOnTouchOutside(flag);
    }
}
