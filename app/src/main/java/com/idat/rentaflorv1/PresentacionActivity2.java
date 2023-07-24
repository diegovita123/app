package com.idat.rentaflorv1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class PresentacionActivity2 extends AppCompatActivity {
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion2);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences preferences=getSharedPreferences("preflogin", Context.MODE_PRIVATE);
                Boolean sesion=preferences.getBoolean("sesion",false);
                if(sesion){
                    Intent in=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(in);
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(),login.class));
                    finish();
                }

            }
        },1500);
    }
}