package io.openim.android.ouicore.entity;

import com.google.gson.Gson;

public class LoginBean {
    /**
     * imResponse : {"errCode":0,"errMsg":"","errDlt":"","data":{"imToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiUGxhdGZvcm1JRCI6MiwiZXhwIjoxNzU2OTkwMzYwLCJpYXQiOjE3NDkyMTQzNTV9.7WuP74VMa9JbDNq8zSjcLgQ7tddt1AmM7DodpBJ9LMA","chatToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiVXNlclR5cGUiOjEsIlBsYXRmb3JtSUQiOjAsImV4cCI6MTc1Njk5MDM2MCwibmJmIjoxNzQ5MjE0MzAwLCJpYXQiOjE3NDkyMTQzNjB9.rdUJoezfmM4H4WKtNQO__7jTa8yBCLXiUx31wpq9UaQ","userID":"3131530772"}}
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NDk2NDYzNjAsInVzZXJuYW1lIjoiMTU4NzM0NzY3MjYiLCJ1c2VyX2lkIjoxMDAwMDA2MCwidXNlcl90eXBlIjoidXNlciIsImlzc3VlZF9hdCI6MTc0OTIxNDM2MH0.DUKTbgsvju_qXzMECKTCdoU9ZTHPiljbjMH3kSMozEM
     */

    private ImResponseDTO imResponse;
    private String token;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ImResponseDTO getImResponse() {
        return imResponse;
    }

    public void setImResponse(ImResponseDTO imResponse) {
        this.imResponse = imResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class ImResponseDTO {
        /**
         * errCode : 0
         * errMsg :
         * errDlt :
         * data : {"imToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiUGxhdGZvcm1JRCI6MiwiZXhwIjoxNzU2OTkwMzYwLCJpYXQiOjE3NDkyMTQzNTV9.7WuP74VMa9JbDNq8zSjcLgQ7tddt1AmM7DodpBJ9LMA","chatToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiVXNlclR5cGUiOjEsIlBsYXRmb3JtSUQiOjAsImV4cCI6MTc1Njk5MDM2MCwibmJmIjoxNzQ5MjE0MzAwLCJpYXQiOjE3NDkyMTQzNjB9.rdUJoezfmM4H4WKtNQO__7jTa8yBCLXiUx31wpq9UaQ","userID":"3131530772"}
         */

        private Integer errCode;
        private String errMsg;
        private String errDlt;
        private DataDTO data;

        public static ImResponseDTO objectFromData(String str) {

            return new Gson().fromJson(str, ImResponseDTO.class);
        }

        public Integer getErrCode() {
            return errCode;
        }

        public void setErrCode(Integer errCode) {
            this.errCode = errCode;
        }

        public String getErrMsg() {
            return errMsg;
        }

        public void setErrMsg(String errMsg) {
            this.errMsg = errMsg;
        }

        public String getErrDlt() {
            return errDlt;
        }

        public void setErrDlt(String errDlt) {
            this.errDlt = errDlt;
        }

        public DataDTO getData() {
            return data;
        }

        public void setData(DataDTO data) {
            this.data = data;
        }

        public static class DataDTO {
            /**
             * imToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiUGxhdGZvcm1JRCI6MiwiZXhwIjoxNzU2OTkwMzYwLCJpYXQiOjE3NDkyMTQzNTV9.7WuP74VMa9JbDNq8zSjcLgQ7tddt1AmM7DodpBJ9LMA
             * chatToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJVc2VySUQiOiIzMTMxNTMwNzcyIiwiVXNlclR5cGUiOjEsIlBsYXRmb3JtSUQiOjAsImV4cCI6MTc1Njk5MDM2MCwibmJmIjoxNzQ5MjE0MzAwLCJpYXQiOjE3NDkyMTQzNjB9.rdUJoezfmM4H4WKtNQO__7jTa8yBCLXiUx31wpq9UaQ
             * userID : 3131530772
             */

            private String imToken;
            private String chatToken;
            private String userID;
            private String userSig;

            public String getUserSig() {
                return userSig;
            }

            public void setUserSig(String userSig) {
                this.userSig = userSig;
            }

            public String getImToken() {
                return imToken;
            }

            public void setImToken(String imToken) {
                this.imToken = imToken;
            }

            public String getChatToken() {
                return chatToken;
            }

            public void setChatToken(String chatToken) {
                this.chatToken = chatToken;
            }

            public String getUserID() {
                return userID;
            }

            public void setUserID(String userID) {
                this.userID = userID;
            }
        }
    }
//    private static final String TAG = "LoginBean";
//    public String imResponse;
//    public String token;
//
//    public String getImResponse() {
//        return imResponse;
//    }
//
//    public void setImResponse(String imResponse) {
//        this.imResponse = imResponse;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }

}
