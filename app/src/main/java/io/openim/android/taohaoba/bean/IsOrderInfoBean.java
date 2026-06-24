package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class IsOrderInfoBean {

    /**
     * is_order_info : true
     * order_id : 10000147
     */

    private Boolean is_order_info;
    private Integer order_id;

    public static IsOrderInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, IsOrderInfoBean.class);
    }

    public Boolean isIs_order_info() {
        return is_order_info;
    }

    public void setIs_order_info(Boolean is_order_info) {
        this.is_order_info = is_order_info;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }
}
