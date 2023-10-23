package com.example.daadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminLoginActivity extends AppCompatActivity {

    Button adminLoginBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminLoginBTN = findViewById(R.id.adminLoginBTN);

        adminLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdminPanel();
            }
        });
    }

    public void goAdminPanel(){
        Intent AdminPanelIntent = new Intent(AdminLoginActivity.this, AdminPanelActivity.class);
        startActivity(AdminPanelIntent);
    }
}