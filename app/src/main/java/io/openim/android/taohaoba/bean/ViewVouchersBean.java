package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class ViewVouchersBean {

    /**
     * place_time : 2025-05-09 13:05:44
     * pay_time : null
     * deal_time : null
     * take_time : null
     * cancel_time : null
     * refund_time : null
     * system_refund_time : null
     * send_time : null
     * verify_time : null
     * created_time : 2025-05-09 13:05:44
     * updated_time : 2025-05-09 13:05:44
     * id : 47
     * order_no : NO1746767143179013458483179454
     * goods_id : 58
     * goods : {"created_time":"2025-04-27 15:23:17","updated_time":"2025-04-27 15:46:45","release_time":"2025-04-27 17:57:15","submit_time":"2025-04-27 15:23:17","id":58,"category_id":1,"Category":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0},"is_inspect":1,"sending_id":1,"Sending":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"status":0,"top":0},"sort":100,"is_indulge":1,"is_authentication":1,"is_account_source":1,"is_reparation":1,"reparation_id":11,"Reparation":{"created_time":"0001-01-01 00:00:00","handle_time":"0001-01-01 00:00:00","service_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"title":"","status":0,"sort":0,"ratio":0,"price":0},"game_id":18,"Game":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"game_no":"","name":"","image":"","game_type_id":0,"GameType":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0,"type":0,"type_zh":""},"device_id":"","operator_id":"","top":0,"status":0,"sort":0,"pinyin":""},"pattern_id":1,"Pattern":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","status":0,"top":0,"sort":0,"seller_service_ratio":0,"buyer_service_ratio":0,"seller_service_price":0,"buyer_service_price":0,"image":"","text":"","type":0,"type_zh":""},"send_automatic_time":100,"timeout":300,"penalty_id":1,"Penalty":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"status":0,"seller_penalty_ratio":0,"buyer_penalty_ratio":0,"seller_penalty_price":0,"buyer_penalty_price":0,"platform_income_ratio":0,"platform_income_price":0},"goods_no":"NO9962485364713938673838747767","stock":100,"unit":"个","user_id":63,"user":{"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":""},"account":"chen11oopp","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","title":"王者荣耀售卖超级牛逼的账号先到先得","status":1,"user_goods_service":{"id":0,"user_id":0,"goods_id":0,"goods":null,"game_type_id":0,"game_type":null,"device_name":"","operator_name":"","game_service_id":"","game_service_name":"","game_id":0,"game":null},"review_status":1,"retail_price":1000,"actual_price":949.5,"connect":"276806275","text":"这个账号超级牛逼的快点来买啊","label":"包赔服务,验证账号","is_play":0,"sparkle":"","authentication_image":"","account_source_image":""}
     * game_id : 18
     * game : {"created_time":"2025-04-12 16:31:17","updated_time":"2025-05-07 19:27:23","id":18,"game_no":"NO5623690932386853549810747011","name":"王者荣耀","image":"http://taohao8.oss-cn-beijing.aliyuncs.com/GameList//1746617237965-%E6%B6%82%E5%B1%B1%E8%93%89%E8%93%89.jpg","game_type_id":3,"GameType":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0,"type":0,"type_zh":""},"device_id":"2,4,5,7","operator_id":"4,6,7,8","top":1,"status":1,"sort":96,"pinyin":"W"}
     * buy_user_id : 58
     * user : {"created_time":"2025-04-22 19:14:56","updated_time":"2025-04-25 15:00:15","disable_time":null,"seal_time":null,"id":58,"username":"18677777777","password":"25f9e794323b453885f5181f1b624d0b","im_id":"6989224765","avatar":"","nickname":"","user_type":1,"status":1,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"124824827@qq.com","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null}
     * status : 8
     * user_goods_service : null
     * status_zh :
     * sell_user_type : 1
     * goods_price : 1000
     * payment_price : 1160.5
     * reparation_price : 100
     * pattern_price : 60.5
     * payment_type : 0
     * sell_user_id : 63
     * sell_user : null
     * buy_shares : 1
     * is_reparation : 1
     * reparation_id : 11
     * pattern_id : 1
     * pattern : null
     * customer_service_id : 1540912350
     * im_group_id : 197
     * im_group : null
     * refund_content :
     * buy_user_contact :
     * reparation_num : 1
     */

    private String place_time;
    private Object pay_time;
    private String deal_time;
    private Object take_time;
    private Object cancel_time;
    private Object refund_time;
    private Object system_refund_time;
    private Object send_time;
    private Object verify_time;
    private String created_time;
    private String updated_time;
    private Integer id;
    private String order_no;
    private Integer goods_id;
    private GoodsDTO goods;
    private Integer game_id;
    private GameDTOX game;
    private Integer buy_user_id;
    private UserDTOX user;
    private Integer status;
    private Object user_goods_service;
    private String status_zh;
    private Integer sell_user_type;
    private String goods_price;
    private Double payment_price;
    private Integer reparation_price;
    private Double pattern_price;
    private Integer payment_type;
    private Integer sell_user_id;
    private Object sell_user;
    private Integer buy_shares;
    private Integer is_reparation;
    private Integer reparation_id;
    private Integer pattern_id;
    private Object pattern;
    private String customer_service_id;
    private Integer im_group_id;
    private Object im_group;
    private String refund_content;
    private String buy_user_contact;
    private Integer reparation_num;

    public static ViewVouchersBean objectFromData(String str) {

        return new Gson().fromJson(str, ViewVouchersBean.class);
    }

    public String getPlace_time() {
        return place_time;
    }

    public void setPlace_time(String place_time) {
        this.place_time = place_time;
    }

    public Object getPay_time() {
        return pay_time;
    }

    public void setPay_time(Object pay_time) {
        this.pay_time = pay_time;
    }

    public String getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(String deal_time) {
        this.deal_time = deal_time;
    }

    public Object getTake_time() {
        return take_time;
    }

    public void setTake_time(Object take_time) {
        this.take_time = take_time;
    }

    public Object getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(Object cancel_time) {
        this.cancel_time = cancel_time;
    }

    public Object getRefund_time() {
        return refund_time;
    }

    public void setRefund_time(Object refund_time) {
        this.refund_time = refund_time;
    }

    public Object getSystem_refund_time() {
        return system_refund_time;
    }

    public void setSystem_refund_time(Object system_refund_time) {
        this.system_refund_time = system_refund_time;
    }

    public Object getSend_time() {
        return send_time;
    }

    public void setSend_time(Object send_time) {
        this.send_time = send_time;
    }

    public Object getVerify_time() {
        return verify_time;
    }

    public void setVerify_time(Object verify_time) {
        this.verify_time = verify_time;
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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public GoodsDTO getGoods() {
        return goods;
    }

    public void setGoods(GoodsDTO goods) {
        this.goods = goods;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public GameDTOX getGame() {
        return game;
    }

    public void setGame(GameDTOX game) {
        this.game = game;
    }

    public Integer getBuy_user_id() {
        return buy_user_id;
    }

    public void setBuy_user_id(Integer buy_user_id) {
        this.buy_user_id = buy_user_id;
    }

    public UserDTOX getUser() {
        return user;
    }

    public void setUser(UserDTOX user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getUser_goods_service() {
        return user_goods_service;
    }

    public void setUser_goods_service(Object user_goods_service) {
        this.user_goods_service = user_goods_service;
    }

    public String getStatus_zh() {
        return status_zh;
    }

    public void setStatus_zh(String status_zh) {
        this.status_zh = status_zh;
    }

    public Integer getSell_user_type() {
        return sell_user_type;
    }

    public void setSell_user_type(Integer sell_user_type) {
        this.sell_user_type = sell_user_type;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public Double getPayment_price() {
        return payment_price;
    }

    public void setPayment_price(Double payment_price) {
        this.payment_price = payment_price;
    }

    public Integer getReparation_price() {
        return reparation_price;
    }

    public void setReparation_price(Integer reparation_price) {
        this.reparation_price = reparation_price;
    }

    public Double getPattern_price() {
        return pattern_price;
    }

    public void setPattern_price(Double pattern_price) {
        this.pattern_price = pattern_price;
    }

    public Integer getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }

    public Integer getSell_user_id() {
        return sell_user_id;
    }

    public void setSell_user_id(Integer sell_user_id) {
        this.sell_user_id = sell_user_id;
    }

    public Object getSell_user() {
        return sell_user;
    }

    public void setSell_user(Object sell_user) {
        this.sell_user = sell_user;
    }

    public Integer getBuy_shares() {
        return buy_shares;
    }

    public void setBuy_shares(Integer buy_shares) {
        this.buy_shares = buy_shares;
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

    public Integer getPattern_id() {
        return pattern_id;
    }

    public void setPattern_id(Integer pattern_id) {
        this.pattern_id = pattern_id;
    }

    public Object getPattern() {
        return pattern;
    }

    public void setPattern(Object pattern) {
        this.pattern = pattern;
    }

    public String getCustomer_service_id() {
        return customer_service_id;
    }

    public void setCustomer_service_id(String customer_service_id) {
        this.customer_service_id = customer_service_id;
    }

    public Integer getIm_group_id() {
        return im_group_id;
    }

    public void setIm_group_id(Integer im_group_id) {
        this.im_group_id = im_group_id;
    }

    public Object getIm_group() {
        return im_group;
    }

    public void setIm_group(Object im_group) {
        this.im_group = im_group;
    }

    public String getRefund_content() {
        return refund_content;
    }

    public void setRefund_content(String refund_content) {
        this.refund_content = refund_content;
    }

    public String getBuy_user_contact() {
        return buy_user_contact;
    }

    public void setBuy_user_contact(String buy_user_contact) {
        this.buy_user_contact = buy_user_contact;
    }

    public Integer getReparation_num() {
        return reparation_num;
    }

    public void setReparation_num(Integer reparation_num) {
        this.reparation_num = reparation_num;
    }

    public static class GoodsDTO {
        /**
         * created_time : 2025-04-27 15:23:17
         * updated_time : 2025-04-27 15:46:45
         * release_time : 2025-04-27 17:57:15
         * submit_time : 2025-04-27 15:23:17
         * id : 58
         * category_id : 1
         * Category : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0}
         * is_inspect : 1
         * sending_id : 1
         * Sending : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"status":0,"top":0}
         * sort : 100
         * is_indulge : 1
         * is_authentication : 1
         * is_account_source : 1
         * is_reparation : 1
         * reparation_id : 11
         * Reparation : {"created_time":"0001-01-01 00:00:00","handle_time":"0001-01-01 00:00:00","service_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"title":"","status":0,"sort":0,"ratio":0,"price":0}
         * game_id : 18
         * Game : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"game_no":"","name":"","image":"","game_type_id":0,"GameType":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0,"type":0,"type_zh":""},"device_id":"","operator_id":"","top":0,"status":0,"sort":0,"pinyin":""}
         * pattern_id : 1
         * Pattern : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","status":0,"top":0,"sort":0,"seller_service_ratio":0,"buyer_service_ratio":0,"seller_service_price":0,"buyer_service_price":0,"image":"","text":"","type":0,"type_zh":""}
         * send_automatic_time : 100
         * timeout : 300
         * penalty_id : 1
         * Penalty : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"status":0,"seller_penalty_ratio":0,"buyer_penalty_ratio":0,"seller_penalty_price":0,"buyer_penalty_price":0,"platform_income_ratio":0,"platform_income_price":0}
         * goods_no : NO9962485364713938673838747767
         * stock : 100
         * unit : 个
         * user_id : 63
         * user : {"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":""}
         * account : chen11oopp
         * image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
         * title : 王者荣耀售卖超级牛逼的账号先到先得
         * status : 1
         * user_goods_service : {"id":0,"user_id":0,"goods_id":0,"goods":null,"game_type_id":0,"game_type":null,"device_name":"","operator_name":"","game_service_id":"","game_service_name":"","game_id":0,"game":null}
         * review_status : 1
         * retail_price : 1000
         * actual_price : 949.5
         * connect : 276806275
         * text : 这个账号超级牛逼的快点来买啊
         * label : 包赔服务,验证账号
         * is_play : 0
         * sparkle :
         * authentication_image :
         * account_source_image :
         */

        private String created_time;
        private String updated_time;
        private String release_time;
        private String submit_time;
        private Integer id;
        private Integer category_id;
        private CategoryDTO Category;
        private Integer is_inspect;
        private Integer sending_id;
        private SendingDTO Sending;
        private Integer sort;
        private Integer is_indulge;
        private Integer is_authentication;
        private Integer is_account_source;
        private Integer is_reparation;
        private Integer reparation_id;
        private ReparationDTO Reparation;
        private Integer game_id;
        private GameDTO Game;
        private Integer pattern_id;
        private PatternDTO Pattern;
        private Integer send_automatic_time;
        private Integer timeout;
        private Integer penalty_id;
        private PenaltyDTO Penalty;
        private String goods_no;
        private Integer stock;
        private String unit;
        private Integer user_id;
        private UserDTO user;
        private String account;
        private String image;
        private String title;
        private Integer status;
        private UserGoodsServiceDTO user_goods_service;
        private Integer review_status;
        private Integer retail_price;
        private Double actual_price;
        private String connect;
        private String text;
        private String label;
        private Integer is_play;
        private String sparkle;
        private String authentication_image;
        private String account_source_image;

        public static GoodsDTO objectFromData(String str) {

            return new Gson().fromJson(str, GoodsDTO.class);
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

        public String getRelease_time() {
            return release_time;
        }

        public void setRelease_time(String release_time) {
            this.release_time = release_time;
        }

        public String getSubmit_time() {
            return submit_time;
        }

        public void setSubmit_time(String submit_time) {
            this.submit_time = submit_time;
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

        public CategoryDTO getCategory() {
            return Category;
        }

        public void setCategory(CategoryDTO Category) {
            this.Category = Category;
        }

        public Integer getIs_inspect() {
            return is_inspect;
        }

        public void setIs_inspect(Integer is_inspect) {
            this.is_inspect = is_inspect;
        }

        public Integer getSending_id() {
            return sending_id;
        }

        public void setSending_id(Integer sending_id) {
            this.sending_id = sending_id;
        }

        public SendingDTO getSending() {
            return Sending;
        }

        public void setSending(SendingDTO Sending) {
            this.Sending = Sending;
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

        public ReparationDTO getReparation() {
            return Reparation;
        }

        public void setReparation(ReparationDTO Reparation) {
            this.Reparation = Reparation;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public GameDTO getGame() {
            return Game;
        }

        public void setGame(GameDTO Game) {
            this.Game = Game;
        }

        public Integer getPattern_id() {
            return pattern_id;
        }

        public void setPattern_id(Integer pattern_id) {
            this.pattern_id = pattern_id;
        }

        public PatternDTO getPattern() {
            return Pattern;
        }

        public void setPattern(PatternDTO Pattern) {
            this.Pattern = Pattern;
        }

        public Integer getSend_automatic_time() {
            return send_automatic_time;
        }

        public void setSend_automatic_time(Integer send_automatic_time) {
            this.send_automatic_time = send_automatic_time;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getPenalty_id() {
            return penalty_id;
        }

        public void setPenalty_id(Integer penalty_id) {
            this.penalty_id = penalty_id;
        }

        public PenaltyDTO getPenalty() {
            return Penalty;
        }

        public void setPenalty(PenaltyDTO Penalty) {
            this.Penalty = Penalty;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public UserGoodsServiceDTO getUser_goods_service() {
            return user_goods_service;
        }

        public void setUser_goods_service(UserGoodsServiceDTO user_goods_service) {
            this.user_goods_service = user_goods_service;
        }

        public Integer getReview_status() {
            return review_status;
        }

        public void setReview_status(Integer review_status) {
            this.review_status = review_status;
        }

        public Integer getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(Integer retail_price) {
            this.retail_price = retail_price;
        }

        public Double getActual_price() {
            return actual_price;
        }

        public void setActual_price(Double actual_price) {
            this.actual_price = actual_price;
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

        public String getSparkle() {
            return sparkle;
        }

        public void setSparkle(String sparkle) {
            this.sparkle = sparkle;
        }

        public String getAuthentication_image() {
            return authentication_image;
        }

        public void setAuthentication_image(String authentication_image) {
            this.authentication_image = authentication_image;
        }

        public String getAccount_source_image() {
            return account_source_image;
        }

        public void setAccount_source_image(String account_source_image) {
            this.account_source_image = account_source_image;
        }

        public static class CategoryDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * name :
             * sort : 0
             * top : 0
             * status : 0
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String name;
            private Integer sort;
            private Integer top;
            private Integer status;

            public static CategoryDTO objectFromData(String str) {

                return new Gson().fromJson(str, CategoryDTO.class);
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

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public Integer getTop() {
                return top;
            }

            public void setTop(Integer top) {
                this.top = top;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }
        }

        public static class SendingDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * name :
             * sort : 0
             * status : 0
             * top : 0
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String name;
            private Integer sort;
            private Integer status;
            private Integer top;

            public static SendingDTO objectFromData(String str) {

                return new Gson().fromJson(str, SendingDTO.class);
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

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
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
        }

        public static class ReparationDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * handle_time : 0001-01-01 00:00:00
             * service_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * title :
             * status : 0
             * sort : 0
             * ratio : 0
             * price : 0
             */

            private String created_time;
            private String handle_time;
            private String service_time;
            private String updated_time;
            private Integer id;
            private String title;
            private Integer status;
            private Integer sort;
            private Integer ratio;
            private Integer price;

            public static ReparationDTO objectFromData(String str) {

                return new Gson().fromJson(str, ReparationDTO.class);
            }

            public String getCreated_time() {
                return created_time;
            }

            public void setCreated_time(String created_time) {
                this.created_time = created_time;
            }

            public String getHandle_time() {
                return handle_time;
            }

            public void setHandle_time(String handle_time) {
                this.handle_time = handle_time;
            }

            public String getService_time() {
                return service_time;
            }

            public void setService_time(String service_time) {
                this.service_time = service_time;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public Integer getRatio() {
                return ratio;
            }

            public void setRatio(Integer ratio) {
                this.ratio = ratio;
            }

            public Integer getPrice() {
                return price;
            }

            public void setPrice(Integer price) {
                this.price = price;
            }
        }

        public static class GameDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * game_no :
             * name :
             * image :
             * game_type_id : 0
             * GameType : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0,"type":0,"type_zh":""}
             * device_id :
             * operator_id :
             * top : 0
             * status : 0
             * sort : 0
             * pinyin :
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String game_no;
            private String name;
            private String image;
            private Integer game_type_id;
            private GameTypeDTO GameType;
            private String device_id;
            private String operator_id;
            private Integer top;
            private Integer status;
            private Integer sort;
            private String pinyin;

            public static GameDTO objectFromData(String str) {

                return new Gson().fromJson(str, GameDTO.class);
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

            public String getGame_no() {
                return game_no;
            }

            public void setGame_no(String game_no) {
                this.game_no = game_no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public Integer getGame_type_id() {
                return game_type_id;
            }

            public void setGame_type_id(Integer game_type_id) {
                this.game_type_id = game_type_id;
            }

            public GameTypeDTO getGameType() {
                return GameType;
            }

            public void setGameType(GameTypeDTO GameType) {
                this.GameType = GameType;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getOperator_id() {
                return operator_id;
            }

            public void setOperator_id(String operator_id) {
                this.operator_id = operator_id;
            }

            public Integer getTop() {
                return top;
            }

            public void setTop(Integer top) {
                this.top = top;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getPinyin() {
                return pinyin;
            }

            public void setPinyin(String pinyin) {
                this.pinyin = pinyin;
            }

            public static class GameTypeDTO {
                /**
                 * created_time : 0001-01-01 00:00:00
                 * updated_time : 0001-01-01 00:00:00
                 * id : 0
                 * name :
                 * sort : 0
                 * top : 0
                 * status : 0
                 * type : 0
                 * type_zh :
                 */

                private String created_time;
                private String updated_time;
                private Integer id;
                private String name;
                private Integer sort;
                private Integer top;
                private Integer status;
                private Integer type;
                private String type_zh;

                public static GameTypeDTO objectFromData(String str) {

                    return new Gson().fromJson(str, GameTypeDTO.class);
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

                public Integer getSort() {
                    return sort;
                }

                public void setSort(Integer sort) {
                    this.sort = sort;
                }

                public Integer getTop() {
                    return top;
                }

                public void setTop(Integer top) {
                    this.top = top;
                }

                public Integer getStatus() {
                    return status;
                }

                public void setStatus(Integer status) {
                    this.status = status;
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
            }
        }

        public static class PatternDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * name :
             * status : 0
             * top : 0
             * sort : 0
             * seller_service_ratio : 0
             * buyer_service_ratio : 0
             * seller_service_price : 0
             * buyer_service_price : 0
             * image :
             * text :
             * type : 0
             * type_zh :
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
            private Integer seller_service_price;
            private Integer buyer_service_price;
            private String image;
            private String text;
            private Integer type;
            private String type_zh;

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

            public Integer getSeller_service_price() {
                return seller_service_price;
            }

            public void setSeller_service_price(Integer seller_service_price) {
                this.seller_service_price = seller_service_price;
            }

            public Integer getBuyer_service_price() {
                return buyer_service_price;
            }

            public void setBuyer_service_price(Integer buyer_service_price) {
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
        }

        public static class PenaltyDTO {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * name :
             * sort : 0
             * status : 0
             * seller_penalty_ratio : 0
             * buyer_penalty_ratio : 0
             * seller_penalty_price : 0
             * buyer_penalty_price : 0
             * platform_income_ratio : 0
             * platform_income_price : 0
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String name;
            private Integer sort;
            private Integer status;
            private Integer seller_penalty_ratio;
            private Integer buyer_penalty_ratio;
            private Integer seller_penalty_price;
            private Integer buyer_penalty_price;
            private Integer platform_income_ratio;
            private Integer platform_income_price;

            public static PenaltyDTO objectFromData(String str) {

                return new Gson().fromJson(str, PenaltyDTO.class);
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

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getSeller_penalty_ratio() {
                return seller_penalty_ratio;
            }

            public void setSeller_penalty_ratio(Integer seller_penalty_ratio) {
                this.seller_penalty_ratio = seller_penalty_ratio;
            }

            public Integer getBuyer_penalty_ratio() {
                return buyer_penalty_ratio;
            }

            public void setBuyer_penalty_ratio(Integer buyer_penalty_ratio) {
                this.buyer_penalty_ratio = buyer_penalty_ratio;
            }

            public Integer getSeller_penalty_price() {
                return seller_penalty_price;
            }

            public void setSeller_penalty_price(Integer seller_penalty_price) {
                this.seller_penalty_price = seller_penalty_price;
            }

            public Integer getBuyer_penalty_price() {
                return buyer_penalty_price;
            }

            public void setBuyer_penalty_price(Integer buyer_penalty_price) {
                this.buyer_penalty_price = buyer_penalty_price;
            }

            public Integer getPlatform_income_ratio() {
                return platform_income_ratio;
            }

            public void setPlatform_income_ratio(Integer platform_income_ratio) {
                this.platform_income_ratio = platform_income_ratio;
            }

            public Integer getPlatform_income_price() {
                return platform_income_price;
            }

            public void setPlatform_income_price(Integer platform_income_price) {
                this.platform_income_price = platform_income_price;
            }
        }

        public static class UserDTO {
            /**
             * created_time : null
             * updated_time : null
             * disable_time : null
             * seal_time : null
             * id : 0
             * username :
             * password :
             * im_id :
             * avatar :
             * nickname :
             * user_type : 0
             * status : 0
             * login_type : 0
             * imit :
             * qq :
             * wei_chat :
             * alipay :
             */

            private Object created_time;
            private Object updated_time;
            private Object disable_time;
            private Object seal_time;
            private Integer id;
            private String username;
            private String password;
            private String im_id;
            private String avatar;
            private String nickname;
            private Integer user_type;
            private Integer status;
            private Integer login_type;
            private String imit;
            private String qq;
            private String wei_chat;
            private String alipay;

            public static UserDTO objectFromData(String str) {

                return new Gson().fromJson(str, UserDTO.class);
            }

            public Object getCreated_time() {
                return created_time;
            }

            public void setCreated_time(Object created_time) {
                this.created_time = created_time;
            }

            public Object getUpdated_time() {
                return updated_time;
            }

            public void setUpdated_time(Object updated_time) {
                this.updated_time = updated_time;
            }

            public Object getDisable_time() {
                return disable_time;
            }

            public void setDisable_time(Object disable_time) {
                this.disable_time = disable_time;
            }

            public Object getSeal_time() {
                return seal_time;
            }

            public void setSeal_time(Object seal_time) {
                this.seal_time = seal_time;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getIm_id() {
                return im_id;
            }

            public void setIm_id(String im_id) {
                this.im_id = im_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Integer getUser_type() {
                return user_type;
            }

            public void setUser_type(Integer user_type) {
                this.user_type = user_type;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Integer getLogin_type() {
                return login_type;
            }

            public void setLogin_type(Integer login_type) {
                this.login_type = login_type;
            }

            public String getImit() {
                return imit;
            }

            public void setImit(String imit) {
                this.imit = imit;
            }

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getWei_chat() {
                return wei_chat;
            }

            public void setWei_chat(String wei_chat) {
                this.wei_chat = wei_chat;
            }

            public String getAlipay() {
                return alipay;
            }

            public void setAlipay(String alipay) {
                this.alipay = alipay;
            }
        }

        public static class UserGoodsServiceDTO {
            /**
             * id : 0
             * user_id : 0
             * goods_id : 0
             * goods : null
             * game_type_id : 0
             * game_type : null
             * device_name :
             * operator_name :
             * game_service_id :
             * game_service_name :
             * game_id : 0
             * game : null
             */

            private Integer id;
            private Integer user_id;
            private Integer goods_id;
            private Object goods;
            private Integer game_type_id;
            private Object game_type;
            private String device_name;
            private String operator_name;
            private String game_service_id;
            private String game_service_name;
            private Integer game_id;
            private Object game;

            public static UserGoodsServiceDTO objectFromData(String str) {

                return new Gson().fromJson(str, UserGoodsServiceDTO.class);
            }

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
    }

    public static class GameDTOX {
        /**
         * created_time : 2025-04-12 16:31:17
         * updated_time : 2025-05-07 19:27:23
         * id : 18
         * game_no : NO5623690932386853549810747011
         * name : 王者荣耀
         * image : http://taohao8.oss-cn-beijing.aliyuncs.com/GameList//1746617237965-%E6%B6%82%E5%B1%B1%E8%93%89%E8%93%89.jpg
         * game_type_id : 3
         * GameType : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0,"type":0,"type_zh":""}
         * device_id : 2,4,5,7
         * operator_id : 4,6,7,8
         * top : 1
         * status : 1
         * sort : 96
         * pinyin : W
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private String game_no;
        private String name;
        private String image;
        private Integer game_type_id;
        private GameTypeDTOX GameType;
        private String device_id;
        private String operator_id;
        private Integer top;
        private Integer status;
        private Integer sort;
        private String pinyin;

        public static GameDTOX objectFromData(String str) {

            return new Gson().fromJson(str, GameDTOX.class);
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

        public String getGame_no() {
            return game_no;
        }

        public void setGame_no(String game_no) {
            this.game_no = game_no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(Integer game_type_id) {
            this.game_type_id = game_type_id;
        }

        public GameTypeDTOX getGameType() {
            return GameType;
        }

        public void setGameType(GameTypeDTOX GameType) {
            this.GameType = GameType;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(String operator_id) {
            this.operator_id = operator_id;
        }

        public Integer getTop() {
            return top;
        }

        public void setTop(Integer top) {
            this.top = top;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public static class GameTypeDTOX {
            /**
             * created_time : 0001-01-01 00:00:00
             * updated_time : 0001-01-01 00:00:00
             * id : 0
             * name :
             * sort : 0
             * top : 0
             * status : 0
             * type : 0
             * type_zh :
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String name;
            private Integer sort;
            private Integer top;
            private Integer status;
            private Integer type;
            private String type_zh;

            public static GameTypeDTOX objectFromData(String str) {

                return new Gson().fromJson(str, GameTypeDTOX.class);
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

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public Integer getTop() {
                return top;
            }

            public void setTop(Integer top) {
                this.top = top;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
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
        }
    }

    public static class UserDTOX {
        /**
         * created_time : 2025-04-22 19:14:56
         * updated_time : 2025-04-25 15:00:15
         * disable_time : null
         * seal_time : null
         * id : 58
         * username : 18677777777
         * password : 25f9e794323b453885f5181f1b624d0b
         * im_id : 6989224765
         * avatar :
         * nickname :
         * user_type : 1
         * status : 1
         * login_type : 0
         * imit :
         * qq :
         * wei_chat :
         * alipay : 124824827@qq.com
         * wallet : {"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""}
         * user_authentication : null
         */

        private String created_time;
        private String updated_time;
        private Object disable_time;
        private Object seal_time;
        private Integer id;
        private String username;
        private String password;
        private String im_id;
        private String avatar;
        private String nickname;
        private Integer user_type;
        private Integer status;
        private Integer login_type;
        private String imit;
        private String qq;
        private String wei_chat;
        private String alipay;
        private WalletDTO wallet;
        private Object user_authentication;

        public static UserDTOX objectFromData(String str) {

            return new Gson().fromJson(str, UserDTOX.class);
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

        public Object getDisable_time() {
            return disable_time;
        }

        public void setDisable_time(Object disable_time) {
            this.disable_time = disable_time;
        }

        public Object getSeal_time() {
            return seal_time;
        }

        public void setSeal_time(Object seal_time) {
            this.seal_time = seal_time;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getIm_id() {
            return im_id;
        }

        public void setIm_id(String im_id) {
            this.im_id = im_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Integer getUser_type() {
            return user_type;
        }

        public void setUser_type(Integer user_type) {
            this.user_type = user_type;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getLogin_type() {
            return login_type;
        }

        public void setLogin_type(Integer login_type) {
            this.login_type = login_type;
        }

        public String getImit() {
            return imit;
        }

        public void setImit(String imit) {
            this.imit = imit;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWei_chat() {
            return wei_chat;
        }

        public void setWei_chat(String wei_chat) {
            this.wei_chat = wei_chat;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public WalletDTO getWallet() {
            return wallet;
        }

        public void setWallet(WalletDTO wallet) {
            this.wallet = wallet;
        }

        public Object getUser_authentication() {
            return user_authentication;
        }

        public void setUser_authentication(Object user_authentication) {
            this.user_authentication = user_authentication;
        }

        public static class WalletDTO {
            /**
             * created_time : null
             * updated_time : null
             * id : 0
             * user_id : 0
             * balance_available : 0
             * balance_locked : 0
             * bond : 0
             * coin :
             */

            private Object created_time;
            private Object updated_time;
            private Integer id;
            private Integer user_id;
            private Integer balance_available;
            private Integer balance_locked;
            private Integer bond;
            private String coin;

            public static WalletDTO objectFromData(String str) {

                return new Gson().fromJson(str, WalletDTO.class);
            }

            public Object getCreated_time() {
                return created_time;
            }

            public void setCreated_time(Object created_time) {
                this.created_time = created_time;
            }

            public Object getUpdated_time() {
                return updated_time;
            }

            public void setUpdated_time(Object updated_time) {
                this.updated_time = updated_time;
            }

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

            public Integer getBalance_available() {
                return balance_available;
            }

            public void setBalance_available(Integer balance_available) {
                this.balance_available = balance_available;
            }

            public Integer getBalance_locked() {
                return balance_locked;
            }

            public void setBalance_locked(Integer balance_locked) {
                this.balance_locked = balance_locked;
            }

            public Integer getBond() {
                return bond;
            }

            public void setBond(Integer bond) {
                this.bond = bond;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }
        }
    }
}
