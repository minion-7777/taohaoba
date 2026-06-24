package io.openim.android.taohaoba.ui.dialog;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsSettingBean;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;

/**
 * 游戏信息配置弹窗
 */
public class PublishProductDialog extends BottomPopupView {

    private List<GoodsSettingBean.ContentDTO.ValueDTO> valueDTOList;
    private List<GoodsSettingBean.ContentDTO.ValueDTO> filteredList = new ArrayList<>(); // 新增：过滤结果列表
    private Context context;
    private String name;
    private int selectType;//2单选  3多选
    private StringBuilder result = new StringBuilder();
    private BaseQuickAdapter baseQuickAdapter;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String code); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public PublishProductDialog(Context context, int selectType, String name, List<GoodsSettingBean.ContentDTO.ValueDTO> valueDTOList, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.valueDTOList = valueDTOList;
        this.name = name;
        this.selectType = selectType;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_game_configuration;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tv_title = findViewById(R.id.tv_title);
        RecyclerView rc_recycler = findViewById(R.id.rc_recycler);
        EditText etSearch = findViewById(R.id.et_search);
        TextView tvSearch = findViewById(R.id.tv_search);

        tv_title.setText(name);

        if (valueDTOList != null) {
            filteredList.addAll(valueDTOList);
        }

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            dismiss();
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 1. 隐藏键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // 2. 执行搜索逻辑
                String query = etSearch.getText().toString().trim();
                performSearch(query);
                return true; // 消费事件
            }
            return false;
        });

        tvSearch.setOnClickListener(v->{
            performSearch(etSearch.getText().toString().trim());
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                for (GoodsSettingBean.ContentDTO.ValueDTO valueDTO : valueDTOList) {
                    if (valueDTO.isExpanded()) {
                        if (result.length() > 0) {
                            result.append(","); // 如果已经有数据，先加上逗号分隔
                        }
                        result.append(valueDTO.getValue()); // 将 Value 添加到结果字符串中
                    }
                }
                verificationListener.onSubmit(result.toString());
                dismiss();
            }
        });

        // 转换dp为px（Android尺寸单位工具）
        int spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_game_configuration, filteredList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                GoodsSettingBean.ContentDTO.ValueDTO bean = (GoodsSettingBean.ContentDTO.ValueDTO) o;
                baseViewHolder.setText(R.id.tv_node_name, bean.getValue());
                TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (selectType == 2) {
                        for (int i = 0; i < filteredList.size(); i++) {
                            filteredList.get(i).setExpanded(i == baseViewHolder.getPosition());
                        }
                    }else {
                        filteredList.get(baseViewHolder.getPosition()).setExpanded(!filteredList.get(baseViewHolder.getPosition()).isExpanded());
                    }
                    notifyDataSetChanged();
                });
            }
        };
        rc_recycler.setLayoutManager(new GridLayoutManager(context, 2));
        // 添加间距装饰（包含边缘间距）
        rc_recycler.addItemDecoration(
                new GridSpacingItemDecoration(2, spacingInPx, true)
        );
        rc_recycler.setAdapter(baseQuickAdapter);

    }

    // 新增：搜索执行方法
    private void performSearch(String query) {
        if (valueDTOList == null || valueDTOList.isEmpty()) return;

        filteredList.clear();
        if (TextUtils.isEmpty(query)){
            filteredList.addAll(valueDTOList);
        }else {
            String lowerQuery = query.toLowerCase(); // 转为小写，忽略大小写匹配

            for (GoodsSettingBean.ContentDTO.ValueDTO item : valueDTOList) {
                // 匹配value字段（忽略大小写），跳过null值
                if (item.getValue() != null && item.getValue().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(item);
                }
            }
        }

        // 通知适配器更新UI
        baseQuickAdapter.notifyDataSetChanged();
    }
}
