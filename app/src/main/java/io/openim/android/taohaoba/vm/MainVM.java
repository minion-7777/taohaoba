package io.openim.android.taohaoba.vm;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.CategoryBean;
import io.openim.android.taohaoba.bean.VersionManageBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.vm.login.LoginVMThb;

public class MainVM extends BaseViewModel<LoginVMThb.ViewAction> {

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
        parameter.add("tag", "thb");

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
     * 海报主页图
     */
    private MutableLiveData<BannerImgBean> posterImgMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BannerImgBean> getPosterImgMutableLiveData() {
        return posterImgMutableLiveData;
    }

    public void get_poster_img() {
        Parameter parameter = new Parameter();
        parameter.add("tag", "thb_poster_app");
        N.APIThb(OpenIMService.class)
                .get_banner_img(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BannerImgBean.class))
                .subscribe(new NetObserverThb<BannerImgBean>(getContext()) {
                    @Override
                    public void onSuccess(BannerImgBean it) {
                        posterImgMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

}
