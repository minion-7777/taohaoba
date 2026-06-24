package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.Map;

public class IntelligentTipsMessageEBean extends TUIMessageBean {

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {

    }

    // 新增嵌套类
    public static class TitleBean {
        private String text;
        private String color;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public static class ButtonBean {
        private String text;
        private String color;
        private UrlBean url;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public UrlBean getUrl() {
            return url;
        }

        public void setUrl(UrlBean url) {
            this.url = url;
        }
    }

    public static class UrlBean {
        private int platform;
        private String pageName;
        private ParamIdBean paramId;

        public int getPlatform() {
            return platform;
        }

        public void setPlatform(int platform) {
            this.platform = platform;
        }

        public String getPageName() {
            return pageName;
        }

        public void setPageName(String pageName) {
            this.pageName = pageName;
        }

        public ParamIdBean getParamId() {
            return paramId;
        }

        public void setParamId(ParamIdBean paramId) {
            this.paramId = paramId;
        }
    }

    public static class ParamIdBean {
        private int game_id;
        private int goods_id;
        private int order_id;
        private int order_status;
        private String order_status_type;
        private int type;
        private int paramId;
        private int buyer_id;
        private int seller_id;
        private int pattern_id;
        private String game_name;

        public int getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(int buyer_id) {
            this.buyer_id = buyer_id;
        }

        public int getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(int seller_id) {
            this.seller_id = seller_id;
        }

        public int getPattern_id() {
            return pattern_id;
        }

        public void setPattern_id(int pattern_id) {
            this.pattern_id = pattern_id;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public int getParamId() {
            return paramId;
        }

        public void setParamId(int paramId) {
            this.paramId = paramId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getGame_id() {
            return game_id;
        }

        public void setGame_id(int game_id) {
            this.game_id = game_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_status_type() {
            return order_status_type;
        }

        public void setOrder_status_type(String order_status_type) {
            this.order_status_type = order_status_type;
        }
    }

    private String content;
    private int intelligentType;
    private TitleBean title;
    private ButtonBean button;
    private int cardType5ContentType;
    private String im_seller_idViewHolder;
    private String im_buyer_idViewHolder;
    private String im_owner_idViewHolder;

    public void setIntelligentData(String jsonStr, String im_seller_idViewHolder, String im_buyer_idViewHolder, String im_owner_idViewHolder) {
        Gson gson = new Gson();
        try {
            Map<String, Object> dataMap = gson.fromJson(jsonStr, Map.class);

            // 基础字段
            this.intelligentType = ((Double) dataMap.get("intelligentType")).intValue();
            this.content = (String) dataMap.get("content");
            // 特殊字段
            this.cardType5ContentType = ((Double) dataMap.get("card_type_5_content_type")).intValue();
            this.im_seller_idViewHolder = im_seller_idViewHolder;
            this.im_buyer_idViewHolder = im_buyer_idViewHolder;
            this.im_owner_idViewHolder = im_owner_idViewHolder;

            // 嵌套title对象
            Map<String,Object> titleMap = (Map<String,Object>) dataMap.get("title");
            this.title = new TitleBean();
            this.title.setText((String) titleMap.get("text"));
            this.title.setColor((String) titleMap.get("color"));

            // 嵌套button对象
            Map<String,Object> buttonMap = (Map<String,Object>) dataMap.get("button");
            this.button = new ButtonBean();
            this.button.setText((String) buttonMap.get("text"));
            this.button.setColor((String) buttonMap.get("color"));

            // 嵌套url对象
            Map<String,Object> urlMap = (Map<String,Object>) buttonMap.get("url");
            UrlBean urlBean = new UrlBean();
            urlBean.setPlatform(((Number) urlMap.get("platform")).intValue());
            urlBean.setPageName((String) urlMap.get("pageName"));

            // 嵌套paramId对象
            Object paramIdObj = urlMap.get("paramId");
            ParamIdBean paramIdBean = new ParamIdBean();
            if (paramIdObj instanceof Map) {
                // 处理对象形式的 paramId
                Map<String, Object> paramMap = (Map<String, Object>) paramIdObj;
                paramIdBean.setGame_id(paramMap.get("game_id") != null ? ((Number) paramMap.get("game_id")).intValue() : 0);
                paramIdBean.setGoods_id(paramMap.get("goods_id") != null ? ((Number) paramMap.get("goods_id")).intValue() : 0);
                paramIdBean.setOrder_id(paramMap.get("order_id") != null ? ((Number) paramMap.get("order_id")).intValue() : 0);
                paramIdBean.setType(paramMap.get("type") != null ? ((Number) paramMap.get("type")).intValue() : 0);
                paramIdBean.setOrder_status(paramMap.get("order_status") != null ? ((Number) paramMap.get("order_status")).intValue() : 0);
                paramIdBean.setOrder_status_type(paramMap.get("order_status_type") != null ? paramMap.get("order_status_type").toString() : "");
                paramIdBean.setBuyer_id(paramMap.get("buyer_id") != null ? ((Number) paramMap.get("buyer_id")).intValue() : 0);
                paramIdBean.setGame_name(paramMap.get("game_name") != null ? paramMap.get("game_name").toString() : "");
                paramIdBean.setPattern_id(paramMap.get("pattern_id") != null ? ((Number) paramMap.get("pattern_id")).intValue() : 0);
                paramIdBean.setSeller_id(paramMap.get("seller_id") != null ? ((Number) paramMap.get("seller_id")).intValue() : 0);
            } else if (paramIdObj instanceof Number) {
                // 处理纯数字形式的 paramId（假设赋值给 ParamId）
                paramIdBean.setParamId(((Number) paramIdObj).intValue());
            }
            urlBean.setParamId(paramIdBean);

            this.button.setUrl(urlBean);



        } catch (Exception e) {
            Log.e("IntelligentTips", "parse data error: " + e.getMessage());
        }
    }

    public String getContent() { return content; }
    public int getIntelligentType() { return intelligentType; }
    public TitleBean getTitle() { return title; }
    public ButtonBean getButton() { return button; }


    public int getCardType5ContentType() { return cardType5ContentType; }

    public String getIm_seller_idViewHolder() {
        return im_seller_idViewHolder;
    }

    public String getIm_buyer_idViewHolder() {
        return im_buyer_idViewHolder;
    }

    public String getIm_owner_idViewHolder() {
        return im_owner_idViewHolder;
    }
}
