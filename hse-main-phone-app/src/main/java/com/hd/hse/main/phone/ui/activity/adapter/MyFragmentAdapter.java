package com.hd.hse.main.phone.ui.activity.adapter;


import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by dubojian on 2017/7/19.
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> pagerlist;
    public MyFragmentAdapter(FragmentManager fm, ArrayList<Fragment> pagerlist) {
        super(fm);
        this.pagerlist=pagerlist;
    }

    @Override
    public Fragment getItem(int position) {
        return pagerlist.get(position);
    }

    @Override
    public int getCount() {
        return pagerlist.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

    }
}
