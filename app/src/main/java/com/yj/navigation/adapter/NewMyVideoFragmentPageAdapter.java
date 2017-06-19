package com.yj.navigation.adapter;

/**
 * Created by foxcen on 16/7/29.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yj.navigation.fragment.AllDeviceFragment_;
import com.yj.navigation.fragment.UpdateDeviceFragment_;

/**
 * 自定义fragment适配器
 *
 * @author ZHF
 */
public class NewMyVideoFragmentPageAdapter extends FragmentPagerAdapter {
    public NewMyVideoFragmentPageAdapter(FragmentManager fm) {
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
                return new AllDeviceFragment_();
            case 1:
                return new UpdateDeviceFragment_();

            default:
                return null;
        }
    }
}

