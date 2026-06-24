package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class TransactionConfBean {

    /**
     * id : 7
     * name : 买家违约金
     */

    private Integer id;
    private String name;
    private boolean isCheck = false;

    public static TransactionConfBean objectFromData(String str) {

        return new Gson().fromJson(str, TransactionConfBean.class);
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
