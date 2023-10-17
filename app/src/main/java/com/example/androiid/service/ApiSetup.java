package com.example.androiid.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;


public class ApiSetup {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://hiretutor-backend.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }
}
