package io.openim.android.taohaoba.ui.activity.game;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson2.JSONObject;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.utils.Routes;
import io.openim.android.taohaoba.databinding.ActivityInputInformationBinding;
import io.openim.android.taohaoba.ui.activity.me.WebViewActivity;
import io.openim.android.taohaoba.ui.dialog.InputPriceDialog;
import io.openim.android.taohaoba.vm.me.InputInformationVM;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 录入信息
 */
@Route(path = Routes.Main.INPUT_INFORMATION)
public class InputInformationActivity extends BaseActivity<InputInformationVM, ActivityInputInformationBinding> implements InputInformationVM.ViewAction{

//    private String paramId;//参数
    private int buyer_id;
    private int game_id;
    private int seller_id;
    private int pattern_id;
    private String imGroupId;
    private String imGroupOwnerUserID;
    private int seller_service_ratio;//卖家费率
    private double seller_service_price;//卖家最低费率
    private double seller_amount_conf;//固定金额

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewDataBinding(ActivityInputInformationBinding.inflate(getLayoutInflater()));
        bindVM(InputInformationVM.class);
        adjustToolbarForStatusBar(view.toolbar);

        initView();
        initListener();

    }

    private void initView() {
//        String paramId = getIntent().getStringExtra("paramId");
        imGroupId = getIntent().getStringExtra("imGroupId");
        imGroupOwnerUserID = getIntent().getStringExtra("imGroupOwnerUserID");
        seller_service_ratio = getIntent().getIntExtra("seller_service_ratio", 0);
        seller_service_price = getIntent().getDoubleExtra("seller_service_price", 0);
        seller_amount_conf = getIntent().getDoubleExtra("seller_amount_conf", 0);

//        JSONObject jsonObjectParamId =  JSONObject.parseObject(paramId);
        buyer_id = getIntent().getIntExtra("buyer_id",0);
        game_id = getIntent().getIntExtra("game_id",0);
        seller_id = getIntent().getIntExtra("seller_id",0);
        pattern_id = getIntent().getIntExtra("pattern_id",0);
        view.tvName.setText(getIntent().getStringExtra("game_name"));

        view.etPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //提交信息
        vm.getMutableLiveDataGoodsRecycle().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            showToast("提交成功");
            finish(); // 结束当前Activity
        });
    }

    private void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.etPrice.setOnClickListener(v -> {
            changePriceDialog(seller_service_ratio, seller_service_price, seller_amount_conf);
        });

        //提交
        view.tvSubmit.setOnClickListener(v->{

            if (TextUtils.isEmpty(view.etAccount.getText().toString())){
                shakeAnimation(view.etAccount);
                showToast("请填写游戏账号");
                return;
            }if (TextUtils.isEmpty(view.etPrice.getText().toString())){
                shakeAnimation(view.etPrice);
                showToast("请填写价格");
                return;
            }

            showLoadingDialog();
            vm.goodsRecycle(
                    game_id,
                    pattern_id,
                    view.etAccount.getText().toString(),
                    TextUtils.isEmpty(view.etPrice.getText().toString())? 0 : Float.parseFloat(view.etPrice.getText().toString()),
                    seller_id,
                    buyer_id,
                    imGroupId,
                    imGroupOwnerUserID);

        });

        view.tvSellerSaleAgreement.setOnClickListener(v->{
            startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("category_id", 1002));
        });
    }

    /**
     * 修改价格弹窗
     */
    private void changePriceDialog(Integer seller_service_ratio, Double seller_service_price, Double seller_amount_conf){
        BasePopupView popupView = new XPopup.Builder(this)
                .isViewMode(true)
                .asCustom(new InputPriceDialog(this, seller_service_ratio, seller_service_price, seller_amount_conf, new InputPriceDialog.OnVerificationListener() {
                    @Override
                    public void onSubmit(String amount) {
                        view.etPrice.setText(amount);
                    }
                })).show();
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        toast(msg);
    }
}
