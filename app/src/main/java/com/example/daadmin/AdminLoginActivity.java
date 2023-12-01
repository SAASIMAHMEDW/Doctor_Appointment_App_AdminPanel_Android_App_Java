package com.example.daadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdminLoginActivity extends AppCompatActivity {

    String EMAIL,PASSWORD,NAME,GENDER,LOGIN_UID,PROFILE_PIC_URL,STATUS,PHONE_NO;
    Button adminLoginBTN;
    CheckBox show_hide_password_checkbox;
    ArrayList<adminUserModel> allDataList;
    EditText adminLoginEmail,adminLoginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adminLoginEmail = findViewById(R.id.adminLoginEmail);
        adminLoginPassword = findViewById(R.id.adminLoginPassword);
        adminLoginBTN = findViewById(R.id.adminLoginBTN);
        show_hide_password_checkbox = findViewById(R.id.show_hide_password_checkbox);

        show_hide_password_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    if (adminLoginPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD )
//                        adminLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                    adminLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    else adminLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_hide_password_checkbox.setText("Hide Password");
                    adminLoginPassword.setSelection(adminLoginPassword.length());
                }else {
                    adminLoginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    if (adminLoginPassword.getInputType() == InputType.TYPE_CLASS_TEXT )
//                        adminLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show_hide_password_checkbox.setText("Show Password");
                    adminLoginPassword.setSelection(adminLoginPassword.length());
                }
            }
        });

        adminLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLoginEmail.setEnabled(false);
                adminLoginPassword.setEnabled(false);
                show_hide_password_checkbox.setEnabled(false);
                adminLoginBTN.setEnabled(false);
                get_admin_login_filled_data();
                progressBar(true);
                if (EMAIL.length()==0){
                    adminLoginEmail.setEnabled(true);
                    adminLoginPassword.setEnabled(true);
                    show_hide_password_checkbox.setEnabled(true);
                    adminLoginBTN.setEnabled(true);
                    progressBar(false);
                    adminLoginEmail.setError("ENTER EMAIL");
                } else if (PASSWORD.length()==0) {
                    adminLoginEmail.setEnabled(true);
                    adminLoginPassword.setEnabled(true);
                    show_hide_password_checkbox.setEnabled(true);
                    adminLoginBTN.setEnabled(true);
                    progressBar(false);
                    adminLoginPassword.setError("ENTER PASSWORD");
                }else delayed();
//                Toast.makeText(AdminLoginActivity.this, EMAIL, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Login(EMAIL,PASSWORD);
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1000);
    }

    public void get_admin_login_filled_data(){
        EMAIL = adminLoginEmail.getText().toString();
        PASSWORD = adminLoginPassword.getText().toString();

    }

    public void clear_admin_login_filled_data(){
        EditText adminLoginEmail,adminLoginPassword;
        adminLoginEmail = findViewById(R.id.adminLoginEmail);
        adminLoginPassword = findViewById(R.id.adminLoginPassword);
        adminLoginEmail.setText("");
        adminLoginPassword.setText("");

    }

    public void Login(String email,String password){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar(false);
                            Toast.makeText(AdminLoginActivity.this, "successfully Login", Toast.LENGTH_SHORT).show();
                            storeLoginStats();
                            getDataFromDB();
                            new Thread(()->storeDoctorData_In_RealtimeDB_delayed()).start();
//                            storeDoctorData_In_RealtimeDB_delayed();
                            delayed_admin_panel();
                        }else {
                            adminLoginEmail.setEnabled(true);
                            show_hide_password_checkbox.setEnabled(true);
                            adminLoginPassword.setEnabled(true);
                            adminLoginBTN.setEnabled(true);
                            Toast.makeText(AdminLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            progressBar(false);

                        }
                    }

                });
    }


    public void goAdminPanel(){
        Intent AdminPanelIntent = new Intent(AdminLoginActivity.this, AdminPanelActivity.class);
        AdminPanelIntent.putExtra("EMAILX",EMAIL);
        AdminPanelIntent.putExtra("PASSWORDX",PASSWORD);
        startActivity(AdminPanelIntent);
        finish();
    }
    public void delayed_admin_panel(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                clear_admin_login_filled_data();
                goAdminPanel();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
    }
    public void progressBar(boolean flag){
        ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBar);
        if (flag)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    public void storeLoginStats(){
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.daadmin_user_login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SP_EMAIL",EMAIL);
        editor.putString("SP_PASSWORD",PASSWORD);
        editor.putBoolean("SP_LOGIN_FLAG",true);
        editor.apply();

    }

    public void getDataFromDB(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference DR = db.collection("ADMINS").document(EMAIL).collection(EMAIL).document("PROFILE");
        DR.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("passwordHint").toString(), Toast.LENGTH_SHORT).show();
                NAME = documentSnapshot.get("name").toString();
                GENDER =  documentSnapshot.get("gender").toString();
                PROFILE_PIC_URL = documentSnapshot.get("profile_url").toString();
                LOGIN_UID = documentSnapshot.get("login_uid").toString();
                STATUS = documentSnapshot.get("status").toString();
                PHONE_NO = documentSnapshot.get("phone_no").toString();
//                        getProfileData(name,age,passwordHint,gender,ds,about,luid,status);
//                Toast.makeText(getContext().getApplicationContext(), documentSnapshot.get("name").toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void storeDoctorData_In_RealtimeDB(){
            StoreDoctorDataRealtimeDBModel data = new StoreDoctorDataRealtimeDBModel(NAME,EMAIL,GENDER,STATUS,LOGIN_UID,PROFILE_PIC_URL,PHONE_NO);
            FirebaseDatabase database;
            DatabaseReference dbRef;
            database = FirebaseDatabase.getInstance();
            dbRef = database.getReference("ADMINS ROOT").child(LOGIN_UID);
            dbRef.setValue(data);
    }

    public void storeDoctorData_In_RealtimeDB_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                storeDoctorData_In_RealtimeDB();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,2000);
    }



}