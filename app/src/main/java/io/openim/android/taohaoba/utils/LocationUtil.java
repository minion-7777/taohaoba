package io.openim.android.taohaoba.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 原生定位工具类（无第三方依赖）
 * 核心特性：
 * - 定位策略：优先使用GPS定位（精度高），超时/失败后自动切换至网络定位（WiFi/基站，兼容性好）
 * - 权限依赖：需调用方确保已获取 ACCESS_COARSE_LOCATION（网络定位）或 ACCESS_FINE_LOCATION（GPS定位）权限
 * - 资源管理：提供 stopLocation() 方法释放定位资源，避免内存泄漏
 * - 结果回调：通过 LocationCallback 接口返回定位结果（成功/失败）
 */
public class LocationUtil {
    private static final String TAG = "LocationUtil";
    private static final long GPS_TIMEOUT = 5000; // GPS定位超时时间（毫秒），超时后自动切换网络定位
    private static final long LOCATION_MIN_DISTANCE = 0; // 位置更新最小距离阈值（米），0表示不限制
    private static final long LOCATION_MIN_TIME = 0; // 位置更新最小时间间隔（毫秒），0表示实时更新

    private Context context; // 应用上下文（避免Activity上下文导致的内存泄漏）
    private LocationManager locationManager; // 系统定位服务管理器
    private LocationCallback callback; // 定位结果回调接口
    private Handler mainHandler = new Handler(Looper.getMainLooper()); // 主线程Handler，确保回调在UI线程执行
    private LocationListener gpsLocationListener; // GPS定位监听器
    private LocationListener networkLocationListener; // 网络定位监听器
    private Runnable gpsTimeoutRunnable; // GPS超时切换任务
    private boolean isGpsRequesting = false; // GPS定位请求状态标记
    private boolean isNetworkRequesting = false; // 网络定位请求状态标记

    /**
     * 定位结果回调接口
     * 所有回调方法均在主线程执行，可直接更新UI
     */
    public interface LocationCallback {
        /**
         * 定位成功回调
         * @param latitude 纬度（例如：39.908823）
         * @param longitude 经度（例如：116.397470）
         * @param address 地址信息（原生API不直接提供，默认空字符串，需额外通过Geocoder实现）
         */
        void onSuccess(double latitude, double longitude, String address);

        /**
         * 定位失败回调
         * @param errorMsg 失败原因描述（例如："GPS定位超时"、"权限被拒绝"等）
         */
        void onFailure(String errorMsg);
    }

    /**
     * 构造函数
     * @param context 上下文，建议传入 Application 上下文（getApplicationContext()）以避免内存泄漏
     * @param callback 定位结果回调接口实现类
     */
    public LocationUtil(Context context, LocationCallback callback) {
        this.context = context.getApplicationContext();
        this.callback = callback;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 开始定位（核心入口方法）
     * 执行流程：
     * 1. 停止已有定位请求（避免重复定位）
     * 2. 检查定位Provider可用性（GPS/网络至少一个可用）
     * 3. 优先发起GPS定位请求
     */
    public void startLocation() {
        stopLocation(); // 停止已有定位，避免资源冲突
        if (!checkLocationProvider()) {
            callback.onFailure("定位服务不可用，请检查位置权限是否开启");
            return;
        }
        requestGpsLocation(); // 优先请求GPS定位（精度更高）
    }

    /**
     * 检查定位Provider是否可用
     * @return true：GPS或网络定位至少一个可用；false：均不可用
     */
    private boolean checkLocationProvider() {
        // 检查GPS和网络定位开关状态
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return isGpsEnabled || isNetworkEnabled;
    }

    /**
     * 请求GPS定位
     * 逻辑说明：
     * - 先检查GPS开关是否开启，未开启则直接切换网络定位
     * - 注册GPS定位监听器，监听位置变化、状态变化等事件
     * - 设置15秒超时任务，超时未获取结果则切换至网络定位
     */
    private void requestGpsLocation() {
        // GPS开关未开启，直接切换网络定位
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            callback.onFailure("GPS定位不可用，请开启GPS");
            requestNetworkLocation();
            return;
        }

        isGpsRequesting = true;
        gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // GPS定位成功，处理结果
                handleLocationSuccess(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // GPS状态异常（如无信号、暂时不可用），切换网络定位
                if (status == LocationProvider.OUT_OF_SERVICE || status == LocationProvider.TEMPORARILY_UNAVAILABLE) {
                    Log.d(TAG, "GPS状态异常，切换到网络定位");
                    switchToNetworkLocation();
                }
            }

            @Override
            public void onProviderEnabled(String provider) {} // GPS被开启（无需处理，已在请求中）

            @Override
            public void onProviderDisabled(String provider) {
                // GPS被禁用，切换网络定位
                switchToNetworkLocation();
            }
        };

