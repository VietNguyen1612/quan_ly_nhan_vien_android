package com.example.pe.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://64ae568cc85640541d4cdae8.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
