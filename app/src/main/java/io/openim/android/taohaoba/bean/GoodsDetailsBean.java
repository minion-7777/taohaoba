package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoodsDetailsBean {

    /**
     * id : 57
     * content : [{"is_required":1,"is_show":1,"is_sort":1,"key":"游戏段位","key_sort":13,"sort_type":1,"type":3,"value":"黄金1"},{"is_required":1,"is_show":1,"is_sort":1,"key":"游戏皮肤","key_sort":10,"sort_type":2,"type":3,"value":"倔强1,倔强2,超神1,超神2,白金1"},{"is_required":1,"is_show":1,"is_sort":0,"key":"英雄数量","key_sort":15,"sort_type":1,"type":1,"value":"王者荣耀里面的花钱，不花钱的英雄全部都有，活动的英雄也全都包含在价格优惠，先到先得"}]
     * category_id : 1
     * is_inspect : 1
     * sort : 100
     * is_indulge : 1
     * is_authentication : 1
     * is_account_source : 1
     * sending_id : 1
     * penalty_id : 1
     * account : chen11oopp
     * title : 王者荣耀售卖超级牛逼的账号先到先得
     * image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
     * retail_price : 1000
     * actual_price : 949.5
     * cost_price : null
     * connect : 276806275
     * text : 这个账号超级牛逼的快点来买啊
     * label : 包赔服务,验证账号
     * is_play : 1
     * is_self : false
     * user_id : 59
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
    private String connect;
    private String text;
    private String label;
    private Integer is_play;
    private Boolean is_self;
    private String user_id;
    private Integer is_reparation;
    private Integer reparation_id;
    private List<ContentDTO> content;
    private Integer review_status;
    private String submit_time;
    private String release_time;
    private String category_name;
    private String goods_no;
    private String game_name;
    private boolean is_concern;
    private String device_name;
    private String operator_name;
    private String game_service_name;
    /**
     * user_goods_service : {"id":1151,"user_id":10000005,"goods_id":1167,"goods":null,"game_type_id":3,"game_type":null,"device_name":"安卓","device_id":4,"operator_id":4,"operator_name":"QQ","game_service_id":"","game_service_name":"","game_id":1005,"game":null}
     */

    private UserGoodsServiceDTO user_goods_service;
    private Integer seller_service_ratio;
    private Double seller_service_price;
    private Double seller_amount_conf;
    /**
     * pattern : {"id":1,"name":"平台寄售","status":1,"top":2,"sort":10,"seller_service_ratio":5,"buyer_service_ratio":1,"seller_service_price":10,"buyer_service_price":0,"image":"12123","text":"只收买家服务费","type":1,"type_zh":"","seller_amount_conf":300,"buyer_amount_conf":0,"created_time":"2025-03-22 08:21:03","updated_time":"2025-06-24 15:08:59"}
     */

    private PatternDTO pattern;

    private Integer game_id;

    private Integer pattern_id;
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

    public Integer getPattern_id() {
        return pattern_id;
    }

    public void setPattern_id(Integer pattern_id) {
        this.pattern_id = pattern_id;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

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

    public boolean isIs_concern() {
        return is_concern;
    }

    public void setIs_concern(boolean is_concern) {
        this.is_concern = is_concern;
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public Integer getReview_status() {
        return review_status;
    }

    public void setReview_status(Integer review_status) {
        this.review_status = review_status;
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

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
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

    public Boolean isIs_self() {
        return is_self;
    }

    public void setIs_self(Boolean is_self) {
        this.is_self = is_self;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public List<ContentDTO> getContent() {
        return content;
    }

    public void setContent(List<ContentDTO> content) {
        this.content = content;
    }

    public UserGoodsServiceDTO getUser_goods_service() {
        return user_goods_service;
    }

    public void setUser_goods_service(UserGoodsServiceDTO user_goods_service) {
        this.user_goods_service = user_goods_service;
    }

    public PatternDTO getPattern() {
        return pattern;
    }

    public void setPattern(PatternDTO pattern) {
        this.pattern = pattern;
    }

    public static class ContentDTO {
        /**
         * is_required : 1
         * is_show : 1
         * is_sort : 1
         * key : 游戏段位
         * key_sort : 13
         * sort_type : 1
         * type : 3
         * value : 黄金1
         */

        private Integer is_required;
        private Integer is_show;
        private Integer is_sort;
        private String key;
        private Integer key_sort;
        private Integer sort_type;
        private Integer type;
        private String value;

        public static ContentDTO objectFromData(String str) {

            return new Gson().fromJson(str, ContentDTO.class);
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

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class UserGoodsServiceDTO {
        /**
         * id : 1151
         * user_id : 10000005
         * goods_id : 1167
         * goods : null
         * game_type_id : 3
         * game_type : null
         * device_name : 安卓
         * device_id : 4
         * operator_id : 4
         * operator_name : QQ
         * game_service_id :
         * game_service_name :
         * game_id : 1005
         * game : null
         */

        private Integer id;
        private Integer user_id;
        private Integer goods_id;
        private Object goods;
        private Integer game_type_id;
        private Object game_type;
        private String device_name;
        private Integer device_id;
        private Integer operator_id;
        private String operator_name;
        private String game_service_id;
        private String game_service_name;
        private Integer game_id;
        private Object game;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public Integer getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(Integer goods_id) {
            this.goods_id = goods_id;
        }

        public Object getGoods() {
            return goods;
        }

        public void setGoods(Object goods) {
            this.goods = goods;
        }

        public Integer getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(Integer game_type_id) {
            this.game_type_id = game_type_id;
        }

        public Object getGame_type() {
            return game_type;
        }

        public void setGame_type(Object game_type) {
            this.game_type = game_type;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public Integer getDevice_id() {
            return device_id;
        }

        public void setDevice_id(Integer device_id) {
            this.device_id = device_id;
        }

        public Integer getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(Integer operator_id) {
            this.operator_id = operator_id;
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

        public String getGame_service_name() {
            return game_service_name;
        }

        public void setGame_service_name(String game_service_name) {
            this.game_service_name = game_service_name;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public Object getGame() {
            return game;
        }

        public void setGame(Object game) {
            this.game = game;
        }
    }

    public static class PatternDTO {
        /**
         * id : 1
         * name : 平台寄售
         * status : 1
         * top : 2
         * sort : 10
         * seller_service_ratio : 5
         * buyer_service_ratio : 1
         * seller_service_price : 10
         * buyer_service_price : 0
         * image : 12123
         * text : 只收买家服务费
         * type : 1
         * type_zh :
         * seller_amount_conf : 300
         * buyer_amount_conf : 0
         * created_time : 2025-03-22 08:21:03
         * updated_time : 2025-06-24 15:08:59
         */

        private Integer id;
        private String name;
        private Integer status;
        private Integer top;
        private Integer sort;
        private Integer seller_service_ratio;
        private Integer buyer_service_ratio;
        private Double seller_service_price;
        private Double buyer_service_price;
        private String image;
        private String text;
        private Integer type;
        private String type_zh;
        private Double seller_amount_conf;
        private Double buyer_amount_conf;
        private String created_time;
        private String updated_time;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getType_zh() {
            return type_zh;
        }

        public void setType_zh(String type_zh) {
            this.type_zh = type_zh;
        }

        public Double getSeller_amount_conf() {
            return seller_amount_conf;
        }

        public void setSeller_amount_conf(Double seller_amount_conf) {
            this.seller_amount_conf = seller_amount_conf;
        }

        public Double getBuyer_amount_conf() {
            return buyer_amount_conf;
        }

        public void setBuyer_amount_conf(Double buyer_amount_conf) {
            this.buyer_amount_conf = buyer_amount_conf;
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
    }
}
