package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class PatternBean {

    private List<PatternDTO> pattern;

    public static PatternBean objectFromData(String str) {

        return new Gson().fromJson(str, PatternBean.class);
    }

    public List<PatternDTO> getPattern() {
        return pattern;
    }

    public void setPattern(List<PatternDTO> pattern) {
        this.pattern = pattern;
    }

    public static class PatternDTO {
        /**
         * created_time : 2025-03-22 00:21:03
         * updated_time : 2025-04-07 17:35:11
         * id : 1
         * name : 自由交易
         * status : 1
         * top : 2
         * sort : 100
         * seller_service_ratio : 5
         * buyer_service_ratio : 5
         * seller_service_price : 50.5
         * buyer_service_price : 60.5
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private String name;
        private Integer status;
        private Integer top;
        private Integer sort;
        private Integer seller_service_ratio;
        private Integer buyer_service_ratio;
        private Double seller_service_price;
        private Double buyer_service_price;
        private int type;
        private String type_zh;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getType_zh() {
            return type_zh;
        }

        public void setType_zh(String type_zh) {
            this.type_zh = type_zh;
        }

        public static PatternDTO objectFromData(String str) {

            return new Gson().fromJson(str, PatternDTO.class);
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdated_time() {
            return updated_time;
        }

        public void setUpdated_time(String updated_time) {
            this.updated_time = updated_time;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Integer getSeller_service_ratio() {
            return seller_service_ratio;
        }

        public void setSeller_service_ratio(Integer seller_service_ratio) {
            this.seller_service_ratio = seller_service_ratio;
        }

        public Integer getBuyer_service_ratio() {
            return buyer_service_ratio;
        }

        public void setBuyer_service_ratio(Integer buyer_service_ratio) {
            this.buyer_service_ratio = buyer_service_ratio;
        }

        public Double getSeller_service_price() {
            return seller_service_price;
        }

        public void setSeller_service_price(Double seller_service_price) {
            this.seller_service_price = seller_service_price;
        }

        public Double getBuyer_service_price() {
            return buyer_service_price;
        }

        public void setBuyer_service_price(Double buyer_service_price) {
            this.buyer_service_price = buyer_service_price;
        }
    }
}
