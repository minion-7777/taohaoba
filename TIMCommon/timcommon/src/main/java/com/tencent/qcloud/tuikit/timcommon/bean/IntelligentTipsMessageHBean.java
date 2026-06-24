package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IntelligentTipsMessageHBean extends TUIMessageBean {

    public static class ContentJsonBean {
        private String key;
        private String value;

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }

    // 在类字段中添加
    private List<ContentJsonBean> contentJson;
    private String title;
    private String content;
    private int intelligentType;

    // 解析数据方法
    public void setIntelligentData(String jsonStr) {
        Gson gson = new Gson();
        try {
            Map<String, Object> dataMap = gson.fromJson(jsonStr, Map.class);
            this.intelligentType = ((Double) dataMap.get("intelligentType")).intValue();
            this.title = (String) dataMap.get("title");
            this.content = (String) dataMap.get("content");

            // 解析contentJson数组
            if (dataMap.containsKey("contentJson")) {
                List<Map<String, Object>> contentJsonList = (List<Map<String, Object>>) dataMap.get("contentJson");
                this.contentJson = new ArrayList<>();
                for (Map<String, Object> itemMap : contentJsonList) {
                    ContentJsonBean bean = new ContentJsonBean();
                    bean.setKey((String) itemMap.get("key"));
                    bean.setValue((String) itemMap.get("value"));
                    this.contentJson.add(bean);
                }
            }
        } catch (Exception e) {
            Log.e("IntelligentTips", "parse data error: " + e.getMessage());
        }
    }

    // Getter 方法
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getIntelligentType() { return intelligentType; }
    public List<ContentJsonBean> getContentJson() { return contentJson; }


    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {

    }
}
