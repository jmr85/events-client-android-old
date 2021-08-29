package com.recitales.app.models;

public class VerViajes {

    private Evento Evento;
    private Vehiculo Vehiculo;
    private String Salida;
    private String Capacidad;
    private String ID;
    private String publicacion;
    private String idusuario;

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
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
