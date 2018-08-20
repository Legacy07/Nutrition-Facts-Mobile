package com.turkishlegacy.nutritionfactsmobile;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.BreakfastTab;
import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.DinnerTab;
import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.LunchTab;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsCustomListViewAdaptor;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsFragmentAdapter;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;

import java.util.ArrayList;
import java.util.List;


public class DiaryFragment extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    //helps to swipe left and right
    ViewPager viewPager;
    //creates tabs
    TabHost tabHost;
    protected View mView;

    ListView listView;
    Main main;

    TabsCustomListViewAdaptor adaptor;
    ArrayList<AllFoodsinTabs> allFoodsList;

    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

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

    public DiaryFragment() {

    }

    // Inflate the layout for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        this.mView = view;
        InitializeTabHost(savedInstanceState);
        InitializeViewPager();
        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Diary");

        main = (Main) getActivity();

//
//        listView = view.findViewById(R.id.listView);
//
//        allFoodsList = new ArrayList<>();

//        adaptor = new TabsCustomListViewAdaptor(getActivity(), allFoodsList);

        //showing the button in action bar
        setHasOptionsMenu(true);
        return view;
    }

    //inflating the menu on action bar within fragment
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.diary_menu, menu);
    }

    //action bar button options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //when add button is selected it adds the values from each list view item and sends to Calories fragment
            case R.id.diaryActionBarItem:

                //confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to clear all meals?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

//                        editor.clear();
//                        editor.commit();
//                        allFoodsList = new ArrayList<>();
//                        adaptor = new TabsCustomListViewAdaptor(getActivity(), allFoodsList);
//                        allFoodsList.clear();

                        main.setBreakfastFoodName("");
                        main.setBreakfastFoodQuantity("");
                        main.setBreakfastFoodCalories("");
                        main.setBreakfastFoodProtein("");
                        main.setBreakfastFoodFat("");
                        main.setBreakfastFoodCarb("");

                        main.setLunchFoodName("");
                        main.setLunchFoodQuantity("");
                        main.setLunchFoodCalories("");
                        main.setLunchFoodProtein("");
                        main.setLunchFoodFat("");
                        main.setLunchFoodCarb("");

                        main.setDinnerFoodName("");
                        main.setDinnerFoodQuantity("");
                        main.setDinnerFoodCalories("");
                        main.setDinnerFoodProtein("");
                        main.setDinnerFoodFat("");
                        main.setDinnerFoodCarb("");

                        //clear intent extras
                        getActivity().getIntent().removeExtra("Calories");
                        getActivity().getIntent().removeExtra("Protein");
                        getActivity().getIntent().removeExtra("Carb");
                        getActivity().getIntent().removeExtra("Fat");

                        getActivity().getIntent().removeExtra("Lunch Calories");
                        getActivity().getIntent().removeExtra("Lunch Protein");
                        getActivity().getIntent().removeExtra("Lunch Carb");
                        getActivity().getIntent().removeExtra("Lunch Fat");

                        getActivity().getIntent().removeExtra("Dinner Calories");
                        getActivity().getIntent().removeExtra("Dinner Protein");
                        getActivity().getIntent().removeExtra("Dinner Carb");
                        getActivity().getIntent().removeExtra("Dinner Fat");

                        getActivity().getIntent().removeExtra("list");
                        getActivity().getIntent().removeExtra("lunch list");
                        getActivity().getIntent().removeExtra("dinner list");

                        dialog.dismiss();

                        Toast.makeText(getActivity(), "Cleared!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void InitializeViewPager() {
        //initialising viewpager
        viewPager = (ViewPager) mView.findViewById(R.id.viewPager);
        //the list created to put fragments in a view pager
        List<Fragment> listFragments = new ArrayList<Fragment>();
        listFragments.add(new BreakfastTab());
        listFragments.add(new LunchTab());
        listFragments.add(new DinnerTab());
        //view pager applies from the adapter
        //getchildfragment manager allows fragments to not be blank after a back press.
        TabsFragmentAdapter tabsFragmentAdapter = new TabsFragmentAdapter(getChildFragmentManager(), listFragments);
        viewPager.setAdapter(tabsFragmentAdapter);
        viewPager.setOnPageChangeListener(DiaryFragment.this);


    }

    private void InitializeTabHost(Bundle args) {
        //tabhost setup
        tabHost = (TabHost) mView.findViewById(android.R.id.tabhost);
        tabHost.setup();
        //tab names
        String[] tabNames = {"Breakfast", "Lunch", "Dinner"};
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
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
    }

    @Override
    public void onTabChanged(String tabId) {
        //changes the tabs when its clicked on using scroll view
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);

        HorizontalScrollView hScrollView = (HorizontalScrollView) mView.findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

//        change colour of selected tab
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i)
                    .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
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


