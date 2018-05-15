package com.turkishlegacy.nutritionfactsmobile;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.CustomListViewAdaptor;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;
import com.turkishlegacy.nutritionfactsmobile.model.Foods;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

//    private ISearchFragment iSearchFragment;

    EditText searchTextVariable;
    ImageButton searchButtonVariable;
    DatabaseHandler db;
    NutritionSummary_Fragment nutritionSummary_fragment;
    //listview and arraylist declared
    ListView listViewLv;
    CustomListViewAdaptor adaptor;
    ArrayList<Foods> foodsArrayList = new ArrayList<>();

    AllFoodsinTabs allFoodsinTabs = new AllFoodsinTabs();

    public String name = "";
    public String calories = "";
    public String protein = "";
    public String carb = "";
    public String fat = "";
    public String quantity = "";
    Main main;

    public SearchFragment() {
    }

    //try this look at 6th solution -- https://stackoverflow.com/questions/5194548/how-to-pass-data-between-fragments
//    public interface ISearchFragment {
//
//        void setNameInterface(String sName);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        db = new DatabaseHandler(getActivity());

        searchTextVariable = (EditText) view.findViewById(R.id.searchTextBox);
        listViewLv = (ListView) view.findViewById(R.id.listView);
        adaptor = new CustomListViewAdaptor(getActivity(), foodsArrayList);

        listViewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    main = (Main) getActivity();

                    String getNameText = foodsArrayList.get(position).getName();
                    //gets the string from breakfastFoodName text box and by using the methods from database handler,
                    // it gets the values by breakfastFoodName and set it in textbox of Nutrition Summary fragment.
                    String setName = db.getName(getNameText);
//                    main.setSearchName(setBreakfastFoodName);

                    String setCalorie = db.getCalories(getNameText);
//                    main.setSearchCalories(setCalorie);

                    String setQuantity = db.getQuantity(getNameText);
//                    main.setSearchQuantity(setBreakfastFoodQuantity);

                    String setProtein = db.getProtein(getNameText);
//                    main.setSearchProtein(setBreakfastFoodProtein);

                    String setCarb = db.getCarb(getNameText);
//                    main.setSearchCarb(setBreakfastFoodCarb);

                    String setFat = db.getFat(getNameText);
//                    main.setSearchFat(setBreakfastFoodFat);

//                    iSearchFragment.setNameInterface(setBreakfastFoodName);

                    FoodNutritions_Fragment foodNutritions_fragment = new FoodNutritions_Fragment();
                    //pass the data via bundle to food nutritions fragment
                    Bundle bundle = new Bundle();

                    bundle.putString("foodName", setName);
                    bundle.putString("foodCalorie", setCalorie);
                    bundle.putString("foodQuantity", setQuantity);
                    bundle.putString("foodProtein", setProtein);
                    bundle.putString("foodCarb", setCarb);
                    bundle.putString("foodFat", setFat);

                    foodNutritions_fragment.setArguments(bundle);

                    //opens the nutrition summary Fragment
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    //replacing the fragment inside the layout
                    manager.beginTransaction().replace(R.id.content_layout, foodNutritions_fragment).commit();

                } catch (Exception exc) {
                    exc.printStackTrace();
                    showMessage("Error", exc.getMessage());
                }
            }
        });

        searchButtonVariable = (ImageButton) view.findViewById(R.id.searchButton);
        Search(view);

        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Foods");
        return view;
    }

    public void Search(View view) {
        searchButtonVariable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    String getNameText = searchTextVariable.getText().toString();
                    showMeals(getNameText);

                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Error", e.getMessage());
                }
            }
        });

    }

    //check if the interface is implemented
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof ISearchFragment) {
//            iSearchFragment = (ISearchFragment) context;
//        } else {
//            new RuntimeException(context.toString() + " ISearchFragment isn't implemented yet!");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        iSearchFragment = null;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    private void showMeals(String food) {
        foodsArrayList.clear();
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Foods f = null;
        Cursor c = db.getCursorName(food);
        try {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                //getting the values from the columns
                String name = c.getString(0);
                String quantity = c.getString(1);
                String calorie = c.getString(2);
                f = new Foods();
                f.setId(id);
                //once the values are gathered, its passed to setter methods.
                f.setName(name);
                f.setQuantity(quantity + " Grams");
                f.setCalorie(calorie + " Calories");
                //add it to array list to hold the information
                foodsArrayList.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error", e.getMessage());
        }
        listViewLv.setAdapter(adaptor);

    }

    @Override
    public void onPause() {
        super.onPause();

//        if (main.getIsBreakfast() == true) {
//            main.setIsBreakfast(false);
//        }
//        if (main.getIsLunch() == true) {
//            main.setIsLunch(false);
//        }
//        if (main.getIsDinner() == true) {
//            main.setIsDinner(false);
//        }
    }
}
