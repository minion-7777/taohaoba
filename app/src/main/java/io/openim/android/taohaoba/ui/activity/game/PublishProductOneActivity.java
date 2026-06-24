package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.formatPrice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameConfigurationListBean;
import io.openim.android.taohaoba.bean.GoodsSettingBean;
import io.openim.android.taohaoba.bean.ValueDTOBean;
import io.openim.android.taohaoba.databinding.ActivityPublishProductBinding;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;
import io.openim.android.taohaoba.ui.dialog.GameDistrictServerDialog;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.ui.dialog.RealNameAuthenticationDialog;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.home.GoodsSettingVM;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;
import io.openim.android.taohaoba.widgets.RoundImageView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 发布商品第一步
 */
public class PublishProductOneActivity extends BaseActivity<GoodsSettingVM, ActivityPublishProductBinding> implements GoodsSettingVM.ViewAction{

    private static final int PERMISSION_REQUEST_CODE = 2000;
    private int patternId;//交易模式id
    private int categoryId;//商品类型id
    private int gameId;//游戏id
    private GameConfigurationListBean gameConfigurationListBean;//区服信息
    private GoodsSettingBean settingBean;
    private String TAG = "PublishProductOneActivity";
    private BaseQuickAdapter baseQuickAdapter;
    private int seller_service_ratio;//卖家费率
    private double seller_service_price;//卖家最低费率
    private double seller_amount_conf;//固定金额
    private boolean isAgree = false;//同意协议
    private int mSystemId;
    private int mSyOperatorId;
    private String game_server_id;
    private ActivityResultLauncher<Intent> resultLauncher;
    private int is_account_source;
    private int is_authentication;
    private int is_indulge;
    private String connect;
    private String label;
    private List<ValueDTOBean> valueDTOBeanList;
    private String price;
    private int operateType;//操作类型 1发布  2编辑
    private String gameName = "";
    private int id;//商品id
    private List<GoodsSettingBean.ContentDTO> contentDTOList = new ArrayList<>();
    private BasePopupView popupView;
    private BasePopupView popupView1;
    private String sparkle;
    private OSSImageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(GoodsSettingVM.class);
        bindViewDataBinding(ActivityPublishProductBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
        protocolInit();
    }

