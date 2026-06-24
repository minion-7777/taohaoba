package io.openim.android.taohaoba.bean;

import java.util.List;

public class CouponListBean {

    private Integer total;
    private List<ListDTO> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO {
//                "user_coupon_id": 1, //卷包id
//                        "coupon_id": 1,
//                        "coupon_name": "满10减8", //优惠劵名称
//                        "coupon_description": "满10减8", //优惠劵描述
//                        "coupon_amount": 8, //优惠劵金额
//                        "coupon_amount_minimum": 10, //优惠劵门槛金额
//                        "conpon_type": 1, //优惠卷类型 1包赔卷 2支付卷 3服务费卷 4商品费卷
//                        "user_coupon_is_use": 0, //是否已使用 0否 1是
//                        "user_coupon_start_time": "2025-09-26 16:33:17", //有效期开始时间
//                        "user_coupon_end_time": "2025-09-27 20:19:57" //有效期结束时间

        private Integer user_coupon_id;
        private Integer coupon_id;
        private String coupon_name;
        private String coupon_description;
        private String coupon_amount;
        private String coupon_amount_minimum;
        private Integer conpon_type;
        private Integer user_coupon_is_use;
        private String user_coupon_start_time;
        private String user_coupon_end_time;
        private boolean isSelect = false;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public Integer getUser_coupon_id() {
            return user_coupon_id;
        }

        public void setUser_coupon_id(Integer user_coupon_id) {
            this.user_coupon_id = user_coupon_id;
        }

        public Integer getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(Integer coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getCoupon_name() {
            return coupon_name;
        }

        public void setCoupon_name(String coupon_name) {
            this.coupon_name = coupon_name;
        }

        public String getCoupon_description() {
            return coupon_description;
        }

        public void setCoupon_description(String coupon_description) {
            this.coupon_description = coupon_description;
        }

        public String getCoupon_amount() {
            return coupon_amount;
        }

        public void setCoupon_amount(String coupon_amount) {
            this.coupon_amount = coupon_amount;
        }

        public String getCoupon_amount_minimum() {
            return coupon_amount_minimum;
        }

        public void setCoupon_amount_minimum(String coupon_amount_minimum) {
            this.coupon_amount_minimum = coupon_amount_minimum;
        }

        public Integer getConpon_type() {
            return conpon_type;
        }

        public void setConpon_type(Integer conpon_type) {
            this.conpon_type = conpon_type;
        }

        public Integer getUser_coupon_is_use() {
            return user_coupon_is_use;
        }

        public void setUser_coupon_is_use(Integer user_coupon_is_use) {
            this.user_coupon_is_use = user_coupon_is_use;
        }

        public String getUser_coupon_start_time() {
            return user_coupon_start_time;
        }

        public void setUser_coupon_start_time(String user_coupon_start_time) {
            this.user_coupon_start_time = user_coupon_start_time;
        }

        public String getUser_coupon_end_time() {
            return user_coupon_end_time;
        }

        public void setUser_coupon_end_time(String user_coupon_end_time) {
            this.user_coupon_end_time = user_coupon_end_time;
        }
    }
}
