package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.bean.PatternBean;
import io.openim.android.taohaoba.bean.SellImgBean;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.activity.home.CustomerServiceCenterActivity;
import io.openim.android.taohaoba.utils.DensityUtil;
import io.openim.android.taohaoba.widgets.CustomDecoration;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 我要卖号弹窗
 */
public class SellNumberDialog extends BottomPopupView {

    private Context context;
    private int categoryId;//商品类型id
    private RecyclerView rc_recycler;
    private BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter;
    private List<PatternBean.PatternDTO> list = new ArrayList<>();


    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(String amount); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public SellNumberDialog(Context context, int categoryId, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.categoryId = categoryId;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_sell_number;
    }

    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();
        rc_recycler = findViewById(R.id.rc_recycler);
        RoundImageView iv_img = findViewById(R.id.iv_img);

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            dismiss();
        });

        Parameter parameter = new Parameter();
        parameter.add("tag", "thb_my_want_buy_app");
        N.APIThb(OpenIMService.class)
                .get_banner_img(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BannerImgBean.class))
                .subscribe(new NetObserverThb<BannerImgBean>(getContext()) {
                    @Override
                    public void onSuccess(BannerImgBean it) {
                        if (it != null && it.getList() != null && it.getList().size() > 0) {
                            Glide.with(context)
                                    .load(it.getList().get(0).getPath())
                                    .apply(new RequestOptions()
                                            .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                            .error(R.mipmap.ic_default_image)// 加载失败的占位图
                                            .centerCrop()// 图片裁剪方式
                                    )
                                    .into(iv_img);
                        }
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                    }
                });

        N.APIThb(OpenIMService.class)
                .getPattern()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(PatternBean.class))
                .subscribe(new NetObserverThb<PatternBean>(getContext()) {
                    @Override
                    public void onSuccess(PatternBean it) {
                        list.addAll(it.getPattern());
                        initRec();
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        Toast.makeText(context, baseTHB.error, Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initRec(){
        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_sell_number, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                PatternBean.PatternDTO bean = (PatternBean.PatternDTO) o;
                baseViewHolder.setText(R.id.tv_text1, bean.getName());

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (bean.getType() == 1) {
                        //自由交易
                        context.startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                                .putExtra("pageIndex", 5)
                                .putExtra("title", bean.getName())
                                .putExtra("patternId", bean.getId())
                                .putExtra("categoryId", categoryId)
                                .putExtra("seller_service_ratio", bean.getSeller_service_ratio())
                                .putExtra("seller_service_price", bean.getSeller_service_price()));
                    }else if (bean.getType() == 2){
                        //快速回收
                        context.startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                                .putExtra("pageIndex", 3)
                                .putExtra("title", bean.getName())
                                .putExtra("patternId", bean.getId())
                                .putExtra("categoryId", categoryId));
                    }else if (bean.getType() == 4){
                        //中介担保
                        context.startActivity(new Intent(getContext(), CustomerServiceCenterActivity.class)
                                .putExtra("pageIndex", 7));
                    }
                    dismiss();
                });
            }
        };
        rc_recycler.addItemDecoration(new CustomDecoration(context, CustomDecoration.VERTICAL_LIST, R.drawable.shape_divider_love, DensityUtil.dipToPx(context, 0)));
        rc_recycler.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        rc_recycler.setAdapter(baseQuickAdapter);
    }
}
