package io.openim.android.taohaoba.ui.activity.me;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.config.PreferencesKey;
import io.openim.android.taohaoba.databinding.ActivityPushSettingBinding;
import io.openim.android.taohaoba.vm.me.AuthenticationCenterVM;
import io.openim.android.taohaoba.vm.me.PushSettingVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 实名认证
 */
@Route(path = Routes.Main.PUSH_SETTING)
public class PushSettingActivity extends BaseActivity<PushSettingVM, ActivityPushSettingBinding> implements AuthenticationCenterVM.ViewAction {
    private static final String TAG = "PushSettingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(PushSettingVM.class);
        bindViewDataBinding(ActivityPushSettingBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();
    }

    protected void initView() {
        boolean allowBeep = mmkv.decodeBool(PreferencesKey.allowBeep,  true);
        boolean allowVibration = mmkv.decodeBool(PreferencesKey.allowVibration,  true);
        Log.d(TAG, "allowBeep="+allowBeep);
        Log.d(TAG, "allowVibration="+allowVibration);
        view.switchBeep.setChecked(allowBeep);
        view.switchVibration.setChecked(allowVibration);
        view.switchBeep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    mmkv.encode(PreferencesKey.allowBeep,true);
                }else {
                    mmkv.encode(PreferencesKey.allowBeep,false);
                }
                boolean allowBeep = mmkv.decodeBool(PreferencesKey.allowBeep,  true);
                boolean allowVibration = mmkv.decodeBool(PreferencesKey.allowVibration,  true);
                Log.d(TAG, "switchBeep-allowBeep="+allowBeep);
                Log.d(TAG, "switchBeep-allowVibration="+allowVibration);
            }
        });
        view.switchVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    mmkv.encode(PreferencesKey.allowVibration,true);
                }else {
                    mmkv.encode(PreferencesKey.allowVibration,false);
                }
                boolean allowBeep = mmkv.decodeBool(PreferencesKey.allowBeep,  true);
                boolean allowVibration = mmkv.decodeBool(PreferencesKey.allowVibration,  true);
                Log.d(TAG, "switchVibration-allowBeep="+allowBeep);
                Log.d(TAG, "switchVibration-allowVibration="+allowVibration);
            }
        });

    }

    protected void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });
    }


    @Override
    public void err(String msg) {
        showToast(msg);
    }
}
