package io.openim.android.taohaoba.ui.activity.home;

import static io.openim.android.taohaoba.utils.AnimatorUtil.shakeAnimation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.openim.android.ouicore.base.BaseActivity;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.databinding.ActivitySearchBinding;
import io.openim.android.taohaoba.utils.SearchHistoryDbHelper;
import io.openim.android.taohaoba.vm.home.SearchVM;
import io.openim.android.taohaoba.widgets.MyLayoutManager;
import io.openim.android.taohaoba.widgets.TitleToolbar;

/**
 * 首页-搜索页
 */
public class SearchActivity extends BaseActivity<SearchVM, ActivitySearchBinding> implements SearchVM.ViewAction{

    private List<String> list = new ArrayList<>();
    private BaseQuickAdapter adapter;
    private SearchHistoryDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindVM(SearchVM.class);
        bindViewDataBinding(ActivitySearchBinding.inflate(getLayoutInflater()));
        adjustToolbarForStatusBar(view.toolbar);

        // 在Activity或Fragment中使用dbHelper对象。确保在适当的地方初始化（比如onCreate方法中）。记得在不再需要时关闭数据库连接或在onDestroy中调用`dbHelper
        dbHelper = SearchHistoryDbHelper.getInstance(this);

        initAdapter();
        initView();
        initListener();
    }


    private void initView() {

    }

    private void initListener() {
        view.toolbar.setOnVerificationListener(new TitleToolbar.OnVerificationListener() {
            @Override
            public void onClose() {
                finish();
            }
        });

        view.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 1. 隐藏键盘
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // 2. 执行搜索逻辑
                String query = view.etSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    dbHelper.insertRecord(view.etSearch.getText().toString().trim());
                    startActivity(new Intent(this, SearchResultActivity.class)
                            .putExtra("searchResult", view.etSearch.getText().toString().trim()));
                } else {
                    shakeAnimation(view.etSearch);
                    showToast("请输入搜索内容");
                }
                return true; // 消费事件
            }
            return false;
        });

        view.tvSearch.setOnClickListener(v->{
            if (TextUtils.isEmpty(view.etSearch.getText().toString().trim())) {
                shakeAnimation(view.etSearch);
                showToast("请输入搜索内容");
                return;
            }
            dbHelper.insertRecord(view.etSearch.getText().toString().trim());
            startActivity(new Intent(this, SearchResultActivity.class)
                    .putExtra("searchResult", view.etSearch.getText().toString().trim()));
        });
    }

    private void initAdapter() {
        if (dbHelper.getRecentRecords() != null) {
            list.addAll(dbHelper.getRecentRecords());
        }
        adapter = new BaseQuickAdapter(R.layout.item_search, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                baseViewHolder.setText(R.id.tv_name, o.toString());

                baseViewHolder.itemView.setOnClickListener(v->{
                    startActivity(new Intent(getContext(), SearchResultActivity.class)
                            .putExtra("searchResult", o.toString()));
                });
            }
        };
        MyLayoutManager layout = new MyLayoutManager();
        layout.setAutoMeasureEnabled(true);//防止recyclerview高度为wrap时测量item高度0(一定要加这个属性，否则显示不出来）
        view.rcRecycler.setLayoutManager(layout);
        view.rcRecycler.setAdapter(adapter);
    }


    @Override
    public void err(String msg) {
        showToast(msg);
    }

}
