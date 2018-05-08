package com.turkishlegacy.nutritionfactsmobile.listviewadaptors;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.turkishlegacy.nutritionfactsmobile.SearchFragment;
import com.turkishlegacy.nutritionfactsmobile.model.AllFoodsinTabs;
import com.turkishlegacy.nutritionfactsmobile.R;

import java.util.ArrayList;

public class TabsCustomListViewAdaptor extends BaseAdapter {

    EditText searchTextVariable;
    Context context;
    //array list for foods class
    ArrayList<AllFoodsinTabs> allFoodsList;
    LayoutInflater inflater;

    public TabsCustomListViewAdaptor(Context c, ArrayList<AllFoodsinTabs> allFoods) {
        this.context = c;
        this.allFoodsList = allFoods;
    }

    //get the size of the arraylist
    @Override
    public int getCount() {
        return allFoodsList.size();
    }

    //get the items
    @Override
    public Object getItem(int position) {
        return allFoodsList.get(position);
    }

    //get position of the item to get the item
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflate it to custom listview layout
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tabs_listview, parent, false);
        }
        //textviews initialised
        TextView nameTxt = (TextView) convertView.findViewById(R.id.tabsListviewName);
        TextView quantityText = (TextView) convertView.findViewById(R.id.tabsListviewQuantity);
        TextView calorieText = (TextView) convertView.findViewById(R.id.tabsListviewCalorie);
        TextView proteinText = (TextView) convertView.findViewById(R.id.tabsListviewProtein);
        TextView carbText = (TextView) convertView.findViewById(R.id.tabsListviewCarb);
        TextView fatText = (TextView) convertView.findViewById(R.id.tabsListviewFat);

        //place gathered item into text view to output
        nameTxt.setText(allFoodsList.get(position).getName());
        quantityText.setText(allFoodsList.get(position).getQuantity());
        calorieText.setText(allFoodsList.get(position).getCalorie());
        proteinText.setText(allFoodsList.get(position).getProtein());
        carbText.setText(allFoodsList.get(position).getCarb());
        fatText.setText(allFoodsList.get(position).getFat());

        //event handler if clicked
      /*  final int pos = position;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return convertView;
    }
}
