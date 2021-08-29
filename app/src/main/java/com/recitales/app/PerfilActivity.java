package com.recitales.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.recitales.app.R;
import com.recitales.app.models.APIError;
import com.recitales.app.models.EditarRequest;
import com.recitales.app.models.ApiClientEditar;
import com.recitales.app.models.EditarResponse;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {


    private EditText nombre, apellido, mail, clave;

    Button btn_editar;


    public static int MILISEGUNDOS_ESPERA = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);


        nombre = (EditText) findViewById(R.id.e_nombre);
        apellido = (EditText) findViewById(R.id.e_apellido);
        mail = (EditText) findViewById(R.id.e_mail);
        TextInputLayout textinputlayout = findViewById(R.id.e_password2);
        clave = textinputlayout.getEditText();


        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String id = preferences.getString("idusuario", "-");
        String n_nombre = preferences.getString("nombre", "-");
        String a_apellido = preferences.getString("apellido", "-");
        String m_mail = preferences.getString("mail", "-");
        String c_clave = preferences.getString("clave", "-");


        nombre.setText(n_nombre);
        apellido.setText(a_apellido);
        mail.setText(m_mail);
        clave.setText(c_clave);

        btn_editar = findViewById(R.id.btn_editar);


        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editar(id);
            }
        });
    }

    public void editar(String id) {
        EditarRequest editarRequest = new EditarRequest();
        editarRequest.setIdusuario(id);
        editarRequest.setNombre(nombre.getText().toString());
        editarRequest.setApellido(apellido.getText().toString());
        editarRequest.setClave(clave.getText().toString());


        Call<EditarResponse> editarResponseCall = ApiClientEditar.userServiceEdit().userEdit(editarRequest, id);
        editarResponseCall.enqueue(new Callback<EditarResponse>() {
            @Override
            public void onResponse(Call<EditarResponse> call, Response<EditarResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(PerfilActivity.this, "Cambios efectuados correctamente.La aplicacion se reiniciara en 5 segundos.", Toast.LENGTH_LONG).show();
                    esperarYCerrar(MILISEGUNDOS_ESPERA);


                } else {

                    APIError message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), APIError.class);
                        Toast.makeText(PerfilActivity.this, "" + message, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<EditarResponse> call, Throwable t) {

                Toast.makeText(PerfilActivity.this, "Intente nuevamente - Throwable" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    public void esperarYCerrar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(PerfilActivity.this, InicioActivity.class);
                startActivity(intent);

            }
        }, milisegundos);
    }


}



