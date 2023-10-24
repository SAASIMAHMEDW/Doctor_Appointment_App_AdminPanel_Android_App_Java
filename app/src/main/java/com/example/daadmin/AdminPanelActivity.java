package com.example.daadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.shape.MaterialShapeDrawable;

import java.util.ArrayList;

public class AdminPanelActivity extends AppCompatActivity {

    String EMAIL,PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        getIntentData();
        bottomNavBar();
    }

    public void SpinnerOptions(){
        Spinner spinner_doctor_status;
        ArrayList<String> doctorStatus = new ArrayList<>();
//        spinner_doctor_status = findViewById(R.id.spinner_doctor_status);
        doctorStatus.add("Available");
        doctorStatus.add("Holiday");
        doctorStatus.add("Op3");
        doctorStatus.add("Op4");
        doctorStatus.add("Op5");

        ArrayAdapter<String> spinnerAdaptor = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,doctorStatus);
//        spinner_doctor_status.setAdapter(spinnerAdaptor);
    }

    public void bottomNavBar(){
        BottomNavigationView adminPanelBottomViewNav;
        adminPanelBottomViewNav = findViewById(R.id.adminPanelBottomViewNav);


        adminPanelBottomViewNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home){
                    fragmentLoader(HomeFragment.getInstance(EMAIL,PASSWORD),true);
                }else if (id == R.id.nav_list){
                    fragmentLoader(new ListFragment(),false);
                }else {
                    fragmentLoader(ProfileFragment.getInstance(EMAIL,PASSWORD),false);
                }

                return true;
            }
        });
        adminPanelBottomViewNav.setSelectedItemId(R.id.nav_home);

    }

    public void fragmentLoader(Fragment fragment, boolean flag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

//        Bundle bundle = new Bundle();
//        bundle.putString("EMAILX",EMAIL);
//        bundle.putString("PASSWORDX",PASSWORD);
//        fragment.setArguments(bundle);

        if (flag){
            ft.add(R.id.adminpanelContainer, fragment);
        }else {
            ft.replace(R.id.adminpanelContainer,fragment);
        }
        ft.commit();
    }

    public void getIntentData(){
        Intent intent = getIntent();
        EMAIL = intent.getStringExtra("EMAILX");
        PASSWORD = intent.getStringExtra("PASSWORDX");
    }

    public void getDataFromDB(){

    }

}