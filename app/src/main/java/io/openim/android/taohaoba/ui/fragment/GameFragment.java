package io.openim.android.taohaoba.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.bean.GameListBean;
import io.openim.android.taohaoba.databinding.FragmentGameBinding;
import io.openim.android.taohaoba.ui.activity.game.AccountNumberListActivity;
import io.openim.android.taohaoba.ui.adapter.GameListAdapter;
import io.openim.android.taohaoba.ui.adapter.GameTypeAdapter;
import io.openim.android.taohaoba.vm.game.GameVM;
import io.openim.android.taohaoba.vm.home.HomeVM;
import io.openim.android.taohaoba.widgets.CustomLetterNavigationView;

public class GameFragment extends BaseFragment<GameVM> implements GameVM.ViewAction{

    private GameListAdapter adapter;
    private GameTypeAdapter gameTypeAdapter;
    private GridLayoutManager layoutManager;
    private int gameTypeId = 0;//游戏id 0=所有
    private FragmentGameBinding binding;

    public static GameFragment newInstance() {
        Bundle args = new Bundle();
        GameFragment fragment = new GameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(GameVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(getLayoutInflater());
        initView();
        return binding.getRoot();
    }

    private void initView(){
        adjustToolbarForStatusBar(binding.rlTitle);
        initData();
        initViewRecycler();
        initSubscribe();
        //字母索引
        binding.includedGameList.tvLetterNavigationView.setOnLetterSelectedListener(new CustomLetterNavigationView.OnLetterSelectedListener() {
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
        binding.includedGameList.tvSearch.setOnClickListener(v->{
            // 获取所有匹配项
            gameDTOList.clear();
            gameDTOList.addAll(allGameDTOList.stream()
                    .filter(dto ->
                            dto.getName() != null &&
                                    dto.getName().toLowerCase().contains(binding.includedGameList.etSearch.getText().toString().toLowerCase()) && (gameTypeId == 0 || gameTypeId==dto.getGame_type_id())
                    )
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });
    }

    //持有所有的游戏列表数据
    private List<GameListBean.GameDTO> allGameDTOList = new ArrayList<>();

    protected void initSubscribe() {

        //游戏列表
        vm.getGameListBeanMutableLiveData().observe(getViewLifecycleOwner(), it->{
            initTabRecycler(it.getGame_type());
            allGameDTOList.addAll(it.getGame());
            gameDTOList.addAll(allGameDTOList);
            adapter.notifyDataSetChanged();
        });
    }

    protected void initData() {
        vm.getGameList("");
    }

    private List<GameListBean.GameTypeDTO> gameTypeDTOList = new ArrayList<>();
    private void initTabRecycler(List<GameListBean.GameTypeDTO> list){
        GameListBean.GameTypeDTO bean = new GameListBean.GameTypeDTO();
        bean.setId(0);
        bean.setName("全部");
        bean.setCheck(true);
        gameTypeDTOList.add(bean);
        gameTypeDTOList.addAll(list);
        //设置布局管理器
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);//主轴为水平方向，起点在左端。
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);//按正常方向换行
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);//交叉轴的起点对齐。
        binding.includedGameList.rcGameType.setLayoutManager(flexboxLayoutManager);
        // 创建适配器
        gameTypeAdapter = new GameTypeAdapter(getContext(), gameTypeDTOList);
        // 设置适配器
        binding.includedGameList.rcGameType.setAdapter(gameTypeAdapter);

        //点击事件
        gameTypeAdapter.setOnVerificationListener((it, position)->{
            gameTypeId = it.getId();
            for (int i = 0; i < gameTypeDTOList.size(); i++) {
                gameTypeDTOList.get(i).setCheck(i == position);
            }
            gameTypeAdapter.notifyDataSetChanged();

            gameDTOList.clear();
            gameDTOList.addAll(allGameDTOList.stream()
                    .filter(dto ->
                            dto.getName() != null && (gameTypeId == 0 || gameTypeId==dto.getGame_type_id())
                    )
                    .collect(Collectors.toList()));
            adapter.notifyDataSetChanged();
        });

    }

    private List<GameListBean.GameDTO> gameDTOList = new ArrayList<>();
    private void initViewRecycler(){
        layoutManager = new GridLayoutManager(getContext(),5, RecyclerView.VERTICAL,false);
        binding.includedGameList.rcRecycler.setLayoutManager(layoutManager);
        // 创建适配器
        adapter = new GameListAdapter(gameDTOList);
        // 设置适配器
        binding.includedGameList.rcRecycler.setAdapter(adapter);

        binding.includedGameList.rcRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                if (firstVisiblePosition != RecyclerView.NO_POSITION) {
                    String firstLetter = adapter.getData().get(firstVisiblePosition).getPinyin();
                    binding.includedGameList.tvLetterNavigationView.highlightLetter(firstLetter);
                }
            }
        });

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(getContext(), AccountNumberListActivity.class)
                        .putExtra("gameId", gameDTOList.get(position).getId())
                        .putExtra("gameTypeId", gameDTOList.get(position).getGame_type_id())
                        .putExtra("gameName", gameDTOList.get(position).getName()));
            }
        });
    }

    @Override
    public void err(String msg) {

    }

    // 在目标 Fragment 中重写 onHiddenChanged()
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 相当于 onResume() 的逻辑
            if (allGameDTOList.size() == 0) {
                initData();
            }
        }
    }
}
