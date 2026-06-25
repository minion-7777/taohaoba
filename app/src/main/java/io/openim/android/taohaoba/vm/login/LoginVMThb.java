package io.openim.android.taohaoba.vm.login;

import static io.openim.android.taohaoba.utils.BadgeNumberUtil.getMobileType;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson2.JSONObject;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.base.IView;
import io.openim.android.ouicore.entity.LoginBean;
import io.openim.android.ouicore.entity.LoginCertificate;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.BuildConfig;
import io.openim.android.taohaoba.DemoApplication;
import io.openim.android.taohaoba.bean.SendSmsBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.signature.GenerateTestUserSig;

public class LoginVMThb extends BaseViewModel<LoginVMThb.ViewAction> {

    private MutableLiveData<LoginBean> loginLiveData = new MutableLiveData<>();

    public MutableLiveData<LoginBean> getLoginLiveData() {
        return loginLiveData;
    }

    private MutableLiveData<LoginCertificate> liveDataLoginCertificate = new MutableLiveData<>();

    public MutableLiveData<LoginCertificate> getLiveDataLoginCertificate() {
        return liveDataLoginCertificate;
    }

    public void login(int type, String username, String password, String code) {
        Parameter parameter = new Parameter();
        parameter.add("type", type);
        parameter.add("username", username);
        parameter.add("password", TextUtils.isEmpty(password) ? null : password);
        parameter.add("code", TextUtils.isEmpty(code) ? null : code);
        parameter.add("platform", 2);
        parameter.add("getui_client_id", DemoApplication.clientid);
        parameter.add("channel_type", BuildConfig.FLAVOR);

        N.APIThb(OpenIMService.class)
                .appLogin(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(LoginBean.class))
                .subscribe(new NetObserverThb<LoginBean>(getContext()) {
                    @Override
                    public void onSuccess(LoginBean loginBean) {
                        TUILogin.login(getContext(), GenerateTestUserSig.SDKAPPID, loginBean.getImResponse().getData().getUserID(), loginBean.getImResponse().getData().getUserSig(), new TUICallback() {
                            @Override
                            public void onSuccess() {
                                Log.d("imsdk", "登录成功");
                                //缓存登录信息
                                LoginCertificate loginCertificate = new LoginCertificate();
                                loginCertificate.userID = loginBean.getImResponse().getData().getUserID();
                                loginCertificate.chatToken = loginBean.getImResponse().getData().getChatToken();

                                loginCertificate.cache(getContext());
                                BaseApp.inst().loginCertificate = loginCertificate;
                                getIView().jump();
                                loginLiveData.postValue(loginBean);
                                liveDataLoginCertificate.postValue(loginCertificate);
                            }

                            @Override
                            public void onError(int errorCode, String errorMessage) {
                                Log.i("imsdk", "登录失败, code:" + errorCode + ", desc:" + errorMessage);
                                getIView().err("登录失败: " + errorMessage + " (" + errorCode + ")");
                            }
                        });

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
                        getIView().err(baseTHB.error);
                    }
                });
    }

    public interface ViewAction extends IView {
        ///跳转
        void jump();

        void err(String msg);

        void succ(Object o);

        void initDate();

    }

}