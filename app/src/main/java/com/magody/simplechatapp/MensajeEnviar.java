package com.magody.simplechatapp;

import java.util.Map;

public class MensajeEnviar extends Mensaje {
    private Map hora;

    private MensajeEnviar(){

    }

    public MensajeEnviar(String mensaje, String nombre, String url_foto_perfil, String tipo, Map hora) {
        super(mensaje, nombre, url_foto_perfil, tipo);
        this.hora = hora;
    }

    public MensajeEnviar(String mensaje, String nombre, String url_foto_perfil, String url_foto, String tipo, Map hora) {
        super(mensaje, nombre, url_foto_perfil, url_foto, tipo);
        this.hora = hora;
    }

    public MensajeEnviar(Map hora) {
        this.hora = hora;
    }

    public Map getHora() {
        return hora;
    }

    public void setHora(Map hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return "MensajeEnviar{" +
                "hora=" + hora +
                '}';
    }
}
