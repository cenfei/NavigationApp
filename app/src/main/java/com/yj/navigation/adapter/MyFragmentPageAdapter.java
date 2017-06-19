package com.yj.navigation.adapter;

/**
 * Created by foxcen on 16/7/29.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yj.navigation.fragment.AllFragment_;
import com.yj.navigation.fragment.WorkNowFragment_;
import com.yj.navigation.fragment.WorkSuccessFragment_;

/**
 * 自定义fragment适配器
 *
 * @author ZHF
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFragment_();
            case 1:
                return new WorkNowFragment_();
            case 2:
                return new WorkSuccessFragment_();
            default:
                return null;
        }
    }
}

