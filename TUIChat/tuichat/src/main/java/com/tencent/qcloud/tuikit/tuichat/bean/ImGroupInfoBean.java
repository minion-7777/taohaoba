package com.tencent.qcloud.tuikit.tuichat.bean;

import com.google.gson.Gson;

import java.util.List;

public class ImGroupInfoBean {


    /**
     * imGroup : {"id":10000260,"group_name":"寄售群0004-哈哈哈-002-3800.00元","group_avatar":"https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685506-1.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685507-2.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-3.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-4.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-5.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-6.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-7.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-8.png","im_group_id":"25052922393301010004","member_count":0,"im_owner_id":"4756257284","seller_id":10000005,"buyer_id":52,"game_id":1005,"good_id":1169,"pattern":1,"status":1,"created_time":"2025-05-29 22:39:33","updated_time":"2025-05-29 22:39:33","delete_time":"0001-01-01 00:00:00","owner_user_id":10000059,"avatar":"","nickname":"测试客服","order_id":10000120,"im_seller_id":"2891214147","im_buyer_id":"3554655067","goods_name":"贵族等级: V14 亮点: v14，35s准37s，新自来也已出","goods_image":"https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685506-1.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685507-2.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-3.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-4.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-5.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-6.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-7.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-8.png","retail_price":3800,"order_status":9,"order_status_type":"已取消","seller_info":{"created_time":"2025-05-16 06:20:07","updated_time":"2025-05-29 15:29:55","disable_time":null,"seal_time":null,"id":10000005,"username":"13177356661","password":"25f9e794323b453885f5181f1b624d0b","im_id":"2891214147","avatar":"","nickname":"002","user_type":0,"status":1,"login_type":0,"imit":"","qq":"2496785623","wei_chat":"2496785623","alipay":"","user_game":null,"jpush_registration_id":"18071adc022f385c758","getui_client_id":"b0affc7d90681b937f38944c29d13c2d","wallet":null,"user_authentication":null,"login_time":"","ip":""},"buyer_info":{"created_time":"2025-04-23 01:45:33","updated_time":"2025-05-22 05:58:22","disable_time":null,"seal_time":null,"id":52,"username":"18646631558","password":"25f9e794323b453885f5181f1b624d0b","im_id":"3554655067","avatar":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747834761331.jpg","nickname":"哈哈哈","user_type":1,"status":1,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","user_game":null,"jpush_registration_id":"160a3797c922a44b681","getui_client_id":"2b6cb5303e3170201b884c7b80930b0b","wallet":null,"user_authentication":null,"login_time":"","ip":""}}
     */

    private ImGroupDTO imGroup;


