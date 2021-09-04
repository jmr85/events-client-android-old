package com.recitales.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.recitales.app.models.ApiErrorLogin;
import com.recitales.app.models.ApiUtilsLogin;
import com.recitales.app.models.Login;
import com.recitales.app.interfaces.UserServiceLogin;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.recitales.app.models.RequestLogin;
import com.recitales.app.models.ResponseLogin;

import java.io.IOException;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InicioActivity extends AppCompatActivity {

    EditText m_mail, c_clave;
    Button btn_login;
    UserServiceLogin userServiceLogin1;

    //ArrayList<Login> login = new ArrayList<>();
    //ResponseLogin post = new Login();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_mail = (EditText) findViewById(R.id.i_mail);

        TextInputLayout textinputlayout = findViewById(R.id.i_pass);
        c_clave = textinputlayout.getEditText();


        btn_login = (Button) findViewById(R.id.btn_login);
        userServiceLogin1 = ApiUtilsLogin.getUserServiceLogin1();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = m_mail.getText().toString();
                String clave = c_clave.getText().toString();

                doLogin(mail, clave);

            }
        });

    }

    public void doLogin(final String mail, String clave) {
        RequestLogin requestLogin = new RequestLogin(mail, clave);

        Call<ResponseLogin> call = userServiceLogin1.login(requestLogin);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        //String post = "";


                       //post = response.body().toString();

                       // String m_mail = post.getMail();

                       /* SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("nombre", n_nombre);
                        editor.putString("clave", c_clave);
                        editor.putString("mail", m_mail);

                        editor.commit();*/

                        Toast.makeText(InicioActivity.this, "Bienvenido!  " + m_mail + "\n" + "Cargando publicaciones...", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(InicioActivity.this, PublicacionesActivity.class);
                        //intent.putExtra("mail", m_mail);
                        //intent.putExtra("idusuario", n_nombre);

                        startActivity(intent);


                    } else {
                       Toast.makeText(InicioActivity.this, "Mail o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Toast.makeText(InicioActivity.this, "Mail o contraseña incorrecta.", Toast.LENGTH_SHORT).show();

                    ApiErrorLogin message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), ApiErrorLogin.class);
                        Toast.makeText(InicioActivity.this, "" + message.getMensaje(), Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(InicioActivity.this, "Error.", Toast.LENGTH_LONG).show();

                    }


                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(InicioActivity.this, "Intente nuevamente...", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void Registrarse(View view) {
        Intent registrarse = new Intent(this, RegistrarseActivity.class);
        startActivity(registrarse);

    }
}