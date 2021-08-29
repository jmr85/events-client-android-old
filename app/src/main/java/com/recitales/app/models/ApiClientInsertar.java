package com.recitales.app.models;

import com.recitales.app.interfaces.UserServiceVerViajes;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientInsertar {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient OkHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://gotest27.herokuapp.com/")
                .client(OkHttpClient)
                .build();

        return retrofit;
    }

    public static UserServiceVerViajes userServiceInsertarPublicaciones (){
        UserServiceVerViajes userServiceInsertarPublicaciones = getRetrofit().create(UserServiceVerViajes.class);
        return userServiceInsertarPublicaciones;

    }


}
