package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class GameConfigurationListBean {

    /**
     * device : [{"created_time":"2025-03-19 01:41:48","updated_time":"2025-04-02 22:16:27","id":2,"name":"苹果","sort":90,"top":1,"status":1},{"created_time":"2025-04-02 21:56:29","updated_time":"2025-04-02 21:56:29","id":4,"name":"安卓","sort":2,"top":1,"status":1}]
     * game_server : [{"id":1002,"name":"苹果","parent_id":0,"ParentItem":[{"id":1003,"name":"QQ","parent_id":1002,"ParentItem":[],"game_id":1002},{"id":1004,"name":"微信","parent_id":1002,"ParentItem":[],"game_id":1002}],"game_id":1002},{"id":1005,"name":"安卓","parent_id":0,"ParentItem":[{"id":1006,"name":"QQ","parent_id":1005,"ParentItem":[],"game_id":1002},{"id":1007,"name":"微信","parent_id":1005,"ParentItem":[],"game_id":1002}],"game_id":1002}]
     * game_type : {"created_time":"2025-03-29 21:05:22","updated_time":"2025-03-29 21:05:24","id":3,"name":"手游","sort":100,"top":1,"status":1,"type":2,"type_zh":""}
     * operator : [{"created_time":"2025-03-19 02:01:46","updated_time":"2025-04-02 23:41:58","id":4,"name":"QQ","sort":103,"top":1,"status":1},{"created_time":"2025-04-02 23:34:02","updated_time":"2025-04-02 23:41:54","id":7,"name":"微信","sort":2,"top":1,"status":1}]
     */

    private GameTypeDTO game_type;
    private List<DeviceDTO> device;
    private List<GameServerDTO> game_server;
    private List<DeviceDTO> operator;

    public static GameConfigurationListBean objectFromData(String str) {

        return new Gson().fromJson(str, GameConfigurationListBean.class);
    }

    public GameTypeDTO getGame_type() {
        return game_type;
    }

    public void setGame_type(GameTypeDTO game_type) {
        this.game_type = game_type;
    }

    public List<DeviceDTO> getDevice() {
        return device;
    }

    public void setDevice(List<DeviceDTO> device) {
        this.device = device;
    }

    public List<GameServerDTO> getGame_server() {
        return game_server;
    }

    public void setGame_server(List<GameServerDTO> game_server) {
        this.game_server = game_server;
    }

    public List<DeviceDTO> getOperator() {
        return operator;
    }

    public void setOperator(List<DeviceDTO> operator) {
        this.operator = operator;
    }

    public static class GameTypeDTO {
        /**
         * created_time : 2025-03-29 21:05:22
         * updated_time : 2025-03-29 21:05:24
         * id : 3
         * name : 手游
         * sort : 100
         * top : 1
         * status : 1
         * type : 2
         * type_zh :
         */

        private String created_time;
        private String updated_time;
        private Integer id;
        private String name;
        private Integer sort;
        private Integer top;
        private Integer status;
        private Integer type;
        private String type_zh;
        private boolean isExpanded;

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public static GameTypeDTO objectFromData(String str) {

            return new Gson().fromJson(str, GameTypeDTO.class);
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

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public String getType_zh() {
            return type_zh;
        }

        public void setType_zh(String type_zh) {
            this.type_zh = type_zh;
        }
    }

    public static class DeviceDTO {
        /**
         * created_time : 2025-03-19 01:41:48
         * updated_time : 2025-04-02 22:16:27
         * id : 2
         * name : 苹果
         * sort : 90
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
        private boolean isExpanded;

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public static DeviceDTO objectFromData(String str) {

            return new Gson().fromJson(str, DeviceDTO.class);
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

    public static class GameServerDTO {
        /**
         * id : 1002
         * name : 苹果
         * parent_id : 0
         * ParentItem : [{"id":1003,"name":"QQ","parent_id":1002,"ParentItem":[],"game_id":1002},{"id":1004,"name":"微信","parent_id":1002,"ParentItem":[],"game_id":1002}]
         * game_id : 1002
         */

        private Integer id;
        private String name;
        private Integer parent_id;
        private Integer game_id;
        private boolean isExpanded;

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        private List<GameServerDTO> ParentItem;

        public static GameServerDTO objectFromData(String str) {

            return new Gson().fromJson(str, GameServerDTO.class);
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

        public Integer getParent_id() {
            return parent_id;
        }

        public void setParent_id(Integer parent_id) {
            this.parent_id = parent_id;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public List<GameServerDTO> getParentItem() {
            return ParentItem;
        }

        public void setParentItem(List<GameServerDTO> ParentItem) {
            this.ParentItem = ParentItem;
        }
    }
}
