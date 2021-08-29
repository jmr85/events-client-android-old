package com.recitales.app.models;

import com.recitales.app.interfaces.UserServiceLogin;

public class ApiUtilsLogin {
    public static final String BASE_URL = "https://salty-bayou-21277.herokuapp.com/";


    public static UserServiceLogin getUserServiceLogin1(){
        return RetrofitClient.getClient(BASE_URL).create(UserServiceLogin.class);
    }

}
