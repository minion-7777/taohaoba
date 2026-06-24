package io.openim.android.taohaoba.bean;

/**
 * Time：2023/11/6 14:24
 * Author：qujifan
 * Description：
 */
public class BaseResponseBean {
    private String msg;
    private String error;
    private int code = 200;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
