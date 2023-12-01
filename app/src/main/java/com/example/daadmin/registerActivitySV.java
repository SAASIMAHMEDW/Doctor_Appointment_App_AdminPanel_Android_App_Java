package com.example.daadmin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class registerActivitySV extends AppCompatActivity {
    String Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,LOGIN_UID,PROFILE_PIC_URL,PHONE_NO;
    String JoinedDS;
    ArrayList<String> DoctorSpecilization;

    Button registerRegisterBTN;

    CircleImageView register_doctor_profile_pic;

    EditText adminName, adminAge, adminEmail, adminPassword, adminPasswordHint, adminAboutYourSelf,checkBoxTextArea,doctor_phone_no;
    RadioGroup adminRadioGroup;
    RadioButton adminRadioButton_Male,adminRadioButton_Female,adminRadioButton_Other;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    String adminGender;

    Uri PROFILE_PIC_URL_URI;
    ActivityResultLauncher<String> launcher;


//    EditText checkBoxTextArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sv);
        initializer();
        checkBoxTextArea.setVisibility(View.INVISIBLE);
        image_activity_result_launcher();
//
//        checkBox7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    checkBoxTextArea.setVisibility(View.VISIBLE);
//            }
//        });

        checkBox7.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            // Perform logic
            if (isChecked) checkBoxTextArea.setVisibility(View.VISIBLE);
            else checkBoxTextArea.setVisibility(View.INVISIBLE);

        });

        registerRegisterBTN.setOnClickListener(v -> {
            get_admin_register_filled_data();
            JoinedDS = String.join(", ",DoctorSpecilization);
            toggleFieldsVisibility(false);
            emailPasswordCreateAuth(Email,Password);
            progressBar(true);

//                createDataCollection(Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,JoinedDS,LOGIN_UID);
//                Toast.makeText(registerActivitySV.this, DoctorSpecilization.get(6), Toast.LENGTH_SHORT).show();
//            upload_profile_pic_firestore_storage();
        });

        register_doctor_profile_pic.setOnClickListener(v -> {
            try {
            handle_upload_button();
            }catch (Exception e){
                Toast.makeText(registerActivitySV.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        doctor_phone_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (doctor_phone_no.getText().toString().length()<10)
                        doctor_phone_no.setError("Phone no. must have 10 digits");
                }

            }
        });
        doctor_phone_no.addTextChangedListener(new TextWatcher() {
            String phone_no;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (doctor_phone_no.getText().toString().length()==10){
                    phone_no = doctor_phone_no.getText().toString();
                    doctor_phone_no.setError(null);
                }
                if (doctor_phone_no.getText().toString().length()>10){
                    doctor_phone_no.setError("Phone no. must be 10 digit only");
                    doctor_phone_no.setText(phone_no);
                    doctor_phone_no.setSelection(phone_no.length());
                }
//                Toast.makeText(registerActivitySV.this, phone_no, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void mainX(){

    }

    public void toggleFieldsVisibility(boolean flag){
        if (flag){
            adminName.setEnabled(flag);
            adminAge.setEnabled(flag);
            doctor_phone_no.setEnabled(flag);
            adminEmail.setEnabled(flag);
            adminPassword.setEnabled(flag);
            adminPasswordHint.setEnabled(flag);
            adminRadioButton_Male.setEnabled(flag);
            adminRadioButton_Female.setEnabled(flag);
            adminRadioButton_Other.setEnabled(flag);
            adminAboutYourSelf.setEnabled(flag);
            register_doctor_profile_pic.setEnabled(flag);
            checkBox1.setEnabled(flag);
            checkBox2.setEnabled(flag);
            checkBox3.setEnabled(flag);
            checkBox4.setEnabled(flag);
            checkBox5.setEnabled(flag);
            checkBox6.setEnabled(flag);
            checkBox7.setEnabled(flag);
            registerRegisterBTN.setEnabled(flag);
        }else {
            adminName.setEnabled(flag);
            adminAge.setEnabled(flag);
            doctor_phone_no.setEnabled(flag);
            adminEmail.setEnabled(flag);
            adminPassword.setEnabled(flag);
            adminPasswordHint.setEnabled(flag);
            adminRadioButton_Male.setEnabled(flag);
            adminRadioButton_Female.setEnabled(flag);
            adminRadioButton_Other.setEnabled(flag);
            adminAboutYourSelf.setEnabled(flag);
            register_doctor_profile_pic.setEnabled(flag);
            checkBox1.setEnabled(flag);
            checkBox2.setEnabled(flag);
            checkBox3.setEnabled(flag);
            checkBox4.setEnabled(flag);
            checkBox5.setEnabled(flag);
            checkBox6.setEnabled(flag);
            checkBox7.setEnabled(flag);
            registerRegisterBTN.setEnabled(flag);
        }
    }

    public void initializer(){
        registerRegisterBTN = findViewById(R.id.registerRegisterBTN);
        adminName = findViewById(R.id.adminName);
        adminAge = findViewById(R.id.adminAge);
        adminEmail = findViewById(R.id.adminEmail);
        adminPassword = findViewById(R.id.adminPassword);
        adminPasswordHint = findViewById(R.id.adminPasswordHint);
        checkBoxTextArea = findViewById(R.id.checkBoxTextArea);
        adminAboutYourSelf = findViewById(R.id.adminAboutYourSelf);
        adminRadioGroup = findViewById(R.id.adminRadioGroup);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        register_doctor_profile_pic = findViewById(R.id.register_doctor_profile_pic);
        doctor_phone_no = findViewById(R.id.doctor_phone_no);
        adminRadioButton_Male = findViewById(R.id.adminRadioButton_Male);
        adminRadioButton_Female = findViewById(R.id.adminRadioButton_Female);
        adminRadioButton_Other = findViewById(R.id.adminRadioButton_Other);
    }

    public void image_activity_result_launcher() {
            launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            PROFILE_PIC_URL_URI = result;
            register_doctor_profile_pic.setImageURI(result);
            PROFILE_PIC_URL = result.toString();
//                Toast.makeText(this, PROFILE_PIC_URL_URI.toString(), Toast.LENGTH_LONG).show();
        });
    }

    public void handle_upload_button() {
        launcher.launch("image/*");
    }

    public void upload_profile_pic_firestore_storage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("IMAGES").child("(USERS) DOCTORS").child("USERS PROFILE PICTURES").child(Email);
        storageReference.putFile(PROFILE_PIC_URL_URI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        PROFILE_PIC_URL = uri.toString();
                        firestore_delayed();
                    }
                });
            }
        });

    }

    public void firestore_delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                createDataCollection(Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,JoinedDS,LOGIN_UID);
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,3000);
    }

    public void get_admin_register_filled_data() {
        //        Gender
        try {
        adminGender = ((RadioButton)findViewById(adminRadioGroup.getCheckedRadioButtonId())).getText().toString();
        }catch (Exception e){
            adminGender = "null";
        }
//        CheckBox
        DoctorSpecilization = new ArrayList<>();

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
        PHONE_NO = doctor_phone_no.getText().toString();
    }

    public void clear_field(){
        adminName.setText("");
        adminAge.setText("");
        adminEmail.setText("");
        adminPassword.setText("");
        adminPasswordHint.setText("");
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
                            upload_profile_pic_firestore_storage();
//                            firestore_delayed();
//                            createDataCollection(Name,Age,Email,Password,PasswordHint,Gender,aboutYourSelf,JoinedDS,LOGIN_UID);

                        }else {
                            toggleFieldsVisibility(true);
                            Toast.makeText(registerActivitySV.this, "Y Failed To Create User doctor", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void createDataCollection(String name,String age, String email, String password,String passwordHint,String gender,String aboutYourSelf,String DS,String login_uid){
//        emailPasswordCreateAuth(email,password);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String firestore_uid = db.collection("ADMINS").document().collection(name).document().getId();
        String admin_firestore_id = db.collection("ADMINS").document().getId();

        adminUserModel user = new adminUserModel(name,age,email,password,passwordHint,gender,aboutYourSelf,DS,firestore_uid,login_uid,admin_firestore_id,"Available",PROFILE_PIC_URL,PHONE_NO);

        db.collection("ADMINS")
                .document(email).collection(email).document("PROFILE")
                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressBar(false);
                            clear_field();
                            delayed();

                        }else {
                            Toast.makeText(registerActivitySV.this, "X Failed To Create Admin User", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    public void goAdminLogin(){
        Intent AdminPanelIntent = new Intent(registerActivitySV.this, AdminLoginActivity.class);
        startActivity(AdminPanelIntent);
        finish();
    }

    public void progressBar(boolean flag){
        ProgressBar progressBar;
        progressBar = findViewById(R.id.progressBar);
        if (flag)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.GONE);

    }

    public void delayed(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                goAdminLogin();
                Toast.makeText(registerActivitySV.this, "Your Account Successfully Created", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext().getApplicationContext(), LOGIN_UID, Toast.LENGTH_SHORT).show();
            }
        };
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable,1500);
    }
}