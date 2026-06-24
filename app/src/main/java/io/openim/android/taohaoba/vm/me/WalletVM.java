package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.BalanceBean;
import io.openim.android.taohaoba.bean.GameAccountValueBean;
import io.openim.android.taohaoba.bean.GoodsConcernListBean;
import io.openim.android.taohaoba.bean.RealnameListBean;
import io.openim.android.taohaoba.bean.SendSmsBean;
import io.openim.android.taohaoba.bean.TransactionBean;
import io.openim.android.taohaoba.bean.TransactionConfBean;
import io.openim.android.taohaoba.bean.UserWithdrawalInfoBean;
import io.openim.android.taohaoba.bean.VersionManageBean;
import io.openim.android.taohaoba.bean.buySellGoodsBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.utils.RSA;

public class WalletVM extends BaseViewModel<WalletVM.ViewAction> {

    /**
     * 会员钱包余额
     */
    private MutableLiveData<BalanceBean> balanceBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BalanceBean> getBalanceBeanMutableLiveData() {
        return balanceBeanMutableLiveData;
    }

    public void getBalance() {

        N.APIThb(OpenIMService.class)
                .getBalance()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BalanceBean.class))
                .subscribe(new NetObserverThb<BalanceBean>(getContext()) {
                    @Override
                    public void onSuccess(BalanceBean it) {
                        balanceBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 会员绑定支付宝实名的展示
     */
    private MutableLiveData<RealnameListBean> realnameListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<RealnameListBean> getRealnameListBeanMutableLiveData() {
        return realnameListBeanMutableLiveData;
    }

    public void getRealname_list() {

        N.APIThb(OpenIMService.class)
                .getRealname_list()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(RealnameListBean.class))
                .subscribe(new NetObserverThb<RealnameListBean>(getContext()) {
                    @Override
                    public void onSuccess(RealnameListBean it) {
                        realnameListBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 会员流水列表
     */
    private MutableLiveData<TransactionBean> transactionBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<TransactionBean> getTransactionBeanMutableLiveData() {
        return transactionBeanMutableLiveData;
    }

    /**
     * @param page
     * @param type //0:全部 1：交易出账  2：交易进账 3：余额提现 4：充值  5：退款  6：违约金  7：包赔支出  8：包赔赔款 9：其他
     */
    public void getTransaction(int page, int type) {

        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("type", type);

        N.APIThb(OpenIMService.class)
                .getTransaction(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(TransactionBean.class))
                .subscribe(new NetObserverThb<TransactionBean>(getContext()) {
                    @Override
                    public void onSuccess(TransactionBean it) {
                        transactionBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }



    /**
     * 会员提现列表
     */
    private MutableLiveData<UserWithdrawalInfoBean> userWithdrawalInfoBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<UserWithdrawalInfoBean> getUserWithdrawalInfoBeanMutableLiveData() {
        return userWithdrawalInfoBeanMutableLiveData;
    }

    public void getUser_withdrawal_info(int page) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);

        N.APIThb(OpenIMService.class)
                .getUser_withdrawal_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(UserWithdrawalInfoBean.class))
                .subscribe(new NetObserverThb<UserWithdrawalInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(UserWithdrawalInfoBean it) {
                        userWithdrawalInfoBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 用户账号密码昵称头像基础信息更新
     */
    private MutableLiveData<Object> setUserInfoBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getSetUserInfoBeanMutableLiveData() {
        return setUserInfoBeanMutableLiveData;
    }

    /**
     * @param type 1：头像 2：昵称 3：手机号码  4：登录密码  5：qq  6:微信  7：支付宝  8：解绑
     */
    public void set_user_info(int type, String avatar, String nickname, String password, String confirm_password, String username, String confirm_username, String qq, String wei_chat, String realname, String alipay, String code) {
        Parameter parameter = new Parameter();
        parameter.add("type", type);
        parameter.add("avatar", avatar);
        parameter.add("nickname", nickname);
        parameter.add("password", password);
        parameter.add("confirm_password", confirm_password);
        parameter.add("username", username);
        parameter.add("confirm_username", confirm_username);
        parameter.add("qq", qq);
        parameter.add("wei_chat", wei_chat);
        parameter.add("realname", realname);
        parameter.add("alipay", alipay);
        parameter.add("code", code);
        parameter.add("platform", 2);

        N.APIThb(OpenIMService.class)
                .set_user_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        setUserInfoBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 发送验证码
     */
    private MutableLiveData<SendSmsBean> sendSmsLiveData = new MutableLiveData<>();

    public MutableLiveData<SendSmsBean> getSendSmsLiveData() {
        return sendSmsLiveData;
    }
    public void send_sms(String mobile) {
        Parameter parameter = new Parameter();
        parameter.add("mobile", mobile);

        N.APIThb(OpenIMService.class)
                .send_sms(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(SendSmsBean.class))
                .subscribe(new NetObserverThb<SendSmsBean>(getContext()) {
                    @Override
                    public void onSuccess(SendSmsBean it) {
                        sendSmsLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 买卖商品条数显示
     */
    private MutableLiveData<buySellGoodsBean> buySellGoodsBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<buySellGoodsBean> getBuySellGoodsBeanMutableLiveData() {
        return buySellGoodsBeanMutableLiveData;
    }
    public void buy_sell_goods() {

        N.APIThb(OpenIMService.class)
                .buy_sell_goods()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(buySellGoodsBean.class))
                .subscribe(new NetObserverThb<buySellGoodsBean>(getContext()) {
                    @Override
                    public void onSuccess(buySellGoodsBean it) {
                        buySellGoodsBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 会员收藏商品列表
     */
    private MutableLiveData<GoodsConcernListBean> goodsConcernListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsConcernListBean> getGoodsConcernListBeanMutableLiveData() {
        return goodsConcernListBeanMutableLiveData;
    }
    public void goods_concern_list(int page) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);

        N.APIThb(OpenIMService.class)
                .goods_concern_list(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsConcernListBean.class))
                .subscribe(new NetObserverThb<GoodsConcernListBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsConcernListBean it) {
                        goodsConcernListBeanMutableLiveData.postValue(it);
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
     * 提现
     */
    private MutableLiveData<Object> userWithdrawalMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getUserWithdrawalMutableLiveData() {
        return userWithdrawalMutableLiveData;
    }

    public void userWithdrawal(Double amount) {
        Parameter parameter = new Parameter();
        parameter.add("amount", amount);

        N.APIThb(OpenIMService.class)
                .userWithdrawal(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        userWithdrawalMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }



    /**
     * 会员流水配置信息
     */
    private MutableLiveData<List<TransactionConfBean>> transactionConfBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<TransactionConfBean>> getTransactionConfBeanMutableLiveData() {
        return transactionConfBeanMutableLiveData;
    }

    public void transaction_conf() {
        Parameter parameter = new Parameter();

        N.APIThb(OpenIMService.class)
                .transaction_conf(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.listTurnThb(TransactionConfBean.class))
                .subscribe(new NetObserverThb<List<TransactionConfBean>>(getContext()) {
                    @Override
                    public void onSuccess(List<TransactionConfBean> it) {
                        transactionConfBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 分配客服
     */
    private MutableLiveData<String> assignCustomerServiceMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getAssignCustomerServiceMutableLiveData() {
        return assignCustomerServiceMutableLiveData;
    }

    public void assignCustomerService() {
        Parameter parameter = new Parameter();
        parameter.add("cs_group_id", 2);
        N.APIThb(OpenIMService.class)
                .assignCustomerService(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(String.class))
                .subscribe(new NetObserverThb<String>(getContext()) {
                    @Override
                    public void onSuccess(String it) {
                        assignCustomerServiceMutableLiveData.postValue(it);
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
     * 版本更新获取
     */
    private MutableLiveData<VersionManageBean> versionManageBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<VersionManageBean> getVersionManageBeanMutableLiveData() {
        return versionManageBeanMutableLiveData;
    }

    /**
     * @param channel_type 渠道类型
     */
    public void get_version_manage(String channel_type) {
        Parameter parameter = new Parameter();
        parameter.add("device_type", "Android");
        parameter.add("channel_type", channel_type);

        N.APIThb(OpenIMService.class)
                .get_version_manage(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(VersionManageBean.class))
                .subscribe(new NetObserverThb<VersionManageBean>(getContext()) {
                    @Override
                    public void onSuccess(VersionManageBean it) {
                        versionManageBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    /**
     * 账户注销
     */
    private MutableLiveData<Object> userDisableMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getUserDisableMutableLiveData() {
        return userDisableMutableLiveData;
    }

    public void user_disable() {
        N.APIThb(OpenIMService.class)
                .user_disable()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        userDisableMutableLiveData.postValue(it);
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
