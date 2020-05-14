package com.vogella.android.projetmobile3a.presentation.controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vogella.android.projetmobile3a.presentation.Constant;
import com.vogella.android.projetmobile3a.presentation.Singletons;
import com.vogella.android.projetmobile3a.presentation.model.Country;
import com.vogella.android.projetmobile3a.presentation.view.MainActivity;
import com.vogella.android.projetmobile3a.presentation.view.RestCountryResponse;

import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainController{

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.view = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){

        List<Country> countryList = getDataFromCache();
        if (countryList != null){
            view.showList(countryList);
        }else {
            ApiCall();
        }
    }

    private void ApiCall(){


        Call<RestCountryResponse> call = Singletons.getCountryApi().getCountryResponse();
        call.enqueue(new Callback<RestCountryResponse>() {
            @Override
            public void onResponse(@NonNull Call<RestCountryResponse> call, @NonNull Response<RestCountryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Country> CountryList = response.body().getCountries();
                    saveList(CountryList);
                    view.showList(CountryList);
                }else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestCountryResponse> call, @NonNull Throwable t) {
                view.showError();
            }
        });
    }

    private void saveList(List<Country> countryList) {
        String jsonString = gson.toJson(countryList);
        sharedPreferences
                .edit()
                .putString(Constant.KEY_Country_List, jsonString)
                .apply();

        Toast.makeText(view.getApplicationContext(), "Liste sauvegardée", Toast.LENGTH_SHORT).show();

    }

    public List<Country> getDataFromCache() {
        String jsonCountry = sharedPreferences.getString(Constant.KEY_Country_List,null);

        if (jsonCountry == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Country>>(){}.getType();
            return gson.fromJson(jsonCountry,listType);
        }

    }

    //Total confirmed cases descending comparator
    public static Comparator<Country> countryTCCComparator_des = (country1, country2) -> {
        double countryID1 = country1.getTotalConfirmed();
        double countryID2 = country2.getTotalConfirmed();
        //ascending order
        return Double.compare(countryID2,countryID1);
    };

    //Sort
    public static void sort(List<Country> A, Comparator<Country> c){
        Country tmp;
        int i,j;
        for (i = 0 ; i <A.size();i++){
            for (j = i+1 ; j <A.size();j++){
                if(c.compare(A.get(i),A.get(j))>0){
                    tmp=A.get(i);
                    A.set(i,A.get(j));
                    A.set(j,tmp);
                }
            }
        }
    }



    public void onItemClick(Country country){
        view.navigateToDetails(country);
    }
    public void onButtonAClick(){

    }
    public void onButtonBClick(){

    }
}
