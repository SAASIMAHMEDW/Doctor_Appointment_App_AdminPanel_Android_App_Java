package com.example.daadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SpalashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalash_screen);
//        try {
//        getSupportActionBar().hide();
//        }catch (NullPointerException ignored){}
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SpalashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };thread.start();
    }
}