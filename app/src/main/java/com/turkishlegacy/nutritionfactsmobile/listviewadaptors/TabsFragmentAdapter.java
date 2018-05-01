package com.turkishlegacy.nutritionfactsmobile.listviewadaptors;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsFragmentAdapter extends FragmentStatePagerAdapter {
    //creating a list to hold all the fragments that will be added to tabs created in diary fragment

    List<Fragment> listFragments;

    public TabsFragmentAdapter(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listFragments = listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        //gets the position of each fragment in the list
        return this.listFragments.get(position);
    }

    @Override
    public int getCount() {
        //returns the count of the list size
        return this.listFragments.size();
    }
}
