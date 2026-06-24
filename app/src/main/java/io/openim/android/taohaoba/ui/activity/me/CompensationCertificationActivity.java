package io.openim.android.taohaoba.ui.activity.me;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.GlideEngine;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.databinding.ActivityCompensationCertificationBinding;
import io.openim.android.taohaoba.ui.dialog.PermissionDescriptionDialog;
import io.openim.android.taohaoba.utils.LocationUtil;
import io.openim.android.taohaoba.utils.OSSImageUploader;
import io.openim.android.taohaoba.utils.SnackBarUtil;
import io.openim.android.taohaoba.vm.me.AuthenticationCenterVM;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;
import io.openim.android.taohaoba.widgets.RoundImageView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 包赔认证
 */
public class CompensationCertificationActivity extends BaseActivity<AuthenticationCenterVM, ActivityCompensationCertificationBinding> implements AuthenticationCenterVM.ViewAction {

    private static final int LOCATION_PERMISSION_CODE = 1001;
    private static final int PERMISSION_REQUEST_CODE = 1002;
    private static final int PERMISSION_REQUEST_CODE1 = 2000;
    private ActivityResultLauncher<Intent> resultLauncher;

    private ArrayList<String> contactsList = new ArrayList<>();
    private BaseQuickAdapter baseQuickAdapter;
    private String addressText;
//    private String name;
//    private String phone;
    private String reparation_image;
    private int type;
    private double lat;
    private double lng;
//    private AMapLocationClient locationClient = null;
    private OSSImageUploader uploader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(AuthenticationCenterVM.class);
        bindViewDataBinding(ActivityCompensationCertificationBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {

        type = getIntent().getIntExtra("type", 1);
        reparation_image = getIntent().getStringExtra("reparation_image");
        String emergency_contact = getIntent().getStringExtra("emergency_contact");
        String emergency_phone = getIntent().getStringExtra("emergency_phone");
        String address = getIntent().getStringExtra("address");
        String contacts = getIntent().getStringExtra("contacts");

        //认证状态 0:基本待审核、1:基本通过 2:基本驳回3:人脸待审核4:人脸通过5:人脸驳回 6:包赔待认证 7：包赔认证中 8：包赔认证已认证 9：包赔认证失败
        if (type == 6) {
            //未认证
        }else {
            view.tvName.setText(emergency_contact);
            view.tvPhone.setText(emergency_phone);
            view.tvSpecificSituation.setText(address);
            if (!TextUtils.isEmpty(contacts)) {
                Type listType = new TypeToken<ArrayList<String>>() {}.getType();
                contactsList.addAll(new Gson().fromJson(contacts, listType));
            }
            Log.i("Contacts", new Gson().toJson(contactsList));
            if (type == 7 || type == 8) {
                //审核中 已认证
                view.tvNotObtained.setText("已获取");
                view.tvSubmit.setVisibility(GONE);
                view.tvName.setEnabled(false);
                view.tvPhone.setEnabled(false);
                view.tvNotObtained.setEnabled(false);
                view.tvSpecificSituation.setFocusable(false);
                view.tvSpecificSituation.setFocusableInTouchMode(false);
            }
        }

        // 初始化oss 获取上传帮助类实例
        uploader = OSSImageUploader.getInstance(this);

        uploadImageview();

        vm.getReparationVerifyBeanMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("提交成功");
            finish();
        });

    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //获取当前定位
        view.tvNotObtained.setOnClickListener(v->{
            initPop();
        });

        //获取联系人
        view.tvName.setOnClickListener(v->{
            checkContactPermission();
        });
        view.tvPhone.setOnClickListener(v->{
            checkContactPermission();
        });

