package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class PlaceOrderBean {

    /**
     * order_id : 123132132
     * is_first : 1
     * im_group : {"id":123,"group_name":"asad","group_avatar":"asad","im_group_id":"asad","member_count":3,"im_owner_id":"asad","seller_id":111,"buyer_id":111,"good_id":111,"status":1}
     */

    private Integer order_id;
    private Integer is_first;
    private ImGroupDTO im_group;

    public static PlaceOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, PlaceOrderBean.class);
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getIs_first() {
        return is_first;
    }

    public void setIs_first(Integer is_first) {
        this.is_first = is_first;
    }

    public ImGroupDTO getIm_group() {
        return im_group;
    }

    public void setIm_group(ImGroupDTO im_group) {
        this.im_group = im_group;
    }

    public static class ImGroupDTO {
        /**
         * id : 123
         * group_name : asad
         * group_avatar : asad
         * im_group_id : asad
         * member_count : 3
         * im_owner_id : asad
         * seller_id : 111
         * buyer_id : 111
         * good_id : 111
         * status : 1
         */

        private Integer id;
        private String group_name;
        private String group_avatar;
        private String im_group_id;
        private Integer member_count;
        private String im_owner_id;
        private Integer seller_id;
        private Integer buyer_id;
        private Integer good_id;
        private Integer status;

        public static ImGroupDTO objectFromData(String str) {

            return new Gson().fromJson(str, ImGroupDTO.class);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getGroup_avatar() {
            return group_avatar;
        }

        public void setGroup_avatar(String group_avatar) {
            this.group_avatar = group_avatar;
        }

        public String getIm_group_id() {
            return im_group_id;
        }

        public void setIm_group_id(String im_group_id) {
            this.im_group_id = im_group_id;
        }

        public Integer getMember_count() {
            return member_count;
        }

        public void setMember_count(Integer member_count) {
            this.member_count = member_count;
        }

        public String getIm_owner_id() {
            return im_owner_id;
        }

        public void setIm_owner_id(String im_owner_id) {
            this.im_owner_id = im_owner_id;
        }

        public Integer getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(Integer seller_id) {
            this.seller_id = seller_id;
        }

        public Integer getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(Integer buyer_id) {
            this.buyer_id = buyer_id;
        }

        public Integer getGood_id() {
            return good_id;
        }

        public void setGood_id(Integer good_id) {
            this.good_id = good_id;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
