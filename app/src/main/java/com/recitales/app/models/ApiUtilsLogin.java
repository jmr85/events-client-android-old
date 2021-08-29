package com.recitales.app.models;

import com.recitales.app.interfaces.UserServiceLogin;

public class ApiUtilsLogin {
    public static final String BASE_URL = "https://vasalaapp.herokuapp.com/";


    public static UserServiceLogin getUserServiceLogin1(){
        return RetrofitClient.getClient(BASE_URL).create(UserServiceLogin.class);
    }

}
