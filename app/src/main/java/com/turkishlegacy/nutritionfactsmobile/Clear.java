package com.turkishlegacy.nutritionfactsmobile;

import android.content.SharedPreferences;
import android.widget.ListView;

import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsCustomListViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class Clear {

    Main main;
    public Clear() {
        main = new Main();


    }

    public void clearAll(ArrayList allFoodsList, ListView listViewLv, TabsCustomListViewAdaptor adaptor) {

        //clear arraylist
        allFoodsList.clear();
        //clear list view
        listViewLv.setAdapter(null);
        adaptor.notifyDataSetChanged();

        //clear fields
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
//        main.getIntent().removeExtra("Calories");
//        main.getIntent().removeExtra("Protein");
//        main.getIntent().removeExtra("Carb");
//        main.getIntent().removeExtra("Fat");
//
//        main.getIntent().removeExtra("Lunch Calories");
//        main.getIntent().removeExtra("Lunch Protein");
//        main.getIntent().removeExtra("Lunch Carb");
//        main.getIntent().removeExtra("Lunch Fat");
//
//        main.getIntent().removeExtra("Dinner Calories");
//        main.getIntent().removeExtra("Dinner Protein");
//        main.getIntent().removeExtra("Dinner Carb");
//        main.getIntent().removeExtra("Dinner Fat");

    }
}
