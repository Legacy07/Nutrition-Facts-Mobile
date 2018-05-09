package com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.turkishlegacy.nutritionfactsmobile.Main;
import com.turkishlegacy.nutritionfactsmobile.R;
import com.turkishlegacy.nutritionfactsmobile.SearchFragment;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.TabsCustomListViewAdaptor;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class LunchTab extends Fragment {

    Button addButton;
    //listview and arraylist declared
    ListView listViewLv;
    //arraylist and adaptor
    TabsCustomListViewAdaptor adaptor;
    ArrayList<AllFoodsinTabs> allFoodsList;

    Main main;

    AllFoodsinTabs allFoodsinTabs;
    Button clearButton;
    //shared prefs
    SharedPreferences sharedPreferences = null;
    SharedPreferences.Editor editor = null;

    public LunchTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lunch_tab, container, false);

        addButton = (Button) view.findViewById(R.id.addLunchButton);
        clearButton = (Button) view.findViewById(R.id.clearLunchButton);

        final Main main = (Main) getActivity();

        loadList();

        //initialise list view and adaptor
        listViewLv = (ListView) view.findViewById(R.id.lunchListView);
        adaptor = new TabsCustomListViewAdaptor(getActivity(), allFoodsList);

        listViewLv.setAdapter(adaptor);

        listMeals();
        gotoSearchFragment();
        storeList();

        main.setLunchFoodName("");
        main.setLunchFoodQuantity("");
        main.setLunchFoodCalories("");
        main.setLunchFoodProtein("");
        main.setLunchFoodFat("");
        main.setLunchFoodCarb("");

        //clear list
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to clear all meals for Lunch?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //clear shared prefs
                        editor.clear();
                        editor.commit();

                        //clear arraylist
                        allFoodsList.clear();
                        //clear list view
                        listViewLv.setAdapter(null);
                        adaptor.notifyDataSetChanged();
                        //clear fields
                        main.setLunchFoodName("");
                        main.setLunchFoodQuantity("");
                        main.setLunchFoodCalories("");
                        main.setLunchFoodProtein("");
                        main.setLunchFoodFat("");
                        main.setLunchFoodCarb("");

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

            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void gotoSearchFragment() {
        //event listener for add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main.setIsLunch(true);
                //opens search fragment
                SearchFragment searchFragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //replacing the fragment inside the layout
                manager.beginTransaction().replace(R.id.layout_Fragment, searchFragment).commit();
            }

        });

    }

    private void listMeals() {
//        allFoodsList.clear();
        allFoodsinTabs = new AllFoodsinTabs();
        main = (Main) getActivity();

        try {

            //getting the values from the get methods in main which is saved from search fragment
            // when the item was searched and selected
            String name = main.getLunchFoodName();
            String quantity = main.getLunchFoodQuantity();
            String calorie = main.getLunchFoodCalories();
            String protein = main.getLunchFoodProtein();
            String carb = main.getLunchFoodCarb();
            String fat = main.getLunchFoodFat();

            if (name.equals("")) {
            } else {

                //once the values are gathered, its passed to setter methods.
                allFoodsinTabs.setName(name + "");
                allFoodsinTabs.setQuantity(quantity + " Grams");
                allFoodsinTabs.setCalorie(calorie + " Calories");
                allFoodsinTabs.setProtein(protein + " grams of Protein");
                allFoodsinTabs.setCarb(carb + " grams of Carbs");
                allFoodsinTabs.setFat(fat + " grams of Fat");

                //add it to array list to hold the information
                allFoodsList.add(allFoodsinTabs);
                adaptor.notifyDataSetChanged();
                listViewLv.setAdapter(adaptor);
            }

        } catch (Exception e)

        {
            e.printStackTrace();
            showMessage("Error", e.getMessage());
        }

    }
    //store the list in shared prefs
    private void storeList(){
        //initialise shared preferences
        sharedPreferences = getActivity().getSharedPreferences("lunch shared preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //initialise gson
        Gson gson = new Gson();
        //store the list in json and save it to shared prefs
        String json = gson.toJson(allFoodsList);
        editor.putString("lunch list", json);
        editor.apply();
    }

    //load the list data
    private void loadList() {
        sharedPreferences = getActivity().getSharedPreferences("lunch shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        //get the stored list
        String json = sharedPreferences.getString("lunch list", null);
        Type type = new TypeToken<ArrayList<AllFoodsinTabs>>() {
        }.getType();
        //convert the data and put inside the list
        allFoodsList = gson.fromJson(json, type);
        //if the list is empty initialise a new one
        if (allFoodsList == null) {

            allFoodsList = new ArrayList<>();

        }

    }
}
