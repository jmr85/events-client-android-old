package com.recitales.app.models;

public class APIError {

    private String mail;
    private String clave;
    private String apellido;
    private String nombre;
    private String mensaje;

    public String getMail() {
        return mail;
    }

    public String getClave() {
        return clave;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
