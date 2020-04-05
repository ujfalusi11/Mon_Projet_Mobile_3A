package com.vogella.android.projetmobile3a;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showList();
        ApiCall();
        }

    private void showList() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        RecyclerView.Adapter mAdapter = new ListAdapter(input);
        recyclerView.setAdapter(mAdapter);

        //Swipe to dismiss
       /* ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                            target) {
                        return false;
                    }
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        input.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);*/
    }

    static final String BASE_URL = "https://api.covid19api.com/summary/";

    private void ApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CountryAPI CountryAPI = retrofit.create(CountryAPI.class);

        Call<RestCountryResponse> call = CountryAPI.getCountryResponse();
        call.enqueue(new Callback<RestCountryResponse>() {
            @Override
            public void onResponse(Call<RestCountryResponse> call, Response<RestCountryResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Country> CountryList = response.body().getCountries();
                    Toast.makeText(getApplicationContext(), "API Success", Toast.LENGTH_SHORT).show();
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestCountryResponse> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
