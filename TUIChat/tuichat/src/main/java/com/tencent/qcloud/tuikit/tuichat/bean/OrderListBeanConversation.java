package com.tencent.qcloud.tuikit.tuichat.bean;

import java.util.List;

import io.openim.android.ouicore.net.RXRetrofit.BaseResponseBean;

public class OrderListBeanConversation extends BaseResponseBean {


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
        private Integer status;
        private String status_zh;

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

        @Override
        public String toString() {
            return "ListDTO{" +
                    "id=" + id +
                    ", order_no='" + order_no + '\'' +
                    ", goods_id=" + goods_id +
                    ", goods_title='" + goods_title + '\'' +
                    ", goods_image='" + goods_image + '\'' +
                    ", goods_price=" + goods_price +
                    ", game_service_name='" + game_service_name + '\'' +
                    ", status=" + status +
                    ", status_zh='" + status_zh + '\'' +
                    '}';
        }
    }
}
