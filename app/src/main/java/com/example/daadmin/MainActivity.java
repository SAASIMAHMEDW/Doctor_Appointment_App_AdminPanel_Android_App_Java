package com.example.daadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mainActivityLoginBTN,mainActivityRegisterBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityLoginBTN = findViewById(R.id.mainActivityLoginBTN);
        mainActivityRegisterBTN = findViewById(R.id.mainActivityRegisterBTN);

        mainActivityLoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLoginActivity();
            }
        });

        mainActivityRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRegisterActivity();
            }
        });
    }

    public void goLoginActivity(){
        Intent loginIntent = new Intent(MainActivity.this, AdminLoginActivity.class);
        startActivity(loginIntent);
    }
    public void goRegisterActivity(){
        Intent registerIntent = new Intent(MainActivity.this, registerActivitySV.class);
        startActivity(registerIntent);
    }
}