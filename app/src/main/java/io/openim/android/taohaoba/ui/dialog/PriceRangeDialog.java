package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;

/**
 * 价格区间弹窗
 */
public class PriceRangeDialog extends CenterPopupView {

    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(Float minPrice, Float maxPrice); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public PriceRangeDialog(Context context, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_price_range;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        EditText et_min = findViewById(R.id.et_min);
        EditText et_max = findViewById(R.id.et_max);

        et_min.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_min.addTextChangedListener(new AmountTextWatcher(et_min));

        et_max.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        et_max.addTextChangedListener(new AmountTextWatcher(et_max));

        //重置
        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            et_min.setText("");
            et_max.setText("");
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                verificationListener.onSubmit(TextUtils.isEmpty(et_min.getText().toString()) ? null : Float.valueOf(et_min.getText().toString()), TextUtils.isEmpty(et_max.getText().toString()) ? null : Float.valueOf(et_max.getText().toString()));
                dismiss();
            }
        });

    }

    public class AmountTextWatcher implements TextWatcher {
        private final EditText editText;
        private String current = "";
        private final int maxDecimalDigits = 2;

        public AmountTextWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable editable) {
            if (!editable.toString().equals(current)) {
                editText.removeTextChangedListener(this);

                String cleanStr = editable.toString()
                        .replaceAll("[^\\d.]", "")
                        .replaceAll("\\.+", ".")
                        .replaceAll("^0+(\\d+)", "$1");

                if (cleanStr.startsWith(".")) {
                    cleanStr = "0" + cleanStr;
                }

                String[] parts = cleanStr.split("\\.");
                String integerPart = parts.length > 0 ? parts[0] : "0";
                String decimalPart = parts.length > 1 ? parts[1] : "";

                if (integerPart.isEmpty() || Long.parseLong(integerPart) == 0) {
                    integerPart = "0";
                }

                if (decimalPart.length() > maxDecimalDigits) {
                    decimalPart = decimalPart.substring(0, maxDecimalDigits);
                }

                StringBuilder formatted = new StringBuilder();
                formatted.append(integerPart);
                if (decimalPart.length() > 0 || cleanStr.contains(".")) {
                    formatted.append(".").append(decimalPart);
                }

                current = formatted.toString();
                editText.setText(current);
                editText.setSelection(current.length());

                editText.addTextChangedListener(this);
            }
        }
    }

}
