package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidapp.service.CountryData;
import com.example.covidapp.service.Flags;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

public class FourthActivity extends AppCompatActivity {
    private TextView text_view_last_update;
    private TextView text_view_country_name_one;
    private TextView text_view_country_name_two;
    private ImageView image_view_country_one;
    private ImageView image_view_country_two;
    private TextView text_view_country_one_cases;
    private TextView text_view_country_two_cases;
    private TextView text_view_country_one_deaths;
    private TextView text_view_country_two_deaths;
    private TextView text_view_country_one_recovered;
    private TextView text_view_country_two_recovered;
    private TextView text_view_country_one_critical;
    private TextView text_view_country_two_critical;
    private TextView text_view_country_one_tests;
    private TextView text_view_country_two_tests;
    private TextView text_view_country_one_population;
    private TextView text_view_country_two_population;
    private String url_country_one = "https://disease.sh/v3/covid-19/countries/";
    private String url_country_two = "https://disease.sh/v3/covid-19/countries/";
    private Bundle bundle;
    private CountryData country_data_one;
    private CountryData country_data_two;
    private String country_name_one;
    private String country_name_two;
    private DateTimeFormatter dtf;
    private LocalDateTime now;

    // Listes des permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //liste des données à recuperer après landscape or portrait
    private String country_one_name;
    private String country_two_name;

    private String country_one_cases;
    private String country_two_cases;

    private String country_one_deaths;
    private String country_two_deaths;

    private String country_one_recovered;
    private String country_two_recovered;

    private String country_one_critical_cases;
    private String country_two_critical_cases;

    private String country_one_tests;
    private String country_two_tests;

    private String country_one_population;
    private String country_two_population;



   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        if(savedInstanceState != null){
            Log.i("Fourth Activity","onCreate : saved Instance "+country_name_one + " "+country_two_name);
            country_one_name = savedInstanceState.getString("country_one_name");
            country_two_name = savedInstanceState.getString("country_two_name");

            country_one_cases = savedInstanceState.getString("country_one_cases");
            country_two_cases = savedInstanceState.getString("country_two_cases");

            country_one_deaths = savedInstanceState.getString("country_one_deaths");
            country_two_deaths = savedInstanceState.getString("country_two_deaths");

            country_one_recovered = savedInstanceState.getString("country_one_recovered");
            country_two_recovered = savedInstanceState.getString("country_two_recovered");

            country_one_critical_cases = savedInstanceState.getString("country_one_critical_cases");
            country_two_critical_cases = savedInstanceState.getString("country_two_critical_cases");

            country_one_tests = savedInstanceState.getString("country_one_tests");
            country_two_tests = savedInstanceState.getString("country_two_tests");

            country_one_population = savedInstanceState.getString("country_one_population");
            country_two_population = savedInstanceState.getString("country_two_population");
        }
        Log.i("Fourth Activity","onCreate");
        init();
        getCountriesFromPreviousActivity();
        getData_country_one();
        getData_country_two();

