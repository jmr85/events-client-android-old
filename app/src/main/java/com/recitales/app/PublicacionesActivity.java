package com.recitales.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.recitales.app.R;
import com.recitales.app.models.APIError;
import com.recitales.app.models.ApiClientInsertar;
import com.recitales.app.models.EditarRequest;
import com.recitales.app.models.InsertarPublicacionRequest;
import com.recitales.app.models.InsertarPublicacionResponse;
import com.recitales.app.models.Publicaciones;
import com.recitales.app.interfaces.PublicaconesAPI;
import com.recitales.app.models.ApiClientEditar;
import com.recitales.app.models.EditarResponse;
import com.google.gson.Gson;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

import android.widget.Toast;

public class PublicacionesActivity extends AppCompatActivity {

    private TextView mJsonTxtView;


    ListView list;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<Publicaciones> publicaciones = new ArrayList<>();


    //Tres puntos
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Item1) {

            SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
            String id_publicaciones = preferences.getString("publicaciones", "-");

            String ID = id_publicaciones;

            Integer i = Integer.valueOf(ID);

            if (i != 0) {

                Intent perfil = new Intent(this, ViajesActivity.class);
                startActivity(perfil);

            } else {

                Toast.makeText(PublicacionesActivity.this, "No se encuentra suscripto a ninguna publicacion.", Toast.LENGTH_LONG).show();

            }


        }
        if (id == R.id.Item2) {
            Intent perfil = new Intent(this, PerfilActivity.class);
            startActivity(perfil);

        }
        if (id == R.id.Item3) {
            finish();
            System.exit(0);

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicaciones);

        TextView usermail = (TextView) findViewById(R.id.t_mail);
        SharedPreferences preferences2 = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String nombre = preferences2.getString("nombre", "-");
        String apellido = preferences2.getString("apellido", "-");
        String mail = preferences2.getString("mail", "-");
        String publicacionesusuario = preferences2.getString("publicaciones", "-");
        usermail.setText(nombre + " " + apellido + " " + mail);


        getPosts();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                muestraDialogo(publicacionesusuario, position);

            }
        });


    }

    private void getPosts() {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);


        list = findViewById(R.id.list);
        list.setAdapter(arrayAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gotest27.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PublicaconesAPI publicaconesAPI = retrofit.create(PublicaconesAPI.class);
        Call<List<Publicaciones>> call = publicaconesAPI.getPosts();

        call.enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                for (Publicaciones post : response.body()) {

                    String fechastring = post.getEvento().getFecha();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    fechastring = formatter.format(date);

                    Integer h = Integer.valueOf(post.getCapacidad());
                    if (h == 0) {

                    } else {
                        titles.add("Partido: " + post.getEvento().getNombre() + "\n" + "Fecha: " + fechastring + "\n" + "Lugar: " + post.getEvento().getLugar() + "\n" + "Usuario: " + post.getUsuario().getEmail() + "\n" + "Direcion de Salida: " + post.getSalida() + "\n" + "Capacidad: " + post.getCapacidad() + "\n" + "Vehiculo: " + post.getVehiculo().getMarca() + " " + post.getVehiculo().getModelo() + " " + post.getVehiculo().getPatente() + "\n");
                        publicaciones.add(post);

                    }


                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {
                mJsonTxtView.setText(t.getMessage());
            }
        });
    }


    private void muestraDialogo(String publicacionesusuario, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String fechastring = publicaciones.get(position).getEvento().getFecha();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        fechastring = formatter.format(date);

        builder.setMessage("" + publicaciones.get(position).getEvento().getNombre() + "\n" + fechastring + "\n" + publicaciones.get(position).getEvento().getLugar() + "\n" + publicaciones.get(position).getID())
                .setIcon(android.R.drawable.btn_star_big_on)
                .setTitle("¿Desea suscribirse al siguiente partido?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        String idevento = publicaciones.get(position).getID();

                        Integer i = Integer.valueOf(publicacionesusuario);
                        Integer s = Integer.valueOf(idevento);


                        if (i == s) {
                            Toast.makeText(PublicacionesActivity.this, "Atencion!" + "\n" + "Ya se encuentra suscripto al partido: " + publicaciones.get(position).getEvento().getNombre() + "\n" + "Por favor seleccione otro partido.", Toast.LENGTH_LONG).show();

                        } else {

                            modificarUsuario(idevento, position);
                            modificarPublicacion(idevento, position);

                            recreate();

                        }


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void modificarPublicacion(String idevento, int position) {

        String capacidad = "1";
        String ID;
        ID = idevento;

        Integer s = Integer.valueOf(capacidad);


        InsertarPublicacionRequest insertarPublicacionRequest = new InsertarPublicacionRequest();
        insertarPublicacionRequest.setCapacidad(s);

        Call<InsertarPublicacionResponse> insertarPublicacionesResponseCall = ApiClientInsertar.userServiceInsertarPublicaciones().userServiceInsertarPublicaciones(insertarPublicacionRequest, ID);
        insertarPublicacionesResponseCall.enqueue(new Callback<InsertarPublicacionResponse>() {
            @Override
            public void onResponse(Call<InsertarPublicacionResponse> call, Response<InsertarPublicacionResponse> response) {
                if (response.isSuccessful()) {


                } else {

                    APIError message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), APIError.class);
                        Toast.makeText(PublicacionesActivity.this, "" + message, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<InsertarPublicacionResponse> call, Throwable t) {

                Toast.makeText(PublicacionesActivity.this, "Intente nuevamente...", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void modificarUsuario(String idevento, int position) {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String idusuario = preferences.getString("idusuario", "-");
        String publicaciones2 = preferences.getString("publicaciones", "-");


        EditarRequest editarRequest = new EditarRequest();
        editarRequest.setIdusuario(idusuario);
        editarRequest.setPublicacion(idevento);

        Call<EditarResponse> editarResponseCall = ApiClientEditar.userServiceEdit().userEdit(editarRequest, idusuario);
        editarResponseCall.enqueue(new Callback<EditarResponse>() {
            @Override
            public void onResponse(Call<EditarResponse> call, Response<EditarResponse> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(PublicacionesActivity.this, "Suscripcion Exitosa!" + "\n" + "Partido: " + publicaciones.get(position).getEvento().getNombre() + "\n" + "Actualizando viajes...", Toast.LENGTH_LONG).show();

                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("publicaciones", idevento);
                    editor.commit();


                } else {

                    APIError message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), APIError.class);
                        Toast.makeText(PublicacionesActivity.this, "" + message, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EditarResponse> call, Throwable t) {

                Toast.makeText(PublicacionesActivity.this, "Intente nuevamente...", Toast.LENGTH_LONG).show();

            }
        });

    }

}




