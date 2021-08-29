package com.recitales.app.interfaces;

import com.recitales.app.models.InsertarPublicacionRequest;
import com.recitales.app.models.InsertarPublicacionResponse;
import com.recitales.app.models.Publicaciones;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UserServiceVerViajes {

    @GET("publicaciones/{ID}")
    Call<List<Publicaciones>> verPublicaciones(@Path("ID") String ID);

    @PATCH("publicaciones/{ID}")
    Call<InsertarPublicacionResponse> userServiceInsertarPublicaciones(@Body InsertarPublicacionRequest insertarpublicacionRequest, @Path("ID") String ID);
}
