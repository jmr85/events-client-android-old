package com.recitales.app.interfaces;

import com.recitales.app.models.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserServiceLogin {

    @GET("usuarios/")

    Call <List<Login>> login(@Query("mail") String mail, @Query("clave") String clave);

}
