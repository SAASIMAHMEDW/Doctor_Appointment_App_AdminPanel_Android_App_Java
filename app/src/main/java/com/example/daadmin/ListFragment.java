package com.example.daadmin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        ViewPager  viewPagerItemTab = view.findViewById(R.id.viewPagerItemTab);
        TabLayout listTabItem = view.findViewById(R.id.listTabItem);

        viewPagerTABadapter tabAdapter = new viewPagerTABadapter(getActivity().getSupportFragmentManager());
        viewPagerItemTab.setAdapter(tabAdapter);
        listTabItem.setupWithViewPager(viewPagerItemTab);

        // Inflate the layout for this fragment

        return view;
    }
    public void Tabpage(){

    }
}