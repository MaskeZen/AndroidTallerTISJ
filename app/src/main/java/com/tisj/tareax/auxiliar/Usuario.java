package com.tisj.tareax.auxiliar;

import com.tisj.tareax.modelo.Estudiante;

/**
 * Created by maske on 02/11/2016.
 */
public class Usuario {

    public static Estudiante estudiante;

    public Usuario(){}

    public Usuario(Estudiante estudiante){
        this.estudiante = estudiante;
    }

    public static Estudiante getEstudiante() {
        return estudiante;
    }

    public static void setEstudiante(Estudiante estudiante) {
        Usuario.estudiante = estudiante;
    }
}
