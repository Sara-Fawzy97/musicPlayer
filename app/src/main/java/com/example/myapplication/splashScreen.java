package com.example.myapplication;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;


public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  Intent i= new Intent(getApplicationContext(),list.class);
                  startActivity(i);

                  finish();
              }
          }, 1000);




    }
}
