package com.vogella.android.projetmobile3a.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vogella.android.projetmobile3a.R;
import com.vogella.android.projetmobile3a.presentation.Singletons;
import com.vogella.android.projetmobile3a.presentation.controller.MainController;
import com.vogella.android.projetmobile3a.presentation.model.Country;

import static java.lang.Integer.parseInt;

public class CountryInfo extends AppCompatActivity {

    private TextView CountryName;
    private TextView CountryCode;
    private TextView CountryTotalConfirmed;
    private TextView CountryNewConfirmed;
    private TextView CountryTotalDeaths;
    private TextView CountryNewDeaths;
    private TextView CountryTotalRecovered;
    private TextView CountryNewRecovered;


    //String countString = CountryTotalConfirmed.getTota().toString();
    //Integer count = Integer.parseInt(countString);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_info);

        CountryName = findViewById(R.id.name);
        CountryCode = findViewById(R.id.code);
        CountryTotalConfirmed = findViewById(R.id.TCn);
        CountryNewConfirmed = findViewById(R.id.NCn);
        CountryTotalDeaths = findViewById(R.id.TDn);
        CountryNewDeaths = findViewById(R.id.NDn);
        CountryTotalRecovered = findViewById(R.id.TRn);
        CountryNewRecovered = findViewById(R.id.NRn);
        Intent intent = getIntent();
        String CountryJson = intent.getStringExtra("CountryKey)");
        Country country = Singletons.getGson().fromJson(CountryJson, Country.class);
        showDetail(country);
    }

    private void showDetail(Country country) {
        CountryName.setText(country.getName());
        CountryCode.setText(country.getCountryCode());
        CountryTotalConfirmed.setText(String.valueOf(country.getTotalConfirmed()));
        CountryNewConfirmed.setText(String.valueOf(country.getNewConfirmed()));
        CountryTotalDeaths.setText(String.valueOf(country.getTotalDeaths()));
        CountryNewDeaths.setText(String.valueOf(country.getNewDeaths()));
        CountryTotalRecovered.setText(String.valueOf(country.getTotalRecovered()));
        CountryNewRecovered.setText(String.valueOf(country.getNewRecovered()));
    }


}
