package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.CreateChatGroupBean;
import io.openim.android.taohaoba.bean.GameAccountValueBean;
import io.openim.android.taohaoba.bean.OrderDetailsBean;
import io.openim.android.taohaoba.bean.PlaceOrderBean;
import io.openim.android.taohaoba.bean.SubmintAccountInformationBean;
import io.openim.android.taohaoba.bean.ViewVouchersBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class OrderDetailsVM extends BaseViewModel<OrderDetailsVM.ViewAction> {

    /**
     * 订单详情
     */
    private MutableLiveData<OrderDetailsBean> orderDetailsBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<OrderDetailsBean> getOrderDetailsBeanMutableLiveData() {
        return orderDetailsBeanMutableLiveData;
    }

    public void getOrderDetails(int order_id) {
        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);

        N.APIThb(OpenIMService.class)
                .getOrderDetails(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(OrderDetailsBean.class))
                .subscribe(new NetObserverThb<OrderDetailsBean>(getContext()) {
                    @Override
                    public void onSuccess(OrderDetailsBean it) {
                        orderDetailsBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 商品下架或更改价格
     */
    private MutableLiveData<Object> orderStatusSetMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getOrderStatusSetMutableLiveData() {
        return orderStatusSetMutableLiveData;
    }

    /**
     * @param order_id      订单id
     * @param type          操纵类型 1:确认验号 2：确认收货 3：取消订单 4：修改价格
     * @param amount        修改的价格
     * @param refund_content 取消原因
     */
    public void setOrderStatus(int order_id, int type, Double amount, String refund_content) {

        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);
        parameter.add("type", type);
        parameter.add("amount", amount);
        parameter.add("refund_content", refund_content);
        parameter.add("platform", 2);

        N.APIThb(OpenIMService.class)
                .setOrderStatus(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        orderStatusSetMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 订单包赔详细信息
     */
    private MutableLiveData<ViewVouchersBean> viewVouchersBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ViewVouchersBean> getViewVouchersBeanMutableLiveData() {
        return viewVouchersBeanMutableLiveData;
    }

    public void order_reparation_info(int order_id) {

        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);

        N.APIThb(OpenIMService.class)
                .order_reparation_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(ViewVouchersBean.class))
                .subscribe(new NetObserverThb<ViewVouchersBean>(getContext()) {
                    @Override
                    public void onSuccess(ViewVouchersBean it) {
                        viewVouchersBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 提交账号信息查询
     */
    private MutableLiveData<List<SubmintAccountInformationBean>> submintAccountInformationBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<SubmintAccountInformationBean>> getSubmintAccountInformationBeanMutableLiveData() {
        return submintAccountInformationBeanMutableLiveData;
    }

    public void get_game_account_conf(int game_id) {

        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);

        N.APIThb(OpenIMService.class)
                .get_game_account_conf(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.listTurnThb(SubmintAccountInformationBean.class))
                .subscribe(new NetObserverThb<List<SubmintAccountInformationBean>>(getContext()) {
                    @Override
                    public void onSuccess(List<SubmintAccountInformationBean> it) {
                        submintAccountInformationBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 订单支付
     */
    private MutableLiveData<String> orderPayMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getOrderPayMutableLiveData() {
        return orderPayMutableLiveData;
    }

    public void getOrderPlay(int order_id, String buy_user_contact, int reparation_num, int order_type, Integer is_reparation, Double payment_price, int is_combined_payment) {

        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);
        parameter.add("order_id", order_id);
        parameter.add("buy_user_contact", buy_user_contact);
        parameter.add("reparation_num", reparation_num);
        parameter.add("order_type", order_type);
        parameter.add("platform", 2);
        parameter.add("is_reparation", is_reparation);//是否购买包赔 0否 1是（必填）
        parameter.add("payment_price", payment_price);//支付价格
        parameter.add("is_combined_payment", is_combined_payment);//是否组合支付 0否 1是

        N.APIThb(OpenIMService.class)
                .getOrderPlay(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(String.class))
                .subscribe(new NetObserverThb<String>(getContext()) {
                    @Override
                    public void onSuccess(String it) {
                        orderPayMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 提交账号信息
     */
    private MutableLiveData<Object> saveGameMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getSaveGameMutableLiveData() {
        return saveGameMutableLiveData;
    }

    /**
     * @param game_id                   游戏id
     * @param goods_id                  商品id
     * @param order_id                  订单id
     * @param is_account_source         账号来源  1: 自己注册 2：本平台购买 3：其他平台购买   （0:是运维人员设置的  1，2，3是用户上架商品的时候选择）必选
     * @param authentication_image      二次实名图片url,
     * @param account_source_image      账号来源图片' 为3：其他平台购买时 必选
     * @param game_account_value        配置项
     */
    public void save_game_account_value(int game_id, int goods_id, int order_id, int is_account_source, String authentication_image,
                                        String account_source_image, List<GameAccountValueBean> game_account_value,String send_im_id,String im_group) {

        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);
        parameter.add("goods_id", goods_id);
        parameter.add("order_id", order_id);
        parameter.add("is_account_source", is_account_source);
        parameter.add("authentication_image", authentication_image);
        parameter.add("account_source_image", account_source_image);
        parameter.add("game_account_value", game_account_value);
        parameter.add("send_im_id", send_im_id);
        parameter.add("im_group_id", im_group);
        parameter.add("platform", 2);

        N.APIThb(OpenIMService.class)
                .save_game_account_value(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        saveGameMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 虚拟订单详情展示
     */
    private MutableLiveData<OrderDetailsBean> virtualSaveBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<OrderDetailsBean> getVirtualSaveBeanMutableLiveData() {
        return virtualSaveBeanMutableLiveData;
    }

    public void virtual_save(long goods_id) {

        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);

        N.APIThb(OpenIMService.class)
                .virtual_save(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(OrderDetailsBean.class))
                .subscribe(new NetObserverThb<OrderDetailsBean>(getContext()) {
                    @Override
                    public void onSuccess(OrderDetailsBean it) {
                        virtualSaveBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 创建订单和群聊
     */
    private MutableLiveData<CreateChatGroupBean> createChatGroupMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CreateChatGroupBean> getCreateChatGroupMutableLiveData() {
        return createChatGroupMutableLiveData;
    }

    /**
     *
     * @param cs_group_id 1交易客服分组,2为咨询客服分组
     * @param good_id 商品ID
     * @param status 为正常
     * @param type 1:建立群聊同时开单 2:否
     */
    public void create_chat_group(int cs_group_id, int good_id, int status, int type) {
        Parameter parameter = new Parameter();
        parameter.add("cs_group_id", cs_group_id);
        parameter.add("good_id", good_id);
        parameter.add("status", status);
        parameter.add("type", type);

        N.APIThb(OpenIMService.class)
                .create_chat_group(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(CreateChatGroupBean.class))
                .subscribe(new NetObserverThb<CreateChatGroupBean>(getContext()) {
                    @Override
                    public void onSuccess(CreateChatGroupBean it) {
                        createChatGroupMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 取消支付
     */
    private MutableLiveData<Object> cancellationOfOrderMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getCancellationOfOrderMutableLiveData() {
        return cancellationOfOrderMutableLiveData;
    }

    public void goods_stock_update(int orderId) {
        Parameter parameter = new Parameter();
        parameter.add("order_id", orderId);

        N.APIThb(OpenIMService.class)
                .goods_stock_update(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        cancellationOfOrderMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 下单锁单
     */
    private MutableLiveData<PlaceOrderBean> placeOrderBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<PlaceOrderBean> getPlaceOrderBeanMutableLiveData() {
        return placeOrderBeanMutableLiveData;
    }

    public void place_order(int goods_id, String buy_user_contact, int is_reparation, int reparation_num, Integer user_coupon_id) {
        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);//商品ID
        parameter.add("buy_user_contact", buy_user_contact);//买家联系方式
        parameter.add("is_reparation", is_reparation);//是否包赔 0否 1是
        parameter.add("reparation_num", reparation_num);//包赔倍数
        parameter.add("user_coupon_id", user_coupon_id);//用户优惠券ID(可选)
        parameter.add("platform", 2);

        N.APIThb(OpenIMService.class)
                .place_order(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(PlaceOrderBean.class))
                .subscribe(new NetObserverThb<PlaceOrderBean>(getContext()) {
                    @Override
                    public void onSuccess(PlaceOrderBean it) {
                        placeOrderBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    public interface ViewAction extends io.openim.android.ouicore.base.IView {
        void err(String msg);
    }
}
