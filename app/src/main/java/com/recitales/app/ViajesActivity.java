package com.recitales.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.recitales.app.R;
import com.recitales.app.models.APIError;
import com.recitales.app.models.ApiClientInsertar;
import com.recitales.app.models.EditarRequest;
import com.recitales.app.models.InsertarPublicacionRequest;
import com.recitales.app.models.InsertarPublicacionResponse;
import com.recitales.app.models.Publicaciones;
import com.recitales.app.interfaces.UserServiceVerViajes;
import com.recitales.app.models.ApiClientEditar;
import com.recitales.app.models.EditarResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViajesActivity extends AppCompatActivity {

    private TextView mJsonTxtView;

    UserServiceVerViajes userServiceVerViajes;

    ListView list2;
    ArrayList<String> t_titles = new ArrayList<>();
    ArrayList<Publicaciones> p_publicaciones = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String id_publicaciones = preferences.getString("publicaciones", "-");

        String ID = id_publicaciones;

        Integer i = Integer.valueOf(ID);

        verPublicaciones(id_publicaciones);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                muestraDialogo(position);
            }
        });


    }


    private void verPublicaciones(final String id_publicaciones) {

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, t_titles);


        list2 = findViewById(R.id.list_viajes_programados);
        list2.setAdapter(arrayAdapter);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gotest27.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserServiceVerViajes userServiceVerViajes = retrofit.create(UserServiceVerViajes.class);
        Call<List<Publicaciones>> call = userServiceVerViajes.verPublicaciones(id_publicaciones);

        call.enqueue(new Callback<List<Publicaciones>>() {
            @Override
            public void onResponse(Call<List<Publicaciones>> call, Response<List<Publicaciones>> response) {
                for (Publicaciones post : response.body()) {

                    String fechastring = post.getEvento().getFecha();
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    fechastring = formatter.format(date);

                    t_titles.add("Partido: " + post.getEvento().getNombre() + "\n" + "Fecha: " + fechastring + "\n" + "Lugar: " + post.getEvento().getLugar() + "\n" + "Usuario: " + "\n" + "Direcion de Salida: " + post.getSalida() + "\n" + "Capacidad: " + post.getCapacidad() + "\n" + "Vehiculo: " + post.getVehiculo().getMarca() + " " + post.getVehiculo().getModelo() + " " + post.getVehiculo().getPatente() + "\n");
                    p_publicaciones.add(post);

                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Publicaciones>> call, Throwable t) {
                mJsonTxtView.setText(t.getMessage());


            }

        });


    }


    private void muestraDialogo(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        String fechastring = p_publicaciones.get(position).getEvento().getFecha();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        fechastring = formatter.format(date);


        builder.setMessage("" + p_publicaciones.get(position).getEvento().getNombre() + "\n" + fechastring + "\n" + p_publicaciones.get(position).getEvento().getLugar())
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("¿Desea desuscribirse del siguiente partido?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String idevento = p_publicaciones.get(position).getID();

                        String eliminarpublicacion = "0";
                        eliminarPublicacion(eliminarpublicacion, position);
                        modificarPublicacion(idevento, position);


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

    private void eliminarPublicacion(String eliminarpublicacion, int position) {

        SharedPreferences preferences2 = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String idusuario = preferences2.getString("idusuario", "-");

        EditarRequest editarRequest = new EditarRequest();
        editarRequest.setIdusuario(idusuario);
        editarRequest.setPublicacion(eliminarpublicacion);


        Call<EditarResponse> editarResponseCall = ApiClientEditar.userServiceEdit().userEdit(editarRequest, idusuario);
        editarResponseCall.enqueue(new Callback<EditarResponse>() {
            @Override
            public void onResponse(Call<EditarResponse> call, Response<EditarResponse> response) {
                if (response.isSuccessful()) {

                    SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("publicaciones", eliminarpublicacion);
                    editor.commit();

                    Toast.makeText(ViajesActivity.this, "Desuscripcion Exitosa!", Toast.LENGTH_LONG).show();
                    recreate();


                } else {

                    APIError message = null;

                    try {
                        message = new Gson().fromJson(response.errorBody().string(), APIError.class);
                        Toast.makeText(ViajesActivity.this, "" + message, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<EditarResponse> call, Throwable t) {

                Toast.makeText(ViajesActivity.this, "Intente nuevamente - Throwable" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void modificarPublicacion(String idevento, int position) {

        String capacidad = "-1";
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
                        Toast.makeText(ViajesActivity.this, "" + message, Toast.LENGTH_LONG).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<InsertarPublicacionResponse> call, Throwable t) {

                Toast.makeText(ViajesActivity.this, "Intente nuevamente...", Toast.LENGTH_LONG).show();

            }
        });

    }

}




