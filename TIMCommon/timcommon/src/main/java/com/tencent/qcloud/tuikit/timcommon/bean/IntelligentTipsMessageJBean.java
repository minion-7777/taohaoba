package com.tencent.qcloud.tuikit.timcommon.bean;

import android.util.Log;

import com.google.gson.Gson;
import com.tencent.imsdk.v2.V2TIMMessage;

import java.util.Map;

public class IntelligentTipsMessageJBean extends TUIMessageBean {
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
        } catch (Exception e) {
            Log.e("IntelligentTips", "parse data error: " + e.getMessage());
        }
    }

    // Getter 方法
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public int getIntelligentType() { return intelligentType; }

    @Override
    public void onProcessMessage(V2TIMMessage v2TIMMessage) {

    }
}
