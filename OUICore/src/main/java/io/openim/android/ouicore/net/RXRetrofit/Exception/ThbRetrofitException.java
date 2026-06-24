package io.openim.android.ouicore.net.RXRetrofit.Exception;


import android.content.Context;

import io.openim.android.ouicore.R;
import io.openim.android.ouicore.base.BaseApp;

public class ThbRetrofitException extends Exception {
private static final long serialVersionUID = 114948L;

    public ThbRetrofitException(int code, String errMsg) {
        super(getErrTips(code, errMsg));
    }
    private static String getErrTips(int code, String errMsg) {
        Context context = BaseApp.inst();
        switch (code) {
            case 203:
                return errMsg;
        }
        return errMsg;
    }

    public ThbRetrofitException(String message) {
        super(message);
    }

    public ThbRetrofitException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThbRetrofitException(Throwable cause) {
        super(cause);
    }

}