        printData_country_one();
        printData_country_two();
    }



    private void init() {
        Log.i("fourth Activity : ", "initialisation");
        text_view_last_update = findViewById(R.id.textView_last_update);
        text_view_country_name_one = findViewById(R.id.textView_country_name_one);
        text_view_country_name_two = findViewById(R.id.textView_country_name_two);
        image_view_country_one = findViewById(R.id.imageView_country_flag_one);
        image_view_country_two = findViewById(R.id.imageView_country_flag_two);
        text_view_country_one_cases = findViewById(R.id.textView_country_one_cases);
        text_view_country_two_cases = findViewById(R.id.textView_country_two_cases);
        text_view_country_one_deaths = findViewById(R.id.textView_country_one_deaths);
        text_view_country_two_deaths = findViewById(R.id.textView_country_two_deaths);
        text_view_country_one_recovered = findViewById(R.id.textView_country_one_recovered);
        text_view_country_two_recovered = findViewById(R.id.textView_country_two_recovered);
        text_view_country_one_critical = findViewById(R.id.textView_country_one_critical);
        text_view_country_two_critical = findViewById(R.id.textView_country_two_critical);
        text_view_country_one_tests = findViewById(R.id.textView_country_one_tests);
        text_view_country_two_tests = findViewById(R.id.textView_country_two_tests);
        text_view_country_one_population = findViewById(R.id.textView_country_one_population);
        text_view_country_two_population = findViewById(R.id.textView_country_two_population);
    }
    private void getCountriesFromPreviousActivity(){
        Log.i("fourth Activity : ", "getNameFromPreviousActivity");
        bundle = getIntent().getExtras();
        country_name_one = bundle.getString("country_name_one");
        if(country_name_one != null){
            Log.i("fourth Activity : ", "getNameFromPreviousActivity : country name one isn't null" + country_name_one);
            url_country_one += country_name_one;
        }
        else{
            Log.i("fourth Activity : ", "getNameFromPreviousActivity : country name one is null");
        }

        country_name_two = bundle.getString("country_name_two");
        if(country_name_two != null){
            Log.i("fourth Activity : ", "getNameFromPreviousActivity : country name two isn't null" + country_name_two);
            url_country_two += country_name_two;
        }
        else {
            Log.i("fourth Activity : ", "getNameFromPreviousActivity : country name two is null");

        }
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

    private void getData_country_one() {
        Log.i("fourth Activity : ", "getData from api for first country");
        if (country_name_one != null) {
            Log.i("fourth Activity : ", "getData : country name one isn't null");
            FourthActivity.MyTask myTask = new MyTask();
            Response response = null;
            try {
                response = myTask.execute(url_country_one).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String response_string = response.body().string();
                Gson gson = new Gson();
                country_data_one = gson.fromJson(response_string, CountryData.class);
                Log.i("fourth Activity : ", "get all data : create attribute" + country_data_one.getCountry());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("fourth Activity : ", "getData : country name one is null");
        }
    }
    private void getData_country_two() {
        Log.i("fourth Activity : ", "getData from api for first country");
        if (country_name_two != null) {
            Log.i("fourth Activity : ", "getData : country name one isn't null");
            FourthActivity.MyTask myTask = new MyTask();
            Response response = null;
            try {
                response = myTask.execute(url_country_two).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String response_string = response.body().string();
                Gson gson = new Gson();
                country_data_two = gson.fromJson(response_string, CountryData.class);
                Log.i("fourth Activity : ", "get all data : create attribute" + country_data_two.getCountry());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("fourth Activity : ", "getData : country name two is null");
        }
    }


    private void printData_country_one(){
        Log.i("fourth Activity : ", "print data for country one");
        if(country_data_one != null){
            Log.i("fourth Activity : ", "print data for country one : data not null " + country_data_one.getCountry());
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Calendar calobj = Calendar.getInstance();
                    text_view_last_update.setText(text_view_last_update.getText()+df.format(calobj.getTime()));

                    Log.i("fourth Activity : ","print data country time : display current time");
                    //System.out.println(df.format(calobj.getTime()));

                    String code_path = display_flag(country_name_one);
                    Log.i("fourth Activity : ", "display country flag one" + code_path);
                    if (code_path != null) {
                        Log.i("fourth Activity : ", "display country flag one");
                        String param = "R.drawable." + code_path;
                        int resID = getResources().getIdentifier(code_path,"drawable", getPackageName());
                        Log.i("fourth Activity : ", "display country flag one :"+resID);
                        Drawable drawable = getResources().getDrawable(resID);
                        Log.i("fourth Activity : ", "display country flag one : "+drawable);
                        image_view_country_one.setImageDrawable(drawable);
                    }
                    text_view_country_name_one.setText(country_data_one.getCountry());
                    text_view_country_name_one.setTextColor(Color.GREEN);

                    text_view_country_one_cases.setText(Integer.toString(country_data_one.getCases()));
                    text_view_country_one_cases.setTextColor(Color.RED);

                    text_view_country_one_deaths.setText(Integer.toString(country_data_one.getDeaths()));
                    text_view_country_one_deaths.setTextColor(Color.RED);

                    text_view_country_one_recovered.setText(Integer.toString(country_data_one.getRecovered()));
                    text_view_country_one_recovered.setTextColor(Color.GREEN);

                    text_view_country_one_critical.setText(Integer.toString(country_data_one.getCritical()));
                    text_view_country_one_critical.setTextColor(Color.RED);

                    text_view_country_one_tests.setText(Integer.toString(country_data_one.getTests()));
                    text_view_country_one_tests.setTextColor(Color.GREEN);

                    text_view_country_one_population.setText(Integer.toString(country_data_one.getPopulation()));
                    text_view_country_one_population.setTextColor(Color.GREEN);
                }
            });
        }
    }
    private void printData_country_two(){
        Log.i("fourth Activity : ", "print data for country two");
        if(country_data_two != null){
            Log.i("fourth Activity : ", "print data for country two : data not null " + country_data_two.getCountry());
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String code_path = display_flag(country_name_two);
                    Log.i("fourth Activity : ", "display country flag two" + code_path);
                    if (code_path != null) {
                        Log.i("fourth Activity : ", "display country flag two");
                        String param = "R.drawable." + code_path;
                        int resID = getResources().getIdentifier(code_path,"drawable", getPackageName());
                        Log.i("fourth Activity : ", "display country flag two :"+resID);
                        Drawable drawable = getResources().getDrawable(resID);
                        Log.i("fourth Activity : ", "display country flag two : "+drawable);
                        image_view_country_two.setImageDrawable(drawable);
                    }
                    text_view_country_name_two.setText(country_data_two.getCountry());
                    text_view_country_name_two.setTextColor(Color.GREEN);

                    text_view_country_two_cases.setText(Integer.toString(country_data_two.getCases()));
                    text_view_country_two_cases.setTextColor(Color.RED);

                    text_view_country_two_deaths.setText(Integer.toString(country_data_two.getDeaths()));
                    text_view_country_two_deaths.setTextColor(Color.RED);

                    text_view_country_two_recovered.setText(Integer.toString(country_data_two.getRecovered()));
                    text_view_country_two_recovered.setTextColor(Color.GREEN);

                    text_view_country_two_critical.setText(Integer.toString(country_data_two.getCritical()));
                    text_view_country_two_critical.setTextColor(Color.RED);

                    text_view_country_two_tests.setText(Integer.toString(country_data_two.getTests()));
                    text_view_country_two_tests.setTextColor(Color.GREEN);

                    text_view_country_two_population.setText(Integer.toString(country_data_two.getPopulation()));
                    text_view_country_two_population.setTextColor(Color.GREEN);
                }
            });
        }
    }
    private String display_flag(String country){
        Flags flags = new Flags(this, country);
        String code = flags.get_code_country();
        if (code != null) {
            Log.i("fourth Activity : ", "display flag code" + code);
            return "ic_list_country_" + code;
        }
        Log.i("fourth Activity : ", "display flag code null");
        return null;
    }
    @Override
    protected void onPause() {
        Log.i("fourth Activity : ","onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("fourth Activity : ","onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("fourth Activity : ","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("fourth Activity : ","onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("fourth Activity : ","onStart");
        super.onStart();
    }
    public void exit(View view) {
        Log.i("fourth Activity : ","exit");
        finishAffinity();
    }

    public void back_to_previous_activity(View view) {
        Log.i("fourth Activity : ","back_to_previous_activity");
        startActivity(new Intent(FourthActivity.this,SecondActivity.class));
    }
    public static void verifyStoragePermissions(Activity activity) {
        Log.i("fourth Activity : ","verifyStoragePermissions");
// Vérifie si nous avons les droits d'écriture
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
// Aie, il faut les demander àl'utilisateur
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public void save(View view) {
        if (!text_view_country_name_one.getText().toString().toLowerCase().equals("empty") &&
            !text_view_country_name_two.getText().toString().toLowerCase().equals("empty")){
            Log.i("fourth Activity : ","save: countries not null");
            File fileout = new File(getFilesDir(),"historic.txt");
            try (FileOutputStream fos = new FileOutputStream(fileout)) {
                PrintStream ps = new PrintStream(fos);
                ps.println("< < Two country > >");
                ps.println("-------------------"+text_view_country_name_one.getText()+"-------------------");
                ps.println(getString(R.string.cases)+text_view_country_one_cases.getText()+".");
                ps.println(getString(R.string.deaths)+text_view_country_one_deaths.getText()+".");
                ps.println(getString(R.string.critical_cases)+text_view_country_one_critical.getText()+".");
                ps.println(getString(R.string.recovered)+text_view_country_one_recovered+".");
                ps.println(getString(R.string.population)+text_view_country_one_population.getText()+".");
                ps.println("-------------------"+text_view_country_name_two.getText()+"-------------------");
                ps.println(getString(R.string.cases)+text_view_country_two_cases.getText()+".");
                ps.println(getString(R.string.deaths)+text_view_country_two_deaths.getText()+".");
                ps.println(getString(R.string.critical_cases)+text_view_country_two_critical.getText()+".");
                ps.println(getString(R.string.recovered)+text_view_country_two_recovered.getText()+".");
                ps.println(getString(R.string.population)+text_view_country_two_population.getText()+".");
                Toast.makeText(this,getString(R.string.successful_saved),Toast.LENGTH_LONG).show();
                Log.i("fourth Activity","save file : successful save");
                ps.close();
            } catch (FileNotFoundException e) {
                Log.e("fourth Activity","save file : File not found",e);
                Log.i("fourth Activity","save file : failed save");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("fourth Activity","save file : Error I/O",e);
                Log.i("fourth Activity","save file : failed save");
            }
        }
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("fourth Activity", "onRestoreInstanceState method" );
        //testTextView.setText(savedName);
        //data first country
        text_view_country_name_one.setText(country_one_name);
        text_view_country_name_one.setTextColor(Color.GREEN);

        text_view_country_one_cases.setText(country_one_cases);
        text_view_country_one_cases.setTextColor(Color.RED);

        text_view_country_one_deaths.setText(country_one_deaths);
        text_view_country_one_deaths.setTextColor(Color.RED);

        text_view_country_one_recovered.setText(country_one_recovered);
        text_view_country_one_recovered.setTextColor(Color.GREEN);

        text_view_country_one_critical.setText(country_one_critical_cases);
        text_view_country_one_critical.setTextColor(Color.RED);

        text_view_country_one_tests.setText(country_one_tests);
        text_view_country_one_tests.setTextColor(Color.GREEN);

        text_view_country_one_population.setText(country_one_population);
        text_view_country_one_population.setTextColor(Color.GREEN);

        //data two country

        text_view_country_name_two.setText(country_two_name);
        text_view_country_name_two.setTextColor(Color.GREEN);

        text_view_country_two_cases.setText(country_two_cases);
        text_view_country_two_cases.setTextColor(Color.RED);

        text_view_country_two_deaths.setText(country_two_deaths);
        text_view_country_two_deaths.setTextColor(Color.RED);

        text_view_country_two_recovered.setText(country_two_recovered);
        text_view_country_two_recovered.setTextColor(Color.GREEN);

        text_view_country_two_critical.setText(country_two_critical_cases);
        text_view_country_two_critical.setTextColor(Color.RED);

        text_view_country_two_tests.setText(country_two_tests);
        text_view_country_two_tests.setTextColor(Color.GREEN);

        text_view_country_two_population.setText(country_two_population);
        text_view_country_two_population.setTextColor(Color.GREEN);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        Log.i("fourth Activity", "onSaveInstanceState method" );
        //outState.putString(KEY_NAME,testTextView.getText().toString());
        outState.putString("country_one_name",text_view_country_name_one.getText().toString());
        outState.putString("country_two_name",text_view_country_name_two.getText().toString());

        outState.putString("country_one_cases",text_view_country_one_cases.getText().toString());
        outState.putString("country_two_cases",text_view_country_two_cases.getText().toString());

        outState.putString("country_one_deaths",text_view_country_one_deaths.getText().toString());
        outState.putString("country_two_deaths",text_view_country_two_deaths.getText().toString());

        outState.putString("country_one_recovered",text_view_country_one_recovered.getText().toString());
        outState.putString("country_two_recovered",text_view_country_two_recovered.getText().toString());

        outState.putString("country_one_critical_cases",text_view_country_one_critical.getText().toString());
        outState.putString("country_two_critical_cases",text_view_country_two_critical.getText().toString());

        outState.putString("country_one_tests",text_view_country_one_tests.getText().toString());
        outState.putString("country_two_tests",text_view_country_two_tests.getText().toString());

        outState.putString("country_one_population",text_view_country_one_population.getText().toString());
        outState.putString("country_two_population",text_view_country_two_population.getText().toString());
    }

}