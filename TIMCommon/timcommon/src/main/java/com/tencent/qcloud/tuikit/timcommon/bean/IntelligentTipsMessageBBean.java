package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.Map;

public class IntelligentTipsMessageBBean extends TUIMessageBean {
    private String title;
    private String content;
    private int intelligentType;
    private String icon;
    private double price;
    private UrlBean url;
    public static class UrlBean {
        private int platform;
        private String pageName;
        private int paramId;

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

        public int getParamId() {
            return paramId;
        }

        public void setParamId(int paramId) {
            this.paramId = paramId;
        }
    }


    // 解析数据方法
    public void setIntelligentData(String jsonStr) {
        Gson gson = new Gson();
        try {
            Map<String, Object> dataMap = gson.fromJson(jsonStr, Map.class);
            this.intelligentType = ((Double) dataMap.get("intelligentType")).intValue();
            this.title = (String) dataMap.get("title");
            this.content = (String) dataMap.get("content");
            this.icon = (String) dataMap.get("icon");
            this.price = dataMap.containsKey("price") ? ((Number) dataMap.get("price")).doubleValue() : 0.0;

            // 修改url解析逻辑
            Map<String,Object> urlMap = (Map<String,Object>) dataMap.get("url");
            this.url = new UrlBean();
            this.url.setPlatform(((Number) urlMap.get("platform")).intValue());
            this.url.setPageName((String) urlMap.get("pageName"));
            this.url.setParamId(((Number) urlMap.get("paramId")).intValue());

        } catch (Exception e) {
            Log.e("IntelligentTips", "parse data error: " + e.getMessage());
        }
    }

    // Getter 方法
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getIntelligentType() { return intelligentType; }

    public UrlBean getUrl() {
        return url;
    }

    public String getIcon() {
        return icon;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {

    }
}
