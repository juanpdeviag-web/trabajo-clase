package com.techpark.model;

public class Administrador extends Empleado {

    public Administrador(String idDocumento, String nombre, int edad, double estatura,
                         String usuario, String contrasena) {
        super(idDocumento, nombre, edad, estatura, usuario, contrasena);
    }

    @Override
    public String toString() {
        return "[ADMINISTRADOR] " + super.toString();
    }
}