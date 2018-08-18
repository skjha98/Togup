package com.sgsj.togup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class PagerViewAdapter extends FragmentPagerAdapter{
    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0: return new LoginFragment();

            case 1: return new SignUpFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
