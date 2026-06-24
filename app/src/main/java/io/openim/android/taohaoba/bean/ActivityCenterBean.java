package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class ActivityCenterBean {


    /**
     * list : [{"activity_code":"lGRnhQZd","activity_name":"活动一","activity_img_url":"https://taohao8.oss-cn-beijing.aliyuncs.com/webtaohao8/home/banner_slices/banner3zheng1.png","activity_jump_url":"https://taohao8.cc/","activity_start_time_formatted":"2025-09-09 15:28:28","activity_end_time_formatted":"2025-12-15 20:04:05","current_status":"ongoing"}]
     * total : 1
     */

    private Integer total;
    private List<ListDTO> list;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO {
        /**
         * activity_code : lGRnhQZd
         * activity_name : 活动一
         * activity_img_url : https://taohao8.oss-cn-beijing.aliyuncs.com/webtaohao8/home/banner_slices/banner3zheng1.png
         * activity_jump_url : https://taohao8.cc/
         * activity_start_time_formatted : 2025-09-09 15:28:28
         * activity_end_time_formatted : 2025-12-15 20:04:05
         * current_status : ongoing
         */

        private String activity_code;
        private String activity_name;
        private String activity_img_url;
        private String activity_jump_url;
        private String activity_start_time_formatted;
        private String activity_end_time_formatted;
        private String current_status;

        public String getActivity_code() {
            return activity_code;
        }

        public void setActivity_code(String activity_code) {
            this.activity_code = activity_code;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getActivity_img_url() {
            return activity_img_url;
        }

        public void setActivity_img_url(String activity_img_url) {
            this.activity_img_url = activity_img_url;
        }

        public String getActivity_jump_url() {
            return activity_jump_url;
        }

        public void setActivity_jump_url(String activity_jump_url) {
            this.activity_jump_url = activity_jump_url;
        }

        public String getActivity_start_time_formatted() {
            return activity_start_time_formatted;
        }

        public void setActivity_start_time_formatted(String activity_start_time_formatted) {
            this.activity_start_time_formatted = activity_start_time_formatted;
        }

        public String getActivity_end_time_formatted() {
            return activity_end_time_formatted;
        }

        public void setActivity_end_time_formatted(String activity_end_time_formatted) {
            this.activity_end_time_formatted = activity_end_time_formatted;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }
    }
}
