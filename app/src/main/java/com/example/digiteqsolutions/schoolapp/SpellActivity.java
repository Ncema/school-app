package com.example.digiteqsolutions.schoolapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.digiteqsolutions.schoolapp.adapters.SpellAdapter;
import com.example.digiteqsolutions.schoolapp.model.SpellResponseModel;
import com.example.digiteqsolutions.schoolapp.service.SchoolService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpellActivity extends AppCompatActivity {
    private final static String API_KEY = "$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
    BottomNavigationView navigation;
    public static String BaseUrl = "https://www.potterapi.com/v1/";
    RecyclerView recyclerView ;
    SpellAdapter spellAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);
        initViews();

    }

    private void initViews(){
        recyclerView = findViewById(R.id.list_of_spell);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getSpell();
    }


    public void getSpell() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<List<SpellResponseModel>> call = service.getSpells(API_KEY);
        call.enqueue(new Callback<List<SpellResponseModel>>() {
            @Override
            public void onResponse(Call<List<SpellResponseModel>> call, Response<List<SpellResponseModel>> response) {
                if(response.code()==200) {
                    List<SpellResponseModel> list = response.body();
                    for (int x = 0; x < list.size(); x++) {
                        spellAdapter = new SpellAdapter(list);
                        recyclerView.setAdapter(spellAdapter);
                    }
                }

            }
            @Override
            public void onFailure(Call<List<SpellResponseModel>> call, Throwable t) {
                Toast.makeText(SpellActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
