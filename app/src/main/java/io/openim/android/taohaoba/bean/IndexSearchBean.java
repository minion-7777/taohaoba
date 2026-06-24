package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

import java.util.List;

public class IndexSearchBean {

    /**
     * device_operator : {"device":[{"created_time":"2025-03-19 01:41:48","updated_time":"2025-04-02 22:16:27","id":2,"name":"苹果","sort":90,"top":1,"status":1},{"created_time":"2025-04-02 21:56:29","updated_time":"2025-04-02 21:56:29","id":4,"name":"安卓","sort":2,"top":1,"status":1},{"created_time":"2025-05-14 21:32:14","updated_time":"2025-05-14 21:32:14","id":1000,"name":"Windows","sort":1,"top":2,"status":1}],"operator":[{"created_time":"2025-03-19 02:01:46","updated_time":"2025-04-02 23:41:58","id":4,"name":"QQ","sort":103,"top":1,"status":1},{"created_time":"2025-04-02 23:34:02","updated_time":"2025-04-02 23:41:54","id":7,"name":"微信","sort":2,"top":1,"status":1},{"created_time":"2025-05-15 16:37:50","updated_time":"2025-05-15 16:37:53","id":8,"name":"网易国服","sort":100,"top":1,"status":1}],"game_type":[{"created_time":"2025-03-19 00:33:26","updated_time":"2025-03-25 01:37:44","id":2,"name":"端游","sort":100,"top":1,"status":1,"type":1,"type_zh":""},{"created_time":"2025-03-29 21:05:22","updated_time":"2025-03-29 21:05:24","id":3,"name":"手游","sort":100,"top":1,"status":1,"type":2,"type_zh":""}]}
     * goods : [{"id":57,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO5204038363397044753961977773","title":"王者荣耀售卖超级牛逼的账号先到先得111","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":58,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO9962485364713938673838747767","title":"王者荣耀售卖超级牛逼的账号先到先得11","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":61,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO0992048727518529160982104935","title":"王者荣耀售卖超级牛逼的账号先到先得11","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":62,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO2923594255042550084584977258","title":"王者荣耀售卖超级牛逼的账号先到先得11","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"苹果","operator_name":"QQ","game_service_name":"","retail_price":1500,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":70,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1746870077571455789787387148","title":"王者荣耀售卖超级牛逼的账号先到先得11","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"","operator_name":"","game_service_name":"电信,艾欧尼亚,区丰神,怒瑞玛","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":71,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1747048213705233587065016586","title":"王者荣耀售卖超级牛逼的账号先到先得","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"","operator_name":"","game_service_name":"电信,艾欧尼亚,区丰神,怒瑞玛","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1},{"id":72,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1747057117075079355135905453","title":"王者荣耀战力查询11","image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747057115749.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747057115766.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747057115765.jpg","device_name":"苹果","operator_name":"微信","game_service_name":"","retail_price":3333,"review_status":0,"label":"包赔服务,验证账号","is_authentication":1},{"id":1000,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1747143959339056614654356373","title":"王者荣耀水晶猎龙者11","image":"https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747143898780.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747143898781.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747143898781.jpg,https://taohao8.oss-cn-beijing.aliyuncs.com/images/1747143898763.jpg","device_name":"安卓","operator_name":"微信","game_service_name":"","retail_price":100,"review_status":0,"label":"包赔服务,验证账号","is_authentication":0},{"id":1021,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1747289683420701242669441903","title":"王者荣耀售卖超级牛逼的账号先到先得11","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"","operator_name":"","game_service_name":"电信,艾欧尼亚,区丰神,怒瑞玛","retail_price":1000,"review_status":0,"label":"包赔服务,验证账号","is_authentication":1},{"id":1023,"category_id":null,"game_id":null,"pattern_id":null,"game_name":null,"goods_no":"NO1747291345047402831425618309","title":"测试王者荣耀051511","image":"https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800","device_name":"","operator_name":"","game_service_name":"电信,艾欧尼亚,区丰神,怒瑞玛","retail_price":1000,"review_status":1,"label":"包赔服务,验证账号","is_authentication":1}]
     * total : 10
     */

    private DeviceOperatorDTO device_operator;
    private Integer total;
    private List<GoodsDTO> goods;

    public static IndexSearchBean objectFromData(String str) {

        return new Gson().fromJson(str, IndexSearchBean.class);
    }

