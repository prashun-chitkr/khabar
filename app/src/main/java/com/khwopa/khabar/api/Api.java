package com.khwopa.khabar.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static Retrofit api;
    private static final String BASE_URL = "https://newsapi.org/";

    public static Retrofit getRetrofitInstance() {
        if(api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return api;
    }
}
