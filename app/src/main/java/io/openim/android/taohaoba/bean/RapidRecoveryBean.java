package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class RapidRecoveryBean {

    /**
     * id : 1
     * user_id : 59
     * user : {"created_time":"2025-04-22 20:37:11","updated_time":"2025-04-22 20:37:11","disable_time":null,"seal_time":null,"id":59,"username":"18646631559","password":"25f9e794323b453885f5181f1b624d0b","im_id":"1975631245","avatar":"","nickname":"","user_type":1,"status":1,"login_type":0,"imit":"","qq":"","wei_chat":"","alipay":""}
     * game_id : 18
     * game : {"created_time":"2025-04-12 16:31:17","updated_time":"2025-05-04 15:52:38","id":18,"game_no":"NO3481849813050633584218184938","name":"王者荣耀","image":"http://gips0.baidu.com/it/u=1690853528,2506870245&fm=3028&app=3028&f=JPEG&fmt=auto?w=1024&h=1024","game_type_id":3,"GameType":{"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0},"device_id":"2,4,5,7","operator_id":"4,6,7,8","top":1,"status":1,"sort":96,"pinyin":"W"}
     */

    private Integer id;
    private Integer user_id;
    private UserDTO user;
    private Integer game_id;
    private GameDTO game;

    public static RapidRecoveryBean objectFromData(String str) {

        return new Gson().fromJson(str, RapidRecoveryBean.class);
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
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    public static class UserDTO {
        /**
         * created_time : 2025-04-22 20:37:11
         * updated_time : 2025-04-22 20:37:11
         * disable_time : null
         * seal_time : null
         * id : 59
         * username : 18646631559
         * password : 25f9e794323b453885f5181f1b624d0b
         * im_id : 1975631245
         * avatar :
         * nickname :
         * user_type : 1
         * status : 1
         * login_type : 0
         * imit :
         * qq :
         * wei_chat :
         * alipay :
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
    }

    public static class GameDTO {
        /**
         * created_time : 2025-04-12 16:31:17
         * updated_time : 2025-05-04 15:52:38
         * id : 18
         * game_no : NO3481849813050633584218184938
         * name : 王者荣耀
         * image : http://gips0.baidu.com/it/u=1690853528,2506870245&fm=3028&app=3028&f=JPEG&fmt=auto?w=1024&h=1024
         * game_type_id : 3
         * GameType : {"created_time":"0001-01-01 00:00:00","updated_time":"0001-01-01 00:00:00","id":0,"name":"","sort":0,"top":0,"status":0}
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
             */

            private String created_time;
            private String updated_time;
            private Integer id;
            private String name;
            private Integer sort;
            private Integer top;
            private Integer status;

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
        }
    }
}
