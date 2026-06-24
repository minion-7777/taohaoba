package io.openim.android.taohaoba.ui.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.luck.picture.lib.thread.PictureThreadUtils.runOnUiThread;
import static io.openim.android.taohaoba.bean.GameFilterBean.ListDTO.TYPE_FOUR;
import static io.openim.android.taohaoba.bean.GameFilterBean.ListDTO.TYPE_ONE;
import static io.openim.android.taohaoba.bean.GameFilterBean.ListDTO.TYPE_THREE;
import static io.openim.android.taohaoba.bean.GameFilterBean.ListDTO.TYPE_TWO;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameFilterBean;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;

public class GameFilterAdapter extends BaseMultiItemQuickAdapter<GameFilterBean.ListDTO, BaseViewHolder> {

    private int spacingInPx;

    // 构造方法中注册布局类型
    public GameFilterAdapter(List<GameFilterBean.ListDTO> data, int spacingInPx) {
        super(data);
        this.spacingInPx = spacingInPx;
        addItemType(TYPE_ONE, R.layout.item_game_filter2);
        addItemType(TYPE_TWO, R.layout.item_game_filter2);
        addItemType(TYPE_THREE, R.layout.item_game_filter3);
        addItemType(TYPE_FOUR, R.layout.item_game_filter4);
    }

