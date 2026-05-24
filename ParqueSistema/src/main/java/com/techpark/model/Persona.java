package com.techpark.model;

public abstract class Persona {
    private String idDocumento;
    private String nombre;
    private int edad;
    private double estatura;

    public Persona(String idDocumento, String nombre, int edad, double estatura) {
        this.idDocumento = idDocumento;
        this.nombre = nombre;
        this.edad = edad;
        this.estatura = estatura;
    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (Edad: %d, Estatura: %.2fm)",
                idDocumento, nombre, edad, estatura);
    }
}