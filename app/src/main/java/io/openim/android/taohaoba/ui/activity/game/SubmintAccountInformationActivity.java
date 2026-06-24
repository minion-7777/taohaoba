package io.openim.android.taohaoba.ui.activity.game;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameAccountValueBean;
import io.openim.android.taohaoba.bean.SubmintAccountInformationBean;
import io.openim.android.taohaoba.databinding.ActivitySubmitAccountInformationBinding;
import io.openim.android.taohaoba.utils.DensityUtil;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.me.OrderDetailsVM;
import io.openim.android.taohaoba.widgets.CustomDecoration;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 提交账号信息
 */
@Route(path = Routes.Main.SUBMINT_ACCOUNT_INFORMATION)
public class SubmintAccountInformationActivity extends BaseActivity<OrderDetailsVM, ActivitySubmitAccountInformationBinding> implements OrderDetailsVM.ViewAction{

    private List<List<SubmintAccountInformationBean>> beanList = new ArrayList<>();
    private BaseQuickAdapter groupAdapter; // 外层Adapter（管理组）
    private BaseQuickAdapter itemAdapter; // 内层Adapter（管理组内项）
    private List<String> imgList = new ArrayList<>();
    private static final int PERMISSION_REQUEST_CODE = 2000;
    private String TAG = "SubmintAccountInformationActivity";
    private String authentication_image="";//二次实名图片url
    private String account_source_image;// '账号来源图片' 为3：其他平台购买时 必选
    private int is_account_source = 1;//'账号来源  1: 自己注册 2：本平台购买 3：其他平台购买   （0:是运维人员设置的  1，2，3是用户上架商品的时候选择）必选
    private int imgType; //1二次实名 2账号来源
    private int gameId;//游戏id
    private int orderId;//订单id
    private int goodsId;//商品id
    private List<GameAccountValueBean> gameAccountValueBeanList = new ArrayList<>();
    private String imGroupId;
    private String imGroupOwnerUserID;
    private boolean isRetrue;//配置项是否有未填写的数据
    private OSSImageUploader uploader;
    private List<SubmintAccountInformationBean> submintAccountInformationBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(OrderDetailsVM.class);
        bindViewDataBinding(ActivitySubmitAccountInformationBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
        initAdapter();
    }

