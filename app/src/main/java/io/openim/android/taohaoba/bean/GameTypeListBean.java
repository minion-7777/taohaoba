package io.openim.android.taohaoba.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GameTypeListBean extends BaseResponseBean {

    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        @SerializedName("game_type")
        private List<GameTypeDTO> gameType;

        public List<GameTypeDTO> getGameType() {
            return gameType;
        }

        public void setGameType(List<GameTypeDTO> gameType) {
            this.gameType = gameType;
        }

        public static class GameTypeDTO {
            @SerializedName("created_time")
            private String createdTime;
            @SerializedName("updated_time")
            private String updatedTime;
            @SerializedName("id")
            private Integer id;
            @SerializedName("name")
            private String name;
            @SerializedName("sort")
            private Integer sort;
            @SerializedName("top")
            private Integer top;
            @SerializedName("status")
            private Integer status;
            private boolean isCheck = false;

            public GameTypeDTO(Integer id, String name, boolean isCheck) {
                this.id = id;
                this.name = name;
                this.isCheck = isCheck;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public String getCreatedTime() {
                return createdTime;
            }

            public void setCreatedTime(String createdTime) {
                this.createdTime = createdTime;
            }

            public String getUpdatedTime() {
                return updatedTime;
            }

            public void setUpdatedTime(String updatedTime) {
                this.updatedTime = updatedTime;
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
}
