package com.yj.navigation.adapter;

/**
 * Created by foxcen on 16/7/29.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yj.navigation.fragment.AllFragment_;
import com.yj.navigation.fragment.VideoFirstFragment;
import com.yj.navigation.fragment.VideoFirstFragment_;
import com.yj.navigation.fragment.VideoSecondFragment;
import com.yj.navigation.fragment.VideoSecondFragment_;

/**
 * 自定义fragment适配器
 *
 * @author ZHF
 */
public class MyVideoFragmentPageAdapter extends FragmentPagerAdapter {
    public MyVideoFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VideoFirstFragment_();
            case 1:
                return new VideoSecondFragment_();

            default:
                return null;
        }
    }
}

