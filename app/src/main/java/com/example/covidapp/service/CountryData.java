package com.example.covidapp.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryData {
    @SerializedName("updated")
    @Expose
    private double updated;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryInfo")
    @Expose
    private CountryInfo countryInfo;
    @SerializedName("cases")
    @Expose
    private int cases;
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
    private int recovered;
    @SerializedName("todayRecovered")
    @Expose
    private int todayRecovered;
    @SerializedName("active")
    @Expose
    private int active;
    @SerializedName("critical")
    @Expose
    private int critical;
    @SerializedName("casesPerOneMillion")
    @Expose
    private int casesPerOneMillion;
    @SerializedName("deathsPerOneMillion")
    @Expose
    private int deathsPerOneMillion;
    @SerializedName("tests")
    @Expose
    private int tests;
    @SerializedName("testsPerOneMillion")
    @Expose
    private int testsPerOneMillion;
    @SerializedName("population")
    @Expose
    private int population;
    @SerializedName("continent")
    @Expose
    private String continent;
    @SerializedName("oneCasePerPeople")
    @Expose
    private int oneCasePerPeople;
    @SerializedName("oneDeathPerPeople")
    @Expose
    private int oneDeathPerPeople;
    @SerializedName("oneTestPerPeople")
    @Expose
    private int oneTestPerPeople;
    @SerializedName("activePerOneMillion")
    @Expose
    private double activePerOneMillion;
    @SerializedName("recoveredPerOneMillion")
    @Expose
    private double recoveredPerOneMillion;
    @SerializedName("criticalPerOneMillion")
    @Expose
    private double criticalPerOneMillion;

    public CountryData(double updated, String country, CountryInfo countryInfo, int cases, int todayCases, int deaths, int todayDeaths, int recovered, int todayRecovered, int active, int critical, int casesPerOneMillion, int deathsPerOneMillion, int tests, int testsPerOneMillion, int population, String continent, int oneCasePerPeople, int oneDeathPerPeople, int oneTestPerPeople, double activePerOneMillion, double recoveredPerOneMillion, double criticalPerOneMillion) {
        this.updated = updated;
        this.country = country;
        this.countryInfo = countryInfo;
        this.cases = cases;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.critical = critical;
        this.casesPerOneMillion = casesPerOneMillion;
        this.deathsPerOneMillion = deathsPerOneMillion;
        this.tests = tests;
        this.testsPerOneMillion = testsPerOneMillion;
        this.population = population;
        this.continent = continent;
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneDeathPerPeople = oneDeathPerPeople;
        this.oneTestPerPeople = oneTestPerPeople;
        this.activePerOneMillion = activePerOneMillion;
        this.recoveredPerOneMillion = recoveredPerOneMillion;
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public double getUpdated() {
        return updated;
    }

    public String getCountry() {
        return country;
    }

    public CountryInfo getCountryInfo() {
        return countryInfo;
    }

    public int getCases() {
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

    public int getRecovered() {
        return recovered;
    }

    public int getTodayRecovered() {
        return todayRecovered;
    }

    public int getActive() {
        return active;
    }

    public int getCritical() {
        return critical;
    }

    public int getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public int getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public int getTests() {
        return tests;
    }

    public int getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public int getPopulation() {
        return population;
    }

    public String getContinent() {
        return continent;
    }

    public int getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public int getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public int getOneTestPerPeople() {
        return oneTestPerPeople;
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
}
