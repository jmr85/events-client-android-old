package com.recitales.app.interfaces;

import com.recitales.app.models.EditarRequest;
import com.recitales.app.models.EditarResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UserServiceEditarPerfil {

    @PATCH("usuarios/{idusuario}")

    Call<EditarResponse> userEdit(@Body EditarRequest editarRequest, @Path("idusuario") String idusuario);

}
