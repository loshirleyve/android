package com.yun9.mobile.camera.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.yun9.mobile.camera.fragment.LocalAlbumFragment;
import com.yun9.mobile.camera.fragment.BaseFragment;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list)
    {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int i)
    {
        return list.get(i);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }
}
