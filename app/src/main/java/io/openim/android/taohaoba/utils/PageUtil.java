package io.openim.android.taohaoba.utils;

public class PageUtil {

    /**
     * 根据总条数获取总页码数
     * @param count
     * @return
     */
    public static int calculateTotalPages(int count) {
        if (count <= 0) return 0;
        int pageSize = 10;
        return (count + pageSize - 1) / pageSize;
    }
}
