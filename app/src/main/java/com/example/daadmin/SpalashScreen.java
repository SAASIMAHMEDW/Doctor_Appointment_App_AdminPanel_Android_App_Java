package com.example.daadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SpalashScreen extends AppCompatActivity {

    String SP_EMAIL,SP_PASSWORD;

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
                    if (sharedPreferenceData()) openIntent(AdminPanelActivity.class);
                    else openIntent(MainActivity.class);
                }
            }
        };thread.start();
    }

    public void openIntent(Class goClass){
        Intent intent = new Intent(SpalashScreen.this, goClass);
        intent.putExtra("SP_EMAIL",SP_EMAIL);
        startActivity(intent);
        finish();
    }

    public boolean sharedPreferenceData(){
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.daadmin_user_login",MODE_PRIVATE);
        SP_EMAIL = sharedPreferences.getString("SP_EMAIL",null);
        SP_PASSWORD  = sharedPreferences.getString("SP_PASSWORD",null);
        return sharedPreferences.getBoolean("SP_LOGIN_FLAG",false);

    }

}