        // 注册GPS定位监听器（可能抛出权限异常）
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_MIN_TIME,
                    LOCATION_MIN_DISTANCE,
                    gpsLocationListener
            );
        } catch (SecurityException e) {
            // 权限被拒绝（需调用方确保已申请 ACCESS_FINE_LOCATION 权限）
            callback.onFailure("GPS定位权限被拒绝：" + e.getMessage());
            return;
        }

        // 设置GPS超时任务（15秒未返回结果则切换网络定位）
        gpsTimeoutRunnable = () -> {
            if (isGpsRequesting) {
                Log.d(TAG, "GPS定位超时，切换到网络定位");
                switchToNetworkLocation();
            }
        };
        mainHandler.postDelayed(gpsTimeoutRunnable, GPS_TIMEOUT);
    }

    /**
     * 切换到网络定位（GPS定位失败/超时后调用）
     */
    private void switchToNetworkLocation() {
        stopGpsLocation(); // 停止GPS定位相关资源
        requestNetworkLocation(); // 发起网络定位请求
    }

    /**
     * 请求网络定位（WiFi/基站定位）
     * 逻辑说明：
     * - 检查网络定位开关是否开启，未开启则回调失败
     * - 注册网络定位监听器，监听位置变化事件
     * - 网络定位精度较低（100-1000米），但室内可用性更好
     */
    private void requestNetworkLocation() {
        // 网络定位开关未开启
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            callback.onFailure("网络定位不可用，请检查网络");
            return;
        }

        isNetworkRequesting = true;
        networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 网络定位成功，处理结果
                handleLocationSuccess(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {} // 网络定位状态变化无需处理

            @Override
            public void onProviderEnabled(String provider) {} // 网络定位被开启（无需处理，已在请求中）

            @Override
            public void onProviderDisabled(String provider) {
                callback.onFailure("网络定位被禁用");
            }
        };

        // 注册网络定位监听器（可能抛出权限异常）
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_MIN_TIME,
                    LOCATION_MIN_DISTANCE,
                    networkLocationListener
            );
        } catch (SecurityException e) {
            // 权限被拒绝（需调用方确保已申请 ACCESS_COARSE_LOCATION 权限）
            callback.onFailure("网络定位权限被拒绝：" + e.getMessage());
        }
    }

    /**
     * 处理定位成功结果
     * @param location 定位结果（包含经纬度等信息）
     */
    private void handleLocationSuccess(Location location) {
        stopLocation(); // 定位成功后停止所有定位请求，释放资源
        // 原生Location API不直接提供地址信息，address默认空字符串（如需地址需额外通过Geocoder实现逆地理编码）
        mainHandler.post(() -> callback.onSuccess(
                location.getLatitude(),  // 纬度
                location.getLongitude(), // 经度
                getAddressFromLocation(location.getLatitude(), location.getLongitude()) // 地址信息（需扩展Geocoder实现）
        ));
        Log.i(TAG, "handleLocationSuccess: " + location.getLatitude() + " " + location.getLongitude() + " " + getAddressFromLocation(location.getLatitude(), location.getLongitude()));
    }

    /**
     * 停止GPS定位（释放监听器和超时任务）
     */
    private void stopGpsLocation() {
        if (isGpsRequesting && gpsLocationListener != null) {
            locationManager.removeUpdates(gpsLocationListener); // 移除GPS监听器
            gpsLocationListener = null;
            isGpsRequesting = false;
            mainHandler.removeCallbacks(gpsTimeoutRunnable); // 移除GPS超时任务
        }
    }

    /**
     * 停止网络定位（释放监听器）
     */
    private void stopNetworkLocation() {
        if (isNetworkRequesting && networkLocationListener != null) {
            locationManager.removeUpdates(networkLocationListener); // 移除网络监听器
            networkLocationListener = null;
            isNetworkRequesting = false;
        }
    }

    /**
     * 停止所有定位请求（对外暴露的资源释放方法）
     * 建议在Activity的onDestroy()中调用，避免内存泄漏
     */
    public void stopLocation() {
        stopGpsLocation();
        stopNetworkLocation();
    }

    /**
     * 通过经纬度获取地址信息（逆地理编码）
     * @param latitude 纬度
     * @param longitude 经度
     * @return 地址字符串（如"北京市东城区王府井大街88号"），解析失败返回空字符串
     */
    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            // 获取地址列表（最多返回1条结果）
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                // 拼接详细地址（可根据需求调整格式）
                StringBuilder addressBuilder = new StringBuilder();
                // 国家（可选）
//                if (!TextUtils.isEmpty(address.getCountryName())) {
//                    addressBuilder.append(address.getCountryName()).append(" ");
//                }
                // 省（可选）
                if (address.getAdminArea() != null) {
                    addressBuilder.append(address.getAdminArea());
                }
                // 市（可选）
                if (address.getLocality() != null) {
                    addressBuilder.append(address.getLocality());
                }
                // 区（可选）
                if (address.getSubLocality() != null) {
                    addressBuilder.append(address.getSubLocality());
                }
                // 街道（可选）
                if (address.getThoroughfare() != null) {
                    addressBuilder.append(address.getThoroughfare());
                }
                // 门牌号（可选）
                if (address.getFeatureName() != null) {
                    addressBuilder.append(address.getFeatureName());
                }
                return addressBuilder.toString().trim(); // 去除末尾空格
            }
        } catch (IOException e) {
            Log.e(TAG, "地址解析失败：" + e.getMessage());
            // 网络异常或Geocoder服务不可用，回调失败信息
            mainHandler.post(() -> callback.onFailure("地址解析失败，请检查网络连接"));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "经纬度参数异常：" + e.getMessage());
        }
        return ""; // 解析失败返回空字符串
    }
}