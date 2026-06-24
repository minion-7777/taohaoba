package io.openim.android.taohaoba.ui.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import io.openim.android.taohaoba.widgets.GridSpacingItemDecoration;
import io.openim.android.taohaoba.widgets.MyLayoutManager;

/**
 * 游戏区服弹窗
 */
public class GameDistrictServerDialog extends BottomPopupView {

    private GameConfigurationListBean gameConfigurationListBean;
    private Context context;
    private int type;
    private int spacingInPx;
    private BaseQuickAdapter fwqAdapter;
    private int game_typeId;//游戏类型id
    private int deviceId;//设备id
    private String game_serverId;//游戏区服id
    private int operatorId;//运营商id
    private String game_typeName;//游戏类型名称
    private String deviceName;//设备名称
    private String game_serverName;//游戏区服名称
    private String operatorName;//运营商名称
    private int mPosition;
    private BaseQuickAdapter baseQuickAdapter1;
    private BaseQuickAdapter baseQuickAdapter2;
    private BaseQuickAdapter baseQuickAdapter3;

    // 定义回调接口
    public interface OnVerificationListener {
        void onSubmit(int game_typeId, int deviceId, String game_serverId, int operatorId, String game_typeName, String deviceName, String game_serverName, String operatorName); // 验证成功回调
    }

    private OnVerificationListener verificationListener;

    // 设置回调监听器
    public void setOnVerificationListener(OnVerificationListener listener) {
        this.verificationListener = listener;
    }

    public GameDistrictServerDialog(Context context,int type, GameConfigurationListBean gameConfigurationListBean, OnVerificationListener listener) {
        super(context);
        this.context = context;
        this.type = type;
        this.gameConfigurationListBean = gameConfigurationListBean;
        this.verificationListener = listener;
    }

