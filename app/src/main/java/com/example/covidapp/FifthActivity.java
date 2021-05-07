package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.service.ContinentData;
import com.example.covidapp.service.CountryData;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FifthActivity extends AppCompatActivity {
    private TextView textView_south_america;
    private TextView textView_africa;
    private TextView textView_north_america;
    private TextView textView_europe;
    private TextView textView_asia;
    private TextView textView_last_update;
    private TextView textView_cases;
    private TextView textView_today_cases;
    private TextView textView_deaths;
    private TextView textView_today_deaths;
    private TextView textView_recovered;
    private TextView textView_today_recovered;
    private TextView textView_critical;
    private TextView textView_tests;
    private Drawable drawable;
    private DateTimeFormatter dtf;
    private LocalDateTime now;
    private String continent_name = "south america";
    private ContinentData all_data;

    //liste des données à recuperer après landscape or portrait
    private String continent;
    private String cases;
    private String new_cases;
    private String deaths;
    private String new_deaths;
    private String recovered;
    private String new_recovered;
    private String critical_cases;
    private String tests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);
        if (savedInstanceState != null) {
            Log.i("Fifth activity", "onCreate : savedInstanceState is not null");
            continent = savedInstanceState.getString("continent");
            cases = savedInstanceState.getString("cases");
            new_cases = savedInstanceState.getString("new_cases");
            deaths = savedInstanceState.getString("deaths");
            new_deaths = savedInstanceState.getString("new_deaths");
            recovered = savedInstanceState.getString("recovered");
            new_recovered = savedInstanceState.getString("new_recovered");
            critical_cases = savedInstanceState.getString("critical_cases");
            tests = savedInstanceState.getString("tests");

        }
        Log.i("Fifth activity", "onCreate");
        init();

    }


    private void init() {
        Log.i("fifth Activity : ", "initialisation");
        drawable = getResources().getDrawable(R.drawable.background_continent);
        textView_south_america = findViewById(R.id.textView_continent_south_america);//by default
        textView_south_america.setBackgroundColor(Color.LTGRAY);
        textView_africa = findViewById(R.id.textView_continent_africa);
        textView_north_america = findViewById(R.id.textView_north_america);
        textView_europe = findViewById(R.id.textView_europe);
        textView_asia = findViewById(R.id.textView_asia);
        textView_last_update = findViewById(R.id.textView_last_update);
        textView_cases = findViewById(R.id.textView_cases);
        textView_today_cases = findViewById(R.id.textView_today_cases);
        textView_deaths = findViewById(R.id.textView_deaths);
        textView_today_deaths = findViewById(R.id.textView_today_deaths);
        textView_recovered = findViewById(R.id.textView_recovered);
        textView_today_recovered = findViewById(R.id.textView_today_recovered);
        textView_critical = findViewById(R.id.textView_critical);
        textView_tests = findViewById(R.id.textView_tests);


        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        textView_last_update.setText(textView_last_update.getText() + df.format(calobj.getTime()));


        /**
         * set data of south America as default data
         */
        getData(continent_name);
        printData_continent();
        textView_south_america.performClick();
    }


    public void click_on_south_america(View view) {
        Log.i("fifth Activity : ", "click_on_south_america");
        textView_south_america.setBackgroundColor(Color.LTGRAY);
        textView_africa.setBackground(drawable);
        textView_north_america.setBackground(drawable);
        textView_europe.setBackground(drawable);
        textView_asia.setBackground(drawable);

        continent_name = "south america";
        getData(continent_name);
        printData_continent();
    }

    public void click_on_africa(View view) {
        Log.i("fifth Activity : ", "click_on_africa");
        textView_africa.setBackgroundColor(Color.LTGRAY);
        textView_south_america.setBackground(drawable);
        textView_north_america.setBackground(drawable);
        textView_europe.setBackground(drawable);
        textView_asia.setBackground(drawable);
        continent_name = null;

        continent_name = "africa";
        Log.i("fifth Activity : ", "click_on_africa : " + continent_name);
        getData(continent_name);
        printData_continent();
    }

    public void click_on_north_america(View view) {
        Log.i("fifth Activity : ", "click_on_north_america");
        textView_north_america.setBackgroundColor(Color.LTGRAY);
        textView_south_america.setBackground(drawable);
        textView_europe.setBackground(drawable);
        textView_africa.setBackground(drawable);
        textView_asia.setBackground(drawable);
        continent_name = "north america";
        getData(continent_name);
        printData_continent();
    }

    public void click_on_europe(View view) {
        Log.i("fifth Activity : ", "click_on_europe");
        textView_europe.setBackgroundColor(Color.LTGRAY);
        textView_africa.setBackground(drawable);
        textView_asia.setBackground(drawable);
        textView_south_america.setBackground(drawable);
        textView_north_america.setBackground(drawable);
        continent_name = "europe";
        getData(continent_name);
        printData_continent();
    }

    public void click_on_asia(View view) {
        Log.i("fifth Activity : ", "click_on_asia");
        textView_asia.setBackgroundColor(Color.LTGRAY);
        textView_south_america.setBackground(drawable);
        textView_north_america.setBackground(drawable);
        textView_africa.setBackground(drawable);
        textView_europe.setBackground(drawable);
        continent_name = "asia";
        getData(continent_name);
        printData_continent();

    }


    private class MyTask extends AsyncTask<String, Void, Response> {

        @Override
        protected Response doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()

                    .url(params[0])
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

    private void getData(String continent) {
        Log.i("fifth Activity : ", "getData from api");
        if (continent != null) {
            Log.i("fifth Activity : ", "getData : country name isn't null");
            FifthActivity.MyTask myTask = new MyTask();
            Response response = null;
            try {
                String url = "https://disease.sh/v3/covid-19/continents/";
                url += continent;
                Log.i("fifth Activity : ", "getData : url " + continent);
                response = myTask.execute(url).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String response_string = response.body().string();
                Gson gson = new Gson();
                all_data = gson.fromJson(response_string, ContinentData.class);
                Log.i("fifth Activity : ", "get all data : create attribute" + all_data.getContinent());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("fifth Activity : ", "getData : country name is null");
        }
    }

    private void printData_continent() {
        Log.i("fifth Activity : ", "print data for country one");
        if (continent_name != null) {
            Log.i("fifth Activity : ", "print data for country one : data not null " + all_data.getContinent());
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Log.i("fifth Activity : ", "print data country time : display current time");


                    textView_cases.setText(Long.toString(all_data.getCases()));
                    textView_cases.setTextColor(Color.RED);

                    textView_today_cases.setText("+" + Integer.toString(all_data.getTodayCases()));
                    textView_today_cases.setTextColor(Color.RED);

                    textView_deaths.setText(Integer.toString(all_data.getDeaths()));
                    textView_deaths.setTextColor(Color.RED);

                    textView_today_deaths.setText("+" + Integer.toString(all_data.getTodayDeaths()));
                    textView_today_deaths.setTextColor(Color.RED);
                    Log.i("fifth Activity : ", "print data country : " + all_data.getTodayDeaths());

                    textView_recovered.setText(Long.toString(all_data.getRecovered()));
                    textView_recovered.setTextColor(Color.GREEN);

                    textView_today_recovered.setText("+" + Integer.toString(all_data.getTodayRecovered()));
                    textView_today_recovered.setTextColor(Color.GREEN);

                    textView_critical.setText(Integer.toString(all_data.getCritical()));
                    textView_critical.setTextColor(Color.RED);

                    textView_tests.setText(Long.toString(all_data.getTests()));
                    textView_tests.setTextColor(Color.GREEN);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        Log.i("fifth activity", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("fifth activity", "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("fifth activity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("fifth activity", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("fifth activity", "onStart");
        super.onStart();
    }

    public void back_to_previous_activity(View view) {
        Log.i("fifthActivity", "back to main activity");
        startActivity(new Intent(FifthActivity.this, MainActivity.class));
    }

    public void exit(View view) {
        Log.i("fifthActivity", "exit from fifth activity");
        finishAffinity();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("fifth Activity", "onRestoreInstanceState");
        //testTextView.setText(savedName);
        textView_cases.setText(cases);
        textView_cases.setTextColor(Color.RED);
        textView_today_cases.setText(new_cases);
        textView_today_cases.setTextColor(Color.RED);
        textView_deaths.setText(deaths);
        textView_deaths.setTextColor(Color.RED);
        textView_today_deaths.setText(new_deaths);
        textView_today_deaths.setTextColor(Color.RED);
        textView_recovered.setText(recovered);
        textView_recovered.setTextColor(Color.GREEN);
        textView_today_recovered.setText(new_recovered);
        textView_today_recovered.setTextColor(Color.GREEN);
        textView_critical.setText(critical_cases);
        textView_critical.setTextColor(Color.RED);
        textView_tests.setText(tests);
        textView_tests.setTextColor(Color.GREEN);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("fifth Activity", "onSaveInstanceState method");
        outState.putString("cases", textView_cases.getText().toString());
        outState.putString("new_cases", textView_today_cases.getText().toString());
        outState.putString("deaths", textView_deaths.getText().toString());
        outState.putString("new_deaths", textView_today_deaths.getText().toString());
        outState.putString("recovered", textView_recovered.getText().toString());
        outState.putString("new_recovered", textView_today_recovered.getText().toString());
        outState.putString("critical_cases", textView_critical.getText().toString());
        outState.putString("tests", textView_tests.getText().toString());
    }

    public void save(View view) {
        Log.i("fifthActivity : ", "save: country not null");
        //File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File fileout = new File(getFilesDir(),"historic.txt");
        try (FileOutputStream fos = new FileOutputStream(fileout)) {
            PrintStream ps = new PrintStream(fos);
            ps.println("< < Continent > >");
            ps.println("-------------------" + continent_name + "-------------------");
            ps.println(getString(R.string.cases) + textView_cases.getText() + ".");
            ps.println(getString(R.string.new_cases) + textView_today_cases.getText() + ".");
            ps.println(getString(R.string.deaths) + textView_deaths.getText() + ".");
            ps.println(getString(R.string.new_deaths) + textView_today_deaths.getText() + ".");
            ps.println(getString(R.string.recovered) + textView_recovered.getText() + ".");
            ps.println(getString(R.string.new_recovered) + textView_today_recovered.getText() + ".");
            ps.println(getString(R.string.critical_cases) + textView_critical.getText() + ".");
            ps.println(getString(R.string.tests) + textView_tests.getText() + ".");

            ps.println("--------------------------------------------------------------------------");
            Toast.makeText(this, getString(R.string.successful_saved) + continent_name, Toast.LENGTH_LONG).show();
            Log.i("fifth Activity", "save file : successful save");
            ps.close();
        } catch (FileNotFoundException e) {
            Log.e("fifth Activity", "save file : File not found", e);
            Log.i("fifth Activity", "save file : failed save");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fifth Activity", "save file : Error I/O", e);
            Log.i("fifth Activity", "save file : failed save");
        }
    }
}