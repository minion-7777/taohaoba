package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class RealnameListBean {

    /**
     * created_time : 2025-04-22 20:10:45
     * updated_time : 2025-04-25 15:00:15
     * submit_time : 2025-04-22 20:10:45
     * examine_time : null
     * face_submit_time : null
     * face_examine_time : null
     * alipay_time : 2025-04-25 15:00:15
     * id : 14
     * user_id : 58
     * User : {"created_time":null,"updated_time":null,"disable_time":null,"seal_time":null,"id":0,"username":"","password":"","im_id":"","avatar":"","nickname":"","user_type":0,"status":0,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":""}
     * realname : 李四
     * number : 441313131313131310
     * front_img :
     * back_img :
     * face_status : 0
     * status : 2
     * alipay_lift_time : 0001-01-01T00:00:00Z
     * type : 1
     * location :
     * face_video :
     */

    private String created_time;
    private String updated_time;
    private String submit_time;
    private Object examine_time;
    private Object face_submit_time;
    private Object face_examine_time;
    private String alipay_time;
    private Integer id;
    private Integer user_id;
    private UserDTO User;
    private String realname;
    private String number;
    private String front_img;
    private String back_img;
    private Integer face_status;
    private Integer status;
    private String alipay_lift_time;
    private Integer type;
    private String location;
    private String face_video;

    public static RealnameListBean objectFromData(String str) {

        return new Gson().fromJson(str, RealnameListBean.class);
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

    public Object getFace_submit_time() {
        return face_submit_time;
    }

    public void setFace_submit_time(Object face_submit_time) {
        this.face_submit_time = face_submit_time;
    }

    public Object getFace_examine_time() {
        return face_examine_time;
    }

    public void setFace_examine_time(Object face_examine_time) {
        this.face_examine_time = face_examine_time;
    }

    public String getAlipay_time() {
        return alipay_time;
    }

    public void setAlipay_time(String alipay_time) {
        this.alipay_time = alipay_time;
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

    public Integer getFace_status() {
        return face_status;
    }

    public void setFace_status(Integer face_status) {
        this.face_status = face_status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlipay_lift_time() {
        return alipay_lift_time;
    }

    public void setAlipay_lift_time(String alipay_lift_time) {
        this.alipay_lift_time = alipay_lift_time;
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
}