        // 注册 ActivityResultLauncher
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // 处理返回的结果
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // 处理返回的数据
                            Uri contactUri = data.getData();
                            getContactInfo(contactUri); // 解析联系人详情
                        }
                    }
                });

        //提交
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

            if (TextUtils.isEmpty(view.tvName.getText().toString().trim())) {
                shakeAnimation(view.tvName);
                showToast("请填写紧急联系人姓名");
                return;
            }
            if (TextUtils.isEmpty(view.tvPhone.getText().toString().trim())) {
                shakeAnimation(view.tvPhone);
                showToast("请填写紧急联系人电话");
                return;
            }
            if (view.tvPhone.getText().toString().length() != 11) {
                shakeAnimation(view.tvPhone);
                showToast("紧急联系人号码错误");
                return;
            }
            if (view.tvNotObtained.getText().toString().trim().equals("未获取")) {
                shakeAnimation(view.tvNotObtained);
                showToast("请授权定位信息");
                return;
            }
            if (TextUtils.isEmpty(result.toString())) {
                showToast("请上传截图凭证");
                return;
            }

            showLoadingDialog();
            vm.reparation_verify(
                    result.toString(),
                    view.tvName.getText().toString().trim(),
                    view.tvPhone.getText().toString().replaceAll("\\s+", ""),
                    lng+","+lat,
                    new Gson().toJson(contactsList),
                    view.tvSpecificSituation.getText().toString()
            );
        });

    }

    /**
     * 权限说明弹窗
     */
    private void initPop(){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new PermissionDescriptionDialog(this, new PermissionDescriptionDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit() {
                        checkLocationPermission();
                    }
                })).show();
    }

    /**
     * 检查通讯录权限状态
     */
    private void checkContactPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            SnackBarUtil.show(this, view.toolbar, "获取联系人信息：", "用于包赔认证");
            // 动态请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    PERMISSION_REQUEST_CODE);
        } else {
            loadContacts();
        }
    }

    /**
     * 读取通讯录核心方法
     */
    private void loadContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        resultLauncher.launch(intent);

        contactsList.clear();

        // 定义查询字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        // 执行查询
        try (Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // 处理联系人数据
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phone = cursor.getString(cursor.getColumnIndexOrThrow(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER))
                            .replace(" ", "");  // 移除号码中的空格

                    contactsList.add(name + ":" + phone);
                } while (cursor.moveToNext());
            }
        } catch (SecurityException e) {
            Log.e("Contacts", "权限异常: " + e.getMessage());
        }
        Log.i("Contacts", contactsList.toString());
    }

    /**
     * 获取单个联系人信息
     * @param uri
     */
    private void getContactInfo(Uri uri) {
        String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}; // 查询的列名
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME); // 获取姓名列的索引
            int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER); // 获取电话号码列的索引
