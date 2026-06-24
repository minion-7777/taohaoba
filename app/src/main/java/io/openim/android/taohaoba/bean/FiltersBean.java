package io.openim.android.taohaoba.bean;

public class FiltersBean {
    private String id;//筛选id
    private String logic;//可选参数，标识多选时是否需要全部满足还是满足一个（默认满足一个） and全部 or一个
    private String value;//选择值

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
