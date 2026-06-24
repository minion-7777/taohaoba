package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class TransactionBean {

    private List<ListDTO> list;
    private Integer total;

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class ListDTO {

        /**
         * created_time : 2025-05-07 15:56:16
         * updated_time : 2025-05-07 15:56:16
         * id : 18
         * userId : 59
         * user : null
         * walletId : 3
         * wallet : null
         * status : 1
         * type : 5
         * type_zh : 交易成功
         * amount : 1000
         * fee : 0
         * withdrawal_id : 0
         * withdrawal : null
         * recharge_id : 0
         * recharge : null
         * remark : 订单支付商品价格
         * change_type : 2
         * before_amount : 1500
         * after_amount : 500
         * date : 2025-05-07
         * adminId : 0
         * orderId : 22
         * order : {"place_time":"2025-05-05 19:07:49","pay_time":"2025-05-07 21:40:58","deal_time":null,"take_time":null,"cancel_time":null,"refund_time":null,"system_refund_time":null,"send_time":null,"verify_time":null,"created_time":"2025-05-05 19:07:49","updated_time":"2025-05-08 13:12:24","id":22,"order_no":"NO2676101437603226347800664818","goods_id":67,"goods":null,"game_id":25,"game":null,"buy_user_id":59,"user":{"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null},"status":0,"user_goods_service":null,"status_zh":"","sell_user_type":1,"goods_price":1000,"payment_price":1325,"reparation_price":200,"pattern_price":125,"payment_type":1,"sell_user_id":58,"sell_user":null,"buy_shares":1,"is_reparation":1,"reparation_id":11,"pattern_id":2,"pattern":null,"customer_service_id":"1540912350","im_group_id":41,"im_group":null,"refund_content":"","buy_user_contact":"这里是买家联系信息","reparation_num":1}
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private Integer userId;
        private Object user;
        private Integer walletId;
        private Object wallet;
        private Integer status;
        private Integer type = 0;
        private String type_zh;
        private String amount;
        private Integer fee;
        private Integer withdrawal_id;
        private Object withdrawal;
        private Integer recharge_id;
        private Object recharge;
        private String remark;
        private Integer change_type = 0;
        private String before_amount;
        private String after_amount;
        private String date;
        private Integer adminId;
        private Integer orderId;
        private OrderDTO order;

        public static TransactionBean objectFromData(String str) {

            return new Gson().fromJson(str, TransactionBean.class);
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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public Integer getWalletId() {
            return walletId;
        }

        public void setWalletId(Integer walletId) {
            this.walletId = walletId;
        }

        public Object getWallet() {
            return wallet;
        }

        public void setWallet(Object wallet) {
            this.wallet = wallet;
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

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public Integer getFee() {
            return fee;
        }

        public void setFee(Integer fee) {
            this.fee = fee;
        }

        public Integer getWithdrawal_id() {
            return withdrawal_id;
        }

        public void setWithdrawal_id(Integer withdrawal_id) {
            this.withdrawal_id = withdrawal_id;
        }

        public Object getWithdrawal() {
            return withdrawal;
        }

        public void setWithdrawal(Object withdrawal) {
            this.withdrawal = withdrawal;
        }

        public Integer getRecharge_id() {
            return recharge_id;
        }

        public void setRecharge_id(Integer recharge_id) {
            this.recharge_id = recharge_id;
        }

        public Object getRecharge() {
            return recharge;
        }

        public void setRecharge(Object recharge) {
            this.recharge = recharge;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Integer getChange_type() {
            return change_type;
        }

        public void setChange_type(Integer change_type) {
            this.change_type = change_type;
        }

        public String getBefore_amount() {
            return before_amount;
        }

        public void setBefore_amount(String before_amount) {
            this.before_amount = before_amount;
        }

        public String getAfter_amount() {
            return after_amount;
        }

        public void setAfter_amount(String after_amount) {
            this.after_amount = after_amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getAdminId() {
            return adminId;
        }

        public void setAdminId(Integer adminId) {
            this.adminId = adminId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public OrderDTO getOrder() {
            return order;
        }

        public void setOrder(OrderDTO order) {
            this.order = order;
        }

        public static class OrderDTO {
            /**
             * place_time : 2025-05-05 19:07:49
             * pay_time : 2025-05-07 21:40:58
             * deal_time : null
             * take_time : null
             * cancel_time : null
             * refund_time : null
             * system_refund_time : null
             * send_time : null
             * verify_time : null
             * created_time : 2025-05-05 19:07:49
             * updated_time : 2025-05-08 13:12:24
             * id : 22
             * order_no : NO2676101437603226347800664818
             * goods_id : 67
             * goods : null
             * game_id : 25
             * game : null
             * buy_user_id : 59
             * user : {"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null}
             * status : 0
             * user_goods_service : null
             * status_zh :
             * sell_user_type : 1
             * goods_price : 1000
             * payment_price : 1325
             * reparation_price : 200
             * pattern_price : 125
             * payment_type : 1
             * sell_user_id : 58
             * sell_user : null
             * buy_shares : 1
             * is_reparation : 1
             * reparation_id : 11
             * pattern_id : 2
             * pattern : null
             * customer_service_id : 1540912350
             * im_group_id : 41
             * im_group : null
             * refund_content :
             * buy_user_contact : 这里是买家联系信息
             * reparation_num : 1
             */

            private String place_time;
            private String pay_time;
            private Object deal_time;
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
            private Object goods;
            private Integer game_id;
            private Object game;
            private Integer buy_user_id;
            private UserDTO user;
            private Integer status;
            private Object user_goods_service;
            private String status_zh;
            private Integer sell_user_type;
            private Integer goods_price;
            private Integer payment_price;
            private Integer reparation_price;
            private Integer pattern_price;
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

            public static OrderDTO objectFromData(String str) {

                return new Gson().fromJson(str, OrderDTO.class);
            }

            public String getPlace_time() {
                return place_time;
            }

            public void setPlace_time(String place_time) {
                this.place_time = place_time;
            }

            public String getPay_time() {
                return pay_time;
            }

            public void setPay_time(String pay_time) {
                this.pay_time = pay_time;
            }

            public Object getDeal_time() {
                return deal_time;
            }

            public void setDeal_time(Object deal_time) {
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

            public Object getGoods() {
                return goods;
            }

            public void setGoods(Object goods) {
                this.goods = goods;
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

            public Integer getBuy_user_id() {
                return buy_user_id;
            }

            public void setBuy_user_id(Integer buy_user_id) {
                this.buy_user_id = buy_user_id;
            }

            public UserDTO getUser() {
                return user;
            }

            public void setUser(UserDTO user) {
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

            public Integer getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(Integer goods_price) {
                this.goods_price = goods_price;
            }

            public Integer getPayment_price() {
                return payment_price;
            }

            public void setPayment_price(Integer payment_price) {
                this.payment_price = payment_price;
            }

            public Integer getReparation_price() {
                return reparation_price;
            }

            public void setReparation_price(Integer reparation_price) {
                this.reparation_price = reparation_price;
            }

            public Integer getPattern_price() {
                return pattern_price;
            }

            public void setPattern_price(Integer pattern_price) {
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
                 * wallet : {"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""}
                 * user_authentication : null
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
                private WalletDTO wallet;
                private Object user_authentication;

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
    }
}
