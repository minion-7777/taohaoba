package io.openim.android.taohaoba.ui.activity.me;

import static io.openim.android.taohaoba.utils.BadgeNumberUtil.getMobileType;
import static io.openim.android.taohaoba.utils.PriceFormatUtils.hideMiddleSixDigits;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.king.app.updater.AppUpdater;
import com.king.app.updater.callback.UpdateCallback;
import com.king.app.updater.http.OkHttpManager;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.ActivityManager;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.BalanceBean;
import io.openim.android.taohaoba.databinding.ActivitySettingBinding;
import io.openim.android.taohaoba.ui.dialog.AppUpgradeDialog;
import io.openim.android.taohaoba.ui.dialog.SecurityVerificationDialog;
import io.openim.android.taohaoba.ui.main.MainActivity;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.me.WalletVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

public class SettingActivity extends BaseActivity<WalletVM, ActivitySettingBinding> implements WalletVM.ViewAction{

    private CountDownTimer countDownTimer;
    private static final long START_TIME_IN_MILLIS = 60000; // 60秒倒计时
    private boolean isTimerRunning = false;
    private TextView tv_send;
    private BalanceBean balanceBean;
    private static final int PERMISSION_REQUEST_CODE = 2000;
    private List<String> imgList = new ArrayList<>();
    private BasePopupView appUpgradeDialog;
    private BasePopupView popupView;
    private OSSImageUploader uploader;
    private String avatar;
    private int typeAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(WalletVM.class);
        bindViewDataBinding(ActivitySettingBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }


    protected void initView() {

        view.tvVersionName.setText((BuildConfig.DEBUG ? "测试" : "") + "版本号v"+BuildConfig.VERSION_NAME);

        //会员钱包余额
        vm.getBalanceBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            balanceBean = it;
            view.tvName.setText(it.getUser().getNickname());
            Glide.with(this)
                    .load(it.getUser().getAvatar())
                    .apply(new RequestOptions()
                            .centerCrop()// 图片裁剪方式
                            .placeholder(R.mipmap.ic_profile_picture)// 加载中的占位图
                            .error(R.mipmap.ic_profile_picture)// 加载失败的占位图
                    )
                    .into(view.ivHead);
            view.tvPhone.setText(hideMiddleSixDigits(it.getUser().getUsername()));
        });

        //发送验证码
        vm.getSendSmsLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            showToast("验证码发送成功");
            startCountdown();
            tv_send.setEnabled(false);  // 禁用发送按钮
        });

        vm.getSetUserInfoBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{

            if (typeAvatar == 1){
                showToast("修改成功");
                typeAvatar = 0;
                Glide.with(getBaseContext())
                        .load(avatar)
                        .apply(new RequestOptions()
                                .centerCrop()// 图片裁剪方式
                                .error(R.mipmap.ic_launcher)// 加载失败的占位图
                        )
                        .into(view.ivHead);
            }
        });

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);

        //版本更新获取
        vm.getVersionManageBeanMutableLiveData().observe(this, it->{
            //<更新  >=不更新
            if (BuildConfig.VERSION_CODE < Integer.valueOf(it.getInfo().getVersion_code())) {
                Upgrade(it.getInfo().getDownload_url(), it.getInfo().getIs_force_update() == 1, Integer.valueOf(it.getInfo().getVersion_code()), it.getInfo().getVersion_name(), it.getInfo().getDescription());
            }else {
                showToast("已是最新版本");
            }
        });

    }

    private void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.clVersionName.setOnClickListener(v->{
            vm.get_version_manage(BuildConfig.FLAVOR);
        });

        //账户注销
        view.tvAccountCancellation.setOnClickListener(v->{
            startActivity(new Intent(this, AccountCancellationActivity.class));
        });

        //修改手机号
