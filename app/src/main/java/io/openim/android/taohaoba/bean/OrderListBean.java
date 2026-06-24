package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderListBean extends BaseResponseBean {


    private List<ListDTO> list;
    private Integer total = 0;

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
        /**
         * id : 50
         * order_no : NO8947045555869041114901372644
         * goods_id : 50
         * goods_title : 王者荣耀售卖超级牛逼的账号先到先得
         * goods_image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
         * goods_price : 1500
         * game_service_name : 苹果,微信,微信140-150大区,微信141区
         * status : 1
         * status_zh : 已付款
         */

        private Integer id;
        private String order_no;
        private Integer goods_id;
        private String goods_title;
        private String goods_image;
        private String goods_price;
        private String game_service_name;
        private Integer game_id;
        private Integer status;
        private String status_zh;
        private String im_group_id;
        private String im_group_name;
        private Integer seller_service_ratio;
        private Double seller_service_price;
        private Double seller_amount_conf;
        private String payment_price;
        private int is_reparation;
        /**
         * order_post_sale : {"id":7,"im_group_id":"1000000286","order_post_sale_status":3,"order_post_sale_reason":1}
         */

        private OrderPostSaleDTO order_post_sale;
        private String game_name;
        private Integer is_customer_service_confirms_account;//是否发送商品账号密码卡片 0否 1是

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

        public String getPayment_price() {
            return payment_price;
        }

        public void setPayment_price(String payment_price) {
            this.payment_price = payment_price;
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

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
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

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGame_service_name() {
            return game_service_name;
        }

        public void setGame_service_name(String game_service_name) {
            this.game_service_name = game_service_name;
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

        public OrderPostSaleDTO getOrder_post_sale() {
            return order_post_sale;
        }

        public void setOrder_post_sale(OrderPostSaleDTO order_post_sale) {
            this.order_post_sale = order_post_sale;
        }

        public static class OrderPostSaleDTO {
            /**
             * id : 7
             * im_group_id : 1000000286
             * order_post_sale_status : 3
             * order_post_sale_reason : 1
             */

            private Integer id;
            private String im_group_id;
            private String im_group_name;
            private Integer order_post_sale_status;
            private Integer order_post_sale_reason;

            public String getIm_group_name() {
                return im_group_name;
            }

            public void setIm_group_name(String im_group_name) {
                this.im_group_name = im_group_name;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getIm_group_id() {
                return im_group_id;
            }

            public void setIm_group_id(String im_group_id) {
                this.im_group_id = im_group_id;
            }

            public Integer getOrder_post_sale_status() {
                return order_post_sale_status;
            }

            public void setOrder_post_sale_status(Integer order_post_sale_status) {
                this.order_post_sale_status = order_post_sale_status;
            }

            public Integer getOrder_post_sale_reason() {
                return order_post_sale_reason;
            }

            public void setOrder_post_sale_reason(Integer order_post_sale_reason) {
                this.order_post_sale_reason = order_post_sale_reason;
            }
        }
    }
}
