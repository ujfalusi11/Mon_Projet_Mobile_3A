package com.vogella.android.projetmobile3a;

import java.util.List;

public class RestCountryResponse {
    private List<Country> Countries;
    private String Date;

    List<Country> getCountries() {
        return Countries;
    }

    public String getDate() {
        return Date;
    }
}
