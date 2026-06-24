package io.openim.android.taohaoba.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameListBean {

    private List<GameDTO> game;
    private List<GameTypeDTO> game_type;

    public List<GameDTO> getGame() {
        return game;
    }

    public void setGame(List<GameDTO> game) {
        this.game = game;
    }

    public List<GameTypeDTO> getGame_type() {
        return game_type;
    }

    public void setGame_type(List<GameTypeDTO> game_type) {
        this.game_type = game_type;
    }

    public static class GameDTO {
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

    public static class GameTypeDTO {

        private Integer id;
        private String name;
        private Integer sort;
        private Integer top;
        private Integer status;
        private boolean isCheck = false;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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
