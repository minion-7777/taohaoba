package io.openim.android.taohaoba.vm.game;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.AuthInfoBean;
import io.openim.android.taohaoba.bean.CreateChatGroupBean;
import io.openim.android.taohaoba.bean.GoodsDetailsBean;
import io.openim.android.taohaoba.bean.GoodsSaveBean;
import io.openim.android.taohaoba.bean.IsOrderInfoBean;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.utils.RSA;

public class ProductDetailsVM extends BaseViewModel<ProductDetailsVM.ViewAction> {


    /**
     * 获取商品详情
     */
    private MutableLiveData<GoodsDetailsBean> goodsDetailsBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsDetailsBean> getGoodsDetailsBeanMutableLiveData() {
        return goodsDetailsBeanMutableLiveData;
    }

    public void getGoodsDetails(int goods_id) {
        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);

        N.APIThb(OpenIMService.class)
                .getGoodsDetails(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsDetailsBean.class))
                .subscribe(new NetObserverThb<GoodsDetailsBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsDetailsBean it) {
                        goodsDetailsBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 创建群聊
     */
    private MutableLiveData<CreateChatGroupBean> createChatGroupMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CreateChatGroupBean> getCreateChatGroupMutableLiveData() {
        return createChatGroupMutableLiveData;
    }

    /**
     * getOneConversation
     */
//    private MutableLiveData<ConversationInfo> liveDataConversationInfo = new MutableLiveData<>();
//
//    public MutableLiveData<ConversationInfo> getLiveDataConversationInfo() {
//        return liveDataConversationInfo;
//    }

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
     * 商品下架或更改价格
     */
    private MutableLiveData<Object> goodsUpdateMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getGoodsUpdateMutableLiveData() {
        return goodsUpdateMutableLiveData;
    }

    /**
     * @param goods_id
     * @param type 1 ：商品下架 2：修改价格 3：删除商品 4：上架
     * @param price 商品售价
     */
    public void goods_update(int goods_id, int type, Double price) {
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
                        goodsUpdateMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 会员商品收藏和取消收藏
     */
    private MutableLiveData<Object> goodsConcernMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getGoodsConcernMutableLiveData() {
        return goodsConcernMutableLiveData;
    }

    /**
     * @param type  1：收藏商品  0:取消收藏商品
     * @param goods_id
     */
    public void goods_concern(int type, int goods_id) {
        Parameter parameter = new Parameter();
        parameter.add("type", type);
        parameter.add("goods_id", goods_id);

        N.APIThb(OpenIMService.class)
                .goods_concern(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        goodsConcernMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 会员三种认证信息
     */
    private MutableLiveData<AuthInfoBean> authInfoBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<AuthInfoBean> getAuthInfoBeanMutableLiveData() {
        return authInfoBeanMutableLiveData;
    }

    public void auth_info() {

        N.APIThb(OpenIMService.class)
                .auth_info()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(AuthInfoBean.class))
                .subscribe(new NetObserverThb<AuthInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(AuthInfoBean it) {
                        authInfoBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 实名认证
     */
    private MutableLiveData<Object> realnameBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getRealnameBeanMutableLiveData() {
        return realnameBeanMutableLiveData;
    }

    public void realname(String realname, String number) {

        Parameter parameter = new Parameter();
        parameter.add("realname", realname);
        parameter.add("number", RSA.encryptByPublicKey(getContext(), number));

        N.APIThb(OpenIMService.class)
                .realname(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        realnameBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 是否有订单存在
     */
    private MutableLiveData<IsOrderInfoBean> isOrderInfoBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<IsOrderInfoBean> getIsOrderInfoBeanMutableLiveData() {
        return isOrderInfoBeanMutableLiveData;
    }

    public void is_order_info(long goods_id) {

        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);

        N.APIThb(OpenIMService.class)
                .is_order_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(IsOrderInfoBean.class))
                .subscribe(new NetObserverThb<IsOrderInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(IsOrderInfoBean it) {
                        isOrderInfoBeanMutableLiveData.postValue(it);
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
