package com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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


public class DinnerTab extends Fragment {


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

    int iCalories;
    int iProtein;
    int iCarb;
    int iFat;
    public DinnerTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dinner_tab, container, false);


        addButton = (Button) view.findViewById(R.id.addDinnerButton);
        clearButton = (Button) view.findViewById(R.id.clearDinnerButton);

        final Main main = (Main) getActivity();

        loadList();

        //initialise list view and adaptor
        listViewLv = (ListView) view.findViewById(R.id.dinnerListView);
        adaptor = new TabsCustomListViewAdaptor(getActivity(), allFoodsList);

        listViewLv.setAdapter(adaptor);

        listMeals();
        gotoSearchFragment();
        storeList();

        main.setDinnerFoodName("");
        main.setDinnerFoodQuantity("");
        main.setDinnerFoodCalories("");
        main.setDinnerFoodProtein("");
        main.setDinnerFoodFat("");
        main.setDinnerFoodCarb("");

        //clear list
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to clear all meals for Dinner?");

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
                sendNutrients();
                Toast.makeText(getActivity(), "Added to Nutrition Summary", Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
                main.setIsDinner(true);
                //opens search fragment
                SearchFragment searchFragment = new SearchFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //replacing the fragment inside the layout
                manager.beginTransaction().replace(R.id.content_layout, searchFragment).addToBackStack(null).commit();
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
            String name = main.getDinnerFoodName();
            String quantity = main.getDinnerFoodQuantity();
            String calorie = main.getDinnerFoodCalories();
            String protein = main.getDinnerFoodProtein();
            String carb = main.getDinnerFoodCarb();
            String fat = main.getDinnerFoodFat();

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
    private void storeList() {
        //initialise shared preferences
        sharedPreferences = getActivity().getSharedPreferences("dinner shared preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //initialise gson
        Gson gson = new Gson();
        //store the list in json and save it to shared prefs
        String json = gson.toJson(allFoodsList);
        editor.putString("dinner list", json);
        editor.apply();
    }

    //load the list data
    private void loadList() {
        sharedPreferences = getActivity().getSharedPreferences("dinner shared preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        //get the stored list
        String json = sharedPreferences.getString("dinner list", null);
        Type type = new TypeToken<ArrayList<AllFoodsinTabs>>() {
        }.getType();
        //convert the data and put inside the list
        allFoodsList = gson.fromJson(json, type);
        //if the list is empty initialise a new one
        if (allFoodsList == null) {

            allFoodsList = new ArrayList<>();

        }

    }

    //gathers data from every listview item and sends to calories fragment to output in total
    public void sendNutrients() {
        iCalories = 0;
        iProtein = 0;
        iCarb = 0;
        iFat = 0;

        String sCalories = "";
        String replacedCalories = "";

        String sProtein = "";
        String replacedProtein = "";

        String sCarb = "";
        String replacedCarb = "";

        String sFat = "";
        String replacedFat = "";

        //go through the list
        for (int i = 0; i < allFoodsList.size(); i++) {
            //get calories from every listview item of the calories textview
            View view1 = listViewLv.getChildAt(i);
            //initiate the text views
            TextView caloriesText = (TextView) view1.findViewById(R.id.tabsListviewCalorie);
            TextView proteinText = (TextView) view1.findViewById(R.id.tabsListviewProtein);
            TextView carbText = (TextView) view1.findViewById(R.id.tabsListviewCarb);
            TextView fatText = (TextView) view1.findViewById(R.id.tabsListviewFat);

            //get the text from the fields
            sCalories = caloriesText.getText().toString();
            sProtein = proteinText.getText().toString();
            sCarb = carbText.getText().toString();
            sFat = fatText.getText().toString();

            //only pull out the numbers
            replacedCalories = sCalories.substring(0, sCalories.length() - 9);
            replacedProtein = sProtein.substring(0, sProtein.length() - 17);
            replacedCarb = sCarb.substring(0, sCarb.length() - 15);
            replacedFat = sFat.substring(0, sFat.length() - 13);

            //add it to total for each nutrient
            iCalories = iCalories + Integer.parseInt(replacedCalories);
            iProtein = iProtein + Integer.parseInt(replacedProtein);
            iCarb = iCarb + Integer.parseInt(replacedCarb);
            iFat = iFat + Integer.parseInt(replacedFat);

            //send the value via bundle
            Bundle bundle = new Bundle();
            bundle.putInt("Dinner Calories", iCalories);
            bundle.putInt("Dinner Protein", iProtein);
            bundle.putInt("Dinner Carb", iCarb);
            bundle.putInt("Dinner Fat", iFat);

            Intent intent = getActivity().getIntent();
            intent.putExtras(bundle);

            Toast.makeText(getActivity(), "Added to Nutrition Summary", Toast.LENGTH_SHORT).show();
        }
    }
}
