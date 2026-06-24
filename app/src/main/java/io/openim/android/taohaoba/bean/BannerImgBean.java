package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class BannerImgBean{


    private List<ListDTO> list;
    private InfoDTO info;

    public InfoDTO getInfo() {
        return info;
    }

    public void setInfo(InfoDTO info) {
        this.info = info;
    }

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public static class ListDTO {
        private String path;
        private Integer jump_type;
        private String target_url;
        private String activity_name;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getJump_type() {
            return jump_type;
        }

        public void setJump_type(Integer jump_type) {
            this.jump_type = jump_type;
        }

        public String getTarget_url() {
            return target_url;
        }

        public void setTarget_url(String target_url) {
            this.target_url = target_url;
        }
    }

    public static class InfoDTO {

        private String path;
        private Integer jump_type;
        private String target_url;
        private String activity_name;
        private String background_color;
        private String article_content;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getJump_type() {
            return jump_type;
        }

        public void setJump_type(Integer jump_type) {
            this.jump_type = jump_type;
        }

        public String getTarget_url() {
            return target_url;
        }

        public void setTarget_url(String target_url) {
            this.target_url = target_url;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getBackground_color() {
            return background_color;
        }

        public void setBackground_color(String background_color) {
            this.background_color = background_color;
        }

        public String getArticle_content() {
            return article_content;
        }

        public void setArticle_content(String article_content) {
            this.article_content = article_content;
        }
    }
}
