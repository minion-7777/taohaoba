package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.CouponListBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class CouponVM extends BaseViewModel<AuthenticationCenterVM.ViewAction>{

    /**
     * 用户优惠券列表
     */
    private MutableLiveData<CouponListBean> couponListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CouponListBean> getCouponListBeanMutableLiveData() {
        return couponListBeanMutableLiveData;
    }

    public void getCouponList(int page, int status) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("page_size", 10);
        parameter.add("status", status);// 0未使用 1已使用 2已过期

        N.APIThb(OpenIMService.class)
                .getCouponList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(CouponListBean.class))
                .subscribe(new NetObserverThb<CouponListBean>(getContext()) {
                    @Override
                    public void onSuccess(CouponListBean it) {
                        couponListBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 订单获取可用优惠券列表
     */
    private MutableLiveData<CouponListBean> orderAvailableListBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<CouponListBean> getOrderAvailableListBeanMutableLiveData() {
        return orderAvailableListBeanMutableLiveData;
    }

    public void getOrderAvailableList() {

        N.APIThb(OpenIMService.class)
                .getOrderAvailableList()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(CouponListBean.class))
                .subscribe(new NetObserverThb<CouponListBean>(getContext()) {
                    @Override
                    public void onSuccess(CouponListBean it) {
                        orderAvailableListBeanMutableLiveData.postValue(it);
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
