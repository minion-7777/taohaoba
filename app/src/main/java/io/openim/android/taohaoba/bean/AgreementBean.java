package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class AgreementBean {

    /**
     * articles : {"id":1002,"ArticlesCategory":{"id":0,"name":"","sort_order":0,"status":0,"created_time":"0001-01-01T00:00:00Z","updated_time":"0001-01-01T00:00:00Z","delete_time":"0001-01-01T00:00:00Z"},"category_id":1001,"title":"测试","content":"<p>测试<\/p>","is_highlighted":0,"status":1,"created_time":"2025-05-19T12:06:37+08:00","updated_time":"2025-05-19T12:06:37+08:00","delete_time":"0001-01-01T00:00:00Z"}
     */

    private ArticlesDTO articles;

    public static AgreementBean objectFromData(String str) {

        return new Gson().fromJson(str, AgreementBean.class);
    }

    public ArticlesDTO getArticles() {
        return articles;
    }

    public void setArticles(ArticlesDTO articles) {
        this.articles = articles;
    }

    public static class ArticlesDTO {
        /**
         * id : 1002
         * ArticlesCategory : {"id":0,"name":"","sort_order":0,"status":0,"created_time":"0001-01-01T00:00:00Z","updated_time":"0001-01-01T00:00:00Z","delete_time":"0001-01-01T00:00:00Z"}
         * category_id : 1001
         * title : 测试
         * content : <p>测试</p>
         * is_highlighted : 0
         * status : 1
         * created_time : 2025-05-19T12:06:37+08:00
         * updated_time : 2025-05-19T12:06:37+08:00
         * delete_time : 0001-01-01T00:00:00Z
         */

        private Integer id;
        private ArticlesCategoryDTO ArticlesCategory;
        private Integer category_id;
        private String title;
        private String content;
        private Integer is_highlighted;
        private Integer status;
        private String created_time;
        private String updated_time;
        private String delete_time;

        public static ArticlesDTO objectFromData(String str) {

            return new Gson().fromJson(str, ArticlesDTO.class);
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public ArticlesCategoryDTO getArticlesCategory() {
            return ArticlesCategory;
        }

        public void setArticlesCategory(ArticlesCategoryDTO ArticlesCategory) {
            this.ArticlesCategory = ArticlesCategory;
        }

        public Integer getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getIs_highlighted() {
            return is_highlighted;
        }

        public void setIs_highlighted(Integer is_highlighted) {
            this.is_highlighted = is_highlighted;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
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

        public static class ArticlesCategoryDTO {
            /**
             * id : 0
             * name :
             * sort_order : 0
             * status : 0
             * created_time : 0001-01-01T00:00:00Z
             * updated_time : 0001-01-01T00:00:00Z
             * delete_time : 0001-01-01T00:00:00Z
             */

            private Integer id;
            private String name;
            private Integer sort_order;
            private Integer status;
            private String created_time;
            private String updated_time;
            private String delete_time;

            public static ArticlesCategoryDTO objectFromData(String str) {

                return new Gson().fromJson(str, ArticlesCategoryDTO.class);
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

            public Integer getSort_order() {
                return sort_order;
            }

            public void setSort_order(Integer sort_order) {
                this.sort_order = sort_order;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
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
}
