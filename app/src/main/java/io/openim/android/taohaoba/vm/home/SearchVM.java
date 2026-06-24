package io.openim.android.taohaoba.vm.home;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.GameGoodsListBean;
import io.openim.android.taohaoba.bean.IndexSearchBean;
import io.openim.android.taohaoba.bean.RecommendListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class SearchVM extends BaseViewModel<SearchVM.ViewAction> {

    /**
     * 首页搜索项
     */
    private MutableLiveData<IndexSearchBean> indexSearchBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<IndexSearchBean> getIndexSearchBeanMutableLiveData() {
        return indexSearchBeanMutableLiveData;
    }

    /**
     * @param page                      页码（必填）
     * @param sort_type                 默认为0   1 ： 价格降序  2：价格升序  3：时间降序 4：时间升序
     * @param device_id                 设备id
     * @param game_type_id              游戏分类  端游 手游
     * @param operator_id               运营商id
     * @param tall_float                最高价格
     * @param lower_float               最低价格
     * @param title                     商品
     */
    public void index_search(int page, int sort_type, int device_id, int game_type_id, int operator_id, Float tall_float, Float lower_float, String title) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("sort_type", sort_type);
        parameter.add("device_id", device_id);
        parameter.add("game_type_id", game_type_id);
        parameter.add("operator_id", operator_id);
        parameter.add("title", title);
        parameter.add("tall_float", tall_float);
        parameter.add("lower_float", lower_float);

        N.APIThb(OpenIMService.class)
                .index_search(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(IndexSearchBean.class))
                .subscribe(new NetObserverThb<IndexSearchBean>(getContext()) {
                    @Override
                    public void onSuccess(IndexSearchBean it) {
                        indexSearchBeanMutableLiveData.postValue(it);
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
