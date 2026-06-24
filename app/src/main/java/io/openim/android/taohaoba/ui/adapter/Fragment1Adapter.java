package io.openim.android.taohaoba.ui.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class Fragment1Adapter extends FragmentPagerAdapter {
    private List<Fragment>mFragmentList;
    private String[] gameList;

    public Fragment1Adapter(FragmentManager fm, List<Fragment> mFragmentList, String[] gameList) {
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
        return gameList[position];
    }
}