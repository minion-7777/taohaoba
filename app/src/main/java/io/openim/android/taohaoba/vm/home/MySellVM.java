package io.openim.android.taohaoba.vm.home;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.bean.SellImgBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class MySellVM extends BaseViewModel <MySellVM.ViewAction> {

    /**
     * 我要卖主页图
     */
    private MutableLiveData<BannerImgBean> sellImgBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BannerImgBean> getSellImgBeanMutableLiveData() {
        return sellImgBeanMutableLiveData;
    }

    public void getMy_want_buy_img() {
        Parameter parameter = new Parameter();
        parameter.add("tag", "thb_banner_app");
        N.APIThb(OpenIMService.class)
                .get_banner_img(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BannerImgBean.class))
                .subscribe(new NetObserverThb<BannerImgBean>(getContext()) {
                    @Override
                    public void onSuccess(BannerImgBean it) {
                        sellImgBeanMutableLiveData.postValue(it);
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

    public interface ViewAction extends IView {
        void err(String msg);
    }
}
