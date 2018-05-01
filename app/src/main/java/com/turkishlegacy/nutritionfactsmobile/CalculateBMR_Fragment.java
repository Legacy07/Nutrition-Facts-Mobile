package com.turkishlegacy.nutritionfactsmobile;


import android.content.DialogInterface;
import android.graphics.Typeface;
import android.icu.text.IDNA;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class CalculateBMR_Fragment extends Fragment {
    EditText weightEditText, heightEditText, ageEditText;
    RadioButton standardRadioButton, surplusRadioButton, deficitRadioButton, maleRadioButton, femaleRadioButton;
    TextView outputTextView;
    Button calculate;
    ImageButton surplusInformation;
    RadioGroup radioGroup, genderRadioGroup;

    public String definitionSurplus;
    public String definitionDeficit;
    public String whatIsBMR;

    public CalculateBMR_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculate_bmr_, container, false);

        //radio buttons
        standardRadioButton = (RadioButton) view.findViewById(R.id.standardRadioButton);
        surplusRadioButton = (RadioButton) view.findViewById(R.id.surplusRadioButton);
        deficitRadioButton = (RadioButton) view.findViewById(R.id.deficitRadioButton);
        femaleRadioButton = (RadioButton) view.findViewById(R.id.femaleRadioButton);
        maleRadioButton = (RadioButton) view.findViewById(R.id.maleRadioButton);
        genderRadioGroup = (RadioGroup) view.findViewById(R.id.genderRadioGroup);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        //Imagebutton
        surplusInformation = (ImageButton) view.findViewById(R.id.questionButton);
        //edittext
        weightEditText = (EditText) view.findViewById(R.id.weightTextBox);
        heightEditText = (EditText) view.findViewById(R.id.heightTextBox);
        ageEditText = (EditText) view.findViewById(R.id.ageTextBox);
        //textview
        outputTextView = (TextView) view.findViewById(R.id.outputTextView);
        //button
        calculate = (Button) view.findViewById(R.id.calculateButton);

        InformationSurplusDeficit();
        calculateRadiobutton();
        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("BMR Calculator");
        //showing the button in action bar
        setHasOptionsMenu(true);
        return view;
    }

    //inflating the menu on action bar within fragment
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bmr_information_menu, menu);
    }

    //action bar button options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        whatIsBMR = "Basal metabolic rate (BMR)" + " - is the total number of calories that your body needs to perform basic, " +
                "life-sustaining functions. These basal functions include circulation, breathing, cell production," +
                " nutrient processing, protein synthesis and ion transport. You can calculate basal metabolic rate " +
                "using a mathematical formula.\nCalculation of BMR will help you to realise how much calories you need to " +
                "maintain your body and/or losing or gaining weight depending on what route you're on. ";

        switch (item.getItemId()) {
            //when add button is selected it adds the values of text boxes in breakfast tab
            case R.id.bmrCalculatorInformation:

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                //inflate the dialog layout to import it into alert dialog in order to view
                LayoutInflater inflater = getActivity().getLayoutInflater();
                //when inflated, then able to get or set value in text view
                View vi = inflater.inflate(R.layout.bmrcalculator_whatisbmr_information, null);
                EditText edittext2 = (EditText) vi.findViewById(R.id.informationEdittext2);
                Button formulaButton = (Button) vi.findViewById((R.id.formulaButton));

                try {
                    edittext2.setText(whatIsBMR);

                    formulaButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String formula = "Men: BMR = 66.47 + (13.47 x weight in kg) + (5 x height in cm) - (6.8 x age in years)\n\n" +
                                    "Women: BMR = 447.593 + (9.247 x weight in kg) + (3.098 x height in cm) - (4.330 x age in years)\n\n" +
                                    "For both genders: \nSurplus = add 250 to 500 calories to your BMR\n\n" +
                                    "Deficit = subtract 250 to 500 calories from your BMR ";

                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            //inflate the dialog layout to import it into alert dialog in order to view
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            //when inflated, then able to get or set value in text view
                            View vi = inflater.inflate(R.layout.bmrcalculator_bmrformula, null);
                            EditText edittext = (EditText) vi.findViewById(R.id.formulaEdittext);

                            try {
                                edittext.setText(formula);
                            } catch (Exception e) {
                                e.printStackTrace();
                                showMessage("Error", e.getMessage());
                            }

                            //set custom layout in dialog
                            dialog.setView(vi);

                            dialog.setCancelable(false);
                            dialog.setPositiveButton("Informed!", null);

                            final AlertDialog alert = dialog.create();
                            alert.show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Error", e.getMessage());
                }
                //set custom layout in dialog
                dialog.setView(vi);

                dialog.setCancelable(false);
                dialog.setPositiveButton("Informed!", null);

                final AlertDialog alert = dialog.create();
                alert.show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    public void calculateRadiobutton() {
        standardRadioButton.setChecked(true);
        maleRadioButton.setChecked(true);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //output to show surplus and deficit
                double secondOutput = 0;
                //output to show bmr
                double output = 0;
                //if fields are empty, make toasts
                if (weightEditText.getText().toString().equals("") || heightEditText.getText().toString().equals("")
                        || ageEditText.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "One or more fields are empty", Toast.LENGTH_LONG).show();
                }
                //male calculation
                else if (maleRadioButton.isChecked() && standardRadioButton.isChecked()) {
                    output = BMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString()));
                    outputTextView.setText("You have a BMR of " + output);
                } else if (maleRadioButton.isChecked() && surplusRadioButton.isChecked()) {
                    output = BMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) + 250;
                    secondOutput = BMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) + 500;
                    outputTextView.setText("Your BMR for a surplus will be between " + output + " - " + secondOutput);

                } else if (maleRadioButton.isChecked() && deficitRadioButton.isChecked()) {
                    output = BMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) - 250;
                    secondOutput = BMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) - 500;

                    outputTextView.setText("Your BMR for a deficit will be between " + output + " - " + secondOutput);
                }
                //female calculation
                else if (femaleRadioButton.isChecked() && standardRadioButton.isChecked()) {
                    output = FemaleBMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString()));
                    outputTextView.setText("You have a BMR of " + output);
                } else if (femaleRadioButton.isChecked() && surplusRadioButton.isChecked()) {
                    output = FemaleBMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) + 250;
                    secondOutput = FemaleBMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) + 500;
                    outputTextView.setText("Your BMR for a surplus will be between " + output + " - " + secondOutput);

                } else if (femaleRadioButton.isChecked() && deficitRadioButton.isChecked()) {
                    output = FemaleBMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) - 250;
                    secondOutput = FemaleBMREquation(Double.parseDouble(weightEditText.getText().toString()),
                            Double.parseDouble(heightEditText.getText().toString()), Double.parseDouble(ageEditText.getText().toString())) - 500;

                    outputTextView.setText("Your BMR for a deficit will be between " + output + " - " + secondOutput);

                }
            }

        });
    }

    public void InformationSurplusDeficit() {
        //definition of calorie surplus
        definitionSurplus = "Caloric Surplus" + " - is a state in which you are eating more calories than you are burning. " +
                "Gaining weight requires a calories surplus. When you enter this state, your body takes the excess energy " +
                "and uses it to either make you more muscular (if you are working out) or build up body fat and make you fatter.";
        //definition of caloric deficit
        definitionDeficit = "Caloric Deficit" + " - is a state in which you are burning more calories than you eat. " +
                "Losing weight requires a calorie deficit. When you enter this state, your body needs to somehow " +
                "make up the difference of what you burn and what you eat.";

        surplusInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                //inflate the dialog layout to import it into alert dialog in order to view
                LayoutInflater inflater = getActivity().getLayoutInflater();
                //when inflated, then able to get or set value in text view
                View vi = inflater.inflate(R.layout.bmrcalculator_radiobutton_information_dialog, null);
                EditText edittext = (EditText) vi.findViewById(R.id.informationEditText);

                try {
                    edittext.setText(definitionSurplus + "\n\n" + definitionDeficit);
                } catch (Exception e) {
                    e.printStackTrace();
                    showMessage("Error", e.getMessage());
                }
                //set custom layout in dialog
                dialog.setView(vi);

                dialog.setCancelable(false);
                dialog.setPositiveButton("Informed!", null);

                final AlertDialog alert = dialog.create();
                alert.show();
            }

        });
    }

    public double BMREquation(double weight, double height, double age) {
        double answer = 66.47 + (13.47 * weight) + (5 * height) - (6.8 * age);
        return answer;
    }

    public double FemaleBMREquation(double weight, double height, double age) {
        double answer = 447.59 + (9.25 * weight) + (3.1 * height) - (4.3 * age);
        return answer;
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public void onResume(){
        super.onResume();
        //OnResume Fragment
    }
}