    @Override
    protected void convert(BaseViewHolder helper, GameFilterBean.ListDTO item) {
        helper.setIsRecyclable(false);
        switch (helper.getItemViewType()) {
            case TYPE_ONE:
            case TYPE_TWO:
                //单选or多选
                RecyclerView rc_recycler = helper.getView(R.id.rc_recycler);
                TextView tvExpand = helper.getView(R.id.tv_expand);
                List<GameFilterBean.NameListDTO> nameListDTOList1 = new ArrayList<>();

                nameListDTOList1.clear();
                if (item.getNameList().size() > 9 && !item.isExpanded()){
                    nameListDTOList1.addAll(item.getNameList().subList(0, 9));
                }else {
                    nameListDTOList1.addAll(item.getNameList());
                }

                BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter(R.layout.item_game_filter5, nameListDTOList1) {
                    @Override
                    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                        baseViewHolder.setIsRecyclable(false);
                        GameFilterBean.NameListDTO it = (GameFilterBean.NameListDTO) o;
                        baseViewHolder.setText(R.id.tv_name, it.getName());
                        baseViewHolder.setBackgroundResource(R.id.tv_name, it.isSelected() ? R.drawable.shape_radius8_564e40 : R.drawable.shape_radius8_3c3c3c);
                        baseViewHolder.setTextColor(R.id.tv_name, it.isSelected() ? baseViewHolder.itemView.getContext().getColor(R.color.color_EACA92) : baseViewHolder.itemView.getContext().getColor(R.color.color_D6D6D6));

                        baseViewHolder.itemView.setOnClickListener(v->{
                            if (item.getField_type() == TYPE_ONE){
                                //单选
                                boolean isCurrentlySelected = it.isSelected();
                                for (int i = 0; i < item.getNameList().size(); i++) {
                                    item.getNameList().get(i).setSelected(false);
                                }
                                it.setSelected(!isCurrentlySelected);
                            }else if (item.getField_type() == TYPE_TWO){
                                //多选
                                it.setSelected(!it.isSelected());
                            }
                            notifyDataSetChanged();
                        });
                    }
                };
                rc_recycler.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(), 3));
                // 添加间距装饰（包含边缘间距）
                rc_recycler.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPx, true));
                rc_recycler.setAdapter(baseQuickAdapter);

                tvExpand.setVisibility(item.getNameList().size() > 9 ? VISIBLE : GONE);

                tvExpand.setOnClickListener(v -> {
                    item.setExpanded(!item.isExpanded());
                    nameListDTOList1.clear();
                    if (item.isExpanded()){
                        tvExpand.setText("收起");
                        nameListDTOList1.addAll(item.getNameList());
                    }else {
                        tvExpand.setText("展开");
                        nameListDTOList1.addAll(item.getNameList().subList(0, 9));
                    }
                    baseQuickAdapter.notifyDataSetChanged();
                });

                break;
            case TYPE_THREE:
                //价格区间
                TextWatcher textWatcher = null;
                TextWatcher textWatcher1 = null;
                EditText et_min = helper.getView(R.id.et_min);
                EditText et_max = helper.getView(R.id.et_max);

                if (textWatcher != null) {
                    et_min.removeTextChangedListener(textWatcher);
                }
                et_min.setText(TextUtils.isEmpty(item.getMinValue()) ? "" : item.getMinValue());
                textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setMinValue(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                };
                et_min.addTextChangedListener(textWatcher);


                if (textWatcher1 != null) {
                    et_max.removeTextChangedListener(textWatcher1);
                }
                et_max.setText(TextUtils.isEmpty(item.getMaxValue()) ? "" : item.getMaxValue());
                textWatcher1 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setMaxValue(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                };
                et_max.addTextChangedListener(textWatcher1);
                break;
            case TYPE_FOUR:
                //分类字段
                RecyclerView rc_recycler1 = helper.getView(R.id.rc_recycler1);
                RecyclerView rc_recycler2 = helper.getView(R.id.rc_recycler2);
                TextView tvSelectAll = helper.getView(R.id.tv_SelectAll);
                RadioButton radioButton1 = helper.getView(R.id.radioButton1);
                RadioButton radioButton2 = helper.getView(R.id.radioButton2);
                TextView tvExpand2 = helper.getView(R.id.tv_expand2);
                List<GameFilterBean.NameListDTO> nameListDTOList = new ArrayList<>();

                BaseQuickAdapter baseQuickAdapter2 = new BaseQuickAdapter(R.layout.item_game_filter5, nameListDTOList) {
                    @Override
                    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                        baseViewHolder.setIsRecyclable(false); // 禁止此 ViewHolder 被回收复用
                        GameFilterBean.NameListDTO it = (GameFilterBean.NameListDTO) o;
                        baseViewHolder.setText(R.id.tv_name, it.getName());
                        baseViewHolder.setBackgroundResource(R.id.tv_name, it.isSelected() ? R.drawable.shape_radius8_564e40 : R.drawable.shape_radius8_3c3c3c);
                        baseViewHolder.setTextColor(R.id.tv_name, it.isSelected() ? baseViewHolder.itemView.getContext().getColor(R.color.color_EACA92) : baseViewHolder.itemView.getContext().getColor(R.color.color_D6D6D6));

                        baseViewHolder.itemView.setOnClickListener(v->{
                            //多选
                            it.setSelected(!it.isSelected());
                            rc_recycler2.post(() -> notifyDataSetChanged());

                            item.getChildren().get(item.getPosition()).setAllSelected(isAllItemsSelected(item.getChildren().get(item.getPosition()).getNameList()));
                            updateSelectAllIcon(tvSelectAll, item.getChildren().get(item.getPosition()).isAllSelected());
                        });
                    }
                };
                rc_recycler2.setLayoutManager(new GridLayoutManager(helper.itemView.getContext(), 3));
                rc_recycler2.addItemDecoration(new GridSpacingItemDecoration(3, spacingInPx, true));
                rc_recycler2.setAdapter(baseQuickAdapter2);

                BaseQuickAdapter baseQuickAdapter1 = new BaseQuickAdapter(R.layout.item_game_name_tab1, item.getChildren()) {
                    @Override
                    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                        baseViewHolder.setIsRecyclable(false); // 禁止此 ViewHolder 被回收复用
                        GameFilterBean.ListDTO it = (GameFilterBean.ListDTO) o;
                        baseViewHolder.setText(R.id.tv_name, it.getName());
                        baseViewHolder.setTextColor(R.id.tv_name, it.isChecked() ? baseViewHolder.itemView.getContext().getColor(R.color.color_EACA92) : baseViewHolder.itemView.getContext().getColor(R.color.white));
                        baseViewHolder.setVisible(R.id.view, it.isChecked());

                        baseViewHolder.itemView.setOnClickListener(v->{
                            item.setPosition(baseViewHolder.getLayoutPosition());
                            updateSelectAllIcon(tvSelectAll, item.getChildren().get(item.getPosition()).isAllSelected());
                            radioButton1.setChecked(item.getChildren().get(item.getPosition()).getTag().equals("and"));
                            radioButton2.setChecked(item.getChildren().get(item.getPosition()).getTag().equals("or"));

                            for (int i = 0; i < item.getChildren().size(); i++) {
                                item.getChildren().get(i).setChecked(false);
                            }
                            it.setChecked(true);
                            notifyDataSetChanged();
                            nameListDTOList.clear();
                            if (it.getNameList().size() > 9){
                                nameListDTOList.addAll(it.getNameList().subList(0, 9));
                            }else {
                                nameListDTOList.addAll(it.getNameList());
                            }

                            tvExpand2.setVisibility(it.getNameList().size() > 9 ? VISIBLE : GONE);
                            item.getChildren().get(item.getPosition()).setExpanded(false);
                            tvExpand2.setText("展开");

                            if (baseQuickAdapter2 != null)
                                baseQuickAdapter2.notifyDataSetChanged();
                        });
                    }
                };
                rc_recycler1.setLayoutManager(new LinearLayoutManager(helper.itemView.getContext(), LinearLayoutManager.HORIZONTAL , false));
                rc_recycler1.setAdapter(baseQuickAdapter1);

                //全选
                tvSelectAll.setOnClickListener(v->{
                    if (!item.getChildren().isEmpty()) {
                        item.getChildren().get(item.getPosition()).setAllSelected(!item.getChildren().get(item.getPosition()).isAllSelected());
                        updateSelectAllIcon(tvSelectAll, item.getChildren().get(item.getPosition()).isAllSelected());
                        for (int i = 0; i < item.getChildren().get(item.getPosition()).getNameList().size(); i++) {
                            item.getChildren().get(item.getPosition()).getNameList().get(i).setSelected(item.getChildren().get(item.getPosition()).isAllSelected());
                        }
                        if (baseQuickAdapter2 != null)
                            rc_recycler2.post(() -> baseQuickAdapter2.notifyDataSetChanged());
                    }
                });

                //默认选中第一个
                radioButton1.setOnClickListener(v->{
                    if (!item.getChildren().isEmpty()) {
                        item.getChildren().get(item.getPosition()).setTag("and");
                    }
                });

                radioButton2.setOnClickListener(v->{
                    if (!item.getChildren().isEmpty()) {
                        item.getChildren().get(item.getPosition()).setTag("or");
                    }
                });

                tvExpand2.setOnClickListener(v -> {
                    item.getChildren().get(item.getPosition()).setExpanded(!item.getChildren().get(item.getPosition()).isExpanded());
                    nameListDTOList.clear();
                    if (item.getChildren().get(item.getPosition()).isExpanded()){
                        tvExpand2.setText("收起");
                        nameListDTOList.addAll(item.getChildren().get(item.getPosition()).getNameList());
                    }else {
                        tvExpand2.setText("展开");
                        nameListDTOList.addAll(item.getChildren().get(item.getPosition()).getNameList().subList(0, 9));
                    }
                    baseQuickAdapter2.notifyDataSetChanged();
                });

                if (!item.getChildren().isEmpty()) {
                    radioButton1.setChecked(item.getChildren().get(item.getPosition()).getTag().equals("and"));
                    radioButton2.setChecked(item.getChildren().get(item.getPosition()).getTag().equals("or"));
                    item.getChildren().get(item.getPosition()).setChecked(true);

                    if (item.getChildren().get(item.getPosition()).getNameList().size() > 9 && !item.getChildren().get(item.getPosition()).isExpanded()){
                        nameListDTOList.addAll(item.getChildren().get(item.getPosition()).getNameList().subList(0, 9));
                    }else {
                        nameListDTOList.addAll(item.getChildren().get(item.getPosition()).getNameList());
                    }
                    baseQuickAdapter2.notifyDataSetChanged();

                    updateSelectAllIcon(tvSelectAll, item.getChildren().get(item.getPosition()).isAllSelected());

                    tvExpand2.setText(item.getChildren().get(item.getPosition()).isExpanded()?"收起":"展开");
                    tvExpand2.setVisibility(item.getChildren().get(item.getPosition()).getNameList().size() > 9 ? VISIBLE : GONE);
                }
                break;
        }
        helper.setText(R.id.tv_name, item.getName());
    }

    /**
     * 更新全选按钮的图标
     */
    private void updateSelectAllIcon(TextView tv, boolean isSelected) {
        Drawable drawable = ContextCompat.getDrawable(tv.getContext(),
                isSelected ? R.mipmap.ic_check_t : R.mipmap.ic_check_f); // 替换为实际选中/未选中图标
        // 设置图标大小为固有尺寸（避免拉伸）
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        // 设置左侧图标（start），其他方向为null
        tv.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 检查列表中所有项是否都被选中
     */
    private boolean isAllItemsSelected(List<GameFilterBean.NameListDTO> list) {
        if (list.isEmpty()) return false; // 空列表不视为"全选"
        for (GameFilterBean.NameListDTO item : list) {
            if (!item.isSelected()) {
                return false; // 只要有一项未选中，返回false
            }
        }
        return true; // 所有项都选中，返回true
    }

}
