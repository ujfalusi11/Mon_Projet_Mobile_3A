package com.vogella.android.projetmobile3a.presentation.model;

public class Country {
    private String Differences;
    private String Country;
    private String CountryCode;
    private String Slug;
    private int NewConfirmed;
    private int TotalConfirmed;
    private int NewDeaths;
    private int TotalDeaths;
    private int NewRecovered;
    private int TotalRecovered;
    private String Date;

    public String getDifferences() {
        return Differences;
    }

    public String getCountry() {
        return Country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public String getSlug() {
        return Slug;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public int getNewRecovered() {
        return NewRecovered;
    }

    public int getTotalRecovered() {
        return TotalRecovered;
    }

    public String getDate() {
        return Date;
    }
}
