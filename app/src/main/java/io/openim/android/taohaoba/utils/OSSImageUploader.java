package io.openim.android.taohaoba.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CompleteMultipartUploadResult;
import com.alibaba.sdk.android.oss.model.MultipartUploadRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.openim.android.taohaoba.config.KeyConfig;

/**
 * OSS图片上传帮助类（单例）
 * 支持单张/多张图片上传，统一回调接口
 */
public class OSSImageUploader {
    private static final String TAG = "OSSImageUploader";
    private static volatile OSSImageUploader instance;
    private OSS ossClient;
    private Context appContext;

    // OSS配置参数（从配置类获取，避免硬编码）
    private final String endpoint = KeyConfig.endpoint;
    private final String bucketName = KeyConfig.bucketName;
    private final String baseUrl = KeyConfig.osstpdz; // 图片访问基础URL
    private final String uploadDir = "images/"; // 图片上传目录


    // 私有构造器（单例）
    private OSSImageUploader(Context context) {
        this.appContext = context.getApplicationContext();
        initOSSClient();
    }

    // 获取单例实例
    public static OSSImageUploader getInstance(Context context) {
        if (instance == null) {
            synchronized (OSSImageUploader.class) {
                if (instance == null) {
                    instance = new OSSImageUploader(context);
                }
            }
        }
        return instance;
    }

    // 初始化OSS客户端
    private void initOSSClient() {
        try {
            // 从配置获取STS凭证（实际项目中建议动态获取，此处简化）
            String accessKeyId = KeyConfig.accessKeyId;
            String accessKeySecret = KeyConfig.accessKeySecret;
            String stsToken = KeyConfig.accessStsToken;

            // 创建凭证提供者
            OSSStsTokenCredentialProvider credentialProvider =
                    new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, stsToken);

            // 配置客户端
            ClientConfiguration config = new ClientConfiguration();
            config.setConnectionTimeout(15 * 1000); // 30秒连接超时
            config.setSocketTimeout(15 * 1000); // 30秒Socket超时
            config.setMaxErrorRetry(2); // 失败重试2次

            // 初始化OSS客户端
            ossClient = new OSSClient(appContext, endpoint, credentialProvider, config);
        } catch (Exception e) {
            Log.e(TAG, "OSS客户端初始化失败: " + e.getMessage());
        }
    }

    /**
     * 上传单张图片
     * @param imagePath 本地图片路径
     * @param callback 上传回调
     */
    public void uploadSingle(String imagePath, UploadCallback callback) {
        List<String> paths = new ArrayList<>();
        paths.add(imagePath);
        uploadMultiple(paths, callback);
    }

    /**
     * 上传多张图片
     * @param imagePaths 本地图片路径列表
     * @param callback 上传回调
     */
    public void uploadMultiple(List<String> imagePaths, UploadCallback callback) {
        if (ossClient == null) {
            callback.onFailure(new Exception("OSS客户端未初始化"));
            return;
        }
        if (imagePaths.isEmpty()) {
            callback.onFailure(new Exception("图片路径列表为空"));
            return;
        }

        long totalSize = calculateTotalSize(imagePaths);
        AtomicInteger completedSize = new AtomicInteger(0);
        AtomicInteger completedCount = new AtomicInteger(0);
        int imageCount = imagePaths.size();
        // 【关键修复1】用数组存储结果，索引对应原始图片顺序
        String[] resultUrlsArray = new String[imageCount];

        // 【关键修复2】使用带索引的for循环，跟踪原始顺序
        for (int i = 0; i < imageCount; i++) {
            int originalIndex = i; // 绑定当前图片的原始索引
            String path = imagePaths.get(originalIndex);
            File file = new File(path);
            if (!file.exists()) {
                callback.onFailure(new Exception("文件不存在: " + path));
                continue;
            }

            // 生成OSS对象Key（保持原有逻辑）
            String fileExt = getFileExtension(path);
            String objectKey = uploadDir + System.currentTimeMillis() +
                    "_" + generateRandomStr(4) + "." + fileExt;

            MultipartUploadRequest request = new MultipartUploadRequest(
                    bucketName,
                    objectKey,
                    path
            );

            // 进度回调（保持原有逻辑）
            request.setProgressCallback((req, currentSize, fileTotal) -> {
                if (totalSize <= 0) return;
                int progress = (int) ((completedSize.get() + currentSize) * 100.0 / totalSize);
                callback.onProgress(progress);
            });

            // 执行分片上传
            ossClient.asyncMultipartUpload(request, new OSSCompletedCallback<MultipartUploadRequest, CompleteMultipartUploadResult>() {
                @Override
                public void onSuccess(MultipartUploadRequest request, CompleteMultipartUploadResult result) {
                    String imageUrl = baseUrl + objectKey;
                    // 【关键修复3】按原始索引存入数组，保证顺序
                    resultUrlsArray[originalIndex] = imageUrl;
                    completedSize.addAndGet((int) file.length());

                    // 所有上传完成后，转换数组为列表并返回
                    if (completedCount.incrementAndGet() == imageCount) {
                        List<String> orderedResult = Arrays.asList(resultUrlsArray);
                        callback.onSuccess(orderedResult);
                    }
                }

                @Override
                public void onFailure(MultipartUploadRequest request, ClientException clientEx, ServiceException serviceEx) {
                    // 错误处理（保持原有逻辑）
                    String errorMsg = "上传失败: ";
                    if (clientEx != null) {
                        errorMsg += "本地异常: " + clientEx.getMessage();
                    } else if (serviceEx != null) {
                        errorMsg += "服务端异常: " + serviceEx.getErrorCode();
                    }
                    callback.onFailure(new Exception(errorMsg));
                }
            });
        }
    }

    // 计算文件总大小
    private long calculateTotalSize(List<String> paths) {
        long total = 0;
        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                total += file.length();
            }
        }
        return total;
    }

    // 获取文件扩展名
    private String getFileExtension(String path) {
        if (TextUtils.isEmpty(path)) return "jpg";
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1) return "jpg";
        return path.substring(lastDotIndex + 1).toLowerCase();
    }

    // 生成随机字符串（避免文件名重复）
    private String generateRandomStr(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return sb.toString();
    }

    /**
     * 上传回调接口
     */
    public interface UploadCallback {
        // 上传成功（返回图片URL列表，单张上传时列表长度为1）
        void onSuccess(List<String> imageUrls);

        // 上传失败（返回异常信息）
        void onFailure(Exception e);

        // 上传进度（0-100）
        void onProgress(int progress);
    }
}
