package com.example.digiteqsolutions.schoolapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.digiteqsolutions.schoolapp.adapters.HouseAdapter;
import com.example.digiteqsolutions.schoolapp.model.HousesResponseModel;
import com.example.digiteqsolutions.schoolapp.service.SchoolService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HouseActivity extends AppCompatActivity {
    private final static String API_KEY = "$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
    public static String BaseUrl = "https://www.potterapi.com/v1/";
    RecyclerView recyclerView;
    HouseAdapter houseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house);
        initViews();

    }

    private void initViews(){
        recyclerView = findViewById(R.id.list_of_houses);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getHouses();
    }



    public void getHouses() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<List<HousesResponseModel>> call = service.getHouses(API_KEY);
        call.enqueue(new Callback<List<HousesResponseModel>>() {
            @Override
            public void onResponse(Call<List<HousesResponseModel>> call, Response<List<HousesResponseModel>> response) {
                if(response.code()==200) {
                    List<HousesResponseModel> list = response.body();
                    for (int x = 0; x < list.size(); x++) {
                        Toast.makeText(HouseActivity.this, "" + list, Toast.LENGTH_LONG).show();
                        houseAdapter = new HouseAdapter(list);
                        recyclerView.setAdapter(houseAdapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<HousesResponseModel>> call, Throwable t) {
                Toast.makeText(HouseActivity.this,"ERror"+t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


}





