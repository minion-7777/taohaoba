package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class OrderDetailsBean {

    /**
     * id : 47
     * goods_id : 58
     * goods_title : 王者荣耀售卖超级牛逼的账号先到先得
     * goods_no : NO9962485364713938673838747767
     * order_no : NO1746767143179013458483179454
     * goods_price : 1000
     * payment_price : 1160.5
     * reparation_price : 100
     * pattern_price : 60.5
     * payment_type : 0
     * status : 0
     * status_zh : 待付款
     * pattern_name : 自由交易
     * game_service_name :
     * device_name : 苹果
     * operator_name : QQ
     * place_time : 2025-05-09 13:05:44
     * pay_time :
     * deal_time :
     * take_time :
     * cancel_time :
     * refund_time :
     * system_refund_time :
     * verify_time :
     * send_time :
     * refund_content :
     * unpaid_conf_time : 30
     * verify_conf_time : 0
     * take_conf_time : 0
     */

    private Integer id;
    private Integer goods_id;
    private String goods_title;
    private String goods_no;
    private String order_no;
    private Double goods_price;
    private Double payment_price;
    private Double reparation_price;
    private Double pattern_price;
    private Integer payment_type;
    private Integer status;
    private String status_zh;
    private String pattern_name;
    private String game_service_name;
    private String device_name;
    private String operator_name;
    private String place_time;
    private String pay_time;
    private String deal_time;
    private String take_time;
    private String cancel_time;
    private String refund_time;
    private String system_refund_time;
    private String verify_time;
    private String send_time;
    private String refund_content;
    private Integer unpaid_conf_time;
    private Integer verify_conf_time;
    private Integer take_conf_time;
    private String goods_image;
    private Integer game_id;
    private String im_group_id;
    private String im_group_name;
    private String buy_user_contact;
    private String buy_user_id;
    private String sell_user_id;
    private Double seller_service_fee;
    private Double reparation_info_amount;
    private String reparation_info_ratio;
    private Double reparation_info_price;
    private Integer seller_service_ratio;
    private Double seller_service_price;
    private Double seller_amount_conf;
    private int is_reparation;
    private Integer is_customer_service_confirms_account;//是否发送商品账号密码卡片 0否 1是
    private String game_name;
    private int is_combined_payment;//是否组合支付 0否 1是
    private Double freeze_price;//冻结金额
    private Double balance_available;//用户余额
    private Integer user_coupon_id;//用户卷包id
    private Double coupon_amount_deduct;//优惠劵减免金额
    private Integer conpon_type;//优惠劵类型

    public Integer getConpon_type() {
        return conpon_type;
    }

    public void setConpon_type(Integer conpon_type) {
        this.conpon_type = conpon_type;
    }

    public Integer getUser_coupon_id() {
        return user_coupon_id;
    }

    public void setUser_coupon_id(Integer user_coupon_id) {
        this.user_coupon_id = user_coupon_id;
    }

    public Double getCoupon_amount_deduct() {
        return coupon_amount_deduct;
    }

    public void setCoupon_amount_deduct(Double coupon_amount_deduct) {
        this.coupon_amount_deduct = coupon_amount_deduct;
    }

    public int getIs_combined_payment() {
        return is_combined_payment;
    }

    public void setIs_combined_payment(int is_combined_payment) {
        this.is_combined_payment = is_combined_payment;
    }

    public Double getFreeze_price() {
        return freeze_price;
    }

    public void setFreeze_price(Double freeze_price) {
        this.freeze_price = freeze_price;
    }

    public Double getBalance_available() {
        return balance_available;
    }

    public void setBalance_available(Double balance_available) {
        this.balance_available = balance_available;
    }

    public Integer getIs_customer_service_confirms_account() {
        return is_customer_service_confirms_account;
    }

    public void setIs_customer_service_confirms_account(Integer is_customer_service_confirms_account) {
        this.is_customer_service_confirms_account = is_customer_service_confirms_account;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getIs_reparation() {
        return is_reparation;
    }

    public void setIs_reparation(int is_reparation) {
        this.is_reparation = is_reparation;
    }

    public Integer getSeller_service_ratio() {
        return seller_service_ratio;
    }

    public void setSeller_service_ratio(Integer seller_service_ratio) {
        this.seller_service_ratio = seller_service_ratio;
    }

    public Double getSeller_service_price() {
        return seller_service_price;
    }

    public void setSeller_service_price(Double seller_service_price) {
        this.seller_service_price = seller_service_price;
    }

    public Double getSeller_amount_conf() {
        return seller_amount_conf;
    }

    public void setSeller_amount_conf(Double seller_amount_conf) {
        this.seller_amount_conf = seller_amount_conf;
    }

    public Double getReparation_info_amount() {
        return reparation_info_amount;
    }

    public void setReparation_info_amount(Double reparation_info_amount) {
        this.reparation_info_amount = reparation_info_amount;
    }

    public String getReparation_info_ratio() {
        return reparation_info_ratio;
    }

    public void setReparation_info_ratio(String reparation_info_ratio) {
        this.reparation_info_ratio = reparation_info_ratio;
    }

    public Double getReparation_info_price() {
        return reparation_info_price;
    }

    public void setReparation_info_price(Double reparation_info_price) {
        this.reparation_info_price = reparation_info_price;
    }

    public Double getSeller_service_fee() {
        return seller_service_fee;
    }

    public void setSeller_service_fee(Double seller_service_fee) {
        this.seller_service_fee = seller_service_fee;
    }

    public String getBuy_user_id() {
        return buy_user_id;
    }

    public void setBuy_user_id(String buy_user_id) {
        this.buy_user_id = buy_user_id;
    }

    public String getSell_user_id() {
        return sell_user_id;
    }

    public void setSell_user_id(String sell_user_id) {
        this.sell_user_id = sell_user_id;
    }

    public String getBuy_user_contact() {
        return buy_user_contact;
    }

    public void setBuy_user_contact(String buy_user_contact) {
        this.buy_user_contact = buy_user_contact;
    }

    public String getIm_group_id() {
        return im_group_id;
    }

    public void setIm_group_id(String im_group_id) {
        this.im_group_id = im_group_id;
    }

    public String getIm_group_name() {
        return im_group_name;
    }

    public void setIm_group_name(String im_group_name) {
        this.im_group_name = im_group_name;
    }

    public static OrderDetailsBean objectFromData(String str) {

        return new Gson().fromJson(str, OrderDetailsBean.class);
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_title() {
        return goods_title;
    }

    public void setGoods_title(String goods_title) {
        this.goods_title = goods_title;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Double getPayment_price() {
        return payment_price;
    }

    public void setPayment_price(Double payment_price) {
        this.payment_price = payment_price;
    }

    public Double getReparation_price() {
        return reparation_price;
    }

    public void setReparation_price(Double reparation_price) {
        this.reparation_price = reparation_price;
    }

    public Double getPattern_price() {
        return pattern_price;
    }

    public void setPattern_price(Double pattern_price) {
        this.pattern_price = pattern_price;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_zh() {
        return status_zh;
    }

    public void setStatus_zh(String status_zh) {
        this.status_zh = status_zh;
    }

    public String getPattern_name() {
        return pattern_name;
    }

    public void setPattern_name(String pattern_name) {
        this.pattern_name = pattern_name;
    }

    public String getGame_service_name() {
        return game_service_name;
    }

    public void setGame_service_name(String game_service_name) {
        this.game_service_name = game_service_name;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getPlace_time() {
        return place_time;
    }

    public void setPlace_time(String place_time) {
        this.place_time = place_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public String getTake_time() {
        return take_time;
    }

    public void setTake_time(String take_time) {
        this.take_time = take_time;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(String refund_time) {
        this.refund_time = refund_time;
    }

    public String getSystem_refund_time() {
        return system_refund_time;
    }

    public void setSystem_refund_time(String system_refund_time) {
        this.system_refund_time = system_refund_time;
    }

    public String getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(String verify_time) {
        this.verify_time = verify_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getRefund_content() {
        return refund_content;
    }

    public void setRefund_content(String refund_content) {
        this.refund_content = refund_content;
    }

    public Integer getUnpaid_conf_time() {
        return unpaid_conf_time;
    }

    public void setUnpaid_conf_time(Integer unpaid_conf_time) {
        this.unpaid_conf_time = unpaid_conf_time;
    }

    public Integer getVerify_conf_time() {
        return verify_conf_time;
    }

    public void setVerify_conf_time(Integer verify_conf_time) {
        this.verify_conf_time = verify_conf_time;
    }

    public Integer getTake_conf_time() {
        return take_conf_time;
    }

    public void setTake_conf_time(Integer take_conf_time) {
        this.take_conf_time = take_conf_time;
    }
}
