package io.openim.android.taohaoba.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class GoodsSettingBean {


    /**
     * id : 56
     * content : [{"is_required":1,"is_show":1,"is_sort":1,"key":"游戏段位","key_sort":13,"sort_type":1,"type":3,"value":[{"id":717,"value":"黄金1","sort":"H"},{"id":718,"value":"倔强1","sort":"J"},{"id":719,"value":"珀金1","sort":"P"},{"id":720,"value":"白金1","sort":"B"}]},{"is_required":1,"is_show":1,"is_sort":1,"key":"游戏皮肤","key_sort":10,"sort_type":2,"type":3,"value":[{"id":721,"value":"倔强1","sort":"0"},{"id":722,"value":"倔强2","sort":"1"},{"id":723,"value":"超神1","sort":"2"},{"id":724,"value":"超神2","sort":"3"},{"id":725,"value":"白金1","sort":"4"}]},{"is_required":1,"is_show":1,"is_sort":0,"key":"英雄数量","key_sort":15,"sort_type":1,"type":1,"value":[{"id":726,"value":"","sort":""}]}]
     * category_id : 1
     * is_inspect : 1
     * sort : 100
     * is_indulge : 1
     * is_authentication : 1
     * is_account_source : 0
     * sending_id : 1
     * penalty_id : 1
     * account : null
     * title : null
     * image : null
     * retail_price : null
     * actual_price : null
     * cost_price : null
     * connect : null
     * text : null
     * label : null
     * is_play : null
     * is_self : false
     * user_id : null
     * is_reparation : 1
     * reparation_id : 11
     */

    private Integer id;
    private Integer category_id;
    private Integer is_inspect;
    private Integer sort;
    private Integer is_indulge;//防沉迷 1:是 0：否
    private Integer is_indulge_required;//防沉迷是否必填 1：是 0：否
    private Integer is_authentication;//是否二次认证需要填写 1：是  0：否
    private Integer is_authentication_required;//二次实名是否必填 1：是 0：否
    private Integer is_account_source = 1;//账号来源  1: 自己注册 2：本平台购买 3：其他平台购买   （0:是运维人员设置的  1，2，3是用户上架商品的时候选择）
    private Integer is_account_source_required;//账号来源是否必填 1：是 0：否
    private Integer sending_id;
    private Integer penalty_id;
    private String account;
    private String title;
    private String image;
    private String retail_price;
    private String actual_price;
    private String cost_price;
    private String text;
    private String label;
    private String sparkle;
    private Integer is_play;
    private Boolean is_self;
    private Integer user_id;
    private Integer is_reparation;
    private Integer reparation_id;
    private Integer total;
    private List<ContentDTO> content;
    private List<RapidRecoveryBean> userList;
    private String review_status;
    private String game_name;
    private String goods_no;
    private String category_name;
    private String submit_time;
    private String release_time;
    private String connect;
    private Integer seller_service_ratio;
    private Double seller_service_price;
    private Double seller_amount_conf;
    private String device_name;
    private String operator_name;
    private String game_service_id;
    private Integer device_id;
    private Integer operator_id;

    public Integer getIs_indulge_required() {
        return is_indulge_required;
    }

    public void setIs_indulge_required(Integer is_indulge_required) {
        this.is_indulge_required = is_indulge_required;
    }

    public Integer getIs_authentication_required() {
        return is_authentication_required;
    }

    public void setIs_authentication_required(Integer is_authentication_required) {
        this.is_authentication_required = is_authentication_required;
    }

    public Integer getIs_account_source_required() {
        return is_account_source_required;
    }

    public void setIs_account_source_required(Integer is_account_source_required) {
        this.is_account_source_required = is_account_source_required;
    }

    public String getSparkle() {
        return sparkle;
    }

    public void setSparkle(String sparkle) {
        this.sparkle = sparkle;
    }

    public Double getSeller_amount_conf() {
        return seller_amount_conf;
    }

    public void setSeller_amount_conf(Double seller_amount_conf) {
        this.seller_amount_conf = seller_amount_conf;
    }

    public Integer getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(Integer operator_id) {
        this.operator_id = operator_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
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

    public String getGame_service_id() {
        return game_service_id;
    }

    public void setGame_service_id(String game_service_id) {
        this.game_service_id = game_service_id;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
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

    public Integer getIs_inspect() {
        return is_inspect;
    }

    public void setIs_inspect(Integer is_inspect) {
        this.is_inspect = is_inspect;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIs_indulge() {
        return is_indulge;
    }

    public void setIs_indulge(Integer is_indulge) {
        this.is_indulge = is_indulge;
    }

    public Integer getIs_authentication() {
        return is_authentication;
    }

    public void setIs_authentication(Integer is_authentication) {
        this.is_authentication = is_authentication;
    }

    public Integer getIs_account_source() {
        return is_account_source;
    }

    public void setIs_account_source(Integer is_account_source) {
        this.is_account_source = is_account_source;
    }

    public Integer getSending_id() {
        return sending_id;
    }

    public void setSending_id(Integer sending_id) {
        this.sending_id = sending_id;
    }

    public Integer getPenalty_id() {
        return penalty_id;
    }

    public void setPenalty_id(Integer penalty_id) {
        this.penalty_id = penalty_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getActual_price() {
        return actual_price;
    }

    public void setActual_price(String actual_price) {
        this.actual_price = actual_price;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getIs_play() {
        return is_play;
    }

    public void setIs_play(Integer is_play) {
        this.is_play = is_play;
    }

    public Boolean getIs_self() {
        return is_self;
    }

    public void setIs_self(Boolean is_self) {
        this.is_self = is_self;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getIs_reparation() {
        return is_reparation;
    }

    public void setIs_reparation(Integer is_reparation) {
        this.is_reparation = is_reparation;
    }

    public Integer getReparation_id() {
        return reparation_id;
    }

    public void setReparation_id(Integer reparation_id) {
        this.reparation_id = reparation_id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ContentDTO> getContent() {
        return content;
    }

    public void setContent(List<ContentDTO> content) {
        this.content = content;
    }

    public List<RapidRecoveryBean> getUserList() {
        return userList;
    }

    public void setUserList(List<RapidRecoveryBean> userList) {
        this.userList = userList;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getRelease_time() {
        return release_time;
    }

    public void setRelease_time(String release_time) {
        this.release_time = release_time;
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

    public static class ContentDTO implements MultiItemEntity {
        /**
         * is_required : 1
         * is_show : 1
         * is_sort : 1
         * key : 游戏段位
         * key_sort : 13
         * sort_type : 1
         * type : 3
         * value : [{"id":717,"value":"黄金1","sort":"H"},{"id":718,"value":"倔强1","sort":"J"},{"id":719,"value":"珀金1","sort":"P"},{"id":720,"value":"白金1","sort":"B"}]
         */
        public static final int TYPE_ONE = 1;
        public static final int TYPE_TWO = 2;
        public static final int TYPE_THREE = 3;
        private Integer is_required;
        private Integer is_show;
        private Integer is_sort;
        private String key;
        private Integer key_sort;
        private Integer sort_type;
        private Integer type;
        private List<ValueDTO> value;
        private String valueName;

        public String getValueName() {
            return valueName;
        }

        public void setValueName(String valueName) {
            this.valueName = valueName;
        }

        public Integer getIs_required() {
            return is_required;
        }

        public void setIs_required(Integer is_required) {
            this.is_required = is_required;
        }

        public Integer getIs_show() {
            return is_show;
        }

        public void setIs_show(Integer is_show) {
            this.is_show = is_show;
        }

        public Integer getIs_sort() {
            return is_sort;
        }

        public void setIs_sort(Integer is_sort) {
            this.is_sort = is_sort;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getKey_sort() {
            return key_sort;
        }

        public void setKey_sort(Integer key_sort) {
            this.key_sort = key_sort;
        }

        public Integer getSort_type() {
            return sort_type;
        }

        public void setSort_type(Integer sort_type) {
            this.sort_type = sort_type;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public List<ValueDTO> getValue() {
            return value;
        }

        public void setValue(List<ValueDTO> value) {
            this.value = value;
        }

        @Override
        public int getItemType() {
            return type;
        }

        public static class ValueDTO {
            /**
             * id : 717
             * value : 黄金1
             * sort : H
             */

            private Integer id;
            private String value;
            private String sort;
            private boolean isExpanded = false;

            public boolean isExpanded() {
                return isExpanded;
            }

            public void setExpanded(boolean expanded) {
                isExpanded = expanded;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }
        }
    }
}
