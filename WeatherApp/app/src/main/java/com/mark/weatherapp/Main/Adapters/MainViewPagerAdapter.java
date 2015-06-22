package com.mark.weatherapp.Main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mark.weatherapp.Main.GUI.MainActivity;
import com.mark.weatherapp.Main.GUI.SetViewValues;
import com.mark.weatherapp.Main.System.Date;

import java.util.List;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private FragmentManager mFragmentManager;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
    }

    public List<Fragment> getmFragments() {
        return mFragments;
    }

    public void setmFragments(List<Fragment> mFragments) {
        this.mFragments = mFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        if(mFragmentManager.getFragments().contains(object))
            return POSITION_NONE;
        else
            return POSITION_UNCHANGED;
        //return MainViewPagerAdapter.POSITION_NONE;

    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        List<Date> dates = MainActivity.sObj.getDates();

        return SetViewValues.makeTitle(dates.get(position).getDate());

    }
}
