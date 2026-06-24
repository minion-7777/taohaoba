package com.tencent.qcloud.tuikit.tuichat.bean;

public class GetGameAccountValueBean {
    private int id;
    private int game_id;
    private int goods_id;
    private int order_id;
    private int seller_id;
    private int game_account_conf_id;
    private String account_value;
    private String key;
    private String authentication_image;
    private int is_account_source;
    private String account_source_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getGame_account_conf_id() {
        return game_account_conf_id;
    }

    public void setGame_account_conf_id(int game_account_conf_id) {
        this.game_account_conf_id = game_account_conf_id;
    }

    public String getAccount_value() {
        return account_value;
    }

    public void setAccount_value(String account_value) {
        this.account_value = account_value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthentication_image() {
        return authentication_image;
    }

    public void setAuthentication_image(String authentication_image) {
        this.authentication_image = authentication_image;
    }

    public int getIs_account_source() {
        return is_account_source;
    }

    public void setIs_account_source(int is_account_source) {
        this.is_account_source = is_account_source;
    }

    public String getAccount_source_image() {
        return account_source_image;
    }

    public void setAccount_source_image(String account_source_image) {
        this.account_source_image = account_source_image;
    }

    @Override
    public String toString() {
        return "GetGameAccountValueBean{" +
                "id=" + id +
                ", game_id=" + game_id +
                ", goods_id=" + goods_id +
                ", order_id=" + order_id +
                ", seller_id=" + seller_id +
                ", game_account_conf_id=" + game_account_conf_id +
                ", account_value='" + account_value + '\'' +
                ", key='" + key + '\'' +
                ", authentication_image='" + authentication_image + '\'' +
                ", is_account_source=" + is_account_source +
                ", account_source_image='" + account_source_image + '\'' +
                '}';
    }
}
