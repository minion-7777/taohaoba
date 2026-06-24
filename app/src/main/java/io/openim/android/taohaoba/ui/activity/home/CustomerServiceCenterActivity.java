package io.openim.android.taohaoba.ui.activity.home;

import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuikit.tuichat.classicui.page.TUIC2CChatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserver;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.utils.Constants;
import io.openim.android.taohaoba.bean.GameListBean;
import io.openim.android.taohaoba.bean.UserWithdrawalInfoBean;
import io.openim.android.taohaoba.databinding.ActivityCustomerServiceCenterBinding;
import io.openim.android.taohaoba.repository.OpenIMService;
import io.openim.android.taohaoba.ui.activity.game.AccountNumberListActivity;
import io.openim.android.taohaoba.ui.activity.game.PublishProductOneActivity;
import io.openim.android.taohaoba.ui.adapter.GameListAdapter;
import io.openim.android.taohaoba.ui.adapter.GameTypeAdapter;
import io.openim.android.taohaoba.ui.login.LoginThbActivity;
import io.openim.android.taohaoba.vm.game.GameVM;
import io.openim.android.taohaoba.widgets.CustomLetterNavigationView;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 客服中心(公共类)
 */
public class CustomerServiceCenterActivity extends BaseActivity<GameVM, ActivityCustomerServiceCenterBinding> implements GameVM.ViewAction {
private static final String TAG = "CustomerServiceCenterActivity";
    private GameListAdapter adapter;
    private GameTypeAdapter gameTypeAdapter;
    private GridLayoutManager layoutManager;
    private int gameTypeId = 0;//游戏id 0=所有
    private int pageIndex; //pageIndex == 1客服 2账号选购 3账号回收 4账号估值 5自由交易（卖账号） 6搜索 7中介担保
    private int patternId;//交易模式id
    private int categoryId;//商品类型id
    private int seller_service_ratio;//卖家费率
    private double seller_service_price;//卖家最低费率
    private Context context;
    private int is_order_info;
    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        bindVM(GameVM.class);
        bindViewDataBinding(ActivityCustomerServiceCenterBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);
        initView();
        initListener();
    }

    protected void initView() {

        pageIndex = getIntent().getIntExtra("pageIndex", 0);
        patternId = getIntent().getIntExtra("patternId", 0);
        categoryId = getIntent().getIntExtra("categoryId", 0);
        seller_service_ratio = getIntent().getIntExtra("seller_service_ratio", 0);
        seller_service_price = getIntent().getDoubleExtra("seller_service_price", 0);
        String title = getIntent().getStringExtra("title");
        switch (pageIndex) {
            case 1:
                view.toolbar.setTitleText("客服");
                view.tvHint.setVisibility(VISIBLE);
                break;
            case 2:
                view.toolbar.setTitleText("账号选购");
                break;
            case 3:
                view.toolbar.setTitleText("账号回收");
                break;
            case 4:
                view.toolbar.setTitleText("账号估值");
                break;
            case 5:
                if (!TextUtils.isEmpty(title)) {
                    view.toolbar.setTitleText(title);
                }
                break;
            case 6:
                view.toolbar.setTitleText("搜索");
                break;
            case 7:
                view.toolbar.setTitleText("中介担保");
                break;
            // 可以继续添加其他 pageIndex 值的处理逻辑
            default:
                // 默认处理
                break;
        }

        initViewRecycler();

        //游戏列表
        vm.getGameListBeanMutableLiveData().observe(this, it->{
            dismissLoadingDialog();
            initTabRecycler(it.getGame_type());
            allGameDTOList.addAll(it.getGame());
            gameDTOList.addAll(allGameDTOList);
            adapter.notifyDataSetChanged();
        });

        showLoadingDialog();
        vm.getGameList("");

    }

    protected void initListener() {

        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        //字母索引
        view.includedGameList.tvLetterNavigationView.setOnLetterSelectedListener(new CustomLetterNavigationView.OnLetterSelectedListener() {
            @Override
            public void onLetterSelected(String letter) {
                // 根据字母滚动到对应位置
                int position = adapter.getPositionByLetter(letter);
                if (position != -1) {
                    layoutManager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        //搜索
        view.includedGameList.tvSearch.setOnClickListener(v->{
            // 获取所有匹配项
            gameDTOList.clear();
            gameDTOList.addAll(allGameDTOList.stream()
                    .filter(dto ->
                            dto.getName() != null &&
                                    dto.getName().toLowerCase().contains(view.includedGameList.etSearch.getText().toString().toLowerCase()) && (gameTypeId == 0 || gameTypeId==dto.getGame_type_id())
                    )
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });

        //分配客服
        vm.getAssignCustomerServiceMutableLiveData().observe(getViewDataBinding().getLifecycleOwner(), it->{
            dismissLoadingDialog();
            Intent intent = new Intent(getBaseContext(), TUIC2CChatActivity.class);
            intent.putExtra(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
            intent.putExtra(TUIConstants.TUIChat.CHAT_ID, it);
            if (is_order_info == 5) {
                intent.putExtra(Constants.SENDMSG, "我想咨询"+gameName+"游戏");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

    }

    //持有所有的游戏列表数据
    private List<GameListBean.GameDTO> allGameDTOList = new ArrayList<>();
    private List<GameListBean.GameTypeDTO> gameTypeDTOList = new ArrayList<>();
    private void initTabRecycler(List<GameListBean.GameTypeDTO> list){
        GameListBean.GameTypeDTO bean = new GameListBean.GameTypeDTO();
        bean.setId(0);
        bean.setName("全部");
        bean.setCheck(true);
        gameTypeDTOList.add(bean);
        gameTypeDTOList.addAll(list);
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
        view.includedGameList.rcGameType.setLayoutManager(flexboxLayoutManager);
        // 创建适配器
        gameTypeAdapter = new GameTypeAdapter(this, gameTypeDTOList);
        // 设置适配器
        view.includedGameList.rcGameType.setAdapter(gameTypeAdapter);

        //点击事件
        gameTypeAdapter.setOnVerificationListener((it, position)->{
            gameTypeId = it.getId();
            for (int i = 0; i < gameTypeDTOList.size(); i++) {
                gameTypeDTOList.get(i).setCheck(i == position);
            }
            gameDTOList.clear();
            gameDTOList.addAll(allGameDTOList.stream()
                    .filter(dto ->
                            dto.getName() != null && (gameTypeId == 0 || gameTypeId==dto.getGame_type_id())
                    )
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
            gameTypeAdapter.notifyDataSetChanged();
        });

    }

    private List<GameListBean.GameDTO> gameDTOList = new ArrayList<>();
    private void initViewRecycler(){
        layoutManager = new GridLayoutManager(this,5, RecyclerView.VERTICAL,false);
        view.includedGameList.rcRecycler.setLayoutManager(layoutManager);
        // 创建适配器
        adapter = new GameListAdapter(gameDTOList);
        // 设置适配器
        view.includedGameList.rcRecycler.setAdapter(adapter);

        view.includedGameList.rcRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisiblePosition != RecyclerView.NO_POSITION) {
                    String firstLetter = adapter.getData().get(firstVisiblePosition).getPinyin();
                    view.includedGameList.tvLetterNavigationView.highlightLetter(firstLetter);
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                if (pageIndex == 5){
                    //自由交易（卖账号）
                    if (isLogin(LoginThbActivity.class)) {
                        startActivity(new Intent(getBaseContext(), PublishProductOneActivity.class)
                                .putExtra("categoryId", categoryId)
                                .putExtra("patternId", patternId)
                                .putExtra("gameId", gameDTOList.get(position).getId())
                                .putExtra("gameName", gameDTOList.get(position).getName())
                                .putExtra("seller_service_ratio", seller_service_ratio)
                                .putExtra("seller_service_price", seller_service_price)
                                .putExtra("operateType", 1));
                    }
                }else if (pageIndex == 6 || pageIndex == 2){
                    //搜索
                    startActivity(new Intent(getBaseContext(), AccountNumberListActivity.class)
                            .putExtra("gameId", gameDTOList.get(position).getId())
                            .putExtra("gameTypeId", gameDTOList.get(position).getGame_type_id())
                            .putExtra("gameName", gameDTOList.get(position).getName()));

                }else if (pageIndex == 3){
                    //快速回收（卖账号）
                    if (isLogin(LoginThbActivity.class)) {
                        startActivity(new Intent(getBaseContext(), RapidRecoveryActivity.class)
                                .putExtra("categoryId", categoryId)
                                .putExtra("patternId", patternId)
                                .putExtra("gameId", gameDTOList.get(position).getId())
                                .putExtra("gameName", gameDTOList.get(position).getName()));
                    }

                }else if (pageIndex == 1) {
                    //1客服
                    if (isLogin(LoginThbActivity.class)) {
                        is_order_info = 2;
                        gameName = gameDTOList.get(position).getName();
                        showLoadingDialog();
                        vm.assignCustomerService(is_order_info, gameDTOList.get(position).getId());
                    }
                }else if (pageIndex == 7) {
                    //中介担保
                    if (isLogin(LoginThbActivity.class)) {
                        is_order_info = 4;
                        showLoadingDialog();
                        vm.assignCustomerService(is_order_info, gameDTOList.get(position).getId());
                    }
                }else if (pageIndex == 4) {
                    //账号估值
                    if (isLogin(LoginThbActivity.class)) {
                        is_order_info = 5;
                        gameName = gameDTOList.get(position).getName();
                        showLoadingDialog();
                        vm.assignCustomerService(is_order_info, gameDTOList.get(position).getId());
                    }
                }
            }
        });
    }

    @Override
    public void err(String msg) {
        dismissLoadingDialog();
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }
}
