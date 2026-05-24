package com.techpark.control;

import com.techpark.model.Administrador;
import com.techpark.model.Visitante;

public class DataSingleton {
    private static SistemaParque instancia;
    private static Visitante visitanteActivo;
    private static Administrador administradorActivo;

    public static SistemaParque getInstancia() {
        return instancia;
    }

    public static void setInstancia(SistemaParque sistema) {
        DataSingleton.instancia = sistema;
    }

    public static Visitante getVisitanteActivo() {
        return visitanteActivo;
    }

    public static void setVisitanteActivo(Visitante visitante) {
        DataSingleton.visitanteActivo = visitante;
    }

    public static Administrador getAdministradorActivo() {
        return administradorActivo;
    }

    public static void setAdministradorActivo(Administrador administrador) {
        DataSingleton.administradorActivo = administrador;
    }

    public static void cerrarSesion() {
        visitanteActivo = null;
        administradorActivo = null;
    }
}