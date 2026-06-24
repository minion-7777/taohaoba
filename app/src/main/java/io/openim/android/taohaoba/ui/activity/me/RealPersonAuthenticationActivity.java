package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.bean.RealPersonAuthenticationBean;
import io.openim.android.taohaoba.databinding.ActivityRealPersonAuthenticationBinding;
import io.openim.android.taohaoba.permissions.EasyPermission;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.dialog.ConfirmInformationDialog;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.me.AuthenticationCenterVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 实人认证
 */
public class RealPersonAuthenticationActivity extends BaseActivity<AuthenticationCenterVM, ActivityRealPersonAuthenticationBinding> implements EasyPermission.PermissionCallback,AuthenticationCenterVM.ViewAction{
    private static final String TAG = "RealPersonAuthenticationActivity";
    private Context context;
    private final int REQUEST_PERMISS = 2;
    private final int REQUEST_IMAGE_CAPTURE = 3;
    private String broadcastAction = "com.idCard.result";

    String[] permissions = new String[]{
            Manifest.permission.CAMERA
    };
    private String currentPhotoPath;
    private String etNameString;
    private String etIdNumberString;
    private static final int PERMISSION_REQUEST_CODE = 2000;
    private List<String> imgList = new ArrayList<>();
    private int typeIndex;//1 .正面 2.反面 3.人脸
    private String front_img;
    private String back_img;
    private int type;//1已认证 2未认证
    private BasePopupView popupView;
    private String face_image;
    private OSSImageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AuthenticationCenterVM.class);
        bindViewDataBinding(ActivityRealPersonAuthenticationBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
    }

    private ResultReceiver resultReceiver;

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

    private class ResultReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(broadcastAction.equals(intent.getAction())){
                String result = intent.getStringExtra("OCRResult");
                Toast.makeText(context, "从广播中接收到扫描数据: " + result, Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void initView() {
        context = this;
        type = getIntent().getIntExtra("type",0);
        if (type == 1) {
            //已认证
            front_img = getIntent().getStringExtra("front_img");
            back_img = getIntent().getStringExtra("back_img");
            view.tvPortrait.setVisibility(GONE);
            view.ivPortrait.setVisibility(VISIBLE);
            view.ivPortrait.setEnabled(false);
            view.tvNationalEmblem.setVisibility(GONE);
            view.ivNationalEmblem.setVisibility(VISIBLE);
            view.ivNationalEmblem.setEnabled(false);
            view.tvSubmit.setVisibility(GONE);
            Glide.with(getBaseContext())
                    .load(front_img)
                    .apply(new RequestOptions()
                    )
                    .into(view.ivPortrait);
            Glide.with(getBaseContext())
                    .load(back_img)
                    .apply(new RequestOptions()
                    )
                    .into(view.ivNationalEmblem);
        }

        //注册扫描广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(broadcastAction);
        intentFilter.addCategory(getPackageName());
        resultReceiver = new ResultReceiver();
        registerReceiver(resultReceiver, intentFilter);

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);

        vm.getVerifyInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner() , it->{
            dismissLoadingDialog();
            etNameString = it.getRealname();
            etIdNumberString= it.getNumber();
            onDialog(it.getRealname(), it.getNumber());
        });

        view.tvPortrait.setOnClickListener(v->{
            typeIndex = 1;
            applyPermission();
        });

        view.ivPortrait.setOnClickListener(v->{
            typeIndex = 1;
            applyPermission();
        });

        view.tvNationalEmblem.setOnClickListener(v->{
            typeIndex = 2;
            applyPermission();
        });

        view.ivNationalEmblem.setOnClickListener(v->{
            typeIndex = 2;
            applyPermission();
        });

        view.tvSubmit.setOnClickListener(v->{
            if (TextUtils.isEmpty(front_img)) {
                shakeAnimation(view.tvPortrait);
                showToast("请上传身份证正面");
                return;
            }
            if (TextUtils.isEmpty(back_img)) {
                shakeAnimation(view.tvNationalEmblem);
                showToast("请上传身份证反面");
                return;
            }

            showLoadingDialog();
            vm.verify_info(front_img, back_img);

//            etNameString = "陈青";
//            etIdNumberString= "430421199708138211";
//            onDialog(etNameString, etIdNumberString);
        });

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

    }

    private void requestPermission(){
        if(EasyPermission.hasPermissions(context, permissions)){
            initPop();
        }else{
            EasyPermission.with(this)
                    .rationale("人脸检测")
                    .addRequestCode(REQUEST_PERMISS)
                    .permissions(permissions)
                    .request();
        }
    }

    /**
     * 实人认证确认信息弹窗
     */
    private void initPop(){
        // 1. 创建Intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 2. 确保有相机应用可以处理该Intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // 关键参数：指定摄像头类型
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1); // 1 代表前置
            // 3. 创建文件保存照片
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // 处理错误
            }

            // 4. 如果文件创建成功
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                        BuildConfig.APPLICATION_ID + ".fileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {
        Toast.makeText(context, "没有相机权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(resultReceiver); //记得要注销
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            File imgFile = new File(currentPhotoPath);
            Log.d(TAG, "imgFile.exists()="+imgFile.exists());
            typeIndex = 3;
            //上传图片
            imgList.clear();
            imgList.add(currentPhotoPath);
            initOss(imgList);
            showLoadingDialog();
        }
    }

    private File createImageFile() throws IOException {
        // 创建唯一文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* 前缀 */
                ".jpg",        /* 后缀 */
                storageDir     /* 目录 */
        );
        // 保存文件路径供后续使用
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    /**
     * 申请动态权限
     */
    private void applyPermission() {
        //检测权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            SnackBarUtil.show(this, view.toolbar, "获取媒体和文件说明：", "用于上传身份信息");
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
                    .asConfirm("需要存储权限", "设置上传身份信息需要访问您的相册图片，请允许权限", () -> {
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
        if (requestCode == REQUEST_PERMISS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户同样授权
                initPop();
            }else {
                // 用户拒绝授权
                Toast.makeText(this, "你拒绝使用相机权限！", Toast.LENGTH_SHORT).show();
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
            public void onSuccess(List<String> allPath) {
                runOnUiThread(()->{
                    if (typeIndex == 1){
                        front_img = allPath.get(0);
                        view.tvPortrait.setVisibility(GONE);
                        view.ivPortrait.setVisibility(VISIBLE);
                        Glide.with(getBaseContext())
                                .load(allPath.get(0))
                                .apply(new RequestOptions())
                                .into(view.ivPortrait);
                    }else if (typeIndex == 2){
                        back_img = allPath.get(0);
                        view.tvNationalEmblem.setVisibility(GONE);
                        view.ivNationalEmblem.setVisibility(VISIBLE);
                        Glide.with(getBaseContext())
                                .load(allPath.get(0))
                                .apply(new RequestOptions())
                                .into(view.ivNationalEmblem);
                    }else if (typeIndex == 3){
                        face_image = allPath.get(0);
                        userFace();
                    }

                });
            }

            @Override
            public void onFailure(Exception e) {
                showToast("上传失败: " + e.getMessage());
            }

            @Override
            public void onProgress(int progress) {
                // 可在此更新进度条UI
            }
        });
    }

    private void userFace(){
        Parameter parameterUserFace = new Parameter();
        parameterUserFace.add("face_image", face_image);
        parameterUserFace.add("name", etNameString);
        parameterUserFace.add("cardNo", etIdNumberString);
        N.APIThb(OpenIMService.class)
                .userFace(parameterUserFace.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(RealPersonAuthenticationBean.class))
                .subscribe(new NetObserverThb<RealPersonAuthenticationBean>(context) {
                    @Override
                    public void onSuccess(RealPersonAuthenticationBean it) {
                        runOnUiThread(()->{
                            dismissLoadingDialog();
                            if (it.getStatus() == 1 && it.getFaceMatched() == 1) {
                                showToast("人脸识别成功");
                                finish();
                            }else {
                                if (it.getStatus() != 1) {
                                    showToast("姓名身份证认证不通过");
                                }
                                if (it.getFaceMatched() != 1) {
                                    showToast("人脸识别失败");
                                }
                            }
                        });
                    }

                    @Override
                    protected void onFailure(BaseTHB e) {
                        runOnUiThread(()->{
                            dismissLoadingDialog();
                            showToast(e.msg);
                        });
                    }
                });
    }

    /**
     * 个人信息
     * @param name
     * @param number
     */
    private void onDialog(String name, String number){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new ConfirmInformationDialog(this, name, number, new ConfirmInformationDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        //扫描之前,提前申请权限..
                        requestPermission();
                    }
                })).show();
    }

}
