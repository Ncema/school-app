 package com.example.digiteqsolutions.schoolapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.digiteqsolutions.schoolapp.adapters.HouseAdapter;
import com.example.digiteqsolutions.schoolapp.model.HousesResponseModel;
import com.example.digiteqsolutions.schoolapp.service.SchoolService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class MainActivity extends AppCompatActivity {
    private final static String API_KEY = "$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
    BottomNavigationView navigation;
     public static String BaseUrl = "https://www.potterapi.com/v1/";
     List<HousesResponseModel> listOfHouses = new ArrayList<>();
     private TextView textviewName, textViewMascout, textViewHeadOfHouse,textViewHouseGhost,textViewFounder,textViewV,textViewSchool,textViewMembers;
     private Button btnDismiss;
     TextView txtdisplay;
     RecyclerView recyclerView;
     HouseAdapter houseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigationView);
        btnDismiss = findViewById(R.id.btnDismiss);
        txtdisplay = findViewById(R.id.display);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initViews();
    }

     private void initViews(){
         recyclerView = findViewById(R.id.list_of_houses);
         recyclerView.setHasFixedSize(true);
         RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
         recyclerView.setLayoutManager(layoutManager);
         getHouses();
     }


     private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem item) {

             switch (item.getItemId()) {
                 case R.id.navigation_house:
                     Intent intent = new Intent(MainActivity.this, HouseActivity.class);
                     startActivity(intent);
                     return true;

                 case R.id.navigation_character:
                     Intent intented = new Intent(MainActivity.this, CharacterActivity.class);
                     startActivity(intented);
                     return true;

                 case R.id.navigation_spell:
                     Intent intents = new Intent(MainActivity.this, SpellActivity.class);
                     startActivity(intents);
                     return true;
             }
             return false;
         }
     };


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
                     listOfHouses = list;
                     for (int x = 0; x < list.size(); x++) {
                         houseAdapter = new HouseAdapter(list);
                         recyclerView.setAdapter(houseAdapter);
                         houseAdapter.setOnItemClickListener(new HouseAdapter.OnItemClickListener() {
                             @Override
                             public void onItemClick(View itemView, int position) {
                                 int pos = position;
                                 String data = listOfHouses.get(pos)._id.toString();
                                 getHouseById(data);
                             }
                         });
                     }
                 }

             }

             @Override
             public void onFailure(Call<List<HousesResponseModel>> call, Throwable t) {
                 Toast.makeText(MainActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();

             }
         });

     }


     public void getHouseById(String newHouseId){

         String idOfHouse = newHouseId;
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(BaseUrl)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         SchoolService service = retrofit.create(SchoolService.class);
         Call<HousesResponseModel> call = service.getHouseDetails(idOfHouse,API_KEY);
         call.enqueue(new Callback<HousesResponseModel>() {
             @Override
             public void onResponse(Call<HousesResponseModel> call, Response<HousesResponseModel> response) {
                 if(response.code()==200) {
                     HousesResponseModel housesResponseModel = response.body();
                     LayoutInflater li = LayoutInflater.from(MainActivity.this);
                     View view = li.inflate(R.layout.custom_popup, null);

                     final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
                     alertDialogBuilder.setView(view);
                     textviewName = view.findViewById(R.id.item_name);
                     textViewMascout = view.findViewById(R.id.item_mascout);
                     textViewHeadOfHouse = view.findViewById(R.id.item_headOfHouse);
                     textViewHouseGhost = view.findViewById(R.id.item_headOfHouse);
                     textViewFounder = view.findViewById(R.id.item_founder);
                     textViewV = view.findViewById(R.id.item_v);
                     textViewSchool = view.findViewById(R.id.item_school);
                     textViewMembers = view.findViewById(R.id.item_members);
                     final AlertDialog show = alertDialogBuilder.show();
                     alertDialogBuilder.create();
                     btnDismiss.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             show.dismiss();

                         }
                     });

                 }

             }

             @Override
             public void onFailure(Call<HousesResponseModel>call, Throwable t) {
                 Toast.makeText(MainActivity.this,"Unable to fetch data ",Toast.LENGTH_LONG).show();
             }
         });
     }


}
