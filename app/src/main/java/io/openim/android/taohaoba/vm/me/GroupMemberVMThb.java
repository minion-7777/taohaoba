package io.openim.android.taohaoba.vm.me;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.tencent.qcloud.tuikit.tuichat.bean.ImGroupInfoBean;
import com.tencent.qcloud.tuikit.tuichat.repository.ThbApiService;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;

public class GroupMemberVMThb extends BaseViewModel<GroupMemberVMThb.ViewAction> {
    public String groupId;

    private MutableLiveData<ImGroupInfoBean> liveDataImGroupInfoBean = new MutableLiveData<>();

    public MutableLiveData<ImGroupInfoBean> getLiveDataImGroupInfoBean() {
        return liveDataImGroupInfoBean;
    }

    public void getSuperGroupMemberList() {
        Parameter parameter = new Parameter();
        parameter.add("groupID", groupId);
        N.APIThb(ThbApiService.class)
                .getGroupInfoByImGroupId(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(ThbApiService.turnThb(ImGroupInfoBean.class))
                .subscribe(new NetObserverThb<ImGroupInfoBean>(getContext()) {
                    @Override
                    public void onSuccess(ImGroupInfoBean imGroupBean) {
                        liveDataImGroupInfoBean.postValue(imGroupBean);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().toast(baseTHB.error);
                    }
                });
    }

    public interface ViewAction extends io.openim.android.ouicore.base.IView {
        void err(String msg);
    }
}
