package io.openim.android.taohaoba.vm.me;

import io.openim.android.ouicore.base.BaseViewModel;

public class PushSettingVM extends BaseViewModel<PushSettingVM.ViewAction> {
    public interface ViewAction extends io.openim.android.ouicore.base.IView {
        void err(String msg);
    }
}
