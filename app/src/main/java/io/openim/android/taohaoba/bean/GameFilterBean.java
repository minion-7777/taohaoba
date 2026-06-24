package io.openim.android.taohaoba.bean;

import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArrayFast;

import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

public class GameFilterBean {

    private List<ListDTO> list;
    private List<NameListDTO> nameList;

    public List<ListDTO> getList() {
        return list;
    }

    public void setList(List<ListDTO> list) {
        this.list = list;
    }

    public List<NameListDTO> getNameList() {
        return nameList;
    }

    public void setNameList(List<NameListDTO> nameList) {
        this.nameList = nameList;
    }

    public static class NameListDTO{
        private String name;
        private boolean isSelected;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }

    public static class ListDTO implements MultiItemEntity {
        /**
         * id : 4
         * game_id : 18
         * pid : 0
         * name : 名称
         * key :
         * sort : 10
         * is_show : 1
         * field_type : 1
         * field_value :
         * max_value : 0
         * data_source : 2
         * logic_type : 1
         * is_del : 0
         * created_time : 2025-10-21T13:52:22+08:00
         * updated_time : 2025-10-21T16:10:02+08:00
         * children : []
         */
        public static final int TYPE_ONE = 3;//单选
        public static final int TYPE_TWO = 4;//多选
        public static final int TYPE_THREE = 6;//价格区间
        public static final int TYPE_FOUR = 7;//分类字段
        private List<NameListDTO> nameList;
        private Integer id;
        private Integer game_id;
        private Integer pid;//父id
        private String name;
        private String key;
        private Integer sort;
        private Integer is_show;
        private Integer field_type;//字段类型 1:文本 2:数字 3:单选 4:多选 5:布尔 6:价格区间 7:分类字段
        private String field_value;//字段值(多个用英文逗号隔开)
        private Integer max_value;//最大值（文本最高长度，数字最大值，多选最多选择）
        private Integer data_source;//数据来源 1:区服 2:价格 3:是否二次实名 4:商品属性 5:动态SQL
        private Integer logic_type;
        private Integer is_del;
        private String created_time;
        private String updated_time;
        private List<ListDTO> children;
        private boolean isAllSelected;//是否全选
        private boolean isChecked;//是否选中
        private String tag = "and";//and or
        private String maxValue;//最大值（文本最高长度，数字最大值，多选最多选择）
        private String minValue;//最小值（文本最低长度，数字最小值，多选最少选择）
        private boolean isExpanded;//是否展开
        private int position;//当前选中的位置

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        public String getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(String maxValue) {
            this.maxValue = maxValue;
        }

        public String getMinValue() {
            return minValue;
        }

        public void setMinValue(String minValue) {
            this.minValue = minValue;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public boolean isAllSelected() {
            return isAllSelected;
        }

        public void setAllSelected(boolean allSelected) {
            isAllSelected = allSelected;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public List<NameListDTO> getNameList() {
            return nameList;
        }

        public void setNameList(List<NameListDTO> nameList) {
            this.nameList = nameList;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getGame_id() {
            return game_id;
        }

        public void setGame_id(Integer game_id) {
            this.game_id = game_id;
        }

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Integer getIs_show() {
            return is_show;
        }

        public void setIs_show(Integer is_show) {
            this.is_show = is_show;
        }

        public Integer getField_type() {
            return field_type;
        }

        public void setField_type(Integer field_type) {
            this.field_type = field_type;
        }

        public String getField_value() {
            return field_value;
        }

        public void setField_value(String field_value) {
            this.field_value = field_value;
            // 新增：当 field_value 更新时，同步更新 nameList
            this.nameList = TextUtils.isEmpty(field_value) ? new ArrayList<>() : splitToNameListDTO(field_value);
        }

        public Integer getMax_value() {
            return max_value;
        }

        public void setMax_value(Integer max_value) {
            this.max_value = max_value;
        }

        public Integer getData_source() {
            return data_source;
        }

        public void setData_source(Integer data_source) {
            this.data_source = data_source;
        }

        public Integer getLogic_type() {
            return logic_type;
        }

        public void setLogic_type(Integer logic_type) {
            this.logic_type = logic_type;
        }

        public Integer getIs_del() {
            return is_del;
        }

        public void setIs_del(Integer is_del) {
            this.is_del = is_del;
        }

        public String getCreated_time() {
            return created_time;
        }

        public void setCreated_time(String created_time) {
            this.created_time = created_time;
        }

        public String getUpdated_time() {
            return updated_time;
        }

        public void setUpdated_time(String updated_time) {
            this.updated_time = updated_time;
        }

        public List<ListDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ListDTO> children) {
            this.children = children;
        }

        @Override
        public int getItemType() {
            return field_type;
        }
    }

    /**
     * 将逗号分隔的字符串转换为GameFilterBean.NameListDTO列表
     * @param input 逗号分隔的字符串
     * @return NameListDTO对象列表
     */
    public static List<GameFilterBean.NameListDTO> splitToNameListDTO(String input) {
        List<GameFilterBean.NameListDTO> resultList = new ArrayList<>();
        if (input == null) {
            return resultList;
        }

        // 使用现有方法分割字符串，自动处理空值和空白字符串
        String[] items = convertToArrayFast(input);
        for (String item : items) {
            GameFilterBean.NameListDTO dto = new GameFilterBean.NameListDTO();
            dto.setName(item);
            dto.setSelected(false); // 默认未选中状态
            resultList.add(dto);
        }
        return resultList;
    }
}
