package io.openim.android.taohaoba.bean;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ValueDTOBean implements Parcelable {

    private String key;
    private int key_sort;
    private String value;
    private int is_required;
    private int is_sort;
    private int is_show;
    private int type;
    private int sort_type;

    public ValueDTOBean(String key, int key_sort, String value, int is_required, int is_sort, int is_show, int type, int sort_type) {
        this.key = key;
        this.key_sort = key_sort;
        this.value = value;
        this.is_required = is_required;
        this.is_sort = is_sort;
        this.is_show = is_show;
        this.type = type;
        this.sort_type = sort_type;
    }

    public ValueDTOBean() {
    }

    protected ValueDTOBean(Parcel in) {
        key = in.readString();
        key_sort = in.readInt();
        value = in.readString();
        is_required = in.readInt();
        is_sort = in.readInt();
        is_show = in.readInt();
        type = in.readInt();
        sort_type = in.readInt();
    }

    public static final Creator<ValueDTOBean> CREATOR = new Creator<ValueDTOBean>() {
        @Override
        public ValueDTOBean createFromParcel(Parcel in) {
            return new ValueDTOBean(in);
        }

        @Override
        public ValueDTOBean[] newArray(int size) {
            return new ValueDTOBean[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getKey_sort() {
        return key_sort;
    }

    public void setKey_sort(int key_sort) {
        this.key_sort = key_sort;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIs_required() {
        return is_required;
    }

    public void setIs_required(int is_required) {
        this.is_required = is_required;
    }

    public int getIs_sort() {
        return is_sort;
    }

    public void setIs_sort(int is_sort) {
        this.is_sort = is_sort;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort_type() {
        return sort_type;
    }

    public void setSort_type(int sort_type) {
        this.sort_type = sort_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeInt(key_sort);
        dest.writeString(value);
        dest.writeInt(is_required);
        dest.writeInt(is_sort);
        dest.writeInt(is_show);
        dest.writeInt(type);
        dest.writeInt(sort_type);
    }
}
