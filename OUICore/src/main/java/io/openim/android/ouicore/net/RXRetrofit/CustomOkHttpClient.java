package io.openim.android.ouicore.net.RXRetrofit;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import io.openim.android.ouicore.net.RXRetrofit.interceptors.HttpLogInterceptor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Description：
 */
public class CustomOkHttpClient {
    private static final String TAG = "CustomOkHttpClient";
    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        create();
        return client;
    }

    private static void create() {
        HttpLoggingInterceptor httpLogger = new HttpLoggingInterceptor(new HttpLogInterceptor());
        httpLogger.setLevel(HttpLoggingInterceptor.Level.BODY);
        String jwtToken = MMKV.defaultMMKV().decodeString("token", "");
        Log.d(TAG,"jwtToken="+jwtToken);
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                builder.addHeader("accept", "application/json");
                builder.addHeader("clientType", "store");
                builder.addHeader("Platform", "2");
                if (!TextUtils.isEmpty(jwtToken)) {
                    builder.addHeader("Authorization", MMKV.defaultMMKV().decodeString("token", ""));
                }

                return chain.proceed(builder.build());
            }
        };

//        File cacheDir = new File(AppApplication.getInstance().getApplicationContext().getCacheDir(), "http-cache");
//        long cacheSize = 10 * 1024 * 1024;
//        Cache cache = new Cache(cacheDir, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .proxy(Proxy.NO_PROXY)// 不使用代理
//                .cache(cache)
                .retryOnConnectionFailure(true)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
//        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(httpLogger);
//        }
        client = builder.build();
    }

}
