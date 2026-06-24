package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class CategoryBean {

    private List<CategoryDTO> category;

    public static CategoryBean objectFromData(String str) {

        return new Gson().fromJson(str, CategoryBean.class);
    }

    public List<CategoryDTO> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryDTO> category) {
        this.category = category;
    }

    public static class CategoryDTO {
        /**
         * created_time : 2025-03-21 00:41:58
         * updated_time : 2025-03-21 00:47:44
         * id : 1
         * name : 游戏账号
         * sort : 100
         * top : 1
         * status : 1
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private String name;
        private Integer sort;
        private Integer top;
        private Integer status;

        public static CategoryDTO objectFromData(String str) {

            return new Gson().fromJson(str, CategoryDTO.class);
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
