package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class GoodsListBean {


    /**
     * count : 2
     * goods : [{"id":57,"goods_no":"NO5204038363397044753961977773","title":null,"image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":58,"goods_no":"NO9962485364713938673838747767","title":null,"image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1}]
     */

    private Integer count;
    private List<GoodsDTO> goods;

    public static GoodsListBean objectFromData(String str) {

        return new Gson().fromJson(str, GoodsListBean.class);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<GoodsDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDTO> goods) {
        this.goods = goods;
    }

    public static class GoodsDTO {
        /**
         * id : 57
         * goods_no : NO5204038363397044753961977773
         * title : null
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
        private String goods_no;
        private String title;
        private String image;
        private String device_name;
        private String operator_name;
        private String game_service_name;
        private String retail_price;
        private Integer review_status;
        private String label;
        private Integer is_authentication;
        private String view_count;//查看人数
        private String favorite_count;//收藏人数

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(String favorite_count) {
            this.favorite_count = favorite_count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(String retail_price) {
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
