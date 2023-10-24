package com.example.daadmin;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    String NAME,AGE,EMAIL,PASSWORD,PASSWORD_HINT,GENDER, SPECIALIZATION,ABOUT;
    ArrayList<adminUserModel> allDataList;
    ArrayList<String> SingleDataList;

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
        progressBar(view,true);
        delayed(view);

        // Inflate the layout for this fragment
        return view;
    }

    public void getBundleData(){
        if (getArguments()!=null){
            EMAIL = getArguments().getString("EMAILX");
            PASSWORD = getArguments().getString("PASSWORDX");
        }
    }
    public void setProfileData(View view){
        EditText adminDoctorProfile_name,adminDoctorProfile_age,adminDoctorProfile_email,adminDoctorProfile_password,adminDoctorProfile_password_hint,adminDoctorProfile_gender,adminDoctorProfile_specilization,adminDoctorProfile_about;

        adminDoctorProfile_name = view.findViewById(R.id.adminDoctorProfile_name);
        adminDoctorProfile_age = view.findViewById(R.id.adminDoctorProfile_age);
        adminDoctorProfile_email = view.findViewById(R.id.adminDoctorProfile_email);
        adminDoctorProfile_password = view.findViewById(R.id.adminDoctorProfile_password);
        adminDoctorProfile_password_hint = view.findViewById(R.id.adminDoctorProfile_password_hint);
        adminDoctorProfile_gender = view.findViewById(R.id.adminDoctorProfile_gender);
        adminDoctorProfile_specilization = view.findViewById(R.id.adminDoctorProfile_specilization);
        adminDoctorProfile_about = view.findViewById(R.id.adminDoctorProfile_about);


        adminDoctorProfile_name.setHint(NAME);
        adminDoctorProfile_age.setHint(AGE);
        adminDoctorProfile_email.setHint(EMAIL);
        adminDoctorProfile_password.setHint(PASSWORD);
        adminDoctorProfile_password_hint.setHint(PASSWORD_HINT);
        adminDoctorProfile_gender.setHint(GENDER);
        adminDoctorProfile_specilization.setHint(SPECIALIZATION);
        adminDoctorProfile_about.setHint(ABOUT);
        progressBar(view,false);

    }

    public void editProfile(View view){
        EditText adminDoctorProfile_name,adminDoctorProfile_age,adminDoctorProfile_email,adminDoctorProfile_password,adminDoctorProfile_password_hint,adminDoctorProfile_gender,adminDoctorProfile_specilization,adminDoctorProfile_about;

        Button adminDoctorProfile_updaterProfile;
        Button adminDoctorProfile_edit;
        adminDoctorProfile_updaterProfile = view.findViewById(R.id.adminDoctorProfile_updaterProfile);
        adminDoctorProfile_edit = view.findViewById(R.id.adminDoctorProfile_edit);

        adminDoctorProfile_name = view.findViewById(R.id.adminDoctorProfile_name);
        adminDoctorProfile_age = view.findViewById(R.id.adminDoctorProfile_age);
        adminDoctorProfile_email = view.findViewById(R.id.adminDoctorProfile_email);
        adminDoctorProfile_password = view.findViewById(R.id.adminDoctorProfile_password);
        adminDoctorProfile_password_hint = view.findViewById(R.id.adminDoctorProfile_password_hint);
        adminDoctorProfile_gender = view.findViewById(R.id.adminDoctorProfile_gender);
        adminDoctorProfile_specilization = view.findViewById(R.id.adminDoctorProfile_specilization);
        adminDoctorProfile_about = view.findViewById(R.id.adminDoctorProfile_about);

            adminDoctorProfile_name.setEnabled(true);
            adminDoctorProfile_age.setEnabled(true);
            adminDoctorProfile_email.setEnabled(true);
            adminDoctorProfile_password.setEnabled(true);
            adminDoctorProfile_password_hint.setEnabled(true);
            adminDoctorProfile_gender.setEnabled(true);
            adminDoctorProfile_specilization.setEnabled(true);
            adminDoctorProfile_about.setEnabled(true);

        adminDoctorProfile_edit.setVisibility(View.GONE);
        adminDoctorProfile_updaterProfile.setVisibility(View.VISIBLE);
        Toast.makeText(getContext().getApplicationContext(), "Start Editing", Toast.LENGTH_SHORT).show();

//            adminDoctorProfile_edit.setClickable(true);


    }

    public void updateProfile(View view){

        EditText adminDoctorProfile_name,adminDoctorProfile_age,adminDoctorProfile_email,adminDoctorProfile_password,adminDoctorProfile_password_hint,adminDoctorProfile_gender,adminDoctorProfile_specilization,adminDoctorProfile_about;
        Button adminDoctorProfile_updaterProfile;
        Button adminDoctorProfile_edit;

        adminDoctorProfile_name = view.findViewById(R.id.adminDoctorProfile_name);
        adminDoctorProfile_age = view.findViewById(R.id.adminDoctorProfile_age);
        adminDoctorProfile_email = view.findViewById(R.id.adminDoctorProfile_email);
        adminDoctorProfile_password = view.findViewById(R.id.adminDoctorProfile_password);
        adminDoctorProfile_password_hint = view.findViewById(R.id.adminDoctorProfile_password_hint);
        adminDoctorProfile_gender = view.findViewById(R.id.adminDoctorProfile_gender);
        adminDoctorProfile_specilization = view.findViewById(R.id.adminDoctorProfile_specilization);
        adminDoctorProfile_about = view.findViewById(R.id.adminDoctorProfile_about);

        adminDoctorProfile_name.setEnabled(false);
        adminDoctorProfile_age.setEnabled(false);
        adminDoctorProfile_email.setEnabled(false);
        adminDoctorProfile_password.setEnabled(false);
        adminDoctorProfile_password_hint.setEnabled(false);
        adminDoctorProfile_gender.setEnabled(false);
        adminDoctorProfile_specilization.setEnabled(false);
        adminDoctorProfile_about.setEnabled(false);


        adminDoctorProfile_updaterProfile = view.findViewById(R.id.adminDoctorProfile_updaterProfile);
        adminDoctorProfile_edit = view.findViewById(R.id.adminDoctorProfile_edit);
        adminDoctorProfile_edit.setVisibility(View.VISIBLE);
        adminDoctorProfile_updaterProfile.setVisibility(View.GONE);

        Toast.makeText(getContext().getApplicationContext(), "Profile Got Updated", Toast.LENGTH_SHORT).show();


    }

    public void getDataFromDB(){
        allDataList = new ArrayList<>();
        SingleDataList = new ArrayList<>();
        SingleDataList.clear();
        allDataList.clear();

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
                        about =documentSnapshot.get("aboutYourSelf").toString();
                getProfileData(name,age,passwordHint,gender,ds,about);
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("name").toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getProfileData(String name,String age,String passwordHint,String gender,String doctorSpeci,String aboutYourSelf){
        NAME = name;
        AGE =age;
        PASSWORD_HINT = passwordHint;
        GENDER = gender;
        SPECIALIZATION = doctorSpeci;
        ABOUT =aboutYourSelf;
    }

    public void delayed(View view){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setProfileData(view);
            }
        };

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,3000);
    }

    public void progressBar(View view,boolean flag){
        ProgressBar progressBar;
        progressBar = view.findViewById(R.id.progressBar);
        if (flag)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

}