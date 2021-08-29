package com.recitales.app.models;

public class Publicaciones {

    private Evento Evento;
    private Vehiculo Vehiculo;
    private String Salida;
    private String Capacidad;
    private String ID;
    private Usuario Usuario;

    public com.recitales.app.models.Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(com.recitales.app.models.Usuario usuario) {
        Usuario = usuario;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public com.recitales.app.models.Evento getEvento() {
        return Evento;
    }

    public void setEvento(com.recitales.app.models.Evento evento) {
        Evento = evento;
    }

    public com.recitales.app.models.Vehiculo getVehiculo() {
        return Vehiculo;
    }

    public void setVehiculo(com.recitales.app.models.Vehiculo vehiculo) {
        Vehiculo = vehiculo;
    }

    public String getSalida() {
        return Salida;
    }

    public void setSalida(String salida) {
        Salida = salida;
    }

    public String getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(String capacidad) {
        Capacidad = capacidad;
    }
}
