package com.turkishlegacy.nutritionfactsmobile;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.zip.Inflater;


public class BodyMeasurement extends Fragment {


    public BodyMeasurement() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_body_measurement, container, false);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Body Measurements");

        return view;

    }



}
