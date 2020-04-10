package com.lxkj.jpz.Utils;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;

    public TabPagerAdapter(FragmentManager fragmentManager, String[] tabTitles, List<Fragment> fragmentList) {
        super(fragmentManager);
        this.tabTitles = tabTitles;
        this.fragmentManager = fragmentManager;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
