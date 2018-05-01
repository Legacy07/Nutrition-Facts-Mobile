package com.turkishlegacy.nutritionfactsmobile.listviewadaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.turkishlegacy.nutritionfactsmobile.R;
import com.turkishlegacy.nutritionfactsmobile.model.Foods;

import java.util.ArrayList;

public class CustomListViewAdaptor extends BaseAdapter {
    EditText searchTextVariable;
    Context c;
    //array list for foods class
    ArrayList<Foods> foods;
    LayoutInflater inflater;

    public CustomListViewAdaptor(Context c, ArrayList<Foods> foods) {
        this.c = c;
        this.foods = foods;
    }

    //get the size of the arraylist
    @Override
    public int getCount() {
        return foods.size();
    }

    //get the item
    @Override
    public Object getItem(int position) {
        return foods.get(position);
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
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
        }
        //place gathered item into text view to output
        TextView nameTxt = (TextView) convertView.findViewById(R.id.listViewNameText);
        TextView quantityText = (TextView) convertView.findViewById(R.id.listViewQuantityText);
        TextView calorieText = (TextView) convertView.findViewById(R.id.listViewCalorieText);

        nameTxt.setText(foods.get(position).getName());
        quantityText.setText(foods.get(position).getQuantity());
        calorieText.setText(foods.get(position).getCalorie());


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
