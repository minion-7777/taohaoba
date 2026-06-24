package io.openim.android.taohaoba.vm.home;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.AuthInfoBean;
import io.openim.android.taohaoba.bean.CreateChatGroupBean;
import io.openim.android.taohaoba.bean.GameConfigurationListBean;
import io.openim.android.taohaoba.bean.GoodsSaveBean;
import io.openim.android.taohaoba.bean.GoodsSettingBean;
import io.openim.android.taohaoba.bean.ValueDTOBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.utils.RSA;

public class GoodsSettingVM extends BaseViewModel<GoodsSettingVM.ViewAction>{

    private static final String TAG = "GoodsSettingVM";
    /**
     * 获取商品中的各项设置
     */
    private MutableLiveData<GoodsSettingBean> goodsSettingBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsSettingBean> getGoodsSettingBeanMutableLiveData() {
        return goodsSettingBeanMutableLiveData;
    }

    /**
     * 创建群聊
     */
    private MutableLiveData<CreateChatGroupBean> liveDataCreateChatGroupBean = new MutableLiveData<>();

    public MutableLiveData<CreateChatGroupBean> getLiveDataCreateChatGroupBean() {
        return liveDataCreateChatGroupBean;
    }

    /**
     *
     * @param category_id 商品类型id 1:卖号，2：游戏币 3：装备
     * @param game_id 游戏id
     * @param pattern_id 交易模式id
     */
    public void getGoodsSetting(int category_id, int game_id, int pattern_id) {
        Parameter parameter = new Parameter();
        parameter.add("category_id", category_id);
        parameter.add("game_id", game_id);
        parameter.add("pattern_id", pattern_id);

        N.APIThb(OpenIMService.class)
                .getGoodsSetting(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsSettingBean.class))
                .subscribe(new NetObserverThb<GoodsSettingBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsSettingBean it) {
                        goodsSettingBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 获取游戏设备运营商
     */
    private MutableLiveData<GameConfigurationListBean> gameConfigurationListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GameConfigurationListBean> getGameConfigurationListBeanMutableLiveData() {
        return gameConfigurationListBeanMutableLiveData;
    }

    public void getGameConfigurationList(int game_id) {
        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);

        N.APIThb(OpenIMService.class)
                .getGameConfigurationList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GameConfigurationListBean.class))
                .subscribe(new NetObserverThb<GameConfigurationListBean>(getContext()) {
                    @Override
                    public void onSuccess(GameConfigurationListBean it) {
                        gameConfigurationListBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 卖家发布商品保存
     */
    private MutableLiveData<GoodsSaveBean> goodsSaveBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsSaveBean> getGoodsSaveBeanMutableLiveData() {
        return goodsSaveBeanMutableLiveData;
    }

    /**
     * @param game_id    游戏id
     * @param pattern_id    模式id
     * @param goods_id  商品id
     * @param device_id    设备Id   如果是端游传0
     * @param operator_id    运营商id   如果是端游传0
     * @param account    游戏账号
     * @param title 游戏标题
     * @param image 游戏图片
     * @param game_service_id   端游服务器id  如果是手游的时候此处为空
     * @param is_inspect    是否审核 1:审核 0:不审核   (如果运维选择了审核，在用户商家商品的时候显示审核或不审核，以下需要选择的单选项都是类似的意思)
     * @param is_indulge    防沉迷 1:启用 0：禁用（备注信息同上）
     * @param is_authentication 是否二次认证需要填写 1：是  0：否(备注信息同上)
     * @param is_account_source 账号来源  1: 自己注册 2：本平台购买 3：其他平台购买   （0:是运维人员设置的  1，2，3是用户上架商品的时候选择）
     * @param price 售卖价格
     * @param connect   联系方式
     * @param text  商品描述信息
     * @param label 商品标签
     * @param sparkle   商品亮点
     * @param content 集合
     *              "key":"游戏段位",     //字段验证必须是字符串
     *             "key_sort":13,    //字段名称排序   字段验证必须是数字
     *             "value":"黄金1",  //字段验证如果存在必须有中文逗号
     *             "is_required":1,  //是否必填 1: 是 2：否   数字
     *             "is_sort":1,    //是否排序   数字
     *             "is_show":1,    //是否显示   数字
     *             "type":3,       //类型 1:文本，2: 单选 3：多选   数字
     *             "sort_type":1  //字段值排序类型 1：字母 2：数字   数字
     */
    public void setGoodsSave(
            int category_id,
                             int game_id,
                             int pattern_id,
                             int goods_id,
                             int device_id,
                             int operator_id,
                             String account,
                             String title,
                             String image,
                             String game_service_id,
                             int is_inspect,
                             int is_indulge,
                             int is_authentication,
                             int is_account_source,
                             double price,
                             String connect,
                             String text,
                             String label,
                             String sparkle,
                             List<ValueDTOBean> content) {
        Parameter parameter = new Parameter();
        parameter.add("category_id", category_id);
        parameter.add("game_id", game_id);
        parameter.add("pattern_id", pattern_id);
        parameter.add("goods_id", goods_id);
        parameter.add("device_id", device_id);
        parameter.add("operator_id", operator_id);
        parameter.add("account", account);
        parameter.add("title", title);
        parameter.add("image", image);
        parameter.add("game_service_id", game_service_id);
        parameter.add("is_inspect", is_inspect);
        parameter.add("is_indulge", is_indulge);
        parameter.add("is_authentication", is_authentication);
        parameter.add("is_account_source", is_account_source);
        parameter.add("price", price);
        parameter.add("connect", connect);
        parameter.add("text", text);
        parameter.add("label", label);
        parameter.add("sparkle", sparkle);
        parameter.add("content", content);

        N.APIThb(OpenIMService.class)
                .setGoodsSave(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsSaveBean.class))
                .subscribe(new NetObserverThb<GoodsSaveBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsSaveBean it) {
                        goodsSaveBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 编辑发布商品信息
     */
    private MutableLiveData<GoodsSettingBean> goodsEditBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsSettingBean> getGoodsEditBeanMutableLiveData() {
        return goodsEditBeanMutableLiveData;
    }

    public void goods_edit(int goods_id) {
        Parameter parameter = new Parameter();
        parameter.add("goods_id", goods_id);

        N.APIThb(OpenIMService.class)
                .goods_edit(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsSettingBean.class))
                .subscribe(new NetObserverThb<GoodsSettingBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsSettingBean it) {
                        goodsEditBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 更新卖家发布商品
     */
    private MutableLiveData<Object> goodsDetailsUpdateBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getGoodsDetailsUpdateBeanMutableLiveData() {
        return goodsDetailsUpdateBeanMutableLiveData;
    }

    /**
     * @param id    id
     * @param game_id    游戏id
     * @param pattern_id    模式id
     * @param device_id    设备Id   如果是端游传0
     * @param operator_id    运营商id   如果是端游传0
     * @param account    游戏账号
     * @param title 游戏标题
     * @param image 游戏图片
     * @param game_service_id   端游服务器id  如果是手游的时候此处为空
     * @param is_inspect    是否审核 1:审核 0:不审核   (如果运维选择了审核，在用户商家商品的时候显示审核或不审核，以下需要选择的单选项都是类似的意思)
     * @param is_indulge    防沉迷 1:启用 0：禁用（备注信息同上）
     * @param is_authentication 是否二次认证需要填写 1：是  0：否(备注信息同上)
     * @param is_account_source 账号来源  1: 自己注册 2：本平台购买 3：其他平台购买   （0:是运维人员设置的  1，2，3是用户上架商品的时候选择）
     * @param price 售卖价格
     * @param connect   联系方式
     * @param text  商品描述信息
     * @param label 商品标签
     * @param sparkle   商品亮点
     * @param content 集合
     *              "key":"游戏段位",     //字段验证必须是字符串
     *             "key_sort":13,    //字段名称排序   字段验证必须是数字
     *             "value":"黄金1",  //字段验证如果存在必须有中文逗号
     *             "is_required":1,  //是否必填 1: 是 2：否   数字
     *             "is_sort":1,    //是否排序   数字
     *             "is_show":1,    //是否显示   数字
     *             "type":3,       //类型 1:文本，2: 单选 3：多选   数字
     *             "sort_type":1  //字段值排序类型 1：字母 2：数字   数字
     */
    public void goods_details_update(
            int category_id,
                             int id,
                             int game_id,
                             int pattern_id,
                             int device_id,
                             int operator_id,
                             String account,
                             String title,
                             String image,
                             String game_service_id,
                             int is_inspect,
                             int is_indulge,
                             int is_authentication,
                             int is_account_source,
                             double price,
                             String connect,
                             String text,
                             String label,
                             String sparkle,
                             List<ValueDTOBean> content) {
        Parameter parameter = new Parameter();
        parameter.add("category_id", category_id);
        parameter.add("id", id);
        parameter.add("game_id", game_id);
        parameter.add("pattern_id", pattern_id);
//        parameter.add("goods_id", goods_id);
        parameter.add("device_id", device_id);
        parameter.add("operator_id", operator_id);
        parameter.add("account", account);
        parameter.add("title", title);
        parameter.add("image", image);
        parameter.add("game_service_id", game_service_id);
        parameter.add("is_inspect", is_inspect);
        parameter.add("is_indulge", is_indulge);
        parameter.add("is_authentication", is_authentication);
        parameter.add("is_account_source", is_account_source);
        parameter.add("price", price);
        parameter.add("connect", connect);
        parameter.add("text", text);
        parameter.add("label", label);
        parameter.add("sparkle", sparkle);
        parameter.add("content", content);

        N.APIThb(OpenIMService.class)
                .goods_details_update(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        goodsDetailsUpdateBeanMutableLiveData.postValue(it);
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

    public void createChatGroup(int cs_group_id, int game_id, int merchant_user_id, int good_id, int status, int type) {
        Parameter parameter = new Parameter();
        parameter.add("cs_group_id", cs_group_id);
        parameter.add("game_id", game_id);
        parameter.add("merchant_user_id", merchant_user_id);
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
                        liveDataCreateChatGroupBean.postValue(it);
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

}
