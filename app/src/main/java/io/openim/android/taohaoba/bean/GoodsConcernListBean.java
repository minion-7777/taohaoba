package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class GoodsConcernListBean {

    /**
     * list : [{"id":57,"category_id":1,"game_id":18,"pattern_id":1,"game_name":"王者荣耀","goods_no":"NO5204038363397044753961977773","title":"王者荣耀售卖超级牛逼的账号先到先得","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1}]
     * total : 1
     */

    private Integer total;
    private List<ListDTO> list;

    public static GoodsConcernListBean objectFromData(String str) {

        return new Gson().fromJson(str, GoodsConcernListBean.class);
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
         * id : 57
         * category_id : 1
         * game_id : 18
         * pattern_id : 1
         * game_name : 王者荣耀
         * goods_no : NO5204038363397044753961977773
         * title : 王者荣耀售卖超级牛逼的账号先到先得
         * image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
         * device_name : 苹果
         * operator_name : QQ
         * game_service_name :
         * retail_price : 1000
         * review_status : 1
         * label : 包赔服务,验证账号
         * is_authentication : 1
         */

        private Integer id;
        private Integer category_id;
        private Integer game_id;
        private Integer pattern_id;
        private String game_name;
        private String goods_no;
        private String title;
        private String image;
        private String device_name;
        private String operator_name;
        private String game_service_name;
        private Integer retail_price;
        private Integer review_status;
        private String label;
        private Integer is_authentication;

        public static ListDTO objectFromData(String str) {

            return new Gson().fromJson(str, ListDTO.class);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public Integer getPattern_id() {
            return pattern_id;
        }

        public void setPattern_id(Integer pattern_id) {
            this.pattern_id = pattern_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getGame_service_name() {
            return game_service_name;
        }

        public void setGame_service_name(String game_service_name) {
            this.game_service_name = game_service_name;
        }

        public Integer getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(Integer retail_price) {
            this.retail_price = retail_price;
        }

        public Integer getReview_status() {
            return review_status;
        }

        public void setReview_status(Integer review_status) {
            this.review_status = review_status;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getIs_authentication() {
            return is_authentication;
        }

        public void setIs_authentication(Integer is_authentication) {
            this.is_authentication = is_authentication;
        }
    }
}
