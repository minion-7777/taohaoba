package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameConfigurationListBean;
import io.openim.android.taohaoba.bean.IndexSearchBean;
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;
import io.openim.android.taohaoba.widgets.MyLayoutManager;

/**
 * 游戏区服弹窗
 */
public class GameDistrictServerOneDialog extends BottomPopupView {

    private IndexSearchBean.DeviceOperatorDTO gameConfigurationListBean;
    private Context context;
    private int spacingInPx;
    private BaseQuickAdapter fwqAdapter;
    private int gameTypeId;//游戏分类  端游 手游
    private int deviceId;//设备id
    private int operatorId;//运营商id

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(int gameTypeId, int deviceId, int operatorId); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public GameDistrictServerOneDialog(Context context, IndexSearchBean.DeviceOperatorDTO gameConfigurationListBean, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.gameConfigurationListBean = gameConfigurationListBean;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_game_district_server_one;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        RecyclerView rc_recycler = findViewById(R.id.rc_recycler);
        RecyclerView rc_recycler1 = findViewById(R.id.rc_recycler1);
        RecyclerView rc_recycler2 = findViewById(R.id.rc_recycler2);
        TextView tv_yxlx = findViewById(R.id.tv_yxlx);
        TextView tv_xt = findViewById(R.id.tv_xt);
        TextView tv_yys = findViewById(R.id.tv_yys);

        // 转换dp为px（Android尺寸单位工具）
        spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );

        if (gameConfigurationListBean.getGame_type() != null && gameConfigurationListBean.getGame_type().size() > 0) {
            tv_yxlx.setVisibility(VISIBLE);
            //游戏类型
            setAdapter(1, rc_recycler, gameConfigurationListBean.getGame_type());
        }

        if (gameConfigurationListBean.getDevice() != null && gameConfigurationListBean.getDevice().size() > 0) {
            tv_xt.setVisibility(VISIBLE);
            //系统
            setAdapter(2, rc_recycler1, gameConfigurationListBean.getDevice());
        }

        if (gameConfigurationListBean.getOperator() != null && gameConfigurationListBean.getOperator().size() > 0) {
            tv_yys.setVisibility(VISIBLE);
            //运营商
            setAdapter(3, rc_recycler2, gameConfigurationListBean.getOperator());
        }

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
            if (verificationListener != null) {
                dismiss();
            }
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {
                for (IndexSearchBean.DeviceOperatorDTO.DeviceDTO deviceDTO : gameConfigurationListBean.getGame_type()) {
                    if (deviceDTO.isExpanded()) {
                        gameTypeId = deviceDTO.getId();
                    }
                }
                for (IndexSearchBean.DeviceOperatorDTO.DeviceDTO deviceDTO : gameConfigurationListBean.getDevice()) {
                    if (deviceDTO.isExpanded()) {
                        deviceId = deviceDTO.getId();
                    }
                }
                for (IndexSearchBean.DeviceOperatorDTO.DeviceDTO deviceDTO : gameConfigurationListBean.getOperator()) {
                    if (deviceDTO.isExpanded()) {
                        operatorId = deviceDTO.getId();
                    }
                }
                verificationListener.onSubmit(gameTypeId, deviceId, operatorId);
                dismiss();
            }
        });

    }


    /**
     * @param type 1游戏类型 2系统 3运营商
     * @param rc_recycler
     */
    private void setAdapter(int type, RecyclerView rc_recycler, List<IndexSearchBean.DeviceOperatorDTO.DeviceDTO> deviceDTOList){

        fwqAdapter = new BaseQuickAdapter(R.layout.item_tree_node, deviceDTOList) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                IndexSearchBean.DeviceOperatorDTO.DeviceDTO bean = (IndexSearchBean.DeviceOperatorDTO.DeviceDTO) o;
                baseViewHolder.setText(R.id.tv_node_name, bean.getName());
                TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                baseViewHolder.itemView.setOnClickListener(v->{
                    if (type == 1) {
                        gameTypeId = bean.getId();
                    }else if (type == 2) {
                        deviceId = bean.getId();
                    }else if (type == 3) {
                        operatorId = bean.getId();
                    }
                    for (int i = 0; i < deviceDTOList.size(); i++) {
                        deviceDTOList.get(i).setExpanded(i == baseViewHolder.getPosition());
                    }
                    notifyDataSetChanged();
                });
            }
        };
        rc_recycler.setLayoutManager(new GridLayoutManager(context, 3));
        // 添加间距装饰（包含边缘间距）
        rc_recycler.addItemDecoration(
                new GridSpacingItemDecoration(3, spacingInPx, true)
        );
//        MyLayoutManager layout = new MyLayoutManager();
//        layout.setAutoMeasureEnabled(true);//防止recyclerview高度为wrap时测量item高度0(一定要加这个属性，否则显示不出来）
//        rc_recycler.setLayoutManager(layout);
        rc_recycler.setAdapter(fwqAdapter);
    }
}
