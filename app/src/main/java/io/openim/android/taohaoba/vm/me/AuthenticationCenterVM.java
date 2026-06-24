package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.AuthInfoBean;
import io.openim.android.taohaoba.bean.VerifyInfoBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.utils.RSA;

public class AuthenticationCenterVM extends BaseViewModel<AuthenticationCenterVM.ViewAction> {


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
     * 包赔认证
     */
    private MutableLiveData<Object> reparationVerifyBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Object> getReparationVerifyBeanMutableLiveData() {
        return reparationVerifyBeanMutableLiveData;
    }

    /**
     * @param reparation_image      包赔图片
     * @param emergency_contact     紧急联系人
     * @param emergency_phone       紧急联系人电话
     * @param location              经纬度
     * @param contacts              通讯录联系人
     * @param address               联系地址
     */
    public void reparation_verify(String reparation_image, String emergency_contact, String emergency_phone, String location, String contacts, String address) {

        Parameter parameter = new Parameter();
        parameter.add("reparation_image", reparation_image);
        parameter.add("emergency_contact", emergency_contact);
        parameter.add("emergency_phone", emergency_phone);
        parameter.add("location", location);
        parameter.add("contacts", contacts);
        parameter.add("address", address);

        N.APIThb(OpenIMService.class)
                .reparation_verify(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(Object.class))
                .subscribe(new NetObserverThb<Object>(getContext()) {
                    @Override
                    public void onSuccess(Object it) {
                        reparationVerifyBeanMutableLiveData.postValue(it);
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
     * 获取身份证信息的ocr
     */
    private MutableLiveData<VerifyInfoBean> verifyInfoBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<VerifyInfoBean> getVerifyInfoBeanMutableLiveData() {
        return verifyInfoBeanMutableLiveData;
    }

    public void verify_info(String front_img, String back_img) {
        Parameter parameter = new Parameter();
        parameter.add("front_img", front_img);//身份证正面
        parameter.add("back_img", back_img);//身份证反面

        N.APIThb(OpenIMService.class)
                .verify_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(VerifyInfoBean.class))
                .subscribe(new NetObserverThb<VerifyInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(VerifyInfoBean it) {
                        verifyInfoBeanMutableLiveData.postValue(it);
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
