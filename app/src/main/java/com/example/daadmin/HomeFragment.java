package com.example.daadmin;

import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public String EMAILX;
    RecyclerView homeRecylerView;
    CardView no_patients_yet_card;
    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER, SPECIALIZATION,ABOUT,STATUS,LOGIN_UID;
    String temp_status;
    ArrayList<homeCardRecycler> card = new ArrayList<>();

    ArrayList<homeCardRecycler> allDataList;
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
        find_views_by_id(view);
        get_patients_delayed();
//        get_doctor_status_From_DB();
        get_doctor_status_From_DB_thread();
//        getDataFromDB();
        getDataFromDB_thread();
        setHomeRecylerView_delayed(view);
//        handle_finished_btn(view);
//        card_model_delayed();
//        recyler_view_delayed(view);
        handle_task_thread_spinner(view);
//        spinner_delayed(view);
//        spinner_doctor_status(view);
        delayed();
        return view;
    }

    public void get_doctor_status_From_DB_thread() {
        Thread thread = new Thread(() -> {
            try {
                get_doctor_status_From_DB();
            } catch (Exception ignored){}
        });thread.start();
    }
    public void get_doctor_status_From_DB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                temp_status = documentSnapshot.get("status").toString();
            }
        });
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
        handler.postDelayed(runnable,7000);
    }

    public void SpinnerOptions(View view){
        Spinner spinner_doctor_status;
        ArrayList<String> doctorStatus = new ArrayList<>();
        spinner_doctor_status = view.findViewById(R.id.spinner_doctor_status);
        doctorStatus.add("Available");
        doctorStatus.add("Holiday");
        doctorStatus.add("Busy");
        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getContext().getApplicationContext(), android.R.layout.simple_list_item_activated_1,doctorStatus);
        spinner_doctor_status.setBackgroundColor(getResources().getColor(R.color.AppPrimaryColorX));
        spinner_doctor_status.setDropDownWidth(500);
        spinner_doctor_status.setMinimumHeight(200);
        spinner_doctor_status.setElevation(50f);
        spinner_doctor_status.setAdapter(spinnerAdaptor);
        spinner_doctor_status.setSelection(set_spinner_option(STATUS));
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

    public void handle_task_thread_spinner(View view){
        Thread thread = new Thread(() -> {
            try {
                sleep(2000);
            }catch (Exception e){}
            finally { spinner_delayed(view); }
        });thread.start();
    }

    public void handle_finished_btn(View view){
        Button doctor_patients_finished_btn;
        TextView home_recycle_view_email;
        home_recycle_view_email = view.findViewById(R.id.home_recycle_view_email);
        doctor_patients_finished_btn = view.findViewById(R.id.doctor_patients_finished_btn);
        doctor_patients_finished_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = home_recycle_view_email.getText().toString();
                Toast.makeText(getContext().getApplicationContext(), email, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setHomeRecylerView(View view){
    if (allDataList.size()==0){
        homeRecylerView.setVisibility(View.GONE);
        no_patients_yet_card.setVisibility(View.VISIBLE);
    }else {
        homeRecylerView.setVisibility(View.VISIBLE);
        no_patients_yet_card.setVisibility(View.GONE);
        card_model_delayed();
        recyler_view_delayed(view);
    }
}

    public void setHomeRecylerView_delayed(View view){
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setHomeRecylerView(view);
        }
    };
    Handler handler = new Handler(Looper.getMainLooper());
    handler.postDelayed(runnable,1600);
}
    public void find_views_by_id(View view){
    no_patients_yet_card = view.findViewById(R.id.no_patients_yet_card);
    homeRecylerView = view.findViewById(R.id.homeRecylerView);

}
    public void recyler_view(View view){
            RecyclerView recylerView = view.findViewById(R.id.homeRecylerView);
//        cardModel();
            homeCardRecylerViewAdapter adapter = new homeCardRecylerViewAdapter((getActivity().getApplicationContext()),card);
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
        handler.postDelayed(runnable,3200);
    }
    public void card_model_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                cardModel();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2300);
    }

    public void get_patients_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                get_patient_book();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1300);
    }


    public void cardModel(){

        ArrayList<String> NAMES = new ArrayList<>();
        ArrayList<String> PROBLEMS = new ArrayList<>();
        ArrayList<String> EMAILS = new ArrayList<>();
        int count_handling=0;
        for (int i=0; i<allDataList.size(); i++){
            if((allDataList.get(i).status).equals("handling")){
                NAMES.add(allDataList.get(i).name.toString());
                PROBLEMS.add(allDataList.get(i).problem.toString());
                EMAILS.add(allDataList.get(i).email.toString());
            }
        }
//
//        for (int i = 0; i < NAMES.size(); i++){
//            card.add(new homeCardRecycler(NAMES.get(i),PROBLEMS.get(i),EMAILS.get(i),EMAIL));
//        }

//        String Names[] = {"AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle","AASIm","Ahmed","Oracle"};
//        String Problem[] = {"cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz","cough","fever","xyz"};
        if (NAMES.size()==0){
            homeRecylerView.setVisibility(View.GONE);
            no_patients_yet_card.setVisibility(View.VISIBLE);
        }else {
            for (int i = 0; i < NAMES.size(); i++){
                card.add(new homeCardRecycler(NAMES.get(i),PROBLEMS.get(i),EMAILS.get(i),EMAIL));
            }
        }

    }


    public void get_patient_book(){
        allDataList = new ArrayList<>();
        allDataList.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference CR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document(EMAIL).collection("MY HANDLING PATIENTS BOOK");
        CR.addSnapshotListener((value, error) -> {
            if (error == null){
                List<homeCardRecycler> data = value.toObjects(homeCardRecycler.class);
                allDataList.addAll(data);
            }
        });
    }

    public void getBundleData(){
        if (getArguments()!=null){
            EMAIL = getArguments().getString("EMAILX");
            EMAILX =getArguments().getString("EMAILX");
            PASSWORD = getArguments().getString("PASSWORDX");
        }
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

    public void setDoctorStatusData_in_REALTIME_DB(){
        doctorDataModel data = new doctorDataModel(NAME,EMAIL,PASSWORD,LOGIN_UID,STATUS);
        FirebaseDatabase database;
        DatabaseReference dbRef;
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("ADMINS ROOT").child(LOGIN_UID);
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
        handler.postDelayed(runnable,5000);
    }
public void getDataFromDB_thread(){
        Thread thread = new Thread(){
            public void run(){
                getDataFromDB();
            }

        };thread.start();
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