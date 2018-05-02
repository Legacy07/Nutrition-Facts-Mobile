package com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.turkishlegacy.nutritionfactsmobile.Main;
import com.turkishlegacy.nutritionfactsmobile.R;
import com.turkishlegacy.nutritionfactsmobile.SearchFragment;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsCustomListViewAdaptor;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;

import java.util.ArrayList;

public class BreakfastTab extends Fragment {

    Button addbuttonVariable;
    //listview and arraylist declared
    ListView listViewLv;
    TabsCustomListViewAdaptor adaptor;
    ArrayList<AllFoodsinTabs> allFoodsList = new ArrayList<>();

    public BreakfastTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breakfast_tab, container, false);

        addbuttonVariable = (Button) view.findViewById(R.id.addButton);
        AddBreakfast();

        listViewLv = (ListView) view.findViewById(R.id.listView);
        adaptor = new TabsCustomListViewAdaptor(getActivity(), allFoodsList);
        listViewLv.setAdapter(adaptor);

        Main main = (Main) getActivity();
        String getname = main.getName();
        getAllFood();
        //actionbar title change
        return view;
    }

    public void AddBreakfast() {
        //event listener for add button
        addbuttonVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //opens search fragment
                SearchFragment searchFragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //replacing the fragment inside the layout
                manager.beginTransaction().replace(R.id.layout_Fragment, searchFragment).commit();
            }

        });

    }

    //Look at this -- https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
    //http://www.mkyong.com/android/android-listview-example/
    private void getAllFood() {
        allFoodsList.clear();
        Main main = (Main) getActivity();

        AllFoodsinTabs f = null;
        try {

            //getting the values from the get methods in main which is saved from search fragment
            // when the item was searched and selected
            String name = main.getName();
            String quantity = main.getQuantity();
            String calorie = main.getCalories();
            String protein = main.getProtein();
            String carb = main.getCarb();
            String fat = main.getFat();

            f = new AllFoodsinTabs();
            if (name.equals("")) {
            } else {
                int index = 0;

                //once the values are gathered, its passed to setter methods.
                f.setName(name);
                f.setQuantity(quantity + " Grams");
                f.setCalorie(calorie + " Calories");
                f.setProtein(protein + " grams of Protein");
                f.setCarb(carb + " grams of Carbs");
                f.setFat(fat + " grams of Fat");
                //add it to array list to hold the information
                allFoodsList.add(f);
                adaptor.notifyDataSetChanged();
            }


        } catch (Exception e)

        {
            e.printStackTrace();
            showMessage("Error", e.getMessage());
        }
        listViewLv.setAdapter(adaptor);
    }

    @Override
    public void onResume() {
        super.onResume();
        //OnResume Fragment
    }

   /* private void getAllFood(String food) {
        allFoodsList.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());

        AllFoodsinTabs f = null;
        Cursor c = db.getCursorName(food);
        try {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                //getting the values from the columns
                String name = c.getString(0);
                String quantity = c.getString(1);
                String calorie = c.getString(2);
                String protein = c.getString(3);
                String carb = c.getString(4);
                String fat = c.getString(5);

                f = new AllFoodsinTabs();
                f.setId(id);
                //once the values are gathered, its passed to setter methods.
                f.setName(name);
                f.setQuantity(quantity + " Grams");
                f.setCalorie(calorie + " Calories");
                f.setProtein(protein);f.setCarb(carb);f.setFat(fat);
                //add it to array list to hold the information
                allFoodsList.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error", e.getMessage());
        }
        listViewLv.setAdapter(adaptor);

    }*/

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}