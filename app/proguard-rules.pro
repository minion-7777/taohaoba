# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.alibaba.sdk.android.oss.** { *; }
-dontwarn okio.**
-dontwarn org.apache.commons.codec.binary.**
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}


-dontoptimize
-dontpreverify

#==================gson && protobuf==========================
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-dontwarn com.squareup.okhttp.**

-keepattributes InnerClasses
-keepattributes EnclosingMethod

-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}

-keep class com.luck.picture.lib.** { *; }
# 如果引入了Camerax库请添加混淆
-keep class com.luck.lib.camerax.** { *; }

# 如果引入了Ucrop库请添加混淆
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#更新
-dontwarn com.king.app.updater.**
-keep class com.king.app.updater.**{ *;}
-keep class * extends com.king.app.updater.**{ *;}
-keep class * implements com.king.app.updater.**{ *;}
-keepattributes InnerClasses

-keep public class * extends android.app.Service

-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# 保留所有公共类和方法以便反射使用
-keep public class * {
    public *;
}

# 避免混淆使用了反射的类
-keep class io.openim.android.taohaoba.** { *; }

# 避免混淆使用了反射的类
-keep class io.openim.android.ouicore.** { *; }

# 保留所有序列化的类
-keepnames class * implements java.io.Serializable

-keep class com.tencent.qcloud.** { *; }
-keep class com.tencent.timpush.** { *; }

-keep class com.tencent.imsdk.** { *; }
-keep class * implements com.tencent.qcloud.tuicore.interfaces.TUIInitializer {}