//            // 获取姓名
//            name = cursor.getString(nameIndex);
//            // 获取电话号码，注意可能需要清理格式或去除空格等处理
//            phone = processPhoneNumber(cursor.getString(numberIndex));
            view.tvName.setText(cursor.getString(nameIndex));
            view.tvPhone.setText(processPhoneNumber(cursor.getString(numberIndex)));
            cursor.close(); // 关闭Cursor资源
            // 使用获取到的姓名和电话号码进行后续操作，例如显示在UI上或进行存储等。
        }
    }

    public static String processPhoneNumber(String rawNumber) {
        String cleaned = cleanPhoneNumber(rawNumber);
        if (isValidChineseMobile(cleaned)) {
            return cleaned; // 返回合法手机号
        }
        return null; // 非法号码返回 null
    }

    public static String cleanPhoneNumber(String rawNumber) {
        if (rawNumber == null) return "";
        return rawNumber.replaceAll("[^0-9]", ""); // 删除非数字字符
    }

    public static boolean isValidChineseMobile(String number) {
        String regex = "^(?:(?:\\+|00)86)?1" // 可选国际前缀
                + "(?:3[0-9]|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])" // 号段规则
                + "\\d{8}$"; // 后接8位数字
        return number.matches(regex);
    }

    //获取定位权限
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_CODE
            );
        } else {
            startLocationUpdates();
        }
    }

    // 新增定位工具类实例和回调
    private LocationUtil locationUtil;

    private void startLocationUpdates() {
//        // 初始化定位客户端
//        try {
//            locationClient = new AMapLocationClient(getApplicationContext());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        // 配置定位参数
//        AMapLocationClientOption option = new AMapLocationClientOption();
//        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy); // 高精度模式
//        option.setOnceLocation(true); // 单次定位
//        option.setNeedAddress(true); // 返回地址信息
//
//        // 设置定位监听器
//        locationClient.setLocationListener(new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation location) {
//                if (location != null && location.getErrorCode() == 0) {
//                    // 定位成功，获取经纬度和地址
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//                    addressText = location.getAddress();
//                    runOnUiThread(()->{
//                        view.tvNotObtained.setText("已获取");
//                        view.tvSpecificSituation.setText(addressText);
//                    });
//                    locationClient.stopLocation();
//                    Log.d("Location", "Lat: " + lat + ", Lng: " + lng + ", Address: " + addressText);
//                } else {
//                    Log.e("Location", "Error: " + location.getErrorCode() + ", " + location.getErrorInfo());
//                }
//            }
//        });
//
//        // 启动定位
//        locationClient.setLocationOption(option);
//        locationClient.startLocation();

        // 创建定位工具类实例，传入上下文和回调
        locationUtil = new LocationUtil(this, new LocationUtil.LocationCallback() {
            @Override
            public void onSuccess(double latitude, double longitude, String address) {
                dismissLoadingDialog();
                // 定位成功，更新UI
                lat = latitude;
                lng = longitude;
                addressText = address;
                runOnUiThread(() -> {
                    view.tvNotObtained.setText("已获取");
                    view.tvSpecificSituation.setText(addressText);
                });
            }

            @Override
            public void onFailure(String errorMsg) {
                dismissLoadingDialog();
                // 定位失败，提示用户
                runOnUiThread(() -> {
                    showToast(errorMsg);
                    view.tvNotObtained.setText("获取失败");
                });
                // 定位失败：判断是否为位置开关未开启
                if (errorMsg.contains("定位服务不可用") || errorMsg.contains("GPS定位不可用") || errorMsg.contains("网络定位不可用")) {
                    showLocationEnableGuideDialog(); // 显示位置开关开启引导对话框
                } else {
                    showToast("定位失败：" + errorMsg);
                }
            }
        });
        showLoadingDialog();
        // 启动定位
        locationUtil.startLocation();
    }

    private StringBuilder result = new StringBuilder();

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
                dismissLoadingDialog();
                runOnUiThread(()->{
                    imgList.addAll(imageUrls);
                    baseQuickAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(Exception e) {
                dismissLoadingDialog();
                showToast("上传失败: " + e.getMessage());
            }

            @Override
            public void onProgress(int progress) {
                // 可在此更新进度条UI
            }
        });
    }

    private List<String> imgList = new ArrayList<>();
    //图片列表
    private void uploadImageview(){
        if (type == 6 || type == 9) {
            imgList.add("");
        }
        if (!TextUtils.isEmpty(reparation_image)) {
            String[] img = reparation_image.split(",");
            for (String s : img) {
                imgList.add(s);
            }
        }

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_upload_img, imgList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {

                RoundImageView iv_img_add = baseViewHolder.getView(R.id.iv_img_add);
                ImageView iv_img_delete = baseViewHolder.getView(R.id.iv_img_delete);

                if (!TextUtils.isEmpty(o.toString())) {
                    Glide.with(baseViewHolder.itemView.getContext())
                            .load(o.toString())
                            .apply(new RequestOptions()
                                    .placeholder(R.mipmap.ic_upload_pictures)// 加载中的占位图
                                    .error(R.mipmap.ic_upload_pictures)// 加载失败的占位图
                                    .centerCrop()// 图片裁剪方式
                            )
                            .into(iv_img_add);
                }else {
                    iv_img_add.setImageResource(R.mipmap.ic_upload_pictures);
                }

                if (type != 7 && type != 8) {
                    iv_img_delete.setVisibility(TextUtils.isEmpty(o.toString()) ? INVISIBLE : VISIBLE);
                }

                iv_img_delete.setOnClickListener(v->{
                    imgList.remove(o);
                    notifyDataSetChanged();
                });

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (type == 6 || type == 9) {
                        if (TextUtils.isEmpty(o.toString())) {
                            applyPermission();
                        }
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            SnackBarUtil.show(this, view.toolbar, "获取媒体和文件说明：", "用于补充用户信息");
            // 如果没有权限，则申请需要的权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE1);
        }else {
            // 已经申请了权限
            setPhoto();
        }
    }

    //图片选择
    private void setPhoto(){

        PictureSelector.create(this)
                .openGallery(SelectMimeType.ofImage())
                .setImageEngine(GlideEngine.createGlideEngine())
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "需要位置权限才能获取坐标", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadContacts();
            } else {
                Toast.makeText(this, "需要通讯录权限才能加载联系人", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == PERMISSION_REQUEST_CODE1) {
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
     * 显示位置开关开启引导对话框
     */
    private void showLocationEnableGuideDialog() {
        new AlertDialog.Builder(this)
                .setTitle("位置服务未开启")
                .setMessage("包赔认证需要获取位置信息，请开启位置服务")
                .setPositiveButton("去开启", (dialog, which) -> {
                    // 打开系统位置服务设置页面
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .setCancelable(false) // 不可取消，确保用户处理
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (locationClient != null) {
//            locationClient.stopLocation();
//            locationClient.onDestroy();
//        }
        if (locationUtil != null) {
            locationUtil.stopLocation();
        }
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        showToast(msg);
    }
}
