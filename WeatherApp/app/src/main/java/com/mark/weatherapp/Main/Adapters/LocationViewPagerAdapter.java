package com.mark.weatherapp.Main.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mark.weatherapp.Main.GUI.MainActivity;
import com.mark.weatherapp.Main.GUI.SetViewValues;
import com.mark.weatherapp.Main.System.Date;

import java.util.List;

public class LocationViewPagerAdapter extends FragmentStatePagerAdapter {
    public LocationViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        /**switch (i) {
            case 0:
                fragment = new DayOneFragment();
                break;
            case 1:
                fragment = new DayTwoFragment();
                break;
            case 2:
                fragment = new DayThreeFragment();
                break;
            case 3:
                fragment = new DayFourFragment();
                break;
        }**/
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        List<Date> dates = MainActivity.sObj.getDates();

        return SetViewValues.makeTitle(dates.get(position).getDate());

    }
}
