package com.techpark.model;

public class Operador extends Empleado {
    private Zona zonaAsignada;

    public Operador(String idDocumento, String nombre, int edad, double estatura,
                    String usuario, String contrasena, Zona zonaAsignada) {
        super(idDocumento, nombre, edad, estatura, usuario, contrasena);
        this.zonaAsignada = zonaAsignada;
    }

    public Zona getZonaAsignada() {
        return zonaAsignada;
    }

    public void setZonaAsignada(Zona zonaAsignada) {
        this.zonaAsignada = zonaAsignada;
    }

    public void realizarMantenimiento(Atraccion atraccion) {
        if (atraccion != null && atraccion.getEstado() == EstadoAtraccion.EN_MANTENIMIENTO) {
            atraccion.reiniciarContadorVisitantes();
            atraccion.setEstado(EstadoAtraccion.ACTIVA);
            atraccion.setMotivoEstado("Mantenimiento preventivo completado");
        }
    }

    @Override
    public String toString() {
        return String.format("[OPERADOR] %s - Zona: %s",
                super.toString(), zonaAsignada != null ? zonaAsignada.getNombre() : "Sin asignar");
    }
}