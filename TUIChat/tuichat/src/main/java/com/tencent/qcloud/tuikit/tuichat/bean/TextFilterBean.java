package com.tencent.qcloud.tuikit.tuichat.bean;

public class TextFilterBean {

    private String filtered_text;// 过滤后的文本
    private Boolean has_sensitive;// 是否包含敏感词
    private String task_id;// 任务ID
    private Boolean valid;// 是否通过  发送图片

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getFiltered_text() {
        return filtered_text;
    }

    public void setFiltered_text(String filtered_text) {
        this.filtered_text = filtered_text;
    }

    public Boolean isHas_sensitive() {
        return has_sensitive;
    }

    public void setHas_sensitive(Boolean has_sensitive) {
        this.has_sensitive = has_sensitive;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