    private void initView() {
        gameId = getIntent().getIntExtra("gameId", 0);
        orderId = getIntent().getIntExtra("orderId", 0);
        goodsId = getIntent().getIntExtra("goodsId", 0);
        imGroupId = getIntent().getStringExtra("imGroupId");
        imGroupOwnerUserID = getIntent().getStringExtra("imGroupOwnerUserID");

        //获取配置项
        vm.getSubmintAccountInformationBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            submintAccountInformationBeans = it;
            beanList.clear();
            // 初始添加第一组（深拷贝原列表，避免后续修改影响所有组）
            if (it != null) {
                beanList.add(new ArrayList<>(it));
            }
            groupAdapter.notifyDataSetChanged();
        });

        //提交信息
        vm.getSaveGameMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("提交成功");
            setResult(RESULT_OK); // 设置返回数据[5,7](@ref)
            finish(); // 结束当前Activity
        });

        vm.get_game_account_conf(gameId);

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);
    }

    private void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                showExitConfirmationDialog();
            }
        });

        //二次实名
        view.ivImgAddSecondRealName.setOnClickListener(v->{
            imgType = 1;
            applyPermission();
        });

        //账号来源
        view.ivImgAddProofOfPurchase.setOnClickListener(v->{
            imgType = 2;
            applyPermission();
        });

        view.radioButton1.setOnClickListener(v->{
            view.llZhly.setVisibility(GONE);
            is_account_source = 1;
        });

        view.radioButton2.setOnClickListener(v->{
            view.llZhly.setVisibility(GONE);
            is_account_source = 2;
        });

        view.radioButton3.setOnClickListener(v->{
            view.llZhly.setVisibility(VISIBLE);
            is_account_source = 3;
        });

        view.tvAdd.setOnClickListener(v->{
            if (submintAccountInformationBeans == null || submintAccountInformationBeans.isEmpty()) {
                showToast("无可用配置项");
                return;
            }
            // 新增一组（深拷贝原配置项列表）
            List<SubmintAccountInformationBean> newGroup = new ArrayList<>();
            for (SubmintAccountInformationBean bean : submintAccountInformationBeans) {
                // 深拷贝单个bean，避免组间数据联动
                SubmintAccountInformationBean copyBean = new SubmintAccountInformationBean();
                copyBean.setId(bean.getId());
                copyBean.setKey(bean.getKey());
                copyBean.setValue(bean.getValue());
                copyBean.setIs_required(bean.getIs_required());
                newGroup.add(copyBean);
            }
            beanList.add(newGroup);
            groupAdapter.notifyItemInserted(beanList.size() - 1); // 局部刷新新增组
            // 滚动到新增组位置
            view.rcRecycler.scrollToPosition(beanList.size() - 1);
        });

        //提交
        view.tvSubmit.setOnClickListener(v->{

            isRetrue = false;
            for (List<SubmintAccountInformationBean> group : beanList) {
                for (SubmintAccountInformationBean bean : group) {
                    if (bean.getIs_required() == 1 && StringUtils.isBlank(bean.getStr())) {
                        showToast("请填写" + bean.getKey() + "信息");
                        isRetrue = true;
                        break;
                    }
                }
                if (isRetrue) break;
            }

            if (isRetrue) return;

            if (is_account_source == 3 && TextUtils.isEmpty(account_source_image)){
                showToast("购买凭证截图");
                return;
            }

            // 收集所有组的账号信息项
            gameAccountValueBeanList.clear();
            for (List<SubmintAccountInformationBean> group : beanList) {
                for (SubmintAccountInformationBean bean : group) {
                    gameAccountValueBeanList.add(new GameAccountValueBean(
                            bean.getId(),
                            TextUtils.isEmpty(bean.getStr()) ? "" : bean.getStr()
                    ));
                }
            }

            showLoadingDialog();
            vm.save_game_account_value(
                    gameId,
                    goodsId,
                    orderId,
                    is_account_source,
                    authentication_image,
                    is_account_source == 3 ? account_source_image : "",
                    gameAccountValueBeanList,
                    imGroupOwnerUserID,
                    imGroupId);

        });
    }


    private void initAdapter() {
        // 外层Adapter：管理每组账号信息
        groupAdapter = new BaseQuickAdapter<List<SubmintAccountInformationBean>, BaseViewHolder>(
                R.layout.item_account_group, beanList) { // 需新增组布局item_account_group
            @Override
            protected void convert(@NonNull BaseViewHolder helper, List<SubmintAccountInformationBean> groupItems) {
                // 绑定删除按钮
                ImageView ivDelete = helper.getView(R.id.iv_delete);
                // 绑定内层RecyclerView（组内账号信息项）
                RecyclerView rvGroupItems = helper.getView(R.id.rv_group_items);

                // 内层Adapter：展示组内具体账号信息项
                itemAdapter = new BaseQuickAdapter<SubmintAccountInformationBean, BaseViewHolder>(
                        R.layout.item_submit_account_information, groupItems) {
                    @Override
                    protected void convert(@NonNull BaseViewHolder itemHelper, SubmintAccountInformationBean bean) {
                        // 复用原item_submit_account_information布局的绑定逻辑
                        itemHelper.getView(R.id.tv_name1).setVisibility(bean.getIs_required() == 1 ? VISIBLE : INVISIBLE);
                        EditText et_game_account = itemHelper.getView(R.id.et_game_account);
                        itemHelper.setText(R.id.tv_name, bean.getKey());

                        if (et_game_account.getTag() instanceof TextWatcher) {
                            et_game_account.removeTextChangedListener((TextWatcher) et_game_account.getTag());
                        }
                        et_game_account.setHint(bean.getValue());
                        et_game_account.setText(bean.getStr()); // 回显已输入内容

                        TextWatcher textWatcher = new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }
                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }
                            @Override
                            public void afterTextChanged(Editable s) {
                                bean.setStr(s.toString()); // 实时保存输入内容到当前组的bean
                            }
                        };
                        et_game_account.addTextChangedListener(textWatcher);
                        et_game_account.setTag(textWatcher);
                    }
                };
                rvGroupItems.setLayoutManager(new LinearLayoutManager(SubmintAccountInformationActivity.this));
                rvGroupItems.setAdapter(itemAdapter);

                // 删除按钮点击事件：移除当前组
                ivDelete.setOnClickListener(v->{
                    int groupPosition = helper.getAdapterPosition();
                    beanList.remove(groupPosition);
                    groupAdapter.notifyItemRemoved(groupPosition);
                    // 刷新剩余项位置（避免索引错乱）
                    groupAdapter.notifyItemRangeChanged(groupPosition, beanList.size() - groupPosition);
                });
            }
        };
        // 设置外层Adapter到主RecyclerView
        view.rcRecycler.setLayoutManager(new LinearLayoutManager(this));
        view.rcRecycler.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.shape_divider_love, DensityUtil.dipToPx(this, 0)));
        view.rcRecycler.setAdapter(groupAdapter);
    }

    /**
     * 申请动态权限
     */
    private void applyPermission() {
        //检测权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            SnackBarUtil.show(this, view.tvWarn, "获取媒体和文件说明：", "用于补充账号信息");
//            // 如果没有权限，则申请需要的权限
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
                    .asConfirm("需要存储权限", "用于补充账号信息需要访问您的相册图片，请允许权限", () -> {
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
                .setMaxSelectNum(1)// 最大图片选择数量
                .setImageEngine(GlideEngine.createGlideEngine())
                .isDisplayCamera(!"huawei".equals(Build.MANUFACTURER.toLowerCase()))
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        imgList.clear();
                        for (LocalMedia localMedia : result) {
                            imgList.add(localMedia.getRealPath());
                        }
                        Glide.with(getBaseContext())
                                .load(imgList.get(0))
                                .apply(new RequestOptions()
                                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                        .centerCrop()// 图片裁剪方式
                                )
                                .into(imgType == 1 ? view.ivImgAddSecondRealName : view.ivImgAddProofOfPurchase);
                        //上传图片
                        initOss(imgList);
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
     * 上传图片到oss
     * @param filePaths
     */
    private void initOss(List<String> filePaths){

        filePaths.removeIf(TextUtils::isEmpty); // 内置安全删除

        // 执行上传（支持单张/多张，此处以多张为例）
        uploader.uploadMultiple(filePaths, new OSSImageUploader.UploadCallback() {
            @Override
            public void onSuccess(List<String> imageUrls) {
                Log.i(TAG, "上传成功"+imageUrls.toString());
                if (imgType == 1){
                    authentication_image = imageUrls.get(0);
                }else if (imgType == 2){
                    account_source_image = imageUrls.get(0);
                }
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

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    @Override
    public void onBackPressed() {
        // 替换默认返回逻辑，显示确认对话框
        showExitConfirmationDialog();
    }

    private void showExitConfirmationDialog() {
        new XPopup.Builder(this)
                .asCustom(new io.openim.android.taohaoba.ui.dialog.CommonDialog(
                        this,
                        "退出后,当前编辑的信息将清空,是否确认退出",
                        new io.openim.android.taohaoba.ui.dialog.CommonDialog.OnVerificationListener() {
                            @Override
                            public void onSubmit() {
                                finish(); // 确认退出
                            }
                        }))
                .show();
    }

}
