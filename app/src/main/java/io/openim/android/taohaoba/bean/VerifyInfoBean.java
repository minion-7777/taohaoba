package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class VerifyInfoBean {

    /**
     * number : 430421199708138211
     * realname : 陈青
     */

    private String number;
    private String realname;

    public static VerifyInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, VerifyInfoBean.class);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}
