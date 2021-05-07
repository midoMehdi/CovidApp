package com.example.covidapp.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContinentData {
    @SerializedName("updated")
    @Expose
    private long updated;
    @SerializedName("cases")
    @Expose
    private long cases;
    @SerializedName("todayCases")
    @Expose
    private int todayCases;
    @SerializedName("deaths")
    @Expose
    private int deaths;
    @SerializedName("todayDeaths")
    @Expose
    private int todayDeaths;
    @SerializedName("recovered")
    @Expose
    private long recovered;
    @SerializedName("todayRecovered")
    @Expose
    private int todayRecovered;
    @SerializedName("active")
    @Expose
    private long active;
    @SerializedName("critical")
    @Expose
    private int critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private double casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private double deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private long tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private double testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private long population;
    @SerializedName("continent")
    @Expose
    private String continent;
    @SerializedName("activePerOneMillion")
    @Expose
    private double activePerOneMillion;
    @SerializedName("recoveredPerOneMillion")
    @Expose
    private double recoveredPerOneMillion;
    @SerializedName("criticalPerOneMillion")
    @Expose
    private double criticalPerOneMillion;
    @SerializedName("continentInfo")
    @Expose
    private ContinentInfo continentInfo;
    @SerializedName("countries")
    @Expose
    private List<String> countries = null;

    public long getUpdated() {
        return updated;
    }

    public long getCases() {
        return cases;
    }

    public int getTodayCases() {
        return todayCases;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getTodayDeaths() {
        return todayDeaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public int getTodayRecovered() {
        return todayRecovered;
    }

    public long getActive() {
        return active;
    }

    public int getCritical() {
        return critical;
    }

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public long getTests() {
        return tests;
    }

    public double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public long getPopulation() {
        return population;
    }

    public String getContinent() {
        return continent;
    }

    public double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public ContinentInfo getContinentInfo() {
        return continentInfo;
    }

    public List<String> getCountries() {
        return countries;
    }
}
