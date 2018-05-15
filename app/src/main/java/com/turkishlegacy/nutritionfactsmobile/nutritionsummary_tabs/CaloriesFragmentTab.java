package com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.turkishlegacy.nutritionfactsmobile.R;
import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.BreakfastTab;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CaloriesFragmentTab extends Fragment {

    TextView totalCalorieFiguresTextView, proteinTotalTextView, carbTotalTextView, fatTotalTextView;

    public CaloriesFragmentTab() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calories_fragment_tab, container, false);

        totalCalorieFiguresTextView = (TextView) view.findViewById(R.id.totalCalorieFiguresTextView);
        proteinTotalTextView = (TextView) view.findViewById(R.id.proteinTotalTextView);
        carbTotalTextView = (TextView) view.findViewById(R.id.carbTotalTextView);
        fatTotalTextView = (TextView) view.findViewById(R.id.fatTotalTextView);

        Intent intent = getActivity().getIntent();
        //check if the data is passed
        if (intent.hasExtra("Calories")) {
            //get data from breakfast, lunch and dinner fragments
            Bundle bundle = getActivity().getIntent().getExtras();
            int caloriesTotal = bundle.getInt("Calories");
            int proteinTotal = bundle.getInt("Protein");
            int carbTotal = bundle.getInt("Carb");
            int fatTotal = bundle.getInt("Fat");

            totalCalorieFiguresTextView.setText(String.valueOf(caloriesTotal));
            proteinTotalTextView.setText(String.valueOf(proteinTotal));
            carbTotalTextView.setText(String.valueOf(carbTotal));
            fatTotalTextView.setText(String.valueOf(fatTotal));
        }
        else{

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

}
