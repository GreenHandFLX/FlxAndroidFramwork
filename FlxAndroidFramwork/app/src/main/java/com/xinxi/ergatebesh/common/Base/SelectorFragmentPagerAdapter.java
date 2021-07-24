package com.xinxi.ergatebesh.common.Base;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/*
 *  @author   flx     2021.7.21
 * selectorFragmentPagerAdapter
 * */

public class SelectorFragmentPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;

    public SelectorFragmentPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentss) {
        super(fm);
        this.fragments = fragmentss;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
