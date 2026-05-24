package com.techpark.model;

public class TicketFamiliar extends Ticket {
    private double porcentajeDescuento;

    public TicketFamiliar(String idTicket, double precioBase, double porcentajeDescuento) {
        super(idTicket, precioBase, TipoTicket.FAMILIAR);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecioBase() * (1 - porcentajeDescuento / 100.0);
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
}

