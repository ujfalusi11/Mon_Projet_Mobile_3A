package com.vogella.android.projetmobile3a.presentation;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vogella.android.projetmobile3a.Constant;
import com.vogella.android.projetmobile3a.data.CountryAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static CountryAPI CountryAPIInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static CountryAPI getCountryApi(){
        if(CountryAPIInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            CountryAPIInstance = retrofit.create(CountryAPI.class);
        }
        return CountryAPIInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance== null){
            sharedPreferencesInstance = context.getSharedPreferences("application_covid19", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
