package com.example.covidapp.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryInfo {
    @SerializedName("_id")
    @Expose
    private double id;
    @SerializedName("iso2")
    @Expose
    private String iso2;
    @SerializedName("iso3")
    @Expose
    private String iso3;
    @SerializedName("lat")
    @Expose
    private double lat;
    @SerializedName("long")
    @Expose
    private double _long;
    @SerializedName("flag")
    @Expose
    private String flag;

    public CountryInfo(double id, String iso2, String iso3, double lat, double _long, String flag) {
        this.id = id;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.lat = lat;
        this._long = _long;
        this.flag = flag;
    }

    public double getId() {
        return id;
    }

    public String getIso2() {
        return iso2;
    }

    public String getIso3() {
        return iso3;
    }

    public double getLat() {
        return lat;
    }

    public double get_long() {
        return _long;
    }

    public String getFlag() {
        return flag;
    }
}
