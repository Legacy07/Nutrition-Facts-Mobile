package com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.turkishlegacy.nutritionfactsmobile.R;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.ArrayList;

public class ChartsFragmentTab extends Fragment {

    private static String TAG = "ChartsFragment";

    private float[] percentageArray = new float[3];
    private String[] nutrientsArray = {"Protein", "Carbohydrate", "Fat"};
    PieChart pieChart;

    public ChartsFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_fragment_tab, container, false);

        pieChart = (PieChart) view.findViewById(R.id.idPieChart);

        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(0F);
        pieChart.setTransparentCircleAlpha(0);
//        pieChart.setCenterText("Chart");
//        pieChart.setCenterTextSize(10);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addDataSet();

//        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Log.d(TAG, "onValueSelected: Value select from chart.");
//                Log.d(TAG, "onValueSelected: " + e.toString());
//                Log.d(TAG, "onValueSelected: " + h.toString());
//
//                int pos1 = e.toString().indexOf("");
//                String percentage = e.toString().substring(pos1 + 0);
//
//                for (int i = 0; i < percentageArray.length; i++) {
//                    if (percentageArray[i] == Float.parseFloat(percentage)) {
//                        pos1 = i;
//                        break;
//                    }
//                }
//                String nutrients = nutrientsArray[pos1 + 1];
//                Toast.makeText(getActivity(), percentage + "% of " + nutrients, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });
        return view;
    }

    private void addDataSet() {
        Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> percentageArrayList = new ArrayList<>();
        ArrayList<String> nutrientsArrayList = new ArrayList<>();

        Intent intent = getActivity().getIntent();
        //check if the data is passed
        if (intent.hasExtra("ProteinPercentage")) {

            Bundle bundle = getActivity().getIntent().getExtras();

            float protein = bundle.getFloat("ProteinPercentage");
            float carb = bundle.getFloat("CarbPercentage");
            float fat = bundle.getFloat("FatPercentage");

            percentageArray[0] = protein;
            percentageArray[1] = carb;
            percentageArray[2] = fat;

            for (int i = 0; i < percentageArray.length; i++) {
                percentageArrayList.add(new PieEntry(percentageArray[i], i));
            }

            for (int i = 1; i < nutrientsArray.length; i++) {
                nutrientsArrayList.add(nutrientsArray[i]);
            }

            //create the data set
            PieDataSet pieDataSet = new PieDataSet(percentageArrayList, "Calorie Breakdown");
            pieDataSet.setSliceSpace(5);
            pieDataSet.setValueTextSize(20);

            //add colors to dataset
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            colors.add(ContextCompat.getColor(getContext(), R.color.colorLightGray));
            colors.add(Color.GREEN);

            pieDataSet.setColors(colors);

            //add legend to chart
            Legend legend = pieChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

            //create pie data object
            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }
}