    public DeviceOperatorDTO getDevice_operator() {
        return device_operator;
    }

    public void setDevice_operator(DeviceOperatorDTO device_operator) {
        this.device_operator = device_operator;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<GoodsDTO> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsDTO> goods) {
        this.goods = goods;
    }

    public static class DeviceOperatorDTO {
        private List<DeviceDTO> device;
        private List<DeviceDTO> operator;
        private List<DeviceDTO> game_type;

        public static DeviceOperatorDTO objectFromData(String str) {

            return new Gson().fromJson(str, DeviceOperatorDTO.class);
        }

        public List<DeviceDTO> getDevice() {
            return device;
        }

        public void setDevice(List<DeviceDTO> device) {
            this.device = device;
        }

        public List<DeviceDTO> getOperator() {
            return operator;
        }

        public void setOperator(List<DeviceDTO> operator) {
            this.operator = operator;
        }

        public List<DeviceDTO> getGame_type() {
            return game_type;
        }

        public void setGame_type(List<DeviceDTO> game_type) {
            this.game_type = game_type;
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
            private boolean isExpanded = false;

            public static DeviceDTO objectFromData(String str) {

                return new Gson().fromJson(str, DeviceDTO.class);
            }

            public boolean isExpanded() {
                return isExpanded;
            }

            public void setExpanded(boolean expanded) {
                isExpanded = expanded;
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

        public static class OperatorDTO {
            /**
             * created_time : 2025-03-19 02:01:46
             * updated_time : 2025-04-02 23:41:58
             * id : 4
             * name : QQ
             * sort : 103
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

            public static OperatorDTO objectFromData(String str) {

                return new Gson().fromJson(str, OperatorDTO.class);
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

        public static class GameTypeDTO {
            /**
             * created_time : 2025-03-19 00:33:26
             * updated_time : 2025-03-25 01:37:44
             * id : 2
             * name : 端游
             * sort : 100
             * top : 1
             * status : 1
             * type : 1
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
    }

    public static class GoodsDTO {
        /**
         * id : 57
         * category_id : null
         * game_id : null
         * pattern_id : null
         * game_name : null
         * goods_no : NO5204038363397044753961977773
         * title : 王者荣耀售卖超级牛逼的账号先到先得111
         * image : https://img2.baidu.com/it/u=3192240317,2727236332&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800
         * device_name : 苹果
         * operator_name : QQ
         * game_service_name :
         * retail_price : 1000
         * review_status : 1
         * label : 包赔服务,验证账号
         * is_authentication : 1
         */

        private Integer id;
        private Object category_id;
        private Object game_id;
        private Object pattern_id;
        private Object game_name;
        private String goods_no;
        private String title;
        private String image;
        private String device_name;
        private String operator_name;
        private String game_service_name;
        private Integer retail_price;
        private Integer review_status;
        private String label;
        private Integer is_authentication;

        private String view_count;//查看人数
        private String favorite_count;//收藏人数

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getFavorite_count() {
            return favorite_count;
        }

        public void setFavorite_count(String favorite_count) {
            this.favorite_count = favorite_count;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Object getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Object category_id) {
            this.category_id = category_id;
        }

        public Object getGame_id() {
            return game_id;
        }

        public void setGame_id(Object game_id) {
            this.game_id = game_id;
        }

        public Object getPattern_id() {
            return pattern_id;
        }

        public void setPattern_id(Object pattern_id) {
            this.pattern_id = pattern_id;
        }

        public Object getGame_name() {
            return game_name;
        }

        public void setGame_name(Object game_name) {
            this.game_name = game_name;
        }

        public String getGoods_no() {
            return goods_no;
        }

        public void setGoods_no(String goods_no) {
            this.goods_no = goods_no;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getOperator_name() {
            return operator_name;
        }

        public void setOperator_name(String operator_name) {
            this.operator_name = operator_name;
        }

        public String getGame_service_name() {
            return game_service_name;
        }

        public void setGame_service_name(String game_service_name) {
            this.game_service_name = game_service_name;
        }

        public Integer getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(Integer retail_price) {
            this.retail_price = retail_price;
        }

        public Integer getReview_status() {
            return review_status;
        }

        public void setReview_status(Integer review_status) {
            this.review_status = review_status;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Integer getIs_authentication() {
            return is_authentication;
        }

        public void setIs_authentication(Integer is_authentication) {
            this.is_authentication = is_authentication;
        }
    }
}
