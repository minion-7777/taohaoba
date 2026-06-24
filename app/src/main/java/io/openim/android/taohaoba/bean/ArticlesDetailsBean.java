package io.openim.android.taohaoba.bean;

import com.google.gson.Gson;

public class ArticlesDetailsBean {

    /**
     * articles : {"id":1012,"ArticlesCategory":{"id":0,"name":"","sort_order":0,"status":0,"created_time":"0001-01-01T00:00:00Z","updated_time":"0001-01-01T00:00:00Z","delete_time":"0001-01-01T00:00:00Z"},"category_id":1018,"user_id":0,"title":"王者荣耀近期新皮肤与联动活动\u200b","cover_url":"https://taohao8.oss-cn-beijing.aliyuncs.com/webtaohao8/home/%E9%BB%98%E8%AE%A4%E5%9B%BE/defaultGoods.png","author":"kke","content":"<h3 style=\"text-align: start;\"><strong>一、新皮肤与联动活动\u200b<\/strong><\/h3><ol><li style=\"text-align: left;\">\u200b\u200b山海经联动系列皮肤上线\u200b\u200b\u200b\u200b诸葛亮-天机白泽\u200b\u200b：无双限定品质，通过祈愿活动抽取（保底约4000点券），大招特效融合白泽神兽虚影，同步返场韩信-群星魔术团等限定皮肤。\u200b\u200b朵莉亚-幻珠鲛人\u200b\u200b：史诗限定皮肤，首周490点券含个性按键，技能特效以粉色鲛人主题为主，可通过绑定点券购买。\u200b\u200b司马懿-九山相柳\u200b\u200b：传说限定皮肤（首周1430点券），大招召唤九头蛇特效，因英雄重做同步上线。<\/li><li style=\"text-align: left;\">\u200b\u200b蜜雪冰城联动皮肤\u200b\u200b\u200b\u200b钟馗联名皮肤\u200b\u200b：以蜜雪冰城红白配色为主题，武器变为黄色冰淇淋造型，预计为史诗限定品质，附带小兵典藏皮肤。<\/li><li style=\"text-align: left;\">\u200b\u200b免费福利皮肤\u200b\u200b\u200b\u200b黄忠-百相守梦\u200b\u200b：史诗品质，通过签到和完成对局任务免费获取，活动持续至8月13日。\u200b\u200b蔷薇珍宝阁开放\u200b\u200b：可兑换嬴政-优雅恋人等限定皮肤，需150蔷薇之心（活动赠送25个）。<\/li><\/ol><hr/><p><br><\/p><h3 style=\"text-align: start;\">\u200b<strong>\u200b二、英雄与玩法调整\u200b<\/strong>\u200b<\/h3><ol><li style=\"text-align: left;\">\u200b\u200b司马懿重做\u200b\u200b能量条改为蓝条机制，新增\u201c幽影之力\u201d强化普攻真伤，大招被打断返还90%冷却，生存和输出能力提升。其他调整：戈娅攻速收益降低，程咬金被动攻击力加成翻倍，夏侯惇二技能强化普攻持续时间延长。<\/li><li style=\"text-align: left;\">\u200b\u200b新增限时装备与技能\u200b\u200b\u200b\u200b四大神装\u200b\u200b：如隐身装备\u201c玄铁·古锭\u201d、位移装备\u201c雌雄·双股\u201d，仅在7月31日-8月28日生效。\u200b\u200b阵营限时技能\u200b\u200b：蜀国\u201c木牛流马\u201d随机补给、吴国\u201c东风火船\u201d群体伤害，每日限用3次。<\/li><\/ol><hr/><p><br><\/p><h3 style=\"text-align: start;\">\u200b<strong>\u200b三、版本活动与福利\u200b<\/strong>\u200b<\/h3><ol><li style=\"text-align: left;\">\u200b\u200b夏日盛典第二弹\u200b\u200b登录送元流之子时装、每日充值活动返利，碎片商城中新增阿轲-暗夜猫娘等5款史诗皮肤。\u200b\u200b神秘商店\u200b\u200b：李白-诗剑行传说皮肤首次5折（844点券）。<\/li><li style=\"text-align: left;\">\u200b\u200b十周年预热\u200b\u200b组队集卡活动可兑换百里守约-特工魅影等史诗皮肤，排名奖励含现金红包。<\/li><\/ol><hr/><p><br><\/p><h3 style=\"text-align: start;\">\u200b<strong>\u200b四、未来动态前瞻\u200b<\/strong>\u200b<\/h3><ul><li style=\"text-align: left;\">\u200b<strong>\u200b敦煌与马年限定\u200b<\/strong>\u200b：西施-遇见青鸾（传说）、苍狼主题麒麟皮肤（2026年春节）。<\/li><li style=\"text-align: left;\">\u200b<strong>\u200b吕布典藏皮肤\u200b<\/strong>\u200b：8月下旬上线，双形态设计，需荣耀水晶兑换。<\/li><\/ul>","is_highlighted":0,"status":1,"created_time":"2025-07-30T16:19:44+08:00","updated_time":"2025-07-31T16:23:20+08:00","delete_time":"0001-01-01T00:00:00Z"}
     */

