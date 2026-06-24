package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntelligentTipsMessageIBean extends TUIMessageBean {

    public static class ButtonBean {
        private String text;
        private String color;
        private UrlBean url;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
        public UrlBean getUrl() { return url; }
        public void setUrl(UrlBean url) { this.url = url; }
    }

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
    private List<IntelligentTipsMessageHBean.ContentJsonBean> contentJson;
    private ButtonBean button;
    private String im_buyer_idViewHolder;

    public void setIntelligentData(String jsonStr, String im_buyer_idViewHolder) {
        Gson gson = new Gson();
        try {
            Map<String, Object> dataMap = gson.fromJson(jsonStr, Map.class);
            this.intelligentType = ((Number) dataMap.get("intelligentType")).intValue();
            this.title = (String) dataMap.get("title");
            this.content = (String) dataMap.get("content");
            this.im_buyer_idViewHolder = im_buyer_idViewHolder;

            // 解析contentJson数组
            if (dataMap.containsKey("contentJson")) {
                List<Map<String, Object>> contentJsonList = (List<Map<String, Object>>) dataMap.get("contentJson");
                this.contentJson = new ArrayList<>();
                for (Map<String, Object> itemMap : contentJsonList) {
                    IntelligentTipsMessageHBean.ContentJsonBean bean = new IntelligentTipsMessageHBean.ContentJsonBean();
                    bean.setKey((String) itemMap.get("key"));
                    bean.setValue((String) itemMap.get("value"));
                    this.contentJson.add(bean);
                }
            }

            // 解析button对象
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
    public List<IntelligentTipsMessageHBean.ContentJsonBean> getContentJson() { return contentJson; }
    public ButtonBean getButton() { return button; }

    public String getIm_buyer_idViewHolder() {
        return im_buyer_idViewHolder;
    }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {
        // 原有处理逻辑保持不变
    }
}
