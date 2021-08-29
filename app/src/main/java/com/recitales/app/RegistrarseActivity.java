package com.recitales.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.recitales.app.R;
import com.recitales.app.models.APIError;
import com.recitales.app.models.RegistrarseRequest;
import com.recitales.app.models.RegistrarseResponse;

import com.recitales.app.models.ApiClientRegister;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrarseActivity extends AppCompatActivity {

    EditText nombre, apellido, password, mail;
    Button btn_registrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        nombre = findViewById(R.id.r_nombre);
        apellido = findViewById(R.id.r_apellido);
        password = findViewById(R.id.r_password);
        mail = findViewById(R.id.r_mail);

        btn_registrar = findViewById(R.id.btn_registrar);


        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registrarse();


            }
        });
    }

    public void registrarse() {
        RegistrarseRequest registrarseRequest = new RegistrarseRequest();
        registrarseRequest.setNombre(nombre.getText().toString());
        registrarseRequest.setApellido(apellido.getText().toString());
        registrarseRequest.setClave(password.getText().toString());
        registrarseRequest.setMail(mail.getText().toString());

        Call<RegistrarseResponse> registrarseResponseCall = ApiClientRegister.getUserServiceRegister().userLoginRegister(registrarseRequest);
        registrarseResponseCall.enqueue(new Callback<RegistrarseResponse>() {
            @Override
            public void onResponse(Call<RegistrarseResponse> call, Response<RegistrarseResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(RegistrarseActivity.this, "Registro Exitoso", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrarseActivity.this, InicioActivity.class);
                    startActivity(intent);

                } else {

                    APIError message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), APIError.class);

                        if (message.getMail() != null) {
                            Toast.makeText(RegistrarseActivity.this, "" + message.getMail(), Toast.LENGTH_SHORT).show();

                        } else if (message.getNombre() != null) {

                            Toast.makeText(RegistrarseActivity.this, "" + message.getNombre(), Toast.LENGTH_SHORT).show();

                        } else if (message.getApellido() != null) {
                            Toast.makeText(RegistrarseActivity.this, "" + message.getApellido(), Toast.LENGTH_SHORT).show();

                        } else if (message.getMensaje() != null) {
                            Toast.makeText(RegistrarseActivity.this, "" + message.getMensaje(), Toast.LENGTH_SHORT).show();

                        } else if (message.getClave() != null) {
                            Toast.makeText(RegistrarseActivity.this, "" + message.getClave(), Toast.LENGTH_SHORT).show();

                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(RegistrarseActivity.this, "Error.", Toast.LENGTH_LONG).show();

                    }


                }
            }


            @Override
            public void onFailure(Call<RegistrarseResponse> call, Throwable t) {

                Toast.makeText(RegistrarseActivity.this, "Intente nuevamente...", Toast.LENGTH_LONG).show();

            }
        });


    }

}