package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class CreateChatGroupBean {

    /**
     * id : 197
     * group_name : 王者荣耀售卖超级牛逼的账号先到先得
     * group_avatar : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
     * im_group_id : 25050905054301010001
     * member_count : 0
     * im_owner_id : 1540912350
     * seller_id : 63
     * buyer_id : 58
     * good_id : 58
     * status : 1
     * created_time : 0001-01-01T00:00:00Z
     * updated_time : 0001-01-01T00:00:00Z
     * delete_time : 0001-01-01T00:00:00Z
     * order_id : 47
     * im_seller_id : 7398718417
     * im_buyer_id : 6989224765
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
    private String created_time;
    private String updated_time;
    private String delete_time;
    private Integer order_id;
    private String im_seller_id;
    private String im_buyer_id;
    private Integer is_first;//是否首次创建 0否 1是

    public Integer getIs_first() {
        return is_first;
    }

    public void setIs_first(Integer is_first) {
        this.is_first = is_first;
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

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getIm_seller_id() {
        return im_seller_id;
    }

    public void setIm_seller_id(String im_seller_id) {
        this.im_seller_id = im_seller_id;
    }

    public String getIm_buyer_id() {
        return im_buyer_id;
    }

    public void setIm_buyer_id(String im_buyer_id) {
        this.im_buyer_id = im_buyer_id;
    }
}
