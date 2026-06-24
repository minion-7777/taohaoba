package com.tencent.qcloud.tuikit.tuichat.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.net.RXRetrofit.Exception.RXRetrofitException;
import io.openim.android.ouicore.net.RXRetrofit.Exception.ThbRetrofitException;
import io.openim.android.ouicore.net.bage.Base;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.net.bage.GsonHel;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ThbApiService {
    static <T> Function<ResponseBody, T> turn(Class<T> tClass) {
        return responseBody -> {
            String body = responseBody.string();
            Base<T> base = GsonHel.dataObject(body, tClass);
            if (base.errCode == 0)
                return null == base.data ? tClass.newInstance() : base.data;
            throw new RXRetrofitException(base.errCode, base.errDlt);
        };
    }

    static <T> Function<ResponseBody, T> turnThb(Class<T> tClass) {
        return responseBody -> {
            String body = responseBody.string();
            Log.d("ThbApiService", "turnThb-body="+body);
            BaseTHB<T> baseThb = GsonHel.dataObjectThb(body, tClass);
            if (baseThb.code == 200) {
                return null == baseThb.data ? tClass.newInstance() : baseThb.data;
            } else {
                Log.d("ThbApiService", "turnThb-body-非200=" + baseThb.code + "," + baseThb.msg + "," + baseThb.error);
//                throw new ThbRetrofitException(baseThb.code, baseThb.error);
                throw new ThbRetrofitException(baseThb.code, body);
            }
        };
    }

    static <T> Function<ResponseBody, List<T>> listTurnThb(Class<T> tClass) {
        return responseBody -> {
            String body = responseBody.string();
            BaseTHB<List<T>> base = GsonHel.dataArrayThb(body, tClass);
            if (base.code == 200) return null == base.data ? new ArrayList<>() : base.data;
            throw new ThbRetrofitException(base.code, base.msg);
        };
    }

    /**
     * 买家和卖家订单列表显示
     */
    @POST("/api/order/order_list")
    Observable<ResponseBody> orderOrderList(@Body RequestBody req);

    /**
     * 商品管理列表
     */
    @POST("/api/goods/goods_list")
    Observable<ResponseBody> goodsGoodsList(@Body RequestBody req);

    /**
     * 发送自定义消息
     */
    @POST("/api/im_chat/send_customize_msg")
    Observable<ResponseBody> sendCustomizeMsg(@Body RequestBody req);

     /**
     * 根据im_group_id获取群组信息
     */
    @POST("/api/im_group/get_groupinfo_by_imgroupid")
    Observable<ResponseBody> getGroupInfoByImGroupId(@Body RequestBody req);

    /**
     * 修改订单状态和更改订单价格
     */
    @POST("/api/order/order_status_set")
    Observable<ResponseBody> setOrderStatus(@Body RequestBody req);

    /**
     *  获取游戏账号密码信息
     */
    @POST("/api/game/get_game_account_value")
    Observable<ResponseBody> getGameAccountValue(@Body RequestBody req);

    /**
     *  会员三种认证信息
     */
    @POST("/api/user/auth_info")
    Observable<ResponseBody> auth_info();

    /**
     *  实名认证
     */
    @POST("/api/user/realname")
    Observable<ResponseBody> realname(@Body RequestBody req);

    /**
     *  文本过滤
     */
    @POST("/api/auth/text_filter")
    Observable<ResponseBody> text_filter(@Body RequestBody req);

    /**
     *  图片过滤
     */
    @POST("/api/auth/img_filter")
    Observable<ResponseBody> img_filter(@Body RequestBody req);

    /**
     *  提交举报
     */
    @POST("/api/report/submit")
    Observable<ResponseBody> submitReport(@Body RequestBody req);
}
