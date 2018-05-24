package com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turkishlegacy.nutritionfactsmobile.Calorie_Breakdown;
import com.turkishlegacy.nutritionfactsmobile.MacrosCalculation;
import com.turkishlegacy.nutritionfactsmobile.R;



public class CaloriesFragmentTab extends Fragment {

    TextView totalCalorieFiguresTextView, goalCaloriesEditText, remainingCaloriesTextView, proteinTotalTextView,
            carbTotalTextView, fatTotalTextView, proteinGoalEditText, carbGoalEditText, fatGoalEditText, remainingProteinTextView,
            remainingCarbTextView, remainingFatTextView, calorieBreakdownProteinFiguresTextView, calorieBreakdownCarbFiguresTextView,
            calorieBreakdownFatFiguresTextView;

    MacrosCalculation macrosCalculation;
    Calorie_Breakdown calorie_breakdown;

    private double doubleTotalProteinPercentage;
    private double doubleTotalCarbPercentage;
    private double doubleTotalFatPercentage;

    int breakfastCaloriesTotal = 0;
    int breakfastProteinTotal = 0;
    int breakfastCarbTotal = 0;
    int breakfastFatTotal = 0;
    int lunchCaloriesTotal = 0;
    int lunchProteinTotal = 0;
    int lunchCarbTotal = 0;
    int lunchFatTotal = 0;
    int dinnerCaloriesTotal = 0;
    int dinnerProteinTotal = 0;
    int dinnerCarbTotal = 0;
    int dinnerFatTotal = 0;

    public CaloriesFragmentTab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories_fragment_tab, container, false);

        //initiliase
        totalCalorieFiguresTextView = (TextView) view.findViewById(R.id.totalCalorieFiguresTextView);
        goalCaloriesEditText = (TextView) view.findViewById(R.id.goalCaloriesFiguresEditText);
        remainingCaloriesTextView = (TextView) view.findViewById(R.id.remainingCaloriesFiguresTextView);

        proteinTotalTextView = (TextView) view.findViewById(R.id.proteinTotalTextView);
        carbTotalTextView = (TextView) view.findViewById(R.id.carbTotalTextView);
        fatTotalTextView = (TextView) view.findViewById(R.id.fatTotalTextView);

        proteinGoalEditText = (TextView) view.findViewById(R.id.proteinGoalEditText);
        carbGoalEditText = (TextView) view.findViewById(R.id.carbGoalEditText);
        fatGoalEditText = (TextView) view.findViewById(R.id.fatGoalEditText);

        remainingProteinTextView = (TextView) view.findViewById(R.id.proteinLeftTextView);
        remainingCarbTextView = (TextView) view.findViewById(R.id.carbLeftTextView);
        remainingFatTextView = (TextView) view.findViewById(R.id.fatLeftTextView);

        calorieBreakdownProteinFiguresTextView = (TextView) view.findViewById(R.id.calorieBreakdownProteinFiguresTextView);
        calorieBreakdownCarbFiguresTextView = (TextView) view.findViewById(R.id.calorieBreakdownCarbFiguresTextView);
        calorieBreakdownFatFiguresTextView = (TextView) view.findViewById(R.id.calorieBreakdownFatFiguresTextView);

        macrosCalculation = new MacrosCalculation();
        calorie_breakdown = new Calorie_Breakdown();

        Intent intent = getActivity().getIntent();
        //check if the data is passed
        if (intent.hasExtra("Calories") || intent.hasExtra("Lunch Calories") || intent.hasExtra("Dinner Calories")) {
            //get data from breakfast, lunch and dinner fragments
            try {
                Bundle bundle = getActivity().getIntent().getExtras();
                breakfastCaloriesTotal = bundle.getInt("Calories");
                breakfastProteinTotal = bundle.getInt("Protein");
                breakfastCarbTotal = bundle.getInt("Carb");
                breakfastFatTotal = bundle.getInt("Fat");

                lunchCaloriesTotal = bundle.getInt("Lunch Calories");
                lunchProteinTotal = bundle.getInt("Lunch Protein");
                lunchCarbTotal = bundle.getInt("Lunch Carb");
                lunchFatTotal = bundle.getInt("Lunch Fat");

                dinnerCaloriesTotal = bundle.getInt("Dinner Calories");
                dinnerProteinTotal = bundle.getInt("Dinner Protein");
                dinnerCarbTotal = bundle.getInt("Dinner Carb");
                dinnerFatTotal = bundle.getInt("Dinner Fat");
            } catch (Exception e) {
                Log.d("Error in getting bundle", e.getMessage());
            }
            //calculate the total form breakfast,lunch and dinner
            int calculateMealCalories = breakfastCaloriesTotal + lunchCaloriesTotal + dinnerCaloriesTotal;
            int calculateMealProtein = breakfastProteinTotal + lunchProteinTotal + dinnerProteinTotal;
            int calculateMealCarb = breakfastCarbTotal + lunchCarbTotal + dinnerCarbTotal;
            int calculateMealFat = breakfastFatTotal + lunchFatTotal + dinnerFatTotal;

            totalCalorieFiguresTextView.setText(String.valueOf(calculateMealCalories));
            proteinTotalTextView.setText(String.valueOf(calculateMealProtein));
            carbTotalTextView.setText(String.valueOf(calculateMealCarb));
            fatTotalTextView.setText(String.valueOf(calculateMealFat));

            //get text from 'goal' edittexts
            String totalCalories = totalCalorieFiguresTextView.getText().toString();
            String goalCalories = goalCaloriesEditText.getText().toString();

            String totalProtein = proteinTotalTextView.getText().toString();
            String goalProtein = proteinGoalEditText.getText().toString();

            String totalCarb = carbTotalTextView.getText().toString();
            String goalCarb = carbGoalEditText.getText().toString();

            String totalFat = fatTotalTextView.getText().toString();
            String goalFat = fatGoalEditText.getText().toString();

            //calculate the remaining calories and other types of nutrients
            remainingCaloriesTextView.setText(String.valueOf(macrosCalculation.calories(Integer.parseInt(totalCalories),
                    Integer.parseInt(goalCalories))));
            remainingProteinTextView.setText(String.valueOf((macrosCalculation.protein(Integer.parseInt(totalProtein),
                    Integer.parseInt(goalProtein)))));
            remainingCarbTextView.setText(String.valueOf((macrosCalculation.carb(Integer.parseInt(totalCarb),
                    Integer.parseInt(goalCarb)))));
            remainingFatTextView.setText(String.valueOf((macrosCalculation.fat(Integer.parseInt(totalFat),
                    Integer.parseInt(goalFat)))));

            //calculate calorie breakdown
            calculateCalorieBreakdown(totalFat, totalCarb, totalProtein, totalCalories);
            calorieBreakdownProteinFiguresTextView.setText(Double.toString(doubleTotalProteinPercentage) + " %");
            calorieBreakdownCarbFiguresTextView.setText(Double.toString(doubleTotalCarbPercentage) + " %");
            calorieBreakdownFatFiguresTextView.setText(Double.toString(doubleTotalFatPercentage) + " %");

        } else {

        }

        //showing the button in action bar
        setHasOptionsMenu(true);
        return view;
    }

    //inflating the menu on action bar within fragment
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_caloriesfragmenttab_menu, menu);
    }

    //action bar button options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //when add button is selected it adds the values of text boxes in breakfast tab
            case R.id.generateChart:

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void calculateCalorieBreakdown(String fat, String carb, String protein, String calories) {
        //Calorie breakdown in fat, protein and carbs.
        //First we get the nutritions in calories then acquire the percentage of it
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
