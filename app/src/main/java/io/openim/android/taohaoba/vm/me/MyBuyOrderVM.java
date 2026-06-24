package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.GoodsManagementListBean;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.bean.SendSmsBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class MyBuyOrderVM extends BaseViewModel<MyBuyOrderVM.ViewAction> {


    /**
     * 买家和卖家订单列表显示
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<OrderListBean>> orderListBeanMutableLiveData = new HashMap<>();

    public MutableLiveData<OrderListBean> getOrderListBeanMutableLiveData(Integer fragmentTag) {
        if (!orderListBeanMutableLiveData.containsKey(fragmentTag)) {
            orderListBeanMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return orderListBeanMutableLiveData.get(fragmentTag);
    }

    /**
     *
     * @param fragmentTag
     * @param page
     * @param order_status 0:全部  1：待付款  2：交易中  3：已完成  4：取消/退款
     * @param user_type 1：买家页面订单列表   2：卖家订单页面列表
     */
    public void getOrderList(Integer fragmentTag, int page, int order_status, int user_type) {

        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("order_status", order_status);
        parameter.add("user_type", user_type);

        N.APIThb(OpenIMService.class)
                .getOrderList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(OrderListBean.class))
                .subscribe(new NetObserverThb<OrderListBean>(getContext()) {
                    @Override
                    public void onSuccess(OrderListBean it) {
                        orderListBeanMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 商品管理列表
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<GoodsManagementListBean>> goodsManagementListBeanMutableLiveData = new HashMap<>();

    public MutableLiveData<GoodsManagementListBean> getGoodsManagementListBeanMutableLiveData(Integer fragmentTag) {
        if (!goodsManagementListBeanMutableLiveData.containsKey(fragmentTag)) {
            goodsManagementListBeanMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return goodsManagementListBeanMutableLiveData.get(fragmentTag);
    }

    /**
     *
     * @param fragmentTag
     * @param page
     * @param review_status 发布商品状态  0：审核中 1：已上架 2：已下架  3:审核失败
     */
    public void getGoodsManagementList(Integer fragmentTag, int page, int review_status) {

        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("review_status", review_status == -1 ? null : review_status);

        N.APIThb(OpenIMService.class)
                .getGoodsManagementList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsManagementListBean.class))
                .subscribe(new NetObserverThb<GoodsManagementListBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsManagementListBean it) {
                        goodsManagementListBeanMutableLiveData.get(fragmentTag).postValue(it);
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
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<Object>> goodsUpdateMutableLiveData = new HashMap<>();

    public MutableLiveData<Object> getGoodsUpdateMutableLiveData(Integer fragmentTag) {
        if (!goodsUpdateMutableLiveData.containsKey(fragmentTag)) {
            goodsUpdateMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return goodsUpdateMutableLiveData.get(fragmentTag);
    }

    /**
     * @param fragmentTag
     * @param goods_id
     * @param type 1 ：商品下架 2：修改价格 3：删除商品 4：上架
     * @param price 商品售价
     */
    public void goods_update(Integer fragmentTag, int goods_id, int type, Double price) {

        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);
        parameter.add("type", type);
        parameter.add("price", price);

        N.APIThb(OpenIMService.class)
                .goods_update(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        goodsUpdateMutableLiveData.get(fragmentTag).postValue(it);
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
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<Object>> orderStatusSetMutableLiveData = new HashMap<>();

    public MutableLiveData<Object> getOrderStatusSetMutableLiveData(Integer fragmentTag) {
        if (!orderStatusSetMutableLiveData.containsKey(fragmentTag)) {
            orderStatusSetMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return orderStatusSetMutableLiveData.get(fragmentTag);
    }

    /**
     * @param fragmentTag
     * @param order_id      订单id
     * @param type          操纵类型 1:确认验号 2：确认收货 3：取消订单 4：修改价格
     * @param amount        修改的价格
     * @param refund_content 取消一样
     */
    public void setOrderStatus(Integer fragmentTag, int order_id, int type, Double amount, String refund_content) {

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
                        orderStatusSetMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 会员买卖商品条数查看
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<Object>> buySellGoodsSetMutableLiveData = new HashMap<>();

    public MutableLiveData<Object> getBuySellGoodsSetMutableLiveData(Integer fragmentTag) {
        if (!buySellGoodsSetMutableLiveData.containsKey(fragmentTag)) {
            buySellGoodsSetMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return buySellGoodsSetMutableLiveData.get(fragmentTag);
    }

    /**
     * @param type      查看的类型  1 ：我买的  2：我卖的 3：我的商品
     * @param status    状态信息  1 ：待付款 2：交易中 3：已完成  4：已取消
     */
    public void buy_sell_goods_set(Integer fragmentTag, int type, int status) {

        Parameter parameter = new Parameter();
        parameter.add("type", type);
        parameter.add("status", status);

        N.APIThb(OpenIMService.class)
                .buy_sell_goods_set(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        buySellGoodsSetMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    public interface ViewAction extends IView {

        void err(String msg);

    }

}