    public static ImGroupInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, ImGroupInfoBean.class);
    }

    public ImGroupDTO getImGroup() {
        return imGroup;
    }

    public void setImGroup(ImGroupDTO imGroup) {
        this.imGroup = imGroup;
    }

    public static class ImGroupDTO {
        /**
         * id : 10000260
         * group_name : 寄售群0004-哈哈哈-002-3800.00元
         * group_avatar : https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685506-1.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685507-2.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-3.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-4.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-5.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-6.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-7.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-8.png
         * im_group_id : 25052922393301010004
         * member_count : 0
         * im_owner_id : 4756257284
         * seller_id : 10000005
         * buyer_id : 52
         * game_id : 1005
         * good_id : 1169
         * pattern : 1
         * status : 1
         * created_time : 2025-05-29 22:39:33
         * updated_time : 2025-05-29 22:39:33
         * delete_time : 0001-01-01 00:00:00
         * owner_user_id : 10000059
         * avatar :
         * nickname : 测试客服
         * order_id : 10000120
         * im_seller_id : 2891214147
         * im_buyer_id : 3554655067
         * goods_name : 贵族等级: V14 亮点: v14，35s准37s，新自来也已出
         * goods_image : https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685506-1.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685507-2.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-3.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-4.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685508-5.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-6.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-7.png,https://taohao8.oss-cn-beijing.aliyuncs.com/GameList%252F1748440685509-8.png
         * retail_price : 3800
         * order_status : 9
         * order_status_type : 已取消
         * seller_info : {"created_time":"2025-05-16 06:20:07","updated_time":"2025-05-29 15:29:55","disable_time":null,"seal_time":null,"id":10000005,"username":"13177356661","password":"25f9e794323b453885f5181f1b624d0b","im_id":"2891214147","avatar":"","nickname":"002","user_type":0,"status":1,"login_type":0,"imit":"","qq":"2496785623","wei_chat":"2496785623","alipay":"","user_game":null,"jpush_registration_id":"18071adc022f385c758","getui_client_id":"b0affc7d90681b937f38944c29d13c2d","wallet":null,"user_authentication":null,"login_time":"","ip":""}
         * buyer_info : {"created_time":"2025-04-23 01:45:33","updated_time":"2025-05-22 05:58:22","disable_time":null,"seal_time":null,"id":52,"username":"18646631558","password":"25f9e794323b453885f5181f1b624d0b","im_id":"3554655067","avatar":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747834761331.jpg","nickname":"哈哈哈","user_type":1,"status":1,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","user_game":null,"jpush_registration_id":"160a3797c922a44b681","getui_client_id":"2b6cb5303e3170201b884c7b80930b0b","wallet":null,"user_authentication":null,"login_time":"","ip":""}
         */

        private Integer id;
        private String group_name;
        private String group_avatar;
        private String im_group_id;
        private Integer member_count;
        private String im_owner_id;
        private Integer seller_id;
        private Integer buyer_id;
        private Integer game_id;
        private Integer good_id;
        private Integer pattern;
        private Integer status;
        private String created_time;
        private String updated_time;
        private String delete_time;
        private Integer owner_user_id;
        private String avatar;
        private String nickname;
        private Integer order_id;
        private String im_seller_id;
        private String im_buyer_id;
        private String goods_name;
        private String goods_image;
        private String retail_price;//商品售价
        private String goods_price;//订单售价
        private Integer order_status;//支付状态 0：待支付 1：支付成功 2：支付失败 3：待发货 4：已发货 5：等待验号  6：已验号  7: 待确认收货  8：已完成  9：已取消 10：待退款 11：退款中    12：已退款    13：退款失败
        private String order_status_type;
        private SellerInfoDTO seller_info;
        private BuyerInfoDTO buyer_info;
        private Integer review_status; //审查状态 0：审核中 1：已上架 2：已下架  3:已删除 4：审核失败  5:已售罄
        private Integer seller_service_ratio = 0;
        private Double seller_service_price = 0.0;
        private Double seller_amount_conf = 0.0;
        private String seller_user_name;
        private String buyer_user_name;
        private List<MembersDTO> members;

        public String getSeller_user_name() {
            return seller_user_name;
        }

        public void setSeller_user_name(String seller_user_name) {
            this.seller_user_name = seller_user_name;
        }

        public String getBuyer_user_name() {
            return buyer_user_name;
        }

        public void setBuyer_user_name(String buyer_user_name) {
            this.buyer_user_name = buyer_user_name;
        }

        public List<MembersDTO> getMembers() {
            return members;
        }

        public void setMembers(List<MembersDTO> members) {
            this.members = members;
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

        public Integer getReview_status() {
            return review_status;
        }

        public void setReview_status(Integer review_status) {
            this.review_status = review_status;
        }
        public static ImGroupDTO objectFromData(String str) {

            return new Gson().fromJson(str, ImGroupDTO.class);
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
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

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public Integer getGood_id() {
            return good_id;
        }

        public void setGood_id(Integer good_id) {
            this.good_id = good_id;
        }

        public Integer getPattern() {
            return pattern;
        }

        public void setPattern(Integer pattern) {
            this.pattern = pattern;
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

        public Integer getOwner_user_id() {
            return owner_user_id;
        }

        public void setOwner_user_id(Integer owner_user_id) {
            this.owner_user_id = owner_user_id;
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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(String retail_price) {
            this.retail_price = retail_price;
        }

        public Integer getOrder_status() {
            return order_status;
        }

        public void setOrder_status(Integer order_status) {
            this.order_status = order_status;
        }

        public String getOrder_status_type() {
            return order_status_type;
        }

        public void setOrder_status_type(String order_status_type) {
            this.order_status_type = order_status_type;
        }

        public SellerInfoDTO getSeller_info() {
            return seller_info;
        }

        public void setSeller_info(SellerInfoDTO seller_info) {
            this.seller_info = seller_info;
        }

        public BuyerInfoDTO getBuyer_info() {
            return buyer_info;
        }

        public void setBuyer_info(BuyerInfoDTO buyer_info) {
            this.buyer_info = buyer_info;
        }

        public static class MembersDTO {


            private Integer id;
            private String user_id;
            private int role_type;//角色类型：0-普通成员，1-卖家，2-买家，3-群主，4-管理员
            private String nickname;
            private String avatar;
            private String im_id;
            private String user_name;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getRole_type() {
                return role_type;
            }

            public void setRole_type(int role_type) {
                this.role_type = role_type;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getIm_id() {
                return im_id;
            }

            public void setIm_id(String im_id) {
                this.im_id = im_id;
            }
        }
        public static class SellerInfoDTO {
            /**
             * created_time : 2025-05-16 06:20:07
             * updated_time : 2025-05-29 15:29:55
             * disable_time : null
             * seal_time : null
             * id : 10000005
             * username : 13177356661
             * password : 25f9e794323b453885f5181f1b624d0b
             * im_id : 2891214147
             * avatar :
             * nickname : 002
             * user_type : 0
             * status : 1
             * login_type : 0
             * imit :
             * qq : 2496785623
             * wei_chat : 2496785623
             * alipay :
             * user_game : null
             * jpush_registration_id : 18071adc022f385c758
             * getui_client_id : b0affc7d90681b937f38944c29d13c2d
             * wallet : null
             * user_authentication : null
             * login_time :
             * ip :
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
            private Object user_game;
            private String jpush_registration_id;
            private String getui_client_id;
            private Object wallet;
            private Object user_authentication;
            private String login_time;
            private String ip;

            public static SellerInfoDTO objectFromData(String str) {

                return new Gson().fromJson(str, SellerInfoDTO.class);
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

            public Object getUser_game() {
                return user_game;
            }

            public void setUser_game(Object user_game) {
                this.user_game = user_game;
            }

            public String getJpush_registration_id() {
                return jpush_registration_id;
            }

            public void setJpush_registration_id(String jpush_registration_id) {
                this.jpush_registration_id = jpush_registration_id;
            }

            public String getGetui_client_id() {
                return getui_client_id;
            }

            public void setGetui_client_id(String getui_client_id) {
                this.getui_client_id = getui_client_id;
            }

            public Object getWallet() {
                return wallet;
            }

            public void setWallet(Object wallet) {
                this.wallet = wallet;
            }

            public Object getUser_authentication() {
                return user_authentication;
            }

            public void setUser_authentication(Object user_authentication) {
                this.user_authentication = user_authentication;
            }

            public String getLogin_time() {
                return login_time;
            }

            public void setLogin_time(String login_time) {
                this.login_time = login_time;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }

        public static class BuyerInfoDTO {
            /**
             * created_time : 2025-04-23 01:45:33
             * updated_time : 2025-05-22 05:58:22
             * disable_time : null
             * seal_time : null
             * id : 52
             * username : 18646631558
             * password : 25f9e794323b453885f5181f1b624d0b
             * im_id : 3554655067
             * avatar : https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747834761331.jpg
             * nickname : 哈哈哈
             * user_type : 1
             * status : 1
             * login_type : 0
             * imit :
             * qq :
             * wei_chat :
             * alipay :
             * user_game : null
             * jpush_registration_id : 160a3797c922a44b681
             * getui_client_id : 2b6cb5303e3170201b884c7b80930b0b
             * wallet : null
             * user_authentication : null
             * login_time :
             * ip :
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
            private Object user_game;
            private String jpush_registration_id;
            private String getui_client_id;
            private Object wallet;
            private Object user_authentication;
            private String login_time;
            private String ip;

            public static BuyerInfoDTO objectFromData(String str) {

                return new Gson().fromJson(str, BuyerInfoDTO.class);
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

            public Object getUser_game() {
                return user_game;
            }

            public void setUser_game(Object user_game) {
                this.user_game = user_game;
            }

            public String getJpush_registration_id() {
                return jpush_registration_id;
            }

            public void setJpush_registration_id(String jpush_registration_id) {
                this.jpush_registration_id = jpush_registration_id;
            }

            public String getGetui_client_id() {
                return getui_client_id;
            }

            public void setGetui_client_id(String getui_client_id) {
                this.getui_client_id = getui_client_id;
            }

            public Object getWallet() {
                return wallet;
            }

            public void setWallet(Object wallet) {
                this.wallet = wallet;
            }

            public Object getUser_authentication() {
                return user_authentication;
            }

            public void setUser_authentication(Object user_authentication) {
                this.user_authentication = user_authentication;
            }

            public String getLogin_time() {
                return login_time;
            }

            public void setLogin_time(String login_time) {
                this.login_time = login_time;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }
        }

        @Override
        public String toString() {
            return "ImGroupDTO{" +
                    "id=" + id +
                    ", group_name='" + group_name + '\'' +
                    ", group_avatar='" + group_avatar + '\'' +
                    ", im_group_id='" + im_group_id + '\'' +
                    ", member_count=" + member_count +
                    ", im_owner_id='" + im_owner_id + '\'' +
                    ", seller_id=" + seller_id +
                    ", buyer_id=" + buyer_id +
                    ", game_id=" + game_id +
                    ", good_id=" + good_id +
                    ", pattern=" + pattern +
                    ", status=" + status +
                    ", created_time='" + created_time + '\'' +
                    ", updated_time='" + updated_time + '\'' +
                    ", delete_time='" + delete_time + '\'' +
                    ", owner_user_id=" + owner_user_id +
                    ", avatar='" + avatar + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", order_id=" + order_id +
                    ", im_seller_id='" + im_seller_id + '\'' +
                    ", im_buyer_id='" + im_buyer_id + '\'' +
                    ", goods_name='" + goods_name + '\'' +
                    ", goods_image='" + goods_image + '\'' +
                    ", retail_price='" + retail_price + '\'' +
                    ", order_status=" + order_status +
                    ", order_status_type='" + order_status_type + '\'' +
                    ", seller_info=" + seller_info +
                    ", buyer_info=" + buyer_info +
                    '}';
        }
    }
}
