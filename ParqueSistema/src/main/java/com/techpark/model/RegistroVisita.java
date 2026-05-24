package com.techpark.model;

import java.time.LocalDateTime;

public class RegistroVisita {
    private Atraccion atraccion;
    private LocalDateTime fechaHora;
    private double gastoAdicional;
    private int tiempoEsperaMinutos;

    public RegistroVisita(Atraccion atraccion, double gastoAdicional, int tiempoEsperaMinutos) {
        this.atraccion = atraccion;
        this.gastoAdicional = gastoAdicional;
        this.tiempoEsperaMinutos = tiempoEsperaMinutos;
        this.fechaHora = LocalDateTime.now();
    }

    public Atraccion getAtraccion() {
        return atraccion;
    }

    public void setAtraccion(Atraccion atraccion) {
        this.atraccion = atraccion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getGastoAdicional() {
        return gastoAdicional;
    }

    public void setGastoAdicional(double gastoAdicional) {
        this.gastoAdicional = gastoAdicional;
    }

    public int getTiempoEsperaMinutos() {
        return tiempoEsperaMinutos;
    }

    public void setTiempoEsperaMinutos(int tiempoEsperaMinutos) {
        this.tiempoEsperaMinutos = tiempoEsperaMinutos;
    }

    @Override
    public String toString() {
        return String.format("Visita a %s - %s - Gasto: $%.2f - Espera: %d min",
                atraccion != null ? atraccion.getNombre() : "N/A",
                fechaHora, gastoAdicional, tiempoEsperaMinutos);
    }
}