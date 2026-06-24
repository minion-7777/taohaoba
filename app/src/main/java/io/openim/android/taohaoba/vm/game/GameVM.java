package io.openim.android.taohaoba.vm.game;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.GameListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class GameVM extends BaseViewModel<GameVM.ViewAction> {

    /**
     * 游戏列表
     */
    private MutableLiveData<GameListBean> gameListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<GameListBean> getGameListBeanMutableLiveData() {
        return gameListBeanMutableLiveData;
    }

    public void getGameList(String name) {
        Parameter parameter = new Parameter();
        parameter.add("name", name);

        N.APIThb(OpenIMService.class)
                .getGameList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(GameListBean.class))
                .subscribe(new NetObserverThb<GameListBean>(getContext()) {
                    @Override
                    public void onSuccess(GameListBean it) {
                        gameListBeanMutableLiveData.postValue(it);
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

    public void assignCustomerService(Integer cs_group_id, Integer game_id) {
        Parameter parameter = new Parameter();
        parameter.add("cs_group_id", cs_group_id);
        parameter.add("game_id", game_id);

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
