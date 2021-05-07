package com.example.covidapp.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllWorldData {
    @SerializedName("updated")
    @Expose
    private double updated;

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
    private double casesPerOneMillion;

    @SerializedName("deathsPerOneMillion")
    @Expose
    private double deathsPerOneMillion;

    @SerializedName("tests")
    @Expose
    private int tests;

    @SerializedName("testsPerOneMillion")
    @Expose
    private double testsPerOneMillion;

    @SerializedName("population")
    @Expose
    private long population;

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

    @SerializedName("affectedCountries")
    @Expose
    private int affectedCountries;

    public AllWorldData(double updated, int cases, int todayCases, int deaths, int todayDeaths, int recovered, int todayRecovered, int active, int critical, double casesPerOneMillion, double deathsPerOneMillion, int tests, double testsPerOneMillion, long population, int oneCasePerPeople, int oneDeathPerPeople, int oneTestPerPeople, double activePerOneMillion, double recoveredPerOneMillion, double criticalPerOneMillion, int affectedCountries) {
        this.updated = updated;
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
        this.oneCasePerPeople = oneCasePerPeople;
        this.oneDeathPerPeople = oneDeathPerPeople;
        this.oneTestPerPeople = oneTestPerPeople;
        this.activePerOneMillion = activePerOneMillion;
        this.recoveredPerOneMillion = recoveredPerOneMillion;
        this.criticalPerOneMillion = criticalPerOneMillion;
        this.affectedCountries = affectedCountries;
    }

    public double getUpdated() {
        return updated;
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

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public int getTests() {
        return tests;
    }

    public double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public long getPopulation() {
        return population;
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

    public int getAffectedCountries() {
        return affectedCountries;
    }

    @Override
    public String toString() {
        return "AllWorldData{" +
                "updated=" + updated +
                ", cases=" + cases +
                ", todayCases=" + todayCases +
                ", deaths=" + deaths +
                ", todayDeaths=" + todayDeaths +
                ", recovered=" + recovered +
                ", todayRecovered=" + todayRecovered +
                ", active=" + active +
                ", critical=" + critical +
                ", casesPerOneMillion=" + casesPerOneMillion +
                ", deathsPerOneMillion=" + deathsPerOneMillion +
                ", tests=" + tests +
                ", testsPerOneMillion=" + testsPerOneMillion +
                ", population=" + population +
                ", oneCasePerPeople=" + oneCasePerPeople +
                ", oneDeathPerPeople=" + oneDeathPerPeople +
                ", oneTestPerPeople=" + oneTestPerPeople +
                ", activePerOneMillion=" + activePerOneMillion +
                ", recoveredPerOneMillion=" + recoveredPerOneMillion +
                ", criticalPerOneMillion=" + criticalPerOneMillion +
                ", affectedCountries=" + affectedCountries +
                '}';
    }
}
