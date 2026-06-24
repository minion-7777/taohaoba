package io.openim.android.taohaoba.vm.game;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.FiltersBean;
import io.openim.android.taohaoba.bean.GameConfigurationListBean;
import io.openim.android.taohaoba.bean.GameFilterBean;
import io.openim.android.taohaoba.bean.GoodsListBean;
import io.openim.android.taohaoba.bean.RecommendListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class AccountNumberVM extends BaseViewModel <AccountNumberVM.ViewAction>{

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
     * 获取商品列表信息
     */
    private MutableLiveData<GoodsListBean> goodsListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GoodsListBean> getGoodsListBeanMutableLiveData() {
        return goodsListBeanMutableLiveData;
    }

    public void getGoodsList(String title, Integer game_id, Integer page, Integer sort_type, Integer device_id, Integer operator_id, String game_server_id, Float tall_float, Float lower_float, List<FiltersBean> filters) {
        Parameter parameter = new Parameter();
        parameter.add("title", title);
        parameter.add("game_id", game_id);
        parameter.add("page", page);
        parameter.add("sort_type", sort_type);
        parameter.add("device_id", device_id);
        parameter.add("operator_id", operator_id);
        parameter.add("game_server_id", game_server_id);
        parameter.add("tall_float", tall_float);
        parameter.add("lower_float", lower_float);
        if (filters != null && !filters.isEmpty()) {
            parameter.add("filters", filters);
        }

        N.APIThb(OpenIMService.class)
                .getGoodsList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GoodsListBean.class))
                .subscribe(new NetObserverThb<GoodsListBean>(getContext()) {
                    @Override
                    public void onSuccess(GoodsListBean it) {
                        goodsListBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 获取筛选条件列表
     */
    private MutableLiveData<GameFilterBean> gameFilterBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GameFilterBean> getGameFilterBeanMutableLiveData() {
        return gameFilterBeanMutableLiveData;
    }

    public void get_filter_categories(int game_id) {
        Parameter parameter = new Parameter();
        parameter.add("game_id", game_id);

        N.APIThb(OpenIMService.class)
                .get_filter_categories(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GameFilterBean.class))
                .subscribe(new NetObserverThb<GameFilterBean>(getContext()) {
                    @Override
                    public void onSuccess(GameFilterBean it) {
                        gameFilterBeanMutableLiveData.postValue(it);
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