    // 返回自定义弹窗的布局
    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_game_district_server;
    }
    // 执行初始化操作，比如：findView，设置点击，或者任何你弹窗内的业务逻辑
    @Override
    protected void onCreate() {
        super.onCreate();

        RecyclerView rc_recycler1 = findViewById(R.id.rc_recycler1);
        RecyclerView rc_recycler2 = findViewById(R.id.rc_recycler2);
        RecyclerView rc_recycler3 = findViewById(R.id.rc_recycler3);
        TextView tv_yxlx = findViewById(R.id.tv_yxlx);
        TextView tv_sb = findViewById(R.id.tv_sb);
        TextView tv_yxqf = findViewById(R.id.tv_yxqf);
        TextView tv_yys = findViewById(R.id.tv_yys);
        LinearLayout ll_yxqf2 = findViewById(R.id.ll_yxqf2);
        RecyclerView rc_recycler4 = findViewById(R.id.rc_recycler4);

        TextView tv_game_type_name = findViewById(R.id.tv_game_type_name);

        // 转换dp为px（Android尺寸单位工具）
        spacingInPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                10, // 10dp
                getResources().getDisplayMetrics()
        );

        if (gameConfigurationListBean.getGame_type() != null) {
            //游戏类型
            tv_yxlx.setVisibility(VISIBLE);
            tv_game_type_name.setVisibility(VISIBLE);
            tv_game_type_name.setText(gameConfigurationListBean.getGame_type().getName());
        }

        if (gameConfigurationListBean.getDevice() != null && gameConfigurationListBean.getDevice().size() > 0) {
            //设备
            tv_sb.setVisibility(VISIBLE);
            baseQuickAdapter1 = new BaseQuickAdapter(R.layout.item_tree_node, gameConfigurationListBean.getDevice()) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    GameConfigurationListBean.DeviceDTO bean = (GameConfigurationListBean.DeviceDTO) o;
                    baseViewHolder.setText(R.id.tv_node_name, bean.getName());
                    TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                    tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                    tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                    baseViewHolder.itemView.setOnClickListener(v->{
                        for (int i = 0; i < gameConfigurationListBean.getDevice().size(); i++) {
                            gameConfigurationListBean.getDevice().get(i).setExpanded(i == baseViewHolder.getPosition());
                        }
                        notifyDataSetChanged();
                    });
                }
            };
            rc_recycler1.setLayoutManager(new GridLayoutManager(context, 3));
            // 添加间距装饰（包含边缘间距）
            rc_recycler1.addItemDecoration(
                    new GridSpacingItemDecoration(3, spacingInPx, true)
            );
            rc_recycler1.setAdapter(baseQuickAdapter1);

        }

        if (gameConfigurationListBean.getGame_server() != null && gameConfigurationListBean.getGame_server().size() > 0) {
            tv_yxqf.setVisibility(VISIBLE);

            setAdapter(rc_recycler4);

            baseQuickAdapter2 = new BaseQuickAdapter(R.layout.item_tree_node, gameConfigurationListBean.getGame_server()) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    GameConfigurationListBean.GameServerDTO bean = (GameConfigurationListBean.GameServerDTO) o;

                    baseViewHolder.setText(R.id.tv_node_name, bean.getName());
                    TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                    tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                    tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                    baseViewHolder.itemView.setOnClickListener(v->{
                        for (int i = 0; i < gameConfigurationListBean.getGame_server().size(); i++) {
                            gameConfigurationListBean.getGame_server().get(i).setExpanded(i == baseViewHolder.getPosition());
                        }
                        notifyDataSetChanged();

                        ll_yxqf2.setVisibility(bean.getParentItem() != null && bean.getParentItem().size() > 0 ? VISIBLE : GONE);
                        if(fwqAdapter != null && bean.getParentItem() != null){
                            //更新服务商列表数据
                            mPosition = baseViewHolder.getPosition();
                            fwqAdapter.setNewData(bean.getParentItem());
                            fwqAdapter.notifyDataSetChanged();
                        }

                    });
                }
            };
            rc_recycler2.setLayoutManager(new GridLayoutManager(context, 3));
            // 添加间距装饰（包含边缘间距）
            rc_recycler2.addItemDecoration(
                    new GridSpacingItemDecoration(3, spacingInPx, true)
            );
            rc_recycler2.setAdapter(baseQuickAdapter2);
        }

        if (gameConfigurationListBean.getOperator() != null && gameConfigurationListBean.getOperator().size() > 0) {
            tv_yys.setVisibility(VISIBLE);
            baseQuickAdapter3 = new BaseQuickAdapter(R.layout.item_tree_node, gameConfigurationListBean.getOperator()) {
                @Override
                protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                    GameConfigurationListBean.DeviceDTO bean = (GameConfigurationListBean.DeviceDTO) o;
                    baseViewHolder.setText(R.id.tv_node_name, bean.getName());
                    TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                    tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                    tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                    baseViewHolder.itemView.setOnClickListener(v->{
                        for (int i = 0; i < gameConfigurationListBean.getOperator().size(); i++) {
                            gameConfigurationListBean.getOperator().get(i).setExpanded(i == baseViewHolder.getPosition());
                        }
                        notifyDataSetChanged();
                    });
                }
            };
            rc_recycler3.setLayoutManager(new GridLayoutManager(context, 3));
            // 添加间距装饰（包含边缘间距）
            rc_recycler3.addItemDecoration(
                    new GridSpacingItemDecoration(3, spacingInPx, true)
            );
            rc_recycler3.setAdapter(baseQuickAdapter3);
        }

        findViewById(R.id.tv_cancel).setOnClickListener(v->{
                game_typeId = 0;
                game_typeName = "";
                deviceId = 0;
                deviceName = "";
                game_serverId = "";
                game_serverName = "";
                operatorId = 0;
                operatorName = "";
//                dismiss();
                if (baseQuickAdapter1 != null) {
                    for (int i = 0; i < gameConfigurationListBean.getDevice().size(); i++) {
                        gameConfigurationListBean.getDevice().get(i).setExpanded(false);
                    }
                    baseQuickAdapter1.notifyDataSetChanged();
                }
                if (baseQuickAdapter2 != null) {
                    for (int i = 0; i < gameConfigurationListBean.getGame_server().size(); i++) {
                        gameConfigurationListBean.getGame_server().get(i).setExpanded(false);
                        for (int i1 = 0; i1 < gameConfigurationListBean.getGame_server().get(i).getParentItem().size(); i1++) {
                            gameConfigurationListBean.getGame_server().get(i).getParentItem().get(i1).setExpanded(false);
                            if (fwqAdapter != null) {
                                fwqAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                    baseQuickAdapter2.notifyDataSetChanged();
                }
                if (baseQuickAdapter3 != null) {
                    for (int i = 0; i < gameConfigurationListBean.getOperator().size(); i++) {
                        gameConfigurationListBean.getOperator().get(i).setExpanded(false);
                    }
                    baseQuickAdapter3.notifyDataSetChanged();
                }
        });

        findViewById(R.id.tv_submit).setOnClickListener(v->{
            if (verificationListener != null) {

                if (gameConfigurationListBean.getGame_type() != null) {
                    game_typeId = gameConfigurationListBean.getGame_type().getId();
                    game_typeName = gameConfigurationListBean.getGame_type().getName();
                }

                if (gameConfigurationListBean.getDevice() != null) {
                    for (GameConfigurationListBean.DeviceDTO deviceDTO : gameConfigurationListBean.getDevice()) {
                        if (deviceDTO.isExpanded()) {
                            deviceId = deviceDTO.getId();
                            deviceName = deviceDTO.getName();
                        }
                    }
                }
                if (gameConfigurationListBean.getGame_server() != null) {
                    // 调用示例
                    List<GameConfigurationListBean.GameServerDTO> rootNodes = gameConfigurationListBean.getGame_server(); // 获取根节点列表
                    List<String> idList = new ArrayList<>();
                    List<String> nameList = new ArrayList<>();

                    if (type == 2) {
                        collectExpandedIds1(rootNodes, idList, nameList);
                    }else {
                        // 调用修改后的collectExpandedIds，直接获取校验结果
                        isSelect = collectExpandedIds(rootNodes, idList, nameList);
                    }
                    // 生成逗号分隔字符串（Android特化版）
                    game_serverId = TextUtils.join(",", idList); // 使用Android SDK原生方法
                    game_serverName = TextUtils.join(".", nameList);
                }

                if (gameConfigurationListBean.getOperator() != null) {
                    for (GameConfigurationListBean.DeviceDTO operatorDTO : gameConfigurationListBean.getOperator()) {
                        if (operatorDTO.isExpanded()) {
                            operatorId = operatorDTO.getId();
                            operatorName = operatorDTO.getName();
                        }
                    }
                }

                if (type == 1) {
                    if (!isSelect) {
                        Toast.makeText(context, "请选择区服信息", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                verificationListener.onSubmit(game_typeId, deviceId, game_serverId, operatorId, game_typeName, deviceName, game_serverName, operatorName);
                dismiss();
            }
        });

        findViewById(R.id.iv_close).setOnClickListener(v->{
            dismiss();
        });

    }

    private boolean isSelect = true;

    public void collectExpandedIds1(List<GameConfigurationListBean.GameServerDTO> nodes, List<String> result, List<String> nameList) {
        for (GameConfigurationListBean.GameServerDTO node : nodes) {
            if (node.isExpanded()) {
                isSelect = true;
                result.add(String.valueOf(node.getId()));
                nameList.add(node.getName());
                // 递归处理子节点[6,7](@ref)
                if (node.getParentItem() != null && !node.getParentItem().isEmpty()) {
                    collectExpandedIds(node.getParentItem(), result, nameList);
                }
            }else if (node.getParentItem() != null && !node.isExpanded()){
                isSelect = false;
            }
        }
    }

    // 修改方法返回值为boolean，用于标记当前层级是否有有效选中项
    private boolean collectExpandedIds(List<GameConfigurationListBean.GameServerDTO> nodes, List<String> result, List<String> nameList) {
        boolean hasValidSelection = false; // 当前层级是否有选中项

        for (GameConfigurationListBean.GameServerDTO node : nodes) {
            if (node.isExpanded()) {
                hasValidSelection = true; // 标记当前层级有选中项
                result.add(String.valueOf(node.getId()));
                nameList.add(node.getName());

                // 若当前节点有下级列表，递归校验子节点
                if (node.getParentItem() != null && !node.getParentItem().isEmpty()) {
                    boolean hasChildSelection = collectExpandedIds(node.getParentItem(), result, nameList);
                    if (!hasChildSelection) { // 子节点未选中任何项
//                        Toast.makeText(context, "请选择「" + node.getName() + "」的子项", Toast.LENGTH_SHORT).show();
                        return false; // 终止递归，返回无效
                    }
                }
            }
        }

        // 若当前层级无选中项，但存在下级列表，提示用户必须选择
        if (!hasValidSelection) {
            for (GameConfigurationListBean.GameServerDTO node : nodes) {
                if (node.getParentItem() != null && !node.getParentItem().isEmpty()) {
//                    Toast.makeText(context, "请选择「" + node.getName() + "」的选项", Toast.LENGTH_SHORT).show();
                    return false; // 终止递归，返回无效
                }
            }
        }

        return hasValidSelection; // 返回当前层级是否有效
    }

    /**
     * @param rc_recycler
     * @return
     */
    private void setAdapter(RecyclerView rc_recycler){
        fwqAdapter = new BaseQuickAdapter(R.layout.item_tree_node, gameConfigurationListBean.getGame_server().get(mPosition).getParentItem()) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                GameConfigurationListBean.GameServerDTO bean = (GameConfigurationListBean.GameServerDTO) o;
                baseViewHolder.setText(R.id.tv_node_name, bean.getName());
                TextView tv_node_name = baseViewHolder.getView(R.id.tv_node_name);
                tv_node_name.setBackground(context.getDrawable(bean.isExpanded() ? R.drawable.shape_radius15_eaca92 : R.drawable.shape_radius15_line_d6d6d6));
                tv_node_name.setTextColor(context.getColor(bean.isExpanded() ? R.color.color_402802 : R.color.color_D6D6D6));

                baseViewHolder.itemView.setOnClickListener(v->{
                    for (int i = 0; i < gameConfigurationListBean.getGame_server().get(mPosition).getParentItem().size(); i++) {
                        gameConfigurationListBean.getGame_server().get(mPosition).getParentItem().get(i).setExpanded(i == baseViewHolder.getPosition());
                    }
                    notifyDataSetChanged();
                });
            }
        };
//        MyLayoutManager layout = new MyLayoutManager();
//        layout.setAutoMeasureEnabled(true);//防止recyclerview高度为wrap时测量item高度0(一定要加这个属性，否则显示不出来）
//        rc_recycler.setLayoutManager(layout);
        rc_recycler.setLayoutManager(new GridLayoutManager(context, 3));
        // 添加间距装饰（包含边缘间距）
        rc_recycler.addItemDecoration(
                new GridSpacingItemDecoration(3, spacingInPx, true)
        );
        rc_recycler.setAdapter(fwqAdapter);
    }

}
