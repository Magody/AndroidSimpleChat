package com.magody.simplechatapp;

public abstract class Mensaje {
    private String mensaje;
    private String nombre;
    private String url_foto_perfil;
    private String url_foto;
    private String tipo;

    public Mensaje() {
    }

    public Mensaje(String mensaje, String nombre, String url_foto_perfil, String tipo) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.url_foto_perfil = url_foto_perfil;
        this.tipo = tipo;
    }

    public Mensaje(String mensaje, String nombre, String url_foto_perfil, String url_foto, String tipo) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.url_foto_perfil = url_foto_perfil;
        this.url_foto = url_foto;
        this.tipo = tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl_foto_perfil() {
        return url_foto_perfil;
    }

    public void setUrl_foto_perfil(String url_foto_perfil) {
        this.url_foto_perfil = url_foto_perfil;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "mensaje='" + mensaje + '\'' +
                ", nombre='" + nombre + '\'' +
                ", url_foto_perfil='" + url_foto_perfil + '\'' +
                ", url_foto='" + url_foto + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
