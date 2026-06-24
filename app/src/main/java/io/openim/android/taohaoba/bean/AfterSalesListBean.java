package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class AfterSalesListBean {

    /**
     * list : [{"id":9,"im_group_id":"","order_post_sale_status":1,"order_post_sale_reason":1,"order_post_sale_notes":"不太行","order_post_sale_acceptance_time":"","order_post_sale_close_time":"","order_post_sale_complete_time":"","created_time":"2025-07-09 10:28:27","order":{"order_no":"NO1750745641","goods_title":"chen王者荣耀00016","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg","goods_price":100,"payment_price":null}},{"id":7,"im_group_id":"","order_post_sale_status":3,"order_post_sale_reason":1,"order_post_sale_notes":"","order_post_sale_acceptance_time":"2025-07-08 17:23:52","order_post_sale_close_time":"2025-07-08 15:16:54","order_post_sale_complete_time":"2025-07-08 18:45:37","created_time":"2025-07-08 10:45:37","order":{"order_no":"NO1750745641","goods_title":"chen王者荣耀00016","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg","goods_price":100,"payment_price":null}},{"id":6,"im_group_id":"","order_post_sale_status":3,"order_post_sale_reason":1,"order_post_sale_notes":"不太行","order_post_sale_acceptance_time":"","order_post_sale_close_time":"","order_post_sale_complete_time":"","created_time":"2025-07-07 06:25:46","order":{"order_no":"NO1750745641","goods_title":"chen王者荣耀00016","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg","goods_price":100,"payment_price":null}}]
     * total : 3
     */

    private Integer total;
    private List<ListDTO> list;

    public static AfterSalesListBean objectFromData(String str) {

        return new Gson().fromJson(str, AfterSalesListBean.class);
    }

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
         * id : 9
         * im_group_id :
         * order_post_sale_status : 1
         * order_post_sale_reason : 1
         * order_post_sale_notes : 不太行
         * order_post_sale_acceptance_time :
         * order_post_sale_close_time :
         * order_post_sale_complete_time :
         * created_time : 2025-07-09 10:28:27
         * order : {"order_no":"NO1750745641","goods_title":"chen王者荣耀00016","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg","goods_price":100,"payment_price":null}
         */

        private Integer id;
        private String im_group_id;
        private String im_group_name;
        private Integer order_post_sale_status;
        private Integer order_post_sale_reason;
        private String order_post_sale_notes;
        private String order_post_sale_acceptance_time;
        private String order_post_sale_close_time;
        private String order_post_sale_complete_time;
        private String created_time;
        private OrderDTO order;

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
             * goods_title : chen王者荣耀00016
             * goods_image : https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567205.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567223.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1750078567222.jpg
             * goods_price : 100
             * payment_price : null
             */

            private Integer order_id;
            private String order_no;
            private String goods_title;
            private String goods_image;
            private String goods_price;
            private String payment_price;
            private String game_service_name;

            public Integer getOrder_id() {
                return order_id;
            }

            public void setOrder_id(Integer order_id) {
                this.order_id = order_id;
            }

            public String getPayment_price() {
                return payment_price;
            }

            public void setPayment_price(String payment_price) {
                this.payment_price = payment_price;
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

        }
    }
}
