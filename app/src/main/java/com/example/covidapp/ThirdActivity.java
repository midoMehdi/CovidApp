package com.example.covidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThirdActivity extends AppCompatActivity {
    private ImageView image_view_flag;
    private TextView text_view_country_name;
    private TextView text_view_cases;
    private TextView text_view_today_cases;
    private TextView text_view_deaths;
    private TextView text_view_today_deaths;
    private TextView text_view_recovered;
    private TextView text_view_today_recovered;
    private TextView text_view_active_cases;
    private TextView text_view_critical_cases;
    private TextView text_view_cases_per_one_million;
    private TextView text_view_deathsPerOneMillion;
    private TextView text_view_tests;
    private TextView text_view_testsPerOneMillion;
    private TextView text_view_population;
    private TextView text_view_continent;
    private TextView textView_last_update;
    private CountryData all_data = null;
    private Bundle bundle;
    private String country_name = null;
    private String url = "https://disease.sh/v3/covid-19/countries/";

    // Listes des permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //liste des données à recuperer après landscape or portrait
    private String country;
    private String cases;
    private String new_cases;
    private String deaths;
    private String new_deaths;
    private String recovered;
    private String new_recovered;
    private String active_cases;
    private String critical_cases;
    private String cases_per_one_million;
    private String deaths_per_one_million;
    private String tests;
    private String tests_per_one_million;
    private String population;
    private String continent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        if (savedInstanceState != null) {
            Log.i("third Activity : ", "onCreate : savedInstanceState : not null");
            country = savedInstanceState.getString("country");
            cases = savedInstanceState.getString("cases");
            new_cases = savedInstanceState.getString("new_cases");
            deaths = savedInstanceState.getString("deaths");
            new_deaths = savedInstanceState.getString("new_deaths");
            recovered = savedInstanceState.getString("recovered");
            new_recovered = savedInstanceState.getString("new_recovered");
            active_cases = savedInstanceState.getString("active_cases");
            critical_cases = savedInstanceState.getString("critical_cases");
            cases_per_one_million = savedInstanceState.getString("cases_per_one_million");
            deaths_per_one_million = savedInstanceState.getString("deaths_per_one_million");
            tests = savedInstanceState.getString("tests");
            tests_per_one_million = savedInstanceState.getString("tests_per_one_million");
            population = savedInstanceState.getString("population");
            continent = savedInstanceState.getString("continent");

        }
        Log.i("third Activity : ", "onCreate");
        init();
        getNameFromPreviousActivity();
        getData();
        printData();
        verifyStoragePermissions(this);
    }

    private void init() {
        Log.i("third Activity : ", "initialisation widget");
        image_view_flag = findViewById(R.id.imageView_country);
        text_view_country_name = findViewById(R.id.textView_country);
        text_view_cases = findViewById(R.id.textView_cases);
        text_view_today_cases = findViewById(R.id.textView_today_cases);
        text_view_deaths = findViewById(R.id.textView_deaths);
        text_view_today_deaths = findViewById(R.id.textView_today_deaths);
        text_view_recovered = findViewById(R.id.textView_recovered);
        text_view_today_recovered = findViewById(R.id.textView_today_recovered);
        text_view_active_cases = findViewById(R.id.textView_active_cases);
        text_view_critical_cases = findViewById(R.id.textView_critical_cases);
        text_view_cases_per_one_million = findViewById(R.id.textView_cases_per_one_million);
        text_view_deathsPerOneMillion = findViewById(R.id.textView_deathsPerOneMillion);
        text_view_tests = findViewById(R.id.textView_tests);
        text_view_testsPerOneMillion = findViewById(R.id.textView_testsPerOneMillion);
        text_view_population = findViewById(R.id.textView_population);
        text_view_continent = findViewById(R.id.textView_continent);
        textView_last_update = findViewById(R.id.textView_last_update);
    }

    private void getNameFromPreviousActivity() {
        Log.i("third Activity : ", "getNameFromPreviousActivity");
        bundle = getIntent().getExtras();
        country_name = bundle.getString("name_country");
        if (country_name != null) {
            Log.i("third Activity : ", "getNameFromPreviousActivity : country name isn't null");
            url += country_name;
        } else {
            Log.i("third Activity : ", "getNameFromPreviousActivity : country name is null");
        }
    }


    private class MyTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()

                    .url(url)
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

    private void getData() {
        Log.i("third Activity : ", "getData from api");
        if (country_name != null) {
            Log.i("third Activity : ", "getData : country name isn't null");
            ThirdActivity.MyTask myTask = new ThirdActivity.MyTask();
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
                all_data = gson.fromJson(response_string, CountryData.class);
                Log.i("third Activity : ", "get all data : create attribute" + all_data.getCountry());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("third Activity : ", "getData : country name is null");
        }
    }

    private void printData() {
        Log.i("third Activity : ", "print data");
        if (all_data != null) {
            Log.i("third Activity : ", "print data : data not null " + all_data.getCountry());
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String code_path = display_flag(country_name);
                    Log.i("third Activity : ", "display country flag" + code_path);
                    if (code_path != null) {
                        Log.i("third Activity : ", "display country flag");
                        String param = "R.drawable." + code_path;
                        int resID = getResources().getIdentifier(code_path, "drawable", getPackageName());
                        Log.i("third Activity : ", "display country flag" + resID);
                        Drawable drawable = getResources().getDrawable(resID);
                        Log.i("third Activity : ", "display country flag" + drawable);
                        image_view_flag.setImageDrawable(drawable);
                    }
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    Calendar calobj = Calendar.getInstance();
                    textView_last_update.setText(textView_last_update.getText() + df.format(calobj.getTime()));
                    text_view_country_name.setText(country_name);
                    text_view_country_name.setTextColor(Color.GREEN);
                    text_view_cases.setText(Integer.toString(all_data.getCases()));
                    text_view_cases.setTextColor(Color.RED);
                    text_view_today_cases.setText("+" + Integer.toString(all_data.getTodayCases()));
                    text_view_today_cases.setTextColor(Color.RED);
                    text_view_deaths.setText(Integer.toString(all_data.getDeaths()));
                    text_view_deaths.setTextColor(Color.RED);
                    text_view_today_deaths.setText("+" + Integer.toString(all_data.getTodayDeaths()));
                    text_view_today_deaths.setTextColor(Color.RED);
                    text_view_recovered.setText(Integer.toString(all_data.getRecovered()));
                    text_view_recovered.setTextColor(Color.GREEN);
                    text_view_today_recovered.setText("+" + Integer.toString(all_data.getTodayRecovered()));
                    text_view_today_recovered.setTextColor(Color.GREEN);
                    text_view_active_cases.setText(Integer.toString(all_data.getActive()));
                    text_view_active_cases.setTextColor(Color.RED);
                    text_view_critical_cases.setText(Integer.toString(all_data.getCritical()));
                    text_view_critical_cases.setTextColor(Color.RED);
                    text_view_cases_per_one_million.setText(Integer.toString(all_data.getCasesPerOneMillion()));
                    text_view_cases_per_one_million.setTextColor(Color.RED);
                    text_view_deathsPerOneMillion.setText(Integer.toString(all_data.getDeathsPerOneMillion()));
                    text_view_deathsPerOneMillion.setTextColor(Color.RED);
                    text_view_tests.setText(Integer.toString(all_data.getTests()));
                    text_view_tests.setTextColor(Color.GREEN);
                    text_view_testsPerOneMillion.setText(Integer.toString(all_data.getTestsPerOneMillion()));
                    text_view_testsPerOneMillion.setTextColor(Color.GREEN);
                    text_view_population.setText(Integer.toString(all_data.getPopulation()));
                    text_view_population.setTextColor(Color.GREEN);
                    text_view_continent.setText(all_data.getContinent());
                    text_view_continent.setTextColor(Color.GREEN);
                }
            });
        } else {
            Log.i("third Activity : ", "printData : data  null");
            ((Activity) this).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text_view_country_name.setText("<<  Empty >>");
                    text_view_country_name.setTextColor(Color.RED);
                    text_view_cases.setText("<<  Empty >>");
                    text_view_cases.setTextColor(Color.RED);
                    text_view_today_cases.setText("<<  Empty >>");
                    text_view_today_cases.setTextColor(Color.RED);
                    text_view_deaths.setText("<<  Empty >>");
                    text_view_deaths.setTextColor(Color.RED);
                    text_view_today_deaths.setText("<<  Empty >>");
                    text_view_today_deaths.setTextColor(Color.RED);
                    text_view_recovered.setText("<<  Empty >>");
                    text_view_recovered.setTextColor(Color.RED);
                    text_view_today_recovered.setText("<<  Empty >>");
                    text_view_today_recovered.setTextColor(Color.RED);
                    text_view_active_cases.setText("<<  Empty >>");
                    text_view_active_cases.setTextColor(Color.RED);
                    text_view_critical_cases.setText("<<  Empty >>");
                    text_view_critical_cases.setTextColor(Color.RED);
                    text_view_cases_per_one_million.setText("<<  Empty >>");
                    text_view_cases_per_one_million.setTextColor(Color.RED);
                    text_view_deathsPerOneMillion.setText("<<  Empty >>");
                    text_view_deathsPerOneMillion.setTextColor(Color.RED);
                    text_view_testsPerOneMillion.setText("<<  Empty >>");
                    text_view_testsPerOneMillion.setTextColor(Color.RED);
                    text_view_population.setText("<<  Empty >>");
                    text_view_population.setTextColor(Color.RED);
                    text_view_continent.setText("<<  Empty >>");
                    text_view_continent.setTextColor(Color.RED);
                }
            });
        }
    }

    private String display_flag(String country) {
        Flags flags = new Flags(this, country);
        String code = flags.get_code_country();
        if (code != null) {
            Log.i("third Activity : ", "display flag code" + code);
            return "ic_list_country_" + code;
        }
        Log.i("third Activity : ", "display flag code null");
        return null;
    }

    @Override
    protected void onPause() {
        Log.i("third Activity : ", "onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("third Activity : ", "onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("third Activity : ", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("third Activity : ", "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("third Activity : ", "onStart");
        super.onStart();
    }

    public void exit(View view) {
        Log.i("third Activity : ", "exit");
        finishAffinity();
    }

    public void back_to_previous_activity(View view) {
        Log.i("third Activity : ", "back_to_previous_activity");
        finish();
        //startActivity(new Intent(ThirdActivity.this,SecondActivity.class));

    }

    public static void verifyStoragePermissions(Activity activity) {
        Log.i("third Activity : ", "verifyStoragePermissions");
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
        if (!text_view_country_name.getText().toString().toLowerCase().equals("empty")) {
            Log.i("third Activity : ", "save: country not null");
            File fileout = new File(getFilesDir(),"historic.txt");
            try (FileOutputStream fos = new FileOutputStream(fileout)) {
                PrintStream ps = new PrintStream(fos);
                ps.println("< < One country > >");
                ps.println("-------------------" + text_view_country_name.getText() + "-------------------");
                ps.println(getString(R.string.cases) + text_view_cases.getText() + ".");
                ps.println(getString(R.string.new_cases) + text_view_today_cases.getText() + ".");
                ps.println(getString(R.string.deaths) + text_view_deaths.getText() + ".");
                ps.println(getString(R.string.new_deaths) + text_view_today_deaths.getText() + ".");
                ps.println(getString(R.string.active_cases) + text_view_active_cases.getText() + ".");
                ps.println(getString(R.string.new_recovered) + text_view_today_recovered.getText() + ".");
                ps.println(getString(R.string.recovered) + text_view_recovered.getText() + ".");
                ps.println("--------------------------------------------------------------------------");
                Toast.makeText(this, getString(R.string.successful_saved), Toast.LENGTH_LONG).show();
                Log.i("third Activity", "save file : successful save");
                ps.close();
            } catch (FileNotFoundException e) {
                Log.e("third Activity", "save file : File not found", e);
                Log.i("third Activity", "save file : failed save");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("third Activity", "save file : Error I/O", e);
                Log.i("third Activity", "save file : failed save");
            }
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("third Activity", "onRestoreInstanceState method");
        //testTextView.setText(savedName);
        text_view_country_name.setText(country);
        text_view_country_name.setTextColor(Color.GREEN);
        text_view_cases.setText(cases);
        text_view_cases.setTextColor(Color.RED);
        text_view_today_cases.setText(new_cases);
        text_view_today_cases.setTextColor(Color.RED);
        text_view_deaths.setText(deaths);
        text_view_deaths.setTextColor(Color.RED);
        text_view_today_deaths.setText(new_deaths);
        text_view_today_deaths.setTextColor(Color.RED);
        text_view_recovered.setText(recovered);
        text_view_recovered.setTextColor(Color.GREEN);
        text_view_today_recovered.setText(new_recovered);
        text_view_today_recovered.setTextColor(Color.GREEN);
        text_view_active_cases.setText(active_cases);
        text_view_active_cases.setTextColor(Color.RED);
        text_view_critical_cases.setText(critical_cases);
        text_view_critical_cases.setTextColor(Color.RED);
        text_view_cases_per_one_million.setText(cases_per_one_million);
        text_view_cases_per_one_million.setTextColor(Color.RED);
        text_view_deathsPerOneMillion.setText(deaths_per_one_million);
        text_view_deathsPerOneMillion.setTextColor(Color.RED);
        text_view_tests.setText(tests);
        text_view_tests.setTextColor(Color.GREEN);
        text_view_testsPerOneMillion.setText(tests_per_one_million);
        text_view_testsPerOneMillion.setTextColor(Color.GREEN);
        text_view_population.setText(population);
        text_view_population.setTextColor(Color.GREEN);
        text_view_continent.setText(continent);
        text_view_continent.setTextColor(Color.GREEN);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        Log.i("third Activity", "onSaveInstanceState method");
        //outState.putString(KEY_NAME,testTextView.getText().toString());
        outState.putString("country", text_view_country_name.getText().toString());
        outState.putString("cases", text_view_cases.getText().toString());
        outState.putString("new_cases", text_view_today_cases.getText().toString());
        outState.putString("deaths", text_view_deaths.getText().toString());
        outState.putString("new_deaths", text_view_today_deaths.getText().toString());
        outState.putString("recovered", text_view_recovered.getText().toString());
        outState.putString("new_recovered", text_view_today_recovered.getText().toString());
        outState.putString("active_cases", text_view_active_cases.getText().toString());
        outState.putString("critical_cases", text_view_critical_cases.getText().toString());
        outState.putString("cases_per_one_million", text_view_cases_per_one_million.getText().toString());
        outState.putString("deaths_per_one_million", text_view_deathsPerOneMillion.getText().toString());
        outState.putString("tests", text_view_tests.getText().toString());
        outState.putString("tests_per_one_million", text_view_testsPerOneMillion.getText().toString());
        outState.putString("population", text_view_population.getText().toString());
        outState.putString("continent", text_view_continent.getText().toString());

    }

}