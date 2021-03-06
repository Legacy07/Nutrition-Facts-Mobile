package com.turkishlegacy.nutritionfactsmobile;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;


public class FoodNutritions_Fragment extends Fragment {

    DatabaseHandler db;
    SearchFragment searchFragment;

    EditText nameTextBoxVariable, caloriesTextBoxVariable,
            quantityTextBoxVariable, proteinTextBoxVariable,
            carbTextBoxVariable, fatTextBoxVariable, gramsEdittextDialog;
    ImageButton imageUpdateButton;
    TextView foodNameTextViewDialog, calorieBreakdownProteinVariable,
            calorieBreakdownCarbVariable, calorieBreakdownFatVariable;
    private double doubleTotalProteinPercentage;
    private double doubleTotalCarbPercentage;
    private double doubleTotalFatPercentage;

    private double dInitialQuantity = 0;

    public String name = "";

    AllFoodsinTabs allFoodsinTabs = new AllFoodsinTabs();
    private String fatText;
    private String carbText;
    private String caloriesText;
    private String proteinText;
    private String quantityText;
    private String nameText;

    Calorie_Breakdown calorie_breakdown;

    public FoodNutritions_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_nutritions, container, false);

        db = new DatabaseHandler(getActivity());
        calorie_breakdown = new Calorie_Breakdown();

        //initialisations
        nameTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionNameTextBox);
        quantityTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionQuantityTextBox);
        caloriesTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionCalorieTextBox);
        proteinTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionProteinTextBox);
        carbTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionCarbTextBox);
        fatTextBoxVariable = (EditText) view.findViewById(R.id.foodNutritionFatTextBox);
        calorieBreakdownProteinVariable = (TextView) view.findViewById(R.id.calorieBreakdownProteinFiguresTextView);
        calorieBreakdownCarbVariable = (TextView) view.findViewById(R.id.calorieBreakdownCarbFiguresTextView);
        calorieBreakdownFatVariable = (TextView) view.findViewById(R.id.calorieBreakdownFatFiguresTextView);
        imageUpdateButton = (ImageButton) view.findViewById(R.id.updateButton);

        //get activity off of main activity
        Main main = (Main) getActivity();
