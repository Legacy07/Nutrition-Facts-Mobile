package com.turkishlegacy.nutritionfactsmobile;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;
import com.turkishlegacy.nutritionfactsmobile.diaryfragment_tabs.*;


public class AddFood_Fragment extends Fragment {

    //variables for textboxes
    EditText nameText, servingSizeText, calorieText, proteinText, carbText, fatText;
    Button addButtonVariable, showButtonVariable;

    DatabaseHandler db;

    public AddFood_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //in order to have findviewbyid method this needs to be done in fragments.
        View view = inflater.inflate(R.layout.fragment_add_food_, container, false);
        //database added
        db = new DatabaseHandler(getActivity());

        //casting created textboxes and the button in a variable
        nameText = (EditText) view.findViewById(R.id.nameTextBox);
        servingSizeText = (EditText) view.findViewById(R.id.servingSizeTextBox);
        calorieText = (EditText) view.findViewById(R.id.calorieTextBox);
        proteinText = (EditText) view.findViewById(R.id.proteinTextBox);
        carbText = (EditText) view.findViewById(R.id.carbTextBox);
        fatText = (EditText) view.findViewById(R.id.fatTextBox);
        addButtonVariable = (Button) view.findViewById(R.id.addButton);

        //showing the button in action bar
        //showing the button in action bar
        setHasOptionsMenu(true);
        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Food Type");

        return view;
    }

    //inflating the menu on action bar within fragment
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_addfood_menu, menu);
    }

    //action bar button options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //when add button is selected it adds the values of text boxes in breakfast tab
            case R.id.foodNutritions_addActionBar:
                //get textboxes values in a string
                String checkNameText = nameText.getText().toString();
                String checkServingSizeText = servingSizeText.getText().toString();
                String checkCalorieText = calorieText.getText().toString();
                String checkProteinText = proteinText.getText().toString();
                String checkCarbText = carbText.getText().toString();
                String checkFatText = fatText.getText().toString();

                //check if textboxes are empty
                if (checkNameText.equals("") || checkServingSizeText.equals("") || checkCalorieText.equals("") ||
                        checkProteinText.equals("") || checkCarbText.equals("") || checkFatText.equals("")) {
                    //if empty then output toast
                    Toast.makeText(getActivity(), "One of the field is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    //adding food to the following columns in that method
                    boolean insertedData = db.addFood(nameText.getText().toString(), servingSizeText.getText().toString(), calorieText.getText().toString(),
                            proteinText.getText().toString(), carbText.getText().toString(), fatText.getText().toString());
                    if (insertedData == true) {
                        Toast.makeText(getActivity(), "Data Added!", Toast.LENGTH_LONG).show();
                        nameText.setText("");
                        servingSizeText.setText("");
                        calorieText.setText("");
                        proteinText.setText("");
                        carbText.setText("");
                        fatText.setText("");

                    } else {
                        Toast.makeText(getActivity(), "Data couldn't be added!", Toast.LENGTH_LONG).show();

                    }
                }
                return true;
            case R.id.addfood_show:
                try {
                    // Show all data
                    String data = db.getAllData();
                    showMessage("Nutrition Facts", data);
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Error", e.getMessage());
                }
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
