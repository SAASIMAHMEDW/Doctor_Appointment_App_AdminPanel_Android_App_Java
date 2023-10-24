package com.example.daadmin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    String EMAIL,PASSWORD;
    ArrayList<homeCardRecycler> card = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment getInstance(String email, String password){
        HomeFragment homeFragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("EMAILX",email);
        bundle.putString("PASSWORDX",password);
        homeFragment.setArguments(bundle);
        return homeFragment;
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
        SpinnerOptions(view);
        // Inflate the layout for this fragment
        return view;
    }

    public void SpinnerOptions(View view){
        Spinner spinner_doctor_status;
        ArrayList<String> doctorStatus = new ArrayList<>();
        spinner_doctor_status = view.findViewById(R.id.spinner_doctor_status);
        doctorStatus.add("Available");
        doctorStatus.add("Holiday");
        doctorStatus.add("Busy");
//        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.select_dialog_singlechoice,doctorStatus);
//        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_activated_1,doctorStatus);
        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_activated_1,doctorStatus);
//        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), R.layout.spinner_dropdown_item,doctorStatus);
//        spinnerAdaptor.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,doctorStatus);
        spinner_doctor_status.setAdapter(spinnerAdaptor);
    }

    public void cardModel(){

        String Names[] = {"AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle"};
        String Problem[] = {"cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz"};

        for (int i = 0; i < Names.length; i++){
            card.add(new homeCardRecycler(Names[i],Problem[i]));
        }
    }

    public void getBundleData(){
        if (getArguments()!=null){
            EMAIL = getArguments().getString("EMAILX");
            PASSWORD = getArguments().getString("PASSWORDX");
        }
    }
}