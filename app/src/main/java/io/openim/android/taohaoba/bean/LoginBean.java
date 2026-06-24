package io.openim.android.taohaoba.bean;


public class LoginBean extends BaseResponseBean {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String openImToken;
        private String token;
        public String openimId;

        public String getOpenimId() {
            return openimId;
        }

        public void setOpenimId(String openimId) {
            this.openimId = openimId;
        }

        public String getOpenImToken() {
            return openImToken;
        }

        public void setOpenImToken(String openImToken) {
            this.openImToken = openImToken;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}
