package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.GameAccountValueBean;
import io.openim.android.taohaoba.bean.SubmintAccountInformationBean;
import io.openim.android.taohaoba.bean.ViewVouchersBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class InputInformationVM extends BaseViewModel<InputInformationVM.ViewAction> {

    /**
     * 订单详情
     */
    private MutableLiveData<Object> mutableLiveDataGoodsRecycle = new MutableLiveData<>();

    public MutableLiveData<Object> getMutableLiveDataGoodsRecycle() {
        return mutableLiveDataGoodsRecycle;
    }

    public void goodsRecycle(int game_id,int pattern_id,String account,Float amount,int seller_id,int buyer_id,String imGroupId,String imGroupOwnerUserID) {
        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);
        parameter.add("pattern_id", pattern_id);
        parameter.add("account", account);
        parameter.add("amount", amount);
        parameter.add("seller_id", seller_id);
        parameter.add("buyer_id", buyer_id);
        parameter.add("send_im_id", imGroupOwnerUserID);
        parameter.add("im_group_id", imGroupId);
        parameter.add("platform", 2);

        N.APIThb(OpenIMService.class)
                .goodsRecycle(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        mutableLiveDataGoodsRecycle.postValue(it);
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

    public void getOrderPlay(int order_id, String buy_user_contact, int reparation_num, int order_type) {

        Parameter parameter = new Parameter();
        parameter.add("order_id", order_id);
        parameter.add("buy_user_contact", buy_user_contact);
        parameter.add("reparation_num", reparation_num);
        parameter.add("order_type", order_type);

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
    public void save_game_account_value(int game_id, int goods_id, int order_id, int is_account_source, String authentication_image, String account_source_image, List<GameAccountValueBean> game_account_value) {

        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);
        parameter.add("goods_id", goods_id);
        parameter.add("order_id", order_id);
        parameter.add("is_account_source", is_account_source);
        parameter.add("authentication_image", authentication_image);
        parameter.add("account_source_image", account_source_image);
        parameter.add("game_account_value", game_account_value);

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


    public interface ViewAction extends io.openim.android.ouicore.base.IView {
        void err(String msg);
    }
}
