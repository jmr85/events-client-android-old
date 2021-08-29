package com.recitales.app.models;

public class InsertarPublicacionRequest {

    private Evento Evento;
    private Vehiculo Vehiculo;
    private String Salida;
    private int Capacidad;
    private String ID;
    private String publicacion;
    private String idusuario;

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

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }
}
