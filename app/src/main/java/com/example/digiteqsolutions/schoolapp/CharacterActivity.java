package com.example.digiteqsolutions.schoolapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digiteqsolutions.schoolapp.Pojo.Pojos;
import com.example.digiteqsolutions.schoolapp.adapters.CharacterAdapter;
import com.example.digiteqsolutions.schoolapp.model.CharactersResponseModel;
import com.example.digiteqsolutions.schoolapp.service.SchoolService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CharacterActivity extends AppCompatActivity {
    private final static String API_KEY = "$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
    public static String BaseUrl = "https://www.potterapi.com/v1/";
    RecyclerView recyclerView ;
    CharacterAdapter characterAdapter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    private Button buttonDismiss;
    SharedPreferences sharedpreferences;
    private TextView textviewName, textViewRole, textViewV,textViewMinisteryofmagic,textViewOrderofthephoniex,textViewDumbledorearmy,textViewDeathEater,textViewBloodstatus,textViewSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        initViews();
    }


    private void initViews(){
        recyclerView = findViewById(R.id.list_of_characters);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        getCharacters();
    }

    public void getCharacters() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<List<CharactersResponseModel>> call = service.getCharacters(API_KEY);
        call.enqueue(new Callback<List<CharactersResponseModel>>() {
            @Override
            public void onResponse(Call<List<CharactersResponseModel>> call, Response<List<CharactersResponseModel>> response) {
                if(response.code()==200) {
                    List<CharactersResponseModel> list = response.body();
                    for (int x = 0; x < list.size(); x++) {
                        characterAdapter = new CharacterAdapter(list);
                        recyclerView.setAdapter(characterAdapter);

                        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(list.get(x)._id);
                        editor.putString("id",json);
                        editor.commit();

                        Pojos pjs = new Pojos();
                        pjs.characterid = json;

                        characterAdapter.setOnItemClickListener(new CharacterAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                getCharacterById();
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(Call<List<CharactersResponseModel>> call, Throwable t) {
                Toast.makeText(CharacterActivity.this,"Unable to fetch data",Toast.LENGTH_LONG).show();
            }
        });

    }


    public void getCharacterById(){

        //getting the saved id from shared preference
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String idOfCharacter = "5a0fa4daae5bc100213c232e";

        Gson gson = new Gson();
        String data = sharedPrefs.getString("id", "");
        Type type = new TypeToken<List<CharactersResponseModel>>() {}.getType();
        List<CharactersResponseModel> arrayList = gson.fromJson(data, type);
        Toast.makeText(this, "List"+data, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SchoolService service = retrofit.create(SchoolService.class);
        Call<CharactersResponseModel> call = service.getCharacterDtails(idOfCharacter,API_KEY);
        call.enqueue(new Callback<CharactersResponseModel>() {
            @Override
            public void onResponse(Call<CharactersResponseModel> call, Response<CharactersResponseModel> response) {
                if(response.code()==200) {
                    CharactersResponseModel characterInfo = response.body();
                    LayoutInflater li = LayoutInflater.from(CharacterActivity.this);
                    View view = li.inflate(R.layout.custom_popup, null);

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(CharacterActivity.this, R.style.AlertDialogStyle);
                    alertDialogBuilder.setView(view);

                    textviewName = view.findViewById(R.id.item_name);
                    textViewRole = view.findViewById(R.id.item_role);
                    textViewV = view.findViewById(R.id.item_v);
                    textViewMinisteryofmagic = view.findViewById(R.id.item_ministeryofmagic);
                    textViewOrderofthephoniex = view.findViewById(R.id.item_orderofthephoniex);
                    textViewDumbledorearmy = view.findViewById(R.id.item_dumbledorearmy);
                    textViewBloodstatus = view.findViewById(R.id.item_bloodstatus);
                    textViewSpecies = view.findViewById(R.id.item_species);
                    textViewDeathEater = view.findViewById(R.id.item_deathEater);
                    buttonDismiss = view.findViewById(R.id.btnDismiss);
                    textviewName.setText("Name:"+characterInfo.name);
                    textViewRole.setText("Role:" +characterInfo.role);
                    textViewV.setText("Version:"+String.valueOf(characterInfo.__v));
                    textViewMinisteryofmagic.setText("Ministery of magic :" +String.valueOf(characterInfo.ministryOfMagic));
                    textViewOrderofthephoniex.setText("Phoniex:" +String.valueOf(characterInfo.orderOfThePhoenix));
                    textViewDumbledorearmy.setText("Doubledorearmy:"+ String.valueOf(characterInfo.dumbledoresArmy));
                    textViewBloodstatus.setText("Blood status :" +characterInfo.bloodStatus);
                    textViewSpecies.setText( "Species :"+characterInfo.species);
                    textViewDeathEater.setText("DeathEater:" +String.valueOf(characterInfo.deathEater));
                    final AlertDialog show = alertDialogBuilder.show();
                    alertDialogBuilder.create();
                    buttonDismiss.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            show.dismiss();

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<CharactersResponseModel>call, Throwable t) {
                Toast.makeText(CharacterActivity.this,"Unable to fetch data ",Toast.LENGTH_LONG).show();
            }
        });
    }
}
