package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class SubmintAccountInformationBean {

    /**
     * created_time : 2025-05-06 15:41:10
     * updated_time : 2025-05-06 15:41:10
     * id : 17
     * game_id : 18
     * key : 密码
     * value : 这里传递密码信息
     * is_required : 1
     * content : null
     */

    private String created_time;
    private String updated_time;
    private Integer id;
    private Integer game_id;
    private String key;
    private String value;
    private Integer is_required;
    private Object content;
    private String str;

    public static SubmintAccountInformationBean objectFromData(String str) {

        return new Gson().fromJson(str, SubmintAccountInformationBean.class);
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
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

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getIs_required() {
        return is_required;
    }

    public void setIs_required(Integer is_required) {
        this.is_required = is_required;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
