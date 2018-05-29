package com.turkishlegacy.nutritionfactsmobile;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;
import com.turkishlegacy.nutritionfactsmobile.listviewadaptors.CustomListViewAdaptor;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;
import com.turkishlegacy.nutritionfactsmobile.model.Foods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchFragment extends Fragment {

//    private ISearchFragment iSearchFragment;

    EditText searchTextEditText;
    ImageButton searchButtonVariable;
    DatabaseHandler db;
    NutritionSummary_Fragment nutritionSummary_fragment;
    //listview and arraylist declared
    ListView listViewLv;
    CustomListViewAdaptor adaptor;
    ArrayList<Foods> foodsArrayList = new ArrayList<>();
    //create a list for filtering
    ArrayList<Foods> filteredList = new ArrayList<>();
    AllFoodsinTabs allFoodsinTabs = new AllFoodsinTabs();

    public String name = "";
    public String calories = "";
    public String protein = "";
    public String carb = "";
    public String fat = "";
    public String quantity = "";
    Main main;

    private RequestQueue requestQueue;
    ProgressDialog dialog;

    Handler handler;

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

        searchTextEditText = (EditText) view.findViewById(R.id.searchTextBox);
        listViewLv = (ListView) view.findViewById(R.id.listView);
        adaptor = new CustomListViewAdaptor(getActivity(), foodsArrayList);

        dialog = new ProgressDialog(getActivity());
        handler = new Handler();

        listViewLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    main = (Main) getActivity();

                    String getNameText = foodsArrayList.get(position).getName();
                    //gets the string from FoodName text box and by using the methods from database handler,
                    // it gets the values by FoodName and set it in textbox of Nutrition Summary fragment.
                    String setName = getNameText;
                    String setCalorie = foodsArrayList.get(position).getCalorie();
                    String setQuantity = foodsArrayList.get(position).getQuantity();
                    String setProtein = foodsArrayList.get(position).getProtein();
                    ;
                    String setCarb = foodsArrayList.get(position).getCarb();
                    String setFat = foodsArrayList.get(position).getFat();

                    //trim calories and quantity because they contain text.
                    String replacedCalories = setCalorie.substring(0, setCalorie.length() - 9);
                    String replacedQuantity = setQuantity.substring(0, setQuantity.length() - 6);

                    FoodNutritions_Fragment foodNutritions_fragment = new FoodNutritions_Fragment();
                    //pass the data via bundle to food nutritions fragment
                    Bundle bundle = new Bundle();
                    bundle.putString("foodName", setName);
                    bundle.putString("foodCalorie", replacedCalories);
                    bundle.putString("foodQuantity", replacedQuantity);
                    bundle.putString("foodProtein", setProtein);
                    bundle.putString("foodCarb", setCarb);
                    bundle.putString("foodFat", setFat);

                    foodNutritions_fragment.setArguments(bundle);

                    //opens the nutrition summary Fragment
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    //replacing the fragment inside the layout
                    manager.beginTransaction().replace(R.id.content_layout, foodNutritions_fragment).addToBackStack(null).commit();

                } catch (Exception exc) {
                    exc.printStackTrace();
                    showMessage("Error", exc.getMessage());
                }
            }
        });

//        try {
////            if (!dialog.isShowing()) {
//                dialog.setMessage("Loading...");
//                dialog.setCancelable(true);
//                dialog.show();
////            }
//
//        } catch (Exception e) {
//            Log.d("Progress Dialog Error: ", e.getMessage());
//        }


        Search(view);

        new BackgroundTask().execute();
        //actionbar title change
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Search Foods");
        return view;
    }

    public void Search(View view) {

//        String getNameText = searchTextEditText.getText().toString();

        searchTextEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterFoods(s.toString());
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

    //filter foods
    public void filterFoods(String food) {
        filteredList.clear();
        //iterate through the array list
        for (Foods foods : foodsArrayList) {
            //if name of the food is within the arraylist then add it to the filtered arraylist
            if (foods.getName().toLowerCase().contains(food.toLowerCase())) {
                filteredList.add(foods);
            }
        }

        adaptor.filterList(filteredList);

    }

    //}
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

    //parse json data
    private void parseJSON() {
        //get the data from url
        String url = "https://deadmanwalking2007.000webhostapp.com/json_data.php";
        //request json objects
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        foodsArrayList.clear();

                        Foods f = null;
                        try {
                            //get the table
                            JSONArray jsonArray = response.getJSONArray("Nutrients");
                            //iterate through json array to pull the data
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject foods = jsonArray.getJSONObject(i);

                                String name = foods.getString("FoodName");
                                String quantity = foods.getString("ServingSize");
                                String calorie = foods.getString("Calories");
                                String protein = foods.getString("Protein");
                                String carb = foods.getString("Carb");
                                String fat = foods.getString("Fat");

                                f = new Foods();
                                //add to foods model
                                f.setName(name);
                                f.setQuantity(quantity + " Grams");
                                f.setCalorie(calorie + " Calories");
                                f.setProtein(protein);
                                f.setCarb(carb);
                                f.setFat(fat);
                                //add it to array list to hold the information
                                foodsArrayList.add(f);
                            }
                            //set the adaptor for listview
                            listViewLv.setAdapter(adaptor);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }

    private class BackgroundTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
//                if (!dialog.isShowing()) {
                dialog.setMessage("Loading...");
                dialog.setCancelable(true);
                dialog.show();
//                }

            } catch (Exception e) {
                Log.d("Progress Dialog Error: ", e.getMessage());
            }

        }

        protected Void doInBackground(Void... args) {

            requestQueue = Volley.newRequestQueue(getActivity());
            parseJSON();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
//            }
        }
    }
}
