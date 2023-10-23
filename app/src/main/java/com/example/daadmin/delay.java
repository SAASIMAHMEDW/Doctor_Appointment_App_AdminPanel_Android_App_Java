package com.example.daadmin;

import android.widget.Toast;

public class delay extends Thread{
    public void run(){
        try {
            Thread.sleep(3000);
        }catch (InterruptedException ignored){
        }
    }
}
