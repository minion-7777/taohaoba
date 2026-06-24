package com.tencent.qcloud.tuikit.tuichat.bean;

import com.google.gson.Gson;

public class AuthInfoBean {

    /**
     * user_auth_bool : {"face":true,"realname":true,"reparation":false}
     * user_auth_info : {"created_time":"2025-05-04 19:44:08","updated_time":"2025-05-06 19:06:52","submit_time":"2025-05-04 19:44:08","examine_time":null,"face_submit_time":"2025-05-06 17:33:27","face_examine_time":null,"reparation_submit_time":"2025-05-06 19:06:52","reparation_examine_time":null,"id":17,"user_id":57,"User":{"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","jpush_registration_id":"","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null},"realname":"李四","number":"441313131313131310","front_img":"","back_img":"","status":6,"status_zh":"","type":2,"location":"134,658","face_video":"","admin_id":0,"remark":"","reparation_image":"http://www.dsakhfgidaklfhakloj.png","emergency_contact":"chen111","emergency_phone":"18646631338","contacts":"18646631335,18646661337","address":"这个就是我的联系地址信息"}
     */

    private UserAuthBoolDTO user_auth_bool;
    private UserAuthInfoDTO user_auth_info;

    public static AuthInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, AuthInfoBean.class);
    }

    public UserAuthBoolDTO getUser_auth_bool() {
        return user_auth_bool;
    }

    public void setUser_auth_bool(UserAuthBoolDTO user_auth_bool) {
        this.user_auth_bool = user_auth_bool;
    }

    public UserAuthInfoDTO getUser_auth_info() {
        return user_auth_info;
    }

    public void setUser_auth_info(UserAuthInfoDTO user_auth_info) {
        this.user_auth_info = user_auth_info;
    }

    public static class UserAuthBoolDTO {
        /**
         * face : true
         * realname : true
         * reparation : false
         */

        private Boolean face;
        private Boolean realname;
        private Integer reparation;

        public static UserAuthBoolDTO objectFromData(String str) {

            return new Gson().fromJson(str, UserAuthBoolDTO.class);
        }

        public Boolean isFace() {
            return face;
        }

        public void setFace(Boolean face) {
            this.face = face;
        }

        public Boolean isRealname() {
            return realname;
        }

        public void setRealname(Boolean realname) {
            this.realname = realname;
        }

        public Integer isReparation() {
            return reparation;
        }

        public void setReparation(Integer reparation) {
            this.reparation = reparation;
        }
    }

    public static class UserAuthInfoDTO {
        /**
         * created_time : 2025-05-04 19:44:08
         * updated_time : 2025-05-06 19:06:52
         * submit_time : 2025-05-04 19:44:08
         * examine_time : null
         * face_submit_time : 2025-05-06 17:33:27
         * face_examine_time : null
         * reparation_submit_time : 2025-05-06 19:06:52
         * reparation_examine_time : null
         * id : 17
         * user_id : 57
         * User : {"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":"","jpush_registration_id":"","wallet":{"created_time":null,"updated_time":null,"id":0,"user_id":0,"balance_available":0,"balance_locked":0,"bond":0,"coin":""},"user_authentication":null}
         * realname : 李四
         * number : 441313131313131310
         * front_img :
         * back_img :
         * status : 6
         * status_zh :
         * type : 2
         * location : 134,658
         * face_video :
         * admin_id : 0
         * remark :
         * reparation_image : http://www.dsakhfgidaklfhakloj.png
         * emergency_contact : chen111
         * emergency_phone : 18646631338
         * contacts : 18646631335,18646661337
         * address : 这个就是我的联系地址信息
         */

        private String created_time;
        private String updated_time;
        private String submit_time;
        private Object examine_time;
        private String face_submit_time;
        private Object face_examine_time;
        private String reparation_submit_time;
        private Object reparation_examine_time;
        private Integer id;
        private Integer user_id;
        private UserDTO User;
        private String realname;
        private String number;
        private String front_img;
        private String back_img;
        private Integer status;
        private String status_zh;
        private Integer type;
        private String location;
        private String face_video;
        private Integer admin_id;
        private String remark;
        private String reparation_image;
        private String emergency_contact;
        private String emergency_phone;
        private String contacts;
        private String address;

        public static UserAuthInfoDTO objectFromData(String str) {

            return new Gson().fromJson(str, UserAuthInfoDTO.class);
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

        public String getSubmit_time() {
            return submit_time;
        }

        public void setSubmit_time(String submit_time) {
            this.submit_time = submit_time;
        }

        public Object getExamine_time() {
            return examine_time;
        }

        public void setExamine_time(Object examine_time) {
            this.examine_time = examine_time;
        }

        public String getFace_submit_time() {
            return face_submit_time;
        }

        public void setFace_submit_time(String face_submit_time) {
            this.face_submit_time = face_submit_time;
        }

        public Object getFace_examine_time() {
            return face_examine_time;
        }

        public void setFace_examine_time(Object face_examine_time) {
            this.face_examine_time = face_examine_time;
        }

        public String getReparation_submit_time() {
            return reparation_submit_time;
        }

        public void setReparation_submit_time(String reparation_submit_time) {
            this.reparation_submit_time = reparation_submit_time;
        }

        public Object getReparation_examine_time() {
            return reparation_examine_time;
        }

        public void setReparation_examine_time(Object reparation_examine_time) {
            this.reparation_examine_time = reparation_examine_time;
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

        public UserDTO getUser() {
            return User;
        }

        public void setUser(UserDTO User) {
            this.User = User;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getFront_img() {
            return front_img;
        }

        public void setFront_img(String front_img) {
            this.front_img = front_img;
        }

        public String getBack_img() {
            return back_img;
        }

        public void setBack_img(String back_img) {
            this.back_img = back_img;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatus_zh() {
            return status_zh;
        }

        public void setStatus_zh(String status_zh) {
            this.status_zh = status_zh;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getFace_video() {
            return face_video;
        }

        public void setFace_video(String face_video) {
            this.face_video = face_video;
        }

        public Integer getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(Integer admin_id) {
            this.admin_id = admin_id;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReparation_image() {
            return reparation_image;
        }

        public void setReparation_image(String reparation_image) {
            this.reparation_image = reparation_image;
        }

        public String getEmergency_contact() {
            return emergency_contact;
        }

        public void setEmergency_contact(String emergency_contact) {
            this.emergency_contact = emergency_contact;
        }

        public String getEmergency_phone() {
            return emergency_phone;
        }

        public void setEmergency_phone(String emergency_phone) {
            this.emergency_phone = emergency_phone;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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
             * jpush_registration_id :
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
            private String jpush_registration_id;
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
