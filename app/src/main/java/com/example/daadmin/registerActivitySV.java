package com.example.daadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import com.google.firebase.database.FirebaseDatabase;
public class registerActivitySV extends AppCompatActivity {
    String Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,LOGIN_UID;
    String JoinedDS;
    ArrayList<String> DoctorSpecilization;

    Button registerRegisterBTN;

//    EditText checkBoxTextArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sv);
//        checkBoxTextArea.setVisibility(View.INVISIBLE);

        registerRegisterBTN = findViewById(R.id.registerRegisterBTN);
        registerRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_admin_register_filled_data();
                JoinedDS = String.join(", ",DoctorSpecilization);
                emailPasswordCreateAuth(Email,Password);
//                createDataCollection(Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,JoinedDS,LOGIN_UID);
//                Toast.makeText(registerActivitySV.this, DoctorSpecilization.get(6), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void get_admin_register_filled_data() {
        EditText adminName, adminAge, adminEmail, adminPassword, adminPasswordHint, adminAboutYourSelf,checkBoxTextArea;
        RadioGroup adminRadioGroup;
        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;

        adminName = findViewById(R.id.adminName);
        adminAge = findViewById(R.id.adminAge);
        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        adminPasswordHint = findViewById(R.id.adminPasswordHint);
        checkBoxTextArea = findViewById(R.id.checkBoxTextArea);
        adminAboutYourSelf = findViewById(R.id.adminAboutYourSelf);
//        Gender
        adminRadioGroup = findViewById(R.id.adminRadioGroup);
        String adminGender = ((RadioButton)findViewById(adminRadioGroup.getCheckedRadioButtonId())).getText().toString();
//        CheckBox
        DoctorSpecilization = new ArrayList<String>();
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        if (checkBox1.isChecked()){
            DoctorSpecilization.add(checkBox1.getText().toString());
        }
        if (checkBox2.isChecked()){
           DoctorSpecilization.add(checkBox2.getText().toString());
        }
        if (checkBox3.isChecked()){
           DoctorSpecilization.add(checkBox3.getText().toString());
        }
        if (checkBox4.isChecked()){
           DoctorSpecilization.add(checkBox4.getText().toString());
        }
        if (checkBox5.isChecked()){
           DoctorSpecilization.add(checkBox5.getText().toString());
        }
        if (checkBox6.isChecked()){
           DoctorSpecilization.add(checkBox6.getText().toString());
        }
        if (checkBox7.isChecked()){
                DoctorSpecilization.add(checkBoxTextArea.getText().toString());
        }

        Name = adminName.getText().toString();
        Age = adminAge.getText().toString();
        Email = adminEmail.getText().toString();
        Password = adminPassword.getText().toString();
        PasswordHint = adminPasswordHint.getText().toString();
        Gender = adminGender;
        aboutYourSelf = adminAboutYourSelf.getText().toString();
    }

    public void emailPasswordCreateAuth(String email,String password){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        LOGIN_UID = mAuth.getCurrentUser().getUid();
                        if (task.isSuccessful()){
//                            String x = user.getUid().toString();
//                            LOGIN_UID = (mAuth.getCurrentUser().getUid());
                            createDataCollection(Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,JoinedDS,LOGIN_UID);

                        }else {
                            Toast.makeText(registerActivitySV.this, "Y Failed To Cretae User Admin", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void createDataCollection(String name,String age, String email, String password,String passwordHint,String gender,String aboutYourSelf,String DS,String login_uid){
//        emailPasswordCreateAuth(email,password);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String firestore_uid = db.collection("ADMINS").document().collection(name).document().getId();
        String admin_firestore_id = db.collection("ADMINS").document().getId();

        adminUserModel user = new adminUserModel(name,age,email,password,passwordHint,gender,aboutYourSelf,DS,firestore_uid,login_uid,admin_firestore_id);

        db.collection("ADMINS")
                .document(email).collection(email).document(email)
                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
//                            Toast.makeText(registerActivitySV.this, "DONME", Toast.LENGTH_SHORT).show();
                            goAdminLogin();
                        }else {
                            Toast.makeText(registerActivitySV.this, "X Failed To Create Admin User", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void goAdminLogin(){
        Intent AdminPanelIntent = new Intent(registerActivitySV.this, AdminLoginActivity.class);
        startActivity(AdminPanelIntent);
    }
}