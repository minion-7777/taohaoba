package io.openim.android.taohaoba.ui.dialog;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency1;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.core.CenterPopupView;

import io.openim.android.taohaoba.R;

/**
 * 黑号查询详情弹窗
 */
public class WithdrawalDialog extends CenterPopupView {

    private String phone;
    private Context context;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(Double current); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public WithdrawalDialog(Context context, String phone, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.phone = phone;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_withdrawal;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        EditText etWitdraw = findViewById(R.id.et_withdraw);
        TextView tvWithdrawAll = findViewById(R.id.tv_withdraw_all);
        TextView tv_hint = findViewById(R.id.tv_hint);

        tv_hint.setText("可提现金额："+formatCurrency(phone));

        etWitdraw.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        etWitdraw.addTextChangedListener(new AmountTextWatcher(etWitdraw));

        tvWithdrawAll.setOnClickListener(v->{
            etWitdraw.setText(formatCurrency1(phone));
        });

        findViewById(R.id.iv_close).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v -> {
            if (verificationListener != null) {
                if (etWitdraw.getText().toString().isEmpty()) {
                    shakeAnimation(etWitdraw);
                    Toast.makeText(context, "请输入提现金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                verificationListener.onSubmit(Double.parseDouble(etWitdraw.getText().toString()));
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
