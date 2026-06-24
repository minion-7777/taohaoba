package io.openim.android.taohaoba.vm.home;

import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.BaseResponseBean;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.CategoryBean;
import io.openim.android.taohaoba.bean.GameGoodsListBean;
import io.openim.android.taohaoba.bean.OrderListBean;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.bean.RecommendListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class HomeVM extends BaseViewModel<HomeVM.ViewAction> {
    /**
     * 首页游戏列表
     */
    private MutableLiveData<RecommendListBean> recommendListLiveData = new MutableLiveData<>();

    public MutableLiveData<RecommendListBean> getRecommendListLiveData() {
        return recommendListLiveData;
    }

    public void getRecommendList() {
        N.APIThb(OpenIMService.class)
                .getRecommendList()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(RecommendListBean.class))
                .subscribe(new NetObserverThb<RecommendListBean>(getContext()) {
                    @Override
                    public void onSuccess(RecommendListBean it) {
                        recommendListLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * banner轮播图
     */
    private MutableLiveData<BannerImgBean> bannerImgLiveData = new MutableLiveData<>();

    public MutableLiveData<BannerImgBean> getBannerImgLiveData() {
        return bannerImgLiveData;
    }

    public void get_banner_img() {
        Parameter parameter = new Parameter();
        parameter.add("tag", "thb_banner_app");
        N.APIThb(OpenIMService.class)
                .get_banner_img(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BannerImgBean.class))
                .subscribe(new NetObserverThb<BannerImgBean>(getContext()) {
                    @Override
                    public void onSuccess(BannerImgBean it) {
                        bannerImgLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 游戏列表下的商品信息
     */
    // 使用 Map 存储不同 Fragment 的 LiveData
    private Map<Integer, MutableLiveData<GameGoodsListBean>> gameGoodsListBeanMutableLiveData = new HashMap<>();

    public MutableLiveData<GameGoodsListBean> getGameGoodsListBeanMutableLiveData(Integer fragmentTag) {
        if (!gameGoodsListBeanMutableLiveData.containsKey(fragmentTag)) {
            gameGoodsListBeanMutableLiveData.put(fragmentTag, new MutableLiveData<>());
        }
        return gameGoodsListBeanMutableLiveData.get(fragmentTag);
    }

    public void getGameGoodsList(Integer fragmentTag,int page, int game_id) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("game_id", game_id);

        N.APIThb(OpenIMService.class)
                .getGameGoodsList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GameGoodsListBean.class))
                .subscribe(new NetObserverThb<GameGoodsListBean>(getContext()) {
                    @Override
                    public void onSuccess(GameGoodsListBean it) {
                        gameGoodsListBeanMutableLiveData.get(fragmentTag).postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 获取商品类型
     */
    private MutableLiveData<CategoryBean> categoryBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CategoryBean> getCategoryBeanMutableLiveData() {
        return categoryBeanMutableLiveData;
    }

    public void getCategory() {
        N.APIThb(OpenIMService.class)
                .getCategory()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(CategoryBean.class))
                .subscribe(new NetObserverThb<CategoryBean>(getContext()) {
                    @Override
                    public void onSuccess(CategoryBean it) {
                        categoryBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 交易模式获取
     */
    private MutableLiveData<PatternBean> patternBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<PatternBean> getPatternBeanMutableLiveData() {
        return patternBeanMutableLiveData;
    }

    public void getPattern() {
        N.APIThb(OpenIMService.class)
                .getPattern()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(PatternBean.class))
                .subscribe(new NetObserverThb<PatternBean>(getContext()) {
                    @Override
                    public void onSuccess(PatternBean it) {
                        patternBeanMutableLiveData.postValue(it);
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
        parameter.add("greet_type", 2);//打招呼类型 0不打招呼 2咨询招呼 4中介招呼（一小时内同一个客服只会招呼一次）
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

    public interface ViewAction extends IView {
        void err(String msg);
    }

}
