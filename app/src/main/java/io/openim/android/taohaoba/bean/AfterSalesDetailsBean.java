package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class AfterSalesDetailsBean {

    /**
     * id : 7
     * im_group_id :
     * order_post_sale_status : 3
     * order_post_sale_phone : 13843838940
     * order_post_sale_wx_code :
     * order_post_sale_imgs :
     * order_post_sale_reason : 1
     * order_post_sale_handle : 处理成功
     * order_post_sale_notes :
     * order_post_sale_acceptance_time : 2025-07-08 17:23:52
     * order_post_sale_close_time : 2025-07-08 15:16:54
     * order_post_sale_complete_time : 2025-07-08 18:45:37
     * created_time : 2025-07-08 10:45:37
     * order : {"order_no":"NO1750745641","goods_no":"NO1750078633","goods_title":"chen王者荣耀00016","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg","goods_price":100,"payment_price":105,"penalty_id":1,"is_reparation":1,"place_time":"2025-06-24 14:14:02","pay_time":"2025-06-24 14:14:02","deal_time":"2025-06-25 14:15:01"}
     */

    private Integer id;
    private String im_group_id;
    private Integer order_post_sale_status;
    private String order_post_sale_phone;
    private String order_post_sale_wx_code;
    private String order_post_sale_imgs;
    private Integer order_post_sale_reason;
    private String order_post_sale_handle;
    private String order_post_sale_notes;
    private String order_post_sale_acceptance_time;
    private String order_post_sale_close_time;
    private String order_post_sale_complete_time;
    private String created_time;
    private OrderDTO order;
    private Integer order_post_sale_is_reparation;
    private Integer order_post_sale_reparation_examine;
    private String order_post_sale_reparation_notes;
    private String order_post_sale_examine_time;
    private String order_post_sale_reparation_price;//赔付金额

    public String getOrder_post_sale_reparation_price() {
        return order_post_sale_reparation_price;
    }

    public void setOrder_post_sale_reparation_price(String order_post_sale_reparation_price) {
        this.order_post_sale_reparation_price = order_post_sale_reparation_price;
    }

    public Integer getOrder_post_sale_is_reparation() {
        return order_post_sale_is_reparation;
    }

    public void setOrder_post_sale_is_reparation(Integer order_post_sale_is_reparation) {
        this.order_post_sale_is_reparation = order_post_sale_is_reparation;
    }

    public Integer getOrder_post_sale_reparation_examine() {
        return order_post_sale_reparation_examine;
    }

    public void setOrder_post_sale_reparation_examine(Integer order_post_sale_reparation_examine) {
        this.order_post_sale_reparation_examine = order_post_sale_reparation_examine;
    }

    public String getOrder_post_sale_reparation_notes() {
        return order_post_sale_reparation_notes;
    }

    public void setOrder_post_sale_reparation_notes(String order_post_sale_reparation_notes) {
        this.order_post_sale_reparation_notes = order_post_sale_reparation_notes;
    }

    public String getOrder_post_sale_examine_time() {
        return order_post_sale_examine_time;
    }

    public void setOrder_post_sale_examine_time(String order_post_sale_examine_time) {
        this.order_post_sale_examine_time = order_post_sale_examine_time;
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

    public String getOrder_post_sale_phone() {
        return order_post_sale_phone;
    }

    public void setOrder_post_sale_phone(String order_post_sale_phone) {
        this.order_post_sale_phone = order_post_sale_phone;
    }

    public String getOrder_post_sale_wx_code() {
        return order_post_sale_wx_code;
    }

    public void setOrder_post_sale_wx_code(String order_post_sale_wx_code) {
        this.order_post_sale_wx_code = order_post_sale_wx_code;
    }

    public String getOrder_post_sale_imgs() {
        return order_post_sale_imgs;
    }

    public void setOrder_post_sale_imgs(String order_post_sale_imgs) {
        this.order_post_sale_imgs = order_post_sale_imgs;
    }

    public Integer getOrder_post_sale_reason() {
        return order_post_sale_reason;
    }

    public void setOrder_post_sale_reason(Integer order_post_sale_reason) {
        this.order_post_sale_reason = order_post_sale_reason;
    }

    public String getOrder_post_sale_handle() {
        return order_post_sale_handle;
    }

    public void setOrder_post_sale_handle(String order_post_sale_handle) {
        this.order_post_sale_handle = order_post_sale_handle;
    }

    public String getOrder_post_sale_notes() {
        return order_post_sale_notes;
    }

    public void setOrder_post_sale_notes(String order_post_sale_notes) {
        this.order_post_sale_notes = order_post_sale_notes;
    }

    public String getOrder_post_sale_acceptance_time() {
        return order_post_sale_acceptance_time;
    }

    public void setOrder_post_sale_acceptance_time(String order_post_sale_acceptance_time) {
        this.order_post_sale_acceptance_time = order_post_sale_acceptance_time;
    }

    public String getOrder_post_sale_close_time() {
        return order_post_sale_close_time;
    }

    public void setOrder_post_sale_close_time(String order_post_sale_close_time) {
        this.order_post_sale_close_time = order_post_sale_close_time;
    }

    public String getOrder_post_sale_complete_time() {
        return order_post_sale_complete_time;
    }

    public void setOrder_post_sale_complete_time(String order_post_sale_complete_time) {
        this.order_post_sale_complete_time = order_post_sale_complete_time;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public static class OrderDTO {
        /**
         * order_no : NO1750745641
         * goods_no : NO1750078633
         * goods_title : chen王者荣耀00016
         * goods_image : https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg
         * goods_price : 100
         * payment_price : 105
         * penalty_id : 1
         * is_reparation : 1
         * place_time : 2025-06-24 14:14:02
         * pay_time : 2025-06-24 14:14:02
         * deal_time : 2025-06-25 14:15:01
         */

        private String order_no;
        private String goods_no;
        private String goods_title;
        private String goods_image;
        private String goods_price;
        private String payment_price;
        private Integer penalty_id;
        private Integer is_reparation;
        private String place_time;
        private String pay_time;
        private String deal_time;
        private String game_service_name;
        private String penalty_name;

        public String getPenalty_name() {
            return penalty_name;
        }

        public void setPenalty_name(String penalty_name) {
            this.penalty_name = penalty_name;
        }

        public String getGame_service_name() {
            return game_service_name;
        }

        public void setGame_service_name(String game_service_name) {
            this.game_service_name = game_service_name;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
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

        public String getPayment_price() {
            return payment_price;
        }

        public void setPayment_price(String payment_price) {
            this.payment_price = payment_price;
        }

        public Integer getPenalty_id() {
            return penalty_id;
        }

        public void setPenalty_id(Integer penalty_id) {
            this.penalty_id = penalty_id;
        }

        public Integer getIs_reparation() {
            return is_reparation;
        }

        public void setIs_reparation(Integer is_reparation) {
            this.is_reparation = is_reparation;
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
    }
}
