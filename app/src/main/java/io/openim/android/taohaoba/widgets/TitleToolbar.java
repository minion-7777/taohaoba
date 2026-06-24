package io.openim.android.taohaoba.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import io.openim.android.taohaoba.R;

public class TitleToolbar extends RelativeLayout {

    private TextView tv_title;

    public TitleToolbar(Context context) {
        super(context);
        initView(context);
    }

    public TitleToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
        initAttrs(attrs);
    }

    public TitleToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleToolbar);
        //标题
        String title = typedArray.getString(R.styleable.TitleToolbar_textTitle);

        if (title != null) {
            setTitleText(title);
        }
    }

    public void setTitleText(String title) {
        tv_title.setText(title);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_title_toolber,this,true);
        tv_title = findViewById(R.id.tv_title);
        findViewById(R.id.iv_close).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onClose();
            }
        });
    }

    public interface OnVerificationListener {
        void onClose(); // 关闭
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }
}
