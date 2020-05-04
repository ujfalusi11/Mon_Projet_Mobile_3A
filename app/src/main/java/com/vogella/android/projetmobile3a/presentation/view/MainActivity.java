package com.vogella.android.projetmobile3a.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.widget.Toast;

import com.vogella.android.projetmobile3a.presentation.Singletons;
import com.vogella.android.projetmobile3a.presentation.controller.MainController;
import com.vogella.android.projetmobile3a.presentation.model.Country;
import com.vogella.android.projetmobile3a.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private MainController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainController(
                 this,
                Singletons.getGson(),
                Singletons.getSharedPreferences(getApplicationContext())

        );
        controller.onStart();

    }


    public void showList(List<Country> CountryList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new ListAdapter(CountryList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Country country) {
                controller.onItemClick(country);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Country country) {
        Toast.makeText(getApplicationContext(), "Navigate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(this);

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
        List<Country> countryList = controller.getDataFromCache();

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


}
