package com.tisj.tareax.modelo;

/**
 * Created by maske on 04/11/2016.
 */
public class Comentario {

    String ciEstudiante;
    String idPractico;
    String idEjercicio;
    String fecha;
    String contenido;

    public String getCiEstudiante() {
        return ciEstudiante;
    }

    public void setCiEstudiante(String ciEstudiante) {
        this.ciEstudiante = ciEstudiante;
    }

    public String getIdPractico() {
        return idPractico;
    }

    public void setIdPractico(String idPractico) {
        this.idPractico = idPractico;
    }

    public String getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(String idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
