package io.openim.android.taohaoba.ui.adapter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.bean.GoodsSettingBean.ContentDTO.TYPE_ONE;
import static io.openim.android.taohaoba.bean.GoodsSettingBean.ContentDTO.TYPE_THREE;
import static io.openim.android.taohaoba.bean.GoodsSettingBean.ContentDTO.TYPE_TWO;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GoodsSettingBean;

public class PublishProductAdapter extends BaseMultiItemQuickAdapter<GoodsSettingBean.ContentDTO, BaseViewHolder> {

    // 构造方法中注册布局类型
    public PublishProductAdapter(List<GoodsSettingBean.ContentDTO> data) {
        super(data);
        addItemType(TYPE_ONE, R.layout.item_game_configuration_one);
        addItemType(TYPE_TWO, R.layout.item_game_configuration_two);
        addItemType(TYPE_THREE, R.layout.item_game_configuration_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsSettingBean.ContentDTO item) {
        helper.setIsRecyclable(false); // 禁止此 ViewHolder 被回收复用
        switch (helper.getItemViewType()) {
            case TYPE_ONE:
                TextWatcher textWatcher = null;
                EditText editText = helper.getView(R.id.et_count);
                // 清除旧监听器防止复用干扰[4](@ref)
                if (textWatcher != null) {
                    editText.removeTextChangedListener(textWatcher);
                }

                editText.setText(item.getValueName());

                // 动态创建 TextWatcher
                textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        item.setValueName(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                };

                editText.addTextChangedListener(textWatcher);
                break;
            case TYPE_TWO:
            case TYPE_THREE:
                helper.setText(R.id.tv_select_text, item.getValueName());
                break;
        }
        helper.setText(R.id.tv_text2, item.getKey());
        helper.getView(R.id.tv_text1).setVisibility(item.getIs_required() == 1 ? VISIBLE : INVISIBLE);
    }
}
