package io.openim.android.ouicore.net.RXRetrofit.interceptors;

import android.util.Log;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Time：2023/11/6 16:33
 * Description：
 */
public class HttpLogInterceptor implements HttpLoggingInterceptor.Logger {
    private final StringBuilder messageBuilder = new StringBuilder();

    @Override
    public void log(String message) {
        // 是否debug 模式
        if (message.startsWith("--> POST") ||
                message.startsWith("--> GET") ||
                message.startsWith("--> PUT") ||
                message.startsWith("--> DELETE")
        ) {
            if (messageBuilder!= null) {
                messageBuilder.setLength(0);
                messageBuilder.append(message);
            }
        }

        messageBuilder.append(message);
        // 如果需要打印 JSON 数据，可以在此处进行处理
        messageBuilder.append("\n");
        if (message.startsWith("<-- END HTTP")) {
            Log.d("http", messageBuilder.toString());
            messageBuilder.delete(0, messageBuilder.length());
        }
    }
}
