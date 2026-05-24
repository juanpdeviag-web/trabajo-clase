package com.techpark.model;

public abstract class Empleado extends Persona {
    private String usuario;
    private String contrasena;

    public Empleado(String idDocumento, String nombre, int edad, double estatura,
                    String usuario, String contrasena) {
        super(idDocumento, nombre, edad, estatura);
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean autenticar(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contrasena.equals(contrasena);
    }

    @Override
    public String toString() {
        return String.format("%s - Usuario: %s", super.toString(), usuario);
    }
}