    private ArticlesDTO articles;

    public static ArticlesDetailsBean objectFromData(String str) {

        return new Gson().fromJson(str, ArticlesDetailsBean.class);
    }

    public ArticlesDTO getArticles() {
        return articles;
    }

    public void setArticles(ArticlesDTO articles) {
        this.articles = articles;
    }

    public static class ArticlesDTO {
        /**
         * id : 1012
         * ArticlesCategory : {"id":0,"name":"","sort_order":0,"status":0,"created_time":"0001-01-01T00:00:00Z","updated_time":"0001-01-01T00:00:00Z","delete_time":"0001-01-01T00:00:00Z"}
         * category_id : 1018
         * user_id : 0
         * title : 王者荣耀近期新皮肤与联动活动​
         * cover_url : https://taohao8.oss-cn-beijing.aliyuncs.com/webtaohao8/home/%E9%BB%98%E8%AE%A4%E5%9B%BE/defaultGoods.png
         * author : kke
         * content : <h3 style="text-align: start;"><strong>一、新皮肤与联动活动​</strong></h3><ol><li style="text-align: left;">​​山海经联动系列皮肤上线​​​​诸葛亮-天机白泽​​：无双限定品质，通过祈愿活动抽取（保底约4000点券），大招特效融合白泽神兽虚影，同步返场韩信-群星魔术团等限定皮肤。​​朵莉亚-幻珠鲛人​​：史诗限定皮肤，首周490点券含个性按键，技能特效以粉色鲛人主题为主，可通过绑定点券购买。​​司马懿-九山相柳​​：传说限定皮肤（首周1430点券），大招召唤九头蛇特效，因英雄重做同步上线。</li><li style="text-align: left;">​​蜜雪冰城联动皮肤​​​​钟馗联名皮肤​​：以蜜雪冰城红白配色为主题，武器变为黄色冰淇淋造型，预计为史诗限定品质，附带小兵典藏皮肤。</li><li style="text-align: left;">​​免费福利皮肤​​​​黄忠-百相守梦​​：史诗品质，通过签到和完成对局任务免费获取，活动持续至8月13日。​​蔷薇珍宝阁开放​​：可兑换嬴政-优雅恋人等限定皮肤，需150蔷薇之心（活动赠送25个）。</li></ol><hr/><p><br></p><h3 style="text-align: start;">​<strong>​二、英雄与玩法调整​</strong>​</h3><ol><li style="text-align: left;">​​司马懿重做​​能量条改为蓝条机制，新增“幽影之力”强化普攻真伤，大招被打断返还90%冷却，生存和输出能力提升。其他调整：戈娅攻速收益降低，程咬金被动攻击力加成翻倍，夏侯惇二技能强化普攻持续时间延长。</li><li style="text-align: left;">​​新增限时装备与技能​​​​四大神装​​：如隐身装备“玄铁·古锭”、位移装备“雌雄·双股”，仅在7月31日-8月28日生效。​​阵营限时技能​​：蜀国“木牛流马”随机补给、吴国“东风火船”群体伤害，每日限用3次。</li></ol><hr/><p><br></p><h3 style="text-align: start;">​<strong>​三、版本活动与福利​</strong>​</h3><ol><li style="text-align: left;">​​夏日盛典第二弹​​登录送元流之子时装、每日充值活动返利，碎片商城中新增阿轲-暗夜猫娘等5款史诗皮肤。​​神秘商店​​：李白-诗剑行传说皮肤首次5折（844点券）。</li><li style="text-align: left;">​​十周年预热​​组队集卡活动可兑换百里守约-特工魅影等史诗皮肤，排名奖励含现金红包。</li></ol><hr/><p><br></p><h3 style="text-align: start;">​<strong>​四、未来动态前瞻​</strong>​</h3><ul><li style="text-align: left;">​<strong>​敦煌与马年限定​</strong>​：西施-遇见青鸾（传说）、苍狼主题麒麟皮肤（2026年春节）。</li><li style="text-align: left;">​<strong>​吕布典藏皮肤​</strong>​：8月下旬上线，双形态设计，需荣耀水晶兑换。</li></ul>
         * is_highlighted : 0
         * status : 1
         * created_time : 2025-07-30T16:19:44+08:00
         * updated_time : 2025-07-31T16:23:20+08:00
         * delete_time : 0001-01-01T00:00:00Z
         */

        private Integer id;
        private ArticlesCategoryDTO ArticlesCategory;
        private Integer category_id;
        private Integer user_id;
        private String title;
        private String cover_url;
        private String author;
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

        public Integer getUser_id() {
            return user_id;
        }

        public void setUser_id(Integer user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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
