package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class RecommendListBean{


    private List<GameDTO> game;


    public List<GameDTO> getGame() {
        return game;
    }

    public void setGame(List<GameDTO> game) {
        this.game = game;
    }

    public static class GameDTO {
        /**
         * id : 18
         * goods_id : null
         * game_no : NO8748100926248150689944274597
         * game_type_id : 3
         * image : http://gips0.baidu.com/it/u=1690853528,2506870245&fm=3028&app=3028&f=JPEG&fmt=auto?w=1024&h=1024
         * name : 王者荣耀
         * sort : 100
         * pinyin : W
         * created_time : 0001-01-01 00:00:00
         * updated_time : 0001-01-01 00:00:00
         */

        private Integer id;
        private Object goods_id;
        private String game_no;
        private Integer game_type_id;
        private String image;
        private String name;
        private Integer sort;
        private String pinyin;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(Object goods_id) {
            this.goods_id = goods_id;
        }

        public String getGame_no() {
            return game_no;
        }

        public void setGame_no(String game_no) {
            this.game_no = game_no;
        }

        public Integer getGame_type_id() {
            return game_type_id;
        }

        public void setGame_type_id(Integer game_type_id) {
            this.game_type_id = game_type_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

    }
}
