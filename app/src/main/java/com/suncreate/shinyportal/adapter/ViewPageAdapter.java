package com.suncreate.shinyportal.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zzx on 2018/04/23/下午 4:33
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<Fragment> mListData;
    private String[] title;

    public ViewPageAdapter(FragmentManager fm, Context mContext, List<Fragment> mListData) {
        super(fm);
        this.mContext = mContext;
        this.mListData = mListData;
    }

    public ViewPageAdapter(FragmentManager fm, Context mContext, List<Fragment> mListData, String[] title) {
        super(fm);
        this.mContext = mContext;
        this.mListData = mListData;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title == null || title.length == 0 ? super.getPageTitle(position) : title[position];
    }
}
