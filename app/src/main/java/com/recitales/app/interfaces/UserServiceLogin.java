package com.recitales.app.interfaces;

import com.recitales.app.models.Login;
import com.recitales.app.models.RequestLogin;
import com.recitales.app.models.ResponseLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserServiceLogin {

    @POST("login/")
    Call <ResponseLogin> login(@Body RequestLogin requestLogin);

}
