package com.example.daadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPagerTABadapter extends FragmentPagerAdapter {

    public viewPagerTABadapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new onGoingTabFragment();
        } else if (position==1) {
            return new handledTabFragment();
        }else {
            return new cancledTabFragment();
        }
    }

    @Override
    public int getCount() {
        return 3; //no. of tabs
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "On Going";
        } else if (position==1) {
            return "Handled";
        }else {
            return "Cancelled";
        }
    }

}
