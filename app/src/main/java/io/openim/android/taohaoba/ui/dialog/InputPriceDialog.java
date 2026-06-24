package io.openim.android.taohaoba.ui.dialog;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatCurrency;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.xpopup.core.BottomPopupView;

import io.openim.android.taohaoba.R;

/**
 * 游戏信息配置-输入价格弹窗
 */
public class InputPriceDialog extends BottomPopupView {

    private Context context;
    private int seller_service_ratio;//卖家费率
    private double seller_service_price;//卖家最低费率
    private double seller_amount_conf;//固定金额
    private Handler handler = new Handler();
    private Runnable runnable;
    private double actualPrice;
    private String price;


    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String amount); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public InputPriceDialog(Context context, int seller_service_ratio, double seller_service_price, double seller_amount_conf, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.seller_service_ratio = seller_service_ratio;
        this.seller_service_price = seller_service_price;
        this.seller_amount_conf = seller_amount_conf;
        this.verificationListener = listener;
    }

    public InputPriceDialog(Context context, String price, int seller_service_ratio, double seller_service_price, double seller_amount_conf, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.price = price;
        this.seller_service_ratio = seller_service_ratio;
        this.seller_service_price = seller_service_price;
        this.seller_amount_conf = seller_amount_conf;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_input_price;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        EditText tv_amount = findViewById(R.id.tv_amount);  // 获取 EditText
        TextView tv_text2 = findViewById(R.id.tv_text2);
        TextView tv_text3 = findViewById(R.id.tv_text3);
        TextView tv_text4 = findViewById(R.id.tv_text4);


        tv_text2.setText("费率"+seller_service_ratio+"%");
        tv_text3.setText(formatCurrency(String.valueOf(seller_service_price)));

        findViewById(R.id.iv_close).setOnClickListener(v->{
            dismiss();
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (TextUtils.isEmpty(tv_amount.getText().toString())) {
                shakeAnimation(tv_amount);
                Toast.makeText(context, "请输入价格", Toast.LENGTH_SHORT).show();
                return;
            }
            if (actualPrice < 0) {
                shakeAnimation(tv_amount);
                Toast.makeText(context, "实际到手价格应≥0，请调整价格", Toast.LENGTH_SHORT).show();
                return;
            }
            if (verificationListener != null) {
                verificationListener.onSubmit(TextUtils.isEmpty(tv_amount.getText().toString()) ? "0" : tv_amount.getText().toString());
                dismiss();
            }
        });

        // 添加 TextWatcher 监听输入变化
        tv_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // 在文字改变前，不做任何操作
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 在文字改变过程中，不做任何操作
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 输入停止后 1 秒执行的代码
                if (runnable != null) {
                    handler.removeCallbacks(runnable);  // 移除之前的回调
                }
                // 创建新的 Runnable，延迟 1 秒执行
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (!TextUtils.isEmpty(editable.toString())) {
                            // 在这里执行你想要的方法
                            tv_text3.setText(formatCurrency(String.valueOf(calculateFee(Double.valueOf(editable.toString()), seller_service_ratio, seller_service_price, seller_amount_conf))));
                            actualPrice = (Double.valueOf(editable.toString())-calculateFee(Double.valueOf(editable.toString()), seller_service_ratio, seller_service_price, seller_amount_conf));
                            tv_text4.setText(formatCurrency(String.valueOf(actualPrice)));
                        }
                    }
                };
                // 延迟 1 秒执行
                handler.postDelayed(runnable, 1000);
            }
        });

        // 使用正则表达式限制输入
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                // 限制输入第一位不能是小数点
                if (dstart == 0 && source.equals(".")) {
                    return "";
                }

                // 限制只能输入最多两位小数
                String currentText = dest.toString() + source.toString();
                if (currentText.matches(".*\\.\\d{3,}")) {
                    return "";
                }

                // 限制输入总位数不能大于 10
                int newLength = dest.length() + (end - start);
                if (newLength > 10) {
                    return "";
                }

                return null;
            }
        };

        tv_amount.setFilters(new InputFilter[]{filter});

        tv_amount.setText(price);

    }

    /**
     * 设置手续费
     * @param amount 输入的金额
     * @param feePercentage 手续费比例
     * @param minAmount 最低手续费金额
     * @return
     */
    public static double calculateFee(double amount, int feePercentage, double minAmount, double seller_amount_conf) {
        // 计算手续费对应的值，注意保持精度
        double fee = amount * (Double.valueOf(String.valueOf(feePercentage))/100);
        // 如果手续费大于 minAmount，则返回手续费；否则返回 minAmount
        return amount > seller_amount_conf ? fee : minAmount;
    }
}
