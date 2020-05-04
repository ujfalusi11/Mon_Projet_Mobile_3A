package com.vogella.android.projetmobile3a.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vogella.android.projetmobile3a.Constant;
import com.vogella.android.projetmobile3a.R;
import com.vogella.android.projetmobile3a.data.CountryAPI;
import com.vogella.android.projetmobile3a.presentation.model.Country;
import com.vogella.android.projetmobile3a.presentation.view.MainActivity;
import com.vogella.android.projetmobile3a.presentation.view.RestCountryResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
            sort(countryList,countryTCCComparator_des);
            view.showList(countryList);
        }else {
            ApiCall();
        }
    }

    private void ApiCall(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CountryAPI CountryAPI = retrofit.create(CountryAPI.class);

        Call<RestCountryResponse> call = CountryAPI.getCountryResponse();
        call.enqueue(new Callback<RestCountryResponse>() {
            @Override
            public void onResponse(Call<RestCountryResponse> call, Response<RestCountryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Country> CountryList = response.body().getCountries();
                    saveList(CountryList);
                    view.showList(CountryList);
                }else {
                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestCountryResponse> call, Throwable t) {
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
    public static Comparator<Country> countryTCCComparator_des = new Comparator<Country>(){
        public int compare (Country country1 , Country country2 ){
            double countryID1 = country1.getTotalConfirmed();
            double countryID2 = country2.getTotalConfirmed();

            //ascending order
            return Double.compare(countryID2,countryID1);
        }
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

    }
    public void onButtonAClick(){

    }
    public void onButtonBClick(){

    }
}
