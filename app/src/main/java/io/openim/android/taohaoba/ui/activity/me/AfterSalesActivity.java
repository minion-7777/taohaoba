package io.openim.android.taohaoba.ui.activity.me;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;
import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityAfterSalesBinding;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.PriceFormatUtils;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.me.AfterSalesVM;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;
import io.openim.android.taohaoba.widgets.RoundImageView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 售后
 */
public class AfterSalesActivity extends BaseActivity<AfterSalesVM, ActivityAfterSalesBinding> implements AfterSalesVM.ViewAction{

    private static final int PERMISSION_REQUEST_CODE = 2000;
    private BaseQuickAdapter baseQuickAdapter;
    private String TAG = "AfterSalesActivity";
    private Integer orderId;
    private String reason;
    private String img;
    private String title;
    private String gameServiceName;
    private String price;
    private String userPhone;
    private OSSImageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AfterSalesVM.class);
        bindViewDataBinding(ActivityAfterSalesBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    protected void initView() {
        userPhone = mmkv.decodeString(PreferencesKey.userPhone);
        orderId = getIntent().getIntExtra("orderId", 0);
        reason = getIntent().getStringExtra("reason");
        img = getIntent().getStringExtra("img");
        title = getIntent().getStringExtra("title");
        gameServiceName = getIntent().getStringExtra("gameServiceName");
        price = getIntent().getStringExtra("price");

        view.tvPhone.setText(userPhone);
        view.rcRecycler.setNestedScrollingEnabled(false); // 禁止RecyclerView独立滚动
        Glide.with(getBaseContext())
                .load(!TextUtils.isEmpty(img) ? convertToArray(img)[0] : "")
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(view.ivOrderGoodsImg);
        view.ivOrderGoodsName.setText(title);
        view.tvOrderGoodsAreaCode.setText(gameServiceName);
        view.tvPrice.setText(PriceFormatUtils.formatPrice(getBaseContext(), price));

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);

        uploadImageview();

        vm.getAfterSalesApplicationBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("售后申请提交成功");
            finish();
        });

    }

    // 正则表达式：以字母开头，6-20个字符（字母、数字、下划线、减号）
    private static final Pattern INPUT_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z0-9_-]{5,19}$");

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //提交
        view.tvSubmit.setOnClickListener(v-> {
            result.setLength(0);
            if (imgList.size() > 1) {
                for (int i = 1; i < imgList.size(); i++) {
                    if (result.length() > 0) {
                        result.append(","); // 如果已经有数据，先加上逗号分隔
                    }
                    result.append(imgList.get(i)); // 将 Value 添加到结果字符串中
                }
            }

            if (TextUtils.isEmpty(view.tvPhone.getText().toString())) {
                shakeAnimation(view.tvPhone);
                showToast("请输入联系电话");
                return;
            }

            if (!TextUtils.isEmpty(view.tvWechatNumber.getText().toString()) && !INPUT_PATTERN.matcher(view.tvWechatNumber.getText().toString()).matches()) {
                shakeAnimation(view.tvWechatNumber);
                showToast("请输入正确的微信号");
                return;
            }

            if (TextUtils.isEmpty(view.tvSpecificSituation.getText().toString())) {
                shakeAnimation(view.tvSpecificSituation);
                showToast("请输入申请说明");
                return;
            }

            showLoadingDialog();
            vm.add_order_for_post_sale(orderId,
                    Integer.parseInt(reason),
                    view.tvPhone.getText().toString(),
                    TextUtils.isEmpty(view.tvWechatNumber.getText().toString()) ? "" : view.tvWechatNumber.getText().toString(),
                    view.tvSpecificSituation.getText().toString(),
                    TextUtils.isEmpty(result.toString()) ? "" : result.toString());
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
                    if (TextUtils.isEmpty(o.toString()) && imgList.size() < 6) {
                        applyPermission();
                    }else if (TextUtils.isEmpty(o.toString()) && imgList.size() >= 6){
                        showToast("最多上传5张图片");
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
//            SnackBarUtil.show(this, view.toolbar, "获取媒体和文件说明：", "用于设置商品图片");
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
                .setMaxSelectNum(6-imgList.size())
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
                Log.i(TAG, "上传成功"+imageUrls.toString());
                runOnUiThread(()->{
                    imgList.addAll(imageUrls);
                    baseQuickAdapter.notifyDataSetChanged();
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

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
