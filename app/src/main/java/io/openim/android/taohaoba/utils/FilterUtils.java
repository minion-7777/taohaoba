package io.openim.android.taohaoba.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.bean.GameFilterBean;

/**
 * 筛选相关工具类
 */
public class FilterUtils {

    /**
     * 检查 NameListDTO 列表中是否存在选中项（isSelected=true）
     * @param nameList 选项列表（直接传入 listDTO.getNameList()）
     * @return true：存在选中项；false：无选中项或列表为空/null
     */
    public static boolean hasSelectedItems(List<GameFilterBean.NameListDTO> nameList) {
        // 空安全检查：列表为 null 或空时直接返回 false
        if (nameList == null || nameList.isEmpty()) return false;

        // 遍历列表检查是否有选中项
        for (GameFilterBean.NameListDTO item : nameList) {
            if (item != null && item.isSelected()) {
                return true; // 找到选中项，立即返回 true
            }
        }
        return false; // 无选中项，返回 false
    }

    /**
     * 提取选中项的名称并拼接为逗号分隔的字符串（如"测试1,测试2,测试3"）
     * @param nameList 选项列表（直接传入 listDTO.getNameList()）
     * @return 选中名称的逗号分隔字符串；无选中项时返回空字符串
     */
    public static String getSelectedNamesAsString(List<GameFilterBean.NameListDTO> nameList) {
        // 空安全检查：列表为 null 或空时返回空字符串
        if (nameList == null || nameList.isEmpty()) {
            return "";
        }

        // 收集选中项的名称
        List<String> selectedNames = new ArrayList<>();
        for (GameFilterBean.NameListDTO item : nameList) {
            // 跳过 null 项，仅添加选中状态的名称
            if (item != null && item.isSelected()) {
                selectedNames.add(item.getName());
            }
        }

        // 用逗号拼接名称列表（无选中项时返回空字符串）
        return String.join(",", selectedNames);
    }

    /**
     * 检查价格区间是否有输入值
     * @param listDTO 价格区间筛选项（field_type=6）
     * @return true：存在有效价格区间；false：无输入值
     */
    public static boolean hasPriceRange(GameFilterBean.ListDTO listDTO) {
        if (listDTO == null) return false;
        return !TextUtils.isEmpty(listDTO.getMinValue()) || !TextUtils.isEmpty(listDTO.getMaxValue());
    }
}
