package com.example.projectppb.RetrofitUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String base_url ="http://192.168.43.83/CelenganApi/api/";
    private Retrofit retrofit;
    private static RetrofitClient instance;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (instance == null)
        {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public CelenganApi getApi(){
        return retrofit.create(CelenganApi.class);
    }
}
