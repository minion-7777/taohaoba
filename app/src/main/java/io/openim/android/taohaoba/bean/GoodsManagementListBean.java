package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class GoodsManagementListBean {


    private List<GoodsDTO> goods;
    private Integer total = 0;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<GoodsDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDTO> goods) {
        this.goods = goods;
    }

    public static class GoodsDTO {
        /**
         * id : 68
         * goods_no : NO0737635474980535652137322723
         * title : 这是测试数据
         * image : https://taohao8.oss-cn-beijing.aliyuncs.com/images/1746610223935.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1746610223934.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1746610223917.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1746610223935.jpg
         * device_name : 苹果
         * operator_name : QQ
         * game_service_name :
         * retail_price : null
         * review_status : 0
         * label : null
         * is_authentication : null
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
        private String game_name;
        private Integer category_id;
        private Integer game_id;
        private Integer pattern_id;
        private Integer pattern_type;
        private Integer is_authentication;
        private String remark;
        private Integer seller_service_ratio;
        private Double seller_service_price;
        private Double seller_amount_conf;

        public Integer getPattern_type() {
            return pattern_type;
        }

        public void setPattern_type(Integer pattern_type) {
            this.pattern_type = pattern_type;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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
