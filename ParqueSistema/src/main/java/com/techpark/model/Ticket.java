package com.techpark.model;

import java.time.LocalDate;

public abstract class Ticket {
    private String idTicket;
    private LocalDate fechaEmision;
    private double precioBase;
    private TipoTicket tipo;
    private boolean usado;

    public Ticket(String idTicket, double precioBase, TipoTicket tipo) {
        this.idTicket = idTicket;
        this.precioBase = precioBase;
        this.tipo = tipo;
        this.fechaEmision = LocalDate.now();
        this.usado = false;
    }

    public abstract double calcularPrecioFinal();

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
        this.idTicket = idTicket;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public TipoTicket getTipo() {
        return tipo;
    }

    public void setTipo(TipoTicket tipo) {
        this.tipo = tipo;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    @Override
    public String toString() {
        return String.format("Ticket %s [%s] - Precio Final: $%.2f - Emitido: %s",
                idTicket, tipo, calcularPrecioFinal(), fechaEmision);
    }
}
