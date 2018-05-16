package com.turkishlegacy.nutritionfactsmobile.nutritionsummary_tabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.turkishlegacy.nutritionfactsmobile.R;


public class ChartsFragmentTab extends Fragment {


    public ChartsFragmentTab() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charts_fragment_tab, container, false);

        return view;
    }

}
