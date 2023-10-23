package com.example.daadmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class onGoingTabFragment extends Fragment {
    ArrayList<homeCardRecycler> card = new ArrayList<>();

    public onGoingTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recylerView = view.findViewById(R.id.homeRecylerView);
        cardModel();
        homeCardRecylerViewAdapter adapter = new homeCardRecylerViewAdapter((getActivity().getApplicationContext()),card);
        recylerView.setAdapter(adapter);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        // Inflate the layout for this fragment
        return view;
        // Inflate the layout for this fragment
    }
    public void cardModel(){

        String Names[] = {"AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle"};
        String Problem[] = {"cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz"};

        for (int i = 0; i < Names.length; i++){
            card.add(new homeCardRecycler(Names[i],Problem[i]));
        }
    }
}