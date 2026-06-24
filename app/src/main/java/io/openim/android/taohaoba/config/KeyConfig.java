package io.openim.android.taohaoba.config;

/**
 * key
 */
public  class KeyConfig {

    //=============================================阿里oss=============================================
    //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数）
    public static final String accessKeyId = "LTAI5t8RAk4yy4ELSVNrmki2";
    public static final String accessKeySecret = "qDGKVKMBe3GdmsX66ynisYUSLOoKB6";
    public static final String accessStsToken = "";
    public static final String bucketName = "taohao8";
    public static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    //----------TODO 以上数据为了安全直接从后台取 但是也可以直接使用--------------
    public static final String osstpdz = "https://taohao8.oss-cn-beijing.aliyuncs.com/";//图片统一访问地址  静态地址路径
    //----------TODO 以上数据为了安全直接从后台取 但是也可以直接使用--------------
    public static final String NOTIFICATION_PERMISSION = "notification_permission";//通知权限 boolean
    public static final String IS_PRIVACY = "is_privacy";//隐私弹窗权限 boolean

}

