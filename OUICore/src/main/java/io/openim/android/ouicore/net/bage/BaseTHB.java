package io.openim.android.ouicore.net.bage;

/**
 * Created by WJ on 2017/12/29.
 */

public class BaseTHB<T> {
    public int code;
    public String msg;
    public String error;
    public T data;

    @Override
    public String toString() {
        return "BaseTHB{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}

