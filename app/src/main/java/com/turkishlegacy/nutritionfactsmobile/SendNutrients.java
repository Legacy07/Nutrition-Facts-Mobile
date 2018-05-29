package com.turkishlegacy.nutritionfactsmobile;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;

import java.util.ArrayList;

public class SendNutrients {
    public SendNutrients() {
    }

    public int send(int iCalories, int iProtein, int iCarb, int iFat, ArrayList<AllFoodsinTabs> list, ListView listView) {
        String sCalories = "";
        String replacedCalories = "";

        String sProtein = "";
        String replacedProtein = "";

        String sCarb = "";
        String replacedCarb = "";

        String sFat = "";
        String replacedFat = "";

        //go through the list
        for (int i = 0; i < list.size(); i++) {
            //get calories from every listview item of the calories textview
            View view1 = listView.getChildAt(i);
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

            return iCalories + iProtein + iCarb + iFat;

        }
        return iCalories + iProtein + iCarb + iFat;
    }

//    public int sendN(String sCalories, String sProtein, String sCarb, String sFat) {
//        String replacedCalories = "";
//
//        String replacedProtein = "";
//
//        String replacedCarb = "";
//
//        String replacedFat = "";
//
//        only pull out the numbers
//        replacedCalories = sCalories.substring(0, sCalories.length() - 9);
//        replacedProtein = sProtein.substring(0, sProtein.length() - 17);
//        replacedCarb = sCarb.substring(0, sCarb.length() - 15);
//        replacedFat = sFat.substring(0, sFat.length() - 13);
//
//        return iCalories + iProtein + iCarb + iFat;
//    }
//
//
}
