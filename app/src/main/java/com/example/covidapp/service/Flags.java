package com.example.covidapp.service;

import android.app.AppComponentFactory;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Flags {
    private String name_country;
    private Context context;

    public Flags(Context context,String name_country) {
        this.context = context;
        this.name_country = name_country;
    }

    public String getName_country() {
        return name_country;
    }

    public void setName_country(String name_country) {
        this.name_country = name_country;
    }

    public String get_code_country(){
        String json;
        try {
            InputStream inputStream = this.context.getAssets().open("countries");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer,"UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Log.i("thirdActivityClsFlag : " ,"display country flag before loop for");
            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i("thirdActivityClsFlag : " ,"display country flag loop for");
                if(jsonObject.getString("name").toLowerCase().equals(this.name_country.toLowerCase())){
                    Log.i("thirdActivityClsFlag : " ,"display country flag"+jsonObject.getString("name").toLowerCase());
                    return jsonObject.getString("code").toLowerCase();
                }
            }
        } catch (IOException e) {
            Log.i("thirdActivityClsFlag : " ,"IOException : "+e.getCause());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.i("Class Flag : " ,"JSONException");
            e.printStackTrace();
        }
        return null;
    }
}
