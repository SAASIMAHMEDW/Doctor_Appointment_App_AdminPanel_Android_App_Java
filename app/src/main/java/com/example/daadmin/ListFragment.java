package com.example.daadmin;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment {

    String DOC_EMAIL,DOC_PASSWORD;
    LinearLayout no_patients_request_layout;
    RecyclerView recylerView;
    ArrayList<RequestingListCardRecyclerViewModel> card = new ArrayList<>();

    ArrayList<RequestingListCardRecyclerViewModel> allDataList;
    public ListFragment() {
        // Required empty public constructor
    }

    public static ListFragment getInstance(String email,String password){

        ListFragment listFragment = new ListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("EMAILX",email);
        bundle.putString("PASSWORDX",password);

        listFragment.setArguments(bundle);

        return listFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_list, container, false);
        find_views_by_id(view);
        getBundleData();
        get_patients_requesting_delayed();
        setRequestListRecylerView_delayed(view);
//        recyler_view(view);
        // Inflate the layout for this fragment

        return view;
    }

    public void find_views_by_id(View view){
        no_patients_request_layout = view.findViewById(R.id.no_patients_request_layout);
        recylerView  = view.findViewById(R.id.list_requested_recycler_view);
    }

    public void setRequestListRecylerView(View view){
        if (allDataList.size()==0){
            recylerView.setVisibility(View.GONE);
            no_patients_request_layout.setVisibility(View.VISIBLE);
        }else {
            recylerView.setVisibility(View.VISIBLE);
            no_patients_request_layout.setVisibility(View.GONE);
            card_model_delayed();
            recyler_view_delayed(view);
        }
    }

    public void setRequestListRecylerView_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setRequestListRecylerView(view);
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,3500);
    }

    public void recyler_view(View view){

//        cardModel();
        list_reqested_recycler_view_model adapter = new list_reqested_recycler_view_model((getActivity().getApplicationContext()),card);
        recylerView.setAdapter(adapter);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

    }

    public void recyler_view_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recyler_view(view);
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,4000);
    }
//    public void cardModel(){
//
//        String Names[] = {"Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle"};
//        String Problem[] = {"fever","xyz","cough","fever","xyz","cough","fever"};
//
//        for (int i = 0; i < Names.length; i++){
//            card.add(new homeCardRecycler(Names[i],Problem[i]));
//        }
//    }

    public void getBundleData(){
        if (getArguments()!=null){
            DOC_EMAIL = getArguments().getString("EMAILX");
            DOC_PASSWORD = getArguments().getString("PASSWORDX");
        }
    }

    public void get_requesting_patient_book(){
        allDataList = new ArrayList<>();
        allDataList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference CR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK");
        CR.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null){
                    List<RequestingListCardRecyclerViewModel> data = value.toObjects(RequestingListCardRecyclerViewModel.class);
                    allDataList.addAll(data);
                }
            }
        });
    }

    public void get_patients_requesting_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_requesting_patient_book();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1500);
    }

    public void cardModel() {

        ArrayList<String> NAMES = new ArrayList<>();
        ArrayList<String> PROBLEMS = new ArrayList<>();
        ArrayList<String> EMAILS = new ArrayList<>();
        ArrayList<String> STATUS = new ArrayList<>();
        for (int i = 0; i < allDataList.size(); i++) {
            if ((allDataList.get(i).status).equals("request")) {
                NAMES.add(allDataList.get(i).name.toString());
                PROBLEMS.add(allDataList.get(i).problem.toString());
                EMAILS.add(allDataList.get(i).email.toString());
                STATUS.add(allDataList.get(i).status.toString());
            }
        }

        if (NAMES.size()==0){
            recylerView.setVisibility(View.GONE);
            no_patients_request_layout.setVisibility(View.VISIBLE);
        }else {
            for (int i = 0; i < NAMES.size(); i++){
                card.add(new RequestingListCardRecyclerViewModel(NAMES.get(i),EMAILS.get(i),PROBLEMS.get(i),STATUS.get(i),DOC_EMAIL));
            }
        }
    }

    public void card_model_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cardModel();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
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