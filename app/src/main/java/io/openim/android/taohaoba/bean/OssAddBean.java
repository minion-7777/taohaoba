package io.openim.android.taohaoba.bean;

public class OssAddBean {
    private int index;
    private String paths; //文件路径
    private String ide;   //标识
    private String type; //类型 TP SP YY

    public OssAddBean(int index,String paths, String ide, String type) {
        this.index = index;
        this.paths = paths;
        this.ide = ide;
        this.type = type;
    }

    public OssAddBean(String paths, String ide, String type) {
        this.paths = paths;
        this.ide = ide;
        this.type = type;
    }

    public OssAddBean(String paths, String type) {
        this.paths = paths;
        this.type = type;
    }

    public OssAddBean() {

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public OssAddBean(String paths) {
        this.paths = paths;
    }

    public String getIde() {
        return ide;
    }

    public void setIde(String ide) {
        this.ide = ide;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
}