//        searchFragment = new SearchFragment();

        //get the data via bundle from search fragment
        Bundle bundle = this.getArguments();
        String foodName = bundle.getString("foodName", bundle.toString());
        String foodQuantity = bundle.getString("foodQuantity", bundle.toString());
        String foodCalorie = bundle.getString("foodCalorie", bundle.toString());
        String foodProtein = bundle.getString("foodProtein", bundle.toString());
        String foodCarb = bundle.getString("foodCarb", bundle.toString());
        String foodFat = bundle.getString("foodFat", bundle.toString());

        //setting text in text boxes thats being saved in main class of off get method
        nameTextBoxVariable.setText(foodName);
        quantityTextBoxVariable.setText(foodQuantity);
        caloriesTextBoxVariable.setText(foodCalorie);
        proteinTextBoxVariable.setText(foodProtein);
        carbTextBoxVariable.setText(foodCarb);
        fatTextBoxVariable.setText(foodFat);

        //get the text from text boxes
        nameText = nameTextBoxVariable.getText().toString();
        fatText = fatTextBoxVariable.getText().toString();
        carbText = carbTextBoxVariable.getText().toString();
        proteinText = proteinTextBoxVariable.getText().toString();
        caloriesText = caloriesTextBoxVariable.getText().toString();
        quantityText = quantityTextBoxVariable.getText().toString();

        dInitialQuantity = Double.parseDouble(quantityText);
        //calculate calorie breakdown and set it to text boxes
        CalculateCalorieBreakdown(fatText, carbText, proteinText, caloriesText);
        calorieBreakdownProteinVariable.setText(Double.toString(doubleTotalProteinPercentage) + " %");
        calorieBreakdownFatVariable.setText(Double.toString(doubleTotalFatPercentage) + " %");
        calorieBreakdownCarbVariable.setText(Double.toString(doubleTotalCarbPercentage) + " %");
        //showing the button in action bar
        setHasOptionsMenu(true);
        imageButton();

        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Summary");
        return view;
    }

    //inflating the menu on action bar within fragment
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.foodnutritions_fragment_menu, menu);
    }

    //action bar button options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //when add button is selected it adds the values of text boxes in breakfast tab
            case R.id.foodNutritions_addActionBar:
                Main main = (Main) getActivity();
                searchFragment = new SearchFragment();

                if (main.getIsBreakfast() == true) {
                    //send it to setters
                    main.setBreakfastFoodName(nameText);
                    main.setBreakfastFoodCalories(caloriesText);
                    main.setBreakfastFoodCarb(carbText);
                    main.setBreakfastFoodFat(fatText);
                    main.setBreakfastFoodProtein(proteinText);
                    main.setBreakfastFoodQuantity(quantityText);

                    //send the values to nutrition summary via bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("FoodName", nameText);
                    bundle.putDouble("Calories", Double.parseDouble(caloriesText));
                    bundle.putDouble("Protein", Double.parseDouble(proteinText));
                    bundle.putDouble("Carb", Double.parseDouble(carbText));
                    bundle.putDouble("Fat", Double.parseDouble(fatText));

                    Intent intent = getActivity().getIntent();
                    intent.putExtras(bundle);

//                    getActivity().getIntent().removeExtra("Lunch Calories");
//                    getActivity().getIntent().removeExtra("Lunch Protein");
//                    getActivity().getIntent().removeExtra("Lunch Carb");
//                    getActivity().getIntent().removeExtra("Lunch Fat");
//
//                    getActivity().getIntent().removeExtra("Dinner Calories");
//                    getActivity().getIntent().removeExtra("Dinner Protein");
//                    getActivity().getIntent().removeExtra("Dinner Carb");
//                    getActivity().getIntent().removeExtra("Dinner Fat");

                    main.setIsBreakfast(false);
                } else if (main.getIsLunch() == true) {

                    main.setLunchFoodName(nameText);
                    main.setLunchFoodCalories(caloriesText);
                    main.setLunchFoodCarb(carbText);
                    main.setLunchFoodFat(fatText);
                    main.setLunchFoodProtein(proteinText);
                    main.setLunchFoodQuantity(quantityText);

                    //send the values to nutrition summary via bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("Lunch FoodName", nameText);
                    bundle.putDouble("Lunch Calories", Double.parseDouble(caloriesText));
                    bundle.putDouble("Lunch Protein", Double.parseDouble(proteinText));
                    bundle.putDouble("Lunch Carb", Double.parseDouble(carbText));
                    bundle.putDouble("Lunch Fat", Double.parseDouble(fatText));

                    Intent intent = getActivity().getIntent();
                    intent.putExtras(bundle);
//
//                    getActivity().getIntent().removeExtra("Calories");
//                    getActivity().getIntent().removeExtra("Protein");
//                    getActivity().getIntent().removeExtra("Carb");
//                    getActivity().getIntent().removeExtra("Fat");
//
//                    getActivity().getIntent().removeExtra("Dinner Calories");
//                    getActivity().getIntent().removeExtra("Dinner Protein");
//                    getActivity().getIntent().removeExtra("Dinner Carb");
//                    getActivity().getIntent().removeExtra("Dinner Fat");

                    main.setIsLunch(false);
                } else if (main.getIsDinner() == true) {

                    main.setDinnerFoodName(nameText);
                    main.setDinnerFoodCalories(caloriesText);
                    main.setDinnerFoodCarb(carbText);
                    main.setDinnerFoodFat(fatText);
                    main.setDinnerFoodProtein(proteinText);
                    main.setDinnerFoodQuantity(quantityText);

                    //send the values to nutrition summary via bundle
                    Bundle bundle = new Bundle();
                    bundle.putString("Dinner FoodName", nameText);
                    bundle.putDouble("Dinner Calories", Double.parseDouble(caloriesText));
                    bundle.putDouble("Dinner Protein", Double.parseDouble(proteinText));
                    bundle.putDouble("Dinner Carb", Double.parseDouble(carbText));
                    bundle.putDouble("Dinner Fat", Double.parseDouble(fatText));

                    Intent intent = getActivity().getIntent();
                    intent.putExtras(bundle);

//                    getActivity().getIntent().removeExtra("Lunch Calories");
//                    getActivity().getIntent().removeExtra("Lunch Protein");
//                    getActivity().getIntent().removeExtra("Lunch Carb");
//                    getActivity().getIntent().removeExtra("Lunch Fat");
//
//                    getActivity().getIntent().removeExtra("Calories");
//                    getActivity().getIntent().removeExtra("Protein");
//                    getActivity().getIntent().removeExtra("Carb");
//                    getActivity().getIntent().removeExtra("Fat");

                    main.setIsDinner(false);
                }
                //opens the nutrition summary Fragment
                DiaryFragment diaryFragment = new DiaryFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //replacing the fragment inside the layout
                manager.beginTransaction().replace(R.id.content_layout, diaryFragment).addToBackStack(null).commit();

            case R.id.generateChart:

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void imageButton() {
        imageUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                //inflate the dialog layout to import it into alert dialog in order to view
                LayoutInflater inflater = getActivity().getLayoutInflater();
                //when inflated, then able to get or set value in text view
                View vi = inflater.inflate(R.layout.food_nutritions_dialog, null);
                gramsEdittextDialog = (EditText) vi.findViewById(R.id.gramsEdittext);
                foodNameTextViewDialog = (TextView) vi.findViewById(R.id.nameTextviewDialog);

                try {
                    foodNameTextViewDialog.setText(nameTextBoxVariable.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Error", e.getMessage());
                }
                //set custom layout in dialog
                dialog.setView(vi);

                dialog.setCancelable(false);
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (gramsEdittextDialog.getText().toString().equals("")) {
                            Toast.makeText(getActivity(), "Enter a value of more than 0", Toast.LENGTH_LONG).show();
                        }
                        try {
                            quantityTextBoxVariable.setText(gramsEdittextDialog.getText().toString());

                            //update other fields according to the updated serving size/breakfastFoodQuantity
                            //FIX THIS L8 -- It doesnt update correctly after initial update because it doesnt get divide the update d
                            String sQuantity = gramsEdittextDialog.getText().toString();
                            double dQuantity = Double.parseDouble(sQuantity);

                            double dDifference = dQuantity / dInitialQuantity;

                            double dFat = Double.parseDouble(fatTextBoxVariable.getText().toString());
                            double dCarb = Double.parseDouble(carbTextBoxVariable.getText().toString());
                            double dProtein = Double.parseDouble(proteinTextBoxVariable.getText().toString());
                            double dCalories = Double.parseDouble(caloriesTextBoxVariable.getText().toString());

                            double fatTotal = dDifference * dFat;
                            double carbTotal = dDifference * dCarb;
                            double proteinTotal = dDifference * dProtein;
                            double caloriesTotal = dDifference * dCalories;

                            fatTextBoxVariable.setText(Double.toString(fatTotal));
                            carbTextBoxVariable.setText(Double.toString(carbTotal));
                            proteinTextBoxVariable.setText(Double.toString(proteinTotal));
                            caloriesTextBoxVariable.setText(Double.toString(caloriesTotal));

                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage("Error", e.getMessage());
                        }
                    }
                }).setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alert = dialog.create();
                alert.show();
            }

        });

    }

    //alert dialog message

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    //Calculates the percentage of nutritions based on the input and the class Calorie_Breakdown
    public void CalculateCalorieBreakdown(String fat, String carb, String protein, String calories) {
        //Calorie breakdown in breakfastFoodFat, breakfastFoodProtein and carbs.
        //First we get the nutritions in breakfastFoodCalories then acquire the percentage of it
        double fatCalories = calorie_breakdown.fatToCalories(Double.parseDouble(fat));
        double totalFatPercentage = calorie_breakdown.caloriesInFat(fatCalories, Double.parseDouble(calories));
        doubleTotalFatPercentage = Math.round(totalFatPercentage);

        double carbCalories = calorie_breakdown.carbToCalories(Double.parseDouble(carb));
        double totalCarbPercentage = calorie_breakdown.caloriesInCarb(carbCalories, Double.parseDouble(calories));
        doubleTotalCarbPercentage = Math.round(totalCarbPercentage);

        double proteinCalories = calorie_breakdown.proteinToCalories(Double.parseDouble(protein));
        double totalProteinPercentage = calorie_breakdown.caloriesinProtein(proteinCalories, Double.parseDouble(calories));
        doubleTotalProteinPercentage = Math.round(totalProteinPercentage);

    }

}
