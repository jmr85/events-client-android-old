package com.recitales.app.interfaces;

import com.recitales.app.models.ResponseLogin;
import com.recitales.app.models.RequestLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserService {

    @POST("usuarios/")
    Call<ResponseLogin> userLoginRegister (@Body RequestLogin loginRequest);


}
