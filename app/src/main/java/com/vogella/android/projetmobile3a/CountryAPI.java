package com.vogella.android.projetmobile3a;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CountryAPI {
    @GET("/summary")
    Call<RestCountryResponse> getCountryResponse();
}