package com.turkishlegacy.nutritionfactsmobile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsFragmentAdapter;
import com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs.CaloriesFragmentTab;
import com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs.ChartsFragmentTab;

import java.util.ArrayList;
import java.util.List;


public class NutritionSummary_Fragment extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    //helps to swipe left and right
    ViewPager viewPager;
    //creates tabs
    TabHost tabHost;
    protected View mView;

    // fake content for tabhost to ensure real content which the view pager will be shown
    private class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    public NutritionSummary_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrition_summary_, container, false);

        //gets view globally
        this.mView = view;
        //initialie tabs
        InitializeTabHost(savedInstanceState);
        //initialize viewpager
        InitializeViewPager();
        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nutrition Summary");

        return view;
    }


    private void InitializeViewPager() {

        //initialising viewpager
        viewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        //the list created to put fragments in a view pager
        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add(new CaloriesFragmentTab());
        listFragments.add(new ChartsFragmentTab());
        //view pager applies from the adapter
        //getchildfragment manager allows fragments to not be blank after a back press.
        TabsFragmentAdapter tabsFragmentAdapter = new TabsFragmentAdapter(getChildFragmentManager(), listFragments);
        viewPager.setAdapter(tabsFragmentAdapter);

        viewPager.setOnPageChangeListener(NutritionSummary_Fragment.this);

    }

    private void InitializeTabHost(Bundle args) {
        //tabhost setup
        tabHost = (TabHost) mView.findViewById(android.R.id.tabhost);
        tabHost.setup();
        //tab names
        String[] tabNames = {"Macros", "Charts"};
        //add the tab names
        for (int i = 0; i < tabNames.length; i++) {

            TabHost.TabSpec tabSpec;
            try {
                tabSpec = tabHost.newTabSpec(tabNames[i]);
                tabSpec.setIndicator(tabNames[i]);
                tabSpec.setContent(new FakeContent(getContext()));
                tabHost.addTab(tabSpec);
            } catch
                    (Exception e) {
                e.printStackTrace();
                showMessage("Error", e.getMessage());
            }

        }
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onTabChanged(String tabId) {
        //changes the tabs when its clicked on using scroll view
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
        //scroll
        HorizontalScrollView hScrollView = (HorizontalScrollView) mView.findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    //alert dialog message box
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}
