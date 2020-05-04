package com.vogella.android.projetmobile3a.presentation.view;

import com.vogella.android.projetmobile3a.presentation.model.Country;

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
