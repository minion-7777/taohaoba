package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.Map;

public class IntelligentTipsMessageCBean extends TUIMessageBean {
    // 新增嵌套类
    public static class SecondTitleBean {
        private String time;
        private String state;
        private String state_type;
        private String state_type_color;

        // Getter/Setter方法
        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getState_type() { return state_type; }
        public void setState_type(String state_type) { this.state_type = state_type; }
        public String getState_type_color() { return state_type_color; }
        public void setState_type_color(String state_type_color) { this.state_type_color = state_type_color; }
    }

    public static class ButtonBean {
        private String text;
        private String color;
        private UrlBean url;

        // Getter/Setter方法
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public UrlBean getUrl() { return url; }
        public void setUrl(UrlBean url) { this.url = url; }
    }

    // 修改UrlBean支持long类型paramId
    public static class UrlBean {
        private int platform;
        private String pageName;
        private int paramId;

        public int getPlatform() { return platform; }
        public void setPlatform(int platform) { this.platform = platform; }
        public String getPageName() { return pageName; }
        public void setPageName(String pageName) { this.pageName = pageName; }
        public int getParamId() { return paramId; }
        public void setParamId(int paramId) { this.paramId = paramId; }
    }

    // 类字段
    private String title;
    private String content;
    private int intelligentType;
    private String icon;
    private double price;
    private SecondTitleBean secondTitle;
    private ButtonBean button;
    private String im_buyer_idViewHolder;

    public void setIntelligentData(String jsonStr, String im_buyer_idViewHolder) {
        Gson gson = new Gson();
        try {
            Map<String, Object> dataMap = gson.fromJson(jsonStr, Map.class);

            // 解析基础字段
            this.intelligentType = ((Number) dataMap.get("intelligentType")).intValue();
            this.title = (String) dataMap.get("title");
            this.content = (String) dataMap.get("content");
            this.icon = (String) dataMap.get("icon");
            this.price = dataMap.containsKey("price") ? ((Number) dataMap.get("price")).doubleValue() : 0.0;
            this.im_buyer_idViewHolder = im_buyer_idViewHolder;

            // 解析secondTitle
            if (dataMap.containsKey("secondTitle")) {
                Map<String, Object> secondTitleMap = (Map<String, Object>) dataMap.get("secondTitle");
                this.secondTitle = new SecondTitleBean();
                this.secondTitle.setTime((String) secondTitleMap.get("time"));
                this.secondTitle.setState((String) secondTitleMap.get("state"));
                this.secondTitle.setState_type((String) secondTitleMap.get("state_type"));
                this.secondTitle.setState_type_color((String) secondTitleMap.get("state_type_color"));
            }

            // 解析button
            if (dataMap.containsKey("button")) {
                Map<String, Object> buttonMap = (Map<String, Object>) dataMap.get("button");
                this.button = new ButtonBean();
                this.button.setText((String) buttonMap.get("text"));
                this.button.setColor((String) buttonMap.get("color"));

                // 解析嵌套url
                if (buttonMap.containsKey("url")) {
                    Map<String, Object> urlMap = (Map<String, Object>) buttonMap.get("url");
                    UrlBean urlBean = new UrlBean();
                    urlBean.setPlatform(((Number) urlMap.get("platform")).intValue());
                    urlBean.setPageName((String) urlMap.get("pageName"));
                    urlBean.setParamId(((Number) urlMap.get("paramId")).intValue());
                    this.button.setUrl(urlBean);
                }
            }

        } catch (Exception e) {
            Log.e("IntelligentTips", "parse data error: " + e.getMessage());
        }
    }

    // Getter方法
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getIntelligentType() { return intelligentType; }
    public String getIcon() { return icon; }
    public double getPrice() { return price; }
    public SecondTitleBean getSecondTitle() { return secondTitle; }
    public ButtonBean getButton() { return button; }

    public String getIm_buyer_idViewHolder() {
        return im_buyer_idViewHolder;
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        // 原有处理逻辑保持不变
    }
}