//        view.clChangePhoneNumber.setOnClickListener(v->{
//            if (balanceBean != null){
//                SecurityVerificationPop();
//            }
//        });

        //认证中心
        view.llRenzhengzhongxin.setOnClickListener(v->{
            startActivity(new Intent(this, AuthenticationCenterActivity.class));
        });

        view.tvChangePassword.setOnClickListener(v->{
            startActivity(new Intent(this, ChangePasswordActivity.class)
                    .putExtra("phone", balanceBean != null ? balanceBean.getUser().getUsername() : ""));
        });

        view.clNickname.setOnClickListener(v->{
            startActivity(new Intent(this, NicknameActivity.class));
        });

        view.tvPushSetting.setOnClickListener(v->{
            startActivity(new Intent(this, PushSettingActivity.class));
        });

        view.tvAboutUs.setOnClickListener(v->{
            startActivity(new Intent(this, AboutUsActivity.class));
        });

        view.tvLoginOut.setOnClickListener(v->{
            onDialog(1, "您确定要退出登录吗？");
        });

        //修改头像
        view.ivHead.setOnClickListener(v->{
            applyPermission();
        });
    }

    /**
     * 验证手机号弹窗
     */
    private void SecurityVerificationPop(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new SecurityVerificationDialog(this, balanceBean.getUser().getUsername(), new SecurityVerificationDialog.OnVerificationListener() {
                    @Override
                    public void onSend(TextView view) {
                        //发送验证码
                        tv_send = view;
                        vm.send_sms(balanceBean.getUser().getUsername());
                    }

                    @Override
                    public void onSubmit(String code) {
                        startActivity(new Intent(getBaseContext(), ChangePhoneNumberActivity.class)
                                .putExtra("code", code));
                    }
                })).show();
    }


    // 启动倒计时
    private void startCountdown() {
        if (isTimerRunning) {
            return; // 如果已经在倒计时中，直接返回
        }

        countDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tv_send.setText(millisUntilFinished / 1000 + "秒后可重新发送");
            }

            @Override
            public void onFinish() {
                tv_send.setEnabled(true); // 启用发送按钮
                tv_send.setText("重新发送"); // 更改按钮文本
                isTimerRunning = false;
            }
        }.start();

        isTimerRunning = true;
    }

    /**
     * 申请动态权限
     */
    private void applyPermission() {
        //检测权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            SnackBarUtil.show(this, view.toolbar, "获取媒体和文件说明：", "用于设置头像");
//            // 如果没有权限，则申请需要的权限
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
//        }else {
//            // 已经申请了权限
//            setPhoto();
//        }

        // 适配Android 13+的媒体权限
        String permission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES :
                Manifest.permission.READ_EXTERNAL_STORAGE;

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // 显示权限说明弹窗
            new XPopup.Builder(this)
                    .asConfirm("需要存储权限", "设置头像需要访问您的相册图片，请允许权限", () -> {
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

                    typeAvatar = 1;
                    avatar = allPath.get(0);

                    vm.set_user_info(
                            1,
                            allPath.get(0),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // 释放倒计时资源
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        vm.getBalance();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }

    /**
     * 版本更新
     * @param apkUrl
     * @param forcedUpgrade
     * @param apkVersionCode
     * @param apkVersionName
     * @param apkDescription
     */
    private void Upgrade(String apkUrl, boolean forcedUpgrade, int apkVersionCode, String apkVersionName, String apkDescription){
        //"https://downv6.qq.com/qqweb/QQ_1/android_apk/Android_9.0.81_64.apk"
        appUpgradeDialog = new XPopup.Builder(this)
                .isViewMode(false)
                .dismissOnBackPressed(false)
                .dismissOnTouchOutside(false)
                .asCustom(new AppUpgradeDialog(this, forcedUpgrade, apkVersionName, apkDescription, new AppUpgradeDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        AppUpdater appUpdater = new AppUpdater.Builder(getBaseContext())
                                .setUrl(apkUrl)
                                .setFilename("taohaoba.apk")
                                .setInstallApk(true)
                                .setNotificationIcon(R.mipmap.ic_launcher)
                                .setShowPercentage(true)
                                .setVersionCode(apkVersionCode)
                                .build();
                        appUpdater.setHttpManager(OkHttpManager.getInstance()) // 使用OkHttp的实现进行下载
                                .setUpdateCallback(new UpdateCallback() { // 更新回调
                                    @Override
                                    public void onDownloading(boolean isDownloading) {
                                        // 下载中：isDownloading为true时，表示已经在下载，即之前已经启动了下载；为false时，表示当前未开始下载，即将开始下载
                                    }

                                    @Override
                                    public void onStart(String url) {
                                        // 开始下载
                                        showToast("开始下载");
                                    }

                                    @Override
                                    public void onProgress(long progress, long total, boolean isChanged) {
                                        // 下载进度更新：建议在isChanged为true时，才去更新界面的进度；因为实际的进度变化频率很高
                                    }

                                    @Override
                                    public void onFinish(File file) {
                                        // 下载完成
                                        showToast("下载完成");
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        // 下载失败
                                        showToast("下载失败");
                                    }

                                    @Override
                                    public void onCancel() {
                                        // 取消下载
                                        showToast("取消下载");
                                    }
                                }).start();
                        if (!forcedUpgrade) {
                            appUpgradeDialog.dismiss();
                        }
                    }
                })).show();
    }

    /**
     *
     * @param type 1退出登录
     */
    private void onDialog(int type, String hint){
        popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new io.openim.android.taohaoba.ui.dialog.CommonDialog(this, hint, new io.openim.android.taohaoba.ui.dialog.CommonDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        if (type == 1) {
                            showLoadingDialog();
                            imLogout();
                        }else if (type == 2) {
                            showLoadingDialog();
                            vm.user_disable();
                        }
                    }
                })).show();
    }

    private void imLogout(){
        V2TIMManager.getInstance().logout(new V2TIMCallback() {
            @Override
            public void onSuccess() {
                Log.i("imsdk", "退出登录成功");
                dismissLoadingDialog();
                FirebaseMessaging.getInstance().deleteToken();
                mmkv.clearAll();
                ActivityManager.finishAllExceptActivity();
                launchActivity(MainActivity.class);
            }

            @Override
            public void onError(int code, String desc) {
                dismissLoadingDialog();
                Log.i("imsdk", "退出登录失败, code:" + code + ", desc:" + desc);
            }
        });
    }

}
