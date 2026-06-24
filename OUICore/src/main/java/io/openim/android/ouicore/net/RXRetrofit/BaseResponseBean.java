package io.openim.android.ouicore.net.RXRetrofit;

public class BaseResponseBean {
    private String message;
    private Object data;
    private int code;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return message == null ? "" : message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int errorCode) {
        this.code = errorCode;
    }
}
