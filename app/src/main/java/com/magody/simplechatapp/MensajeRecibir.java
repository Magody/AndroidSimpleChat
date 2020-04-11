package com.magody.simplechatapp;

public class MensajeRecibir extends Mensaje {
    private long hora;

    public MensajeRecibir() {
    }

    public MensajeRecibir(String mensaje, String nombre, String url_foto_perfil, String url_foto, String tipo, long hora) {
        super(mensaje, nombre, url_foto_perfil, url_foto, tipo);
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String nombre, String url_foto_perfil, String tipo, long hora) {
        super(mensaje, nombre, url_foto_perfil, tipo);
        this.hora = hora;
    }

    public MensajeRecibir(long hora) {
        this.hora = hora;
    }

    public long getHora() {
        return hora;
    }

    public void setHora(long hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "MensajeRecibir{" +
                "hora=" + hora +
                '}';
    }
}
