package io.openim.android.taohaoba.repository;

;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.net.RXRetrofit.Exception.RXRetrofitException;
import io.openim.android.ouicore.net.RXRetrofit.Exception.ThbRetrofitException;
import io.openim.android.ouicore.net.bage.Base;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.net.bage.GsonHel;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface OpenIMService {

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
            Log.d("OpenIMService", "turnThb-body=" + body);
            BaseTHB<T> baseThb = GsonHel.dataObjectThb(body, tClass);
            if (baseThb.code == 200) {
                return null == baseThb.data ? tClass.newInstance() : baseThb.data;
            } else {
                Log.d("OpenIMService", "turnThb-body-非200=" + baseThb.code + "," + baseThb.msg + "," + baseThb.error);
                throw new ThbRetrofitException(baseThb.code, body);
            }
        };
    }

    static <T> Function<ResponseBody, List<T>> listTurnThb(Class<T> tClass) {
        return responseBody -> {
            String body = responseBody.string();
            Log.d("NetDebug", "listTurnThb-body="+body);
            BaseTHB<List<T>> baseThb = GsonHel.dataArrayThb(body, tClass);
            if (baseThb.code == 200) {
                return null == baseThb.data ? new ArrayList<>() : baseThb.data;
            } else {
                Log.d("NetDebug", "listTurnThb-body-非200=" + baseThb.code + "," + baseThb.msg + "," + baseThb.error);
                throw new ThbRetrofitException(baseThb.code, baseThb.error);
            }
        };
    }

    /**
     * 登录
     */
    @POST("/api/login")
    Observable<ResponseBody> appLogin(@Body RequestBody req);

    /**
     * 发送验证码
     */
    @POST("/api/send_sms")
    Observable<ResponseBody> send_sms(@Body RequestBody req);

    /**
     * 首页游戏列表
     */
    @POST("/api/main/index")
    Observable<ResponseBody> getRecommendList();

    /**
     * banner轮播图
     */
    @POST("/api/images/get_tag_img")
    Observable<ResponseBody> get_banner_img(@Body RequestBody req);

    /**
     * 首页点击更多显示的游戏列表
     */
    @POST("/api/main/game_list")
    Observable<ResponseBody> getGameList(@Body RequestBody req);

    /**
     * 首页点击更多显示的游戏列表
     */
    @POST("/api/order/order_list")
    Observable<ResponseBody> getOrderList(@Body RequestBody req);

    /**
     * 我要买主页图
     */
    @POST("/api/images/my_want_buy_img")
    Observable<ResponseBody> getMy_want_buy_img();

    /**
     * 交易模式获取
     */
    @POST("/api/main/pattern")
    Observable<ResponseBody> getPattern();

    /**
     * 获取商品类型
     */
    @POST("/api/main/category")
    Observable<ResponseBody> getCategory();

    /**
     * 获取商品中的各项配置
     */
    @POST("/api/goods/goods_setting")
    Observable<ResponseBody> getGoodsSetting(@Body RequestBody req);

    /**
     * 获取列表下的商品信息
     */
    @POST("/api/main/game_goods_list")
    Observable<ResponseBody> getGameGoodsList(@Body RequestBody req);

    /**
     * 获取游戏设备运营商
     */
    @POST("/api/game/device_service")
    Observable<ResponseBody> getGameConfigurationList(@Body RequestBody req);

    /**
     * 获取商品列表信息
     */
    @POST("/api/main/goods_list")
    Observable<ResponseBody> getGoodsList(@Body RequestBody req);

    /**
     * 获取商品详情
     */
    @POST("/api/goods/goods_details")
    Observable<ResponseBody> getGoodsDetails(@Body RequestBody req);

    /**
     * 卖家发布商品保存
     */
    @POST("/api/goods/goods_save")
    Observable<ResponseBody> setGoodsSave(@Body RequestBody req);

    /**
     * 商品管理列表
     */
    @POST("/api/goods/goods_list")
    Observable<ResponseBody> getGoodsManagementList(@Body RequestBody req);

    /**
     * 订单详情
     */
    @POST("/api/order/order_details")
    Observable<ResponseBody> getOrderDetails(@Body RequestBody req);

    /**
     * 订单支付
     */
    @POST("/api/order/order_play")
    Observable<ResponseBody> getOrderPlay(@Body RequestBody req);

    /**
     * 修改订单状态和更改订单价格
     */
    @POST("/api/order/order_status_set")
    Observable<ResponseBody> setOrderStatus(@Body RequestBody req);

    /**
     * 创建群聊
     */
    @POST("/api/im_chat/create_chat_group")
    Observable<ResponseBody> create_chat_group(@Body RequestBody req);

    /**
     * 商品下架或更改价格
     */
    @POST("/api/goods/goods_update")
    Observable<ResponseBody> goods_update(@Body RequestBody req);

    /**
     *  订单包赔详细信息
     */
    @POST("/api/order/order_reparation_info")
    Observable<ResponseBody> order_reparation_info(@Body RequestBody req);

    /**
     *  提交账号信息查询
     */
    @POST("/api/game/get_game_account_conf")
    Observable<ResponseBody> get_game_account_conf(@Body RequestBody req);

    /**
     *  提交账号信息
     */
    @POST("/api/game/save_game_account_value")
    Observable<ResponseBody> save_game_account_value(@Body RequestBody req);

    /**
     *  编辑发布商品信息
     */
    @POST("/api/goods/goods_edit")
    Observable<ResponseBody> goods_edit(@Body RequestBody req);

    /**
     *  更新卖家发布商品
     */
    @POST("/api/goods/goods_details_update")
    Observable<ResponseBody> goods_details_update(@Body RequestBody req);

    /**
     *  版本更新获取
     */
    @POST("/api/get_version_manage")
    Observable<ResponseBody> get_version_manage(@Body RequestBody req);

    /**
     *  快速回收商品发布
     */
    @POST("/api/goods/goods_recycle")
    Observable<ResponseBody> goodsRecycle(@Body RequestBody req);

    /**
     *  会员提现
     */
    @POST("/api/user/user_withdrawal")
    Observable<ResponseBody> userWithdrawal(@Body RequestBody req);

    /**
     *  实名认证
     */
    @POST("/api/user/realname")
    Observable<ResponseBody> realname(@Body RequestBody req);

    /**
     *  包赔认证
     */
    @POST("/api/user/reparation_verify")
    Observable<ResponseBody> reparation_verify(@Body RequestBody req);

    /**
     *  会员三种认证信息
     */
    @POST("/api/user/auth_info")
    Observable<ResponseBody> auth_info();

    /**
     *  会员钱包余额
     */
    @POST("/api/user/balance")
    Observable<ResponseBody> getBalance();

    /**
     *  会员绑定支付宝实名的展示
     */
    @POST("/api/user/realname_list")
    Observable<ResponseBody> getRealname_list();

    /**
     *  会员流水列表
     */
    @POST("/api/user/transaction")
    Observable<ResponseBody> getTransaction(@Body RequestBody req);

    /**
     *  会员提现列表
     */
    @POST("/api/user/user_withdrawal_info")
    Observable<ResponseBody> getUser_withdrawal_info(@Body RequestBody req);

    /**
     *  用户账号密码昵称头像基础信息更新
     */
    @POST("/api/user/user_info_set")
    Observable<ResponseBody> set_user_info(@Body RequestBody req);

    /**
     *  买卖商品条数显示
     */
    @POST("/api/user/buy_sell_goods")
    Observable<ResponseBody> buy_sell_goods();

    /**
     *  会员买卖商品条数查看
     */
    @POST("/api/user/buy_sell_goods_set")
    Observable<ResponseBody> buy_sell_goods_set(@Body RequestBody req);

    /**
     *  会员商品收藏和取消收藏
     */
    @POST("/api/user/goods_concern")
    Observable<ResponseBody> goods_concern(@Body RequestBody req);

    /**
     *  会员收藏商品列表
     */
    @POST("/api/user/goods_concern_list")
    Observable<ResponseBody> goods_concern_list(@Body RequestBody req);

    /**
     *  会员人脸认证
     */
    @POST("/api/user/face")
    Observable<ResponseBody> userFace(@Body RequestBody req);

    /**
     *  获取游戏账号密码信息
     */
    @POST("/api/game/get_game_account_value")
    Observable<ResponseBody> getGameAccountValue(@Body RequestBody req);

    /**
     *  分配客服
     */
    @POST("/api/im_chat/assign_customer_service")
    Observable<ResponseBody> assignCustomerService(@Body RequestBody req);

    /**
     *  首页搜索项
     */
    @POST("/api/main/index_search")
    Observable<ResponseBody> index_search(@Body RequestBody req);

    /**
     *  海报主页图
     */
    @POST("/api/images/get_poster_img")
    Observable<ResponseBody> get_poster_img();

    /**
     *  是否有订单存在
     */
    @POST("/api/order/is_order_info")
    Observable<ResponseBody> is_order_info(@Body RequestBody req);

    /**
     *  虚拟订单详情展示
     */
    @POST("/api/order/virtual_save")
    Observable<ResponseBody> virtual_save(@Body RequestBody req);

    /**
     *  服务协议
     */
    @POST("/api/articles/get_agreement")
    Observable<ResponseBody> get_agreement(@Body RequestBody req);

    /**
     *  会员流水配置信息
     */
    @POST("/api/user/transaction_conf")
    Observable<ResponseBody> transaction_conf(@Body RequestBody req);

    /**
     *  获取身份证信息的ocr
     */
    @POST("/api/user/verify_info")
    Observable<ResponseBody> verify_info(@Body RequestBody req);

    /**
     *  账户注销
     */
    @POST("/api/user/user_disable")
    Observable<ResponseBody> user_disable();

    /**
     *  取消支付
     */
    @POST("/api/order/goods_stock_update")
    Observable<ResponseBody> goods_stock_update(@Body RequestBody req);

    /**
     *  售后列表
     */
    @POST("/api/order/post_sale_list")
    Observable<ResponseBody> getPost_sale_list(@Body RequestBody req);

    /**
     *  申请售后
     */
    @POST("/api/order/add_order_for_post_sale")
    Observable<ResponseBody> add_order_for_post_sale(@Body RequestBody req);

    /**
     *  取消申请
     */
    @POST("/api/order/cancel_order_for_post_sale")
    Observable<ResponseBody> cancel_order_for_post_sale(@Body RequestBody req);

    /**
     *  售后详情
     */
    @POST("/api/order/post_sale_info")
    Observable<ResponseBody> post_sale_info(@Body RequestBody req);

    /**
     *  文章列表
     */
    @GET("/api/articles/index")
    Observable<ResponseBody> getArticlesIndex(@Query("page") String page, @Query("category_id") String category_id, @Query("limit") String limit);

    /**
     *  文章详情
     */
    @POST("/api/articles/details")
    Observable<ResponseBody> getArticlesDetails(@Body RequestBody req);

    /**
     *  帮助中心分类
     */
    @POST("/api/articles_category/index")
    Observable<ResponseBody> getArticlesCategory();

    /**
     *  活动列表
     */
    @POST("/api/activity/list")
    Observable<ResponseBody> getActivityList(@Body RequestBody req);

    /**
     *  用户优惠券列表
     */
    @POST("/api/coupon/list")
    Observable<ResponseBody> getCouponList(@Body RequestBody req);

    /**
     *  订单获取可用优惠券列表
     */
    @POST("/api/coupon/order_available_list")
    Observable<ResponseBody> getOrderAvailableList();

    /**
     *  下单锁单
     */
    @POST("/api/order/place_order")
    Observable<ResponseBody> place_order(@Body RequestBody req);

    /**
     *  获取图片详情
     */
    @POST("/api/images/get_tag_info")
    Observable<ResponseBody> get_tag_info(@Body RequestBody req);

    /**
     *  获取筛选条件列表
     */
    @POST("/api/main/get_filter_categories")
    Observable<ResponseBody> get_filter_categories(@Body RequestBody req);
}
