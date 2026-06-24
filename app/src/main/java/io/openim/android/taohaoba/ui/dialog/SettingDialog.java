package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.AttachPopupView;

import io.openim.android.taohaoba.R;

/**
 * 筛选
 */
public class SettingDialog extends AttachPopupView {

    private Context context;
    private OnFilterChangeListener mListener;
    private int msg;

    public SettingDialog(@NonNull Context context, int msg, OnFilterChangeListener listener) {
        super(context);
        this.context = context;
        this.msg = msg;
        this.mListener = listener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_setting;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        TextView tv_msg_count = findViewById(R.id.tv_msg_count);
        tv_msg_count.setVisibility(msg == 0 ? GONE : VISIBLE);
        tv_msg_count.setText(String.valueOf(msg));

        findViewById(R.id.tv_go_home).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItem1Click();
            }
        });

        findViewById(R.id.tv_go_msg).setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItem2Click();
            }
        });

    }

    public interface OnFilterChangeListener {
        void onItem1Click();
        void onItem2Click();
    }

}
