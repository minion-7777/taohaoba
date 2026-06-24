package io.openim.android.taohaoba.widgets;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 自定义时分秒倒计时控件
 */
public class CountdownView extends AppCompatTextView {
    private static final String TIME_FORMAT = "%02d:%02d:%02d";
    private long totalSeconds;  // 总秒数
    private long remainingSeconds; // 剩余秒数
    private boolean isRunning;  // 倒计时状态
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            if (remainingSeconds > 0) {
                remainingSeconds--;
                updateTimeDisplay();
                handler.postDelayed(this, 1000);
            } else {
                stop();
                if (listener != null) listener.onFinish();
            }
        }
    };
    private OnCountdownListener listener;

    // 构造函数（支持XML属性）
    public CountdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultStyle();  // 初始化默认字体/颜色
    }

    // 初始化倒计时
    public void init(long seconds) {
        this.totalSeconds = seconds;
        this.remainingSeconds = seconds;
        updateTimeDisplay();
    }

    // 开始倒计时
    public void start() {
        if (!isRunning) {
            isRunning = true;
            handler.post(updateTask);
        }
    }

    // 停止倒计时
    public void stop() {
        isRunning = false;
        handler.removeCallbacks(updateTask);
    }

    // 更新显示
    private void updateTimeDisplay() {
        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;
        setText(String.format(TIME_FORMAT, hours, minutes, seconds));
    }

    // 样式初始化
    private void initDefaultStyle() {
        setTextSize(12);  // 默认12sp
        setTextColor(Color.WHITE);
    }

    // 回调接口
    public interface OnCountdownListener {
        void onFinish();
    }

    public void setOnCountdownListener(OnCountdownListener listener) {
        this.listener = listener;
    }
}
