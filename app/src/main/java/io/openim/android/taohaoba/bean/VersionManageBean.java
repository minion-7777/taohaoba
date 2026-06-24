package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class VersionManageBean {

    /**
     * info : {"id":2,"version_name":"9","version_code":"3.2.1","device_type":"Android","channel_type":"vivo","download_url":"http://sdsdsd.com/1.apk","is_force_update":0,"description":"sdfdsf","publish_time":"2025-05-13T20:04:27+08:00","is_enabled":1,"created_time":"2025-05-13T20:04:27+08:00","updated_time":"2025-05-13T20:04:27+08:00","delete_time":"0001-01-01T00:00:00Z"}
     */

    private InfoDTO info;

    public static VersionManageBean objectFromData(String str) {

        return new Gson().fromJson(str, VersionManageBean.class);
    }

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public static class InfoDTO {
        /**
         * id : 2
         * version_name : 9
         * version_code : 3.2.1
         * device_type : Android
         * channel_type : vivo
         * download_url : http://sdsdsd.com/1.apk
         * is_force_update : 0
         * description : sdfdsf
         * publish_time : 2025-05-13T20:04:27+08:00
         * is_enabled : 1
         * created_time : 2025-05-13T20:04:27+08:00
         * updated_time : 2025-05-13T20:04:27+08:00
         * delete_time : 0001-01-01T00:00:00Z
         */

        private Integer id;
        private String version_name;//版本名称
        private String version_code;//版本号
        private String device_type;//设备类型
        private String channel_type;//渠道类型
        private String download_url;//下载地址
        private Integer is_force_update;//是否强制更新 1为强制更新 0为不
        private String description;//版本描述
        private String publish_time;
        private Integer is_enabled;//是否启用 1为是 0为否
        private String created_time;
        private String updated_time;
        private String delete_time;

        public static InfoDTO objectFromData(String str) {

            return new Gson().fromJson(str, InfoDTO.class);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getChannel_type() {
            return channel_type;
        }

        public void setChannel_type(String channel_type) {
            this.channel_type = channel_type;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public Integer getIs_force_update() {
            return is_force_update;
        }

        public void setIs_force_update(Integer is_force_update) {
            this.is_force_update = is_force_update;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public Integer getIs_enabled() {
            return is_enabled;
        }

        public void setIs_enabled(Integer is_enabled) {
            this.is_enabled = is_enabled;
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
    }
}
