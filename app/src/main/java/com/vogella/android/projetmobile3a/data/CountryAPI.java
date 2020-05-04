package com.vogella.android.projetmobile3a.data;

import com.vogella.android.projetmobile3a.presentation.view.RestCountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountryAPI {
    @GET("/summary")
    Call<RestCountryResponse> getCountryResponse();
}
