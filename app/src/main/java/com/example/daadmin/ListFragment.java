package com.example.daadmin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class ListFragment extends Fragment {

    ArrayList<homeCardRecycler> card = new ArrayList<>();
    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        recyler_view(view);
        // Inflate the layout for this fragment

        return view;
    }
    public void Tabpage(){

    }

    public void recyler_view(View view){
        RecyclerView recylerView = view.findViewById(R.id.list_requested_recycler_view);
        cardModel();
        list_reqested_recycler_view_model adapter = new list_reqested_recycler_view_model((getActivity().getApplicationContext()),card);
        recylerView.setAdapter(adapter);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

    }
    public void cardModel(){

        String Names[] = {"Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle"};
        String Problem[] = {"fever","xyz","cough","fever","xyz","cough","fever"};

        for (int i = 0; i < Names.length; i++){
            card.add(new homeCardRecycler(Names[i],Problem[i]));
        }
    }

//tab
//    public void load_tab(View view){
//        ViewPager  viewPagerItemTab = view.findViewById(R.id.viewPagerItemTab);
//        TabLayout listTabItem = view.findViewById(R.id.listTabItem);
//
//        viewPagerTABadapter tabAdapter = new viewPagerTABadapter(getActivity().getSupportFragmentManager());
//        viewPagerItemTab.setAdapter(tabAdapter);
//        listTabItem.setupWithViewPager(viewPagerItemTab);
//    }
}