    private void initView() {
        operateType = getIntent().getIntExtra("operateType", 0);
        categoryId = getIntent().getIntExtra("categoryId", 0);
        patternId = getIntent().getIntExtra("patternId", 0);
        gameId = getIntent().getIntExtra("gameId", 0);
        gameName = getIntent().getStringExtra("gameName");

        if (operateType == 2){
            //编辑
            id = getIntent().getIntExtra("goodsId", 0);
        }

        view.tvPrice.setText(formatPrice(getBaseContext(), "0"));

        view.toolbar.setTitleText(gameName);
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.tvDistrictServer.setOnClickListener(v->{
            if (gameConfigurationListBean != null)
                onGameTypeDialog();
        });

        //参数设置
        view.tvParameter.setOnClickListener(v->{
            if (settingBean != null) {
                Intent intent = new Intent(this, PublishProductTwoActivity.class);
                intent.putExtra("gameName", gameName);
                intent.putExtra("goodsLiveData", new Gson().toJson(settingBean));
                resultLauncher.launch(intent); // 启动目标Activity[6,8](@ref)
            }else {
                showToast("配置信息为空");
            }
        });

        //提交实名认证
        vm.getRealnameBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("已提交实名认证");
        });

        // 注册结果监听器
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        is_account_source = data.getIntExtra("is_account_source", 1);
                        is_authentication = data.getIntExtra("is_authentication", 0);
                        is_indulge = data.getIntExtra("is_indulge", 0);
                        connect = data.getStringExtra("connect");
                        label = data.getStringExtra("label");
                        sparkle = data.getStringExtra("sparkle");
                        Type listType = new TypeToken<List<ValueDTOBean>>() {}.getType();
                        valueDTOBeanList = new Gson().fromJson(data.getStringExtra("content"), listType);
                        view.tvParameter.setText("已填写");

                        settingBean.setIs_account_source(is_account_source);
                        settingBean.setIs_authentication(is_authentication);
                        settingBean.setIs_indulge(is_indulge);
                        settingBean.setConnect(connect);
                        settingBean.setLabel(label);
                        settingBean.setSparkle(sparkle);
                        if (settingBean.getContent() != null) {
                            for (int i = 0; i < settingBean.getContent().size(); i++) {
                                settingBean.getContent().get(i).setValueName(valueDTOBeanList.get(i).getValue());
                            }
                        }
                    }
                }
        );

        view.ivCheck.setOnClickListener(v->{
            isAgree = !isAgree;
            view.ivCheck.setImageResource(isAgree ? R.mipmap.ic_check_t : R.mipmap.ic_check_f);
        });

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);

        uploadImageview();

        //发布
        view.tvSubmit.setOnClickListener(v->{

            result.setLength(0);
            if (imgList.size() > 1) {
                for (int i = 1; i < imgList.size(); i++) {
                    if (result.length() > 0) {
                        result.append(","); // 如果已经有数据，先加上逗号分隔
                    }
                    result.append(imgList.get(i)); // 将 Value 添加到结果字符串中
                }
            }

            if (TextUtils.isEmpty(view.tvSpecificSituationTitle.getText().toString().trim())) {
                shakeAnimation(view.tvSpecificSituationTitle);
                showToast("请输入标题");
                return;
            }
            if (TextUtils.isEmpty(view.tvSpecificSituation.getText().toString().trim())) {
                shakeAnimation(view.tvSpecificSituation);
                showToast("请输入描述");
                return;
            }
            if (TextUtils.isEmpty(view.tvName.getText().toString().trim())) {
                shakeAnimation(view.tvName);
                showToast("请输入游戏账号");
                return;
            }
            if (TextUtils.isEmpty(result.toString())) {
                shakeAnimation(view.ivCheck);
                showToast("请上传游戏资产截图");
                return;
            }
            if (gameConfigurationListBean == null) {
                shakeAnimation(view.tvDistrictServer);
                showToast("游戏区服配置未填写");
                return;
            }
            if (gameConfigurationListBean.getGame_server() != null && gameConfigurationListBean.getGame_server().size() > 0 && TextUtils.isEmpty(game_server_id)) {
                shakeAnimation(view.tvDistrictServer);
                showToast("请选择游戏区服");
                return;
            }
            if (gameConfigurationListBean.getDevice() != null && gameConfigurationListBean.getDevice().size() > 0 && (mSystemId == 0 || mSyOperatorId == 0)){
                shakeAnimation(view.tvDistrictServer);
                showToast("请选择游戏区服");
                return;
            }
            if (operateType == 1 && valueDTOBeanList == null) {
                shakeAnimation(view.tvParameter);
                showToast("游戏参数配置未填写");
                return;
            }
            if (TextUtils.isEmpty(price)) {
                shakeAnimation(view.tvPrice);
                showToast("请输入账号售价");
                return;
            }
            if (!isAgree) {
                shakeAnimation(view.ivCheck);
                showToast("请勾选协议");
                return;
            }

            showLoadingDialog();
            //获取是否实名认证
            vm.auth_info();
        });

        //账号售价
        view.tvPrice.setOnClickListener(v->{
            onInputPriceDialog();
        });
    }

    private void initListener() {

        //获取游戏配置
        vm.getGoodsSettingBeanMutableLiveData().observe(this, it->{
            dismissLoadingDialog();
            settingBean = it;
            seller_service_ratio = it.getSeller_service_ratio();
            seller_service_price = it.getSeller_service_price();
            seller_amount_conf = it.getSeller_amount_conf();
            if (operateType == 2) {
                vm.goods_edit(id);
            }
        });

        //获取游戏区服信息
        vm.getGameConfigurationListBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            gameConfigurationListBean = it;
        });

        //发布
        vm.getGoodsSaveBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("发布成功！");
            startActivity(new Intent(this, PublishProductSuccessActivity.class));
            finish();
        });

        //编辑发布商品信息
        vm.getGoodsEditBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            setGoodsEdit(it);
        });

        //更新卖家发布商品
        vm.getGoodsDetailsUpdateBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("更新成功！");
            finish();
        });

        //是否实名认证
        vm.getAuthInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            if (it.getUser_auth_info().getType()!=0) {
                //已实名认证
                if (operateType == 1) {
                    //发布
                    showLoadingDialog();
                    vm.setGoodsSave(
                            categoryId,
                            gameId,
                            patternId,
                            settingBean.getId(),
                            mSystemId,
                            mSyOperatorId,
                            view.tvName.getText().toString(),
                            view.tvSpecificSituationTitle.getText().toString(),
                            result.toString(),
                            game_server_id,
                            1,
                            is_indulge,
                            is_authentication,
                            is_account_source,
                            Double.valueOf(price),
                            connect,
                            view.tvSpecificSituation.getText().toString(),
                            label,
                            sparkle,
                            valueDTOBeanList
                    );
                }else if (operateType == 2){
                    //编辑

                    if (valueDTOBeanList == null && settingBean.getContent() != null) {
                        valueDTOBeanList = settingBean.getContent().stream()
                                .map(item -> {
                                    ValueDTOBean bean = new ValueDTOBean();
                                    bean.setKey(item.getKey());
                                    bean.setKey_sort(item.getKey_sort());
                                    bean.setValue(item.getValueName());
                                    bean.setIs_required(item.getIs_required());
                                    bean.setIs_sort(item.getIs_sort());
                                    bean.setIs_show(item.getIs_show());
                                    bean.setType(item.getType());
                                    bean.setSort_type(item.getSort_type());
                                    // 手动映射其他字段...
                                    return bean;
                                })
                                .collect(Collectors.toList());
                    }

                    showLoadingDialog();
                    vm.goods_details_update(
                            categoryId,
                            id,
                            gameId,
                            patternId,
                            mSystemId,
                            mSyOperatorId,
                            view.tvName.getText().toString(),
                            view.tvSpecificSituationTitle.getText().toString(),
                            result.toString(),
                            game_server_id,
                            1,
                            is_indulge,
                            is_authentication,
                            is_account_source,
                            Double.valueOf(price),
                            connect,
                            view.tvSpecificSituation.getText().toString(),
                            label,
                            sparkle,
                            valueDTOBeanList
                    );
                }
            }else {
                //未实名认证
                realNameAuthenticationDialog();
            }
        });

        //提交实名认证
        vm.getRealnameBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("已提交实名认证");
        });

        showLoadingDialog();
        vm.getGoodsSetting(categoryId, gameId, patternId);
        vm.getGameConfigurationList(gameId);

    }

    //从编辑按钮进入的，设置初始化数据
    private void setGoodsEdit(GoodsSettingBean it) {
        view.tvParameter.setText("已填写");
        settingBean.setIs_account_source(it.getIs_account_source());
        settingBean.setIs_authentication(it.getIs_authentication());
        settingBean.setIs_indulge(it.getIs_indulge());
        settingBean.setConnect(it.getConnect());
        settingBean.setLabel(it.getLabel());
        settingBean.setSparkle(it.getSparkle());

        if (settingBean.getContent() != null) {
            // 创建 Key 到 DTO 的映射，避免嵌套循环
            Map<String, GoodsSettingBean.ContentDTO> keyToDtoMap = new HashMap<>();
            for (GoodsSettingBean.ContentDTO dto : settingBean.getContent()) {
                keyToDtoMap.put(dto.getKey(), dto);
            }

            // 遍历处理每个 ContentDTO
            for (GoodsSettingBean.ContentDTO contentDTO : it.getContent()) {
                GoodsSettingBean.ContentDTO targetDto = keyToDtoMap.get(contentDTO.getKey());
                if (targetDto != null) {
                    // 使用 Stream 拼接 ID 字符串
                    String valueName = contentDTO.getValue().stream()
                            .map(valueDTO -> String.valueOf(valueDTO.getValue()))
                            .collect(Collectors.joining("，"));
                    targetDto.setValueName(valueName);
                }
            }
        }

        id = it.getId();
        view.tvName.setText(it.getAccount());
        view.tvSpecificSituationTitle.setText(it.getTitle());

        Set<String> valueSet = Stream.of(it.getImage().split(","))
                .collect(Collectors.toCollection(HashSet::new));
        for (String s : valueSet) {
            imgList.add(s);
        }
        baseQuickAdapter.notifyDataSetChanged();

        is_indulge = it.getIs_indulge();
        is_authentication = it.getIs_authentication();
        is_account_source = it.getIs_account_source();
        price = String.valueOf(it.getRetail_price());
        view.tvPrice.setText(formatPrice(getBaseContext(), price));
        connect = it.getConnect();
        view.tvSpecificSituation.setText(it.getText());
        label = it.getLabel();
        sparkle = it.getSparkle();
        if (it.getContent() != null) {
            contentDTOList.addAll(it.getContent());
        }
        view.tvDistrictServer.setText(TextUtils.isEmpty(it.getDevice_name()) ? it.getGame_service_id() : (it.getDevice_name()+"，"+it.getOperator_name()));
        seller_service_ratio = it.getSeller_service_ratio();
        seller_service_price = it.getSeller_service_price();
        seller_amount_conf = it.getSeller_amount_conf();
        mSystemId = it.getDevice_id();
        mSyOperatorId = it.getOperator_id();
        game_server_id = it.getGame_service_id();

        if (gameConfigurationListBean != null) {
            if (gameConfigurationListBean.getGame_server() != null) {
                //端游
                String[] str = it.getGame_service_id().split(",");
                for (GameConfigurationListBean.GameServerDTO gameServerDTO : gameConfigurationListBean.getGame_server()) {
                    if (str.length > 0 && gameServerDTO.getId() == Integer.valueOf(str[0])) {
                        gameServerDTO.setExpanded(true);
                        view.tvDistrictServer.setText(gameServerDTO.getName());
                    }
                    if (gameServerDTO.getParentItem() != null) {
                        for (GameConfigurationListBean.GameServerDTO serverDTO : gameServerDTO.getParentItem()) {
                            if (str.length > 1 && serverDTO.getId() == Integer.valueOf(str[1])) {
                                serverDTO.setExpanded(true);
                                view.tvDistrictServer.setText(gameServerDTO.getName()+"."+serverDTO.getName());
                            }
                        }
                    }
                }
            }else {
                //手游
                for (GameConfigurationListBean.DeviceDTO deviceDTO : gameConfigurationListBean.getDevice()) {
                    if (deviceDTO.getName().equals(it.getDevice_name())) {
                        deviceDTO.setExpanded(true);
                    }
                }
                if (gameConfigurationListBean.getOperator() != null) {
                    for (GameConfigurationListBean.DeviceDTO deviceDTO : gameConfigurationListBean.getOperator()) {
                        if (deviceDTO.getName().equals(it.getOperator_name())) {
                            deviceDTO.setExpanded(true);
                        }
                    }
                }
            }
        }

    }


    private StringBuilder str = new StringBuilder();
    /**
     * 游戏类型
     */
    private void onGameTypeDialog(){
        if (popupView1 == null) {
            popupView1 = new XPopup.Builder(this)
                    .isViewMode(false)
                    .asCustom(new GameDistrictServerDialog(this, 1, gameConfigurationListBean, new GameDistrictServerDialog.OnVerificationListener() {
                        @Override
                        public void onSubmit(int game_typeId, int deviceId, String game_serverId, int operatorId, String game_typeName, String deviceName, String game_serverName, String operatorName) {
                            str.setLength(0);
                            strAdd(str, game_typeName);
                            strAdd(str, deviceName);
                            strAdd(str, game_serverName);
                            strAdd(str, operatorName);
                            view.tvDistrictServer.setText(str.toString());
                            mSystemId = deviceId;
                            game_server_id = game_serverId;
                            mSyOperatorId = operatorId;
                        }
                    }));
        }
        popupView1.show();
    }

    private static void strAdd(StringBuilder str, String str1){
        if (!TextUtils.isEmpty(str1)){
            if (str.length() > 0) {
                str.append("."); // 如果已经有数据，先加上逗号分隔
            }
            str.append(str1);
        }
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

    private StringBuilder result = new StringBuilder();

    /**
     * 上传图片到oss
     * @param filePaths
     */
    private void initOss(List<String> filePaths){
        Log.i(TAG, "上传前"+filePaths.toString());

        filePaths.removeIf(TextUtils::isEmpty); // 内置安全删除

        // 执行上传（支持单张/多张，此处以多张为例）
        uploader.uploadMultiple(filePaths, new OSSImageUploader.UploadCallback() {
            @Override
            public void onSuccess(List<String> imageUrls) {
                dismissLoadingDialog();
                Log.i(TAG, "图片上传成功: " + imageUrls);
                runOnUiThread(() -> {
                    imgList.addAll(imageUrls); // 更新图片列表
                    baseQuickAdapter.notifyDataSetChanged(); // 刷新UI
                });
            }

            @Override
            public void onFailure(Exception e) {
                dismissLoadingDialog();
                Log.e(TAG, "图片上传失败: " + e.getMessage());
                showToast("上传失败: " + e.getMessage());
            }

            @Override
            public void onProgress(int progress) {
                Log.i(TAG, "上传进度: " + progress + "%");
                // 可在此更新进度条UI
            }
        });
    }

    private List<String> imgList = new ArrayList<>();
    //图片列表
    private void uploadImageview(){
        imgList.add("");
        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_upload_img, imgList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {

                RoundImageView iv_img_add = baseViewHolder.getView(R.id.iv_img_add);
                ImageView iv_img_delete = baseViewHolder.getView(R.id.iv_img_delete);

                if (!TextUtils.isEmpty(o.toString())) {
                    Glide.with(baseViewHolder.itemView.getContext())
                            .load(o.toString())
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                    .error(R.mipmap.ic_default_image)// 加载失败的占位图
                            )
                            .into(iv_img_add);
                }else {
                    iv_img_add.setImageResource(R.mipmap.ic_upload_pictures);
                }

                iv_img_delete.setVisibility(TextUtils.isEmpty(o.toString()) ? INVISIBLE : VISIBLE);

                iv_img_delete.setOnClickListener(v->{
                    imgList.remove(o);
                    notifyDataSetChanged();
                });

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (TextUtils.isEmpty(o.toString())) {
                        applyPermission();
                    }
                });
            }
        };
        // 转换dp为px（Android尺寸单位工具）
        int spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );
        view.rcRecycler.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
        // 添加间距装饰（包含边缘间距）
        view.rcRecycler.addItemDecoration(
                new GridSpacingItemDecoration(3, spacingInPx, true)
        );
        view.rcRecycler.setAdapter(baseQuickAdapter);
    }

    /**
     * 申请动态权限
     */
    private void applyPermission() {
        //检测权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            // 如果没有权限，则申请需要的权限
//            SnackBarUtil.show(this, view.tvWarn, "获取媒体和文件说明：", "用于设置商品图片");
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }else {
//            // 已经申请了权限
//            setPhoto();
//        }
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES :
                Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // 显示权限说明弹窗
            new XPopup.Builder(this)
                    .asConfirm("需要存储权限", "设置商品图片需要访问您的相册图片，请允许权限", () -> {
                        // 正确请求当前版本需要的权限
                        ActivityCompat.requestPermissions(
                                this,
                                new String[]{permission},
                                PERMISSION_REQUEST_CODE
                        );
                    })
                    .show();
        } else {
            setPhoto();
        }
    }

    //图片选择
    private void setPhoto(){

        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
                .setMaxSelectNum(21-imgList.size())
                .isDisplayCamera(!"huawei".equals(Build.MANUFACTURER.toLowerCase()))
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        showLoadingDialog();
                        List<String> image = new ArrayList<>();
                        for (LocalMedia localMedia : result) {
                            image.add(localMedia.getRealPath());
                        }
                        initOss(image);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    /**
     * 用户选择是否开启权限操作后的回调；TODO 同意/拒绝
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户同样授权
                setPhoto();
            }else {
                // 用户拒绝授权
                Toast.makeText(this, "你拒绝使用存储权限！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 账号售价
     */
    private void onInputPriceDialog(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new InputPriceDialog(this, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        price = amount;
                        view.tvPrice.setText(formatPrice(getBaseContext(), amount));
                    }
                })).show();
    }

    /**
     * 协议
     */
    private void protocolInit() {
        String originalText = "我已阅读并同意《卖家出售协议》";

        SpannableStringBuilder builder = new SpannableStringBuilder(originalText);

        int startIndex1 = originalText.indexOf("《卖家出售协议》");
        int endIndex1 = startIndex1 + "《卖家出售协议》".length();

        ClickableSpan clickableSpan1 = new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1002));
            }
        };

        builder.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        int blueColor = ContextCompat.getColor(this, R.color.color_EACA92);
        builder.setSpan(new ForegroundColorSpan(blueColor), startIndex1, endIndex1, SpannableStringBuilder.SPAN_INCLUSIVE_INCLUSIVE);

        view.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        view.tvAgreement.setHighlightColor(Color.TRANSPARENT);
        view.tvAgreement.setText(builder);
    }

    private static abstract class CustomClickableSpan extends ClickableSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            // 去掉下划线
            ds.setUnderlineText(false);
        }
    }

    /**
     * 实名认证弹窗
     */
    private void realNameAuthenticationDialog(){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new RealNameAuthenticationDialog(this, new RealNameAuthenticationDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String name, String number) {
                        showLoadingDialog();
                        vm.realname(name, number);
                    }
                })).show();
    }

}
