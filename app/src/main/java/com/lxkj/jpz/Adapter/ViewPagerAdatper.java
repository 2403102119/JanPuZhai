package com.lxkj.jpz.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdatper extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> fragmentsList;

	/**
	 * 通过构造方法把ArrayList<Fragment>传过来
	 * @param fm
	 * @param fragments
	 */
	public ViewPagerAdatper(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragmentsList = fragments;
    }

	@Override
	public Fragment getItem(int arg0) {
//		之前的getItem 返回的是 Object
//		这里返回传过来的Fragment 然后通过adapter把这个Fragment放到viewpager里
		return fragmentsList.get(arg0);
	}

	@Override
	public int getCount() {
//		跟之前adapter一样 获取数据的数量 告诉adapter添加几个item
		return fragmentsList.size();
	}

}
