package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.AfterSalesApplicationBean;
import io.openim.android.taohaoba.bean.AfterSalesDetailsBean;
import io.openim.android.taohaoba.bean.AfterSalesListBean;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class AfterSalesVM extends BaseViewModel<AfterSalesVM.ViewAction> {

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
     * 售后列表
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<AfterSalesListBean>> afterSalesListBeanMutableLiveData = new HashMap<>();

    public MutableLiveData<AfterSalesListBean> getAfterSalesListBeanMutableLiveData(Integer fragmentTag) {
        if (!afterSalesListBeanMutableLiveData.containsKey(fragmentTag)) {
            afterSalesListBeanMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return afterSalesListBeanMutableLiveData.get(fragmentTag);
    }

    /**
     *
     * @param fragmentTag
     * @param page
     * @param status 售后状态 0全部 1待处理 2受理中 3已完成 4已关闭
     */
    public void getAfterSalesList(Integer fragmentTag, int page, int status) {

        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("limit", 10);
        parameter.add("status", status);

        N.APIThb(OpenIMService.class)
                .getPost_sale_list(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(AfterSalesListBean.class))
                .subscribe(new NetObserverThb<AfterSalesListBean>(getContext()) {
                    @Override
                    public void onSuccess(AfterSalesListBean it) {
                        afterSalesListBeanMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 取消申请
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<Object>> cancelOrderForPostSaleMutableLiveData = new HashMap<>();

    public MutableLiveData<Object> getCancelOrderForPostSaleMutableLiveData(Integer fragmentTag) {
        if (!cancelOrderForPostSaleMutableLiveData.containsKey(fragmentTag)) {
            cancelOrderForPostSaleMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return cancelOrderForPostSaleMutableLiveData.get(fragmentTag);
    }

    public void cancel_order_for_post_sale(Integer fragmentTag, int post_sale_id) {

        Parameter parameter = new Parameter();
        parameter.add("post_sale_id", post_sale_id);

        N.APIThb(OpenIMService.class)
                .cancel_order_for_post_sale(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        cancelOrderForPostSaleMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 售后申请
     */
    private MutableLiveData<AfterSalesApplicationBean> afterSalesApplicationBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<AfterSalesApplicationBean> getAfterSalesApplicationBeanMutableLiveData() {
        return afterSalesApplicationBeanMutableLiveData;
    }

    /**
     * @param order_id  订单id
     * @param reason    售后原因 1实名问题 2账号被找回 3换绑问题 4账号封禁冻结 5其他
     * @param phone     联系电话
     * @param wx_code   微信号
     * @param notes     申请说明
     * @param imgs      凭证图片地址 多个地址用英文逗号隔开
     */
    public void add_order_for_post_sale(int order_id, int reason, String phone, String wx_code, String notes, String imgs) {
        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);
        parameter.add("reason", reason);
        parameter.add("phone", phone);
        parameter.add("wx_code", wx_code);
        parameter.add("notes", notes);
        parameter.add("imgs", imgs);

        N.APIThb(OpenIMService.class)
                .add_order_for_post_sale(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(AfterSalesApplicationBean.class))
                .subscribe(new NetObserverThb<AfterSalesApplicationBean>(getContext()) {
                    @Override
                    public void onSuccess(AfterSalesApplicationBean it) {
                        afterSalesApplicationBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 售后详情
     */
    private MutableLiveData<AfterSalesDetailsBean> afterSalesDetailsBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<AfterSalesDetailsBean> getAfterSalesDetailsBeanMutableLiveData() {
        return afterSalesDetailsBeanMutableLiveData;
    }

    public void post_sale_info(int post_sale_id) {
        Parameter parameter = new Parameter();
        parameter.add("post_sale_id", post_sale_id);

        N.APIThb(OpenIMService.class)
                .post_sale_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(AfterSalesDetailsBean.class))
                .subscribe(new NetObserverThb<AfterSalesDetailsBean>(getContext()) {
                    @Override
                    public void onSuccess(AfterSalesDetailsBean it) {
                        afterSalesDetailsBeanMutableLiveData.postValue(it);
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
