package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.bean.GoodsSettingBean;
import io.openim.android.taohaoba.bean.ValueDTOBean;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityPublishProductTwoBinding;
import io.openim.android.taohaoba.ui.adapter.PublishProductAdapter;
import io.openim.android.taohaoba.ui.dialog.PublishProductDialog;
import io.openim.android.taohaoba.vm.home.GoodsSettingVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 发布商品第二步
 */
public class PublishProductTwoActivity extends BaseActivity<GoodsSettingVM, ActivityPublishProductTwoBinding> implements GoodsSettingVM.ViewAction{


    private List<GoodsSettingBean.ContentDTO> settingBean = new ArrayList<>();
    private PublishProductAdapter quickAdapter;
    private GoodsSettingBean goodsSettingBean;
    private int is_account_source = 1;
    private int is_authentication = 0;
    private int is_indulge = 0;
    private String label = "包赔服务,验证账号";
    private boolean isRetrue; //配置项是否有未填写的数据
    private String userPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(GoodsSettingVM.class);
        bindViewDataBinding(ActivityPublishProductTwoBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    private void initView() {
        userPhone = mmkv.decodeString(PreferencesKey.userPhone);
        String gameName = getIntent().getStringExtra("gameName");
        Type listType = new TypeToken<GoodsSettingBean>() {}.getType();
        goodsSettingBean = new Gson().fromJson(getIntent().getStringExtra("goodsLiveData"), listType);
        if (goodsSettingBean.getContent() != null) {
            settingBean.addAll(goodsSettingBean.getContent());
        }
        view.toolbar.setTitleText(gameName);
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        for (GoodsSettingBean.ContentDTO contentDTO : settingBean) {
            if (!TextUtils.isEmpty(contentDTO.getValueName())) {
                String[] str = contentDTO.getValueName().split("，");
                List<GoodsSettingBean.ContentDTO.ValueDTO> valueDTOs = contentDTO.getValue();

                // 预处理：将分割后的字符串转为小写（忽略空格）
                Set<String> targetValues = Arrays.stream(str)
                        .map(s -> s.trim().toLowerCase())
                        .collect(Collectors.toSet());

                // 遍历每个 ValueDTO，直接判断是否匹配
                for (GoodsSettingBean.ContentDTO.ValueDTO valueDTO : valueDTOs) {
                    String dtoValue = valueDTO.getValue().trim().toLowerCase();
                    valueDTO.setExpanded(targetValues.contains(dtoValue));
                }
            }
        }

        view.etCount.setText(TextUtils.isEmpty(goodsSettingBean.getConnect()) ? userPhone : goodsSettingBean.getConnect());
        view.etSparkle.setText(goodsSettingBean.getSparkle());

        view.clzhly.setVisibility(goodsSettingBean.getIs_account_source_required() == 1 ? VISIBLE : GONE);
        view.clecsm.setVisibility(goodsSettingBean.getIs_authentication_required() == 1 ? VISIBLE : GONE);
        view.clfcm.setVisibility(goodsSettingBean.getIs_indulge_required() == 1 ? VISIBLE : GONE);

        view.radioButton1.setChecked(goodsSettingBean.getIs_account_source() == 0 || goodsSettingBean.getIs_account_source() == 1);
        view.radioButton2.setChecked(goodsSettingBean.getIs_account_source() == 2);
        view.radioButton3.setChecked(goodsSettingBean.getIs_account_source() == 3);

        view.radioButton4.setChecked(goodsSettingBean.getIs_authentication() == 1);
        view.radioButton5.setChecked(goodsSettingBean.getIs_authentication() == 0);

        view.radioButton6.setChecked(goodsSettingBean.getIs_indulge() == 1);
        view.radioButton7.setChecked(goodsSettingBean.getIs_indulge() == 0);

        onGameTypeAdapter();

    }

    private void initListener() {
        List<GoodsSettingBean.ContentDTO.ValueDTO> valueDTOList = new ArrayList<>();
        GoodsSettingBean.ContentDTO.ValueDTO valueDTO = new GoodsSettingBean.ContentDTO.ValueDTO();
        valueDTO.setValue("包赔服务");
        GoodsSettingBean.ContentDTO.ValueDTO valueDTO1 = new GoodsSettingBean.ContentDTO.ValueDTO();
        valueDTO1.setValue("验证账号");
        valueDTOList.add(valueDTO);
        valueDTOList.add(valueDTO1);

        //赋予默认值
        goodsSettingBean.setLabel(label);
        label = goodsSettingBean.getLabel();
        view.tvLabel.setText(label);

        if (!TextUtils.isEmpty(goodsSettingBean.getLabel())) {
            Set<String> valueSet = Stream.of(label.split(","))
                    .collect(Collectors.toCollection(HashSet::new));

            valueDTOList.stream()
                    .parallel() // 大数据量时启用并行流
                    .forEach(dto -> dto.setExpanded(valueSet.contains(dto.getValue())));
        }

        view.tvLabel.setOnClickListener(v->{
            onGameTypeDialog(
                    0,
                    3,
                    "商品标签",
                    valueDTOList,0);
        });

        view.tvSubmit.setOnClickListener(v->{

            if (view.radioButton1.isChecked()){
                is_account_source = 1;
            }else if (view.radioButton2.isChecked()){
                is_account_source = 2;
            }else if (view.radioButton3.isChecked()){
                is_account_source = 3;
            }

            if (view.radioButton4.isChecked()){
                is_authentication = 1;
            }else if (view.radioButton5.isChecked()){
                is_authentication = 0;
            }

            if (view.radioButton6.isChecked()){
                is_indulge = 1;
            }else if (view.radioButton7.isChecked()){
                is_indulge = 0;
            }


            if (TextUtils.isEmpty(view.etCount.getText().toString().trim())) {
                shakeAnimation(view.etCount);
                showToast("请输入联系方式");
                return;
            }

            if (TextUtils.isEmpty(label)) {
                shakeAnimation(view.tvLabel);
                showToast("请选择标签");
                return;
            }

            for (GoodsSettingBean.ContentDTO contentDTO : settingBean) {
                if (contentDTO.getIs_required() == 1 && StringUtils.isBlank(contentDTO.getValueName())) {
                    showToast(contentDTO.getKey()+"项配置值缺少");
                    isRetrue = true;
                    break; // 退出循环
                }else {
                    isRetrue = false;
                }
            }

            if (isRetrue) {
                return;
            }

            List<ValueDTOBean> listB = settingBean.stream()
                    .map(item -> {
                        ValueDTOBean bean = new ValueDTOBean();
                        bean.setKey(item.getKey());
                        bean.setKey_sort(item.getKey_sort());
                        bean.setValue(TextUtils.isEmpty(item.getValueName()) ? "" : item.getValueName());
                        bean.setIs_required(item.getIs_required());
                        bean.setIs_sort(item.getIs_sort());
                        bean.setIs_show(item.getIs_show());
                        bean.setType(item.getType());
                        bean.setSort_type(item.getSort_type());
                        return bean;
                    })
                    .collect(Collectors.toList());

            Intent resultIntent = new Intent();
            resultIntent.putExtra("is_account_source", is_account_source);
            resultIntent.putExtra("is_authentication", is_authentication);
            resultIntent.putExtra("is_indulge", is_indulge);
            resultIntent.putExtra("connect", view.etCount.getText().toString().trim());
            resultIntent.putExtra("sparkle", TextUtils.isEmpty(view.etSparkle.getText().toString()) ? "" : view.etSparkle.getText().toString().trim());
            resultIntent.putExtra("label", label);
            resultIntent.putExtra("content", new Gson().toJson(listB));
            setResult(RESULT_OK, resultIntent); // 设置返回数据[5,7](@ref)
            finish(); // 结束当前Activity
        });
    }

    private void onGameTypeAdapter() {
        view.rcRecycler.setNestedScrollingEnabled(false);
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        // 创建适配器
        quickAdapter = new PublishProductAdapter(settingBean);
        // 设置适配器
        view.rcRecycler.setAdapter(quickAdapter);

        quickAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (settingBean.get(position).getType() != 1) {
                    onGameTypeDialog(
                            settingBean.get(position).getKey_sort(),
                            settingBean.get(position).getType(),
                            settingBean.get(position).getKey(),
                            settingBean.get(position).getValue(),
                            position);
                }
            }
        });
    }

    /**
     * 选择弹窗
     * selectType 2单选  3多选
     */
    private void onGameTypeDialog(int type, int selectType, String name, List<GoodsSettingBean.ContentDTO.ValueDTO> valueDTOList, int position){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new PublishProductDialog(this, selectType, name, valueDTOList, new PublishProductDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String code) {
                        if (type == 0){
                            label = code;
                            view.tvLabel.setText(label);
                        }else {
                            for (GoodsSettingBean.ContentDTO contentDTO : settingBean) {
                                if (name.equals(contentDTO.getKey())){
                                    contentDTO.setValueName(code);
                                }
                            }
                            quickAdapter.notifyDataSetChanged();
                            quickAdapter.notifyItemChanged(position);
                        }
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
