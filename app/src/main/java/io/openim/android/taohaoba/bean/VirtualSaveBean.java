package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class VirtualSaveBean {

    /**
     * order_info : {"id":null,"buy_user_id":55,"sell_user_id":63,"goods_id":1208,"game_id":18,"goods_title":"lee测试0531007","goods_no":"NO1748687802","goods_image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","order_no":null,"goods_price":1000,"payment_price":1150,"reparation_price":100,"pattern_price":50,"payment_type":null,"status":null,"status_zh":null,"pattern_name":"平台寄售","game_service_name":"电信,怒瑞玛","device_name":"苹果","operator_name":"QQ","buy_user_contact":null,"place_time":null,"pay_time":null,"deal_time":null,"take_time":null,"cancel_time":null,"refund_time":null,"system_refund_time":null,"verify_time":null,"send_time":null,"refund_content":null,"unpaid_conf_time":0,"verify_conf_time":0,"take_conf_time":0,"im_group_id":null}
     */

    private OrderInfoDTO order_info;

    public static VirtualSaveBean objectFromData(String str) {

        return new Gson().fromJson(str, VirtualSaveBean.class);
    }

    public OrderInfoDTO getOrder_info() {
        return order_info;
    }

    public void setOrder_info(OrderInfoDTO order_info) {
        this.order_info = order_info;
    }

    public static class OrderInfoDTO {
        /**
         * id : null
         * buy_user_id : 55
         * sell_user_id : 63
         * goods_id : 1208
         * game_id : 18
         * goods_title : lee测试0531007
         * goods_no : NO1748687802
         * goods_image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
         * order_no : null
         * goods_price : 1000
         * payment_price : 1150
         * reparation_price : 100
         * pattern_price : 50
         * payment_type : null
         * status : null
         * status_zh : null
         * pattern_name : 平台寄售
         * game_service_name : 电信,怒瑞玛
         * device_name : 苹果
         * operator_name : QQ
         * buy_user_contact : null
         * place_time : null
         * pay_time : null
         * deal_time : null
         * take_time : null
         * cancel_time : null
         * refund_time : null
         * system_refund_time : null
         * verify_time : null
         * send_time : null
         * refund_content : null
         * unpaid_conf_time : 0
         * verify_conf_time : 0
         * take_conf_time : 0
         * im_group_id : null
         */

        private Object id;
        private Integer buy_user_id;
        private Integer sell_user_id;
        private Integer goods_id;
        private Integer game_id;
        private String goods_title;
        private String goods_no;
        private String goods_image;
        private Object order_no;
        private Integer goods_price;
        private Integer payment_price;
        private Integer reparation_price;
        private Integer pattern_price;
        private Object payment_type;
        private Object status;
        private Object status_zh;
        private String pattern_name;
        private String game_service_name;
        private String device_name;
        private String operator_name;
        private Object buy_user_contact;
        private Object place_time;
        private Object pay_time;
        private Object deal_time;
        private Object take_time;
        private Object cancel_time;
        private Object refund_time;
        private Object system_refund_time;
        private Object verify_time;
        private Object send_time;
        private Object refund_content;
        private Integer unpaid_conf_time;
        private Integer verify_conf_time;
        private Integer take_conf_time;
        private Object im_group_id;

        public static OrderInfoDTO objectFromData(String str) {

            return new Gson().fromJson(str, OrderInfoDTO.class);
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Integer getBuy_user_id() {
            return buy_user_id;
        }

        public void setBuy_user_id(Integer buy_user_id) {
            this.buy_user_id = buy_user_id;
        }

        public Integer getSell_user_id() {
            return sell_user_id;
        }

        public void setSell_user_id(Integer sell_user_id) {
            this.sell_user_id = sell_user_id;
        }

        public Integer getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(Integer goods_id) {
            this.goods_id = goods_id;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public Object getOrder_no() {
            return order_no;
        }

        public void setOrder_no(Object order_no) {
            this.order_no = order_no;
        }

        public Integer getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(Integer goods_price) {
            this.goods_price = goods_price;
        }

        public Integer getPayment_price() {
            return payment_price;
        }

        public void setPayment_price(Integer payment_price) {
            this.payment_price = payment_price;
        }

        public Integer getReparation_price() {
            return reparation_price;
        }

        public void setReparation_price(Integer reparation_price) {
            this.reparation_price = reparation_price;
        }

        public Integer getPattern_price() {
            return pattern_price;
        }

        public void setPattern_price(Integer pattern_price) {
            this.pattern_price = pattern_price;
        }

        public Object getPayment_type() {
            return payment_type;
        }

        public void setPayment_type(Object payment_type) {
            this.payment_type = payment_type;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getStatus_zh() {
            return status_zh;
        }

        public void setStatus_zh(Object status_zh) {
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

        public Object getBuy_user_contact() {
            return buy_user_contact;
        }

        public void setBuy_user_contact(Object buy_user_contact) {
            this.buy_user_contact = buy_user_contact;
        }

        public Object getPlace_time() {
            return place_time;
        }

        public void setPlace_time(Object place_time) {
            this.place_time = place_time;
        }

        public Object getPay_time() {
            return pay_time;
        }

        public void setPay_time(Object pay_time) {
            this.pay_time = pay_time;
        }

        public Object getDeal_time() {
            return deal_time;
        }

        public void setDeal_time(Object deal_time) {
            this.deal_time = deal_time;
        }

        public Object getTake_time() {
            return take_time;
        }

        public void setTake_time(Object take_time) {
            this.take_time = take_time;
        }

        public Object getCancel_time() {
            return cancel_time;
        }

        public void setCancel_time(Object cancel_time) {
            this.cancel_time = cancel_time;
        }

        public Object getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(Object refund_time) {
            this.refund_time = refund_time;
        }

        public Object getSystem_refund_time() {
            return system_refund_time;
        }

        public void setSystem_refund_time(Object system_refund_time) {
            this.system_refund_time = system_refund_time;
        }

        public Object getVerify_time() {
            return verify_time;
        }

        public void setVerify_time(Object verify_time) {
            this.verify_time = verify_time;
        }

        public Object getSend_time() {
            return send_time;
        }

        public void setSend_time(Object send_time) {
            this.send_time = send_time;
        }

        public Object getRefund_content() {
            return refund_content;
        }

        public void setRefund_content(Object refund_content) {
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

        public Object getIm_group_id() {
            return im_group_id;
        }

        public void setIm_group_id(Object im_group_id) {
            this.im_group_id = im_group_id;
        }
    }
}
