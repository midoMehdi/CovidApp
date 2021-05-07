package com.example.covidapp;
/**
 * this interface is the second, dedicated to displaying statistics of the world, with two text views
 * (displaying details ...), one for the countries and the other for the continents. as well as an exit button.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidapp.service.AllWorldData;
import com.example.covidapp.service.CountryData;
import com.google.gson.Gson;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //private final String APP_TAG = "message_tag_tracker";
    private TextView text_view_cases;
    private TextView text_view_deaths;
    private TextView text_view_recovered;
    private TextView textView_show_by_country;
    private TextView textView_show_by_continent;
    private AllWorldData all_data = null;
   // private final String show_by_country = "Show detail by country > >";
    private SpannableString content_country;// = new SpannableString(show_by_country);

    //private final String show_by_continent = "Show detail by continent > >";
    private SpannableString content_continent;// = new SpannableString(show_by_continent);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("main Activity","onCreate");
        init();
        extractingData();
        printData();
    }
    //=============================================================================//
    //=                     Initializations of widgets of widgets                  =
    //=                                                                            =
    //==============================================================================


    private void init(){
        Log.i("main Activity","initialisation widgets");
        text_view_cases = findViewById(R.id.textView_allcases);
        text_view_deaths = findViewById(R.id.textView_alldeaths);
        text_view_recovered = findViewById(R.id.textView_allrecovered);
        textView_show_by_country = findViewById(R.id.textView_show_by_country);
        textView_show_by_continent = findViewById(R.id.textView_show_by_continent);
        String show_by_country = textView_show_by_country.getText()+"> >";
        String show_by_continent = textView_show_by_continent.getText()+"> >";
        content_country = new SpannableString(show_by_country);
        content_continent = new SpannableString(show_by_continent);
        content_country.setSpan(new UnderlineSpan(), 0, show_by_country.length(), 0);
        content_continent.setSpan(new UnderlineSpan(), 0, show_by_continent.length(), 0);
        textView_show_by_country.setText(content_country);
        textView_show_by_continent.setText(content_continent);
    }

    /**
     * this method  will be executed by clicking on the text view display details by country
     * @param view
     */

    public void show_detail_by_country(View view) {
        Log.i("main Activity","show_detail_by_country");
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }

    /**
            * this method  will be executed by clicking on the text view display details by continents
     * @param view
     */

    public void show_detail_by_continent(View view) {
        Log.i("main Activity","show_detail_by_continent");
        startActivity(new Intent(MainActivity.this,FifthActivity.class));
    }



    private  class MyTask extends AsyncTask<Void,Void,Response> {

        @Override
        protected Response doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://disease.sh/v3/covid-19/all")
                    .method("GET", null)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    /**
     * extracting data from api
     */

    private void extractingData() {
        Log.i("main Activity","extracting data");
        MyTask myTask = new MyTask();
        Response response = null;
        try {
            response = myTask.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String response_string = response.body().string();
            Gson gson = new Gson();
            all_data = gson.fromJson(response_string, AllWorldData.class);
            Log.i("mainActivity","get all data : create attribute"+all_data.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * display of data on the interface
     */

    private void printData(){
        Log.i("main Activity" ,"print data");
        if (all_data != null){
            Log.i("main Activity","print data : data not null "+all_data.toString());
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_view_cases.setText(Integer.toString(all_data.getCases()));
                    text_view_cases.setTextColor(Color.RED);
                    text_view_deaths.setText(Integer.toString(all_data.getDeaths()));
                    text_view_deaths.setTextColor(Color.RED);
                    text_view_recovered.setText(Integer.toString(all_data.getRecovered()));
                }
            });
        }
        else {
            Log.i("main Activity","printData : data  null");
        }
    }

    @Override
    protected void onPause() {
        Log.i("main Activity","onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("main Activity","onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("main Activity","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("main Activity","onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("main Activity","onStart");
        super.onStart();
    }


    public void exit(View view) {
        Log.i("main Activity","exit");
        finishAffinity();
    }
   /* @Override
    public void onBackPressed()
    {
        Log.i("main Activity","onBackPressed");
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }*/

    /**
     * if the user clicks on the back button of the device, the app process will die.
     * @param keyCode
     * @param event
     * @return
     */
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event)
   {
       if(keyCode == KeyEvent.KEYCODE_BACK)
       {
           finishAffinity();
           return true;
       }
       return false;
   }
}