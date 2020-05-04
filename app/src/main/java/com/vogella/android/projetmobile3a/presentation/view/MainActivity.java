package com.vogella.android.projetmobile3a.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vogella.android.projetmobile3a.Constant;
import com.vogella.android.projetmobile3a.presentation.model.Country;
import com.vogella.android.projetmobile3a.data.CountryAPI;
import com.vogella.android.projetmobile3a.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private SharedPreferences sharedPreferences;
    private Gson gson;
   // List<Country> countryList = new List<Country>;
    //adapter = new  RecyclerView.Adapter(countryList);
   // ListAdapter adapter = new ListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("application_covid19", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();

        List<Country> countryList = getDataFromCache();
        if (countryList != null){
            sort(countryList,countryTCCComparator_des);
            showList(countryList);
        }else {
            ApiCall();
        }



        }

    private List<Country> getDataFromCache() {
        String jsonCountry = sharedPreferences.getString(Constant.KEY_Country_List,null);

        if (jsonCountry == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Country>>(){}.getType();
            return gson.fromJson(jsonCountry,listType);
        }

    }

    private void showList(List<Country> CountryList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        RecyclerView.Adapter mAdapter = new ListAdapter(CountryList);
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
                    saveList(CountryList);
                    showList(CountryList);
                }else {
                    showError();
                }
            }

            private void saveList(List<Country> countryList) {
                String jsonString = gson.toJson(countryList);
                sharedPreferences
                        .edit()
                        .putString(Constant.KEY_Country_List, jsonString)
                        .apply();

                Toast.makeText(getApplicationContext(), "Liste sauvegardée", Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(this);

        // Associate searchable configuration with the SearchView
       /* SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/

        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String userInput = newText.toLowerCase();
        List<Country> newList = new ArrayList<>();
        List<Country> countryList = getDataFromCache();
       /// Country currentCountry = countryList.get(position);
        assert countryList != null;
        for(Country name : countryList)
        {
            if (name.getCountry().toLowerCase().contains(userInput))
            {
                newList.add(name);
            }
        }
        showList(newList);
        return true;
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
}
