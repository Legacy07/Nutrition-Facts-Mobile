package com.turkishlegacy.nutritionfactsmobile;

public class Calorie_Breakdown {

    //1 gram of fat is 9 calories
    public static double fatToCalories(double fat)
    {
        double totalCalories = fat * 9;

        return totalCalories;
    }
    //1 gram of carb in 4 calories
    public static double carbToCalories(double carb)
    {
        double totalCalories = carb * 4;

        return totalCalories;
    }
    //1 gram of protein is 4 calories
    public static double proteinToCalories(double protein)
    {
        double totalCalories = protein * 4;

        return totalCalories;
    }
    //percentage of fat in total Calories
    public static double caloriesInFat(double fatCalories, double calories)
    {
        double totalCalories = (fatCalories / calories) * 100;

        return totalCalories;
    }
    //percentage of carb in total Calories
    public static double caloriesInCarb(double carbCalories, double calories)
    {
        double totalCalories = (carbCalories / calories) * 100;

        return totalCalories;
    }
    //percentage of protein in total Calories
    public static double caloriesinProtein(double proteinCalories, double calories)
    {
        double totalCalories = (proteinCalories / calories) * 100;

        return totalCalories;

    }
}
