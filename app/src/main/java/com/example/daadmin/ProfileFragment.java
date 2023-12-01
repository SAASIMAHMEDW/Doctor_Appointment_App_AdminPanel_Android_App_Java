package com.example.daadmin;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER, SPECIALIZATION,ABOUT,STATUS,PROFILE_PIC,PHONE_NUMBER,LOGIN_UID;
    String UPDATED_NAME,UPDATED_AGE,UPDATED_EMAIL,UPDATED_PASSWORD,UPDATED_PASSWORD_HINT,UPDATED_GENDER, UPDATED_SPECIALIZATION,UPDATED_ABOUT,UPDATED_STATUS,UPDATED_PHONE_NUMBER;

    ArrayList<adminUserModel> allDataList;
    ArrayList<String> SingleDataList;

    EditText adminDoctorProfile_name,adminDoctorProfile_age,adminDoctorProfile_email,adminDoctorProfile_password,adminDoctorProfile_password_hint,adminDoctorProfile_gender,adminDoctorProfile_specilization,adminDoctorProfile_about,adminDoctorProfile_status,adminDoctorProfile_phoneNo;
    Button adminDoctorProfile_updaterProfile;
    Button adminDoctorProfile_edit,adminDoctorProfile_logout_btn;
    ImageView profile_fragment_profile_pic;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(String email,String password){

        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("EMAILX",email);
        bundle.putString("PASSWORDX",password);

        profileFragment.setArguments(bundle);

        return profileFragment;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        getBundleData();
        get_find_view_by_id_data(view);
        getDataFromDB();


        Button adminDoctorProfile_edit,adminDoctorProfile_updaterProfile;
        adminDoctorProfile_updaterProfile = view.findViewById(R.id.adminDoctorProfile_updaterProfile);
        adminDoctorProfile_edit = view.findViewById(R.id.adminDoctorProfile_edit);
        adminDoctorProfile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile(view);
            }
        });

        adminDoctorProfile_updaterProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(view);
            }
        });
        adminDoctorProfile_logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout(v);
            }
        });
        progressBar(view,true);
        delayed(view);

        // Inflate the layout for this fragment
        return view;
    }

    public void handleLogout(View v){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("com.example.daadmin_user_login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(v.getContext().getApplicationContext(), AdminLoginActivity.class);
        startActivity(intent);
        getActivity().finishAffinity();
    }


    public void getBundleData(){
        if (getArguments()!=null){
            EMAIL = getArguments().getString("EMAILX");
            PASSWORD = getArguments().getString("PASSWORDX");
        }
    }
    public void setProfileData(View view){
        adminDoctorProfile_name.setHint(NAME);
        adminDoctorProfile_age.setHint(AGE);
        adminDoctorProfile_phoneNo.setHint(PHONE_NUMBER);
        adminDoctorProfile_email.setHint(EMAIL);
        adminDoctorProfile_password.setHint(PASSWORD);
        adminDoctorProfile_password_hint.setHint(PASSWORD_HINT);
        adminDoctorProfile_gender.setHint(GENDER);
        adminDoctorProfile_specilization.setHint(SPECIALIZATION);
        adminDoctorProfile_about.setHint(ABOUT);
        adminDoctorProfile_status.setHint(STATUS);
        progressBar(view,false);

    }

    public void editProfile(View view){
            adminDoctorProfile_name.setEnabled(true);
            adminDoctorProfile_age.setEnabled(true);
//            adminDoctorProfile_email.setEnabled(true);
//            adminDoctorProfile_password.setEnabled(true);
            adminDoctorProfile_password_hint.setEnabled(true);
            adminDoctorProfile_gender.setEnabled(true);
            adminDoctorProfile_specilization.setEnabled(true);
            adminDoctorProfile_about.setEnabled(true);
            adminDoctorProfile_status.setEnabled(true);
            adminDoctorProfile_phoneNo.setEnabled(true);

        adminDoctorProfile_edit.setVisibility(View.GONE);
        adminDoctorProfile_updaterProfile.setVisibility(View.VISIBLE);
        Toast.makeText(getContext().getApplicationContext(), "Start Editing", Toast.LENGTH_SHORT).show();

    }

    public void updateProfile(View view){
        adminDoctorProfile_name.setEnabled(false);
        adminDoctorProfile_age.setEnabled(false);
        adminDoctorProfile_phoneNo.setEnabled(false);
        adminDoctorProfile_email.setEnabled(false);
        adminDoctorProfile_password.setEnabled(false);
        adminDoctorProfile_password_hint.setEnabled(false);
        adminDoctorProfile_gender.setEnabled(false);
        adminDoctorProfile_specilization.setEnabled(false);
        adminDoctorProfile_about.setEnabled(false);
        adminDoctorProfile_status.setEnabled(false);
        adminDoctorProfile_edit.setVisibility(View.VISIBLE);
        adminDoctorProfile_updaterProfile.setVisibility(View.GONE);
        get_Updated_Data();
        adminDoctorProfile_name.setHint(UPDATED_NAME);
        adminDoctorProfile_age.setHint(UPDATED_AGE);
        adminDoctorProfile_phoneNo.setHint(UPDATED_PHONE_NUMBER);
        adminDoctorProfile_email.setHint(UPDATED_EMAIL);
        adminDoctorProfile_password.setHint(UPDATED_PASSWORD);
        adminDoctorProfile_password_hint.setHint(UPDATED_PASSWORD_HINT);
        adminDoctorProfile_gender.setHint(UPDATED_GENDER);
        adminDoctorProfile_specilization.setHint(UPDATED_SPECIALIZATION);
        adminDoctorProfile_about.setHint(UPDATED_ABOUT);
        adminDoctorProfile_status.setHint(UPDATED_STATUS);
        delayed();
//        Toast.makeText(getContext().getApplicationContext(), "Profile Got Updated "+UPDATED_NAME, Toast.LENGTH_SHORT).show();


    }

    public void get_Updated_Data(){
//        NAME
        if ((adminDoctorProfile_name.getText().toString()).length()==0){
            UPDATED_NAME = adminDoctorProfile_name.getHint().toString();
        }else {
            UPDATED_NAME = adminDoctorProfile_name.getText().toString();
        }

//        AGE
        if ((adminDoctorProfile_age.getText().toString()).length()==0){
            UPDATED_AGE = adminDoctorProfile_age.getHint().toString();
        }else {
            UPDATED_AGE = adminDoctorProfile_age.getText().toString();
        }

//        EMAIL
        if ((adminDoctorProfile_email.getText().toString()).length()==0){
            UPDATED_EMAIL = adminDoctorProfile_email.getHint().toString();
        }else {
            UPDATED_EMAIL = adminDoctorProfile_email.getText().toString();
        }

//        PASSWORD
        if ((adminDoctorProfile_password.getText().toString()).length()==0){
            UPDATED_PASSWORD = adminDoctorProfile_password.getHint().toString();
        }else {
            UPDATED_PASSWORD = adminDoctorProfile_password.getText().toString();
        }

//        PASSWORD_HINT
        if ((adminDoctorProfile_password_hint.getText().toString()).length()==0){
            UPDATED_PASSWORD_HINT = adminDoctorProfile_password_hint.getHint().toString();
        }else {
            UPDATED_PASSWORD_HINT = adminDoctorProfile_password_hint.getText().toString();
        }

//        GENDER
        if ((adminDoctorProfile_gender.getText().toString()).length()==0){
            UPDATED_GENDER = adminDoctorProfile_gender.getHint().toString();
        }else {
            UPDATED_GENDER = adminDoctorProfile_gender.getText().toString();
        }

//        SPECIALIZATION
        if ((adminDoctorProfile_specilization.getText().toString()).length()==0){
            UPDATED_SPECIALIZATION = adminDoctorProfile_specilization.getHint().toString();
        }else {
            UPDATED_SPECIALIZATION = adminDoctorProfile_specilization.getText().toString();
        }

//        ABOUT
        if ((adminDoctorProfile_about.getText().toString()).length()==0){
            UPDATED_ABOUT = adminDoctorProfile_about.getHint().toString();
        }else {
            UPDATED_ABOUT = adminDoctorProfile_about.getText().toString();
        }

//        STATUS
        if ((adminDoctorProfile_status.getText().toString()).length()==0){
            UPDATED_STATUS = adminDoctorProfile_status.getHint().toString();
        }else {
            UPDATED_STATUS = adminDoctorProfile_status.getText().toString();
        }
        if ((adminDoctorProfile_phoneNo.getText().toString()).length()==0){
            UPDATED_PHONE_NUMBER = adminDoctorProfile_phoneNo.getHint().toString();
        }else {
            UPDATED_PHONE_NUMBER = adminDoctorProfile_phoneNo.getText().toString();
        }

    }

    public void get_find_view_by_id_data(View view){

        adminDoctorProfile_name = view.findViewById(R.id.adminDoctorProfile_name);
        adminDoctorProfile_age = view.findViewById(R.id.adminDoctorProfile_age);
        adminDoctorProfile_email = view.findViewById(R.id.adminDoctorProfile_email);
        adminDoctorProfile_password = view.findViewById(R.id.adminDoctorProfile_password);
        adminDoctorProfile_password_hint = view.findViewById(R.id.adminDoctorProfile_password_hint);
        adminDoctorProfile_gender = view.findViewById(R.id.adminDoctorProfile_gender);
        adminDoctorProfile_specilization = view.findViewById(R.id.adminDoctorProfile_specilization);
        adminDoctorProfile_about = view.findViewById(R.id.adminDoctorProfile_about);
        adminDoctorProfile_status = view.findViewById(R.id.adminDoctorProfile_status);

        adminDoctorProfile_updaterProfile = view.findViewById(R.id.adminDoctorProfile_updaterProfile);
        adminDoctorProfile_edit = view.findViewById(R.id.adminDoctorProfile_edit);
        adminDoctorProfile_logout_btn = view.findViewById(R.id.adminDoctorProfile_logout_btn);
        profile_fragment_profile_pic = view.findViewById(R.id.profile_fragment_profile_pic);
        adminDoctorProfile_phoneNo = view.findViewById(R.id.adminDoctorProfile_phoneNo);

    }

    public void getDataFromDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        CollectionReference CR = db.collection("ADMINS").document(EMAIL).collection(EMAIL);
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("passwordHint").toString(), Toast.LENGTH_SHORT).show();
                String name = documentSnapshot.get("name").toString(),
                        age = documentSnapshot.get("age").toString(),
                        passwordHint = documentSnapshot.get("passwordHint").toString(),
                        gender =  documentSnapshot.get("gender").toString(),
                        ds = documentSnapshot.get("doctorSpeci").toString(),
                        about =documentSnapshot.get("aboutYourSelf").toString(),
                        status =documentSnapshot.get("status").toString(),
                        profile_pic = documentSnapshot.get("profile_url").toString(),
                        phone_number = documentSnapshot.get("phone_no").toString(),
                        loginUID = documentSnapshot.get("phone_no").toString();
                Picasso.get().load(profile_pic).into(profile_fragment_profile_pic);
                getProfileData(name,age,passwordHint,gender,ds,about,status,profile_pic,phone_number,loginUID);
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("name").toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getProfileData(String name,String age,String passwordHint,String gender,String doctorSpeci,String aboutYourSelf,String status,String profile_pic,String phone_number,String loginUID){
        NAME = name;
        AGE =age;
        PASSWORD_HINT = passwordHint;
        GENDER = gender;
        SPECIALIZATION = doctorSpeci;
        ABOUT =aboutYourSelf;
        STATUS = status;
        PROFILE_PIC = profile_pic;
        PHONE_NUMBER = phone_number;
        LOGIN_UID = loginUID;
    }

    public void delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setProfileData(view);
            }
        };

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
    }

    public void progressBar(View view,boolean flag){
        ProgressBar progressBar;
        progressBar = view.findViewById(R.id.progressBar);
        if (flag)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    public void set_Updated_Data_in_firestore(){
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");

        DR.update("name",UPDATED_NAME, "age",UPDATED_AGE,"email",UPDATED_EMAIL,"password",UPDATED_PASSWORD,"passwordHint",UPDATED_PASSWORD_HINT,"gender",UPDATED_GENDER,"status",UPDATED_STATUS,"doctorSpeci",UPDATED_SPECIALIZATION,"aboutYourSelf",UPDATED_ABOUT,"phone_no",UPDATED_PHONE_NUMBER).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext().getApplicationContext(), "Updated Successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext().getApplicationContext(), "Failed To Update", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setUpdatedPhoneNoInRealtimeDB(){
        FirebaseDatabase database;
        DatabaseReference dbRef;
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("ADMINS ROOT").child(LOGIN_UID);
//        dbRef.updateChildren()
    }


    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                set_Updated_Data_in_firestore();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1000);
    }

}