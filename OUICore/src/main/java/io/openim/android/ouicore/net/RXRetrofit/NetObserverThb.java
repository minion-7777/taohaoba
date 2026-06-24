package io.openim.android.ouicore.net.RXRetrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson2.JSONObject;
import com.tencent.mmkv.MMKV;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.event.MessageEvent;
import io.openim.android.ouicore.net.RXRetrofit.Exception.NetworkException;
import io.openim.android.ouicore.net.RXRetrofit.Exception.ThbRetrofitException;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.utils.Constants;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * There's only one corner of the universe you can be sure of improving, and that's your own self.
 */
public abstract class NetObserverThb<T> implements Observer<T> {
    private static final String TAG = "NetObserverThb";
    private Context context;
    private String sign; //用于dispose的sign

    /**
     * @param context 用于dispose的key
     */
    public NetObserverThb(Context context) {
        Log.d("NetDebug", "NetObserverThb");
        this.sign = context.getClass().getSimpleName();
    }

    /**
     * @param sign 用于dispose的key
     */
    public NetObserverThb(String sign) {
        this.sign = sign;
    }

    @Override
    public void onSubscribe(@NonNull final Disposable d) {
        if (null != context) {
            sign = context.getClass().getSimpleName();
        }
        N.addDispose(sign, d);
    }


    @Override
    public void onNext(@NonNull T o) {
        if (HttpConfig.isDebug) {
            onSuccess(o);
        } else {
            try {
                onSuccess(o);
            } catch (Exception e) {
                try {
                    throw new ThbRetrofitException(e);
                } catch (ThbRetrofitException error) {
                    error.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        onComplete();
        BaseTHB baseTHB = new BaseTHB();
        baseTHB.code = 203;//业务异常码
        baseTHB.msg = "";
        baseTHB.error = "";
        baseTHB.data = null;
        e.printStackTrace();
        Log.d(TAG, "onError-egetClass=" + e.getClass());
        Log.d(TAG, "onError-egetMessage=" + e.getMessage());

        if (!isConnected()) {
            e = new NetworkException();
            baseTHB.msg = e.getMessage();
        } else {
            if (e instanceof SocketTimeoutException) {
                baseTHB.msg = "网络超时，请检查网络";
            } else if (e instanceof IOException) {
                baseTHB.msg = "网络错误，请重试";
            } else if (e instanceof ThbRetrofitException) {
                BaseTHB baseThbTemp = JSONObject.parseObject(e.getMessage(), BaseTHB.class);
                baseTHB.code = baseThbTemp.code;
                baseTHB.msg = TextUtils.isEmpty(baseThbTemp.msg) ? "" : baseThbTemp.msg;
                baseTHB.error = TextUtils.isEmpty(baseThbTemp.error) ? "" : baseThbTemp.error;
                baseTHB.data = baseThbTemp.data;
            } else if (e instanceof HttpException) {
                if (((HttpException)e).code() == 401) {
                    baseTHB.msg = ((HttpException)e).code() == 401 ? "账号在其它地方登录" : "服务器错误: " + e.getMessage();
                    EventBus.getDefault().post(new MessageEvent(Constants.NOLOGIN));
                }else {
                    e = new ThbRetrofitException("服务器错误: " + ((HttpException) e).code());
                    baseTHB.msg = "服务器错误: " + e.getMessage();
                }
            }
//            else {
//                baseTHB.error = "未知错误: " + e.getMessage();
//            }
        }

        if (HttpConfig.isDebug) {
            onFailure(baseTHB);
        } else {
            try {
                onFailure(baseTHB);
            } catch (Exception e1) {
                try {
                    throw new ThbRetrofitException(e1);
                } catch (ThbRetrofitException error) {
                    error.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onComplete() {

    }
    public abstract void onSuccess(@NonNull T o);

    protected void onFailure(BaseTHB baseTHB) {
    }

    /**
     * 判断网络是否连接
     * @return
     */
    public static boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) BaseApp.inst()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
