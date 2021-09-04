package com.recitales.app.models;

import java.io.Serializable;

public class ResponseLogin  implements Serializable {
    private String clave;
    private String mail;
    //private int idusuario;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    /*public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }*/
}
