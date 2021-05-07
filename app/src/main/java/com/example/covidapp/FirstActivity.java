package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * it is the first interface of the application,
 * it is the first interface of the application, in which the logo and the name of the application appear,
 * after 3 seconds the Main Activity (second interface) will start automatically in which the logo and the name of the
 * application appear, after 3 seconds the Main Activity (second interface) will start automatically.
 */

public class FirstActivity extends AppCompatActivity {
    TextView download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Log.i("firstActivity","onCreate");
        download = findViewById(R.id.text_view_downloading);
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();


        /**
         * here we test if the application is connected.
         */

        if (netInfo == null){
            Toast.makeText(this,"Pas de Connection ...",Toast.LENGTH_LONG).show();
            finishAffinity();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        },3000);
    }

}