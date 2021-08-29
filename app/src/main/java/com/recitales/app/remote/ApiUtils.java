package com.recitales.app.remote;

import com.recitales.app.interfaces.UserService;

public class ApiUtils {

    public static final String BASE_URL = "https://vasalaapp.herokuapp.com/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}
