package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.luck.picture.lib.utils.ToastUtils.showToast;
import static com.tencent.qcloud.tuikit.tuichat.classicui.widget.ChatView.realNameAuthenticationDialog;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageEBean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;
import com.tencent.qcloud.tuikit.tuichat.bean.AuthInfoBean;
import com.tencent.qcloud.tuikit.tuichat.bean.GetGameAccountValueBean;
import com.tencent.qcloud.tuikit.tuichat.classicui.widget.ChatView;
import com.tencent.qcloud.tuikit.tuichat.dialog.CommonDialog;
import com.tencent.qcloud.tuikit.tuichat.repository.ThbApiService;
import com.tencent.qcloud.tuikit.tuichat.util.ImageZoomUtil;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.ouicore.utils.Routes;

public class IntelligentTipsMessageHolderE extends MessageContentHolder {

    private static final String TAG = IntelligentTipsMessageHolderE.class.getSimpleName();
    private TextView titlel5;
    private TextView contentl5;
    private TextView operationl5;
    private List<String> mListRoutesMain = new ArrayList<>();

    public IntelligentTipsMessageHolderE(View itemView) {
        super(itemView);
        titlel5 = itemView.findViewById(R.id.titlel5);
        contentl5 = itemView.findViewById(R.id.contentl5);
        operationl5 = itemView.findViewById(R.id.operationl5);

        mListRoutesMain.add(Routes.Main.PRODUCT_DETAILS);
        mListRoutesMain.add(Routes.Main.ORDER_DETAILS);
        mListRoutesMain.add(Routes.Main.CONFIRM_AN_ORDER);
        mListRoutesMain.add(Routes.Main.INPUT_INFORMATION);
        mListRoutesMain.add(Routes.Main.SUBMINT_ACCOUNT_INFORMATION);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_5;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageEBean) {
            IntelligentTipsMessageEBean tipsMsg = (IntelligentTipsMessageEBean) msg;

            titlel5.setText(tipsMsg.getTitle().getText());
            if (!TextUtils.isEmpty(tipsMsg.getTitle().getColor())) {
                titlel5.setTextColor(Color.parseColor(tipsMsg.getTitle().getColor()));
            }
            contentl5.setText(tipsMsg.getContent());
            operationl5.setText(tipsMsg.getButton().getText());

            if (tipsMsg.getCardType5ContentType() == 4 || tipsMsg.getCardType5ContentType() == 5) {
                if (TUILogin.getUserId().equalsIgnoreCase(tipsMsg.getIm_buyer_idViewHolder())) {
                    operationl5.setVisibility(VISIBLE);
                } else {
                    operationl5.setVisibility(GONE);
                }
            } else if (tipsMsg.getCardType5ContentType() == 2) {
                if (TUILogin.getUserId().equalsIgnoreCase(tipsMsg.getIm_seller_idViewHolder())) {
                    operationl5.setVisibility(VISIBLE);
                } else {
                    operationl5.setVisibility(GONE);
                }
            } else if (tipsMsg.getCardType5ContentType() == 6) {
                if (TUILogin.getUserId().equalsIgnoreCase(tipsMsg.getIm_seller_idViewHolder())) {
                    operationl5.setVisibility(VISIBLE);
                } else {
                    operationl5.setVisibility(GONE);
                }
            } else if (tipsMsg.getCardType5ContentType() == 3){
                operationl5.setVisibility(VISIBLE);
            } else if (tipsMsg.getCardType5ContentType() == 7){
                operationl5.setVisibility(GONE);
            }else {
                operationl5.setVisibility(VISIBLE);
            }
            operationl5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "intelligentType-5-onClick");
                    //确认验号和收货弹框
                    if (tipsMsg.getCardType5ContentType() == 4 || tipsMsg.getCardType5ContentType() == 5) {

                        if (tipsMsg.getButton().getUrl().getPageName().equalsIgnoreCase("alert_confirm")) {
                            int orderStatusSetType = tipsMsg.getButton().getUrl().getParamId().getType();
                            String hint = "";
                            if (orderStatusSetType == 1) {
                                hint = "是否要确认验号？";
                            } else if (orderStatusSetType == 2) {
                                hint = "是否要确认换绑？";
                            }

                            new XPopup.Builder(getRecyclerView().getContext())
                                    .isViewMode(true)
                                    .asCustom(new CommonDialog(getRecyclerView().getContext(), hint, new CommonDialog.OnVerificationListener() {
                                        @Override
                                        public void onSubmit() {
                                            //操纵类型 1:确认验号 2：确认收货（后台订单确认完成按钮需要传递的参数值） 3：取消订单 4：修改价格 5:延长发货时间  6：发货按钮 7：主动退款（必填字段）
                                            if (orderStatusSetType == 1) {
                                                // 用户点击确认后的操作
                                                Parameter parameter = new Parameter();
                                                parameter.add("order_id", tipsMsg.getButton().getUrl().getParamId().getOrder_id());
                                                parameter.add("type", orderStatusSetType);
                                                parameter.add("amount", 0);
                                                parameter.add("refund_content", "");
                                                parameter.add("platform", 2);
                                                N.APIThb(ThbApiService.class)
                                                        .setOrderStatus(parameter.buildJsonBody())
                                                        .compose(N.IOMain())
                                                        .map(ThbApiService.turnThb(Object.class))
                                                        .subscribe(new NetObserverThb<Object>(getRecyclerView().getContext()) {
                                                            @Override
                                                            public void onSuccess(Object it) {
                                                                Toast.makeText(getRecyclerView().getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            protected void onFailure(BaseTHB baseTHB) {
                                                                Toast.makeText(getRecyclerView().getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else if (orderStatusSetType == 2) {
                                                // 用户点击确认后的操作
                                                Parameter parameter = new Parameter();
                                                parameter.add("order_id", tipsMsg.getButton().getUrl().getParamId().getOrder_id());
                                                parameter.add("type", orderStatusSetType);
                                                parameter.add("amount", 0);
                                                parameter.add("refund_content", "");
                                                parameter.add("platform", 2);
                                                N.APIThb(ThbApiService.class)
                                                        .setOrderStatus(parameter.buildJsonBody())
                                                        .compose(N.IOMain())
                                                        .map(ThbApiService.turnThb(Object.class))
                                                        .subscribe(new NetObserverThb<Object>(getRecyclerView().getContext()) {
                                                            @Override
                                                            public void onSuccess(Object it) {
                                                                Toast.makeText(getRecyclerView().getContext(), "操作成功", Toast.LENGTH_SHORT).show();
                                                            }

                                                            @Override
                                                            protected void onFailure(BaseTHB baseTHB) {
                                                                Toast.makeText(getRecyclerView().getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    })).show();
                        }
                    } else if (tipsMsg.getCardType5ContentType() == 2) {
                        //填写账号信息
                        if (ChatView.getOrderStatus() != 1 && ChatView.getOrderStatus() != 3) {
                            return;
                        }
                        if (!TextUtils.isEmpty(tipsMsg.getButton().getUrl().getPageName())) {
                            boolean isMatch = false;
                            String matchedRoutesPageName = "";
                            for (int i = 0; i < mListRoutesMain.size(); i++) {
                                if (mListRoutesMain.get(i).contains(tipsMsg.getButton().getUrl().getPageName())) {
                                    isMatch = true;
                                    matchedRoutesPageName = mListRoutesMain.get(i);
                                    break;
                                }
                            }
                            if (isMatch) {
                                ARouter.getInstance().build(matchedRoutesPageName)
                                        .withInt("gameId", tipsMsg.getButton().getUrl().getParamId().getGame_id())
                                        .withInt("goodsId", tipsMsg.getButton().getUrl().getParamId().getGoods_id())
                                        .withInt("orderId", tipsMsg.getButton().getUrl().getParamId().getOrder_id())
                                        .withString("imGroupId", msg.getGroupId())
                                        .withString("imGroupOwnerUserID", tipsMsg.getIm_owner_idViewHolder())
                                        .navigation();
                            } else {
                                Toast.makeText(getRecyclerView().getContext(), "app版本过低，请升级。", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (tipsMsg.getCardType5ContentType() == 3) {
                        //提取账号信息
                        if (tipsMsg.getButton().getUrl().getPageName().equalsIgnoreCase("alert_show")) {
                            if (ChatView.getOrderStatus() >= 10) {
                                Toast.makeText(getRecyclerView().getContext(), "退款订单无法获取账号信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Parameter parameter = new Parameter();
                            parameter.add("goods_id", tipsMsg.getButton().getUrl().getParamId().getParamId());
                            N.APIThb(ThbApiService.class)
                                    .getGameAccountValue(parameter.buildJsonBody())
                                    .compose(N.IOMain())
                                    .map(ThbApiService.listTurnThb(GetGameAccountValueBean.class))
                                    .subscribe(new NetObserverThb<List<GetGameAccountValueBean>>(getRecyclerView().getContext()) {
                                        @Override
                                        public void onSuccess(List<GetGameAccountValueBean> list) {
                                            if (list != null && list.size() > 0) {
                                                String authentication_image_value = "";
                                                String account_source_image = "";
                                                authentication_image_value = list.get(0).getAuthentication_image().toString();
                                                account_source_image = list.get(0).getAccount_source_image().toString();

                                                View dialogView = LayoutInflater.from(getRecyclerView().getContext())
                                                        .inflate(R.layout.conversation_dialog_get_account_infomation, null);
                                                dialogView.setBackground(getRecyclerView().getContext().getDrawable(R.color.color_242527));

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getRecyclerView().getContext());
                                                builder.setView(dialogView);
                                                // 创建并显示对话框
                                                AlertDialog dialog = builder.create();

                                                RecyclerView rc_recycler = dialogView.findViewById(R.id.rc_recycler);
                                                ImageView iv_image = dialogView.findViewById(R.id.iv_image);
                                                ImageView iv_image1 = dialogView.findViewById(R.id.iv_image1);
                                                LinearLayout ll_sm = dialogView.findViewById(R.id.ll_sm);
                                                LinearLayout ll_ly = dialogView.findViewById(R.id.ll_ly);
                                                BaseQuickAdapter baseQuickAdapter = new BaseQuickAdapter(R.layout.item_conversation_dialog_get_account_infomation, list) {
                                                    @Override
                                                    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

                                                    }

                                                    @Override
                                                    protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                                                        GetGameAccountValueBean it = (GetGameAccountValueBean) o;
                                                        baseViewHolder.setText(R.id.tv_name, it.getKey());
                                                        baseViewHolder.setText(R.id.et_game_account, it.getAccount_value());

                                                        baseViewHolder.getView(R.id.et_game_account).setOnClickListener(v -> {
                                                            ClipboardManager clipboard = (ClipboardManager) baseViewHolder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                                                            ClipData clip = ClipData.newPlainText("label", it.getAccount_value());
                                                            clipboard.setPrimaryClip(clip);
                                                            showToast(baseViewHolder.itemView.getContext(), "复制成功");
                                                        });
                                                    }
                                                };
                                                rc_recycler.setLayoutManager(new LinearLayoutManager(getRecyclerView().getContext(), LinearLayoutManager.VERTICAL, false));
                                                rc_recycler.setAdapter(baseQuickAdapter);

                                                ll_sm.setVisibility(TextUtils.isEmpty(authentication_image_value) ? GONE : VISIBLE);
                                                Glide.with(getRecyclerView().getContext())
                                                        .load(authentication_image_value)
                                                        .into(iv_image);

                                                ll_ly.setVisibility(TextUtils.isEmpty(account_source_image) ? GONE : VISIBLE);
                                                Glide.with(getRecyclerView().getContext())
                                                        .load(account_source_image)
                                                        .into(iv_image1);

                                                String finalAuthentication_image_value = authentication_image_value;
                                                iv_image.setOnClickListener(v1 -> {
                                                    ImageZoomUtil.showZoomView(getRecyclerView().getContext(), finalAuthentication_image_value);
                                                    dialog.dismiss();
                                                });

                                                String finalAccount_source_image = account_source_image;
                                                iv_image1.setOnClickListener(v1 -> {
                                                    ImageZoomUtil.showZoomView(getRecyclerView().getContext(), finalAccount_source_image);
                                                    dialog.dismiss();
                                                });

                                                dialog.show();
                                            } else {
                                                Toast.makeText(getRecyclerView().getContext(), "未获取到账号信息", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        protected void onFailure(BaseTHB baseTHB) {
                                            Toast.makeText(getRecyclerView().getContext(), baseTHB.msg, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else if (tipsMsg.getCardType5ContentType() == 6) {
                        //卖家录入商品
                        if (ChatView.getGoodsId() > 0) {
                            showToast(getRecyclerView().getContext(), "已录入商品信息");
                            return;
                        }
                        N.APIThb(ThbApiService.class)
                                .auth_info()
                                .compose(N.IOMain())
                                .map(ThbApiService.turnThb(AuthInfoBean.class))
                                .subscribe(new NetObserverThb<AuthInfoBean>(getRecyclerView().getContext()) {
                                    @Override
                                    public void onSuccess(AuthInfoBean it) {
                                        if (it.getUser_auth_info().getType()!=0) {
                                            //已实名
                                            if (!TextUtils.isEmpty(tipsMsg.getButton().getUrl().getPageName())) {
                                                boolean isMatch = false;
                                                String matchedRoutesPageName = "";
                                                for (int i = 0; i < mListRoutesMain.size(); i++) {
                                                    if (mListRoutesMain.get(i).contains(tipsMsg.getButton().getUrl().getPageName())) {
                                                        isMatch = true;
                                                        matchedRoutesPageName = mListRoutesMain.get(i);
                                                        break;
                                                    }
                                                }
                                                if (isMatch) {
                                                    ARouter.getInstance().build(matchedRoutesPageName)
                                                            .withInt("buyer_id", tipsMsg.getButton().getUrl().getParamId().getBuyer_id())
                                                            .withInt("game_id", tipsMsg.getButton().getUrl().getParamId().getGame_id())
                                                            .withString("game_name", tipsMsg.getButton().getUrl().getParamId().getGame_name())
                                                            .withInt("pattern_id", tipsMsg.getButton().getUrl().getParamId().getPattern_id())
                                                            .withInt("seller_id", tipsMsg.getButton().getUrl().getParamId().getSeller_id())
                                                            .withString("imGroupId", msg.getGroupId())
                                                            .withString("imGroupOwnerUserID", ChatView.getImGroupInfoBean().getImGroup().getIm_owner_id())
                                                            .withInt("seller_service_ratio", ChatView.getImGroupInfoBean().getImGroup().getSeller_service_ratio())
                                                            .withDouble("seller_service_price", ChatView.getImGroupInfoBean().getImGroup().getSeller_service_price())
                                                            .withDouble("seller_amount_conf", ChatView.getImGroupInfoBean().getImGroup().getSeller_amount_conf())
                                                            .navigation();
                                                } else {
                                                    Toast.makeText(getRecyclerView().getContext(), "app版本过低，请升级。", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }else {
                                            //未实名认证
                                            realNameAuthenticationDialog(getRecyclerView().getContext());
                                        }
                                    }

                                    @Override
                                    protected void onFailure(BaseTHB baseTHB) {
                                        Toast.makeText(getRecyclerView().getContext(), baseTHB.error, Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }
            });
        }
    }

}
