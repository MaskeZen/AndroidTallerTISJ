package com.tisj.tareax.modelo;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maske on 04/11/2016.
 */
public class Ejercicio {

    String idPractico;
    String numero;
    String imagenUrl;
    Bitmap imagen;
    List<Comentario> comentarios;

    public Ejercicio() {
        comentarios = new ArrayList<>();
    }

    public String getIdPractico() {
        return idPractico;
    }

    public void setIdPractico(String idPractico) {
        this.idPractico = idPractico;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(ArrayList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
