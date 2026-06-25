package io.openim.android.ouicore.entity;

import com.google.gson.Gson;

public class LoginBean {

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
            private String chatToken;
            private String userID;
            private String userSig;

            public String getUserSig() {
                return userSig;
            }

            public void setUserSig(String userSig) {
                this.userSig = userSig;
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
}
