package com.recitales.app.interfaces;

import com.recitales.app.models.Publicaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PublicaconesAPI {

    @GET("publicaciones")
    Call<List<Publicaciones>> getPosts();

}



