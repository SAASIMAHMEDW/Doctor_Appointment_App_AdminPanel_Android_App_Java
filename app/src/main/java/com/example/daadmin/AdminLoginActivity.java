package com.example.daadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AdminLoginActivity extends AppCompatActivity {

    String EMAIL,PASSWORD,UID;
    Button adminLoginBTN;
    ArrayList<adminUserModel> allDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminLoginBTN = findViewById(R.id.adminLoginBTN);

        adminLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_admin_login_filled_data();
                Login(EMAIL,PASSWORD);
//                Toast.makeText(AdminLoginActivity.this, EMAIL, Toast.LENGTH_SHORT).show();


            }
        });
    }
//    public void UsereData(){
//        allDataList = new ArrayList<>();
//        allDataList.clear();
//        FirebaseFirestore db;
//        db = FirebaseFirestore.getInstance();
//        db.collection("ADMINS").document().collection(EMAIL).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error == null){
//                    List<adminUserModel> data = value.toObjects(adminUserModel.class);
//                    allDataList.addAll(data);
//                }else {
//                    Toast.makeText(AdminLoginActivity.this, "Failed To Load Data", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

//    public void VerifiedUserData(){
//        if (EMAIL.length()==0 || PASSWORD.length()==0){
//            Toast.makeText(AdminLoginActivity.this, "LoginActivity->Fill Email Password Field", Toast.LENGTH_SHORT).show();
//        }else {
//            for (int i=0; i<allDataList.size(); i++) {
//                String testEmail = (allDataList.get(i).email).toString();
//                String testpassord = (allDataList.get(i).password).toString();
//                if (EMAIL.equals(testEmail) && PASSWORD.equals(testpassord)) {
//                    goAdminPanel();
//                    break;
//                }
//            }
//        }
//    }

    public void get_admin_login_filled_data(){
        EditText adminLoginEmail,adminLoginPassword;
        adminLoginEmail = findViewById(R.id.adminLoginEmail);
        adminLoginPassword = findViewById(R.id.adminLoginPassword);
        EMAIL = adminLoginEmail.getText().toString();
        PASSWORD = adminLoginPassword.getText().toString();

    }

    public void Login(String email,String password){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
//                            Toast.makeText(AdminLoginActivity.this, "successfully Login", Toast.LENGTH_SHORT).show();
                            goAdminPanel();

                        }else {
                            Toast.makeText(AdminLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

//    public void emailPasswordCreateAuth(String email,String password){
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
////                            String x = user.getUid().toString();
//                            String id =mAuth.getCurrentUser().getUid();
//                            UID = id;
//                        }
//
//                    }
//                });
//    }

    public void goAdminPanel(){
        Intent AdminPanelIntent = new Intent(AdminLoginActivity.this, AdminPanelActivity.class);
        AdminPanelIntent.putExtra("EMAILX",EMAIL);
        AdminPanelIntent.putExtra("PASSWORDX",PASSWORD);
        startActivity(AdminPanelIntent);
    }


}