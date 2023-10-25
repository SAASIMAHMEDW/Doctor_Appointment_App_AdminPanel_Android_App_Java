package com.example.daadmin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER, SPECIALIZATION,ABOUT,STATUS,LOGIN_UID;
    String temp_status;
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
        getBundleData();
        get_doctor_status_From_DB();
        getDataFromDB();
        recyler_view(view);
        spinner_delayed(view);
//        spinner_doctor_status(view);
        delayed();
        // Inflate the layout for this fragment
        return view;
    }

    public void recyler_view(View view){
        RecyclerView recylerView = view.findViewById(R.id.homeRecylerView);
        cardModel();
        homeCardRecylerViewAdapter adapter = new homeCardRecylerViewAdapter((getActivity().getApplicationContext()),card);
        recylerView.setAdapter(adapter);
        recylerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

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
        spinner_doctor_status.setBackgroundColor(getResources().getColor(R.color.AppPrimaryColorX));
        spinner_doctor_status.setDropDownWidth(500);
        spinner_doctor_status.setMinimumHeight(200);
        spinner_doctor_status.setElevation(50f);
        spinner_doctor_status.setAdapter(spinnerAdaptor);
        spinner_doctor_status.setSelection(set_spinner_option(STATUS));
    }

    public void spinner_delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SpinnerOptions(view);
                spinner_doctor_status(view);
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
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

    public void spinner_doctor_status(View view){
        Spinner spinner_doctor_status;
        spinner_doctor_status = view.findViewById(R.id.spinner_doctor_status);
        spinner_doctor_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected_val=spinner_doctor_status.getSelectedItem().toString();
                STATUS = selected_val;
                delayed();
//                Toast.makeText(getContext().getApplicationContext(), selected_val ,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    
    public void get_doctor_login_UID_From_DB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("passwordHint").toString(), Toast.LENGTH_SHORT).show();
//                String uid = documentSnapshot.get("login_uid").toString();
                LOGIN_UID = documentSnapshot.get("login_uid").toString();
//                getProfileData(name,age,passwordHint,gender,ds,about);
//                Toast.makeText(getContext().getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void get_doctor_status_From_DB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("passwordHint").toString(), Toast.LENGTH_SHORT).show();
//                String uid = documentSnapshot.get("login_uid").toString();
                temp_status = documentSnapshot.get("status").toString();
//                STATUS = temp_status;
//                getProfileData(name,age,passwordHint,gender,ds,about);
//                Toast.makeText(getContext().getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setDoctorStatusData_in_REALTIME_DB(){
//        DatabaseReference myRef;
//        myRef = FirebaseDatabase.getInstance().getReference();
        doctorDataModel data = new doctorDataModel(NAME,EMAIL,PASSWORD,LOGIN_UID,STATUS);
//        myRef.child("TEST").child(LOGIN_UID).setValue(data);
        // Write a message to the database
        FirebaseDatabase database;
        DatabaseReference dbRef;
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("ADMINS ROOT").child(LOGIN_UID);
//        dbRef = database.getReference("ADMINS ROOT");

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference(LOGIN_UID);
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                dbRef.child(LOGIN_UID).setValue(data);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        dbRef.setValue(data);

    }
    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setDoctorStatusData_in_REALTIME_DB();
                set_Updated_status_in_firestore();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
    }

    public void getDataFromDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        CollectionReference CR = db.collection("ADMINS").document(EMAIL).collection(EMAIL);
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("passwordHint").toString(), Toast.LENGTH_SHORT).show();
                NAME = documentSnapshot.get("name").toString();
                AGE = documentSnapshot.get("age").toString();
                PASSWORD_HINT = documentSnapshot.get("passwordHint").toString();
                GENDER =  documentSnapshot.get("gender").toString();
                SPECIALIZATION = documentSnapshot.get("doctorSpeci").toString();
                ABOUT =documentSnapshot.get("aboutYourSelf").toString();
                LOGIN_UID = documentSnapshot.get("login_uid").toString();
                STATUS = documentSnapshot.get("status").toString();
//                        getProfileData(name,age,passwordHint,gender,ds,about,luid,status);
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("name").toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getProfileData(String name,String age,String passwordHint,String gender,String doctorSpeci,String aboutYourSelf,String luid,String status){
        NAME = name;
        AGE =age;
        PASSWORD_HINT = passwordHint;
        GENDER = gender;
        SPECIALIZATION = doctorSpeci;
        ABOUT =aboutYourSelf;
        LOGIN_UID = luid;
        STATUS = status;
    }

    public int set_spinner_option(String STATUS){
        if(STATUS.equals("Available"))
            return 0;
        else if (STATUS.equals("Holiday")) {
            return 1;
        }else {
            return 2;
        }
    }

    public void set_Updated_status_in_firestore(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        DR.update("status",STATUS).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(getContext().getApplicationContext(), "Status Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}