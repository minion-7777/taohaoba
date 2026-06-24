package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class BalanceBean {


    /**
     * user : {"created_time":"2025-04-22 17:45:33","updated_time":"2025-05-15 21:01:37","disable_time":null,"seal_time":null,"id":52,"username":"18646631558","password":"25f9e794323b453885f5181f1b624d0b","im_id":"3554655067","avatar":"","nickname":"嘿嘿","user_type":1,"status":1,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","jpush_registration_id":"1104a89793978b023c8","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null}
     * user_concern : 2
     */

    private UserDTO user;
    private Integer user_concern;
    private boolean is_authentication;
    private String realname;

    public boolean isIs_authentication() {
        return is_authentication;
    }

    public void setIs_authentication(boolean is_authentication) {
        this.is_authentication = is_authentication;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public static BalanceBean objectFromData(String str) {

        return new Gson().fromJson(str, BalanceBean.class);
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getUser_concern() {
        return user_concern;
    }

    public void setUser_concern(Integer user_concern) {
        this.user_concern = user_concern;
    }

    public static class UserDTO {
        /**
         * created_time : 2025-04-22 17:45:33
         * updated_time : 2025-05-15 21:01:37
         * disable_time : null
         * seal_time : null
         * id : 52
         * username : 18646631558
         * password : 25f9e794323b453885f5181f1b624d0b
         * im_id : 3554655067
         * avatar :
         * nickname : 嘿嘿
         * user_type : 1
         * status : 1
         * login_type : 0
         * imit :
         * qq :
         * wei_chat :
         * alipay :
         * jpush_registration_id : 1104a89793978b023c8
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
        private String jpush_registration_id;
        private WalletDTO wallet;
        private Object user_authentication;

        public static UserDTO objectFromData(String str) {

            return new Gson().fromJson(str, UserDTO.class);
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

        public String getJpush_registration_id() {
            return jpush_registration_id;
        }

        public void setJpush_registration_id(String jpush_registration_id) {
            this.jpush_registration_id = jpush_registration_id;
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
            private String balance_available;
            private String balance_locked;
            private String bond;
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

            public String getBalance_available() {
                return balance_available;
            }

            public void setBalance_available(String balance_available) {
                this.balance_available = balance_available;
            }

            public String getBalance_locked() {
                return balance_locked;
            }

            public void setBalance_locked(String balance_locked) {
                this.balance_locked = balance_locked;
            }

            public String getBond() {
                return bond;
            }

            public void setBond(String bond) {
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
