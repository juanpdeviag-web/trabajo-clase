package com.techpark.model;

public class Atraccion {
    private String idAtraccion;
    private String nombre;
    private TipoAtraccion tipo;
    private int capacidadPorCiclo;
    private double alturaMinima;
    private int edadMinima;
    private double costoAdicional;
    private int visitantesAcumulados;
    private int tiempoEsperaMinutos;
    private EstadoAtraccion estado;
    private String motivoEstado;
    private static final int UMBRAL_MANTENIMIENTO = 500;

    public Atraccion(String idAtraccion, String nombre, TipoAtraccion tipo,
                     int capacidadPorCiclo, double alturaMinima, int edadMinima,
                     double costoAdicional) {
        this.idAtraccion = idAtraccion;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidadPorCiclo = capacidadPorCiclo;
        this.alturaMinima = alturaMinima;
        this.edadMinima = edadMinima;
        this.costoAdicional = costoAdicional;
        this.visitantesAcumulados = 0;
        this.tiempoEsperaMinutos = 0;
        this.estado = EstadoAtraccion.ACTIVA;
        this.motivoEstado = "Operativa";
    }

    public void evaluarCierrePorClima(String tipoClima) {
        if ((tipoClima.equalsIgnoreCase("lluvia") || tipoClima.equalsIgnoreCase("tormenta"))
                && (tipo == TipoAtraccion.ACUATICA || tipo == TipoAtraccion.MECANICA_ALTURA)) {
            this.estado = EstadoAtraccion.CERRADA;
            this.motivoEstado = "Cerrada por condiciones climáticas: " + tipoClima;
        }
    }

    public boolean registrarIngresoVisitante() {
        if (estado != EstadoAtraccion.ACTIVA) {
            return false;
        }

        visitantesAcumulados++;

        if (visitantesAcumulados >= UMBRAL_MANTENIMIENTO) {
            this.estado = EstadoAtraccion.EN_MANTENIMIENTO;
            this.motivoEstado = "Mantenimiento preventivo automático - 500 visitantes alcanzados";
            return true;
        }

        return true;
    }

    public String getIdAtraccion() {
        return idAtraccion;
    }

    public void setIdAtraccion(String idAtraccion) {
        this.idAtraccion = idAtraccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoAtraccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoAtraccion tipo) {
        this.tipo = tipo;
    }

    public int getCapacidadPorCiclo() {
        return capacidadPorCiclo;
    }

    public void setCapacidadPorCiclo(int capacidadPorCiclo) {
        this.capacidadPorCiclo = capacidadPorCiclo;
    }

    public double getAlturaMinima() {
        return alturaMinima;
    }

    public void setAlturaMinima(double alturaMinima) {
        this.alturaMinima = alturaMinima;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        this.costoAdicional = costoAdicional;
    }

    public int getVisitantesAcumulados() {
        return visitantesAcumulados;
    }

    public void setVisitantesAcumulados(int visitantesAcumulados) {
        this.visitantesAcumulados = visitantesAcumulados;
    }

    public int getTiempoEsperaMinutos() {
        return tiempoEsperaMinutos;
    }

    public void setTiempoEsperaMinutos(int tiempoEsperaMinutos) {
        this.tiempoEsperaMinutos = tiempoEsperaMinutos;
    }

    public EstadoAtraccion getEstado() {
        return estado;
    }

    public void setEstado(EstadoAtraccion estado) {
        this.estado = estado;
    }

    public String getMotivoEstado() {
        return motivoEstado;
    }

    public void setMotivoEstado(String motivoEstado) {
        this.motivoEstado = motivoEstado;
    }

    public void reiniciarContadorVisitantes() {
        this.visitantesAcumulados = 0;
    }

    @Override
    public String toString() {
        return String.format("%s - %s [%s] - Estado: %s - Visitantes: %d/500",
                idAtraccion, nombre, tipo, estado, visitantesAcumulados);
    }
}