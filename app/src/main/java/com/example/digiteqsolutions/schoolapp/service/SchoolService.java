package com.example.digiteqsolutions.schoolapp.service;

import com.example.digiteqsolutions.schoolapp.model.CharactersResponseModel;
import com.example.digiteqsolutions.schoolapp.model.HousesResponseModel;
import com.example.digiteqsolutions.schoolapp.model.SpellResponseModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SchoolService {
    @GET("characters")
    Call<List<CharactersResponseModel>> getCharacters(@Query("key") String apiKey);

    @GET("characters/{id}")
    Call<CharactersResponseModel> getCharacterDtails(@Path("id") String id, @Query("key") String apiKey);

     @GET("houses")
     Call<List<HousesResponseModel>> getHouses(@Query("key") String apiKey);

    @GET("houses/{id}")
    Call<HousesResponseModel> getHouseDetails(@Path("id") String id, @Query("key") String apiKey);

    @GET("spells")
    Call<List<SpellResponseModel>> getSpells(@Query("key") String apiKey);




}
