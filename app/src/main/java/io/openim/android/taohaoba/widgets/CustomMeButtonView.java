package io.openim.android.taohaoba.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import io.openim.android.taohaoba.R;


public class CustomMeButtonView extends ConstraintLayout {

    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_count;
    private ConstraintLayout cl_but;
    private int mIcon;
    private String mText;
    private int mCount;

    public CustomMeButtonView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CustomMeButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initTypeValue(context,attrs);
        initView(context);
    }

    public void initTypeValue(Context context ,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomMeButtonView);
        mIcon = a.getResourceId(R.styleable.CustomMeButtonView_customIcon1, 0);
        mText = a.getString(R.styleable.CustomMeButtonView_customText);
        mCount = a.getInteger(R.styleable.CustomMeButtonView_customCount,0);

        a.recycle();
    }

    public void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_me_button,this,true);

        iv_icon = findViewById(R.id.iv_icon);
        tv_name = findViewById(R.id.tv_name);
        tv_count = findViewById(R.id.tv_count);
        cl_but = findViewById(R.id.cl_but);


        if (mIcon != 0) {
            iv_icon.setImageResource(mIcon);
        }
        if (!TextUtils.isEmpty(mText)) {
            tv_name.setText(mText);
        }
        tv_count.setVisibility(mCount != 0 ? VISIBLE : GONE);
        tv_count.setText(String.valueOf(mCount));
    }

//    public void setBackClickListener(OnClickListener listener){
//        btn_left.setOnClickListener(listener);
//    }
//
//    public void setRightClickListener(OnClickListener listener){
//        btn_right.setOnClickListener(listener);
//    }

    public void setCount(int str) {
        mCount = str;
//        invalidate();
        tv_count.setVisibility(mCount != 0 ? VISIBLE : GONE);
        tv_count.setText(String.valueOf(mCount));
    }

}
