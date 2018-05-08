package com.turkishlegacy.nutritionfactsmobile;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.BreakfastTab;
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

    AllFoodsinTabs allFoodsinTabs = new AllFoodsinTabs();

    public FoodNutritions_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_nutritions, container, false);

        db = new DatabaseHandler(getActivity());

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
        searchFragment = new SearchFragment();

        //setting text in text boxes thats being saved in main class of off get method
        nameTextBoxVariable.setText(main.getSearchName());
        quantityTextBoxVariable.setText(main.getSearchQuantity());
        caloriesTextBoxVariable.setText(main.getSearchCalories());
        proteinTextBoxVariable.setText(main.getSearchProtein());
        carbTextBoxVariable.setText(main.getSearchCarb());
        fatTextBoxVariable.setText(main.getSearchFat());

        String fatText = fatTextBoxVariable.getText().toString();
        String carbText = carbTextBoxVariable.getText().toString();
        String proteinText = proteinTextBoxVariable.getText().toString();
        String caloriesText = caloriesTextBoxVariable.getText().toString();
        String quantityText = quantityTextBoxVariable.getText().toString();

        dInitialQuantity = Double.parseDouble(quantityText);

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
                //send it to setters
                main.setName(nameTextBoxVariable.getText().toString());
                main.setCalories(caloriesTextBoxVariable.getText().toString());
                main.setCarb(carbTextBoxVariable.getText().toString());
                main.setFat(fatTextBoxVariable.getText().toString());
                main.setProtein(proteinTextBoxVariable.getText().toString());
                main.setQuantity(quantityTextBoxVariable.getText().toString());


                //opens the nutrition summary Fragment
                DiaryFragment diaryFragment = new DiaryFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                //replacing the fragment inside the layout
                manager.beginTransaction().replace(R.id.layout_Fragment, diaryFragment).commit();

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

                            //update other fields according to the updated serving size/quantity
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
        //Calorie breakdown in fat, protein and carbs.
        //First we get the nutritions in calories then acquire the percentage of it
        double fatCalories = Calorie_Breakdown.fatToCalories(Double.parseDouble(fat));
        double totalFatPercentage = Calorie_Breakdown.caloriesInFat(fatCalories, Double.parseDouble(calories));
        doubleTotalFatPercentage = Math.round(totalFatPercentage);

        double carbCalories = Calorie_Breakdown.carbToCalories(Double.parseDouble(carb));
        double totalCarbPercentage = Calorie_Breakdown.caloriesInCarb(carbCalories, Double.parseDouble(calories));
        doubleTotalCarbPercentage = Math.round(totalCarbPercentage);

        double proteinCalories = Calorie_Breakdown.proteinToCalories(Double.parseDouble(protein));
        double totalProteinPercentage = Calorie_Breakdown.caloriesinProtein(proteinCalories, Double.parseDouble(calories));
        doubleTotalProteinPercentage = Math.round(totalProteinPercentage);

    }

}
