package io.openim.android.taohaoba.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.List;

import io.openim.android.taohaoba.bean.RecommendListBean;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment>mFragmentList;
    private List<RecommendListBean.GameDTO> gameList;

    public FragmentAdapter(FragmentManager fm, List<Fragment> mFragmentList, List<RecommendListBean.GameDTO> gameList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.gameList = gameList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return gameList.get(position).getName();
    }
}