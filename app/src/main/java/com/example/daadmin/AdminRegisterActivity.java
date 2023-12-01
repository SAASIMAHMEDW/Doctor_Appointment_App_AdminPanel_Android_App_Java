package com.example.daadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminRegisterActivity extends AppCompatActivity {
    Button registerRegisterBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        registerRegisterBTN = findViewById(R.id.registerRegisterBTN);
        registerRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdminPanel();
            }
        });
    }

    public void goAdminPanel(){
        Intent AdminPanelIntent = new Intent(AdminRegisterActivity.this, AdminPanelActivity.class);
        startActivity(AdminPanelIntent);
        finish();
    }
}