package com.example.covidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SecondActivity extends AppCompatActivity {
    private Spinner spinner_select_country_one = null;
    private Switch aSwitch;
    private Spinner spinner_select_country_two = null;
    private Button button_display;
    private Intent intent;
    private  ArrayAdapter adapter;

    private SharedPreferences sharedPref;

    private Set<String> searchedCountriesName = new HashSet<>();
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.i("second Activity","on create");
        init();
        display_historic();//display historic

    }

    private void init() {
        Log.i("second Activity","initialisation widgets");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        spinner_select_country_one = findViewById(R.id.select_country_one);
        aSwitch = findViewById(R.id.switch1);
        spinner_select_country_two = findViewById(R.id.select_country_two);
        button_display = findViewById(R.id.button_display);
        adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, R.layout.my_selected_country);
        adapter.setDropDownViewResource(R.layout.m_dropdown_item);
        spinner_select_country_one.setAdapter(adapter);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spinner_select_country_two.setEnabled(true);
                    spinner_select_country_two.setAdapter(adapter);
                }
                else {
                    spinner_select_country_two.setEnabled(false);
                    spinner_select_country_two.setAdapter(null);
                }
            }
        });
    }
    public void checkSwitch(View view) {
        Log.i("second Activity","check switch");
        spinner_select_country_two.setAdapter(adapter);
    }


    public void display(View view) {
        Log.i("second Activity","display: go to next activity");
        if (spinner_select_country_one.getSelectedItem().toString().isEmpty()){

            Log.i("second Activity","country selected is empty");
            Toast.makeText(this,getString(R.string.select_at_least_one_country),Toast.LENGTH_SHORT).show();
            vibrator.vibrate(500);
            //Log.i("secondActivity","country selected is empty two : "+spinner_select_country_two.getSelectedItem().toString());
        }
        else if(spinner_select_country_two.getAdapter() != null && !spinner_select_country_two.getSelectedItem().toString().isEmpty()){//is not empty
            Log.i("second Activity","country two is noy empty,country one is not empty"+spinner_select_country_one.getSelectedItem().toString()+", "+spinner_select_country_two.getSelectedItem().toString());
            intent = new Intent(this,FourthActivity.class);
            intent.putExtra("country_name_one",spinner_select_country_one.getSelectedItem().toString());
            intent.putExtra("country_name_two",spinner_select_country_two.getSelectedItem().toString());
            searchedCountriesName.add(spinner_select_country_one.getSelectedItem().toString());
            searchedCountriesName.add(spinner_select_country_two.getSelectedItem().toString());
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPref.edit().putStringSet("historyCountriesName", searchedCountriesName).commit();
            startActivity(intent);
        }
        else  {
            Log.i("second Activity","country two is empty,country one is not empty"+spinner_select_country_one.getSelectedItem().toString());
            intent = new Intent(this,ThirdActivity.class);
            intent.putExtra("name_country",spinner_select_country_one.getSelectedItem().toString());
            searchedCountriesName.add(spinner_select_country_one.getSelectedItem().toString());
            sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            sharedPref.edit().putStringSet("historyCountriesName", searchedCountriesName).commit();
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        Log.i("second Activity : ","onPause");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.i("second  Activity : ","onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("second Activity : ","onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.i("second Activity : ","onRestart");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.i("second Activity : ","onStart");
        super.onStart();
    }
    public void back_to_previous_activity(View view) {
        Log.i("second Activity","back to main activity");
        //finish();
        startActivity(new Intent(SecondActivity.this,MainActivity.class));

    }

    public void exit(View view) {
        Log.i("second Activity","exit from second activity");
        finishAffinity();
    }

    // Fonction qui recharge un historique
    public void reload_historic() {
        Log.i("second Activity","reload_historic");
        // Récuperation de l'objet unique qui s'occupe de la sauvegarde
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // Récuperation de l'ancienne valeur ou d'une valeur par défaut
        searchedCountriesName = sharedPref.getStringSet("historyCountriesName", new TreeSet<String>());
    }
    // Fonction qui affiche l'historique àpartir de l'attribut searchedPokemonName
    // Il faut donc avoir chargé l'historique avant!
    public void display_historic() {
        reload_historic();
        Log.i("second Activity","historic ("+ (new Date())+ ") size= "+searchedCountriesName.size()+": ");
        for (String item : searchedCountriesName) {
            Log.i("country","\t- " + item);
        }
    }
}